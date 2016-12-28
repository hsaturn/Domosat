/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.settings.model;

import com.hsaturn.arduino.document.AbstractSetting;
import com.hsaturn.arduino.settings.AbstractSettingsArray;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hsaturn
 * @param <SETTINGS>	Conteneur de TCLASS
 * @param <SETTING>	Classe héritée de Setting
 */
public abstract class AbstractSettingsTableModel<SETTINGS extends AbstractSettingsArray<SETTING>, SETTING extends AbstractSetting> extends AbstractTableModel implements Observer {

	private final Class settingsClass;
	private final Class settingClass;
	private SETTINGS settings;

	public AbstractSettingsTableModel(SETTINGS theSettings, Class class1, Class class2) {
		settingsClass = class1;
		settingClass = class2;
		setSettings(theSettings);
	}

	public final void setSettings(SETTINGS newSettings) {
		if (settings != null) {
			settings.deleteObserver(this);
		}
		settings = newSettings;
		if (settings != null) {
			settings.addObserver(this);
		} else {
			System.err.println("ERROR Rule : NULL SETTINGS");
		}
		fireTableStructureChanged();

	}

	@Override
	public int getRowCount() {
		if (settings == null) {
			return 0;
		}
		return settings.size();
	}

	@Override
	final public int getColumnCount() {
		return getColumnsName().length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex < getColumnCount()) {
			return getColumnsName()[columnIndex];
		}
		return "<noname>";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;	// @TODO
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (settings == null) {
			return false;
		}
		if (rowIndex >= getRowCount()) {
			return false;
		}
		return columnIndex < getColumnCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (settings == null) {
			return null;
		}
		if (rowIndex >= getRowCount()) {
			return null;
		}
		if (columnIndex >= getColumnCount()) {
			return null;
		}

		SETTING r = settings.get(rowIndex);
		if (r == null) {
			return "<null class>";
		}

		return getColumnOfSetting(r, columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (settings == null) {
			return;
		}
		if (rowIndex >= getRowCount()) {
			return;
		}
		if (columnIndex >= getColumnCount()) {
			return;
		}
		SETTING r = settings.get(rowIndex);
		String value = aValue.toString();
		setColumnOfSetting(r, value, columnIndex);

		fireTableRowsUpdated(rowIndex, rowIndex);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if (settingsClass.isInstance(o)) {
			SETTINGS sets = (SETTINGS) o;
			if (sets != null) {
				for (SETTING r : sets.getValues()) {
					r.addObserver(this);
				}
				fireTableStructureChanged();	// TODO Whole refresh
			}
		}
		if (settingClass.isInstance(arg)) {
			SETTING r = (SETTING) arg;
			if (arg != null) {
				int iRow = settings.getIndexOf(r);
				if (iRow != -1) {
					fireTableRowsUpdated(iRow, iRow);
				} else {
					System.out.println("SettingsTableModel received a msg from unknown ");
				}
			}
		}
	}

	public SETTING removeRow(int selectedRow) {
		if (settings == null) {
			return null;
		}

		return settings.remove(selectedRow);
	}

	public void add(SETTING r) {
		if (settings == null) {
			return;
		}
		if (settings.contains(r)) {
			return;
		}
		settings.add(r);
	}

	public SETTINGS getSettings() {
		return settings;
	}

	public SETTING get(int selectedRow) {
		return settings.get(selectedRow);
	}

	protected abstract String getColumnOfSetting(SETTING r, int columnIndex);

	protected abstract void setColumnOfSetting(SETTING r, String value, int columnIndex);

	public abstract String[] getColumnsName();

}
