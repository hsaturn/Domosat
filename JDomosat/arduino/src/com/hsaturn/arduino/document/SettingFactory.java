/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.document;

import com.hsaturn.utils.PropertyBag;

/**
 *
 * @author hsaturn
 * @param <CLASS>
 */
public abstract class SettingFactory {

	/**
	 *
	 * @param bag
	 * @param bag_name
	 * @return
	 */
	public abstract AbstractSetting createInstance(PropertyBag bag, String bag_name);
}
