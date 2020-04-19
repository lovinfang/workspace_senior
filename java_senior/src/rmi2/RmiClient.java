package rmi2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {

	public static void main(String[] args) throws RemoteException,MalformedURLException,NotBoundException{
		String url = "addNumbers";
		AddServer add = (AddServer) Naming.lookup(url);
		int result = add.AddNumbers(10, 5);
		System.out.println(result);
	}
}
