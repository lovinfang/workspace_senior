package com.dubbo.demo.provider;

import com.dubbo.demo.api.IHelloWorldService;

public class HelloWorldServiceImpl2 implements IHelloWorldService{

	public String sayHello(String name) {
		return "Œ“ «Hello World 2";
	}

	public String sayHelloDemoTwo() {
		return null;
	}

}
