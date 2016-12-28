/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.settings;

import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import com.hsaturn.arduino.document.AbstractSetting;
import com.hsaturn.arduino.document.SettingFactory;
import java.util.ArrayList;

/**
 *
 * @author hsaturn
 */
public abstract class AbstractSettingsArray<SETTING extends AbstractSetting> extends AbstractSettings implements Observer {

	ArrayList<SETTING> settings;
	static transient private SettingFactory factory;

	private Object readResolve() {
		for(SETTING setting : settings)
		{
			AbstractSetting t = (AbstractSetting) setting;
			t.addObserver(this);
		}
		return this;
	}
/*
	AbstractSettingsArray(PropertyBag bag, SettingFactory theFactory) {
		super(bag);
		factory = theFactory;
		settings = new ArrayList<SETTING>();
		read(bag);
		checkIntegrity();
	}*/

	/**
	 * Concatenate more important settings to a human readable short form.
	 * Intented to be displayed, not to be parsed in any manner.
	 *
	 * @return String
	 */
	@Override
	public String readableValues() {
		return "no readable value";	// TODO
	}

	/**
	 * Name for saving/reloading settings from PropertyBag
	 *
	 * @return
	 */
	abstract public String name();

	final public int size() {
		if (settings == null) {
			return 0;

		}
		return settings.size();
	}

	// Setting manipulation
	final public int getIndexOf(SETTING instance) {
		if (settings == null) {
			return -1;
		}
		return settings.indexOf(instance);
	}

	final public SETTING get(int index) {
		if (index >= settings.size()) {
			return null;
		}
		return settings.get(index);
	}

	final public ArrayList<SETTING> getValues() {

		return settings;
	}

	final public boolean remove(SETTING instance) {
		if (canRemove() && settings != null) {
			return _remove(instance);
		}
		return false;
	}

	final public SETTING remove(int index) {
		if (canRemove()) {
			return settings.remove(index);
		}
		return null;
	}

	final public boolean contains(SETTING instance) {
		if (settings == null) {
			return false;
		}
		return settings.contains(instance);
	}

	final public void add(SETTING instance) {
		if (canAdd()) {
			_add(instance);
		}
	}

	final protected void _add(SETTING instance) {
		if (settings == null) {
			return;
		}
		settings.add(instance);
		setChanged();
		notifyObservers(instance);
	}

	/**
	 * Override this method to cancel add
	 *
	 */
	public boolean canAdd() {
		return true;
	}
	
	public boolean hasCustomEditForm()
	{
		return true;
	}

	/**
	 * Overrid this method to cancel remove
	 */
	public boolean canRemove() {
		return true;
	}

	protected final boolean _remove(SETTING instance) {
		settings.remove(instance);
		setChanged();
		notifyObservers(instance);
		return true;
	}

	public void checkIntegrity() {
	}

	@Override
	public final void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}

}
