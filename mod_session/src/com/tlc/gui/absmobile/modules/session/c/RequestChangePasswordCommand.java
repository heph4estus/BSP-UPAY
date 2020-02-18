package com.tlc.gui.absmobile.modules.session.c;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.Logger;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.m.SessionPassword;
import com.tlc.gui.absmobile.modules.session.utils.EmailUtils;
import com.tlc.gui.absmobile.modules.session.v.GenericJsonView;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class RequestChangePasswordCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionPassword cpwd = new SessionPassword(this.params.get("OLDPASS"));
		SessionPassword newPassword = new SessionPassword(this.params.get("NEWPASS"));
		String userslevel = this.params.get("USERSLEVEL");
		String token = PasswordGenerator.generatePassword(9);
	 	
		IView v=null;
		try {
			sess = ExistingSession.parse(this.reqHeaders);
			cpwd.setAuthorizedSession(sess);
			cpwd.setToken(token);
			cpwd.setUserslevel(userslevel);
			
			if(sess.exists()){
				if(cpwd.correct()){
					//if(!cpwd.sameWithPreviousPassword(this.params.get("NEWPASS"))){
						if(!cpwd.isPassHistory(this.params.get("NEWPASS"))){
							cpwd.requestChange(newPassword);
							if(cpwd.getDetails()){
								cpwd.setState(new ObjectState("00","Request successfully sent. Please check associated email for token."));
								try{
									EmailUtils.sendToken(cpwd.getEmail().toString(),cpwd.getToken());
								}catch (Exception e) { 
									Logger.LogServer(e);
								}
							}
						}
						
					//}
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
		return "REQUESTCHANGEPASSWORD";
	}

	@Override
	public int getId() {
		return 3904;
	}

}
