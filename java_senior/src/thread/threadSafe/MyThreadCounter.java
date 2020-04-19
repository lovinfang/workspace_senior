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
		//ÿ���߳���list���100��Ԫ��
		for(int i=0;i<100;i++){
			counter.addCount();
		}
		countDownLatch.countDown();
	}
	
	public static void main(String[] args) {
		
		int threadCount = 1000;
		//�����̵߳ȵ����߳�ִ�����
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);
		Counter counter = new Counter();
		
		for(int i=0;i<threadCount;i++){
			Thread thread = new Thread(new MyThreadCounter(counter,countDownLatch));
			thread.start();
		}
		
		//���̵߳ȴ��������߳�ִ����ϣ�������ִ��
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(counter.getCount());
	}
	
}
