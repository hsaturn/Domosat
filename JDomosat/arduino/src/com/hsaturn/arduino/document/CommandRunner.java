/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.openide.util.Exceptions;

/**
 *
 * @author hsaturn
 */
class CommandRunner {

	private final String cmd;
	private final ArrayList<String>	stdout = new ArrayList<String>();
	private final ArrayList<String>	stderr = new ArrayList<String>();

	public CommandRunner(String sCommand) {
		cmd = sCommand;
	}

	public boolean run(ArrayList<String> arguments) {
		try {
			ArrayList<String> all;
			all = new ArrayList<>(arguments);
			all.add(0,cmd);
			Process proc = new ProcessBuilder(all).start();
			
			BufferedReader stdoutBuf = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stderrBuf = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			
			String s;
			while ((s = stdoutBuf.readLine()) != null) {
				stdout.add(s);
			}
			while((s = stderrBuf.readLine()) != null) {
				stderr.add(s);
			}
			return true;
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
		return false;
	}
	
	public ArrayList<String> getStdout() { return stdout; }
	public ArrayList<String> getStderr() { return stderr; }
	
}
