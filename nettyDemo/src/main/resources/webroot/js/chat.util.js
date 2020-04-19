$(document).ready(function(){
	//alert("jquery生效");
	if(!window.WebSocket){
		window.WebSocket = window.MozWebSocket;
	}
	
	if(window.WebSocket){
		// alert("您的浏览器支持WebSocket");
		var socket = new WebSocket("ws://localhost:8080/im"); //发送WebSocket请求 ws:WebSocket简写
		socket.onmessage = function(e){
			console.log("获取服务器发来的消息:"+e.data);
		};
		socket.onopen = function(e){
			console.log("与服务器建立连接");
			socket.send("您好");
		};
		socket.onclose = function(e){
			console.log("服务器关闭");
		}
	}else{
		alert("您的浏览器不支持WebSocket");
	}
});