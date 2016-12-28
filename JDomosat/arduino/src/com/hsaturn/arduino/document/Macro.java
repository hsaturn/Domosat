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
 */
public final class Macro extends AbstractSetting {

	public String key;
	public String macro;
	public String description;

	public Macro(String skey, String smacro, String sdescription) {
		key = skey;
		macro = smacro;
		description = sdescription;
	}

	public Macro(PropertyBag bag, String key) {
		read(bag, key);
	}

	@Override
	public void read(PropertyBag p, String prefix) {
		key = p.get(prefix + ".name", "noname");
		macro = p.get(prefix + ".macro", "");
		description = p.get(prefix + ".description", "");
	}

	@Override
	public void save(PropertyBag p, String prefix) {
		p.set(prefix + ".name", key);
		p.set(prefix + ".macro", macro);
		p.set(prefix + ".description", description);
	}

	public void setName(String newName) {
		key = newName;
	}

	public void setMacro(String newMacro) {
		macro = newMacro;
	}

	public void setDescription(String newDescription) {
		description = newDescription;
	}

	public String name() {
		return key;
	}

	public String macro() {
		return macro;
	}

	public String description() {
		return description;
	}

	@Override
	protected boolean _equals(AbstractSetting t) {
		Macro m = (Macro) t;
		return	key.equals(m.key) && 
				macro.equals(m.macro) &&
				description.equals(m.description);
	}
}
