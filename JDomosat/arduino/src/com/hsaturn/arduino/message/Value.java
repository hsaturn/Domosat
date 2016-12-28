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
public class Value {

	private final String name;
	private final String rawValue;
	private final AbstractConverter converter;
	public boolean important;

	public Value(String name, String rawValue, AbstractConverter converter, boolean important) {
		this.name = name;
		this.important = important;
		if (rawValue == null) {
			rawValue = "";
		}
		if (converter == null) {
			System.err.println("WARNING: using default converter.");
			converter = new ConverterIdentity();
		}
		this.converter = converter;
		this.rawValue = rawValue;
	}
	
	public boolean equals(String s)
	{
		return converter.ConvertFrom(rawValue).equals(s);
	}

	public String getName() {
		return name;
	}

	public String getRawValue() {
		return rawValue;
	}

	public String getAnnotated() {
		return name + "=" + converter.ConvertFrom(rawValue);
	}

	@Override
	public String toString() {
		return converter.ConvertFrom(rawValue);
	}

	String name() {
		return name;
	}

	public int toInt() {
		return converter.toInt(rawValue);
	}
}
