package com.psi.role.management.m;


import org.json.simple.JSONArray;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;
import com.tlc.sky.pingen.arch.uc.acct.AbstractRegisteredPingenGroup;

public class RegisteredPingenGroup extends AbstractRegisteredPingenGroup {
	
	protected String modaudit;
	protected String portal;
	public RegisteredPingenGroup(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean exists() {
		UISession sess = this.getAuthorizedSession();
		
		DataRow dr =  this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?)", name);
			if(dr!=null){
				this.setSessionTimeout(dr.getInteger("SESSIONTIMEOUT"));
				this.setSearchRange(dr.getInteger("SEARCHRANGE"));
				this.setPasswordExpiry(dr.getInteger("PASSWORDEXPIRY"));
				this.setPasswordHistory(dr.getInteger("PASSWORDHISTORY"));
				this.setMinPassword(dr.getInteger("MINPASSWORD"));
				this.setAccountStatus(dr.getString("ACCOUNTSTATUS"));
				this.setHomePage(dr.getString("HOMEPAGE"));
				
//				this.db.QueryUpdate("INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?)",
//						sess.getToken().Username,sess.getToken().UserId,this.name+" Selected",sess.getToken().IpAddress,"3020",sess.getId(),"TLC-3020-00",this.id);
				
				return true;
				
			}
			else{
				
//				this.db.QueryUpdate("INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?)",
//						sess.getToken().Username,sess.getToken().UserId,"Failed to SELECT "+this.name,sess.getToken().IpAddress,"3020",sess.getId(),"TLC-3020-01",this.id);
				return false;
			}
		}
	
	public boolean duplicateEntry(String accountnumber)
	  {
	    DataRow dr = this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?) AND ACCOUNTNUMBER = ?",this.name,accountnumber);
	   return dr.size()>0;  
	  }
	
	public boolean duplicateEntry()
	  {
	    DataRow dr = this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?) AND UPPER(PORTAL) = ?",this.name,this.portal.toUpperCase());
	   return dr.size()>0;  
	  }
	
	public boolean duplicateWithOtherDealer(String accountnumber)
	  {
	    DataRow dr = this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?) AND PORTAL <> 'operator'",this.name);
	   return dr.size()>0;  
	  }
	

	@Override
	public boolean deactivate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update() {
		
		UISession sess = this.getAuthorizedSession();
		
		Integer[] modArr = this.getModules();
		Integer[] notifArr = this.getNotifications();
		JSONArray modJsonArr = new JSONArray();
		JSONArray notifJsonArr = new JSONArray();
		StringBuilder modulesBuilder = new StringBuilder();
		StringBuilder notifBuilder = new StringBuilder();
		String modules = "INSERT INTO TBLGROUPMODULES (MODULEID, GROUPID, PORTAL) VALUES";
		String notifications = "INSERT INTO TBLNOTIFICATIONMGR (MODULEID, GROUPID) VALUES";
		

		try{
			for(Integer m : modArr){
				modJsonArr.add(m);
			}
			
			 for(int i = 0; i < modJsonArr.size() ; i++){
				 modulesBuilder.append(modules);
				 modulesBuilder.append("("+modArr[i]+","+this.id+","+"'OPERATOR')");
				 
				 modulesBuilder.append(";\n");
				}
		}
		 catch(Exception e){
			 
		 }

		 try{
			 for(Integer m : notifArr){
				 notifJsonArr.add(m);
				}
				
				 for(int i = 0; i < notifJsonArr.size() ; i++){
					 notifBuilder.append(notifications);
					 notifBuilder.append("("+notifArr[i]+","+this.id+")");
					 notifBuilder.append(";\n");
					}
		 }
		 catch(Exception e){
			 
		 }
			 
		 
		
		 String sql = "BEGIN\n"
				    + "UPDATE TBLUSERSLEVEL SET SESSIONTIMEOUT = ?, PASSWORDEXPIRY = ?, MINPASSWORD = ?, PASSWORDHISTORY = ?,  SEARCHRANGE = ?, ACCOUNTSTATUS = ?, HOMEPAGE = ? WHERE USERSLEVEL = ? AND PORTAL='operator';\n"
				    + "DELETE FROM TBLGROUPMODULES WHERE GROUPID = ? AND PORTAL ='OPERATOR';\n"
				    + "DELETE FROM TBLNOTIFICATIONMGR WHERE GROUPID = ?;\n"
				    + modulesBuilder
				    + notifBuilder
				//    + "INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?);\n"
				    + "END;\n";
		 
		int dr =  this.db.QueryUpdate(sql.toString(),
		this.sessionTimeout,this.passwordExpiry,this.minPassword,this.passwordHistory,this.searchRange,this.accountStatus,this.homepage,this.name,this.id,this.id);
		//sess.getToken().Username,sess.getToken().UserId,this.name+" Updated Successfully",sess.getToken().IpAddress,"3040",sess.getId(),"TLC-3040-00",this.id);
		
		
		if (dr>0){
			DataRow row =  this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?)", name);
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(sess.getIpAddress());
    		audit.setModuleid(String.valueOf(this.getId()));
    		audit.setEntityid(name);
    		audit.setLog("Role Successfully Updated");
    		audit.setStatus("00");
    		audit.setUserid(sess.getAccount().getId());
    		audit.setUsername(sess.getAccount().getUserName());
    		audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setData(name+"|"+homepage+"|"+minPassword+"|"+this.accountStatus+"|"+this.getModaudit());
		    audit.setOlddata(row.getString("USERSLEVEL")+"|"+row.getString("HOMEPAGE")+"|"+row.getString("MINPASSWORD")+"|"+row.getString("ACCOUNTSTATUS")+"|"+SystemInfo.getDb().QueryScalar("SELECT LISTAGG(MODULEID,',') WITHIN GROUP (ORDER BY MODULEID) MODULEID FROM TBLGROUPMODULES WHERE GROUPID=?","",this.id));
		
		    audit.insert();
			return true;
		}
		else{
//			this.db.QueryUpdate("INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?)",
//					sess.getToken().Username,sess.getToken().UserId,"UserLevel Update Failed",sess.getToken().IpAddress,"3040",sess.getId(),"TLC-3040-01",this.id);
			return false;
		}
	}

	public String getModaudit() {
		return modaudit;
	}
	public void setModaudit(String modaudit) {
		this.modaudit = modaudit;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	
}
