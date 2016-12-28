/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.settings;

import com.hsaturn.arduino.document.Macro;
import com.hsaturn.arduino.gui.settings.model.AbstractSettingsTableModel;
import com.hsaturn.arduino.gui.settings.model.MacrosTableModel;
import com.hsaturn.arduino.settings.MacroSettings;

/**
 *
 * @author hsaturn
 */
public class MacrosEditor extends  AbstractSettingsTableEditor<MacroSettings, Macro> {

	public MacrosEditor() {
		super(MacroSettings.class);
	}

	@Override
	public String getTitle() {
		return "Macros";
	}

	@Override
	protected void openEditor(Macro r) {
	}

	@Override
	protected Macro createNewSetting() {
		return null;
	}
	
	@Override
	protected AbstractSettingsTableModel<MacroSettings, Macro> createNewModel(MacroSettings t) {
		return new MacrosTableModel(t);
	}
	
}
