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
class ConverterIdentity extends AbstractConverter {

	public ConverterIdentity() {
	}

	@Override
	public String ConvertFrom(String s) {
		return s;
	}

	@Override
	public String ConvertTo(String s) {
		return s;
	}

	@Override
	public String stringPattern(int iSize) {
		return "[0-9A-F]"+size(2*iSize);
	}

	@Override
	public int toInt(String s) {
		throw new UnsupportedOperationException("ConverterIdentity::toInt() Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
