package com.dubbo.demo.provider;

import com.dubbo.demo.api.IHelloWorldService;

/**
 * ʵ������֮ǰ�����api�ӿ�
 * @author Administrator
 *
 */
public class HelloWorldServiceImpl implements IHelloWorldService{

	public String sayHello(String name) {
		return "Hello World:"+name;
	}

	public String sayHelloDemoTwo() {
		return "Hello dubbo DubboProvider";
	}

}
