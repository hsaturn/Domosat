/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.settings;

import com.hsaturn.arduino.document.Rule;
import com.hsaturn.arduino.gui.CustomView;
import com.hsaturn.arduino.gui.settings.model.RulesTableModel;
import com.hsaturn.arduino.settings.RulesSettings;
import org.openide.windows.TopComponent;

/**
 *
 * @author hsaturn
 */
public class RulesEditor extends AbstractSettingsTableEditor<RulesSettings, Rule> {

	public RulesEditor() {
		super(RulesSettings.class);
	}

	@Override
	protected void openEditor(Rule r) {

		if (r != null) {
			CustomView v = new CustomView(TopComponent.PERSISTENCE_NEVER, "New rule");
			v.add(new FormSingleRuleEditor(v, r));
			v.open();
		}
	}

	@Override
	protected RulesTableModel createNewModel(RulesSettings t) {
		return new RulesTableModel(t);
	}

	@Override
	protected Rule createNewSetting() {
		return new Rule("New rule", "", "");
	}

	@Override
	public String getTitle() {
		return "Rules";
	}

}
