package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.ManageAccount;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.v.JsonView;
import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class LockAccountCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
			String userid = this.params.get("UserId").toString();
			String locked = this.params.get("Locked").toString();
			String stat = "";
			if(locked.equalsIgnoreCase("YES")){
				stat = "Locked";
			} else {
				stat = "Unlocked";
			}
			ManageAccount manageacct = new ManageAccount();
			manageacct.setUserid(userid);
			manageacct.setIsLocked(locked);
			manageacct.setAuthorizedSession(sess);
			if(manageacct.update()){
				if(locked.equalsIgnoreCase("YES")){EmailUtils.sendLock(userid,manageacct.getAuthorizedSession().getIpAddress(),manageacct.getAuthorizedSession().getAccount().getUserName());	
				}else{EmailUtils.sendunlock(userid,manageacct.getAuthorizedSession().getIpAddress(),manageacct.getAuthorizedSession().getAccount().getUserName());}	
				manageacct.setState(new ObjectState("00", "Successfully "+stat+" the account"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(manageacct.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(userid);
	    		audit.setLog("Successfully "+stat+" the account");
	    		audit.setStatus("00");
	    		audit.setUserid(manageacct.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(manageacct.getAuthorizedSession().getAccount().getUserName());
	    		audit.setSessionid(manageacct.getAuthorizedSession().getId());
	    		audit.setBrowser(manageacct.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(manageacct.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(manageacct.getAuthorizedSession().getPortalverion());
			    audit.setOs(manageacct.getAuthorizedSession().getOs());
			    audit.setUserslevel(manageacct.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.setData("USERID:"+userid+"|LOCKED:"+locked);
			    audit.setRequest(this.params.toString());
			    
	    		audit.insert();
				return new JsonView(manageacct);  
			}else{
				manageacct.setState(new ObjectState("99", "System busy"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(manageacct.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(userid);
	    		audit.setLog("System busy");
	    		audit.setStatus("99");
	    		audit.setUserid(manageacct.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(manageacct.getAuthorizedSession().getAccount().getUserName());
	    		audit.setSessionid(manageacct.getAuthorizedSession().getId());
	    		audit.setBrowser(manageacct.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(manageacct.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(manageacct.getAuthorizedSession().getPortalverion());
			    audit.setOs(manageacct.getAuthorizedSession().getOs());
			    audit.setUserslevel(manageacct.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.setData("USERID:"+userid+"|LOCKED:"+locked);
			    audit.setRequest(this.params.toString());
	    		audit.insert();
				return new JsonView(manageacct);  
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
		return "MANAGEUSERACCOUNT";
	}

	@Override
	public int getId() {
		
		return 1932;
	}
		
}
