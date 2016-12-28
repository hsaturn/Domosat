/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author francois
 */
public class MessagePattern {

	public String name;
	private String stringPattern;
	private String simplifiedPattern;
	public Pattern pattern;
	public Map<String, AbstractConverter> converters;
	public List<String> vars;
	public List<String> commonValues;

	private MessagePattern() {
	}

	/**
	 *
	 * @param name              Name of the MessagePattern (short !)
	 * @param simplifiedPattern Data section pattern.
	 * @param bCompleteMessage  if false, then a header is added (as in Message.h or Arduino)
	 */
	public MessagePattern(String name, String simplifiedPattern, boolean bCompleteMessage) {
		this.name = name;
		if (bCompleteMessage) {
			simplifiedPattern = "{from}{srce}{to}{module:char}{ttl}{nr}{msg:char}{size}" + simplifiedPattern;
		}

		commonValues = new ArrayList<>();
		commonValues.add("from");
		commonValues.add("srce");
		commonValues.add("to");
		commonValues.add("module");
		commonValues.add("ttl");
		commonValues.add("nr");
		commonValues.add("msg");
		commonValues.add("size");

		this.simplifiedPattern = simplifiedPattern;
		this.stringPattern = simplifiedPattern;
		this.vars = new ArrayList<>();
		converters = new HashMap<>();
		buildPattern();
	}

	private void buildPattern() {
		Pattern tmpPattern = Pattern.compile("\\{(?<var>[a-zA-Z_]*)(:(?<type>[a-z0-9_]*))?(,(?<size>([0-9]*|\\*)))?\\}");
		Matcher m = tmpPattern.matcher(simplifiedPattern);
		stringPattern = simplifiedPattern;
		String type;
		while (m.find()) {
			vars.add(m.group("var"));
			int size;
			if (m.group("size") != null) {
				if (m.group("size").equals("*"))
					size=0;
				else
					size = Integer.parseInt(m.group("size"));
			} else {
				size = 1;
			}
			type = m.group("type");
			if (type == null) {
				type = "hex_int8";
			}
			AbstractConverter converter = AbstractConverter.factory(type);
			converters.put(m.group("var"), converter);
			stringPattern = m.replaceFirst("(?<" + m.group("var") + ">" + converter.stringPattern(size) + ")");
			m = tmpPattern.matcher(stringPattern);
		}

		// stringPattern += "(?<end>.*)";

		System.out.println(name+" [" + simplifiedPattern + "] \n ... gave pattern [" + stringPattern + "]");
		try {
			pattern = Pattern.compile(stringPattern);
		} catch (PatternSyntaxException e) {
			System.err.println("ERREUR " + this.getClass().getSimpleName() + '.'+name+" : SYNTAXE PATTERN [" + e.getDescription() + "]");
		}
	}

	public Message match(String msg) {
		if (pattern == null) {
			return null;
		}
		Matcher m = pattern.matcher(msg);
		if (m.find()) {
			Map<String, Integer> namedGroups = null;
			Message message = new Message(name, m);

			try {
				boolean important;
				for (String var : vars) {
					message.addValue(
							new Value(
									var,
									m.group(var),
									converters.get(var),
									!commonValues.contains(var))
					);
				}
			} catch (SecurityException | IllegalArgumentException e) {
				//Logger.getLogger(MessagePattern.class.getName()).log(Level.SEVERE, null, ex);
				e.printStackTrace();
			}
			return message;
		} else {
			//System.out.println("no match for (" + msg + ")");
		}
		return null;
	}

}
