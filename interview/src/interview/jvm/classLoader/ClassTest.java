package interview.jvm.classLoader;

import java.util.Date;

public class ClassTest {

	public static void main(String[] args) throws Exception{
		//小知识
		Class clazz=new MyClassLoader("cypherClass").loadClass("ClassAttachment");
		//这里是因为这个字节码被打乱了 ClassAttachment类型 是没办法接受返回的实例
		Date ca =(Date)clazz.newInstance();
		System.out.println(ca.toString());
		

	}

}
