/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.menu;

import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.document.ProjectList;
import com.hsaturn.arduino.gui.CustomView;
import com.hsaturn.arduino.gui.FormConsole;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import static org.openide.windows.TopComponent.PERSISTENCE_NEVER;

@ActionID(
		category = "Tools",
		id = "com.hsaturn.arduino.gui.menu.ActionOpenConsole"
)
@ActionRegistration(
		iconBase = "com/hsaturn/arduino/gui/menu/images/konsole.png",
		displayName = "#CTL_ActionOpenConsole"
)
@ActionReferences({
	@ActionReference(path = "Menu/Tools", position = 0),
	@ActionReference(path = "Toolbars/File", position = 375)
})
@Messages("CTL_ActionOpenConsole=Open Console")
public final class ActionOpenConsole implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ArduinoProject project = ProjectList.getInstance().getCurrentProject();

		if (project != null) {
			FormConsole con = new FormConsole();
			con.init(project);
			// ??? con.addFocusListener( new FocusListener(node.getProject()));
			CustomView win = new CustomView(PERSISTENCE_NEVER, project.name() + " console...");
			win.add(con);
			win.open();
		}
	}
}
