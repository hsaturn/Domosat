/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;

/**
 *
 * @author hsaturn
 */
public class ProjectList extends Observable {

	static private ProjectList mInstance = null;
	private static boolean isClosing = false;
	List<ArduinoProject> lstProjects;
	private ArduinoProject mCurrentProject = null;

	public void addProject(ArduinoProject aThis) {
		if (lstProjects.add(aThis))
		{			System.out.println("Project Added");
			setChanged();
			super.notifyObservers(lstProjects);
		}
	}

	static public void close() {
		isClosing = true;
		for (ArduinoProject p : mInstance.lstProjects) {
			p.close();
		}
	}

	@Override
	public void notifyObservers() {
		System.out.println("Notifications simples: " + super.countObservers());
		super.notifyObservers();
	}

	@Override
	public void notifyObservers(Object arg) {

		System.out.println("Notifications complexes : " + super.countObservers() + " avec " + arg.toString());
		super.notifyObservers(arg);
	}

	private ProjectList() {
		lstProjects = new ArrayList<ArduinoProject>() {
		};
	}

	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
		System.out.println("Observer added for ProjectList:" + o.toString());
	}

	static public ProjectList getInstance() {
		if (mInstance == null) {
			mInstance = new ProjectList();
		}
		return mInstance;
	}

	public void setCurrentProject(ArduinoProject project) {
		if (mCurrentProject != project) {
			mCurrentProject = project;
			setChanged();
			super.notifyObservers(project);
		}
	}

	public ArduinoProject getCurrentProject() {
		return mCurrentProject;
	}

	public List<ArduinoProject> getProjectList() {
		return lstProjects;
	}

	void removeProject(ArduinoProject project) {

		if (isClosing) {
			return;
		}
		for (Iterator<ArduinoProject> it = lstProjects.iterator(); it.hasNext();) {
			if (project == it.next()) {
				it.remove();
				break;
			}
		}
	}

	public String getNewDefaultName() {
		Integer i = 1;
		String sName = "";
		while (sName.length() == 0) {
			sName = "arduino.project " + i.toString();
			i = i + 1;
			for (ArduinoProject p : lstProjects) {
				if (p.name().compareTo(sName) == 0) {
					sName = "";
					break;
				}
			}
		}

		return sName;
	}
}
