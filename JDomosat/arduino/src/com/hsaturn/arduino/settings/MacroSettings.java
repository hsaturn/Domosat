/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.settings;

import com.hsaturn.arduino.document.Macro;
import com.hsaturn.arduino.document.AbstractSetting;
import com.hsaturn.arduino.document.SettingFactory;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Iterator;

/**
 *
 * @author hsaturn
 */
@XStreamAlias("macros")
public class MacroSettings extends AbstractSettingsArray<Macro> {

	@Override
	public String readableValues() {
		return "Macros has no readable value";
	}

	@Override
	public String name() {
		return "macro";
	}

	@Override
	public boolean canAdd() {
		return false;
	}

	@Override
	public boolean canRemove() {
		return false;
	}

	@Override
	public boolean hasCustomEditForm() {
		return false;
	}

	@Override
	public void checkIntegrity() {
		for (Iterator<Macro> it = settings.iterator(); it.hasNext();) {
			Macro m = it.next();
			if (!m.name().matches("F[1-9]")) {
				it.remove();
			}
		}
		for (int i = 1; i <= 9; i++) {
			String name = "F" + i;
			boolean bFound = false;
			for (Macro m : settings) {
				if (m.name().equals(name)) {
					bFound = true;
				}
			}
			if (!bFound) {
				// Missing macro
				Macro m = new Macro(name, "", "");
				_add(m);
			}
		}
	}

}
