package com.netty.demo.nettyDemo.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String>{


	//表示我们客户端跟服务已经连接成功，并且成功创建工作流
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//获得上下文，然后获得渠道，并向服务端写入数据
		ctx.channel().writeAndFlush("你好");
	}
	
	@Override
	protected void channelRead0(
			ChannelHandlerContext paramChannelHandlerContext, String paramI)
			throws Exception {
		
	}
	
	

}
