package thread.threadSafe;

import java.util.concurrent.CountDownLatch;

public class MyThreadCounter implements Runnable{

	
	private Counter counter;
	private CountDownLatch countDownLatch;
	
	private MyThreadCounter(Counter counter,CountDownLatch countDownLatch){
		this.counter = counter;
		this.countDownLatch = countDownLatch;
	}
	
	
	@Override
	public void run() {
		//每个线程向list添加100个元素
		for(int i=0;i<100;i++){
			counter.addCount();
		}
		countDownLatch.countDown();
	}
	
	public static void main(String[] args) {
		
		int threadCount = 1000;
		//让主线程等到子线程执行完毕
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);
		Counter counter = new Counter();
		
		for(int i=0;i<threadCount;i++){
			Thread thread = new Thread(new MyThreadCounter(counter,countDownLatch));
			thread.start();
		}
		
		//主线程等待所有子线程执行完毕，再向下执行
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(counter.getCount());
	}
	
}
