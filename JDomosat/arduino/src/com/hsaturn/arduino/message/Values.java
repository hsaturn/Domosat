/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author hsaturn
 */
public class Values {

	ArrayList<Value> values;

	public Values() {
		this.values = new ArrayList<>();
	}

	public ArrayList<Value> getValues() {
		return values;
	}
	
	public Value get(String var)
	{
		
		for(Value v : values)
		{
			if (v.getName().equals(var))
				return v;
		}
		return null;
	}

	public void add(Value value) {
		for (int i = 0; i < values.size(); i++) {
			Value v = values.get(i);
			if (v.getName().equals(value.getName())) {
				values.set(i, value);
				return;
			}
		}
		values.add(value);
	}

	@Override
	public String toString() {
		String s = "";
		for (Value v : values) {
			if (s.length() > 0) {
				s += ", ";
			}
			s += v.getAnnotated();
		}
		return s;
	}

	public final String getAnnotatedList() {
		String s = "";
		for (Value v : values) {
			if (s.length() > 0) {
				s += ", ";
			}
			s += v.getName() + '=' + v.getRawValue();
		}
		return s;

	}

	public Values getImportantValues() {
		Values important = new Values();
		for (Value v : values) {
			if (v.important) {
				important.add(v);
			}
		}
		return important;
	}

	String getRawList(String sep) {
		String s = "";
		for (Value v : values) {
			s += v.getRawValue() + sep;
		}
		return s;
	}

	public boolean contains(String name) {
		for (Value v : values)
			if (v.getName().equals(name))
				return true;
		return false;
	}

}
