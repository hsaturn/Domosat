/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.document;

import com.hsaturn.utils.PropertyBag;
import com.hsaturn.utils.Observable;

/**
 *
 * @author hsaturn
 */
public abstract class AbstractSetting extends Observable {

	public boolean equals(AbstractSetting t) {
		if (t == null) {
			return false;
		}
		if (t.getClass() == this.getClass()) {
			return _equals(t);
		}
		return false;
	}

	abstract public void read(PropertyBag p, String prefix);

	abstract public void save(PropertyBag bag, String prefix);

	abstract protected boolean _equals(AbstractSetting t);

}
