package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


//UnicastRemoteObject���ڵ�����Զ�̶���ͻ�����Զ�̶���ͨ�ŵĴ���� 
public class ServiceImpl extends UnicastRemoteObject implements IService {
	
	private String name;
	
	public ServiceImpl(String name)throws RemoteException{
		this.name = name;
	}

	@Override
	public String service(String content) throws RemoteException {
		return "server >> " + content;
	}

}
