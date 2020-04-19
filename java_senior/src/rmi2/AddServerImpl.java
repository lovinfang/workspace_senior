package rmi2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddServerImpl extends UnicastRemoteObject implements AddServer {

	protected AddServerImpl() throws RemoteException {
		super();
	}

	@Override
	public int AddNumbers(int firstnumber, int secondnumber)
			throws RemoteException {
		return firstnumber + secondnumber;
	}

}
