package thread.threadDataShare;

import java.util.Random;

/**
 * ���߳�(��ͻ���)�µ�
 * ���������Ʒ��
 * ����һ�����߳���ά�����������Ϣ(�۸���Ϣ) 
 * ���뵽����ģ����N�ദ��
 */
public class ThreadSourceData {

	private static int price;
	
	public static void main(String[] args) {
		for(int i=0;i<3;i++){
			new Thread(new Runnable() {
				public void run() {
					//�ֽ��࣬��Դ��ͬһ����
					synchronized (ThreadSourceData.class) {
						//�۸���Ϣ 
						price = new Random().nextInt(10000);
						System.out.println("�����߳����ƣ�"+Thread.currentThread().getName()+" ,�۸�Ϊ�� "+price);
						//����Aģ��������
						new A().getInfo();
						new B().getInfo();
					}
				}
			}).start();
		}
	}
	
	//A ����ͻ����ջ���ַģ��
	static class A{
		public void getInfo(){
			System.out.println(Thread.currentThread().getName()+" ����Aģ�飬����ļ۸���Ϣ��: "+price);
		}
	}
	
	static class B{
		public void getInfo(){
			System.out.println(Thread.currentThread().getName()+" ����Bģ�飬����ļ۸���Ϣ��: "+price);
		}
	}
}
