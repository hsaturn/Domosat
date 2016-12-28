/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.hardware;

import com.hsaturn.arduino.message.Message;
import com.hsaturn.arduino.message.MessagePattern;
import com.hsaturn.utils.Observable;
import com.hsaturn.utils.Observer;
import java.util.ArrayList;

/**
 *
 * @author francois
 */
public abstract class Module extends Observable implements Observer {

	transient static int iMsg = 0;	// Numéro message reçu
	transient Message lastMsg;
	transient String buffer = "";	// @FIXME, one buffer per module, even per each part of an arduino ...
	transient private ArrayList<MessagePattern> patterns;
	transient public Arduino arduino = null;	// Arduino associé à ce module
	
	@Override
	public boolean equals(Object m) {
		if (m instanceof Module)
		{
			return ((Module)m).getModuleIdentifier().equals(getModuleIdentifier());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return getModuleIdentifier().hashCode();
	}

	private ArrayList<MessagePattern> getPatterns() {
		if (patterns == null) {
			patterns = new ArrayList<>();
			fillPatterns();
		}
		return patterns;
	}

	protected void addPattern(String name, String simplifiedPattern, boolean bCompleteMessage) {
		MessagePattern mp = new MessagePattern(name, simplifiedPattern, bCompleteMessage);
		if (mp.pattern != null) {
			patterns.add(mp);
		} else {
			System.err.println("ERROR while compiling simplified pattern " + getModuleIdentifier() + "." + name);
		}
	}

	/**
	 * Tente de convertir un message hexa en Message
	 *
	 * @param msg message hexa
	 * @return Message ou null
	 */
	protected Message parse(String msg) {
		for (MessagePattern msgp : getPatterns()) {
			// System.out.println(getModuleIdentifier()+": "+msgp.pattern.pattern());
			Message message = msgp.match(msg);
			if (message != null) {
				//System.out.println("Matched message from moRdule <"+message.get("module")+"> by "+getModuleIdentifier()+"."+msgp.name);
				if (message.get("module").toString().equals(getModuleIdentifier())) {
					System.out.println("INCOMING MESSAGE #"+iMsg+" : " + message.toString());
					System.out.println("RAW : "+message.getRawMessage(" "));
					iMsg++;
					lastMsg = message;
					onMessage(message);
					setChanged();
					notifyObservers(message);
					return lastMsg;
				}
			}
		}
		return null;
	}

	abstract void display(Message m);

	abstract public String getModuleIdentifier();

	abstract public void fillPatterns();

	/**
	 * Override to take action when a message is received
	 */
	protected void onMessage(Message m) {
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof BytesIncomingEvent) {
			// System.out.println("Module update event " + getModuleIdentifier());
			BytesIncomingEvent evt = (BytesIncomingEvent) arg;
			buffer += evt.data;
			buffer = buffer.replace('\r', '\n');
			int index = buffer.indexOf('\n');
			while (index != -1) {
				if (index > 0) {
					String start = buffer.substring(0, index - 1);
					start=start.replace(" ","");
					// System.out.println("  trying to parse [" + start + "]");
					Message m = parse(start);
					// @TODO cut the buffer with m.matcher.start .. m.matcher.end
				}
				buffer = buffer.substring(index + 1);
				index = buffer.indexOf('\n');
			}
			BufferUpdated();
		}
	}

	protected void BufferUpdated() {
	}

	abstract public String name();


}
