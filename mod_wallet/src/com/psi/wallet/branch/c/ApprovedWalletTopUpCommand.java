package com.psi.wallet.branch.c;
import com.psi.audit.trail.m.AuditTrail;
import com.psi.wallet.branch.m.ApprovedWalletTopup;
import com.psi.wallet.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ApprovedWalletTopUpCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				ApprovedWalletTopup topup = new ApprovedWalletTopup();
				
				
				String id = this.params.get("Id");
				String linkid = this.params.get("LinkId");
				String password = this.params.get("Password");
				String userslevel = this.params.get("Userslevel");

				topup.setId(id);
				topup.setLinkid(linkid);
				topup.setPassword(password);
			    topup.setAuthorizedSession(sess);
			    topup.getData();
					try {
						if(!topup.validate()){
							topup.setState(new ObjectState("01", "Incorrect password"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(topup.getState().getMessage());
				    		audit.setStatus(topup.getState().getCode());
				    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(topup.getAuthorizedSession().getId());
				    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
						    audit.setOs(topup.getAuthorizedSession().getOs());
						    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData(topup.getAuditdata());
				    		audit.insert();
							return new JsonView(topup); 
						}
						if(!topup.isExist()){
							topup.setState(new ObjectState("02", "Transaction not found"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(topup.getState().getMessage());
				    		audit.setStatus(topup.getState().getCode());
				    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(topup.getAuthorizedSession().getId());
				    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
						    audit.setOs(topup.getAuthorizedSession().getOs());
						    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData(topup.getAuditdata());
				    		audit.insert();
							return new JsonView(topup); 
						}
						if(topup.isapproved()){
							topup.setState(new ObjectState("03", "Already approved"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(topup.getState().getMessage());
				    		audit.setStatus(topup.getState().getCode());
				    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(topup.getAuthorizedSession().getId());
				    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
						    audit.setOs(topup.getAuthorizedSession().getOs());
						    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData(topup.getAuditdata());
				    		audit.insert();
							return new JsonView(topup); 
						}
						if(topup.approved()){
							if(topup.sendMail()){
								topup.setState(new ObjectState("0", "Successfully approved allocation request"));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(id);
					    		audit.setLog(topup.getState().getMessage());
					    		audit.setStatus(topup.getState().getCode());
					    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(topup.getAuthorizedSession().getId());
					    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
							    audit.setOs(topup.getAuthorizedSession().getOs());
							    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(topup.getAuditdata());
					    		audit.insert();
								return new JsonView(topup); 
							}else{
								topup.setState(new ObjectState("04", "Unable to send email"));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(id);
					    		audit.setLog(topup.getState().getMessage());
					    		audit.setStatus(topup.getState().getCode());
					    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(topup.getAuthorizedSession().getId());
					    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
							    audit.setOs(topup.getAuthorizedSession().getOs());
							    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(topup.getAuditdata());
					    		audit.insert();
								return new JsonView(topup); 
							}
							
						}else{
							topup.setState(new ObjectState("05", "Unable to approved"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(topup.getState().getMessage());
				    		audit.setStatus(topup.getState().getCode());
				    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(topup.getAuthorizedSession().getId());
				    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
						    audit.setOs(topup.getAuthorizedSession().getOs());
						    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData(topup.getAuditdata());
				    		audit.insert();
							return new JsonView(topup); 
						}
					} catch (Exception e) {
						topup.setState(new ObjectState("99", "System busy"));
						return new JsonView(topup); 
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
		return "APPROVEDWALLETTOPUP";
	}

	@Override
	public int getId() {
		return 1302;
	}

}
