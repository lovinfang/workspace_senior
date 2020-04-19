package com.netty.chat.protocol;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * IM 协议编码器，服务端往客户端输出，要编码
 * @author Administrator
 *
 */
public class IMEncoder extends MessageToByteEncoder<IMMessage>{

	//实现序列化的一个过程
	@Override
	protected void encode(ChannelHandlerContext ctx,IMMessage msg, ByteBuf out) throws Exception {
		//序列化的过程
		out.writeBytes(new MessagePack().write(msg));
	}
	
	/**
	 * 编码，把IMMessage对象解析成我们的IMP协议字符串，方便于输出到客户端
	 * @param message
	 * @return
	 */
	public String encode(IMMessage msg){
		if(null == msg){
			return "";
		}
		String prex = "["+msg.getCmd()+"]["+msg.getTime()+"]";
		if(IMP.LOGIN.getName().equals(msg.getCmd())
				|| IMP.CHAT.getName().equals(msg.getCmd())
				|| IMP.FLOWER.getName().equals(msg.getCmd())
				|| IMP.LOGOUT.getName().equals(msg.getCmd())){
			prex +=("["+msg.getSender()+"]");
		}else if(IMP.SYSTEM.getName().equals(msg.getCmd())){
			prex +=("["+msg.getOnline()+"]");
		}
		if((null == msg.getContent() || "".equals(msg.getContent().trim()))){
			prex += (" - "+msg.getContent());
		}
		return prex;
	}
}
