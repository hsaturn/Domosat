/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.settings;

import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.settings.CommSettings;
import com.hsaturn.arduino.settings.AbstractSettingsArray;
import com.hsaturn.arduino.gui.MessageConsole;
import com.hsaturn.arduino.settings.AbstractSettings;
import jssc.SerialPortList;

/**
 *
 * @author hsaturn
 */
public class CommSettingsEditor extends AbstractSettingsTab {

	private CommSettings settings;
	private MessageConsole msg;

	/**
	 * Creates new form GuiCommSettings
	 */
	public CommSettingsEditor() {
		super();
		initComponents();
		refreshPortList();
	}

	public void refreshPortList() {
		cmbDev.removeAllItems();
		String[] portNames = SerialPortList.getPortNames();
		for (String s : portNames) {
			cmbDev.addItem(s);
		}
		
		lblCommPort.setEnabled(cmbDev.getItemCount()>0);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbSpeed = new javax.swing.JComboBox<Integer>();
        lblSpeed = new javax.swing.JLabel();
        lblCommPort = new javax.swing.JLabel();
        cmbDev = new javax.swing.JComboBox<String>();
        lblBits = new javax.swing.JLabel();
        cmbBits = new javax.swing.JComboBox<Integer>();
        lblStopBits = new javax.swing.JLabel();
        cmbStopBits = new javax.swing.JComboBox<Integer>();
        lblParity = new javax.swing.JLabel();
        cmbParity = new javax.swing.JComboBox<String>();
        lblProjectName = new javax.swing.JLabel();
        chkEcho = new javax.swing.JCheckBox();
        btnReloadDevicesList = new javax.swing.JButton();

        cmbSpeed.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        cmbSpeed.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "9600", "19200", "57600", "115200" }));
        cmbSpeed.setToolTipText(org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.cmbSpeed.toolTipText")); // NOI18N
        cmbSpeed.setName(""); // NOI18N
        cmbSpeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSpeedActionPerformed(evt);
            }
        });

        lblSpeed.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblSpeed, org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.lblSpeed.text")); // NOI18N

        lblCommPort.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblCommPort, org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.lblCommPort.text")); // NOI18N

        cmbDev.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        cmbDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDevActionPerformed(evt);
            }
        });

        lblBits.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblBits, org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.lblBits.text")); // NOI18N

        cmbBits.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        cmbBits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8", "7" }));
        cmbBits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBitsActionPerformed(evt);
            }
        });

        lblStopBits.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblStopBits, org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.lblStopBits.text")); // NOI18N

        cmbStopBits.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        cmbStopBits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2" }));
        cmbStopBits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStopBitsActionPerformed(evt);
            }
        });

        lblParity.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblParity, org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.lblParity.text")); // NOI18N

        cmbParity.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        cmbParity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "None", "Odd", "Even" }));
        cmbParity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbParityActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(lblProjectName, org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.lblProjectName.text")); // NOI18N

        chkEcho.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(chkEcho, org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.chkEcho.text")); // NOI18N
        chkEcho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEchoActionPerformed(evt);
            }
        });

        btnReloadDevicesList.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnReloadDevicesList, org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.btnReloadDevicesList.text")); // NOI18N
        btnReloadDevicesList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadDevicesListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProjectName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSpeed)
                    .addComponent(lblBits)
                    .addComponent(lblCommPort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDev, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbSpeed, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkEcho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReloadDevicesList, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbBits, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblStopBits)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbStopBits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(lblParity)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbParity, 0, 62, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblProjectName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCommPort)
                    .addComponent(cmbDev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSpeed))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBits)
                    .addComponent(lblStopBits)
                    .addComponent(cmbStopBits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblParity)
                    .addComponent(cmbParity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkEcho)
                    .addComponent(btnReloadDevicesList, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbSpeed.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.cmbSpeed.AccessibleContext.accessibleName")); // NOI18N
        cmbSpeed.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CommSettingsEditor.class, "CommSettingsEditor.cmbSpeed.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSpeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSpeedActionPerformed
		if (settings != null) {
			settings.setSpeed(Integer.parseInt(cmbSpeed.getSelectedItem().toString()));
		}
    }//GEN-LAST:event_cmbSpeedActionPerformed

    private void cmbDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDevActionPerformed
		if (settings != null) {
			if (cmbDev.getSelectedItem() != null) {
				settings.setCommPort(cmbDev.getSelectedItem().toString());
			}
		}
    }//GEN-LAST:event_cmbDevActionPerformed

    private void cmbBitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBitsActionPerformed
		if (settings != null) {
			settings.setBits(Integer.parseInt(cmbBits.getSelectedItem().toString()));
		}
    }//GEN-LAST:event_cmbBitsActionPerformed

    private void cmbStopBitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStopBitsActionPerformed
		if (settings != null) {
			settings.setStopBits(cmbStopBits.getSelectedIndex());
		}
    }//GEN-LAST:event_cmbStopBitsActionPerformed

    private void cmbParityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbParityActionPerformed
		if (settings != null) {
			settings.setParity(cmbParity.getSelectedIndex());
		}
    }//GEN-LAST:event_cmbParityActionPerformed

    private void chkEchoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEchoActionPerformed
		if (settings != null) {
			settings.setEcho(chkEcho.isSelected());
		}
    }//GEN-LAST:event_chkEchoActionPerformed

    private void btnReloadDevicesListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadDevicesListActionPerformed
		refreshPortList();
    }//GEN-LAST:event_btnReloadDevicesListActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReloadDevicesList;
    private javax.swing.JCheckBox chkEcho;
    private javax.swing.JComboBox<Integer> cmbBits;
    private javax.swing.JComboBox<String> cmbDev;
    private javax.swing.JComboBox<String> cmbParity;
    private javax.swing.JComboBox<Integer> cmbSpeed;
    private javax.swing.JComboBox<Integer> cmbStopBits;
    private javax.swing.JLabel lblBits;
    private javax.swing.JLabel lblCommPort;
    private javax.swing.JLabel lblParity;
    private javax.swing.JLabel lblProjectName;
    private javax.swing.JLabel lblSpeed;
    private javax.swing.JLabel lblStopBits;
    // End of variables declaration//GEN-END:variables

	private void loadSettings(CommSettings set) {
		if (settings != null) {
			settings.deleteObserver(this);
		}
		settings = set;
		if (settings != null) {
			cmbSpeed.setSelectedItem(Integer.toString(settings.getSpeeed()));
			cmbDev.setSelectedItem(settings.getDevice());
			cmbBits.setSelectedItem(settings.bits());
			cmbParity.setSelectedIndex(settings.parity());
			cmbStopBits.setSelectedIndex(settings.stop_bits());
			System.out.println("GuiCommSettings, Settings loaded");
		}
	}

	@Override
	public String getTitle() {
		return "Port";
	}

	@Override
	protected void onProjectChanged(ArduinoProject project) {
		System.out.println("GuiCommSettings: Main project has changed.");
		lblProjectName.setText("Settings of " + project.name());
		CommSettings object;
		object = (CommSettings) project.getSettings("CommSettings");
		if (object != null) {
			loadSettings( object);
		} else {
			loadSettings(null);
			System.out.println("GuiCommSettings: Unable to find CommSettings in ArduinoProject");
		}
	}

	@Override
	public String getSettingsClass() {
		return "CommSettings";
	}

	/**
	 *
	 * @param newSettings
	 */
	@Override
	protected void onSettingsChanged(AbstractSettings newSettings) {
		loadSettings((CommSettings) newSettings);
	}

}