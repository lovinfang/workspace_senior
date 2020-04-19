package thread.threadSafe;

public class Counter {
	
	private int count = 0;

	public int getCount() {
		return count;
	}
	
	/**
	 * 同步锁，实现线程安全 
	 */
	public synchronized void addCount(){
		count++;
	}

}
