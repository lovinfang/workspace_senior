package thread.threadDataShare;

import java.util.Random;

/**
 * ���߳�(��ͻ���)�µ�
 * ���������Ʒ��
 * ����һ�����߳���ά�����������Ϣ(�۸���Ϣ) 
 * ���뵽����ģ����N�ദ��
 */
public class ThreadSourceData2 {

	//ThreadLocal�������룬�ռ任ʱ�䣬�ڲ�����������ÿ���̶߳��������ռ�
	private static ThreadLocal<Integer> buyThreadPrice = new ThreadLocal<Integer>();
	
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			new Thread(new Runnable() {
				public void run() {
					//�ֽ��࣬��Դ��ͬһ����
					//�۸���Ϣ 
					int price = new Random().nextInt(10000);
					System.out.println("�����߳����ƣ�"+Thread.currentThread().getName()+" ,�۸�Ϊ�� "+price);
					buyThreadPrice.set(price);
					//����Aģ��������
					new A().getInfo();
					new B().getInfo();
				}
			}).start();
		}
	}
	
	//A ����ͻ����ջ���ַģ��
	static class A{
		public void getInfo(){
			int price = buyThreadPrice.get();
			System.out.println(Thread.currentThread().getName()+" ����Aģ�飬����ļ۸���Ϣ��: "+price);
		}
	}
	
	static class B{
		public void getInfo(){
			int price = buyThreadPrice.get();
			System.out.println(Thread.currentThread().getName()+" ����Bģ�飬����ļ۸���Ϣ��: "+price);
		}
	}
}
