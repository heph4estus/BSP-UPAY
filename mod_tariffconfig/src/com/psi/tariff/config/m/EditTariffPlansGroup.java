package com.psi.tariff.config.m;

import org.json.simple.JSONArray;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.config.util.Tariffs;
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
				 planBuilder.append("('"+this.groupname+"',"+parts[0]+",'"+this.type+"','"+parts[1]+"')");
				 planBuilder.append(";\n");
				}
		}
		 catch(Exception e){
			 
		 }
		
		 String sql = "BEGIN\n"
				    + "DELETE FROM TBLTARIFFCONFIG WHERE TARIFFGROUP = ? AND TYPE=?;\n"
				    + planBuilder
				   + "END;\n";
		 
		 int res =  SystemInfo.getDb().QueryUpdate(sql.toString(),this.groupname,this.type);
		 if(res>0){
			 AuditTrail audit  = new AuditTrail();
	    		audit.setIp(sess.getIpAddress());
	    		audit.setModuleid("8040");
	    		audit.setEntityid(this.groupname);
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
			    audit.setData("NEW DETAILS: "+this.groupname+"|"+this.type+"|"+this.getPlanid()
			    		+"OLD DETAILS: "+this.groupname+"|"+this.type+"|"+SystemInfo.getDb().QueryArray("SELECT PLANID FROM TBLTARIFFCONFIG WHERE TARIFFGROUP = ? AND TYPE=?", new Integer(0), this.groupname,this.type));
	    		audit.insert();
	    		return true;
		 }else{
			 return false;
		 }
		
		
	}

}
