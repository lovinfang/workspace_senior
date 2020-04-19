package com.dubbo.demo.version;

import com.dubbo.demo.api.IHelloWorldService;

public class HelloWorldImplVersion2 implements IHelloWorldService{

	public String sayHello(String name) {
		return null;
	}

	public String sayHelloDemoTwo() {
		return "我的版本号为:2.0.0";
	}

}
