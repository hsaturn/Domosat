package com.hsaturn.arduino.hardware;

import com.hsaturn.arduino.message.ConverterFixedFloat;
import com.hsaturn.arduino.message.ConverterHexInt;
import com.hsaturn.arduino.message.Message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hsaturn
 */
public class EepromPart extends Module {

	public static final int MAX_EEPROM_MSG = 24;
	private final Byte[] bytes;

	public EepromPart() {
		bytes = new Byte[2048];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = new Byte();
		}
	}

	Byte get(int index) {
		return bytes[index];
	}

	public void read(int iStart, int iCount) {
		while (iCount > 0) {
			String sMsg = "m00RO";
			sMsg += ConverterHexInt.hexaFromInt(iStart, 4, true);
			int iSize = iCount;
			if (iCount > 16) {
				iSize = 16;
			} else {
				iSize = iCount;
			}
			iCount -= iSize;
			sMsg += ConverterHexInt.hexaFromInt(iSize, 2, true);
			arduino.send(sMsg, true);
		}
	}

	public boolean write(int iOffset, String hexa) {
		return write(arduino, iOffset, hexa);
	}

	// @FIXME, ca sent le mauvais design ça ...
	public static boolean write(Arduino arduino, int iOffset, String hexa) {
		boolean bRet = true;
		hexa = hexa.replace(" ", "");
		while (bRet && hexa.length() > 0) {
			String send;
			int length;
			if (hexa.length() > MAX_EEPROM_MSG) {
				send = hexa.substring(0, MAX_EEPROM_MSG);
				hexa = hexa.substring(MAX_EEPROM_MSG);
			} else {
				send = hexa;
				hexa = "";
			}
			length = send.length();
			String sMsg = "m001O";
			sMsg += ConverterHexInt.hexaFromInt(iOffset, 4, true);
			sMsg += ConverterHexInt.hexaFromInt(length >> 1, 2, true);
			sMsg += send;
			bRet = bRet && arduino.send(sMsg, true);
			if (bRet) {
				while (send.length() > 0) {
					Byte b = new Byte(send.substring(0,2), Byte.WROTE);
					send = send.substring(2);
					iOffset++;
				}
			} else {
				System.out.println("ERROR while sending bytes to module EEprom");
			}
		}
		return bRet;
	}

	@Override
	void display(Message m) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getModuleIdentifier() {
		return "O";
	}

	@Override
	public void fillPatterns() {
		addPattern("eeprom_dump", "{address:hex_int16}{length:hex_int8}{dump:hex_int8,*}", true);
	}

	@Override
	public String name() {
		return "Eeprom";
	}

	@Override
	protected void onMessage(Message m) {
		if (m.name.equals("eeprom_dump")) {
			int address = m.get("address").toInt();
			int length = m.get("length").toInt();
			String s = m.get("dump").getRawValue();
			if (address >= 0 && address + length <= bytes.length) {
				while (length > 0) {
					if (s.length() > 2) {
						String hex = s.substring(0, 2);
						s = s.substring(2);
						bytes[address++] = new Byte(hex, Byte.READ);
					} else {
						System.err.println("ERROR: Eeprom dump is not large enough");
						System.err.println("  length = " + m.get("le"));
						System.err.println("  dump   = " + m.get("dump").getRawValue());
					}
					length--;
				}
				setChanged();
				notifyObservers(m);
			}
		}
	}

	public Byte[] getBytes() {
		return bytes;
	}

	public void fireChanged() {
		setChanged();
		notifyObservers();
	}

}
