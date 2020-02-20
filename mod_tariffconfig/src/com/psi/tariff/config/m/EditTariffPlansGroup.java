package com.psi.tariff.config.m;

import java.lang.reflect.Field;

import org.json.simple.JSONArray;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.config.util.Tariffs;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class EditTariffPlansGroup extends Tariffs{
	
	@SuppressWarnings("unchecked")
	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		String[] plansArr = this.getPlanid();
		JSONArray plansJsonArr = new JSONArray();
		StringBuilder planBuilder = new StringBuilder();
		String plans = "INSERT INTO TBLTARIFFCONFIG (TARIFFGROUP, PLANID, TYPE, TABLENAME) VALUES";
		
		try{
			for(String m : plansArr){
				plansJsonArr.add(m);
			}
			
			 for(int i = 0; i < plansJsonArr.size() ; i++){
				 String t = plansArr[i];
				 String[] parts = t.split("-");
				 
				 planBuilder.append(plans);
				 planBuilder.append("('"+this.tarrifGroup+"',"+parts[0]+",'"+this.type+"','"+parts[1]+"')");
				 planBuilder.append(";\n");
				}
		}
		 catch(Exception e){
			 
		 }
		
		 String sql = "BEGIN\n"
				    + "DELETE FROM TBLTARIFFCONFIG WHERE TARIFFGROUP = ? AND TYPE=?;\n"
				    + planBuilder
				   + "END;\n";
		 
		 int res =  SystemInfo.getDb().QueryUpdate(sql.toString(),this.tarrifGroup,this.type);
		 if(res>0){
			 AuditTrail audit  = new AuditTrail();
	    		audit.setIp(sess.getIpAddress());
	    		audit.setModuleid("8040");
	    		audit.setEntityid(this.tarrifGroup);
	    		audit.setLog("Tariff Plans Group Successfully Updated");
	    		audit.setStatus("00");
	    		audit.setUserid(sess.getAccount().getId());
	    		audit.setUsername(sess.getAccount().getUserName());
	    		audit.setSessionid(sess.getId());
	    		audit.setBrowser(sess.getBrowser());
			    audit.setBrowserversion(sess.getBrowserversion());
			    audit.setPortalversion(sess.getPortalverion());
			    audit.setOs(sess.getOs());
			    audit.setUserslevel(sess.getAccount().getGroup().getName());
			    audit.setData("NEW DETAILS: "+this.tarrifGroup+"|"+this.type+"|"+this.getPlanid()
			    		+"OLD DETAILS: "+this.tarrifGroup+"|"+this.type+"|"+SystemInfo.getDb().QueryArray("SELECT PLANID FROM TBLTARIFFCONFIG WHERE TARIFFGROUP = ? AND TYPE=?", new Integer(0), this.tarrifGroup,this.type));
	    		audit.insert();
	    		return true;
		 }else{
			 return false;
		 }
		
		
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
