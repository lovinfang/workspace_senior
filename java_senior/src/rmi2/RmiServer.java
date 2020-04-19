package rmi2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class RmiServer {

	public static void main(String[] args) throws RemoteException,MalformedURLException{
		AddServerImpl add = new AddServerImpl();
		Naming.rebind("addNumbers", add);
	}
}
