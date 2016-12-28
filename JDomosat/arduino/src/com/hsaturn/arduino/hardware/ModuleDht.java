/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.hardware;

import com.hsaturn.arduino.message.Message;
import com.hsaturn.arduino.message.MessagePattern;

/**
 *
 * @author francois
 */
public class ModuleDht extends Module {
	
	@Override
	void display(Message m) {
		System.out.println(m.name+" : " + m.getValues().get("t") + "°");
	}

	@Override
	public void fillPatterns() {
		addPattern("température","00 *{temperature:fixed_float}{humidity:fixed_float}",true);
	}

	@Override
	public String getModuleIdentifier() {
		return "T";
	}

	@Override
	public String name() {
		return "Température";
	}
}
