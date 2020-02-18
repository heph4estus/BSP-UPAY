package com.psi.backoffice.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.backoffice.m.ManageRegisteredUser;
import com.psi.backoffice.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class DeleteRegisteredCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String id = this.params.get("Id").toString();
				String password = this.params.get("Password").toString();
					 	
				ManageRegisteredUser reg = new ManageRegisteredUser();
				
				reg.setPassword(password);
				reg.setId(id);
			    reg.setAuthorizedSession(sess);
				
				if(!reg.exist()){
					reg.setState(new ObjectState("01", "Account do not exist"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog("Account do not exist: "+id);
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
				    
		    		audit.insert();
					return new JsonView(reg);  
				}
				if(!reg.validate()){
					reg.setState(new ObjectState("02", "Incorrect password"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog("Account do not exist: "+id);
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
				    
		    		audit.insert();
					return new JsonView(reg);  
				}
					if(reg.delete()){
						reg.setState(new ObjectState("00", "Delete Succesfully"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog("Successfully deleted: "+id);
			    		audit.setStatus(reg.getState().getCode());
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
					    audit.setOs(reg.getAuthorizedSession().getOs());
					    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setData(id+"|"+reg.getUsername()+"|"+reg.getFirstname()+"|"+reg.getLastname()+"|"+reg.getEmail()+"|"+reg.getMsisdn());
					    audit.setRequest(this.params.toString());
					    
			    		audit.insert();
						return new JsonView(reg);  
					}else{
						reg.setState(new ObjectState("99", "System busy"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog("System is currently busy : "+id);
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
					    audit.setData(id+"|"+reg.getUsername()+"|"+reg.getFirstname()+"|"+reg.getLastname()+"|"+reg.getEmail()+"|"+reg.getMsisdn());					    
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
	}
		return v;
	}

	@Override
	public String getKey() {
		return "DELETEBACKOFFICEUSER";
	}

	@Override
	public int getId() {
		return 1003;
	}

}
