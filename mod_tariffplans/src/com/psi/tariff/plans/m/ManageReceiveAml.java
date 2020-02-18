package com.psi.tariff.plans.m;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ManageReceiveAml extends Model{

	protected Long minamount;
	protected Long maxamount;
	protected Long maxamountday;
	protected Long maxamountweek;
	protected Long maxamountmonth;
	protected String maxtransday;
	protected String maxtransweek;
	protected String maxtransmonth;
	protected String type;
	protected String id;
	
	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPERECEIVE WHERE ID = ?", this.id);
		int res =  SystemInfo.getDb().QueryUpdate("UPDATE TBLAMLACCOUNTTYPERECEIVE SET MINAMOUNT=?,MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=?,MAXTRANSDAY=?,MAXTRANSWEEK=?,MAXTRANSMONTH=? WHERE ID = ?", this.minamount,this.maxamount,this.maxamountday,this.maxamountweek, this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.id);
		if(res>0){
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(sess.getIpAddress());
    		audit.setModuleid("9532");
    		audit.setEntityid(type);
    		audit.setLog("Successfully updated AML Receive");
    		audit.setStatus("00");
    		audit.setUserid(sess.getAccount().getId());
    		audit.setUsername(sess.getAccount().getUserName());
    		audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData("NEW DETAILS: "+type+"|"+id+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxamountweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth
		    		+"OLD DETAILS: "+row.getString("KEY")+"|"+id+"|"+row.getString("MINAMOUNT")+"|"+row.getString("MAXAMOUNT")+"|"+row.getString("MAXAMOUNTDAY")+"|"+row.getString("MAXAMOUNTWEEK")+"|"+row.getString("MAXAMOUNTMONTH")+"|"+row.getString("MAXTRANSDAY")+"|"+row.getString("MAXTRANSWEEK")+"|"+row.getString("MAXTRANSMONTH"));
    		
    		audit.insert();
			return true;
		}else{
			return false;
		}
	}
	
	public boolean delete(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPERECEIVE  WHERE ID = ?", this.id);
		int res =  SystemInfo.getDb().QueryUpdate("DELETE FROM TBLAMLACCOUNTTYPERECEIVE  WHERE ID = ?", this.id);
		if(res>0){
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(sess.getIpAddress());
    		audit.setModuleid("9534");
    		audit.setEntityid(this.type);
    		audit.setLog("Successfully deleted AML Receive");
    		audit.setStatus("00");
    		audit.setUserid(sess.getAccount().getId());
    		audit.setUsername(sess.getAccount().getUserName());
    		audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData(row.getString("KEY")+"|"+id+"|"+row.getString("MINAMOUNT")+"|"+row.getString("MAXAMOUNT")+"|"+row.getString("MAXAMOUNTDAY")+"|"+row.getString("MAXAMOUNTWEEK")+"|"+row.getString("MAXAMOUNTMONTH")+"|"+row.getString("MAXTRANSDAY")+"|"+row.getString("MAXTRANSWEEK")+"|"+row.getString("MAXTRANSMONTH"));
    		audit.insert();
			return true;
		}else{
			return false;
		}
	}
	
	public boolean existalready(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPERECEIVE WHERE MINAMOUNT = ? AND MAXAMOUNT = ? AND MAXAMOUNTDAY = ? AND MAXAMOUNTWEEK = ? AND MAXAMOUNTMONTH = ? AND MAXTRANSDAY=? AND MAXTRANSWEEK=? AND MAXTRANSMONTH=? AND KEY=?", this.minamount,this.maxamount,this.maxamountday,this.maxamountweek, this.maxamountmonth,this.maxtransday,this.maxtransweek,this.maxtransmonth,this.type).size()>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAMLACCOUNTTYPERECEIVE WHERE ID = ?", this.id).size()>0;
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

	public Long getMaxamountday() {
		return maxamountday;
	}

	public void setMaxamountday(Long maxamountday) {
		this.maxamountday = maxamountday;
	}

	public Long getMaxamountweek() {
		return maxamountweek;
	}

	public void setMaxamountweek(Long maxamountweek) {
		this.maxamountweek = maxamountweek;
	}

	public Long getMaxamountmonth() {
		return maxamountmonth;
	}

	public void setMaxamountmonth(Long maxamountmonth) {
		this.maxamountmonth = maxamountmonth;
	}

	public String getMaxtransday() {
		return maxtransday;
	}

	public void setMaxtransday(String maxtransday) {
		this.maxtransday = maxtransday;
	}

	public String getMaxtransweek() {
		return maxtransweek;
	}

	public void setMaxtransweek(String maxtransweek) {
		this.maxtransweek = maxtransweek;
	}

	public String getMaxtransmonth() {
		return maxtransmonth;
	}

	public void setMaxtransmonth(String maxtransmonth) {
		this.maxtransmonth = maxtransmonth;
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
