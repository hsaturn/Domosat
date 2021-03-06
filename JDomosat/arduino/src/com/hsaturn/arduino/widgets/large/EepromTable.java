/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.widgets.large;

import com.hsaturn.arduino.hardware.Byte;
import com.hsaturn.arduino.hardware.EepromPart;
import com.hsaturn.arduino.hardware.Module;
import com.hsaturn.arduino.message.Message;
import com.hsaturn.arduino.widgets.Widget;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hsaturn
 */
public class EepromTable extends Widget {

	EepromPart eeprom;
	int[] rowClicked;

	/**
	 * Creates new form Eeprom
	 */
	public EepromTable(Module module) {
		super(module);
		if (!(module instanceof EepromPart)) {
			System.err.println("ERROR: EepromTable called with a bad module.");
		}
		eeprom = (EepromPart) module;

		rowClicked = new int[256];
		initComponents();
		try {
			tblEeprom.setModel(new BytesTableModel(module, 16));
		} catch (BytesTableModelException ex) {
			System.err.println("Unable to build EepromTable");
			tblEeprom.setModel(new DefaultTableModel());
		}
		tblEeprom.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (rowClicked[e.getFirstIndex()] == 0) {
					rowClicked[e.getFirstIndex()] = 1;
					eeprom.read(e.getFirstIndex() * 16, 16);
				}
			}
		});

		ByteCellEditorRenderer editor = new ByteCellEditorRenderer();
		tblEeprom.setDefaultRenderer(Byte.class, editor);
		tblEeprom.setDefaultEditor(Byte.class, editor);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEeprom = new javax.swing.JTable();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(EepromTable.class, "EepromTable.jLabel1.text")); // NOI18N

        tblEeprom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Offset", "0", "1", "2", "3", "4", "5", "6", "7", " ", "9", "A", "B", "C", "D", "E", "F"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEeprom.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        tblEeprom.setShowHorizontalLines(false);
        tblEeprom.setShowVerticalLines(false);
        tblEeprom.setUpdateSelectionOnSort(false);
        tblEeprom.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(tblEeprom);
        if (tblEeprom.getColumnModel().getColumnCount() > 0) {
            tblEeprom.getColumnModel().getColumn(0).setResizable(false);
            tblEeprom.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblEeprom.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "EepromTable.tblEeprom.columnModel.title0")); // NOI18N
            tblEeprom.getColumnModel().getColumn(1).setResizable(false);
            tblEeprom.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title2")); // NOI18N
            tblEeprom.getColumnModel().getColumn(2).setResizable(false);
            tblEeprom.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title3")); // NOI18N
            tblEeprom.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title4")); // NOI18N
            tblEeprom.getColumnModel().getColumn(4).setResizable(false);
            tblEeprom.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title5")); // NOI18N
            tblEeprom.getColumnModel().getColumn(5).setResizable(false);
            tblEeprom.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title6")); // NOI18N
            tblEeprom.getColumnModel().getColumn(6).setResizable(false);
            tblEeprom.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title7")); // NOI18N
            tblEeprom.getColumnModel().getColumn(7).setResizable(false);
            tblEeprom.getColumnModel().getColumn(7).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title8")); // NOI18N
            tblEeprom.getColumnModel().getColumn(8).setResizable(false);
            tblEeprom.getColumnModel().getColumn(8).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title9")); // NOI18N
            tblEeprom.getColumnModel().getColumn(9).setResizable(false);
            tblEeprom.getColumnModel().getColumn(9).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title10")); // NOI18N
            tblEeprom.getColumnModel().getColumn(10).setResizable(false);
            tblEeprom.getColumnModel().getColumn(10).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title11")); // NOI18N
            tblEeprom.getColumnModel().getColumn(11).setResizable(false);
            tblEeprom.getColumnModel().getColumn(11).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title12")); // NOI18N
            tblEeprom.getColumnModel().getColumn(12).setResizable(false);
            tblEeprom.getColumnModel().getColumn(12).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title13")); // NOI18N
            tblEeprom.getColumnModel().getColumn(13).setResizable(false);
            tblEeprom.getColumnModel().getColumn(13).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title14")); // NOI18N
            tblEeprom.getColumnModel().getColumn(14).setResizable(false);
            tblEeprom.getColumnModel().getColumn(14).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title15")); // NOI18N
            tblEeprom.getColumnModel().getColumn(15).setResizable(false);
            tblEeprom.getColumnModel().getColumn(15).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title16")); // NOI18N
            tblEeprom.getColumnModel().getColumn(16).setResizable(false);
            tblEeprom.getColumnModel().getColumn(16).setHeaderValue(org.openide.util.NbBundle.getMessage(EepromTable.class, "Eeprom.jTable1.columnModel.title17")); // NOI18N
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addContainerGap(408, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(263, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEeprom;
    // End of variables declaration//GEN-END:variables

	@Override
	protected void onMessage(Message msg) {
		tblEeprom.repaint();
	}
}
