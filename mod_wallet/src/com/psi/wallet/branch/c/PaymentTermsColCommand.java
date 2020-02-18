package com.psi.wallet.branch.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.wallet.branch.m.PaymentTermsCollection;
import com.psi.wallet.v.CollectionView;
import com.psi.wallet.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class PaymentTermsColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String accountnumber = this.params.get("AccountNumber");
				
				PaymentTermsCollection topup = new PaymentTermsCollection();
				topup.setAccountnumber(accountnumber);
				topup.setAuthorizedSession(sess);
				if(topup.hasRows()){
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(accountnumber);
		    		audit.setLog("Successfully fetch data");
		    		audit.setStatus("00");
		    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(topup.getAuthorizedSession().getId());
		    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
				    audit.setOs(topup.getAuthorizedSession().getOs());
				    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
		    		audit.setData(accountnumber);
		    		audit.insert();
					return new CollectionView("00",topup);  
				}else{
						ObjectState state = new ObjectState("01", "No data found");
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(accountnumber);
			    		audit.setLog("No data found");
			    		audit.setStatus("01");
			    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
			    		audit.setSessionid(topup.getAuthorizedSession().getId());
			    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
					    audit.setOs(topup.getAuthorizedSession().getOs());
					    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setRequest(this.params.toString());
			    		audit.setData(accountnumber);
			    		audit.insert();
						return new NoDataFoundView(state); 
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
		return "PAYMENTTERMSCOL";
	}

	@Override
	public int getId() {
		return 1312;
	}

}
