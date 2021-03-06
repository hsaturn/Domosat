/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui;

import com.hsaturn.arduino.hardware.BytesIncomingEvent;
import com.hsaturn.arduino.document.ArduinoProject;
import com.hsaturn.arduino.settings.CommSettings;
import com.hsaturn.arduino.document.Macro;
import com.hsaturn.arduino.hardware.Arduino;
import com.hsaturn.arduino.settings.MacroSettings;
import com.hsaturn.serial.SerialPort;
import com.hsaturn.serial.SerialPortEvent;
import com.hsaturn.serial.SerialPortEventListener;
import java.awt.event.ActionEvent;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jssc.SerialPortException;
import org.openide.util.Exceptions;

/**
 *
 * @author hsaturn
 */
public final class FormConsole extends javax.swing.JPanel implements Observer, DocumentListener, CaretListener, SerialPortEventListener {

	transient SerialPort serialPort;
	transient boolean bEcho = true;
	transient private boolean bPreventSend = false;
	transient private int currentCaret = 0;
	transient private boolean bRecurse = false;
	transient private Arduino arduino;
	transient private boolean lastOpenStatus = false;

	private void setMacro(Macro m) {
		txtSerial.getInputMap().put(KeyStroke.getKeyStroke(m.key), m.key);
		txtSerial.getActionMap().put(m.key, new MacroSendAction(m.macro));
		m.addObserver(this);
	}

	private void bytesIncoming(BytesIncomingEvent evt) {
		try {
			bPreventSend = true;
			txtSerial.append(evt.data);
			moveCaretToEnd();
		} catch (Exception ex) {
			setError(ex.getMessage());
		}
		updateStatus();
		bPreventSend = false;
	}

	private void updateStatus() {
		boolean currentStatus = lastOpenStatus;
		if (serialPort != null) {
			lastOpenStatus = serialPort.isOpened();
		} else {
			currentStatus = false;
		}
		if (currentStatus != lastOpenStatus) {
			String image;
			if (lastOpenStatus) {
				image = "user-online-16.png";
			} else {
				image = "user-offline-16.png";
			}
			lblConnectStatus.setIcon(new ImageIcon(getClass().getResource("/com/hsaturn/arduino/gui/menu/images/" + image)));
		}
	}

	private void toggleConnect() {
		if (serialPort != null) {
			try {
				if (serialPort.isOpened()) {
					serialPort.closePort();
				} else {
					serialPort.openPort();
				}
			} catch (SerialPortException ex) {
				Exceptions.printStackTrace(ex);
			}
		}
	}

	private void setSerialPort(SerialPort serialPort) {
		if (this.serialPort != null) {
			this.serialPort.removeListener(this);
		}
		this.serialPort = serialPort;
		if (this.serialPort != null) {
			this.serialPort.addEventListener(this);
		}
	}

	@Override
	public void serialEvent(SerialPortEvent spe) {
		if (spe.isOpen() || spe.isClose())
			updateStatus();
	}

	class MacroSendAction extends AbstractAction {

		private final String sWhat;

		public MacroSendAction(String s) {
			sWhat = s;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			arduino.send(sWhat, true);
		}
	}

	public FormConsole() {
		initComponents();
		setError("");
		lblStatus.setText("");
	}

