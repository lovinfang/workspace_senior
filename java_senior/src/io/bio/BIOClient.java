package io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

public class BIOClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket client = new Socket("localhost", 8080);
		//�ͻ�����߾���д����
		OutputStream os = client.getOutputStream();
		os.write("������".getBytes());
		os.close();
		client.close();
		
		/**
		 * ʹ��javaԭ��api���и߲���
		 */
		final CountDownLatch latch = new CountDownLatch(100);
		
		for(int i=0;i<100;i++){
			new Thread() {
				@Override
				public void run() {
					try{
						latch.await();
						Socket client = new Socket("localhost", 8080);
						//�ͻ�����߾���д����
						OutputStream os = client.getOutputStream();
						os.write("������".getBytes());
						os.close();
						client.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}.start();
			latch.countDown();
		}
		
		
	}
	
}
