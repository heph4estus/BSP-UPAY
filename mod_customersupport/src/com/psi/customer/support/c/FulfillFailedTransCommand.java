package com.psi.customer.support.c;

import java.text.ParseException;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.customer.support.m.ManageFailedTransactions;
import com.psi.customer.support.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class FulfillFailedTransCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String referenceid = this.params.get("ReferenceId").toString();
				String traceid = this.params.get("TraceId").toString();
				String receiptvalidation = this.params.get("ReceiptValidation").toString();
				String accountnumber = this.params.get("AccountNumber").toString();
				ManageFailedTransactions create = new ManageFailedTransactions();
				create.setReferenceid(referenceid);
				create.setTraceid(traceid);
				create.setReceiptvalidation(receiptvalidation);
				create.setAccountnumber(accountnumber);
				create.setAuthorizedSession(sess);
					
					try {		
						if(create.fulfill()){
								create.setState(new ObjectState("00", create.getState().getMessage()));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(create.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(referenceid);
					    		audit.setLog(create.getState().getMessage());
					    		audit.setStatus(create.getState().getCode());
					    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(create.getAuthorizedSession().getId());
					    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
							    audit.setOs(create.getAuthorizedSession().getOs());
							    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(referenceid+"|"+traceid+"|"+receiptvalidation+"|"+accountnumber);
					    		audit.insert();
								return new JsonView(create); 

							}else{
								create.setState(new ObjectState("01", create.getState().getMessage()));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(create.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(referenceid);
					    		audit.setLog(create.getState().getMessage());
					    		audit.setStatus(create.getState().getCode());
					    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(create.getAuthorizedSession().getId());
					    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
							    audit.setOs(create.getAuthorizedSession().getOs());
							    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(referenceid+"|"+traceid+"|"+receiptvalidation+"|"+accountnumber);
					    		audit.insert();
								return new JsonView(create); 
							}
					} catch (ParseException e) {
						e.printStackTrace();
						create.setState(new ObjectState("99", "System busy"));
						return new JsonView(create); 
						
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
		return "FULFILLFAILED";
	}

	@Override
	public int getId() {
		return 96061;
	}

}
