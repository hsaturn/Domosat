/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.widgets;

import com.hsaturn.arduino.hardware.Module;
import com.hsaturn.arduino.message.Message;
import com.hsaturn.arduino.message.Value;
import com.hsaturn.arduino.widgets.large.EepromTable;
import com.hsaturn.arduino.widgets.small.Clock;
import com.hsaturn.arduino.widgets.small.Disassembler;
import com.hsaturn.arduino.widgets.small.Stack;
import com.hsaturn.arduino.widgets.small.Temperature;
import com.hsaturn.arduino.widgets.small.UProg;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * Contient un couple Dimension / JPanel pour faciliter le traitement des
 * positionnement des panels dans ArduinoHui
 *
 * @author hsaturn
 */
public abstract class Widget extends javax.swing.JPanel implements Observer {

	public Rectangle rect = new Rectangle();		// Rectangle dans lequel est positionné le JPanel
	public boolean positionned = false;				// Is the panel positionned ?
	public String mod = "";							// Module letter
	transient protected Module module = null;

	public Widget(Module module) {
		super();
		setName(module.name());
		this.module = module;
		if (module != null) {
			module.addObserver(this);
		}
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	public static Widget[] factory(Module m) {
		switch (m.getModuleIdentifier()) {
			case "C":
				return new Widget[]{new Clock(m)};
			case "T":
				return new Widget[]{new Temperature(m)};
			case "P":
				return new Widget[]{new Stack(m), new UProg(m), new Disassembler(m)};
			case "O":
				return new Widget[]{new EepromTable(m)};
			default:
				System.out.println("INTO: No widget for module " + m.name()+" ("+m.getModuleIdentifier()+")");
		}
		return new Widget[]{};
	}

	public Dimension getDimension() {
		return getPreferredSize();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Message) {
			Message msg = (Message) arg;
			onMessage(msg);
		}
	}

	abstract protected void onMessage(Message msg);

}
