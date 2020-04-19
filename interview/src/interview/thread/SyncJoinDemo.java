package interview.thread;

public class SyncJoinDemo implements Runnable{

	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
	
	public static void main(String[] args) {
		try {
			SyncJoinDemo sd = new SyncJoinDemo();
			Thread td1 = new Thread(sd,"Thread-A");
			Thread td2 = new Thread(sd,"Thread-B");
			Thread td3 = new Thread(sd,"Thread-C");
			td1.start();
			td1.join();
			td2.start();
			td2.join();
			td3.start();
			td3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
