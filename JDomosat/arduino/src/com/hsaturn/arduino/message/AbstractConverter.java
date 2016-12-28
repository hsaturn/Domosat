/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.message;

/**
 *
 * @author hsaturn
 */
public abstract class AbstractConverter implements ConverterInterface {

	public AbstractConverter() {

	}

	static public AbstractConverter factory(String type) {

		if (type != null) {
			switch (type) {	// @DESIGN: pas terrible le swtich
				case "fixed_float":
					return new ConverterFixedFloat();
				case "hhmm":
					return new ConverterHhmm();
				case "hex_int8":
					return new ConverterHexInt(8);
				case "hex_int16":
					return new ConverterHexInt(16);
				case "dow":
					return new ConverterDow();
				case "char":
					return new ConverterChar();
				case "int":
					return new ConverterInt();
			}
		}
		System.err.println("ERROR: Unkown Converter Factory type : " + type);
		return null;
	}

	abstract public String stringPattern(int iSize);

	public final String size(int i) {
		if (i > 1) {
			return "{" + i + "}";
		} else if (i ==0)
		{
			return "*";
		}
		return "";
	}

	public static final String hexaPad(int i, String s) {
		s=s.toUpperCase();
		while (s.length() < i) {
			s = "0" + s;
		}
		return s;
	}

	public static final String reverseHexa(String s) {
		String sReverse = "";
		while (s.length() > 1) {
			sReverse = s.substring(0, 2) + sReverse;
			s = s.substring(2);
		}
		return sReverse;
	}

	/**
	 *
	 * @param i          Integer to transform
	 * @param iSize      Final hexa size
	 * @param bReversed  Reverse the hexa result (0xFF45 -> 0x45FF)
	 * @return
	 */
	public static final String hexaFromInt(int i, int iSize, boolean bReversed) {
		String hex= Integer.toString(i,16);
		hex=hexaPad(iSize, hex);
		if (bReversed)
			hex=reverseHexa(hex);
		return hex;
	}

	String getType() {
		String s = this.getClass().getSimpleName();

		return s.replace("Converter", "");
	}
	
	abstract public int toInt(String s);
	
	public float toFloat(String s)
	{
		System.err.println("Not yet implemented "+this.getClass().getSimpleName()+".toFloat()");
		return 0;
	}

}
