package thread.threadSafe;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public class MyThreadLocal implements Runnable{


	/*
	
	private static ThreadLocal<Integer> num = new ThreadLocal<Integer>();
	
	// 线程安全
	public void run() {
		num.set(0);
		
		for(int i=0;i<3;i++){
			num.set(num.get()+1);
			System.out.println(Thread.currentThread().getName()+" num="+num.get());
		}
	}
	
	public static void main(String[] args) {
		MyThreadLocal myThreadLocal = new MyThreadLocal();
		Thread thread1 = new Thread(myThreadLocal,"线程1");
		Thread thread2 = new Thread(myThreadLocal,"线程2");
		Thread thread3 = new Thread(myThreadLocal,"线程3");
		Thread thread4 = new Thread(myThreadLocal,"线程4");
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}

 ##############################################################################################################
	*/
	
	private List<Object> list;
	
	private CountDownLatch countDownLatch;
	
	private MyThreadLocal(List<Object> list,CountDownLatch countDownLatch){
		this.list = list;
		this.countDownLatch = countDownLatch;
	}
	
	
	@Override
	public void run() {
		//每个线程向list添加100个元素
		for(int i=0;i<100;i++){
			list.add(new Object());
		}
		countDownLatch.countDown();
	}
	
	public static void main(String[] args) {
//		List<Object> list = new ArrayList<Object>();   //线程不安全 得不到1000天数据，也许还会报错，扩容的速度比不上插进数据的速度就报错
		List<Object> list = new Vector<Object>();
		
		int threadCount = 1000;
		//让主线程等到子线程执行完毕
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);
		
		for(int i=0;i<threadCount;i++){
			Thread thread = new Thread(new MyThreadLocal(list,countDownLatch));
			thread.start();
		}
		
		//主线程等待所有子线程执行完毕，再向下执行
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(list.size());
	}
	
}
