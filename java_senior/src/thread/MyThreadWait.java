package thread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThreadWait {
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//�������ǳ�����ٽ���Դ
	String flag[] = {"true"};
	
	int count = 0;
	
	public static void main(String[] args) {
		MyThreadWait wait = new MyThreadWait();
		wait.new AThread().start();
		wait.new BThread().start();
	}
	
	
	/**
	 * ������֤���wait����
	 * @author Administrator
	 */
	class AThread extends Thread{
		
		public void run() {
			while(flag[0] != "false"){
				if(count >= 100){
					try {
						System.out.println("�߳����� "+Thread.currentThread().getName()
								+" ִ��ʱ�䣺"+df.format(new Date())+" ��ʼ�ȴ���");
						long curTime = System.currentTimeMillis();
						
						synchronized (flag) {
							flag.wait();
						}
						
						System.out.println("�߳����� "+Thread.currentThread().getName()
								+" ִ��ʱ�䣺"+df.format(new Date())+" �ȴ�ʱ��Ϊ��"+(System.currentTimeMillis() - curTime));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					System.out.println("�߳����� "+Thread.currentThread().getName()
							+" ִ��ʱ�䣺"+df.format(new Date())+" count:"+ count++);
				}
			}
		}
	}
	
	//���������е�A�߳�
	class BThread extends Thread{
		public void run() {
			
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (flag) {
				flag[0] = "false";
				System.out.println("�߳����� "+Thread.currentThread().getName()
						+" ִ��ʱ�䣺"+df.format(new Date())+"��Ҫ����a�߳�");
				flag.notifyAll();
			}
		}
	}
}
