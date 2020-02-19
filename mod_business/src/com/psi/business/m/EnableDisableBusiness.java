package com.psi.business.m;

import java.lang.reflect.Field;

import com.psi.business.util.Branch;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class EnableDisableBusiness extends Branch{
	
	public boolean update (){
		String rr = "";
		DataRowCollection row = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE STATUS=1 AND KEYACCOUNT = ?", this.accountnumber);
		if(!row.isEmpty()){
			for(DataRow r:row){
				rr += "'"+r.getString("ACCOUNTNUMBER")+"',";
			}
		}else{
			rr = "'',";
		}
		
		String rrr = "";
		DataRowCollection roww = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE STATUS=0 AND KEYACCOUNT = ?", this.accountnumber);
		if(!roww.isEmpty()){
			for(DataRow r:roww){
				rrr += "'"+r.getString("ACCOUNTNUMBER")+"',";
			}
		}else{
			rrr = "'',";
		}
		if(this.status.equals("DISABLE")){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET LOCKED = 'YES',STATUS='INACTIVE' WHERE ACCOUNTNUMBER IN ("+rr+"''"+"); \n");
		query.append("UPDATE TBLPOSUSERS SET PASSWORD='1166' WHERE ACCOUNTNUMBER IN ("+rr+"''"+"); \n");
		query.append("UPDATE TBLBUSINESS SET STATUS=0 WHERE ACCOUNTNUMBER=?; \n");
		query.append("UPDATE TBLBRANCHES SET STATUS=0 WHERE KEYACCOUNT=? AND STATUS = 1; \n");
		query.append("UPDATE TBLUSERS SET LOCKED = 'YES',STATUS='INACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber, this.accountnumber)>0;	
		 
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET LOCKED = 'NO',STATUS='ACTIVE' WHERE ACCOUNTNUMBER IN ("+rrr+"''"+"); \n");
			query.append("UPDATE TBLPOSUSERS SET PASSWORD='123456789' WHERE ACCOUNTNUMBER IN ("+rrr+"''"+"); \n");
			query.append("UPDATE TBLBUSINESS SET STATUS=1 WHERE ACCOUNTNUMBER=?; \n");
			query.append("UPDATE TBLBRANCHES SET STATUS=1 WHERE KEYACCOUNT=? AND STATUS = 0; \n");
			query.append("UPDATE TBLUSERS SET LOCKED = 'NO',STATUS='ACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber, this.accountnumber)>0;	
		}
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBUSINESS WHERE ACCOUNTNUMBER=?", this.accountnumber).size()>0;
	}
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ? AND PASSWORD=ENCRYPT(?,?,USERNAME)", sess.getAccount().getUserName(),this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
	/**
	 * MVO 18-02-2020
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();

		/*
		 * Super Class fields
		 */
		for (Field f : superFields) {
			try {
				f.setAccessible(true);
				if (f.get(this) != null && !f.getName().equalsIgnoreCase("servicescashout") && !f.getName().equalsIgnoreCase("servicesabscbn") && !f.getName().equalsIgnoreCase("serviceskapamilya") && !f.getName().equalsIgnoreCase("servicescashin") && !f.getName().equalsIgnoreCase("commissionbill") && !f.getName().equalsIgnoreCase("commissionairt") && !f.getName().equalsIgnoreCase("servicesbill") && !f.getName().equalsIgnoreCase("servicesairt") && !f.getName().equalsIgnoreCase("tariff") && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}

		}
		/*
		 * Class fields
		 */
		Field[] classFields = this.getClass().getDeclaredFields();
		for (Field f : classFields) {
			f.setAccessible(true);
			try {

				if (f.get(this) != null && !f.getName().equalsIgnoreCase("servicescashout") && !f.getName().equalsIgnoreCase("servicesabscbn") && !f.getName().equalsIgnoreCase("serviceskapamilya") && !f.getName().equalsIgnoreCase("servicescashin") && !f.getName().equalsIgnoreCase("commissionbill") && !f.getName().equalsIgnoreCase("commissionairt") && !f.getName().equalsIgnoreCase("servicesbill") && !f.getName().equalsIgnoreCase("servicesairt") && !f.getName().equalsIgnoreCase("tariff") && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}

		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.lastIndexOf("|"));
		return sb.toString();
	}

}
