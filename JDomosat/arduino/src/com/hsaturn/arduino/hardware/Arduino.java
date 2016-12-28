package com.hsaturn.arduino.hardware;

import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.settings.CommSettings;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import com.hsaturn.arduino.remote.JDomosatRemote;
import com.hsaturn.serial.SerialPort;
import com.hsaturn.serial.SerialPortEvent;
import com.hsaturn.serial.SerialPortEventListener;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import jssc.SerialPortException;
import org.openide.util.Exceptions;

/**
 * Observable update notifications
 *
 * update(this, Object)
 *
 * Object can be; - SerialPortEvent : when something arrives from the serialport
 * - CommSettings : when the comm settings has changed.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hsaturn
 */
public class Arduino extends Observable implements Observer, SerialPortEventListener {

	public int id;	// Id de l'arduino (donné par l'arduino), -1 if non encore lu
	public EepromPart eeprom;
	transient private SerialPort serialPort;	// @FIXME devrait passer par une factory
	public ArrayList<Module> modules = null;
	transient boolean bResolving = false;
	transient boolean bInit = true;

	public Arduino() {
		id = -1;
	}

	public String getName() {
		return "Arduino #" + id;
	}

	private Object readResolve() {
		System.out.println("Resolving Arduino " + id);
		bResolving = true;
		initModules();
		bResolving = false;
		return this;
	}

	public void init(ArduinoProject project) {
		if (bInit) {
			bInit = false;
			initModules();
			setSettings((CommSettings) project.getSettings("CommSettings"));
		}
	}

	private void initModules() {
		// @TODO Doit être extrait de l'arduino !!!
		addModule(new ModuleDht());
		addModule(new ModuleClock());
		addModule(new ModuleProg());
		eeprom = new EepromPart();
		addModule(eeprom);

		ArrayList<String> here = new ArrayList<>();
		System.out.println("Registered modules for Arduino #" + id);
		for (int i = modules.size() - 1; i >= 0; i--) {
			Module m = modules.get(i);
			if (here.contains(m.getModuleIdentifier())) {
				// @FIXME Erreur grave
				System.err.println("Removing alread registered twice :" + m.getModuleIdentifier());
				modules.remove(i);
			} else {
				here.add(m.getModuleIdentifier());
			}
		}
	}

	/**
	 * Add a module if it is not already existing.
	 *
	 * @param module
	 * @return
	 */
	public boolean addModule(Module module) {
		if (modules == null) {
			modules = new ArrayList<>();
		}

		if (modules.contains(module)) {
			return false;
		}

		System.out.println("Adding observer module " + module.name());
		modules.add(module);
		module.arduino = this;
		module.addObserver(this);	// @FIME double link ...
		addObserver(module);
		return true;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!bResolving) {
			if (o instanceof CommSettings) {
				setSettings((CommSettings) o);
			}
		}
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	@Override
	public void serialEvent(SerialPortEvent spe) {
		if (spe.isRXCHAR()) {
			byte[] b;
			String s = "";
			try {
				b = serialPort.readBytes(spe.getEventValue());
				try {
					s = new String(b, "ISO-8859-1");
				} catch (UnsupportedEncodingException ex) {
					Exceptions.printStackTrace(ex);
				}
				setChanged();
				notifyObservers(new BytesIncomingEvent(s));
			} catch (SerialPortException ex) {
				Exceptions.printStackTrace(ex);
			}
		}

	}

	private void setSettings(CommSettings commSettings) {
		if (commSettings != null) {
			try {
				// @FIXME Multiple arduino can <<share>> the same port... with tons of problems for that
				System.out.println("FormConsole : reloading comm settings :" + commSettings.readableValues());
				try {
					if (serialPort != null) {
						serialPort.closePort();
					}
				} catch (Exception e) {

				}
				serialPort = SerialPort.factory(commSettings.getDevice());
				//serialPort.setDTR(false);
				//serialPort.setRTS(false);
				serialPort.openPort();
				serialPort.setParams(
						commSettings.getSpeeed(),
						commSettings.bits(),
						commSettings.stop_bits(),
						commSettings.parity());
				serialPort.addEventListener(this);
				// @FIXME bEcho = commSettings.echo();
				commSettings.addObserver(this);
				setChanged();
				notifyObservers(serialPort);
				/*
				try {
					JDomosatRemote.getInstance().setArduino(this);
				} catch (RemoteException ex) {
					Exceptions.printStackTrace(ex);
				}*/
			} catch (SerialPortException ex) {
				System.err.println("FormConsole : cannot open port. " + ex.getMessage());
			}
		} else {
			System.err.println("Arduino : unable to get settings");
		}
	}

	public boolean send(String msg, boolean bCr) {
		// @TODO reflect change in console ? (non !)
		if (serialPort.isOpened()) {
			System.out.println("SEND ["+msg+"]");
			try {
				if (bCr) {
					msg += "\r";
				}
				serialPort.writeString(msg);
				Thread.sleep(2*msg.length());
				return true;
			} catch (SerialPortException | InterruptedException ex) {
				System.out.println("Ex: "+ex.getMessage());
				Exceptions.printStackTrace(ex);
			}
		}
		return false;
	}

}
