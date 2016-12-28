/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.settings;

import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.document.ProjectList;
import com.hsaturn.arduino.settings.AbstractSettings;
import java.awt.Component;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;

/**
 *
 * @author hsaturn
 */
abstract public class AbstractSettingsTab extends javax.swing.JPanel implements Observer {

	private AbstractSettings settings;
	private ArduinoProject project;

	public AbstractSettingsTab() {
		super();
		ProjectList.getInstance().addObserver(this);
	}

	@Override
	public void setEnabled(boolean bEnabled) {
		super.setEnabled(bEnabled);
		for (Component comp : getComponents()) {
			comp.setEnabled(bEnabled);
		}
	}

	@Override
	final public void update(Observable o, Object arg) {
		if (arg instanceof ArduinoProject) {
			if (arg != project) {
				System.out.println("AbstractSettingsTab "+this.getClass().getSimpleName()+" : Changing project");
				project = (ArduinoProject) arg;
				onProjectChanged(project);
			}
			//
			AbstractSettings newSettings = project.getSettings(getSettingsClass());
			changeSettings(newSettings);

		} else if (arg instanceof AbstractSettings) {
			changeSettings((AbstractSettings) arg);
		} else {
			updateOther(o, arg);
		}
	}

	abstract public String getTitle();

	abstract public String getSettingsClass();

	abstract protected void onProjectChanged(ArduinoProject project);

	abstract protected void onSettingsChanged(AbstractSettings newSettings);

	private void changeSettings(AbstractSettings newSettings) {
		if (settings != newSettings) {
			if (settings != null) {
				settings.deleteObserver(this);
			}
			if (newSettings != null) {
				newSettings.addObserver(this);
			}
			settings = newSettings;
			onSettingsChanged(settings);
		}
	}

	/**
	 * Override this when neither ArduinoProject, nor Settings is trapped by
	 * update().
	 *
	 * @param o
	 * @param arg
	 */
	public void updateOther(Observable o, Object arg) {
	}
;
}
