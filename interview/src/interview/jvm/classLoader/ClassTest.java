package interview.jvm.classLoader;

import java.util.Date;

public class ClassTest {

	public static void main(String[] args) throws Exception{
		//С֪ʶ
		Class clazz=new MyClassLoader("cypherClass").loadClass("ClassAttachment");
		//��������Ϊ����ֽ��뱻������ ClassAttachment���� ��û�취���ܷ��ص�ʵ��
		Date ca =(Date)clazz.newInstance();
		System.out.println(ca.toString());
		

	}

}
