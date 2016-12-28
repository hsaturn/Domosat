/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.widgets.small;

import com.hsaturn.arduino.hardware.EepromPart;
import com.hsaturn.arduino.uprog.ByteReaderInterface;
import com.hsaturn.arduino.uprog.ProgDefsParser;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.AbstractListModel;

/**
 *
 * @author hsaturn
 */
public class ListModelProg extends AbstractListModel {

	private final int rowsUp;
	private final int rowsDown;
	private final int pc;
	private final EepromPart eeprom;

	private final int[] pgmBytes = new int[2048];	// FIXME

	public ListModelProg(EepromPart eeprom, int pc, int rowsUp, int rowsDown) {
		for (int i = 0; i < 2048; i++) {
			//pgmBytes[i] = 1;
		}
		this.rowsUp = rowsUp;
		this.rowsDown = rowsDown;
		this.eeprom = eeprom;
		this.pc = pc;
	}

	@Override
	public int getSize() {
		return rowsDown + 1 + rowsUp;
	}

	@Override
	public Object getElementAt(int index) {
		try {
			int curpc = pc;
			for (int i = 0; i < rowsUp; i++) {
				curpc--;
				if (pgmBytes[curpc - 1] == 2) {
					curpc--;
				}
			}
			while (index > 0) {
				index--;
				curpc += pgmBytes[curpc];
			}
			if (index == rowsUp && curpc != pc) {
				System.err.println("ListModelProg: on devrait être sur le PC (" + curpc + "/" + pc + ")");
			}

			ByteReaderInterfaceImpl reader = new ByteReaderInterfaceImpl(curpc);

			String cmd = ProgDefsParser.instance().uncompile(reader);
			String spc = Integer.toString(curpc, 16);
			while (spc.length() != 4) {
				spc = "0" + spc;
			}
			pgmBytes[curpc] = reader.used();
			return "0x" + spc + " " + cmd;
		} catch (Exception ex) {
			System.err.println("Exception in ListModelProg::getElementAt: " + ex.getMessage());
		}
		return "xxxxxx";
	}

	private class ByteReaderInterfaceImpl implements ByteReaderInterface {

		int pc;
		int used = 0;

		public ByteReaderInterfaceImpl(int idxpc) {
			this.pc = idxpc - 1;
		}

		@Override
		public int nextByte() {
			if (eeprom != null) {
				pc++;
				used++;
				if (pc >= 0 && pc < eeprom.getBytes().length) {
					int b = eeprom.getBytes()[pc].value();
					return b;
				}
				return 0;
			} else {
				System.err.println("ListModelProg : No eeprom !");
			}
			return 0;
		}

		public int getPc() {
			return pc;
		}

		private int used() {
			return used;
		}
	}
}
