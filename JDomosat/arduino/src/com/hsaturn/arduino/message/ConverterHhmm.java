/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author francois
 */
public class ConverterHhmm extends ConverterHexInt {

	public ConverterHhmm()
	{
		super(16);
	}
	
	private String formatInt(int i)
	{
		String s=Integer.toString(i);
		if (s.length()==1)
			s="0"+s;
		return s;
	}
	
	@Override
	public String ConvertFrom(String s) {
		int i=super.toInt(s);
		return formatInt(i/100)+':'+formatInt(i%100);
	}

	@Override
	public String ConvertTo(String s) {
		s=hexaPad(5, s);
		Pattern p=Pattern.compile("(?<h>[0-9]{2}):(?<m>[0-9]{2})");
		Matcher m = p.matcher(s);
		if (m.find())
		{
			int hh = Integer.parseInt(m.group("h"));
			int mn = Integer.parseInt(m.group("m"));
			return hexaFromInt(hh*100+mn, 4, true);
		}
		return "";
	}

	@Override
	public String stringPattern(int iSize) {
		return "([0-9A-F]{2} ?)"+size(iSize*2);
	}

}
