package com.psi.branch.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.branch.m.ManageUpgrade;
import com.psi.branch.utils.EmailUtils;
import com.psi.branch.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ApproveUpgradeCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String accountnumber = this.params.get("AccountNumber");
				 	
				ManageUpgrade reg = new ManageUpgrade();
				
				reg.setAccountnumber(accountnumber);
				reg.getData();
				reg.setAuthorizedSession(sess);
						if(!reg.existupgrade()){
							reg.setState(new ObjectState("03", "Merchant account do not exist"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(reg.getAuthorizedSession().getId());
				    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
						    audit.setOs(reg.getAuthorizedSession().getOs());
						    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData("ACCOUNTNUMBER:"+accountnumber+"|BRANCH:"+reg.getAuditdata());
				    		audit.insert();
							return new JsonView(reg);
						}
						if(reg.approve()){
							reg.setState(new ObjectState("00", "Successfully upgraded merchant account"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(reg.getAuthorizedSession().getId());
				    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
						    audit.setOs(reg.getAuthorizedSession().getOs());
						    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData("ACCOUNTNUMBER:"+accountnumber+"|BRANCH:"+reg.getAuditdata());
				    		audit.insert();
							return new JsonView(reg);
						}else{
							reg.setState(new ObjectState("01", "Failed! Unable to upgrade merchant account"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(reg.getAuthorizedSession().getId());
				    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
						    audit.setOs(reg.getAuthorizedSession().getOs());
						    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData("ACCOUNTNUMBER:"+accountnumber+"|BRANCH:"+reg.getAuditdata());
				    		audit.insert();
							return new JsonView(reg);
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
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "APPROVEUPGRADE";
	}

	@Override
	public int getId() {
		return 1213;
	}

}
