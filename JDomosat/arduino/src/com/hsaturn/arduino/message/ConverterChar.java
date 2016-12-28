/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.message;

/**
 *
 * @author francois
 */
class ConverterChar extends AbstractConverter {

	public ConverterChar() {
	}

	@Override
	public String ConvertFrom(String s) {
		if (s.length() == 2) {
			return ""+(char)(Integer.parseInt(s, 16));
		} else {
			return s;
		}
	}

	@Override
	public String ConvertTo(String s) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String stringPattern(int iSize) {
		return "([0-9A-F]{2} ?)"+size(iSize);
		//return "([0-9A-F]"+size(2*iSize)+"|[a-zA-Z]"+size(iSize)+")";

	}

	@Override
	public int toInt(String s) {
		throw new UnsupportedOperationException("Converter char does not support toInt()."); //To change body of generated methods, choose Tools | Templates.
	}

}
