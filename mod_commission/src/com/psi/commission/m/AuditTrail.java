package com.psi.commission.m;

import com.tlc.common.DbWrapper;
import com.tlc.common.SystemInfo;

public class AuditTrail {
	
	private String username;
	private String ip;
	private String log;
	private String sessionid;
	private String status;
	private String entityid;
	private Integer userid;
	private String moduleid;
	private String image;
	private String data;
	private String userslevel;
	private String portalversion;
	private String browserversion;
	private String browser;
	private String os;
	private String request;
	private String remarks;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEntityid() {
		return entityid;
	}
	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	
	
	public boolean insert(){
		
		try{
		DbWrapper db = SystemInfo.getDb();
		
		int	res = db.QueryUpdate("INSERT INTO TBLAUDITTRAIL (USERNAME,USERID,LOG,IP,TIMESTAMP,MODULE,SESSIONID,STATUS,ENTITYID,DATA,IMAGE,BROWSER,BROWSERVERSION,PORTALVERSION,OS,USERSLEVEL,REQUEST,REMARKS) VALUES(?,?,?,?,CURRENT_TIMESTAMP,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				
				this.username,
				this.userid,
				this.log,
				this.ip,
				this.moduleid,
				this.sessionid,
				this.status,
				this.entityid,
				this.data,
				this.image,
				this.browser,
				this.browserversion,
				this.portalversion,
				this.os,
				this.userslevel,
				this.request,
				this.remarks);
	
		return res>0;
		}catch(NullPointerException e){
			
			
		}catch(Exception e){
			
		}
		return false;
		
		
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getUserslevel() {
		return userslevel;
	}
	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
	}
	public String getPortalversion() {
		return portalversion;
	}
	public void setPortalversion(String portalversion) {
		this.portalversion = portalversion;
	}
	public String getBrowserversion() {
		return browserversion;
	}
	public void setBrowserversion(String browserversion) {
		this.browserversion = browserversion;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
