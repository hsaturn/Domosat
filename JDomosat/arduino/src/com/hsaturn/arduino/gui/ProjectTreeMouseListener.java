/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui;

import com.hsaturn.arduino.document.ProjectList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

/**
 *
 * @author hsaturn
 */
public class ProjectTreeMouseListener implements MouseListener {

	private final JPopupMenu popup;

	public ProjectTreeMouseListener(JPopupMenu projectPopup) {
		super();
		System.out.println("Mouse listener");
		popup = projectPopup;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTree tree = (JTree) e.getSource();
		if (tree != null) {
			ProjectTreeNode node = (ProjectTreeNode) tree.getLastSelectedPathComponent();
			if (node != null) {
				System.out.println("ProjectTreeMouseListener : changing current project to "+node.getProject().name());
				ProjectList.getInstance().setCurrentProject(node.getProject());
			}
			// Popup menu for a project
			if (SwingUtilities.isRightMouseButton(e)) {
				int row = tree.getClosestRowForLocation(e.getX(), e.getY());
				tree.setSelectionRow(row);
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mousePressed");
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
