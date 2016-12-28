/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.widgets.large;

import com.hsaturn.arduino.hardware.Byte;
import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author hsaturn
 */
class ByteCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	private final BytePanel renderer;
	private final BytePanel editor;

	public ByteCellEditorRenderer() {
		this.editor = new BytePanel();
		this.renderer = new BytePanel();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof Byte) {
			renderer.setByte((Byte) value);
			return renderer;
		}
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value instanceof Byte) {
			editor.setByte((Byte) value);
			return editor;
		}
		return null;
	}

	@Override
	public Object getCellEditorValue() {
		return editor.getByte();
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
	}

}

class BytePanel extends JTextField {

	private Byte value;
	private String old;

	public BytePanel() {
		super();
		setBorder(null);
	}

	public void setByte(Byte b) {
		if (b == null) {
			b = new Byte();
		}
		value = b;
		old = b.toString();

		setText(b.toString());
		switch (b.state()) {
			case Byte.READ:
				setForeground(Color.black);
				break;
			case Byte.WROTE:
				setForeground(Color.blue);
				break;
			case Byte.UNVERIFIED:
				setForeground(Color.red);
				break;
		}
		selectAll();
	}

	public Byte getByte() {
		if (!old.equals(getText())) {
			value.updateUnsigned(getText(), Byte.UNVERIFIED);
		}
		return value;
		//return new Byte(getText(), Byte.UNVERIFIED);
	}
}
