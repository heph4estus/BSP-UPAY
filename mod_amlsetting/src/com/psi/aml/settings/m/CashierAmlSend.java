package com.psi.aml.settings.m;

import java.util.ArrayList;

import com.psi.audit.trail.m.GetAuditPreviousData;
import com.tlc.common.SystemInfo;

public class CashierAmlSend extends AmlSetting{
	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.accountnumber);
		  parameters.add(this.userslevel);
		  parameters.add(this.key);
		  parameters.add(this.alertlevel);
		  parameters.add(this.scope);
		  previous.setQuery("SELECT * FROM TBLAMLACCOUNTTYPESEND WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=? AND ALERTLEVEL = ? AND SCOPE = ?");
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public boolean register (){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO TBLAMLACCOUNTTYPESEND (ACCOUNTNUMBER,USERSLEVEL,KEY,MINAMOUNT,MAXAMOUNT,MAXAMOUNTDAY,MAXAMOUNTWEEK,MAXAMOUNTMONTH,MAXTRANSDAY,MAXTRANSWEEK,MAXTRANSMONTH,ALERTLEVEL,SCOPE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key,this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.alertlevel,this.scope)>0;		
	
	}
	
	public boolean exist(){
		if(this.alertlevel.isEmpty() && !this.scope.isEmpty()){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPESEND WHERE USERSLEVEL = ? AND ACCOUNTNUMBER = ? AND KEY = ? AND ALERTLEVEL IS NULL AND SCOPE=?", this.userslevel,this.accountnumber,this.key,this.scope).size()>0;
		}else if(!this.alertlevel.isEmpty() && this.scope.isEmpty()){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPESEND WHERE USERSLEVEL = ? AND ACCOUNTNUMBER = ? AND KEY = ? AND ALERTLEVEL = ? AND SCOPE IS NULL", this.userslevel,this.accountnumber,this.key,this.alertlevel).size()>0;
		}else if(this.alertlevel.isEmpty() && this.scope.isEmpty()){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPESEND WHERE USERSLEVEL = ? AND ACCOUNTNUMBER = ? AND KEY = ?", this.userslevel,this.accountnumber,this.key).size()>0;
		}else {
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPESEND WHERE USERSLEVEL = ? AND ACCOUNTNUMBER = ? AND KEY = ? AND ALERTLEVEL = ? AND SCOPE=?", this.userslevel,this.accountnumber,this.key,this.alertlevel,this.scope).size()>0;
		}
	}
	
	public boolean update (){
		if(this.alertlevel.isEmpty() && !this.scope.isEmpty()){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLAMLACCOUNTTYPESEND SET MINAMOUNT=?,MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=?,MAXTRANSDAY=?,MAXTRANSWEEK=?,MAXTRANSMONTH=? WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=? AND SCOPE = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.accountnumber,this.userslevel,this.key,this.scope)>0;		
		}else if(!this.alertlevel.isEmpty() && this.scope.isEmpty()){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLAMLACCOUNTTYPESEND SET MINAMOUNT=?,MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=?,MAXTRANSDAY=?,MAXTRANSWEEK=?,MAXTRANSMONTH=? WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=? AND ALERTLEVEL=?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.accountnumber,this.userslevel,this.key,this.alertlevel)>0;		
		}else if(this.alertlevel.isEmpty() && this.scope.isEmpty()){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLAMLACCOUNTTYPESEND SET MINAMOUNT=?,MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=?,MAXTRANSDAY=?,MAXTRANSWEEK=?,MAXTRANSMONTH=? WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=? ; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.accountnumber,this.userslevel,this.key)>0;		
		}else {
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLAMLACCOUNTTYPESEND SET MINAMOUNT=?,MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=?,MAXTRANSDAY=?,MAXTRANSWEEK=?,MAXTRANSMONTH=? WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=? AND ALERTLEVEL=? AND SCOPE = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.minamount,this.maxamount,this.maxamountday,this.maxamountweek,this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.accountnumber,this.userslevel,this.key,this.alertlevel,this.scope)>0;		
		}
	}
	
	public boolean delete (){
		if(this.alertlevel.isEmpty() && !this.scope.isEmpty()){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("DELETE FROM TBLAMLACCOUNTTYPESEND WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=?  AND SCOPE = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key,this.scope)>0;		
		}else if(!this.alertlevel.isEmpty() && this.scope.isEmpty()){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("DELETE FROM TBLAMLACCOUNTTYPESEND WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=? AND ALERTLEVEL = ? ; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key,this.alertlevel)>0;		
		}else if(this.alertlevel.isEmpty() && this.scope.isEmpty()){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("DELETE FROM TBLAMLACCOUNTTYPESEND WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=? ; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key)>0;		
		}else {
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("DELETE FROM TBLAMLACCOUNTTYPESEND WHERE ACCOUNTNUMBER=? AND USERSLEVEL = ? AND KEY=? AND ALERTLEVEL = ? AND SCOPE = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.userslevel,this.key,this.alertlevel,this.scope)>0;		
		}
	}
}
