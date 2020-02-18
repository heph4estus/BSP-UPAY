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

public class ActivateAccountCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		String userid = this.params.get("UserId").toString();
		String status = this.params.get("Status").toString();
		String stat = "";
		if(status.equalsIgnoreCase("ACTIVE")){
			stat = "Activated";
		} else {
			stat = "Deactivated";
		}
		ManageAccount mgtacct = new ManageAccount();
		mgtacct.setUserid(userid);
		mgtacct.setStatus(status);
		mgtacct.setAuthorizedSession(sess);
		if(mgtacct.activate()){
			if(status.equalsIgnoreCase("ACTIVE")){EmailUtils.sendActive(userid,mgtacct.getAuthorizedSession().getIpAddress(),mgtacct.getAuthorizedSession().getAccount().getUserName());	
			}else{EmailUtils.sendDeactive(userid,mgtacct.getAuthorizedSession().getIpAddress(),mgtacct.getAuthorizedSession().getAccount().getUserName());}	

			mgtacct.setState(new ObjectState("00", "Account Successfully "+stat));
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(mgtacct.getAuthorizedSession().getIpAddress());
    		audit.setModuleid(String.valueOf(this.getId()));
    		audit.setEntityid(userid);
    		audit.setLog("Account Successfully "+stat);
    		audit.setStatus("00");
    		audit.setUserid(mgtacct.getAuthorizedSession().getAccount().getId());
    		audit.setUsername(mgtacct.getAuthorizedSession().getAccount().getUserName());
    		audit.setSessionid(mgtacct.getAuthorizedSession().getId());
    		audit.setBrowser(mgtacct.getAuthorizedSession().getBrowser());
		    audit.setBrowserversion(mgtacct.getAuthorizedSession().getBrowserversion());
		    audit.setPortalversion(mgtacct.getAuthorizedSession().getPortalverion());
		    audit.setOs(mgtacct.getAuthorizedSession().getOs());
		    audit.setUserslevel(mgtacct.getAuthorizedSession().getAccount().getGroup().getName());
		    audit.setData("USERID:"+userid+"|STATUS:"+status);
		    audit.setRequest(this.params.toString());
		    
    		audit.insert();
			return new JsonView(mgtacct);  
		} else {
			mgtacct.setState(new ObjectState("99", "System busy"));
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(mgtacct.getAuthorizedSession().getIpAddress());
    		audit.setModuleid(String.valueOf(this.getId()));
    		audit.setEntityid(userid);
    		audit.setLog("System busy");
    		audit.setStatus("99");
    		audit.setUserid(mgtacct.getAuthorizedSession().getAccount().getId());
    		audit.setUsername(mgtacct.getAuthorizedSession().getAccount().getUserName());
    		audit.setSessionid(mgtacct.getAuthorizedSession().getId());
    		audit.setBrowser(mgtacct.getAuthorizedSession().getBrowser());
		    audit.setBrowserversion(mgtacct.getAuthorizedSession().getBrowserversion());
		    audit.setPortalversion(mgtacct.getAuthorizedSession().getPortalverion());
		    audit.setOs(mgtacct.getAuthorizedSession().getOs());
		    audit.setUserslevel(mgtacct.getAuthorizedSession().getAccount().getGroup().getName());
		    audit.setData("USERID:"+userid+"|STATUS:"+status);
		    audit.setRequest(this.params.toString());
		    
    		audit.insert();
			return new JsonView(mgtacct);  
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
		return "ACTIVATEUSERACCOUNT";
	}

	@Override
	public int getId() {
		return 1933;
	}

}
