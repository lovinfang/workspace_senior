package com.lovin.zookeeper.zkClient.master;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * 通过ZkClient实现创建节点来争抢Master节点
 * @author lovin
 *
 */
public class MasterServer {

	//表示这是zkClient客户端
	private ZkClient zkClient;
	
	//表示这是一个争抢的master节点
	private final String MASTER_NODE="/master";
	
	//server连接字符串
	private static final String CONNECTION_STRING="192.168.20.104:2181,192.168.20.104:2182,192.168.20.104:2183";
	
	//连接超时时间
	private static final int SESSION_TIMEOUT=5000;
	
	//争抢master节点的服务器
	private ServerData serverData;
	
	//争抢到master节点的服务器信息
	private ServerData masterData; 
	
	/**
	 * 服务是否是启动状态,分布式共享锁
	 * volatile:在多线程情况下，保证这个running对所有线程是有可见性
	 */
	private volatile boolean running = false;  
	
	//master节点的监听事件
	IZkDataListener dataListener;
	
	private ScheduledExecutorService scheService = Executors.newScheduledThreadPool(1);  //创建一个定时器
	
	public MasterServer(ZkClient zkClient ,ServerData serverData){
		this.zkClient = zkClient;
		this.serverData = serverData; //表示当前来争抢master节点的服务
		dataListener = new IZkDataListener() { //初始化一个监听
			
			//当master节点删除的时候就会去争抢master节点，所以监听这个事件
			public void handleDataDeleted(String dataPath) throws Exception {
				//每5秒钟去抢一次
				scheService.schedule(new Runnable() {
					public void run() {
						takeMaster();
					}
				}, 5, TimeUnit.SECONDS);
			}
			
			public void handleDataChange(String datapath, Object data) throws Exception {}
		};
	}
	
	//开始争抢master的方法
	public void start(){
		if(running){
			throw new RuntimeException("服务已经启动");
		}
		running = true;
		//给master节点监听一个事件
		zkClient.subscribeDataChanges(MASTER_NODE, dataListener);
		takeMaster();
	}
	
	//停止争抢master
	public void stop(){
		if(!running){
			throw new RuntimeException("服务已经停止了");
		}
		running = false;
		zkClient.subscribeDataChanges(MASTER_NODE, dataListener);
		releaseMaster();
	}
	
	//争抢master节点的具体实现
	private void takeMaster(){
		if(!running){
			//如果服务未启动，返回
			return;
		}
		System.out.println(serverData.getServerName()+" 来抢master节点");
		try{
			//创建一个临时节点，考虑到master节点会断开，并不是一个持久化的节点，所以这里创建一个临时节点
			zkClient.createEphemeral(MASTER_NODE,serverData); //用到了一个临时节点的特性
			masterData = serverData;
			System.out.println(masterData.getServerName()+"成功抢到master节点");
			scheService.schedule(new Runnable() { //每5秒钟释放一次，定时任务，这里只是为了演示，所以每5秒钟释放一次
				public void run() {
					if(checkMaster()){ //判断如果当前节点为master节点，释放
						zkClient.delete(MASTER_NODE);
					}
				}
			}, 5, TimeUnit.SECONDS);
		}catch(ZkNodeExistsException e){
			//抛出节点存在异常，意味着这个节点存在，读取此时master节点信息
			ServerData serverData = zkClient.readData(MASTER_NODE);
			if(serverData == null){
				//在读取过程中，发现master节点已经被释放，继续争抢节点
				takeMaster();
			}else{
				masterData = serverData;
			}
		}
	}
	
	//释放master节点
	private void releaseMaster(){
		if(checkMaster()){
//			zkClient.close();//也可以释放
			/**
			 * 在这里使用delete，是因为删除节点的时候，我们会有一个监听事件，模拟场景继续做选举
			 */
			zkClient.delete(MASTER_NODE);
		}
	}
	
	//校验当前的服务器是不是master
	private boolean checkMaster(){
		try{
			//读取当前master节点的数据并肤质给masterData
			ServerData ms = zkClient.readData(MASTER_NODE);
			masterData = ms;
			/**
			 * 这个时候，如果master节点的数据和当前过来争抢master节点的服务器的数据是一样的话，那么意味着当前的 serverData就是master
			 */
			if(masterData.getServerName().equals(serverData.getServerName())){
				return true;
			}
			return false;
		}catch(ZkNoNodeException e){
			return false;
		}catch(ZkInterruptedException e){
			return checkMaster();
		}catch(ZkException e){
			return false;
		}
	}
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool(); //定义一个线程池
		final Semaphore semaphore = new Semaphore(10);  //假设10个并发
		for(int i=0;i<10;i++){
			final int idx = i;
			//假设有10个客户端
			Runnable runnable = new Runnable() {
				
				public void run() {
					try {
						semaphore.acquire(); //当有十个线程的时候才开始执行，模拟高并发
						//初始化一个zkClient的连接
						ZkClient zkClient = new ZkClient(CONNECTION_STRING,
								SESSION_TIMEOUT,SESSION_TIMEOUT,new SerializableSerializer());
						//定义一台争抢master节点的服务器
						ServerData serverData = new ServerData();
						serverData.setServerId(idx);
						serverData.setServerName("#server-"+idx);
						//初始化一个争抢master节点的服务
						MasterServer ms = new MasterServer(zkClient, serverData);
						ms.start();
						semaphore.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} //当有10个线程的时候，才开始启动
				}
			};
			service.execute(runnable);  //装入线程池
		}
		service.shutdown();
	}
}
