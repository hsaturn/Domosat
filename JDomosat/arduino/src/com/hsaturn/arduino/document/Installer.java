/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.document;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

public class Installer extends ModuleInstall {
	@Override
	public void restored() {
		/*try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Exceptions.printStackTrace(ex);
		}*/
           
		System.out.println("RESTORING APPLICATION");
				//new ActionNewProject().actionPerformed(null);
	}
	
	@Override
	public boolean closing()
	{
		System.out.println("CLOSING APPLICATION");
		ProjectList.close();
		return true;
	}
	
}
