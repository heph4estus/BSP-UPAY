package com.psi.tariff.group.m;

import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class NewTariffGroup extends Model{
	
	protected String groupname;
	protected String description;
	
	public boolean create(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryUpdate("INSERT INTO TBLTARIFFGROUP (GROUPNAME,DESCRIPTION,TIMESTAMP,CREATEDBY) VALUES(?,?,SYSDATE,?) ", this.groupname,this.description,sess.getAccount().getUserName().toUpperCase())>0;
	}

	public boolean exist(){
		Logger.LogServer(this.groupname);
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFGROUP WHERE GROUPNAME = ?", this.groupname).size()>0;
	}
	
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
