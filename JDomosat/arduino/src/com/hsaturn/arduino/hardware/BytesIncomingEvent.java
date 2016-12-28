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
public class BytesIncomingEvent {

	public String data;

	/**
	 *
	 * @param data
	 */
	public BytesIncomingEvent(String data) {
		if (data == null) {
			this.data = "";
		} else {
			this.data = data;
		}
	}
}
