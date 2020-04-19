package com.dubbo.demo.provider;

import com.dubbo.demo.api.IHelloWorldService;

public class HelloWorldServiceImpl implements IHelloWorldService{

	public String sayHello(String name) {
		return null;
	}

	public String sayHelloDemoTwo() {
		return "Hello dubbo DubboProvider2";
	}

}
