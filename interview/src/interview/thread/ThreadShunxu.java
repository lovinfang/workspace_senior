package interview.thread;

public class ThreadShunxu {
	
	public static void main(String[] args) {
		Object lock = new Object();
		ThreadA threadA = new ThreadShunxu().new ThreadA(lock);
		ThreadB threadB = new ThreadShunxu().new ThreadB(lock);
		new Thread(threadA).start();
		new Thread(threadB).start();
	}
	
	class ThreadA implements Runnable{
		private Object lock;
		public ThreadA(Object lock){
			this.lock = lock;
		}
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("�߳�A��ʼ����");
			synchronized(lock){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("�߳�A��������");
			}
		}
	}
	
	class ThreadB implements Runnable{
		private Object lock;
		public ThreadB(Object lock){
			this.lock = lock;
		}
		public void run() {
			System.out.println("�߳�B��ʼ����");
			synchronized(lock){
				lock.notify();
				System.out.println("�߳�B��������");
			}
		}
	}

}
