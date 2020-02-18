package com.psi.tariff.plans.m;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class NewLoyalty extends Model{
	
	protected Long forevery;
	protected Long earn;
	protected String type;
	
	public boolean create(){
		return SystemInfo.getDb().QueryUpdate("INSERT INTO TBLLOYALTYSETTINGS (FOREVERY,EARN,TYPE) VALUES(?,?,?)", this.forevery,this.earn,this.type)>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSERVICEFEE WHERE FOREVERY = ? AND EARN = ? AND TYPE = ?", this.forevery,this.earn,this.type).size()>0;
	}
	
	public Long getForevery() {
		return forevery;
	}

	public void setForevery(Long forevery) {
		this.forevery = forevery;
	}

	public Long getEarn() {
		return earn;
	}

	public void setEarn(Long earn) {
		this.earn = earn;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
