package com.hsaturn.arduino.uprog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hsaturn
 */
public class ProgDefsParser {

	private static ProgDefsParser instance = null;
	ArrayList<ProgCmd> cmds;

	public static ProgDefsParser instance() {
		if (instance == null) {
			instance = new ProgDefsParser("/home/hsaturn/Projets/Arduino/Domosat/ProgDefs.hpp");
		}
		return instance;
	}

	private ProgDefsParser(String sFileName) {
		cmds = new ArrayList<>();
		for (int i = 0; i < 64; i++) {
			cmds.add(null);
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(sFileName));
			String line;
			Pattern cmdPattern = Pattern.compile("#define CMD_(?<cmd>[A-Z][A-Z0-9_]*)[ \t]*(?<value>[0-9]+)");
			while ((line = br.readLine()) != null) {

				//	Pattern tmpPattern = Pattern.compile("\\{(?<var>[a-zA-Z_]*)(:(?<type>[a-z0-9_]*))?(,(?<size>([0-9]*|\\*)))?\\}");
				//		Matcher m = tmpPattern.matcher(simplifiedPattern);
				Matcher m = cmdPattern.matcher(line);
				while (m.find()) {
					System.out.println(m.group("value"));
					int value = Integer.parseInt(m.group("value"));
					cmds.set(value, new ProgCmd(m.group("cmd").toLowerCase(), (byte) value));
				}
			}
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		} finally {
			try {
				br.close();
			} catch (IOException ex) {
			}
		}
	}

	public String uncompile(ByteReaderInterface reader) {

		int b = reader.nextByte();
		switch (b >> 6) // Get type of instruction
		{
			case 0x0:	// num 6 bits
				if ((b & 0x20) != 0) {
					b = (byte) (b | 0xC0);
				}
				return "push " + Integer.toString((int) b);

			case 0x1:
				int s;
				s = reader.nextByte();
				s = ((b & 0x3f) << 8) | s;
				return "push " + Integer.toString((int) s);

			case 0x2:	// name
				char c = (char) ((b & 0x3F) + '*');
				return "'" + c + "'";

			case 0x3:	// instruction
				ProgCmd cmd = cmds.get(b & 0x3F);
				if (cmd == null) {
					return "???";
					//throw new ProgCmdException("Unknown command : " + b);
				}
				return cmd.cmd;
		}
		return "??? ("+Integer.toString(b, 16);
	}

	private static class ProgCmdException extends Exception {

		public ProgCmdException(String msg) {
			super(msg);
		}
	}

}
