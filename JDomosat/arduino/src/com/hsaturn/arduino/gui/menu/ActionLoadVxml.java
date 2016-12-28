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
		category = "Edit",
		id = "com.hsaturn.arduino.gui.menu.ActionLoadVxml"
)
@ActionRegistration(
		iconBase = "com/hsaturn/arduino/gui/menu/images/folder_favorite.png",
		displayName = "#CTL_ActionLoadVxml"
)
@ActionReference(path = "Toolbars/File", position = 200)
@Messages("CTL_ActionLoadVxml=Load v.xml")
public final class ActionLoadVxml implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		String sName = "/home/hsaturn/v.xml";
		System.out.println("loading project from file:" + sName);

		ArduinoProject project;
		project = ArduinoProject.createFromXml(sName);
		if (project != null) {
			ProjectList.getInstance().setCurrentProject(project);
		}
	}
}
