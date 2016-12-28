/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.settings;

/**
 *
 * @author hsaturn
 */
public class CommSettings extends AbstractSettings {

	private int speed;
	private String comm_port;
	private int bits;
	private Integer parity;
	private Integer stop_bits;
	private boolean echo;

	public int speed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if (this.speed != speed) {
			this.speed = speed;
			setChanged();
			notifyObservers();
		}
	}

	public void setCommPort(String commPort) {
		if (!commPort.equals(comm_port)) {
			comm_port = commPort;
			setChanged();
			notifyObservers();
		}
	}

	public int getSpeeed() {
		return speed;
	}

	public String getDevice() {
		return comm_port;
	}

	public int parity() {
		return parity;
	}

	public int stop_bits() {
		return stop_bits;
	}

	public int bits() {
		return bits;
	}

	@Override
	public String readableValues() {
		StringBuilder buff = new StringBuilder();

		buff.append("Port:").append(comm_port);
		buff.append(" ").append(Integer.toString(speed));
		buff.append(", ").append(Integer.toString(bits));
		if (parity == 0) {
			buff.append('N');
		} else if (parity == 1) {
			buff.append('E');
		} else {
			buff.append('O');
		}
		buff.append(stop_bits);
		return buff.toString();
	}

	public void setBits(int bits) {
		if (this.bits != bits) {
			this.bits = bits;
			setChanged();
			notifyObservers();
		}
	}

	public void setStopBits(Integer stopBits) {
		if (stop_bits != stopBits) {
			stop_bits = stopBits;
			setChanged();
			notifyObservers();
		}
	}

	public void setParity(Integer parity) {
		if (this.parity != parity) {
			this.parity = parity;
			setChanged();
			notifyObservers();
		}
	}

	public void setEcho(boolean selected) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public boolean echo() {
		return echo;
	}

}
