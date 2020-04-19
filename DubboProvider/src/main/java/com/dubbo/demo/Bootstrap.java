package com.dubbo.demo;

import java.io.IOException;

import com.alibaba.dubbo.container.Main;

/**
 * 启动服务
 * 加载的配置文件为src\main\resources\META-INF\spring\provider.xml
 * @author Administrator
 *
 */
public class Bootstrap {

	/**
	 * 一开始百思不得其解，注册中心用zookeeper，spring的配置文件里配置的注册中心类似如下：
	 * protocol=”zookeeper” address=”10.0.50.150:2181″
	 * 看这个配置也没有问题。最后发现，应用的classpath里还有一份dubbo.properties文件，该文件的dubbo.register.address值就是spring配置里的address值。值得注意的是，dubbo.properties文件里配置的注册中心地址一定是要带协议的，比如应该配置为:zookeeper://10.0.50.150:2181，而不是10.0.50.150:2181。
     * dubbo.properties配置为10.0.50.150:2181会带来问题，因为没有配置注册中心协议，所以默认就是dubbo，这样这个地址其实变为了:dubbo://10.0.50.150:2181,dubbo会认为注册中心地址是一个dubbo服务，但其实10.0.50.150:2181运行的是一个zookeeper服务，根本不是dubbo服务，内部报错，然后注册时应用等待超时。
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Main.main(args);
		System.out.println("我是服务器1启动成功");
		System.in.read();
	}
}
