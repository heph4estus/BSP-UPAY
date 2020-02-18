package com.psi.aml.settings.m;

import com.tlc.common.DataRow;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;

public class CashierAmlReceive extends AmlSetting{
	public boolean register (){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO TBLAMLACCOUNTTYPERECEIVE (ACCOUNTNUMBER,USERSLEVEL,KEY,MINAMOUNT,MAXAMOUNT,MAXAMOUNTDAY,MAXAMOUNTWEEK,MAXAMOUNTMONTH,MAXTRANSDAY,MAXTRANSWEEK,MAXTRANSMONTH) VALUES(?,?,?,?,?,?,?,?,?,?,?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key,this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth)>0;		
	
	}
	
	public boolean exist(){
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPERECEIVE WHERE USERSLEVEL = ? AND ACCOUNTNUMBER = ? AND KEY = ?", this.userslevel,this.accountnumber,this.key);
		this.setMinamount(Long.parseLong(LongUtil.toString(Long.parseLong(r.getString("MINAMOUNT")))));
		this.setMaxamount(Long.parseLong(LongUtil.toString(Long.parseLong(r.getString("MAXAMOUNT")))));
		this.setMaxamountday(Long.parseLong(LongUtil.toString(Long.parseLong(r.getString("MAXAMOUNTDAY")))));
		this.setMaxamountweek(Long.parseLong(LongUtil.toString(Long.parseLong(r.getString("MAXAMOUNTWEEK")))));
		this.setMaxamountmonth(Long.parseLong(LongUtil.toString(Long.parseLong(r.getString("MAXAMOUNTMONTH")))));
		this.setMaxtransday(r.getString("MAXTRANSDAY"));
		this.setMaxtransweek(r.getString("MAXTRANSWEEK"));
		this.setMaxtransmonth(r.getString("MAXTRANSMONTH"));
		this.setKey(r.getString("KEY"));
		return r.size()>0;
	}
	
	public boolean update (){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLAMLACCOUNTTYPERECEIVE SET MINAMOUNT=?,MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=?,MAXTRANSDAY=?,MAXTRANSWEEK=?,MAXTRANSMONTH=? WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.accountnumber,this.userslevel,this.key)>0;		
	}
	
	public boolean delete (){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("DELETE FROM TBLAMLACCOUNTTYPERECEIVE WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key)>0;		
	}
}
