package thread;

public class MyThreadSleep extends Thread{
	
	
	MyClass my = new MyClass();
	
	public MyThreadSleep(MyClass myclass){
		this.my = myclass;
	}
	
	public MyClass getMy() {
		return my;
	}

	public void setMy(MyClass my) {
		this.my = my;
	}

	public static void main(String[] args) {
		
		MyClass my = new MyClass();
		MyThreadSleep mythread1 = new MyThreadSleep(my);
		MyThreadSleep mythread2 = new MyThreadSleep(my);
		mythread1.start();
		mythread2.start();
	}
	
	public void run() {
		while(true){
			my.b();
		}
	}

}
