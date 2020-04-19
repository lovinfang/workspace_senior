package com.dubbo.demo.provider;

import com.dubbo.demo.api.IHelloWorldService;

/**
 * 实现我们之前定义的api接口
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
