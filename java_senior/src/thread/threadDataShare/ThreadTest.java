package thread.threadDataShare;

public class ThreadTest {

	public static void main(String[] args) {
		//���̲߳���Ҫ����50�ֻ���ÿ���ֻ�ѭ����30��
		new Thread(new Runnable(){
			public void run() {
				for(int i=1;i<=50;i++){
					//���߳�Ҫ���ҵ��
					for(int j=1;j<=30;j++){
						System.out.println("================"+"���߳����е�: "+i+" �֣���: "+j+" ��");
					}
				}
			}
			
		}).start();
		
		//50�ֻأ����̲߳���
		for(int i=1;i<=50;i++){
			//���߳�Ҫ���ҵ��
			for(int j=1;j<=40;j++){
				System.out.println("���߳����е�: "+i+"�֣��ڣ�"+j+" ��");
			}
		}
	}
}
