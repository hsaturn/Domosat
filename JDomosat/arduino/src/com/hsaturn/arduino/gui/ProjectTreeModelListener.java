/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 *
 * @author hsaturn
 */
public class ProjectTreeModelListener implements TreeModelListener {

	
	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		System.out.println("treeNodesChanged");
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
	}
	
}
