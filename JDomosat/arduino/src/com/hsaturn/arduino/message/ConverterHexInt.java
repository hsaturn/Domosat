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
public class ConverterHexInt extends AbstractConverter {

	private int quartets;
	

	/**
	 * Renvoie la valeur int
	 * @param s Chaine hexa
	 * @return 
	 */
	@Override
	public int toInt(String s)
	{
		return hexaToInt(s);
	}

	public static int hexaToInt(String hex)
	{
		String sReverse =  reverseHexa(hex);
		int i = (short) Integer.parseInt(sReverse,16);
		return i;
	}

	public ConverterHexInt(int bits) {
		if (bits == 8) {
			quartets = 2;
		} else if (bits == 16) {
			quartets = 4;
		} else {
			System.err.println("ERREUR, bad size for converter (" + bits + ")");
		}
	}

	@Override
	public String ConvertFrom(String s) {
		String result = "";
		while (s.length()>0)
		{
			if (s.length()>quartets)
			{
				result += Integer.toString(toInt(s.substring(0, quartets)))+" ";
				s=s.substring(quartets);
			}
			else
			{
				result += Integer.toString(toInt(s));
				s="";
			}
		}
		return result;
	}

	@Override
	public String ConvertTo(String s) {
		return hexaPad(2,Integer.toString(Integer.parseInt(s)));
		
	}

	@Override
	public String stringPattern(int iSize) {
		return "[0-9A-F]" + size(quartets*iSize);
	}

}
