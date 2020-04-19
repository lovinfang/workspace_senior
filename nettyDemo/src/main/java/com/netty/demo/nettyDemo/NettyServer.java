package com.netty.demo.nettyDemo;

import com.netty.demo.nettyDemo.handler.ServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {

	private int port = 8080;
	
	public NettyServer(){
		//Netty的工作机制 boss线程
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//Work线程
		EventLoopGroup workerGroup = new NioEventLoopGroup();
			
		try {
			//相当于ServerSocketChannel
			ServerBootstrap sbs = new ServerBootstrap();
			
			//多线程NIO 主从模式   option：配置 1024 最大的子线程为1024
			sbs.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChannelInitializer<SocketChannel>() { //每次来都new一个工作流
	
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					//在这里写我们的工作流，串行链表，来实现我们的业务逻辑
					System.out.println(sc.remoteAddress()); //获取客户端管道的ip地址
					sc.pipeline().addLast(new StringDecoder()); //加入解码器
					//接受客户端发来的信息
					sc.pipeline().addLast(new ServerHandler());
				}
				
			});
			
			//绑定端口，采用同步方式
			ChannelFuture f = sbs.bind(this.port).sync();
			System.out.println("服务已启动，监听端口是："+this.port);
			//管道全部关闭
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//释放线程
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();  
		}
	}
	
	public NettyServer(int port){
		this.port = port;
	}
	/**
	 * 启动监听
	 */
	public void listener(){
		
	}
	
	public static void main(String[] args) {
		new NettyServer().listener();
	}
}
