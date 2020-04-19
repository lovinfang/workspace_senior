package com.netty.chat.server.handler;

import com.netty.chat.protocol.IMEncoder;
import com.netty.chat.protocol.IMMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理自定义协议的逻辑
 * @author Administrator
 *
 */
public class ImpHandler extends SimpleChannelInboundHandler<IMMessage>{

	private IMEncoder encoder = new IMEncoder();
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IMMessage msg)throws Exception {
		System.out.println(encoder.encode(msg));
	}

}
