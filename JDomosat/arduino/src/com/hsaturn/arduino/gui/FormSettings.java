/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui;

import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.document.ProjectList;
import com.hsaturn.arduino.gui.settings.MacrosEditor;
import com.hsaturn.arduino.settings.AbstractSettingsArray;
import com.hsaturn.arduino.gui.settings.CommSettingsEditor;
import com.hsaturn.arduino.gui.settings.AbstractSettingsTab;
import com.hsaturn.arduino.gui.settings.RulesEditor;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
		dtd = "-//com.hsaturn.arduino.gui//Settings//EN",
		autostore = false
)
@TopComponent.Description(
		preferredID = "SettingsTopComponent",
		//iconBase="SET/PATH/TO/ICON/HERE", 
		persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "properties", openAtStartup = false)
@ActionID(category = "Window", id = "com.hsaturn.arduino.gui.SettingsTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
		displayName = "#CTL_SettingsAction",
		preferredID = "SettingsTopComponent"
)
@Messages({
	"CTL_SettingsAction=Settings",
	"CTL_SettingsTopComponent=Settings Window",
	"HINT_SettingsTopComponent=This is a Settings window"
})
public final class FormSettings extends TopComponent implements Observer {

	private static final List<AbstractSettingsTab> lstSettingsTabs = new ArrayList<AbstractSettingsTab>();

	public FormSettings() {
		initComponents();
		setName(Bundle.CTL_SettingsTopComponent());
		setToolTipText(Bundle.HINT_SettingsTopComponent());
		putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
		putClientProperty(TopComponent.PROP_SLIDING_DISABLED, Boolean.TRUE);
		ProjectList.getInstance().addObserver(this);
		
		// FIXME J'aimerais que ce soit automatique (static initializer de CommSettingsInterface
		FormSettings.registerSettingsInterface((AbstractSettingsTab) new CommSettingsEditor());
		FormSettings.registerSettingsInterface((AbstractSettingsTab) new MacrosEditor());
		FormSettings.registerSettingsInterface((AbstractSettingsTab) new RulesEditor());

		buildInterface();
	}

	private void buildInterface() {
		if (null != lstSettingsTabs) {
			for (AbstractSettingsTab gui : lstSettingsTabs) {
				panSettings.addTab(gui.getTitle(), gui);
			}
		}
		else
		{
			System.out.println("WARNING: lstSettingsInterface is null");
		}
	}

	static public void registerSettingsInterface(AbstractSettingsTab settingsTab) {
		if (settingsTab != null) {
			lstSettingsTabs.add(settingsTab);
			System.out.println("FormSettings : Registering settings interface");
		} else {
			System.out.println("Cannot register null");
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panSettings = new javax.swing.JTabbedPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane panSettings;
    // End of variables declaration//GEN-END:variables
	@Override
	public void componentOpened() {
		// TODO add custom code on component opening
	}

	@Override
	public void componentClosed() {
		// TODO add custom code on component closing
	}

	void writeProperties(java.util.Properties p) {
		// better to version settings since initial version as advocated at
		// http://wiki.apidesign.org/wiki/PropertyFiles
		p.setProperty("version", "1.0");
		// TODO store your settings
	}

	void readProperties(java.util.Properties p) {
		String version = p.getProperty("version");
		// TODO read your settings according to their version
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof ArduinoProject) {
			// Reload settings with the project
		}
	}

}
