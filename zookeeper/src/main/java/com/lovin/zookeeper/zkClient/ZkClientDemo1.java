package com.lovin.zookeeper.zkClient;

import java.io.IOException;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 当前类与ZkClientDemo2共同搭建一个集群配置文件 
 * @author Administrator
 *
 */
public class ZkClientDemo1 {

	static ZkClient zk;
	
	private static String CONNECTION_STRING="192.168.20.104:2181,192.168.20.104:2182,192.168.20.104:2183";
	private static int 	SESSION_TIMEOUT=3000;
	
	 static {
	        zk=new ZkClient(CONNECTION_STRING,SESSION_TIMEOUT,SESSION_TIMEOUT,new MyZkSerializer());
	 }
	
	private static void initData(){
        if(!zk.exists("/configuration")) {
            zk.createPersistent("/configuration");
        }
        zk.createPersistent("/configuration/userName","root");
        zk.createPersistent("/configuration/password","password");
	 }
	
	public static void main(String[] args) {
//		initData();
		//subscribeDataChanges只能监听当前path /configuration/userName 节点的数据的修改，删除，不能监听configuration下的password
		zk.subscribeDataChanges("/configuration/userName", new IZkDataListener() {
			public void handleDataDeleted(String dataPath) throws Exception {
				
			}
			
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("触发事件:"+dataPath);
			}
		});
		
		//subscribeChildChanges 是订阅监听当前Path节点/configuration 下字节点的增加和删除事件，子节点数据修改不监听
		zk.subscribeChildChanges("/configuration", new IZkChildListener() {
			public void handleChildChange(String parentPath, List<String> currentChildren) throws Exception {
				System.out.println("触发事件:"+parentPath);
				for(String str:currentChildren){
					System.out.println(str);
				}
			}
		});
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			zk.close();
		}
	}
}
