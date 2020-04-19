package com.netty.demo.nettyDemo;

import com.netty.demo.nettyDemo.handler.ClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {

	//连接到服务器
	public void connect(String host,int port){
		//只需要单线程就ok，不需要使用主从模式
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			Bootstrap b = new Bootstrap();
			b.group(workGroup)    //客户端单线程
			.channel(NioSocketChannel.class)  //客户端管道
			.option(ChannelOption.SO_KEEPALIVE, true)  //与服务器端保持长连接
			.handler(new ChannelInitializer<SocketChannel>() {
	
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast(new StringEncoder());
					sc.pipeline().addLast(new ClientHandler());
				}
			});
		
			ChannelFuture f = b.connect(host, port).sync();  //同步
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new NettyClient().connect("127.0.0.1", 8080);
	}
}
