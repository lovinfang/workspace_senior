package com.dubbo.demo.group;

import com.dubbo.demo.api.IHelloWorldService;

/**
 * 测试Dubbo的分组
 * @author Administrator
 *
 */
public class ChinaHelloWorldImpl implements IHelloWorldService{

	public String sayHello(String name) {
		return null;
	}

	public String sayHelloDemoTwo() {
		return "我是中文的:你好";
	}

}
