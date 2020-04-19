package interview.jvm.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * �Զ��������  ���������ָ����·��
 * ����Ʋ��ί�л���
 * ���ǵ�Ŀ���ǣ�������˳�����ֽ��뻹ԭ ֮�� ����ʵ��.����
 * @author chenjg
 *
 */
public class MyClassLoader extends ClassLoader{
    //��Ա���� class·��
	private String classDir;
	public MyClassLoader(){
		
	}
	public MyClassLoader(String classDir){
		this.classDir = classDir;
	}
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// ������ִ�и���� jdk�ṩAppClassLoader ��ִ�����findClass
		//return super.findClass(name);
		//·����������  ·��
		String classFileName=classDir+ "\\" +name.substring(name.lastIndexOf("\\")+1)+".class";
		 try {
			//�ڶ���һ��������
			FileInputStream fis = new FileInputStream(classFileName);
			//�ֽ����������
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			//����
			cypher(fis,bos);
			fis.close();
			//��һ��diy�ķ�ʽ
			System.out.println("��������������Զ��������MyClassLoader �������д��findClass");
			byte[] bytes =bos.toByteArray();
			//Class ���ʵ��
			return defineClass(null,bytes,0,bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public static void main(String[] args) throws Exception  {
		  //ָ��һ������  ԴĿ¼
		String srcPath =args[0];
		 //ָ��һ������Ŀ��Ŀ¼
		String desDir =args[1];
		//�ļ��������������ԴĿ¼��ȡ�ֽ���
	     FileInputStream fis = new FileInputStream(srcPath);
	     //ԭĿ���е��ļ������ó���
	   String destFileName  =srcPath.substring(srcPath.lastIndexOf("\\")+1);
	   String destPath = desDir+"\\"+destFileName;
	   FileOutputStream fos = new FileOutputStream(destPath);
	   cypher(fis,fos);
	   fis.close();
	   fos.close();

	}
	//�����㷨 �����ܷ�
	/** 
	 *  0000 1101
	 *  1111 1111  0xff
	 *  1111 0010      ���ܵ���������
	 *  1111 1111  0xff
	 *  0000 1101
	 * 
	 */
	/**
	 * ������InputStream �ֽ���
	 * @param ips
	 */
	private static void cypher(InputStream ips,OutputStream ops) throws Exception{
		//����һ����ʼ����
		   int b=-1;
		   while((b=ips.read()) !=-1){
			   ops.write(b ^ 0xff);
		   }
		   
	}
	

}
