package com.provider.management.c;

import com.provider.management.m.AuditTrail;
import com.provider.management.m.ProviderCollection;
import com.provider.management.v.CollectionView;
import com.provider.management.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ProviderCollectionCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String service = this.params.get("Service").toString();
				
				ProviderCollection useraccounts = new ProviderCollection();
				useraccounts.setService(service);
				useraccounts.setAuthorizedSession(sess);
				if(useraccounts.hasRows()){
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(useraccounts.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(service);
		    		audit.setLog("Successfully fecth provider");
		    		audit.setStatus("01");
		    		audit.setUserid(useraccounts.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(useraccounts.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(useraccounts.getAuthorizedSession().getId());
		    		audit.setBrowser(useraccounts.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(useraccounts.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(useraccounts.getAuthorizedSession().getPortalverion());
				    audit.setOs(useraccounts.getAuthorizedSession().getOs());
				    audit.setUserslevel(useraccounts.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(service);
				    audit.insert();
					return new CollectionView("00",useraccounts);
				} else {
					ObjectState state = new ObjectState("01","No provider Found");
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(useraccounts.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(service);
		    		audit.setLog("No provider found");
		    		audit.setStatus("01");
		    		audit.setUserid(useraccounts.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(useraccounts.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(useraccounts.getAuthorizedSession().getId());
		    		audit.setBrowser(useraccounts.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(useraccounts.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(useraccounts.getAuthorizedSession().getPortalverion());
				    audit.setOs(useraccounts.getAuthorizedSession().getOs());
				    audit.setUserslevel(useraccounts.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(service);
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
		return "PROVIDERCOL";
	}

	@Override
	public int getId() {
		return 5502;
	}

}
