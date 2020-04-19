package com.dubbo.demo.consumer;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dubbo.demo.api.IHelloWorldService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	//���ö��Э���
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        //����ע�ᵽĳһ��ע�����ĵķ���
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer2.xml"});
        IHelloWorldService iHelloWorldService =  (IHelloWorldService) context.getBean("demoService2");
        System.out.println(iHelloWorldService.sayHello("lovinfang"));
        System.in.read();
    }
}
