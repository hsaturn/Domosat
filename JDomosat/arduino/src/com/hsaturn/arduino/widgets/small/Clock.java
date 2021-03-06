/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.widgets.small;

import com.hsaturn.arduino.hardware.Module;
import com.hsaturn.arduino.hardware.ModuleClock;
import com.hsaturn.arduino.message.ConverterHhmm;
import com.hsaturn.arduino.message.Message;
import com.hsaturn.arduino.message.Value;
import com.hsaturn.arduino.widgets.Widget;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hsaturn
 */
public class Clock extends Widget implements Observer {

	
	/**
	 * Creates new form ModuleClockMini
	 */
	public Clock(Module m) {
		super(m);
		initComponents();
		txtTime.setBackground(getBackground());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeure = new javax.swing.JLabel();
        lblJour = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        txtDow = new javax.swing.JTextField();

        lblHeure.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblHeure, org.openide.util.NbBundle.getMessage(Clock.class, "Clock.lblHeure.text_1")); // NOI18N

        lblJour.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblJour, org.openide.util.NbBundle.getMessage(Clock.class, "Clock.lblJour.text_1")); // NOI18N

        txtTime.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        txtTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTime.setText(org.openide.util.NbBundle.getMessage(Clock.class, "Clock.txtTime.text")); // NOI18N
        txtTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimeFocusLost(evt);
            }
        });
        txtTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeActionPerformed(evt);
            }
        });

        txtDow.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        txtDow.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDow.setText(org.openide.util.NbBundle.getMessage(Clock.class, "Clock.txtDow.text_1")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHeure, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblJour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDow)
                    .addComponent(txtTime)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHeure)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJour)
                    .addComponent(txtDow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeActionPerformed
			ConverterHhmm conv = new ConverterHhmm();
			
			String msg="m002C00"+conv.ConvertTo(txtTime.getText());
			if (module.arduino.send(msg,true))
				requestFocus();
    }//GEN-LAST:event_txtTimeActionPerformed

    private void txtTimeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimeFocusGained
		txtTime.setBackground(Color.white);
    }//GEN-LAST:event_txtTimeFocusGained

    private void txtTimeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimeFocusLost
		txtTime.setBackground(getBackground());       
    }//GEN-LAST:event_txtTimeFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblHeure;
    private javax.swing.JLabel lblJour;
    private javax.swing.JTextField txtDow;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables

	@Override
	protected void onMessage(Message msg) {
		switch (msg.name) {
			case "clock":
				// Ensure not editing
				if (!txtTime.getBackground().equals(Color.white)) {
					Value heure = msg.get("hhmm");
					txtTime.setText(heure.toString());
				}
				break;
			case "dow":
				Value dow = msg.get("dow");
				txtDow.setText(dow.toString());
				break;
		}

	}
}
