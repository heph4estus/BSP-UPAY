package com.psi.aml.settings.m;

import com.tlc.common.SystemInfo;

public class CashierAmlSend extends AmlSetting{
	
	public boolean register (){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO TBLAMLACCOUNTTYPESEND (ACCOUNTNUMBER,USERSLEVEL,KEY,MINAMOUNT,MAXAMOUNT,MAXAMOUNTDAY,MAXAMOUNTWEEK,MAXAMOUNTMONTH,MAXTRANSDAY,MAXTRANSWEEK,MAXTRANSMONTH) VALUES(?,?,?,?,?,?,?,?,?,?,?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key,this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth)>0;		
	
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPESEND WHERE USERSLEVEL = ? AND ACCOUNTNUMBER = ? AND KEY = ?", this.userslevel,this.accountnumber,this.key).size()>0;
	}
	
	public boolean update (){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLAMLACCOUNTTYPESEND SET MINAMOUNT=?,MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=?,MAXTRANSDAY=?,MAXTRANSWEEK=?,MAXTRANSMONTH=? WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.accountnumber,this.userslevel,this.key)>0;		
	}
	
	public boolean delete (){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("DELETE FROM TBLAMLACCOUNTTYPESEND WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key)>0;		
	}
}
