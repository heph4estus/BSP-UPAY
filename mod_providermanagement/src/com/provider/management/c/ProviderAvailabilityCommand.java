package com.provider.management.c;

import com.provider.management.m.AuditTrail;
import com.provider.management.m.ProviderAvailability;
import com.provider.management.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ProviderAvailabilityCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String provider = this.params.get("Provider").toString();
				String status = this.params.get("Status").toString();
				
				ProviderAvailability a = new ProviderAvailability();
				a.setProvider(provider);
				a.setStatus(status);
				a.setAuthorizedSession(sess);
				if(a.setAvailability()){
					a.setState(new ObjectState("00", "Provider Successfully "+status=="ENABLE"?"ENABLED":"DISABLED"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(a.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(provider+"|"+status);
		    		audit.setLog(a.getState().getMessage());
		    		audit.setStatus(a.getState().getCode());
		    		audit.setUserid(a.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(a.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(a.getAuthorizedSession().getId());
		    		audit.setBrowser(a.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(a.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(a.getAuthorizedSession().getPortalverion());
				    audit.setOs(a.getAuthorizedSession().getOs());
				    audit.setUserslevel(a.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(provider+"|"+status);
				    audit.insert();
					return new JsonView(a);  
				} else {
					a.setState(new ObjectState("99", "System busy"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(a.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(provider+"|"+status);
		    		audit.setLog(a.getState().getMessage());
		    		audit.setStatus(a.getState().getCode());
		    		audit.setUserid(a.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(a.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(a.getAuthorizedSession().getId());
		    		audit.setBrowser(a.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(a.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(a.getAuthorizedSession().getPortalverion());
				    audit.setOs(a.getAuthorizedSession().getOs());
				    audit.setUserslevel(a.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(provider+"|"+status);
				    audit.insert();
					return new JsonView(a);  
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
		return "SETPROVIDERAVAILABILITY";
	}

	@Override
	public int getId() {
		return 5500;
	}

}
