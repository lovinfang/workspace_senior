package thread.threadDataShare;

import java.util.Random;

/**
 * 多线程(多客户端)下单
 * 点击购买商品类
 * 产生一个多线程来维护这个购买信息(价格信息) 
 * 进入到服务模块做N多处理
 */
public class ThreadSourceData {

	private static int price;
	
	public static void main(String[] args) {
		for(int i=0;i<3;i++){
			new Thread(new Runnable() {
				public void run() {
					//字节类，资源，同一把锁
					synchronized (ThreadSourceData.class) {
						//价格信息 
						price = new Random().nextInt(10000);
						System.out.println("产生线程名称："+Thread.currentThread().getName()+" ,价格为： "+price);
						//进入A模块做处理
						new A().getInfo();
						new B().getInfo();
					}
				}
			}).start();
		}
	}
	
	//A 处理客户端收货地址模块
	static class A{
		public void getInfo(){
			System.out.println(Thread.currentThread().getName()+" 进入A模块，处理的价格信息是: "+price);
		}
	}
	
	static class B{
		public void getInfo(){
			System.out.println(Thread.currentThread().getName()+" 进入B模块，处理的价格信息是: "+price);
		}
	}
}
