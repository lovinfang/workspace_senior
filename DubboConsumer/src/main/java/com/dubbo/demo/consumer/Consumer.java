package com.dubbo.demo.consumer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.RpcContext;
import com.dubbo.demo.api.IHelloWorldService;

public class Consumer {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/**
		 * 测试两个 DubboProvider 以及 DubboProvider2工程中Bootstrap.java启动的两个服务，可以查看调用的具体是哪一个
		 * 因为两个服务都实现的是同一个接口
		 */
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer3.xml"});
		
		/**
		 * 测试dubbo负载均衡
		 */
		/*for(int i=0;i<10;i++){
			IHelloWorldService helloWorldService = (IHelloWorldService) context.getBean("demoService");
			System.out.println(helloWorldService.sayHelloDemoTwo());
		}*/
		
		/**
		 * 测试dubbo分组
		 */
		/*IHelloWorldService helloWorldService = (IHelloWorldService) context.getBean("demoService");
		System.out.println(helloWorldService.sayHelloDemoTwo());*/
		
		/**
		 * 测试dubbo异步
		 */
		IHelloWorldService helloWorldService = (IHelloWorldService) context.getBean("demoService");
		System.out.println("同步返回结果："+helloWorldService.sayHelloDemoTwo());
		Future<String> str = RpcContext.getContext().getFuture();
		System.out.println("异步返回结果:"+str.get());
		
	}
}
