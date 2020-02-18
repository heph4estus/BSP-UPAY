package com.psi.service.fee.m;

import org.apache.commons.lang3.StringEscapeUtils;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class NewSFByProduct extends Model{
	
	protected Long minamount;
	protected Long maxamount;
	protected String percentage;
	protected Long fixed;
	protected String ibayadcode;
	protected String moduleid;
	public boolean create(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryUpdate("INSERT INTO TBLPRODUCTFEES (MINAMOUNT,MAXAMOUNT,PERCENTAGE,FIXED,IBAYADCODE,CREATEDBY,MODULEID) VALUES(?,?,?,?,?,?,?)", this.minamount,this.maxamount,this.percentage,this.fixed, this.ibayadcode,sess.getAccount().getUserName().toUpperCase(),this.moduleid)>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTFEES WHERE MINAMOUNT = ? AND MAXAMOUNT = ? AND PERCENTAGE = ? AND FIXED = ? AND IBAYADCODE = ?", this.minamount,this.maxamount,this.percentage,this.fixed,this.ibayadcode).size()>0;
	}
	
	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public Long getMinamount() {
		return minamount;
	}
	public void setMinamount(Long minamount) {
		this.minamount = minamount;
	}
	public Long getMaxamount() {
		return maxamount;
	}
	public void setMaxamount(Long maxamount) {
		this.maxamount = maxamount;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public Long getFixed() {
		return fixed;
	}
	public void setFixed(Long fixed) {
		this.fixed = fixed;
	}
	public String getIbayadcode() {
		return ibayadcode;
	}
	public void setIbayadcode(String ibayadcode) {
		this.ibayadcode = ibayadcode;
	}
	

}
