package com.lovin.zookeeper.Curator.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

public class CuratorLockDemo {

	//server连接字符串
	private static final String CONNECTION_STRING="192.168.20.104:2181,192.168.20.104:2182,192.168.20.104:2183";
	
	//连接超时时间
	private static final int SESSION_TIMEOUT=5000;
	
	private static final String CURATOR_LOCK_ROOT="/curator_lock";

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(10);
		for(int i=0;i<10;i++){
			Runnable runnable = new Runnable() {
				
				public void run() {
					try {
						CuratorFramework curatorFramework = 
								CuratorFrameworkFactory.newClient(CONNECTION_STRING, new RetryNTimes(10, 5000)); //循环创建客户端
						curatorFramework.start();
						semaphore.acquire();
						doLock(curatorFramework);
						semaphore.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			executorService.execute(runnable);
		}
		executorService.shutdown();
	}
	
	private static void doLock(CuratorFramework curatorFramework){
		System.out.println(Thread.currentThread().getName()+" try to get lock!");
		InterProcessMutex mutex = new InterProcessMutex(curatorFramework, CURATOR_LOCK_ROOT);
		try {
			if(mutex.acquire(5, TimeUnit.SECONDS)){ //acquire获取锁
				System.out.println(Thread.currentThread().getName() + " hold lock");
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				mutex.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
