package interview.jvm.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 自定类加载器  类加载我们指定的路径
 * 我们撇开委托机制
 * 我们的目的是：将打乱顺数的字节码还原 之后 调用实例.方法
 * @author chenjg
 *
 */
public class MyClassLoader extends ClassLoader{
    //成员变量 class路径
	private String classDir;
	public MyClassLoader(){
		
	}
	public MyClassLoader(String classDir){
		this.classDir = classDir;
	}
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// 这里是执行父类的 jdk提供AppClassLoader 不执行这个findClass
		//return super.findClass(name);
		//路径加上名字  路径
		String classFileName=classDir+ "\\" +name.substring(name.lastIndexOf("\\")+1)+".class";
		 try {
			//在定义一个输入流
			FileInputStream fis = new FileInputStream(classFileName);
			//字节数组输出流
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			//解密
			cypher(fis,bos);
			fis.close();
			//顶一个diy的方式
			System.out.println("这里进入了我们自定类加载器MyClassLoader 里面的重写的findClass");
			byte[] bytes =bos.toByteArray();
			//Class 类的实例
			return defineClass(null,bytes,0,bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public static void main(String[] args) throws Exception  {
		  //指定一个参数  源目录
		String srcPath =args[0];
		 //指定一个参数目标目录
		String desDir =args[1];
		//文件输入流兑入这个源目录读取字节码
	     FileInputStream fis = new FileInputStream(srcPath);
	     //原目标中的文件名称拿出来
	   String destFileName  =srcPath.substring(srcPath.lastIndexOf("\\")+1);
	   String destPath = desDir+"\\"+destFileName;
	   FileOutputStream fos = new FileOutputStream(destPath);
	   cypher(fis,fos);
	   fis.close();
	   fos.close();

	}
	//加密算法 异或加密法
	/** 
	 *  0000 1101
	 *  1111 1111  0xff
	 *  1111 0010      加密的里面内容
	 *  1111 1111  0xff
	 *  0000 1101
	 * 
	 */
	/**
	 * 参数：InputStream 字节码
	 * @param ips
	 */
	private static void cypher(InputStream ips,OutputStream ops) throws Exception{
		//定义一个初始长度
		   int b=-1;
		   while((b=ips.read()) !=-1){
			   ops.write(b ^ 0xff);
		   }
		   
	}
	

}
