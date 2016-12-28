package com.hsaturn.arduino.remote;

import java.rmi.*;
import java.rmi.registry.*;

public class JDomosatClientRemote
{
	static public void main(String args[])
	{
		ReceiveMessageInterface rmiServer;
		Registry registry;
		String serverAddress=args[0];
		String serverPort=args[1];
		String text=args[2];
		System.out.println("sending "+text+" to "+serverAddress+":"+serverPort);
		try{
			// get the registry 
			registry=LocateRegistry.getRegistry(
					serverAddress,
					(new Integer(serverPort)).intValue()
					);
			// look up the remote object
			rmiServer=
				(ReceiveMessageInterface)(registry.lookup("JDomosatRemote"));
			// call the remote method
			rmiServer.receiveMessage(text);
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
		catch(NotBoundException e){
			e.printStackTrace();
		}
	}
}

