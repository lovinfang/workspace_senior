package thread.threadDataShare;

public class BusinessDemo {

	//�ڲ���һ����������  ����
	private boolean isShowSonThread=true;
	//���߳��Ƚ���
	public synchronized void sonBusiness(int i){
		while(!isShowSonThread){
			try{
				//���߳̾����������
				this.wait();
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
		//
		for(int j=1;j<=30;j++){
			System.out.println("============���߳����еڣ� "+j+"�֣��� "+j+" ��");
		}
		
		isShowSonThread = false;
		//֪ͨ���߳�
		this.notify();
	}
	
	//���߳�ҵ��ģ�飬synchronized��ʽ���������� ��װ�����棬���ÿ��ƣ�Ч�ʷǳ�����
	// synchronized ʱ�任�̰߳�ȫ(�߳̿ռ�)
	public synchronized void mainBusiness(int i){
		while(isShowSonThread){
			try{
				this.wait();
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
		//���߳�������ҵ��
		for(int j=1;j<=40;j++){
			System.out.println("���߳����е�: "+i+"�֣��ڣ�"+j+" ��");
		}
		
		isShowSonThread = true;
		//֪ͨ���߳�
		this.notify();
	}
}
