/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.message;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 *
 * @author hsaturn
 */
public class Message {

	private Values values;
	public String name;
	final Matcher matcher;	// The matcher that gave this message.
	
	public Message(String name, Matcher matcher) {
		this.name = name;
		this.matcher = matcher;
		this.values = new Values();
	}

	public Values getValues() {
		return values;
	}

	@Override
	public String toString() {
		return values.toString();
	}

	public Values getImportantValues() {
		return values.getImportantValues();
	}

	public final String getRawMessage(String sep) {
		return values.getRawList(sep);
	}

	public void addValue(Value value) {
		values.add(value);
	}
	
	/**
	 * return Value or null
	 * @param var
	 * @return 
	 */
	public Value get(String var) {
		return values.get(var);
	}

	public boolean contains(String dow) {
		return values.contains(dow);
	}
}
