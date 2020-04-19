package com.dubbo.demo.version;

import com.dubbo.demo.api.IHelloWorldService;

/**
 * 测试Dubbo的分组
 * @author Administrator
 *
 */
public class HelloWorldImplVersion1 implements IHelloWorldService{

	public String sayHello(String name) {
		return null;
	}

	public String sayHelloDemoTwo() {
		return "我的版本号为：1.0.0";
	}

}
