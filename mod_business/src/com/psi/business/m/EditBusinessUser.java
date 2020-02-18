package com.psi.business.m;

import java.lang.reflect.Field;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.business.util.Manager;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class EditBusinessUser extends Manager{
	
	public boolean update(){		
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET EMAIL=?,FIRSTNAME=?,LASTNAME=?,MSISDN=? WHERE USERNAME=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(), 
				  this.email, this.firstname, this.lastname,this.msisdn,this.username
				  );
		if(res>0){
			AuditTrail audit  = new AuditTrail();
			audit.setIp(sess.getIpAddress());
			audit.setModuleid(String.valueOf("1105"));
			audit.setEntityid(this.accountnumber);
			audit.setLog("Successfully update company manager user");
			audit.setStatus("00");
			audit.setData(this.email+"|"+this.firstname+"|"+this.lastname+"|"+this.msisdn+"|"+this.username);
			audit.setUserid(sess.getAccount().getId());
			audit.setUsername(sess.getAccount().getUserName());
			audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setOlddata(row.getString("EMAIL")+"|"+row.getString("FIRSTNAME")+"|"+row.getString("LASTNAME")+"|"+row.getString("MSISDN")+"|"+row.getString("USERNAME"));
		
			audit.insert();
			return true;
		}else{
			return false;
		}
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username).size()>0;
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
				if (f.get(this) != null && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
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

				if (f.get(this) != null)
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
