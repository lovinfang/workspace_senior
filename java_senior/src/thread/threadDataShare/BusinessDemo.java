package thread.threadDataShare;

public class BusinessDemo {

	//内部有一个控制属性  开关
	private boolean isShowSonThread=true;
	//子线程先进来
	public synchronized void sonBusiness(int i){
		while(!isShowSonThread){
			try{
				//子线程就在外面等着
				this.wait();
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
		//
		for(int j=1;j<=30;j++){
			System.out.println("============子线程运行第： "+j+"轮，第 "+j+" 次");
		}
		
		isShowSonThread = false;
		//通知主线程
		this.notify();
	}
	
	//主线程业务模块，synchronized隐式锁，锁对象 封装到里面，不好控制，效率非常低下
	// synchronized 时间换线程安全(线程空间)
	public synchronized void mainBusiness(int i){
		while(isShowSonThread){
			try{
				this.wait();
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
		//主线程做如下业务
		for(int j=1;j<=40;j++){
			System.out.println("主线程运行第: "+i+"轮，第："+j+" 次");
		}
		
		isShowSonThread = true;
		//通知子线程
		this.notify();
	}
}
