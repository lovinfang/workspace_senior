package interview.jvm.classLoader;

public class ClassLoaderTest {

	public static void main(String[] args) {
		//我们现在要将这个文件加载到jvm  而且我们要得到加载这个文件的累加器的名字
		System.out.println(ClassLoaderTest.class.getClassLoader().getClass().getName());
		
	/*	ClassLoader loader =ClassLoaderTest.class.getClassLoader();
		while(loader!=null){
			System.out.println(loader.getClass().getName());
			loader=loader.getParent();
		}
		//第三个其实就是由C++编写的嵌入到虚拟机 随虚拟机一起启动的  java方法是拿不到的啊
		System.out.println(loader);//bootStrap
	*/
		System.out.println(new ClassAttachment().toString());
		
	}
}
