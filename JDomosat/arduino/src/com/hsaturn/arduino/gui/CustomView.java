/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import org.openide.windows.TopComponent;

/**
 *
 * @author hsaturn
 */
public class CustomView extends TopComponent {

	int miPersistenceType = PERSISTENCE_ALWAYS;

	public CustomView(int iPersistenceType, String title) {
		super();
		setLayout(new BorderLayout());
		setDisplayName(title);
	}

	@Override
	protected void componentActivated() {
	}

	@Override
	protected void componentDeactivated() {
	}

	@Override
	public int getPersistenceType() {
		return miPersistenceType;
	}

	@Override
	public void open() {
		super.open();
		requestActive();
	}
}
