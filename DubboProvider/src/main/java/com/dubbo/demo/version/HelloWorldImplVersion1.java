package com.dubbo.demo.version;

import com.dubbo.demo.api.IHelloWorldService;

/**
 * ����Dubbo�ķ���
 * @author Administrator
 *
 */
public class HelloWorldImplVersion1 implements IHelloWorldService{

	public String sayHello(String name) {
		return null;
	}

	public String sayHelloDemoTwo() {
		return "�ҵİ汾��Ϊ��1.0.0";
	}

}
