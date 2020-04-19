package thread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThreadSynchronized {
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static void main(String[] args) {
		MyThreadSynchronized ms = new MyThreadSynchronized();
		MyTest mt = ms.new MyTest();
//		ms.new MyThread(mt).start();
//		ms.new MyThread(mt).start();
		
//		ms.new MyThread(ms.new MyTest()).start();
//		ms.new MyThread(ms.new MyTest()).start();
		
		ms.new MyThread1(mt).start();
		ms.new MyThread(mt).start();
	}
	
	
	class MyTest{
		public void a() {
			System.out.println("线程名称 "+Thread.currentThread().getName()
					+" 执行时间："+df.format(new Date())+" method : a() ");
		}
		
		public synchronized void c(){
			System.out.println("线程名称 "+Thread.currentThread().getName()
					+" 执行时间："+df.format(new Date())+" method : c() ");
		}
		
		public synchronized void b(){
			System.out.println("线程名称 "+Thread.currentThread().getName()
					+" 执行时间："+df.format(new Date())+" method : b() ");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class MyThread extends Thread{
		
		MyTest mt;
		
		MyThread(MyTest mt){
			this.mt = mt;
		}
		
		public void run() {
			mt.b();
		}
	}
	
	class MyThread1 extends Thread{
		
		MyTest mt;
		
		MyThread1(MyTest mt){
			this.mt = mt;
		}
		
		public void run() {
			mt.c();
		}
	}
}
