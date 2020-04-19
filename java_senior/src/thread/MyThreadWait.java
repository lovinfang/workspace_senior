package thread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThreadWait {
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//代表我们程序的临界资源
	String flag[] = {"true"};
	
	int count = 0;
	
	public static void main(String[] args) {
		MyThreadWait wait = new MyThreadWait();
		wait.new AThread().start();
		wait.new BThread().start();
	}
	
	
	/**
	 * 负责验证这个wait方法
	 * @author Administrator
	 */
	class AThread extends Thread{
		
		public void run() {
			while(flag[0] != "false"){
				if(count >= 100){
					try {
						System.out.println("线程名称 "+Thread.currentThread().getName()
								+" 执行时间："+df.format(new Date())+" 开始等待了");
						long curTime = System.currentTimeMillis();
						
						synchronized (flag) {
							flag.wait();
						}
						
						System.out.println("线程名称 "+Thread.currentThread().getName()
								+" 执行时间："+df.format(new Date())+" 等待时间为："+(System.currentTimeMillis() - curTime));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					System.out.println("线程名称 "+Thread.currentThread().getName()
							+" 执行时间："+df.format(new Date())+" count:"+ count++);
				}
			}
		}
	}
	
	//负责唤醒所有的A线程
	class BThread extends Thread{
		public void run() {
			
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (flag) {
				flag[0] = "false";
				System.out.println("线程名称 "+Thread.currentThread().getName()
						+" 执行时间："+df.format(new Date())+"我要唤醒a线程");
				flag.notifyAll();
			}
		}
	}
}
