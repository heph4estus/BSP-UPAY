package com.psi.aml.settings.m;

import com.tlc.gui.modules.common.Model;

public class AmlSetting extends Model{
	
	protected String accountnumber;
	protected String userslevel;
	protected String key;
	protected Long minamount;
	protected Long maxamount;
	protected Long maxamountday;
	protected Long maxamountweek;
	protected Long maxamountmonth;
	protected String maxtransday;
	protected String maxtransweek;
	protected String maxtransmonth;
	protected String alertlevel;
	protected String scope;
	protected String auditdata;
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getUserslevel() {
		return userslevel;
	}
	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public Long getMaxamountday() {
		return maxamountday;
	}
	public void setMaxamountday(Long maxamountday) {
		this.maxamountday = maxamountday;
	}
	public Long getMaxamountweek() {
		return maxamountweek;
	}
	public void setMaxamountweek(Long maxamountweek) {
		this.maxamountweek = maxamountweek;
	}
	public Long getMaxamountmonth() {
		return maxamountmonth;
	}
	public void setMaxamountmonth(Long maxamountmonth) {
		this.maxamountmonth = maxamountmonth;
	}
	public String getMaxtransday() {
		return maxtransday;
	}
	public void setMaxtransday(String maxtransday) {
		this.maxtransday = maxtransday;
	}
	public String getMaxtransweek() {
		return maxtransweek;
	}
	public void setMaxtransweek(String maxtransweek) {
		this.maxtransweek = maxtransweek;
	}
	public String getMaxtransmonth() {
		return maxtransmonth;
	}
	public void setMaxtransmonth(String maxtransmonth) {
		this.maxtransmonth = maxtransmonth;
	}
	public String getAlertlevel() {
		return alertlevel;
	}
	public void setAlertlevel(String alertlevel) {
		this.alertlevel = alertlevel;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getAuditdata() {
		return auditdata;
	}
	public void setAuditdata(String auditdata) {
		this.auditdata = auditdata;
	}
}
