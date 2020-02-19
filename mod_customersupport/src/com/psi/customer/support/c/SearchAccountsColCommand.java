package com.psi.customer.support.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.customer.support.m.CustomerCollection;
import com.psi.customer.support.v.CollectionView;
import com.psi.customer.support.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class SearchAccountsColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String accountnumber = this.params.get("AccountNumber").toString();
				CustomerCollection model = new CustomerCollection();
						model.setAccountnumber(accountnumber);
						model.setAuthorizedSession(sess);
						if(model.hasRows()){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid("");
				    		audit.setLog("Successfully fetch account data");
				    		audit.setStatus("00");
				    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(model.getAuthorizedSession().getId());
				    		audit.setBrowser(model.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(model.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(model.getAuthorizedSession().getPortalverion());
						    audit.setOs(model.getAuthorizedSession().getOs());
						    audit.setUserslevel(model.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData("ACCOUNTNUMBER:"+accountnumber);
				    		audit.insert();
							return new CollectionView("00",model);  
						}else{
								ObjectState state = new ObjectState("01", "No data found");
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid("");
					    		audit.setLog("No data found");
					    		audit.setStatus("01");
					    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(model.getAuthorizedSession().getId());
					    		audit.setBrowser(model.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(model.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(model.getAuthorizedSession().getPortalverion());
							    audit.setOs(model.getAuthorizedSession().getOs());
							    audit.setUserslevel(model.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData("ACCOUNTNUMBER:"+accountnumber);
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
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "ACCOUNTCOLLECTION";
	}

	@Override
	public int getId() {
		return 1940;
	}

}
