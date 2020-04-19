package com.netty.chat.server.handler;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedNioFile;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

	//class路径
	private URL basePath = HttpHandler.class.getProtectionDomain().getCodeSource().getLocation();
	//静态文件的存放目录
	private final String webroot = "webroot";
	
	/**
	 * 从磁盘中找到一个静态资源
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	private File getResource(String uri) throws Exception{
		String path = basePath.toURI() + webroot + "/" + uri;
		path = !path.contains("file:") ? path : path.substring(5);
		System.out.println("path="+path);
		path = path.replace("//", "/");
		return new File(path);
	}
			
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		//一个URI对应一个资源，只能解析静态资源
		String uri = req.getUri();
		System.out.println("获取url："+uri);
		String resource = uri.equals("/")?"chat.html":uri;
		RandomAccessFile file = null;
		try{
			file = new RandomAccessFile(getResource(resource), "r");
		}catch(Exception e){
			//继续下一次请求，服务端捕获异常并进行处理
			ctx.fireChannelRead(req.retain());
			return;
		}
		
		HttpResponse response = new DefaultHttpResponse(req.getProtocolVersion(), HttpResponseStatus.OK);
		String contextType = "text/html";
		if(uri.endsWith(".css")){
			contextType="text/css";
		}else if(uri.endsWith(".js")){
			contextType="text/javascript";
		}else if(uri.toLowerCase().matches("(jpg|png|gif|ico)$")){
			String ext = uri.substring(uri.lastIndexOf("."));
			contextType = "image/"+ext;
		}
		
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE,contextType+" charset=utf-8"); //context 头
		
		boolean isKeepAlive = HttpHeaders.isKeepAlive(req); //是否长连接
		//如果是长连接
		if(isKeepAlive){
			response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length()); //返回长度
			response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE); //长连接
		}
		
		//如果不是长连接，然后文件也全部输出完毕了，那么就关闭连接
		ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		if(!isKeepAlive){
			future.addListener(ChannelFutureListener.CLOSE);
		}
		
		ctx.write(response);
		ctx.writeAndFlush(new ChunkedNioFile(file.getChannel()));
		file.close();
	}

}
