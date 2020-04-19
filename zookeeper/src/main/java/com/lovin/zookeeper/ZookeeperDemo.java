package com.lovin.zookeeper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

/**
 * 测试zookeeper的增删改等基础操作
 * @author Administrator
 *
 */
public class ZookeeperDemo {

	private static final int SESSION_TIMEOUT=3000;
	
	public static void main(String[] args) {
		ZooKeeper zk = null;
		try {
			//创建连接
			zk = new ZooKeeper("192.168.20.104:2181",SESSION_TIMEOUT,new Watcher(){
				public void process(WatchedEvent event) {
					System.out.println("触发时间:"+event.getType());
				}
			});
//			createDemo(zk);
//			updateDemo(zk);
//			deleteDemo(zk);
//			aclDemo(zk);
//			watcherDemo(zk);
			watcherDemo2(zk);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
//		catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
		finally{
			if(zk != null){
				try {
					zk.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 创建节点
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	private static void createDemo(ZooKeeper zk) throws KeeperException, InterruptedException{
		if(zk.exists("/node_2", true) == null){
			// ZooDefs.Ids.OPEN_ACL_UNSAFE 对所有用户开放    CreateMode.PERSISTENT 持久化节点
			zk.create("/node_2", "abc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			// 获取信息      path, watch:true创建监控, stat:节点详细信息
			System.out.println(new String(zk.getData("/node_2", true, null)));     
		}
	}
	
	/**
	 * 修改节点
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	private static void updateDemo(ZooKeeper zk) throws KeeperException, InterruptedException{
		//version -1 表示不需要判断任何版本
		zk.setData("/node_2", "www".getBytes(), -1);
		System.out.println(new String(zk.getData("/node_2", true, null)));     
	}
	
	/**
	 * 删除节点
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	private static void deleteDemo(ZooKeeper zk) throws KeeperException, InterruptedException{
		zk.delete("/node_2", -1);
		System.out.println(new String(zk.getData("/node_2", true, null)));     
	}
	
	/**
	 * 权限问题
	 * @param zk
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws NoSuchAlgorithmException 
	 */
	private static void aclDemo(ZooKeeper zk) throws KeeperException, InterruptedException, NoSuchAlgorithmException{
		if(zk.exists("/node_3", true) == null){
			//创建一个ACL权限
			ACL acl = new ACL(ZooDefs.Perms.ALL,new Id("digest",DigestAuthenticationProvider.generateDigest("root:root")));
			List<ACL> acls = new ArrayList<ACL>();
			acls.add(acl);
			//加入权限
			zk.create("/node_3", "abc".getBytes(),acls,  CreateMode.PERSISTENT);
			System.out.println(new String(zk.getData("/node_3", true, null)));     
		}
		zk.addAuthInfo("digest", "root:root".getBytes());
		System.out.println(new String(zk.getData("/node_3", true, null)));
	}
	
	/**
	 * 监听节点
	 * @param zk
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	private static void watcherDemo(ZooKeeper zk) throws KeeperException, InterruptedException{
		zk.create("/node_4", "abc".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//添加监听事件
		byte [] rsbyte = zk.getData("/node_4", new Watcher() {
			
			public void process(WatchedEvent event) {
				System.out.println("触发节点事件:"+event.getType());
//				System.out.println("触发节点事件:"+event.getPath());
			}
		}, null);
		System.out.println(new String(rsbyte)); 
		
		zk.setData("/node_4", "pk".getBytes(), -1);  //对节点进行更新，可以查看该节点触发了什么事件
//		System.out.println(new String(zk.getData("/node_4", true, null)));   
	}
	
	
	/**
	 * 监听节点
	 * @param zk
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	private static void watcherDemo2(final ZooKeeper zk) throws KeeperException, InterruptedException{
		if(zk.exists("/node_2", true) == null){
			zk.create("/node_4", "abc".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		//添加监听事件
		byte [] rsbyte = zk.getData("/node_4", new Watcher() {
			
			public void process(WatchedEvent event) {
				System.out.println("触发节点事件:"+event.getPath());
				try {
					System.out.println(new String(zk.getData(event.getPath(), true, null)));
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, null);
		System.out.println(new String(rsbyte)); 
		
		zk.setData("/node_4", "pksyz".getBytes(), -1);  //对节点进行更新，可以查看该节点触发了什么事件
	}
}
