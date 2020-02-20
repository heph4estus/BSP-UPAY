package com.psi.tariff.group.m;

import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class NewTariffGroup extends Model{
	
	protected String tariffGroup;
	protected String description;
	
	public boolean create(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryUpdate("INSERT INTO TBLTARIFFGROUP (GROUPNAME,DESCRIPTION,TIMESTAMP,CREATEDBY) VALUES(?,?,SYSDATE,?) ", this.tariffGroup,this.description,sess.getAccount().getUserName().toUpperCase())>0;
	}

	public boolean exist(){
		Logger.LogServer(this.tariffGroup);
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFGROUP WHERE GROUPNAME = ?", this.tariffGroup).size()>0;
	}
	
	public String getGroupname() {
		return tariffGroup;
	}
	public void setGroupname(String groupname) {
		this.tariffGroup = groupname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
