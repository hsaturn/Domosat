/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui.settings;

import com.hsaturn.arduino.document.AbstractSetting;
import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.gui.settings.model.AbstractSettingsTableModel;
import com.hsaturn.arduino.settings.AbstractSettings;
import com.hsaturn.arduino.settings.AbstractSettingsArray;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author hsaturn
 * @param <SETTINGS>
 * @param <SETTING>
 */
public abstract class AbstractSettingsTableEditor<SETTINGS extends AbstractSettingsArray<SETTING>, SETTING extends AbstractSetting> extends AbstractSettingsTab {

	Class<SETTINGS> settingsClass;
	JTable table;
	AbstractSettingsTableModel model;	// FIXME Utilité ??? (table.getModel()) ?

	protected AbstractSettingsTableEditor(Class clazz) {
		settingsClass = clazz;
		initComponents();
	}

	private void setModel(AbstractSettingsTableModel newModel) {
		// System.out.println("AbstractSettingsTableEditor : New model "+newModel);
		model = newModel;
		if (table == null) {
			table = new JTable(model); //model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//	TableColumnAdjuster tca = new TableColumnAdjuster(tblRules);
			//	tca.setDynamicAdjustment(true);
			panTable.add(new JScrollPane(table));
		}
		else
		{
			table.setModel(newModel);
		}
		
		// Update buttons
		if (model != null)
		{
			btnNew.setVisible(model.getSettings().canAdd());
			btnRemove.setVisible(model.getSettings().canRemove());
			btnEdit.setVisible(model.getSettings().hasCustomEditForm());
		}
	}

	/**
	 * Open the editor for the setting
	 *
	 * @param r
	 */
	protected abstract void openEditor(SETTING r);

	private SETTING getSelectedSetting() {
		return (SETTING) model.get(table.getSelectedRow());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        panTable = new javax.swing.JPanel();

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(AbstractSettingsTableEditor.class, "AbstractSettingsTableEditor.jButton1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnNew, org.openide.util.NbBundle.getMessage(AbstractSettingsTableEditor.class, "AbstractSettingsTableEditor.btnNew.text")); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnRemove, org.openide.util.NbBundle.getMessage(AbstractSettingsTableEditor.class, "AbstractSettingsTableEditor.btnRemove.text")); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnEdit, org.openide.util.NbBundle.getMessage(AbstractSettingsTableEditor.class, "AbstractSettingsTableEditor.btnEdit.text")); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        panTable.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panTable, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove))
            .addComponent(panTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
		if (table.getSelectedRow() != -1) {
			model.removeRow(table.getSelectedRow());
		}
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
		if (model == null){
			System.err.println("Cannot add setting to null model.");
			return;
		}
		SETTING r = createNewSetting();
		model.add(r);
		openEditor(r);
    }//GEN-LAST:event_btnNewActionPerformed


    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
		SETTING r = getSelectedSetting();
		openEditor(r);
    }//GEN-LAST:event_btnEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel panTable;
    // End of variables declaration//GEN-END:variables

	@Override
	final public String getSettingsClass() {
		return settingsClass.toString();
	}

	@Override
	protected void onProjectChanged(ArduinoProject project) {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onSettingsChanged(AbstractSettings settings) {

		SETTINGS t = null;
		if (settingsClass.isInstance(settings)) {
			t = (SETTINGS) settings;
		} else {
			System.err.println("Bad settings, should be "+settingsClass.toString()+" got a "+settings.getClass().toString());
		}
		setModel(createNewModel(t));
		setEnabled(t != null);
	}

	protected abstract SETTING createNewSetting();

	protected abstract AbstractSettingsTableModel createNewModel(SETTINGS t);

}
