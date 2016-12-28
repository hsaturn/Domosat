/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.settings.model;

import com.hsaturn.arduino.document.Rule;
import com.hsaturn.arduino.settings.RulesSettings;

/**
 *
 * @author hsaturn
 */
public class RulesTableModel extends AbstractSettingsTableModel<RulesSettings, Rule> {

	public RulesTableModel(RulesSettings theSettings) {
		super(theSettings, RulesSettings.class, Rule.class);
	}

	@Override
	protected void setColumnOfSetting(Rule r, String value, int columnIndex) {
		if (value == null) {
			value = "0";
		}
		if (columnIndex == 0) {
			r.setRuleName(value);
		}
		if (columnIndex == 1) {
			r.setNumber(Integer.parseInt(value));
		}
		if (columnIndex == 2) {
			r.setRule(value);
		}
		if (columnIndex == 3) {
			r.setDescription(value);
		}
		return;
	}

	@Override
	public String[] getColumnsName() {
		return new String[]{"Name", "Number", "Rule", "Description"};
	}

	@Override
	protected String getColumnOfSetting(Rule r, int columnIndex) {
		if (r == null) {
			return "<null>";
		}
		try {
			if (columnIndex == 0) {
				return r.rulename();
			}
			if (columnIndex == 1) {
				return Integer.toString(r.ruleNumber());
			}
			if (columnIndex == 2) {
				return r.rule();
			}
			if (columnIndex == 3) {
				return r.description();
			}
		} catch (Exception ex) {

		}
		return "<oob>";
	}

}
