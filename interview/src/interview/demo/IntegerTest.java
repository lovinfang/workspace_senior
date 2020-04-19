package interview.demo;

import java.lang.reflect.Field;

public class IntegerTest {

	public static void main(String[] args) {
//		Integer a = 10;
//		Integer b = 20;
//		try {
//			method(a,b);
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		System.out.println("a="+a);
//		System.out.println("b="+b);
		
		
		int a = 10;
		int b = 20;
		try {
			swap(a,b);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		System.out.println("a="+a);
		System.out.println("b="+b);
	}


	private static void swap(Integer a, Integer b) 
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = Integer.class.getDeclaredField("value");
		field.setAccessible(true);
		int tmp = a.intValue();
		field.setInt(a, b.intValue());
		field.setInt(b, tmp);
	}


	private static void method(Integer a, Integer b) 
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = Integer.class.getDeclaredField("value");
		field.setAccessible(true);
		Integer tmp = new Integer(a.intValue());
//		int tmp = a.intValue();
//		System.out.println(a == tmp); //true
		field.set(a, b.intValue());  //set(Object obj, Object value)   
		// Integer.valueOf(b.intValue()).intValue()
		// b.intValue()  -> 20
		// Integer.valueOf(20) -> 20对应的地址
		// 20.intValue()  -> 20
		
		field.set(b, tmp);  //Integer.valueOf(tmp.intValue()).intValue()  由于tmp与a相同地址 所以还是取的a的地址的值 a此时为20
		
	}
}
