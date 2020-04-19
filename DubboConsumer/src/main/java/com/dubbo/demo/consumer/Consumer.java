package com.dubbo.demo.consumer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.RpcContext;
import com.dubbo.demo.api.IHelloWorldService;

public class Consumer {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/**
		 * �������� DubboProvider �Լ� DubboProvider2������Bootstrap.java�������������񣬿��Բ鿴���õľ�������һ��
		 * ��Ϊ��������ʵ�ֵ���ͬһ���ӿ�
		 */
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer3.xml"});
		
		/**
		 * ����dubbo���ؾ���
		 */
		/*for(int i=0;i<10;i++){
			IHelloWorldService helloWorldService = (IHelloWorldService) context.getBean("demoService");
			System.out.println(helloWorldService.sayHelloDemoTwo());
		}*/
		
		/**
		 * ����dubbo����
		 */
		/*IHelloWorldService helloWorldService = (IHelloWorldService) context.getBean("demoService");
		System.out.println(helloWorldService.sayHelloDemoTwo());*/
		
		/**
		 * ����dubbo�첽
		 */
		IHelloWorldService helloWorldService = (IHelloWorldService) context.getBean("demoService");
		System.out.println("ͬ�����ؽ����"+helloWorldService.sayHelloDemoTwo());
		Future<String> str = RpcContext.getContext().getFuture();
		System.out.println("�첽���ؽ��:"+str.get());
		
	}
}
