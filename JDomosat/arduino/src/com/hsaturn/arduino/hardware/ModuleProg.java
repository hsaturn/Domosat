/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.hardware;

import com.hsaturn.arduino.message.Message;

/**
 *
 * @author hsaturn
 */
public class ModuleProg extends Module {

	public static int BYTES_PER_ROW = 32;

	protected void onMessage(Message m) {

	}

	@Override
	void display(Message m) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getModuleIdentifier() {
		return "P";
	}

	@Override
	public void fillPatterns() {
		String PROG_STATS = "(FC{freemem:hex_int16}{pc:hex_int16})";
		String STACK = "FA{stksize:hex_int8}{stack:hex_int16,*}";
		addPattern("uprog_state", "(02|03|04){filler:hex_int8}" + PROG_STATS + STACK, true);
		addPattern("uprog_stack", "04{fillerE:hex_int8}" + PROG_STATS + STACK, true);
	}

	@Override
	public String name() {
		return "uProg";
	}

}
