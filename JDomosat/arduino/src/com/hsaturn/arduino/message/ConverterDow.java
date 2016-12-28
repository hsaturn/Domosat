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
class ConverterDow extends AbstractConverter {

	public ConverterDow() {
	}

	@Override
	public String ConvertFrom(String s) {
		String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "???"};
		int iDay = 8;
		if (s.length() == 2) {
			iDay = Integer.parseInt(s, 16);
		}
		if (iDay < 0 || iDay > 8) {
			iDay = 8;
		}
		return days[iDay];
	}

	@Override
	public String ConvertTo(String s) {
		return Integer.toString(toInt(s));
	}

	@Override
	public String stringPattern(int iSize) {
		return "([0-9A-F]{2} ?)" + size(iSize);

	}

	@Override
	public int toInt(String s) {
		return Integer.parseInt(s,16);
	}

}
