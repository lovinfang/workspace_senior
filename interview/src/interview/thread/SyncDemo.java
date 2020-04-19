package interview.thread;

public class SyncDemo implements Runnable{

	private static int a = 0;
	
	public void run() {
		for(int i=0;i<=5;i++){
//			a=a+1;
			a++;
		}
	}
	
	public static void main(String[] args) {
		SyncDemo sd = new SyncDemo();
		Thread td = new Thread(sd,"Ïß³ÌÃû³Æ-A");
		td.start();
		try {
			td.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(SyncDemo.a);
	}

}
