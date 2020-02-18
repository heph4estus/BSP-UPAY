package com.psi.customer.support.m;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ManageAccount extends Model{
	protected String status;
	protected String accountnumber;
	protected String password;
	protected String accounttype;
	public boolean activatedeactivatebranch(){
		if(this.status.equals("INACTIVE")){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLBRANCHES SET STATUS=0 WHERE ACCOUNTNUMBER=?; \n");
				query.append("UPDATE TBLUSERS SET STATUS='INACTIVE',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='INACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber,this.accountnumber)>0;	
		 
		}else{
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLBRANCHES SET STATUS=1 WHERE ACCOUNTNUMBER=?; \n");
				query.append("UPDATE TBLUSERS SET STATUS='ACTIVE',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='ACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber,this.accountnumber)>0;	
		}
	}
	
	public boolean activatedeactivatecompany(){
		if(this.status.equals("INACTIVE")){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLBUSINESS SET STATUS=0 WHERE ACCOUNTNUMBER=?; \n");
				query.append("UPDATE TBLBRANCHES SET STATUS=0 WHERE KEYACCOUNT=?; \n");
				query.append("UPDATE TBLUSERS SET STATUS='INACTIVE',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='INACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='INACTIVE' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");
				query.append("UPDATE TBLUSERS SET STATUS='INACTIVE',SESSIONID='' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber,this.accountnumber,this.accountnumber,this.accountnumber,this.accountnumber)>0;	
		 
		}else{
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLBUSINESS SET STATUS=1 WHERE ACCOUNTNUMBER=?; \n");
				query.append("UPDATE TBLBRANCHES SET STATUS=1 WHERE KEYACCOUNT=?; \n");
				query.append("UPDATE TBLUSERS SET STATUS='ACTIVE',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='ACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='ACTIVE' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");
				query.append("UPDATE TBLUSERS SET STATUS='ACTIVE',SESSIONID='' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");		
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber,this.accountnumber)>0;	
		}
	}
	public boolean activatedeactivatecustomer(){
		if(this.status.equals("INACTIVE")){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET STATUS='INACTIVE',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='INACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;	
		 
		}else{
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET STATUS='ACTIVE',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='ACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;	
		}
	}
	public boolean lockunlockbranch(){
		if(this.status.equals("YES")){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET LOCKED='YES',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED='YES' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;	
		 
		}else{
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET LOCKED='NO',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED='NO' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;	
		}
	}
	public boolean lockunlockcustomer(){
		if(this.status.equals("YES")){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET LOCKED='YES',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED='YES' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;	
		 
		}else{
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET LOCKED='NO',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED='NO' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;	
		}
	}
	public boolean lockunlockcompany(){
		if(this.status.equals("YES")){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET LOCKED='YES',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED='YES' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED='YES' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");
				query.append("UPDATE TBLUSERS SET LOCKED='YES',SESSIONID='' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");						
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber,this.accountnumber,this.accountnumber)>0;	
		 
		}else{
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET LOCKED='NO',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED='NO' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED='NO' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");
				query.append("UPDATE TBLUSERS SET LOCKED='NO',SESSIONID='' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");										
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber,this.accountnumber,this.accountnumber)>0;	
		}
	}
	public boolean terminatebranch(){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET STATUS='TERMINATED',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='TERMINATED' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;	
				
	}
	public boolean terminatecustomer(){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET STATUS='TERMINATED',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='TERMINATED' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;			 	
	}
	public boolean terminatecompany(){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET STATUS='TERMINATED',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO STATUS='TERMINATED' WHERE ACCOUNTNUMBER = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO STATUS='TERMINATED' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");
				query.append("UPDATE TBLUSERS SET STATUS='TERMINATED',SESSIONID='' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");						
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber,this.accountnumber,this.accountnumber)>0;			 
		
	}
public boolean cancelbranch(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET STATUS='CANCELLED',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='CANCELLED' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;	
		
}
public boolean cancelcustomer(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET STATUS='CANCELLED',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS='CANCELLED' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber)>0;			 	
}
public boolean cancelcompany(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET STATUS='CANCELLED',SESSIONID='' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO STATUS='CANCELLED' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO STATUS='CANCELLED' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");
		query.append("UPDATE TBLUSERS SET STATUS='CANCELLED',SESSIONID='' WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT= ?); \n");						
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber,this.accountnumber,this.accountnumber)>0;			 

}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER=?", this.accountnumber).size()>0;
	}
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ? AND PASSWORD=ENCRYPT(?,?,USERNAME)", sess.getAccount().getUserName(),this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

}
