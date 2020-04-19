package com.lovin.zookeeper.zkClient;

import java.io.IOException;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import com.lovin.zookeeper.bean.Student;

/**
 * 测试Zookeeper的ZkClient实例
 * @author Administrator
 *
 */
public class ZkClientDemo {
	
	//集群的ip：port
	private static String CONNECTION_STRING="192.168.20.104:2181,192.168.20.104:2182,192.168.20.104:2183";
	//session 失效时间 3秒
	private static int 	SESSION_TIMEOUT=3000;

	public static void main(String[] args) {
		//Zookeeper集群ip，session失效时间，客户端连接失效时间，序列化
		//new SerializableSerializer()  自带的序列化有乱码
		ZkClient zkClient = 
				new ZkClient(CONNECTION_STRING, SESSION_TIMEOUT, SESSION_TIMEOUT, new MyZkSerializer());
		try{
//			create(zkClient);
//			update(zkClient);
//			delete(zkClient);
			subwatch(zkClient);
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			zkClient.close();
		}
	}
	
	/**
	 * 创建节点
	 * @param zkClient
	 */
	private static void create(ZkClient zkClient){
		//此时不需要"abc".getBytes(),因为已经序列化了,创建的是临时节点
//			zkClient.createEphemeral("/node_5", "abc"); 
		/*
		 zkClient.createPersistent("/node_5");// 创建的是持久化节点
		 System.out.println(zkClient.readData("/node_5").toString());
		*/
		/**
		 * 递归创建多级目录
		 */
		zkClient.createPersistent("/node_5/node_5_1/node_5_1_1", true);
	}
	
	/**
	 * 更新节点内容值
	 * @param zkClient
	 */
	private static void update(ZkClient zkClient){
		zkClient.writeData("/node_5", "abc");
	}
	
	/**
	 * 删除节点以及递归删除子节点
	 * @param zkClient
	 */
	private static void delete(ZkClient zkClient){
		/*
		boolean bool = zkClient.delete("/node_5");
		System.out.println(bool);
		*/
		/**
		 * 递归删除
		 */
//		boolean bool = zkClient.delete("/node_5/node_5_1/node_5_1_1"); // 只会删除最后一级
		boolean bool = zkClient.deleteRecursive("/node_5"); //删除node_5 以及node_5下面的子节点
		System.out.println(bool);
	}
	
	private static void subwatch(ZkClient zkClient){
		if(!zkClient.exists("/node_5")){
			zkClient.createPersistent("/node_5");
		}
		//数据订阅事件
		zkClient.subscribeDataChanges("/node_5", new IZkDataListener() {
			
			/**
			 * 节点删除事件
			 */
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("触发删除事件:"+dataPath);
			}
			
			/**
			 * 数据改变
			 */
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("触发事件: "+dataPath+"->"+data);
			}
		});
	}
}
