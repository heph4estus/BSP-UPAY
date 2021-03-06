package com.psi.subs.management.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.subs.management.m.ManageBlackList;
import com.psi.subs.management.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class DeleteBlackListCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String filename = this.params.get("FileName").toString();
				String batch = this.params.get("Batch").toString();
				String username = this.params.get("UserName").toString();
				String password = this.params.get("Password").toString();
				ManageBlackList create = new ManageBlackList();
				create.setUsername(username);
				create.setPassword(password);
				create.setFilename(filename);
				create.setAuthorizedSession(sess);
					
				try {

					if(!create.isvalid()){
						create.setState(new ObjectState("03", "Incorrect username/password"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(create.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(filename+"|"+batch+"|"+username);
				    		audit.setLog(create.getState().getMessage());
				    		audit.setStatus(create.getState().getCode());
				    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
				    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
				  		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
				  		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
				  		  	audit.setOs(create.getAuthorizedSession().getOs());
				  		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
				  		  	audit.setData(filename+"|"+batch+"|"+username);
				  		  	audit.setRequest(this.params.toString());
				    		audit.insert();
							return new JsonView(create); 

						}
						if(create.delete(batch)){
								create.setState(new ObjectState("00", "Successfully deleted blacklisted customers."));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(create.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(filename+"|"+batch+"|"+username);
					    		audit.setLog(create.getState().getMessage());
					    		audit.setStatus(create.getState().getCode());
					    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
					  		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
					  		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
					  		  	audit.setOs(create.getAuthorizedSession().getOs());
					  		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
					  		  	audit.setData(filename+"|"+batch+"|"+username);
					  		  	audit.setRequest(this.params.toString());
					    		audit.insert();
								return new JsonView(create); 

							}else{
								create.setState(new ObjectState("01", create.getState().getMessage()));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(create.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(filename+"|"+batch+"|"+username);
					    		audit.setLog(create.getState().getMessage());
					    		audit.setStatus(create.getState().getCode());
					    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
					  		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
					  		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
					  		  	audit.setOs(create.getAuthorizedSession().getOs());
					  		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
					  		  	audit.setData(filename+"|"+batch+"|"+username);
					  		  	audit.setRequest(this.params.toString());
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
		return "DELETEBLBATCHUPLOAD";
	}

	@Override
	public int getId() {
		return 1007;
	}

}
