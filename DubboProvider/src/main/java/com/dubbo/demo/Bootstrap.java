package com.dubbo.demo;

import java.io.IOException;

import com.alibaba.dubbo.container.Main;

/**
 * ��������
 * ���ص������ļ�Ϊsrc\main\resources\META-INF\spring\provider.xml
 * @author Administrator
 *
 */
public class Bootstrap {

	/**
	 * һ��ʼ��˼������⣬ע��������zookeeper��spring�������ļ������õ�ע�������������£�
	 * protocol=��zookeeper�� address=��10.0.50.150:2181��
	 * ���������Ҳû�����⡣����֣�Ӧ�õ�classpath�ﻹ��һ��dubbo.properties�ļ������ļ���dubbo.register.addressֵ����spring�������addressֵ��ֵ��ע����ǣ�dubbo.properties�ļ������õ�ע�����ĵ�ַһ����Ҫ��Э��ģ�����Ӧ������Ϊ:zookeeper://10.0.50.150:2181��������10.0.50.150:2181��
     * dubbo.properties����Ϊ10.0.50.150:2181��������⣬��Ϊû������ע������Э�飬����Ĭ�Ͼ���dubbo�����������ַ��ʵ��Ϊ��:dubbo://10.0.50.150:2181,dubbo����Ϊע�����ĵ�ַ��һ��dubbo���񣬵���ʵ10.0.50.150:2181���е���һ��zookeeper���񣬸�������dubbo�����ڲ�����Ȼ��ע��ʱӦ�õȴ���ʱ��
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Main.main(args);
		System.out.println("���Ƿ�����1�����ɹ�");
		System.in.read();
	}
}
