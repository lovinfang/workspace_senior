package thread.threadDataShare;

public class ThreadTest2 {

	public static void main(String[] args) {
		//����Դ���������
		final BusinessDemo demo = new BusinessDemo();
		//���̲߳���Ҫ��:��50�ֻ���ÿ���ֻ�ѭ����30��
		new Thread(new Runnable() {
			public void run() {
				for(int i=1;i<=50;i++){
					//���߳�Ҫ��30��
					//������Դ��
					demo.sonBusiness(i);
				}
			}
		}).start();
		
		for(int i=1;i<=50;i++){
			//���߳̽���һ�Σ�40��
			//������Դ��
			demo.mainBusiness(i);
		}
	}
}
