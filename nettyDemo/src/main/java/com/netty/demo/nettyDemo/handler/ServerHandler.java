package com.netty.demo.nettyDemo.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<String>{

//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
//		System.out.println("接受到客户端发来的信息："+new String((byte []) msg));
//	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端可用");
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端加入");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext paramChannelHandlerContext, String msg)
			throws Exception {
		System.out.println("接受到客户端发来的信息："+new String(msg));
	}
	
	
}
