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
		category = "File",
		id = "com.hsaturn.arduino.gui.menu.ActionSaveProjectAs"
)
@ActionRegistration(
		iconBase = "com/hsaturn/arduino/gui/menu/images/filesaveas.png",
		displayName = "#CTL_ActionSaveProjectAs"
)
@ActionReferences({
	@ActionReference(path = "Menu/File", position = 1325),
	@ActionReference(path = "Toolbars/File", position = 350)
})
@Messages("CTL_ActionSaveProjectAs=Save Project As")
public final class ActionSaveProjectAs implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent ev) {

		ArduinoProject project;
		project = ProjectList.getInstance().getCurrentProject();
		if (project != null) {
			JFileChooser f = new JFileChooser();

			f.showSaveDialog(null);
			// String sName = ProjectList.getInstance().getNewDefaultName();
			String sName = f.getSelectedFile().getAbsolutePath();
			System.out.println("saving project to file:" + sName);
			
			project.saveToXml(sName);

		}		// TODO use context
	}
}
