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
public interface ConverterInterface {

	String ConvertFrom(String s);

	String ConvertTo(String s);
	
}
