package thread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThreadSleep2 extends Thread{
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

	public static void main(String[] args) {
		new MyThreadSleep2().start();
		new MyThreadSleep2().start();
	}
	
	public void run() {
		while(true){
			System.out.println("�߳����� "+Thread.currentThread().getName()+" ִ��ʱ�䣺"+df.format(new Date()));
			
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
