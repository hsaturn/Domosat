/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.serial;

/**
 *
 * @author hsaturn
 */
public class SerialPortEvent extends jssc.SerialPortEvent {

	public static final int OPEN = 1024;
	public static final int CLOSE = 2048;
	public static final int SETTINGS_CHANGED = 4096;

	public SerialPortEvent(String portName, int eventType, int eventValue) {
		super(portName, eventType, eventValue);
	}

	public SerialPortEvent(jssc.SerialPortEvent jevt) {
		super(jevt.getPortName(), jevt.getEventType(), jevt.getEventValue());
	}

	public boolean isOpen() {
		return getEventType() == OPEN;
	}

	public boolean isClose() {
		return getEventType() == CLOSE;
	}

	public boolean isSettingsChanged() {
		return getEventType() == SETTINGS_CHANGED;
	}
}
