package com.psi.wallet.branch.c;

import java.text.ParseException;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.wallet.branch.m.Balances;
import com.psi.wallet.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class PaymentBalanceCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String accountnumber = this.params.get("AccountNumber");

				Balances reg = new Balances();
				reg.setAuthorizedSession(sess);
				
				try {
						if(reg.paymentbalance(accountnumber)){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
				    		audit.setLog("Successfully Fetch Balance");
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
				    		audit.setData(accountnumber);
				    		audit.insert();
							return new JsonView(reg); 
						}else{
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
				    		audit.setLog("System is currently busy, please try again.");
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
				    		audit.setData(accountnumber);
				    		audit.insert();
							return new JsonView(reg); 
						}
					} catch (ParseException e) {
						e.printStackTrace();
						reg.setState(new ObjectState("99", "System busy"));
						return new JsonView(reg); 
						
					} catch (Exception e) {
						reg.setState(new ObjectState("99", "System busy"));
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
		return "PAYMENTBALN";
	}

	@Override
	public int getId() {
		return 39071;
	}

}
