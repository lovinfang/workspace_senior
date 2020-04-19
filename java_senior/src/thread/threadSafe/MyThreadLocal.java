package thread.threadSafe;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public class MyThreadLocal implements Runnable{


	/*
	
	private static ThreadLocal<Integer> num = new ThreadLocal<Integer>();
	
	// �̰߳�ȫ
	public void run() {
		num.set(0);
		
		for(int i=0;i<3;i++){
			num.set(num.get()+1);
			System.out.println(Thread.currentThread().getName()+" num="+num.get());
		}
	}
	
	public static void main(String[] args) {
		MyThreadLocal myThreadLocal = new MyThreadLocal();
		Thread thread1 = new Thread(myThreadLocal,"�߳�1");
		Thread thread2 = new Thread(myThreadLocal,"�߳�2");
		Thread thread3 = new Thread(myThreadLocal,"�߳�3");
		Thread thread4 = new Thread(myThreadLocal,"�߳�4");
		
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
		//ÿ���߳���list���100��Ԫ��
		for(int i=0;i<100;i++){
			list.add(new Object());
		}
		countDownLatch.countDown();
	}
	
	public static void main(String[] args) {
//		List<Object> list = new ArrayList<Object>();   //�̲߳���ȫ �ò���1000�����ݣ�Ҳ���ᱨ�����ݵ��ٶȱȲ��ϲ�����ݵ��ٶȾͱ���
		List<Object> list = new Vector<Object>();
		
		int threadCount = 1000;
		//�����̵߳ȵ����߳�ִ�����
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);
		
		for(int i=0;i<threadCount;i++){
			Thread thread = new Thread(new MyThreadLocal(list,countDownLatch));
			thread.start();
		}
		
		//���̵߳ȴ��������߳�ִ����ϣ�������ִ��
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(list.size());
	}
	
}
