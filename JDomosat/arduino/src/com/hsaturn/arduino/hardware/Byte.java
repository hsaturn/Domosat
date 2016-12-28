/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.hardware;

/**
 *
 * @author hsaturn
 */
public class Byte {

	public final static int READ = 0;
	public final static int WROTE = 1;
	public final static int UNVERIFIED = 2;

	private boolean initialized;
	private int value;
	private int state;

	public Byte() {
		initialized = false;
		this.state = UNVERIFIED;
	}

	public Byte(String hex, int state) {
		updateUnsigned(hex, state);
	}

	public Byte(int b, int state) {
		this.state = state;
		initialized = true;
		value = b;
	}

	public int value() {
		return this.value;
	}

	public int state() {
		return this.state;
	}

	@Override
	public String toString() {
		if (initialized) {
			String s = "";
			int b = value;
			while (s.length() != 2) {
				char c = (char) (b & 0xF);
				if (c >= 10) {
					c = (char) ('A' - 10 + c);
				} else {
					c = (char) ('0' + c);
				}
				s = c + s;
				b >>= 4;
			}
			return s;
		} else {
			return "--";
		}
	}

	public void updateUnsigned(String hex, int state) {
		value = 0;
		initialized = false;

		if (hex.length() != 2) {
			return;
		}

		hex = hex.toUpperCase();
		this.state = state;
		for (int i = 0; i < hex.length(); i++) {
			value <<= 4;
			char c = hex.charAt(i);
			if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F') {
				if (c >= 'A') {
					value += c - 'A' + 10;
				} else {
					value += c - '0';
				}
			} else {
				return;
			}
		}
		this.initialized = true;
		this.state = state;
	}

	public void updateSigned(String hex, int state) {
		try {
			this.state = state;
			value = (byte) Integer.parseInt(hex, 16);
			initialized = true;
		} catch (NumberFormatException ex) {
			this.state = UNVERIFIED;
			initialized = false;
		}
	}
}
