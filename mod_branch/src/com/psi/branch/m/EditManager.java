package com.psi.branch.m;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.branch.utils.Manager;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class EditManager extends Manager{
	
	public boolean update(){		
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET EMAIL=?,FIRSTNAME=?,LASTNAME=?,MSISDN=?,MIDDLENAME=? WHERE USERNAME=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(), 
				  this.email, this.firstname, this.lastname,this.msisdn,this.midname,this.username
				  );
		if(res>0){
			AuditTrail audit  = new AuditTrail();
			audit.setIp(sess.getIpAddress());
			audit.setModuleid(String.valueOf("1205"));
			audit.setEntityid(accountnumber);
			audit.setLog("Successfully update branch manager user");
			audit.setStatus("00");
			audit.setData("EMAIL:"+this.email+"|FIRSTNAME:"+this.firstname+"|LASTNAME:"+"|MIDDLENAME:"+this.midname+this.lastname+"|MSISDN:"+this.msisdn+"|USERNAME:"+this.username);
			audit.setUserid(sess.getAccount().getId());
			audit.setUsername(sess.getAccount().getUserName());
			audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setOlddata("EMAIL:"+row.getString("EMAIL")+"|FIRSTNAME:"+row.getString("FIRSTNAME")+"|MIDDLENAME:"+row.getString("MIDDLENAME")+"|LASTNAME:"+row.getString("LASTNAME")+"|MSISDN:"+row.getString("MSISDN")+"|USERNAME:"+row.getString("USERNAME"));
	
			audit.insert();
			return true;
		}else{
			AuditTrail audit  = new AuditTrail();
			audit.setIp(sess.getIpAddress());
			audit.setModuleid(String.valueOf("1205"));
			audit.setEntityid(accountnumber);
			audit.setLog("Unable to update branch manager user");
			audit.setStatus("99");
			audit.setData("EMAIL:"+this.email+"|FIRSTNAME:"+this.firstname+"|LASTNAME:"+"|MIDDLENAME:"+this.midname+this.lastname+"|MSISDN:"+this.msisdn+"|USERNAME:"+this.username);
			audit.setUserid(sess.getAccount().getId());
			audit.setUsername(sess.getAccount().getUserName());
			audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setOlddata("EMAIL:"+row.getString("EMAIL")+"|FIRSTNAME:"+row.getString("FIRSTNAME")+"|MIDDLENAME:"+row.getString("MIDDLENAME")+"|LASTNAME:"+row.getString("LASTNAME")+"|MSISDN:"+row.getString("MSISDN")+"|USERNAME:"+row.getString("USERNAME"));
	
			audit.insert();
			return false;
		}
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username).size()>0;
	}
}
