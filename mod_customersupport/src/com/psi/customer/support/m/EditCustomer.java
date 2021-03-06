package com.psi.customer.support.m;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class EditCustomer extends Model{
	protected String firstname;
	protected String lastname;
	protected String middlename;
	protected String username;
	
	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username);
		if(!row.isEmpty()){
			if(row.getString("USERSLEVEL").toString().equals("CUSTOMER")){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=? WHERE USERNAME = ?; \n");
				query.append("UPDATE TBLPOSUSERS SET FIRSTNAME=?,LASTNAME=? WHERE USERID = ?; \n");
				query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=? WHERE ACCOUNTNUMBER = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				AuditTrail audit  = new AuditTrail();
				audit.setIp(sess.getIpAddress());
				audit.setModuleid("3907");
				audit.setEntityid(this.username);
				audit.setLog("Successfully updated firstname, middlename and lastname");
				audit.setStatus("00");
				audit.setData("NEW DETAILS:"+this.firstname+"|"+this.middlename+"|"+this.lastname+"|"+this.username
							   +"| OLD DETAILS:"+row.getString("FIRSTNAME").toString()+"|"+row.getString("MIDDLENAME").toString()+"|"+row.getString("LASTNAME").toString()+"|"+row.getString("USERNAME").toString());
				audit.setUserid(sess.getAccount().getId());
				audit.setUsername(sess.getAccount().getUserName());
				audit.setSessionid(sess.getId());
				audit.setBrowser(sess.getAccount().getBrowser());
			    audit.setBrowserversion(sess.getAccount().getBrowserversion());
			    audit.setPortalversion(sess.getAccount().getPortalversion());
			    audit.setOs(sess.getAccount().getOs());
			    audit.setUserslevel(sess.getAccount().getGroup().getName());
			    
				audit.insert();
				return SystemInfo.getDb().QueryUpdate(query.toString(),this.firstname,this.lastname,this.middlename,this.username,
																	this.firstname,this.lastname,this.username,
																	this.firstname,this.lastname,this.middlename,row.getString("ACCOUNTNUMBER").toString())>0;
			}else{
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=? WHERE USERNAME = ?; \n");
				query.append("UPDATE TBLPOSUSERS SET FIRSTNAME=?,LASTNAME=? WHERE USERID = ?; \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				AuditTrail audit  = new AuditTrail();
				audit.setIp(sess.getIpAddress());
				audit.setModuleid("3907");
				audit.setEntityid(this.username);
				audit.setLog("Successfully updated firstname, middlename and lastname");
				audit.setStatus("00");
				audit.setData("NEW DETAILS:"+this.firstname+"|"+this.middlename+"|"+this.lastname+"|"+this.username
							   +"| OLD DETAILS:"+row.getString("FIRSTNAME").toString()+"|"+row.getString("MIDDLENAME").toString()+"|"+row.getString("LASTNAME").toString()+"|"+row.getString("USERNAME").toString());
				audit.setUserid(sess.getAccount().getId());
				audit.setUsername(sess.getAccount().getUserName());
				audit.setSessionid(sess.getId());
				audit.setBrowser(sess.getAccount().getBrowser());
			    audit.setBrowserversion(sess.getAccount().getBrowserversion());
			    audit.setPortalversion(sess.getAccount().getPortalversion());
			    audit.setOs(sess.getAccount().getOs());
			    audit.setUserslevel(sess.getAccount().getGroup().getName());
			    
				audit.insert();
				return SystemInfo.getDb().QueryUpdate(query.toString(),this.firstname,this.lastname,this.middlename,this.username,
						this.firstname,this.lastname,this.username)>0;

			}
		}else{
			AuditTrail audit  = new AuditTrail();
			audit.setIp(sess.getIpAddress());
			audit.setModuleid("1926");
			audit.setEntityid(this.username);
			audit.setLog("No account details found via username");
			audit.setStatus("01");
			audit.setData("");
			audit.setUserid(sess.getAccount().getId());
			audit.setUsername(sess.getAccount().getUserName());
			audit.setSessionid(sess.getId());
			audit.setBrowser(sess.getAccount().getBrowser());
		    audit.setBrowserversion(sess.getAccount().getBrowserversion());
		    audit.setPortalversion(sess.getAccount().getPortalversion());
		    audit.setOs(sess.getAccount().getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    
			audit.insert();
			return false;
		}
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", this.username).size()>0;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
