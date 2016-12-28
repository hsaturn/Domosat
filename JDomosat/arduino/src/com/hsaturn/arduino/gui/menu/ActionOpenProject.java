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
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
		category = "Edit",
		id = "com.hsaturn.arduino.bin.ActionOpenProject"
)
@ActionRegistration(
		iconBase = "com/hsaturn/arduino/gui/menu/images/folder_blue_open.png",
		displayName = "#CTL_ActionOpenProject"
)
@ActionReferences({
	@ActionReference(path = "Menu/File", position = 1250),
	@ActionReference(path = "Toolbars/File", position = 300)
})
@Messages("CTL_ActionOpenProject=Open Project")
public final class ActionOpenProject implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser f = new JFileChooser();

		f.showOpenDialog(null);
		// String sName = ProjectList.getInstance().getNewDefaultName();
		String sName = f.getSelectedFile().getAbsolutePath();
		System.out.println("loading project from file:" + sName);

		ArduinoProject project;
		project = ArduinoProject.createFromXml(sName);
		if (project != null) {
			ProjectList.getInstance().setCurrentProject(project);
		}
	}
}
