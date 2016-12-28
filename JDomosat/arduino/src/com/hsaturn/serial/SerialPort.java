/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.serial;

import java.util.ArrayList;
import jssc.SerialPortException;

/**
 *
 * @author hsaturn
 */
public class SerialPort implements jssc.SerialPortEventListener {

	transient static ArrayList<SerialPort> serialPorts = new ArrayList<>();

	transient ArrayList<SerialPortEventListener> eventListeners;
	transient jssc.SerialPort serialPort;
	private int baud_rate = 1200;
	private long total_bytes_received = 0;

	private SerialPort(String portName) {
		serialPort = new jssc.SerialPort(portName);
		eventListeners = new ArrayList<>();

	}

	public static SerialPort factory(String portName) throws SerialPortException {
		for (SerialPort sp : serialPorts) {
			if (sp.getPortName().equals(portName)) {
				return sp;
			}
		}
		SerialPort sp = new SerialPort(portName);
		serialPorts.add(sp);
		return sp;
	}

	public String getPortName() {
		return serialPort.getPortName();
	}

	@Override
	public void serialEvent(jssc.SerialPortEvent spe) {
		if (spe.isRXCHAR()) {
			total_bytes_received += spe.getEventValue();
		}
		SerialPortEvent evt = new SerialPortEvent(spe);
		notifyListeners(evt);
	}

	private void notifyListeners(SerialPortEvent evt) {
		for (SerialPortEventListener eventListener : eventListeners) {
			eventListener.serialEvent(evt);
		}
	}

	public byte[] readBytes(int byteCount) throws SerialPortException {
		return serialPort.readBytes(byteCount);
	}

	public boolean closePort() throws SerialPortException {
		boolean b = serialPort.closePort();
		notifyListeners(new SerialPortEvent(serialPort.getPortName(), SerialPortEvent.CLOSE, 1));
		return b;
	}

	public boolean openPort() throws SerialPortException {
		boolean b = false;
		try {

			b = serialPort.openPort();
			serialPort.addEventListener(this);

			notifyListeners(new SerialPortEvent(serialPort.getPortName(), SerialPortEvent.OPEN, 1));
		} catch (SerialPortException ex) {
			throw ex;
		}
		return b;
	}

	public boolean setParams(int baudRate, int dataBits, int stopBits, int parity) throws SerialPortException {

		boolean b = serialPort.setParams(baudRate, dataBits, stopBits, parity);
		this.baud_rate = baudRate;
		notifyListeners(new SerialPortEvent(serialPort.getPortName(), SerialPortEvent.SETTINGS_CHANGED, 0));
		return b;
	}

	public void addEventListener(SerialPortEventListener eventListener) {
		if (!eventListeners.contains(eventListener)) {
			eventListeners.add(eventListener);
		}
	}

	public boolean isOpened() {
		return serialPort.isOpened();
	}

	public boolean writeString(String str) throws SerialPortException {
		return serialPort.writeString(str);
	}

	public void removeListener(SerialPortEventListener eventListener) {
		eventListeners.remove(eventListener);
	}

	public int getBaudRate() {
		return baud_rate;
	}

	public long getTotalBytesReceived() {
		return total_bytes_received;
	}
}
