/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.widgets.large;

import com.hsaturn.arduino.hardware.Byte;
import com.hsaturn.arduino.hardware.EepromPart;
import com.hsaturn.arduino.hardware.Module;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hsaturn
 */
class BytesTableModel extends DefaultTableModel implements Observer {

	private int columns;
	private EepromPart eeprom;

	public BytesTableModel(Module module, int columns) throws BytesTableModelException {
		super();
		if (!(module instanceof EepromPart)) {
			throw new BytesTableModelException("BytesTableModel can only handle EepromPart object");
		}
		this.columns = columns;
		this.eeprom = (EepromPart) module;
		eeprom.addObserver(this);
	}

	@Override
	public int getRowCount() {
		if (eeprom == null) {
			return 0;
		}
		return eeprom.getBytes().length;
	}

	@Override
	public int getColumnCount() {
		return columns+2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Address";
		}
		if (columnIndex == 9) {
			return " ";
		}
		return Integer.toString(getByteColumn(columnIndex), 16);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (isColumnEditable(columnIndex)) {
			return Byte.class;
		}
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		System.out.println("BytesTableModel editable(" + rowIndex + "," + columnIndex + ")=" + inBounds(rowIndex, columnIndex));
		return inBounds(rowIndex, columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			int address = rowIndex * columns;
			return Integer.toString(address, 16);
		} else if (isColumnEditable(columnIndex)) {
			if (inBounds(rowIndex, columnIndex)) {
				return getByte(rowIndex, columnIndex);
			}
		}

		return "";
	}

	@Override
	public void setValueAt(Object hex, int rowIndex, int columnIndex) {
		if (hex instanceof String) {
			int index = getIndex(rowIndex, columnIndex);
			Byte[] bytes = eeprom.getBytes();
			bytes[index] = new Byte((String) hex, Byte.UNVERIFIED);
		} else if (hex instanceof Byte) {
			eeprom.fireChanged();
			System.out.println("********************************* ");
		}
	}

	private int getByteColumn(int columnIndex) {
		if (columnIndex <= 8) {
			return columnIndex - 1;
		}
		return columnIndex - 2;
	}

	private boolean inBounds(int rowIndex, int columnIndex) {
		if (isColumnEditable(columnIndex)) {
			return getIndex(rowIndex, columnIndex) <= eeprom.getBytes().length;
		}
		return false;
	}

	private int getIndex(int rowIndex, int columnIndex) {
		return this.columns * rowIndex + getByteColumn(columnIndex);
	}

	/**
	 * Check if a table column is editable
	 *
	 * @param columnIndex
	 * @return boolean
	 */
	private boolean isColumnEditable(int columnIndex) {
		return (columnIndex != 0) && (columnIndex != 9);
	}

	private Object getByte(int rowIndex, int columnIndex) {
		if (inBounds(rowIndex, columnIndex)) {
			Byte b = eeprom.getBytes()[getIndex(rowIndex, columnIndex)];
			return b;
		}
		System.err.println("ERROR : BytesTableModel has to create a dummy byte");
		return new Byte();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("WARNING: Better check arg for performance please");
		fireTableDataChanged();
	}

}
