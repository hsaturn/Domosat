/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.utils;

import java.util.Properties;

/**
 *
 * @author hsaturn
 */
public class PropertyBag extends Properties {

	int get(String name, int iDefault) {
		return Integer.parseInt(getProperty(name, Integer.toString(iDefault)));
	}

	public boolean get(String name, boolean bDefault) {
		return Boolean.parseBoolean(getProperty(name, Boolean.toString(bDefault)));
	}

	public int getInt(String name) {
		return Integer.parseInt(getProperty(name, "0"));
	}

	public String get(String name) {
		return getProperty(name);
	}

	public String get(String name, String sDefault) {
		return getProperty(name, sDefault);
	}

	public void set(String name, int value) {
		if (checkNull(name, value)) {
			setProperty(name, Integer.toString(value));
		}
	}

	public void set(String name, String value) {
		if (checkNull(name, value)) {
			setProperty(name, value);
		}
	}

	public void set(String name, boolean value) {
		if (checkNull(name, value)) {
			setProperty(name, Boolean.toString(value));
		}
	}

	private boolean checkNull(String name, Object value) {
		if (value == null) {
			System.err.println("Attempting to save " + name + " that is null.");
			return false;
		}
		return true;
	}

}
