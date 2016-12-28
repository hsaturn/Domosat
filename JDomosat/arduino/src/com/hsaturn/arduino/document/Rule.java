/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.arduino.document;

import com.hsaturn.utils.PropertyBag;
import java.util.ArrayList;

/**
 *
 * @author hsaturn
 */
public class Rule extends AbstractSetting {

	private int rule_number = 0;
	private String rule_name;
	private String rule;
	private int size = 0;
	private boolean valid;
	private String hexa = null;
	private String postfix = null;
	private String description = "";

	public Rule(Rule copy) {
		copyFrom(copy);
	}

	public Rule(String name, String sRule, String desc) {
		rule_name = name;
		rule = sRule;
		description = desc;
		hexa = null;
	}

	public Rule(PropertyBag bag, String prefix) {
		read(bag, prefix);
	}

	@Override
	public void read(PropertyBag bag, String prefix) {
		rule_name = bag.get(prefix + ".name");
		rule = bag.get(prefix + ".rule");
		description = bag.get(prefix + ".description");
		hexa = null;
	}

	@Override
	public void save(PropertyBag bag, String prefix) {
		System.out.println("  SAVING RULE " + rule_name);
		bag.set(prefix + ".name", rule_name);
		bag.set(prefix + ".rule", rule);
		if (hexa != null) {
			bag.set(prefix + ".hexa", hexa);
		} else {
			bag.set(prefix + ".hexa", "<not compiled>");
		}
		bag.set(prefix + ".description", description);
	}

	public String rulename() {
		return rule_name;
	}

	public String rule() {
		return rule;
	}

	public String description() {
		return description;
	}

	public void setRule(String s) {
		if (!s.equals(rule)) {
			hexa = null;
			rule = s;
			setChanged();
			notifyObservers(this);
		}
	}

	public void setRuleName(String rule_name) {
		rule_name = rule_name.replace(',', ' ');
		if (!this.rule_name.equals(rule_name)) {
			this.rule_name = rule_name;
			setChanged();
			notifyObservers(this);
		}
	}

	public void setDescription(String description) {
		if (!description.equals(this.description)) {
			this.description = description;
			setChanged();
			notifyObservers(this);
		}
	}

	public String hexa() {
		if (hexa == null) {
			CommandRunner o = new CommandRunner("/home/hsaturn/Projets/Arduino/Domosat/prefix");
			ArrayList<String> args = new ArrayList<String>();
			args.add("-d");
			args.add("-c");
			args.add(rule);
			valid = false;
			if (o.run(args)) {
				ArrayList<String> stdout = o.getStdout();

				for (String out : stdout) {
					out = out.trim();
					if (out.startsWith("garbage:")) {
						hexa = out;
					} else if (out.startsWith("postfix:")) {
						postfix = out.substring(8);
					} else if (out.startsWith("domosat:")) {
						hexa = out.substring(8);
						valid = true;
					} else if (out.startsWith("size:")) {
						try {
							size = Integer.parseInt(out.substring(6));
						} catch (Exception e) {
							size = -1;
						}
					}
				}
			}
		}
		return hexa;
	}

	@Override
	public String toString() {
		return rule_name;
	}

	public int size() {
		return size;
	}

	public String postfix() {
		return postfix;
	}

	public void copyFrom(Rule copy) {
		rule_name = copy.rule_name;
		rule = copy.rule;
		hexa = copy.hexa;
		description = copy.description;
		size = copy.size;
		valid = copy.valid;
	}

	@Override
	protected boolean _equals(AbstractSetting t) {
		Rule r = (Rule) t;
		return rule.equals(r.rule)
				&& rule_name.equals(r.rule_name)
				&& description.equals(r.description);
	}

	public void setNumber(int number) {
		if (rule_number != number) {
			rule_number = number;
			setChanged();
			notifyObservers();
		}
	}

	public int ruleNumber() {
		return 0;
	}

}
