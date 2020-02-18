package com.psi.wallet.branch.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.wallet.branch.m.PayoutCollection;
import com.psi.wallet.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class PayoutCollectionCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;

		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {

				String id = this.params.get("Id").toString();
				String type = this.params.get("Type").toString();
				PayoutCollection req = new PayoutCollection();
				
				req.setId(id);
				req.setAuthorizedSession(sess);					
					
					try {
					if(type.equals("PENDING")){
						if(req.reports()){
							req.setState(new ObjectState(req.getState().getCode(),req.getState().getMessage()));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(req.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(req.getState().getMessage());
				    		audit.setStatus(req.getState().getCode());
				    		audit.setUserid(req.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(req.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(req.getAuthorizedSession().getId());
				    		audit.setBrowser(req.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(req.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(req.getAuthorizedSession().getPortalverion());
						    audit.setOs(req.getAuthorizedSession().getOs());
						    audit.setUserslevel(req.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
				    		audit.setData(id+"|"+type);
				    		audit.insert();
							return new JsonView(req); 
						}else{
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(req.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(req.getState().getMessage());
				    		audit.setStatus(req.getState().getCode());
				    		audit.setUserid(req.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(req.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(req.getAuthorizedSession().getId());
				    		audit.setBrowser(req.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(req.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(req.getAuthorizedSession().getPortalverion());
						    audit.setOs(req.getAuthorizedSession().getOs());
						    audit.setUserslevel(req.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
				    		audit.setData(id+"|"+type);
				    		audit.insert();
							return new JsonView(req); 
						}
					}else if(type.equals("COMPLETED")){
						if(req.reportscompleted()){
							req.setState(new ObjectState(req.getState().getCode(),req.getState().getMessage()));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(req.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(req.getState().getMessage());
				    		audit.setStatus(req.getState().getCode());
				    		audit.setUserid(req.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(req.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(req.getAuthorizedSession().getId());
				    		audit.setBrowser(req.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(req.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(req.getAuthorizedSession().getPortalverion());
						    audit.setOs(req.getAuthorizedSession().getOs());
						    audit.setUserslevel(req.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
				    		audit.setData(id+"|"+type);
				    		audit.insert();
							return new JsonView(req); 
						}else{
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(req.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(req.getState().getMessage());
				    		audit.setStatus(req.getState().getCode());
				    		audit.setUserid(req.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(req.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(req.getAuthorizedSession().getId());
				    		audit.setBrowser(req.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(req.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(req.getAuthorizedSession().getPortalverion());
						    audit.setOs(req.getAuthorizedSession().getOs());
						    audit.setUserslevel(req.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
				    		audit.setData(id+"|"+type);
				    		audit.insert();
							return new JsonView(req); 
						}
					}else{
						if(req.reportsrejected()){
							req.setState(new ObjectState(req.getState().getCode(),req.getState().getMessage()));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(req.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(req.getState().getMessage());
				    		audit.setStatus(req.getState().getCode());
				    		audit.setUserid(req.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(req.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(req.getAuthorizedSession().getId());
				    		audit.setBrowser(req.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(req.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(req.getAuthorizedSession().getPortalverion());
						    audit.setOs(req.getAuthorizedSession().getOs());
						    audit.setUserslevel(req.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
				    		audit.setData(id+"|"+type);
				    		audit.insert();
							return new JsonView(req); 
						}else{
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(req.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(req.getState().getMessage());
				    		audit.setStatus(req.getState().getCode());
				    		audit.setUserid(req.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(req.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(req.getAuthorizedSession().getId());
				    		audit.setBrowser(req.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(req.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(req.getAuthorizedSession().getPortalverion());
						    audit.setOs(req.getAuthorizedSession().getOs());
						    audit.setUserslevel(req.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
				    		audit.setData(id+"|"+type);
				    		audit.insert();
							return new JsonView(req); 
						}
					}
					} catch (Exception e) {
						req.setState(new ObjectState("99", "System busy"));
						return new JsonView(req); 
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
		return "PAYOUTCOLLECTION";
	}

	@Override
	public int getId() {
		return 1320;
	}

}
