/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.settings.model;

import com.hsaturn.arduino.document.Macro;
import com.hsaturn.arduino.settings.MacroSettings;

/**
 *
 * @author hsaturn
 */
public class MacrosTableModel extends AbstractSettingsTableModel<MacroSettings, Macro> {

	public MacrosTableModel(MacroSettings theSettings) {
		super(theSettings, MacroSettings.class, Macro.class);
	}

	@Override
	protected void setColumnOfSetting(Macro macro, String value, int columnIndex) {
		if (columnIndex == 0) macro.setName(value);
		if (columnIndex == 1) macro.setMacro(value);
		if (columnIndex == 2) macro.setDescription(value);
	}

	@Override
	public String[] getColumnsName() {
		return new String[]{"Name","Macro","Description"};
	}

	@Override
	protected String getColumnOfSetting(Macro m, int columnIndex) {
		if (columnIndex == 0) return m.name();
		if (columnIndex == 1) return m.macro();
		if (columnIndex == 2) return m.description();
		return "<oob>";
	}

}
