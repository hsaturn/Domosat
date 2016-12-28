/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.message;

import java.text.DecimalFormat;

/**
 *
 * @author hsaturn
 */
public class ConverterFixedFloat extends ConverterHexInt {

	public ConverterFixedFloat() {
		super(16);
	}

	@Override
	public String ConvertFrom(String s) {
		String f = new DecimalFormat("###.#").format(toFloat(s));
		return f;
	}

	@Override
	public String ConvertTo(String s) {
		String hex = Integer.toString((int) (Float.parseFloat(s) * 10), 16);
		hex = hexaPad(4, hex);
		return reverseHexa(hex);
	}

	public float toFloat(String s) {
		return ((float) super.toInt(s)) / 10;
	}
}
