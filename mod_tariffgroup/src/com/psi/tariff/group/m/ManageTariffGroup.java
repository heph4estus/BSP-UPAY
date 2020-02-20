package com.psi.tariff.group.m;

import java.lang.reflect.Field;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ManageTariffGroup extends Model{
	
	protected String tariffGroup;
	protected String description;
	protected String password;
	protected String id;

	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFGROUP WHERE ID = ?", this.id);
		int res =  SystemInfo.getDb().QueryUpdate("UPDATE TBLTARIFFGROUP  SET GROUPNAME=?, DESCRIPTION = ?,DATEMODIFIED=SYSDATE WHERE ID = ?", this.tariffGroup, this.description,this.id);
		if(res > 0){
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(sess.getIpAddress());
    		audit.setModuleid(String.valueOf(this.getId()));
    		audit.setEntityid(tariffGroup);
    		audit.setLog("Successfully updated Tariff Group");
    		audit.setStatus("00");
    		audit.setUserid(sess.getAccount().getId());
    		audit.setUsername(sess.getAccount().getUserName());
    		audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData("TARIFFGROUP:"+this.tariffGroup+"|DESCRIPTION:"+description);
		    audit.setOlddata("TARIFFGROUP:"+row.getString("GROUPNAME")+"|DESCRIPTION:"+row.getString("DESCRIPTION"));
		    
    		audit.insert();
    		return true;
		}else{
			return false;
		}
	}
	
	public boolean delete(){
		StringBuilder str = new StringBuilder();
		str.append("BEGIN\n");
		str.append("DELETE FROM TBLTARIFFGROUP WHERE GROUPNAME = ?;\n");
		str.append("DELETE FROM TBLTARIFFCONFIG WHERE TARIFFGROUP = ?;\n");
		str.append("DELETE FROM TBLCOMPANYTARIFF WHERE TARIFFGROUP = ?;\n");
		str.append("END;\n");
		return SystemInfo.getDb().QueryUpdate(str.toString(), this.tariffGroup,this.tariffGroup,SystemInfo.getDb().QueryScalar("SELECT ID FROM TBLTARIFFGROUP WHERE GROUPNAME = ?", "", this.tariffGroup))>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFGROUP WHERE GROUPNAME = ?", this.tariffGroup).size()>0;
	}
	
	public boolean existid(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFGROUP WHERE ID = ?", this.id).size()>0;
	}
	
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)",sess.getAccount().getId() ,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
	public boolean authorize(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFGROUP WHERE ID = ? AND UPPER(CREATEDBY) = ? ",this.id,sess.getAccount().getUserName().toUpperCase()).size()>0;
	}
	
	public boolean authorizebygrouname(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFGROUP WHERE GROUPNAME = ? AND UPPER(CREATEDBY) = ? ",this.tariffGroup,sess.getAccount().getUserName().toUpperCase()).size()>0;
	}

	public String getGroupname() {
		return tariffGroup;
	}

	public void setGroupname(String groupname) {
		this.tariffGroup = groupname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
				if (f.get(this) != null && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
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

				if (f.get(this) != null && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
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
