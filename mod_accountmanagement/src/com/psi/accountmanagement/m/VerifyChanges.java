package com.psi.accountmanagement.m;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.psi.accountmanagement.utils.Users;
import com.psi.audit.trail.m.GetAuditPreviousData;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;

public class VerifyChanges extends Users{
	
	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.userid);
		  previous.setQuery("SELECT EMAIL,MSISDN FROM TBLUSERS WHERE USERID=?");
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
		 }
	
	public boolean updateMobile(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.userid);
		if(row.isEmpty()){
			return false;
		} else {
			this.setUserslevel(row.getString("USERSLEVEL"));
			this.setUsername(row.getString("USERNAME"));
			this.setMsisdn(row.getString("TEMPMSISDN"));
			this.setEmail(row.getString("TEMPEMAIL"));
		}
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET MSISDN=?,TEMPMSISDN='' WHERE USERID = ?; \n");
		query.append("UPDATE TBLPOSUSERS SET MSISDN=? WHERE USERID = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
				row.getString("TEMPMSISDN"),this.userid,
				row.getString("TEMPMSISDN"),row.getString("USERNAME"))>0;
	}
	
	public boolean updateEmail(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.userid);
		if(row.isEmpty()){
			return false;
		} else {
			this.setUserslevel(row.getString("USERSLEVEL"));
			this.setUsername(row.getString("USERNAME"));
			this.setEmail(row.getString("TEMPEMAIL"));
			this.setMsisdn(row.getString("TEMPMSISDN"));
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET EMAIL=?,TEMPEMAIL='',MSISDNOTP='',ISOTPVERIFIED = 1 WHERE USERID = ?; \n");
			query.append("UPDATE TBLPOSUSERS SET EMAIL=? WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 
			return SystemInfo.getDb().QueryUpdate(query.toString(), 
					row.getString("TEMPEMAIL"),this.userid,
					row.getString("TEMPEMAIL"),row.getString("USERNAME"))>0;
		}
		
	}
	
	 public boolean validateToken(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND MSISDNOTP = ?", this.userid,this.token).size()>0;	
		}	
	 
	 public boolean isAlreadyVerifiedEmail(){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND TEMPEMAIL IS NULL", this.userid).size()>0;	
			}	
	 public boolean isAlreadyVerifiedMobile(){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND TEMPMSISDN IS NULL", this.userid).size()>0;	
			}	
}
