package com.psi.tariff.plans.m;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class NewReceiveAml extends Model{
	
	protected String key;
	protected Long minamount;
	protected Long maxamount;
	protected Long maxamountday;
	protected Long maxamountweek;
	protected Long maxamountmonth;
	protected String maxtransday;
	protected String maxtransweek;
	protected String maxtransmonth;
	
	public boolean register (){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO TBLAMLACCOUNTTYPERECEIVE (KEY,MINAMOUNT,MAXAMOUNT,MAXAMOUNTDAY,MAXAMOUNTWEEK,MAXAMOUNTMONTH,MAXTRANSDAY,MAXTRANSWEEK,MAXTRANSMONTH) VALUES(?,?,?,?,?,?,?,?,?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.key,this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth)>0;		
	
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPERECEIVE WHERE KEY = ? AND MINAMOUNT=? AND MAXAMOUNT=? AND MAXAMOUNTDAY=? AND MAXAMOUNTWEEK=? AND MAXAMOUNTMONTH=? AND MAXTRANSDAY=? AND MAXTRANSWEEK= ? AND MAXTRANSMONTH =? ", this.key,this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth).size()>0;
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
	
	

}
