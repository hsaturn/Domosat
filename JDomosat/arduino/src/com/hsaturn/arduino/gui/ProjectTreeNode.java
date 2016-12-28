/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.gui;

import com.hsaturn.arduino.document.ArduinoProject;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author hsaturn
 */

public class ProjectTreeNode extends DefaultMutableTreeNode {

	private ArduinoProject project;
	String text;

	ProjectTreeNode(String s, ArduinoProject p) {
		super(s);
		text=s;
		project = p;
	}

	boolean hasChild(String s) {
		return getChild(s) != null;
	}

	ProjectTreeNode getChild(String s) {
		if (super.children != null) {
			for (Object p : super.children) {
				ProjectTreeNode node = (ProjectTreeNode) p;
				if (node != null && (s.compareTo(p.toString()) == 0)) {
					return node;
				}
			}
		}
		return null;
	}

	void addChild(ProjectTreeNode child) {
		super.add(child);
	}

	ArduinoProject getProject() {
		return project;
	}

	String getText()
	{
		return text;
	}
}
