/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.settings;

import com.hsaturn.utils.Observable;

/**
 *
 * @author hsaturn
 */
abstract public class AbstractSettings extends Observable {
	abstract public String readableValues() ;
}
