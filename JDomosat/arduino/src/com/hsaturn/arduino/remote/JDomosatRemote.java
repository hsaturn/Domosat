package com.hsaturn.arduino.remote;

// Lancement de message avec 
// java com.hsaturn.arduino.remote.JDomosatClientRemote 127.0.0.1 3233 close_port
import com.hsaturn.arduino.hardware.Arduino;
import com.hsaturn.serial.SerialPort;
import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;
import javax.swing.SwingUtilities;

// @FIXME
// cette classe devrait contenir un ArrayList<Arduino> pour pouvoir itérer
// et répondre correctement en toute circonstance.
public class JDomosatRemote extends java.rmi.server.UnicastRemoteObject
		implements ReceiveMessageInterface {

	static private JDomosatRemote instance = null;

	public static JDomosatRemote getInstance() throws RemoteException {
		if (instance == null) {
			instance = new JDomosatRemote();
		}
		return instance;
	}

	Arduino arduino;

	int thisPort;
	String thisAddress;
	Registry registry;    // rmi registry for lookup the remote objects.

	// This method is called from the remote client by the RMI.
	// This is the implementation of the ReceiveMessageInterface.h.
	@Override
	public void receiveMessage(final String x) throws RemoteException {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Here, we can safely update the GUI
				// because we'll be called from the
				// event dispatch thread

				System.out.println(x);
				if (arduino != null) {
					SerialPort p = arduino.getSerialPort();
					if (p != null) {
						try {
							switch (x) {
								case "close_port":
									p.closePort();
									break;
								case "open_port":
									p.openPort();
									break;
							}
						} catch (Exception ex) {
							System.err.println(ex.getMessage());
						}
					}
					arduino.notifyObservers(p);
				}
			}
		});
	}

	private JDomosatRemote() throws RemoteException {

		try {
			// get the address of this host.
			thisAddress = (InetAddress.getLocalHost()).toString();
		} catch (Exception e) {
			throw new RemoteException("can't get inet address.");
		}
		thisPort = 3233;  // this port(registryfs port)
		System.out.println("this address=" + thisAddress + ",port=" + thisPort);
		try {
			// create the registry and bind the name and object.
			registry = LocateRegistry.createRegistry(thisPort);
			registry.rebind("JDomosatRemote", this);
		} catch (RemoteException e) {
			throw e;
		}
	}

	public void setArduino(Arduino arduino) {
		this.arduino = arduino;
	}

}
