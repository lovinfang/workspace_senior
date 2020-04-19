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

public class NIOClient {
	
	//一个NIO的客户端
	SocketChannel client;
	//服务端的请求地址
	InetSocketAddress serverAdrress = new InetSocketAddress("localhost", 8080);
	
	Selector selector;
	
	
	ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
	ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
	
	public NIOClient() throws IOException{
		//先开路，先把告诉公路修起来，提供多路复用的通道
		client = SocketChannel.open();
		//设置成非阻塞模式
		client.configureBlocking(false);
		//与服务端建立连接
		client.connect(serverAdrress);
		
		//叫号系统要开始工作
		selector = Selector.open();
		//告诉叫号系统，我下一个感兴趣的事件是：与服务器建立连接成功之后，记得通知我
		client.register(selector, SelectionKey.OP_CONNECT);
		
		
	}
	
	/**
	 * 与服务器端建立连接以后的会话内容
	 * @throws IOException
	 */
	public void session() throws IOException{
		//先要判断是否已经建立连接,判断这个管道是不是打通了
		//告诉公路有没有封闭
		if(client.isConnectionPending()){
			//已经进入了告诉公路
			client.finishConnect();
			System.out.println("请在控制台登记姓名");
			//告诉叫号系统，下一步操作就是我要往服务器端发送消息
			client.register(selector, SelectionKey.OP_WRITE);
		}
		
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()){
			String name = scan.nextLine();
			if("".equals(name)){continue;}
			process(name);
		}
		
	}
	
	public void process(String name) throws IOException{
		boolean unFinish = true;
		while(unFinish){
			//判断一下，当前有没有客户来注册，有没有排队的，有没有取号的
			int i = selector.select();
			if(i == 0){ continue;}
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			
			while (iterator.hasNext()) {
				
				SelectionKey key = iterator.next();
				if(key.isWritable()){
					sendBuffer.clear();
					sendBuffer.put(name.getBytes());
					sendBuffer.flip();
					
					client.write(sendBuffer);
					
					client.register(selector, SelectionKey.OP_READ);
					
				}else if(key.isReadable()){
					receiveBuffer.clear();
					int len = client.read(receiveBuffer);
					if(len > 0){
						receiveBuffer.flip();
						System.out.println("获取到服务端反馈的信息：" + new String(receiveBuffer.array(),0,len));
						client.register(selector, SelectionKey.OP_WRITE);
						unFinish = false;
					}
					
					
				}
				
			}
			
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		new NIOClient().session();
	}
	
	
}