	/**
	 *
	 * Creates new form FormConsole
	 *
	 * @param project
	 */
	public void init(ArduinoProject project) {
		this.arduino = project.getArduino();

		arduino.addObserver(this);
		setSerialPort(project.getArduino().getSerialPort());
		txtSerial.getDocument().addDocumentListener(this);
		txtSerial.addCaretListener(this);

		MacroSettings macros = (MacroSettings) project.getSettings("MacroSettings"); // GRRR
		for (Macro m : macros.getValues()) {
			setMacro(m);
		}
		updateStatus();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof SerialPort) {
			setSerialPort((SerialPort) arg);
		} else if (arg instanceof BytesIncomingEvent) {
			bytesIncoming((BytesIncomingEvent) arg);
		} else if (arg instanceof CommSettings) {
			CommSettings comm = (CommSettings) arg;
			lblStatus.setText(comm.readableValues());
		} else if (arg instanceof Macro) {
			Macro m = (Macro) arg;
			setMacro(m);
		}
		updateStatus();
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
        txtSerial = new javax.swing.JTextArea();
        panStatusBar = new javax.swing.JPanel();
        lblStatusError = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblConnectStatus = new javax.swing.JLabel();

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        txtSerial.setBackground(new java.awt.Color(0, 0, 0));
        txtSerial.setColumns(20);
        txtSerial.setFont(new java.awt.Font("Liberation Mono", 0, 10)); // NOI18N
        txtSerial.setForeground(new java.awt.Color(0, 255, 0));
        txtSerial.setRows(5);
        txtSerial.setCaretColor(new java.awt.Color(51, 255, 0));
        txtSerial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtSerialMousePressed(evt);
            }
        });
        txtSerial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSerialKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtSerial);

        lblStatusError.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        lblStatusError.setForeground(new java.awt.Color(255, 0, 0));
        org.openide.awt.Mnemonics.setLocalizedText(lblStatusError, org.openide.util.NbBundle.getMessage(FormConsole.class, "FormConsole.lblStatusError.text")); // NOI18N
        lblStatusError.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lblStatusError.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStatusErrorMouseClicked(evt);
            }
        });

        lblStatus.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblStatus, org.openide.util.NbBundle.getMessage(FormConsole.class, "FormConsole.lblStatus.text")); // NOI18N
        lblStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblConnectStatus.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        lblConnectStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConnectStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/hsaturn/arduino/gui/menu/images/user-offline-16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblConnectStatus, org.openide.util.NbBundle.getMessage(FormConsole.class, "FormConsole.lblConnectStatus.text")); // NOI18N
        lblConnectStatus.setToolTipText(org.openide.util.NbBundle.getMessage(FormConsole.class, "FormConsole.lblConnectStatus.toolTipText")); // NOI18N
        lblConnectStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lblConnectStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConnectStatusMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panStatusBarLayout = new javax.swing.GroupLayout(panStatusBar);
        panStatusBar.setLayout(panStatusBarLayout);
        panStatusBarLayout.setHorizontalGroup(
            panStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panStatusBarLayout.createSequentialGroup()
                .addComponent(lblStatusError, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConnectStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panStatusBarLayout.setVerticalGroup(
            panStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panStatusBarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblStatusError, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(lblConnectStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSerialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSerialKeyPressed

    }//GEN-LAST:event_txtSerialKeyPressed

    private void txtSerialMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSerialMousePressed
		moveCaretToEnd();        // TODO add your handling code here:
    }//GEN-LAST:event_txtSerialMousePressed

    private void lblStatusErrorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStatusErrorMouseClicked
		setError("");
    }//GEN-LAST:event_lblStatusErrorMouseClicked

    private void lblConnectStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConnectStatusMouseClicked
		toggleConnect();        // TODO add your handling code here:
    }//GEN-LAST:event_lblConnectStatusMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConnectStatus;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusError;
    private javax.swing.JPanel panStatusBar;
    private javax.swing.JTextArea txtSerial;
    // End of variables declaration//GEN-END:variables

	private void setError(String string) {
		lblStatusError.setText(string);
	}

	private void moveCaretToEnd() {
		txtSerial.setCaretPosition(txtSerial.getText().length());
		currentCaret = txtSerial.getCaretPosition();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		change(e, "insert");
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		change(e, "update");
	}

	private void change(DocumentEvent e, String sType) {
		if (bPreventSend == false) {
			if (serialPort != null && serialPort.isOpened()) {
				try {
					String s = "";
					for (int i = currentCaret; i < txtSerial.getText().length(); i++) {
						s += txtSerial.getText().charAt(i);
					}
					serialPort.writeString(s);
				} catch (SerialPortException ex) {
					Exceptions.printStackTrace(ex);
				}

			} else {
				setError("SerialPort is null or not opened");
			}
		}

		moveCaretToEnd();
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (bRecurse == false) {
			bRecurse = true;
			moveCaretToEnd();
			bRecurse = false;
		}
	}

}
