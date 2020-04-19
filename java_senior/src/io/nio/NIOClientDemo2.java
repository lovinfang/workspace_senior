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
	
	//一个NIO的客户端
	SocketChannel client;
	//服务端的请求地址
	InetSocketAddress serverAdrress = new InetSocketAddress("localhost", 8080);
	
	Selector selector;
	
	
	ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
	ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
	
	public NIOClientDemo2() throws IOException{
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
		
		String uuid = UUID.randomUUID().toString();
	}
	
	/**
	 * 处理轮询的业务逻辑
	 * 判断每一个事件响应的时候，该干点啥
	 * @param name
	 * @throws IOException
	 */
	public void process(String name) throws IOException{
		boolean unFinish = true;
		//开始轮询
		while(unFinish){
			//阻塞在这个位置，只要有感兴趣的事件发生，那么程序就会往下执行
			int i = selector.select();
			if(i == 0){ continue;}
			Set<SelectionKey> keys = selector.selectedKeys(); //触发事件的集合
			Iterator<SelectionKey> iterator = keys.iterator();
			
			while (iterator.hasNext()) {
				//拿到一个触发事件
				SelectionKey key = iterator.next();
				if(key.isWritable()){
					//看这个响应事件是啥，如果是可以写的
					sendBuffer.clear(); //解锁
					sendBuffer.put(name.getBytes());
					sendBuffer.flip(); //锁定缓冲区，如果从缓冲区写的时候，那么这个时候就不要往缓冲区中读数据了，不允许再做别的操作了
					client.write(sendBuffer);
					client.register(selector, SelectionKey.OP_READ);
					sendBuffer.clear(); 
				}else if(key.isReadable()){
					//可读事件
					receiveBuffer.clear(); //解锁
					int len = client.read(receiveBuffer);
					if(len > 0){
						receiveBuffer.flip(); //锁定缓冲区，不允许再做别的操作了
						System.out.println("获取到服务端反馈的信息：" + new String(receiveBuffer.array(),0,len));
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
