package com.dubbo.demo;

import java.io.IOException;

import com.alibaba.dubbo.container.Main;

public class Bootstrap {

	public static void main(String[] args) throws IOException {
		Main.main(args);
		
		System.in.read();
	}
}
