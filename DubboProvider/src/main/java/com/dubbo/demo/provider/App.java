package com.dubbo.demo.provider;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) throws IOException {
		//��ʾ���Э��
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
		
		//��ע������
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider2.xml"});
		context.start();
		System.in.read();
	}
}
