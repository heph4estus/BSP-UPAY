package com.psi.tariff.plans.m;

import org.apache.commons.lang3.StringEscapeUtils;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class NewServiceFee extends Model{
	
	protected Long minamount;
	protected Long maxamount;
	protected Long percentage;
	protected Long fixed;
	protected String type;
	
	public boolean create(){
		return SystemInfo.getDb().QueryUpdate("INSERT INTO TBLSERVICEFEE (MINAMOUNT,MAXAMOUNT,PERCENTAGE,FIXED,TYPE) VALUES(?,?,?,?,?)", this.minamount,this.maxamount,this.percentage,this.fixed, StringEscapeUtils.unescapeJava(this.type))>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSERVICEFEE WHERE MINAMOUNT = ? AND MAXAMOUNT = ? AND PERCENTAGE = ? AND FIXED = ? AND TYPE = ?", this.minamount,this.maxamount,this.percentage,this.fixed,this.type).size()>0;
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
	public Long getPercentage() {
		return percentage;
	}
	public void setPercentage(Long percentage) {
		this.percentage = percentage;
	}
	public Long getFixed() {
		return fixed;
	}
	public void setFixed(Long fixed) {
		this.fixed = fixed;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
