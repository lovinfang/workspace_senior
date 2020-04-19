package com.netty.chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,TextWebSocketFrame msg) throws Exception {
		System.out.println("服务器端接收到客户端的消息："+new TextWebSocketFrame(msg.text()));
		ctx.writeAndFlush(new TextWebSocketFrame(msg.text()));
	}

}
