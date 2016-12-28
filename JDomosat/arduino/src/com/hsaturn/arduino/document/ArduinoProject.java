package com.hsaturn.arduino.document;

import com.hsaturn.arduino.hardware.Arduino;
import com.hsaturn.arduino.hardware.ModuleClock;
import com.hsaturn.arduino.hardware.ModuleDht;
import com.hsaturn.arduino.settings.AbstractSettings;
import com.hsaturn.arduino.settings.CommSettings;
import com.hsaturn.arduino.settings.MacroSettings;
import com.hsaturn.utils.PropertyBag;
import com.hsaturn.arduino.settings.RulesSettings;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import java.util.Properties;
import org.openide.util.Exceptions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Les observers constateront que le projet courant a changé.
 *
 * @author hsaturn
 */
public class ArduinoProject extends Observable implements Observer, Serializable {

	private String name;
	private String filename;
	private String description;
transient private Arduino arduino = null;
	private final List<AbstractSettings> settings;
	transient private PropertyBag mProperties;
	transient private boolean bResolving;

	public ArduinoProject(String sProjectFile) {
		this.settings = null;
		createFromXml(sProjectFile);
	}

	@Override
	public void finalize() {
		try {
			System.err.println("C'EST QUOI CE MERDIER !!!!!!!!!!!!!!!!!!!!!!!!");
		} finally {
			try {
				super.finalize();
			} catch (Throwable ex) {
				Exceptions.printStackTrace(ex);
			}
		}
	}

	/**
	 * Arduino physique utilisé par le projet
	 *
	 * @return
	 */
	public Arduino getArduino() {
		if (arduino == null) {
			arduino = new Arduino();
		}
		arduino.init(this);
		return arduino;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Object readResolve() {
		bResolving = true;
		if (settings != null) {
			for (AbstractSettings settings : this.settings) {
				settings.addObserver(this);
			}
		}
		getArduino();
		bResolving = false;
		return this;
	}

	public String name() {
		if (name == null) {
			return filename;	// @TODO
		}
		return name;
	}

	static public ArduinoProject createFromXml(String sXmlFile) {
		try {
			XStream xs = getXStream();
			ArduinoProject project = (ArduinoProject) xs.fromXML(new FileReader(sXmlFile));
			project.filename = sXmlFile;
			ProjectList.getInstance().addProject(project);
			return project;
		} catch (FileNotFoundException ex) {
			Exceptions.printStackTrace(ex);
		}
		return null;
	}

	public String getFileName() {
		return filename;
	}

	public void close() {
		saveToXml(filename);
	}

	public Properties getProperties() {
		return mProperties;
	}

	public String getProperty(String sName) {
		return mProperties.getProperty(sName);
	}

	public String getProperty(String sName, String sDefault) {
		String s = getProperty(sName);
		if (s.length() == 0) {
			return sDefault;
		}
		return s;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @FIXME use Class instead of String !!!
	 * @param sClass
	 * @return
	 */
	public AbstractSettings getSettings(String sClass) {
		for (AbstractSettings one_settings : this.settings) {
			if (one_settings.getClass().toString().endsWith(sClass)) {
				return one_settings;
			}
		}
		return null;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!bResolving) {
			setChanged();
			notifyObservers(arg);	// @FIXME Macros.notifyObservers(Macro m)	-> il faut notifier avec arg
			notifyObservers(o);		// @FIXME ...alors que CommSettings.nofifyObservers()	-> il faut notifier avec o
		}
	}

	static private XStream getXStream() {
		XStream xs = new XStream(new DomDriver());
		xs.alias("project", ArduinoProject.class);
		xs.alias("rules", RulesSettings.class);
		xs.alias("comm", CommSettings.class);
		xs.alias("rule", Rule.class);
		xs.alias("macro", Macro.class);
		xs.alias("macros", MacroSettings.class);
		xs.alias("module_dht", ModuleDht.class);
		xs.alias("module_clock", ModuleClock.class);
		xs.omitField(Observable.class, "obs");
		xs.omitField(Observable.class, "changed");
		xs.aliasField("port", CommSettings.class, "comm_port");
		xs.aliasField("stops", CommSettings.class, "stop_bits");
		xs.aliasAttribute(Macro.class, "key", "key");
		xs.aliasAttribute(Rule.class, "rule_name", "name");
		xs.aliasAttribute(Rule.class, "size", "bytes-count");
		xs.aliasAttribute(Rule.class, "valid", "valid");
		xs.aliasAttribute(Rule.class, "hexa", "hexa");
		return xs;
	}

	public boolean saveToXml(String sName) {
		try {
			PrintWriter out = new PrintWriter(sName);
			out.print(getXStream().toXML(this));
			out.close();
			System.out.println("Project saved to :" + sName);
			return true;
		} catch (FileNotFoundException ex) {
			Exceptions.printStackTrace(ex);
		}
		return false;
	}

}
