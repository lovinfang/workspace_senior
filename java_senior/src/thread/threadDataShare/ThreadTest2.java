package thread.threadDataShare;

public class ThreadTest2 {

	public static void main(String[] args) {
		//把资源类引入进来
		final BusinessDemo demo = new BusinessDemo();
		//子线程部分要求:是50轮换，每个轮换循环跑30次
		new Thread(new Runnable() {
			public void run() {
				for(int i=1;i<=50;i++){
					//子线程要跑30次
					//操作资源类
					demo.sonBusiness(i);
				}
			}
		}).start();
		
		for(int i=1;i<=50;i++){
			//主线程进来一次，40次
			//操作资源类
			demo.mainBusiness(i);
		}
	}
}
