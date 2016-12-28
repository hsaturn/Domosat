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
class ConverterInt extends AbstractConverter {

	/**
	 * Renvoie la valeur int
	 * @param s Chaine hexa
	 * @return 
	 */
	@Override
	public int toInt(String s)
	{
		return Integer.parseInt(s,16);
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
		return "[0-9]+";
	}


}
