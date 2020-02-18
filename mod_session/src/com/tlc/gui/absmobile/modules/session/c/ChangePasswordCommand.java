package com.tlc.gui.absmobile.modules.session.c;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.m.SessionPassword;
import com.tlc.gui.absmobile.modules.session.v.GenericJsonView;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ChangePasswordCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionPassword cpwd = new SessionPassword();
	 	cpwd.setToken(this.params.get("TOKEN"));
	 	String force = this.params.get("ISFORCED").toString();
	 	IView v=null;
		try {
			sess = ExistingSession.parse(this.reqHeaders);
			cpwd.setAuthorizedSession(sess);
			
			if(sess.exists()){
				if(cpwd.isTokenValid()){
					if(cpwd.isTokenExpired()){
						if(force.equals("TRUE")){
							cpwd.forceChange();
						}else{
							cpwd.change();
						}
						cpwd.setState(new ObjectState("00","Success"));
					}
				}
				
				AuditTrail audit = new AuditTrail();
			    audit.setIp(cpwd.getAuthorizedSession().getIpAddress());
			    audit.setModuleid(String.valueOf(this.getId()));
			    audit.setEntityid(String.valueOf(cpwd.getAuthorizedSession().getToken().UserId));
			    audit.setLog(cpwd.getState().getMessage());
			    audit.setStatus(cpwd.getState().getCode());
			    audit.setUserid(cpwd.getAuthorizedSession().getToken().UserId);
			    audit.setUsername(cpwd.getAuthorizedSession().getAccount().getUserName());
			    audit.setSessionid(cpwd.getAuthorizedSession().getId());
			    audit.setBrowser(cpwd.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(cpwd.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(cpwd.getAuthorizedSession().getPortalverion());
			    audit.setOs(cpwd.getAuthorizedSession().getOs());
			    audit.setUserslevel(cpwd.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.insert();
				v= new GenericJsonView(cpwd);
			}else{
			   UISession u = new UISession(null);
		       u.setState(new ObjectState("TLC-3902-01"));
		       v = new SessionView(u);
			}
		} catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("TLC-3904-01"));
			v = new SessionView(u);
		}	 
		return v;
	}

	@Override
	public String getKey() {
		return "CPWD";
	}

	@Override
	public int getId() {
		return 3904;
	}

}
