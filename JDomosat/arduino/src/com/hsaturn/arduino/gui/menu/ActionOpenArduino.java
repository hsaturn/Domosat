/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.menu;

import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.document.ProjectList;
import com.hsaturn.arduino.gui.CustomView;
import com.hsaturn.arduino.gui.ArduinoGui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import static org.openide.windows.TopComponent.PERSISTENCE_NEVER;

@ActionID(
		category = "Tools",
		id = "com.arduino.harware.ActionOpenArduino"
)
@ActionRegistration(
		iconBase = "com/hsaturn/arduino/hardware/media-flash.png",
		displayName = "#CTL_ActionOpenArduino"
)
@ActionReference(path = "Toolbars/File", position = 387)
@Messages("CTL_ActionOpenArduino=Open Arduino")
public final class ActionOpenArduino implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ArduinoProject project = ProjectList.getInstance().getCurrentProject();

		if (project != null) {
			ArduinoGui gui = new ArduinoGui();
			gui.setArduino(project.getArduino());
			// ??? con.addFocusListener( new FocusListener(node.getProject()));
			CustomView win = new CustomView(PERSISTENCE_NEVER, project.getArduino().getName());
			win.add(gui);
			win.open();
		}
	}
}
