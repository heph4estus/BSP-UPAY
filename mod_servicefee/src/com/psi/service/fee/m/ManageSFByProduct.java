package com.psi.service.fee.m;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ManageSFByProduct extends Model{

	protected Long minamount;
	protected Long maxamount;
	protected String percentage;
	protected Long fixed;
	protected String ibayadcode;
	protected String id;
	protected String password;
	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTFEES WHERE ID = ?", this.id);
		int res =  SystemInfo.getDb().QueryUpdate("UPDATE TBLPRODUCTFEES SET MINAMOUNT=?,MAXAMOUNT=?,PERCENTAGE=?,FIXED=?,IBAYADCODE=? WHERE ID = ?", this.minamount,this.maxamount,this.percentage,this.fixed, this.ibayadcode,this.id);
		
		if(res>0){
			AuditTrail audit  = new AuditTrail();
			audit.setIp(sess.getIpAddress());
			audit.setModuleid(String.valueOf(this.getId()));
			audit.setEntityid(this.ibayadcode);
			audit.setLog("Successfully updated Service Fee");
			audit.setStatus("00");
			audit.setUserid(sess.getAccount().getId());
			audit.setUsername(sess.getAccount().getUserName());
			audit.setSessionid(sess.getId());
			audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData("NEW DETAILS: "+this.id+"|"+this.ibayadcode+"|"+this.minamount+"|"+this.maxamount+"|"+this.fixed+"|"+this.percentage
		    			+"| OLD DETAILS: "+row.getString("ID")+"|"+row.getString("IBAYADCODE")+"|"+row.getString("MINAMOUNT")+"|"+row.getString("MAXAMOUNT")+"|"+row.getString("FIXED")+"|"+row.getString("PERCENTAGE"));
			audit.insert();
			return true;
		}else{
			return false;
		}
	}
	
	public boolean delete(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTFEES  WHERE ID = ?", this.id);
		int res = SystemInfo.getDb().QueryUpdate("DELETE FROM TBLPRODUCTFEES  WHERE ID = ?", this.id);
		if(res>0){
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(sess.getIpAddress());
    		audit.setModuleid(String.valueOf(this.getId()));
    		audit.setEntityid(id);
    		audit.setLog("Successfully deleted Service Fee");
    		audit.setStatus("00");
    		audit.setUserid(sess.getAccount().getId());
    		audit.setUsername(sess.getAccount().getUserName());
    		audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData(this.id+"|"+row.getString("IBAYADCODE")+"|"+row.getString("MINAMOUNT")+"|"+row.getString("MAXAMOUNT")+"|"+row.getString("FIXED")+"|"+row.getString("PERCENTAGE")+"|"+row.getString("CREATEDBY"));
    		audit.insert();
			return true;
		}else{
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(sess.getIpAddress());
    		audit.setModuleid(String.valueOf(this.getId()));
    		audit.setEntityid(id);
    		audit.setLog("System is currently busy, please try again later");
    		audit.setStatus("99");
    		audit.setUserid(sess.getAccount().getId());
    		audit.setUsername(sess.getAccount().getUserName());
    		audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData(this.id+"|"+row.getString("IBAYADCODE")+"|"+row.getString("MINAMOUNT")+"|"+row.getString("MAXAMOUNT")+"|"+row.getString("FIXED")+"|"+row.getString("PERCENTAGE")+"|"+row.getString("CREATEDBY"));
    		audit.insert();
			return false;
		}
	}
	
	public boolean existalready(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTFEES WHERE MINAMOUNT = ? AND MAXAMOUNT = ? AND PERCENTAGE = ? AND FIXED = ? AND IBAYADCODE = ?", this.minamount,this.maxamount,this.percentage,this.fixed,this.ibayadcode).size()>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTFEES WHERE ID = ?", this.id).size()>0;
	}
	
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)",sess.getAccount().getId() ,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
	public boolean authorize(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTFEES WHERE ID= ? AND UPPER(CREATEDBY) = ? ",this.id,sess.getAccount().getUserName().toUpperCase()).size()>0;
	}
	
	public Long getMinamount() {
		return minamount;
	}
	public void setMinamount(Long minamount) {
		this.minamount = minamount;
	}
	public Long getMaxamount() {
		return maxamount;
	}
	public void setMaxamount(Long maxamount) {
		this.maxamount = maxamount;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public Long getFixed() {
		return fixed;
	}
	public void setFixed(Long fixed) {
		this.fixed = fixed;
	}
	public String getIbayadcode() {
		return ibayadcode;
	}
	public void setIbayadcode(String ibayadcode) {
		this.ibayadcode = ibayadcode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
