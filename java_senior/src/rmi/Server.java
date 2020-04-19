package rmi;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;


public class Server {

	public static void main(String[] args) {
		try {
			IService service = new ServiceImpl("service");
			Context namingContext = new InitialContext();
			namingContext.rebind("rmi://localhost/service", service);
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("��������������ע����1��Զ�̷������");
	}
}
