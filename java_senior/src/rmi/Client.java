package rmi;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Client {

	public static void main(String[] args) {
		String url = "rmi://localhost/";
		try {
			Context namingContext = new InitialContext();
			IService service = (IService) namingContext.lookup(url+"service");
			Class stubClass = service.getClass();
			System.out.println(service+" �� " + stubClass.getName()+" ��ʵ��!");
			Class[] interfaces = stubClass.getInterfaces();
			for(Class c : interfaces){
				System.out.println("�����ʵ���� "+ c.getName()+" �ӿ�!");
			}
			System.out.println(service.service("����!"));
		} catch (NamingException e) {
			e.printStackTrace();
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}
}
