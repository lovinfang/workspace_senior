package com.lovin.zookeeper.Curator;

import java.io.IOException;
import java.util.Collection;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;

public class GuratorDemo {

	private static String CONNECTION_STRING="192.168.20.104:2181,192.168.20.104:2182,192.168.20.104:2183";
	private static int 	SESSION_TIMEOUT=3000;
	
	public static void main(String[] args) {
		//session连接链式     ExponentialBackoffRetry重试策略，1000：重试间隔时间1秒，10：重试次数为10次
		//连接Zookeeper服务  链式结构 fluent格式的写法
		CuratorFramework framework =  CuratorFrameworkFactory
				.newClient(CONNECTION_STRING,SESSION_TIMEOUT,SESSION_TIMEOUT,new ExponentialBackoffRetry(1000, 10));
		
		//启动服务
		framework.start();
		
//		create(framework);
//		update(framework);
//		delete(framework);
//		transaction(framework);
//		listenerDemo1(framework);
		listenerDemo2(framework);
		//输出连接状态
		CuratorFrameworkState state = framework.getState();
		System.out.println(state);
	}
	
	private static void create(CuratorFramework framework){
		try {
			/**
			 * creatingParentsIfNeeded  如果父类不存在，则创建，递归创建节点
			 * withMode(CreateMode.PERSISTENT) 持久化节点
			 * "zzy".getBytes()  是给最后一个节点赋值
			 */
			String rs = framework.create().creatingParentsIfNeeded()
					.withMode(CreateMode.PERSISTENT).forPath("/node_6/node_6_1","zzy".getBytes());
			/**
			 * inBackground 表示是后台操作，异步返回通知
			 */
//			String rs = framework.create().withMode(CreateMode.EPHEMERAL).inBackground()
//						.forPath("/node_7/node_7_1","zzy".getBytes());
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			framework.close();
		}
	} 
	
	private static void update(CuratorFramework framework){
		try {
			Stat stat = framework.setData().forPath("/node_6/node_6_1","xyz".getBytes());
			System.out.println(stat); //输出节点详细信息
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			framework.close();
		}
	}
	
	private static void delete(CuratorFramework framework){
		try {
			//删除node_6_1
//			framework.delete().deletingChildrenIfNeeded().forPath("/node_6/node_6_1");
			//删除node_6以及下面的子节点
			framework.delete().deletingChildrenIfNeeded().forPath("/node_6");
			
			framework.delete().forPath("/node_6"); //如果node_6下面有子节点，会报错
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			framework.close();
		}
	}
	
	/**
	 * 开启一个事务 创建 /node_2 /node_3节点，删除/node_99节点
	 * 如果 /node_2 /node_3任何一个节点存在但是要创建 ，/node_99不存在，但是要删除都会回滚这个事务，一个都不成功
	 * 可以自动回滚事务
	 * @param framework
	 */
	private static void transaction(CuratorFramework framework){
		try {
			Collection<CuratorTransactionResult> results 
				= framework.inTransaction().create().forPath("/node_2")
				.and().create().forPath("/node_3").and().delete().forPath("/node_99").and().commit();
			for(CuratorTransactionResult result : results){
				System.out.println(result.getResultStat()+"->"+result.getForPath()+"->"+result.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void listenerDemo1(CuratorFramework framework){
		try {
/*			framework.getCuratorListenable().addListener(new CuratorListener() {
				
				public void eventReceived(CuratorFramework client, CuratorEvent event)throws Exception {
					System.out.println("触发事件:"+event.getType());
				}
			});
*/			
			/**
			 * 通过CuratorWatcher去监听指定节点的事件，只监听一次
			 */
			framework.getData().usingWatcher(new CuratorWatcher() {
				public void process(WatchedEvent event) throws Exception {
					System.out.println("触发时间"+event.getType());
				}
			}).forPath("/node_2");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 时刻监听node_2的字节点的事件，不包含node_2节点本身
	 * NodeCacheListenner  TreeCacheListenner  可以参考PathChildrenCache
	 * @param framework
	 */
	private static void listenerDemo2(CuratorFramework framework){
		try {
			PathChildrenCache childrenCache = new PathChildrenCache(framework, "/node_2", true); //true表示是否缓存数据
			childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);//启动的时候就监听
			childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
				public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)throws Exception {
					System.out.println(event.getType()+"事件监听");
				}
			});
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
