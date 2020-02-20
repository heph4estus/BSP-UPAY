package com.psi.tariff.plans.m;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.DataRow;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ManageLoyalty extends Model{

	protected Long forevery;
	protected Long earn;
	protected String type;
	protected String id;
	
	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLLOYALTYSETTINGS WHERE ID = ?", this.id);
		int res =  SystemInfo.getDb().QueryUpdate("UPDATE TBLLOYALTYSETTINGS SET FOREVERY=?,EARN=?,TYPE=? WHERE ID = ?", this.forevery,this.earn,this.type,this.id);
		if(res>0){
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(sess.getIpAddress());
    		audit.setModuleid("9529");
    		audit.setEntityid(this.type);
    		audit.setLog("Successfully updated Loyalty");
    		audit.setStatus("00");
    		audit.setUserid(sess.getAccount().getId());
    		audit.setUsername(sess.getAccount().getUserName());
    		audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData("ID: "+id+"|TYPE:"+type+"|EARN:"+earn+"|FOREVERY:"+ LongUtil.toString(forevery));
    		audit.setOlddata("ID: "+id+"|TYPE:"+row.getString("TYPE")+"|EARN:"+row.getString("EARN")+"|FOREVERY:"+LongUtil.toString(Long.valueOf(row.getString("FOREVERY"))));
		    audit.insert();
    		return true;
		}else{
			return false;
		}
	}
	
	public boolean delete(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLLOYALTYSETTINGS  WHERE ID = ?", this.id);
		int res =  SystemInfo.getDb().QueryUpdate("DELETE FROM TBLLOYALTYSETTINGS  WHERE ID = ?", this.id);
		if(res>0){
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(sess.getIpAddress());
    		audit.setModuleid("9530");
    		audit.setEntityid(this.type);
    		audit.setLog("Successfully deleted Loyalty");
    		audit.setStatus("00");
    		audit.setUserid(sess.getAccount().getId());
    		audit.setUsername(sess.getAccount().getUserName());
    		audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData("ID: "+id+"|TYPE:"+type+"|EARN:"+earn+"|FOREVERY:"+ LongUtil.toString(forevery));
		    audit.setOlddata("ID: "+id+"|TYPE:"+row.getString("TYPE")+"|EARN:"+row.getString("EARN")+"|FOREVERY:"+LongUtil.toString(Long.valueOf(row.getString("FOREVERY"))));
    		audit.insert();
			return true;
		}else{
			return false;
		}
	}
	
	public boolean existalready(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLLOYALTYSETTINGS WHERE FOREVERY = ? AND EARN = ? AND TYPE = ? ", this.forevery,this.earn,this.type).size()>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLLOYALTYSETTINGS WHERE ID = ?", this.id).size()>0;
	}
	
	
	public Long getForevery() {
		return forevery;
	}

	public void setForevery(Long forevery) {
		this.forevery = forevery;
	}

	public Long getEarn() {
		return earn;
	}

	public void setEarn(Long earn) {
		this.earn = earn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
