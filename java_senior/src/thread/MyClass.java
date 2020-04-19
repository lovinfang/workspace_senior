package thread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyClass {

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	void b() {
		while(true){
			System.out.println("线程名称 "+Thread.currentThread().getName()+" 执行时间："+df.format(new Date()));
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
