package com.provider.management.c;

import com.provider.management.m.AuditTrail;
import com.provider.management.m.ProviderCategoryCollection;
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

public class ProviderCategoryColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String provider = this.params.get("Provider").toString();
				
				ProviderCategoryCollection useraccounts = new ProviderCategoryCollection();
				useraccounts.setProvider(provider);
				useraccounts.setAuthorizedSession(sess);
				if(useraccounts.hasRows()){
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(useraccounts.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(provider);
		    		audit.setLog("Successfully fetch provider category");
		    		audit.setStatus("00");
		    		audit.setUserid(useraccounts.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(useraccounts.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(useraccounts.getAuthorizedSession().getId());
		    		audit.setBrowser(useraccounts.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(useraccounts.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(useraccounts.getAuthorizedSession().getPortalverion());
				    audit.setOs(useraccounts.getAuthorizedSession().getOs());
				    audit.setUserslevel(useraccounts.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(provider);
				    audit.insert();
					return new CollectionView("00",useraccounts);
				} else {
					ObjectState state = new ObjectState("01","No provider category Found");
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(useraccounts.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(provider);
		    		audit.setLog("No provider category found");
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
				    audit.setData(provider);
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
		return "PROVIDERCATEGORYCOL";
	}

	@Override
	public int getId() {
		return 5503;
	}

}