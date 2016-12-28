/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.widgets.small;

import com.hsaturn.arduino.hardware.EepromPart;
import com.hsaturn.arduino.hardware.Module;
import com.hsaturn.arduino.message.Message;
import com.hsaturn.arduino.widgets.Widget;

/**
 * private final int iRowsAbove =4; private final int iRowsBelow = 4;
 *
 * public Disassembler(Module module) { super(module); }
 *
 * @Override protected void onMessage(Message msg) { {
 *
 * }
 * }
 * }
 * }
 * @author hsaturn
 */
public class Disassembler extends Widget {

	public EepromPart eeprom = null;

	/**
	 * Creates new form Disassembler
	 */
	public Disassembler(Module module) {
		super(module);
		initComponents();
		eeprom = module.arduino.eeprom;
		if (eeprom != null) {
			eeprom.addObserver(this);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstAsm = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();

        lstAsm.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 10)); // NOI18N
        jScrollPane1.setViewportView(lstAsm);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(Disassembler.class, "Disassembler.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstAsm;
    // End of variables declaration//GEN-END:variables

	@Override
	protected void onMessage(Message msg) {
		if (msg.name.equals("uprog_state")) {
			int pc = msg.get("pc").toInt();
			lstAsm.setModel(new ListModelProg(eeprom, pc, 4, 4));
			lstAsm.setSelectedIndex(4);
		}
	}
}