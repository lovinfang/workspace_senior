package interview.jvm.classLoader;

public class ClassLoaderTest {

	public static void main(String[] args) {
		//��������Ҫ������ļ����ص�jvm  ��������Ҫ�õ���������ļ����ۼ���������
		System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getName());
		
	/*	ClassLoader loader =ClassLoaderTest.class.getClassLoader();
		while(loader!=null){
			System.out.println(loader.getClass().getName());
			loader=loader.getParent();
		}
		//��������ʵ������C++��д��Ƕ�뵽����� �������һ��������  java�������ò����İ�
		System.out.println(loader);//bootStrap
	*/
		System.out.println(new ClassAttachment().toString());
		
	}
}
