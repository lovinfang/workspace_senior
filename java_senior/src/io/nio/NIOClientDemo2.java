package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class NIOClientDemo2 {
	
	//һ��NIO�Ŀͻ���
	SocketChannel client;
	//����˵������ַ
	InetSocketAddress serverAdrress = new InetSocketAddress("localhost", 8080);
	
	Selector selector;
	
	
	ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
	ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
	
	public NIOClientDemo2() throws IOException{
		//�ȿ�·���ȰѸ��߹�·���������ṩ��·���õ�ͨ��
		client = SocketChannel.open();
		//���óɷ�����ģʽ
		client.configureBlocking(false);
		//�����˽�������
		client.connect(serverAdrress);
		
		//�к�ϵͳҪ��ʼ����
		selector = Selector.open();
		//���߽к�ϵͳ������һ������Ȥ���¼��ǣ���������������ӳɹ�֮�󣬼ǵ�֪ͨ��
		client.register(selector, SelectionKey.OP_CONNECT);
		
		
	}
	
	/**
	 * ��������˽��������Ժ�ĻỰ����
	 * @throws IOException
	 */
	public void session() throws IOException{
		//��Ҫ�ж��Ƿ��Ѿ���������,�ж�����ܵ��ǲ��Ǵ�ͨ��
		//���߹�·��û�з��
		if(client.isConnectionPending()){
			//�Ѿ������˸��߹�·
			client.finishConnect();
			System.out.println("���ڿ���̨�Ǽ�����");
			//���߽к�ϵͳ����һ������������Ҫ���������˷�����Ϣ
			client.register(selector, SelectionKey.OP_WRITE);
		}
		
		String uuid = UUID.randomUUID().toString();
	}
	
	/**
	 * ������ѯ��ҵ���߼�
	 * �ж�ÿһ���¼���Ӧ��ʱ�򣬸øɵ�ɶ
	 * @param name
	 * @throws IOException
	 */
	public void process(String name) throws IOException{
		boolean unFinish = true;
		//��ʼ��ѯ
		while(unFinish){
			//���������λ�ã�ֻҪ�и���Ȥ���¼���������ô����ͻ�����ִ��
			int i = selector.select();
			if(i == 0){ continue;}
			Set<SelectionKey> keys = selector.selectedKeys(); //�����¼��ļ���
			Iterator<SelectionKey> iterator = keys.iterator();
			
			while (iterator.hasNext()) {
				//�õ�һ�������¼�
				SelectionKey key = iterator.next();
				if(key.isWritable()){
					//�������Ӧ�¼���ɶ������ǿ���д��
					sendBuffer.clear(); //����
					sendBuffer.put(name.getBytes());
					sendBuffer.flip(); //����������������ӻ�����д��ʱ����ô���ʱ��Ͳ�Ҫ���������ж������ˣ�������������Ĳ�����
					client.write(sendBuffer);
					client.register(selector, SelectionKey.OP_READ);
					sendBuffer.clear(); 
				}else if(key.isReadable()){
					//�ɶ��¼�
					receiveBuffer.clear(); //����
					int len = client.read(receiveBuffer);
					if(len > 0){
						receiveBuffer.flip(); //������������������������Ĳ�����
						System.out.println("��ȡ������˷�������Ϣ��" + new String(receiveBuffer.array(),0,len));
						client.register(selector, SelectionKey.OP_WRITE);
						sendBuffer.clear(); 
						unFinish = false;
					}
				}
			}
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		new NIOClientDemo2().session();
	}
	
	
}
