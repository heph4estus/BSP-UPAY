package com.psi.tariff.config.util;

import com.tlc.gui.modules.common.Model;

public class Tariffs extends Model{
	public static final String PROP_PLANID="PLANID";
	protected String[] planid;
	protected String tarrifGroup;
	protected String type;
	protected String tablename;
	
	public String[] getPlanid() {
		return planid;
	}
	public void setPlanid(String[] planid) {
		this.props.put(PROP_PLANID, planid);
		this.planid = planid;
	}
	public String getGroupname() {
		return tarrifGroup;
	}
	public void setGroupname(String groupname) {
		this.tarrifGroup = groupname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
}
