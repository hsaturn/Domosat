/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.hardware;

import com.hsaturn.arduino.message.Message;

/**
 *
 * @author francois
 */
public class ModuleClock extends Module {
	
	@Override
	void display(Message m) {
		if (m.contains("hhmm"))
		{
			System.out.println("Message Clock : "+m.get("hhmm").toString());
		}
		else if (m.contains("dow"))
		{
			System.out.println("Message dow : "+m.get("dow").toString());
		}
	}

	@Override
	public void fillPatterns() {
		addPattern("clock","00 *{hhmm:hhmm}",true);
		addPattern("dow","01 *{dow:dow}",true);
	}

	@Override
	public String getModuleIdentifier() {
		return "C";
	}

	@Override
	public String name() {
		return "Clock";
	}

}
