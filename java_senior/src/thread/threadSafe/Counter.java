package thread.threadSafe;

public class Counter {
	
	private int count = 0;

	public int getCount() {
		return count;
	}
	
	/**
	 * ͬ������ʵ���̰߳�ȫ 
	 */
	public synchronized void addCount(){
		count++;
	}

}
