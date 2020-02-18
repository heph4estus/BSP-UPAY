package com.psi.commission.c;

import java.text.ParseException;

import com.psi.commission.m.AuditTrail;
import com.psi.commission.m.ManageCommission;
import com.psi.commission.v.JsonView;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditCommissionCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String accountnumber = this.params.get("AccountNumber").toString();
				String type = this.params.get("Type").toString();
				Long fixed = LongUtil.toLong(this.params.get("Fixed").toString());
				Long percent = LongUtil.toLong(this.params.get("Percent").toString());
				String expirydate = this.params.get("ExpiryDate").toString();
				
				ManageCommission create = new ManageCommission();
				create.setAccountnumber(accountnumber);
				create.setType(type);
				create.setFixed(fixed);
				create.setPercent(percent);
				create.setExpirydate(expirydate);
				create.setAuthorizedSession(sess);
					
					try {	
						if(!create.exist()){
							create.setState(new ObjectState("01","Commission does not exist"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(create.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber+"|"+type+"|"+fixed+"|"+percent+"|"+expirydate);
				    		audit.setLog(create.getState().getMessage());
				    		audit.setStatus(create.getState().getCode());
				    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(create.getAuthorizedSession().getId());
				    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
						    audit.setOs(create.getAuthorizedSession().getOs());
						    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData(accountnumber+"|"+type+"|"+fixed+"|"+percent+"|"+expirydate);
				    		audit.insert();
							return new JsonView(create); 

						}
						if(create.edit()){
								create.setState(new ObjectState("00", "Commission Updated Successfully"));
								DataRow old = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCOMMISSION WHERE TYPE = ? AND ACCOUNTNUMBER=?", type,accountnumber);
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(create.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(accountnumber+"|"+type+"|"+"OLD:"+old.getString("FIXED")+"|"+old.getString("PERCENT")+"|"+old.getString("EXPIRYDATE")+"|"+"NEW:"+fixed+"|"+percent+"|"+expirydate);
					    		audit.setLog(create.getState().getMessage());
					    		audit.setStatus(create.getState().getCode());
					    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(create.getAuthorizedSession().getId());
					    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
							    audit.setOs(create.getAuthorizedSession().getOs());
							    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(accountnumber+"|"+type+"|"+"OLD:"+old.getString("FIXED")+"|"+old.getString("PERCENT")+"|"+old.getString("EXPIRYDATE")+"|"+"NEW:"+fixed+"|"+percent+"|"+expirydate);
					    		audit.insert();
					    		return new JsonView(create); 

							}else{
								create.setState(new ObjectState("01", "System is Busy"));
								DataRow old = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCOMMISSION WHERE TYPE = ? AND ACCOUNTNUMBER=?", type,accountnumber);
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(create.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(accountnumber+"|"+type+"|"+"OLD:"+old.getString("FIXED")+"|"+old.getString("PERCENT")+"|"+old.getString("EXPIRYDATE")+"|"+"NEW:"+fixed+"|"+percent+"|"+expirydate);
					    		audit.setLog("System is Busy");
					    		audit.setStatus(create.getState().getCode());
					    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(create.getAuthorizedSession().getId());
					    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
							    audit.setOs(create.getAuthorizedSession().getOs());
							    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(accountnumber+"|"+type+"|"+"OLD:"+old.getString("FIXED")+"|"+old.getString("PERCENT")+"|"+old.getString("EXPIRYDATE")+"|"+"NEW:"+fixed+"|"+percent+"|"+expirydate);
					    		audit.insert();
								return new JsonView(create); 
							}
					} catch (Exception e) {
						create.setState(new ObjectState("99", "System busy"));
						return new JsonView(create); 
					}
			}else{	
				UISession u = new UISession(null);
			    u.setState(new ObjectState("TLC-3902-01"));
			    v = new SessionView(u);
			}				 
		}catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
		    u.setState(new ObjectState("TLC-3902-01"));
		    v = new SessionView(u);
			Logger.LogServer(e);
	} catch (Exception e) {
		UISession u = new UISession(null);
	    u.setState(new ObjectState("TLC-3902-01"));
	    v = new SessionView(u);
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "EDITCOMMISSION";
	}

	@Override
	public int getId() {
		return 1802;
	}

}
