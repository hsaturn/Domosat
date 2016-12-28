/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.menu;

import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.document.ProjectList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
		category = "File",
		id = "com.hsaturn.arduino.gui.menu.ActionNewProject"
)
@ActionRegistration(
		iconBase = "com/hsaturn/arduino/gui/menu/images/blockdevice.png",
		displayName = "#CTL_ActionNewProject"
)
@ActionReference(path = "Menu/File", position = 1100)
@Messages("CTL_ActionNewProject=New project")
public final class ActionNewProject implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser f = new JFileChooser();
		
		// f.showOpenDialog(null);

		String sName = ProjectList.getInstance().getNewDefaultName();
		String sProjectFile = "/home/hsaturn/"+sName+".arg";

		ArduinoProject p = new ArduinoProject(sProjectFile);

		ProjectList.getInstance().setCurrentProject(p);
	}
}
