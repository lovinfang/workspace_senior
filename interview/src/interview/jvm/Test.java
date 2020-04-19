package interview.jvm;

public class Test {

	public static void main(String[] args) {
		ClassLoader c = Test.class.getClassLoader();
		while(c !=null){
			System.out.println("===="+c);
			c = c.getParent();
		}
	}
}
