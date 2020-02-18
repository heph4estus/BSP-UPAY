package com.psi.tariff.group.m;

import com.tlc.gui.modules.common.Model;

public class Branches extends Model{

	public static final String PROP_BRANCHES="BRANCHES";
	protected String[] branches;
	
	public String[] getBranches() {
		return branches;
	}
	public void setBranches(String[] branches) {
		this.props.put(PROP_BRANCHES,branches);
		this.branches = branches;
	}
	
	
}
