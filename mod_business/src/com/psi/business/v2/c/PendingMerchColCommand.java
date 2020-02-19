package com.psi.business.v2.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.business.v.CollectionView;
import com.psi.business.v.NoDataFoundView;
import com.psi.business.v2.m.PendingMerchCollection;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class PendingMerchColCommand extends UICommand{
	
	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				PendingMerchCollection col = new PendingMerchCollection();
	
				col.setAuthorizedSession(sess);
				
				if(col.hasRows()){
				 AuditTrail audit  = new AuditTrail();
		    		audit.setIp(col.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid("");
		    		audit.setLog("Successfully fetched data");
		    		audit.setStatus("00");
		    		audit.setUserid(col.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(col.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(col.getAuthorizedSession().getId());
		    		audit.setBrowser(col.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(col.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(col.getAuthorizedSession().getPortalverion());
				    audit.setOs(col.getAuthorizedSession().getOs());
				    audit.setUserslevel(col.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData("Display Details");
		    		audit.insert();
					return new CollectionView("00",col);  
				}else{
						ObjectState state = new ObjectState("01", "No data found");
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(col.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid("");
			    		audit.setLog("No data found");
			    		audit.setStatus("01");
			    		audit.setUserid(col.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(col.getAuthorizedSession().getAccount().getUserName());
			    		audit.setSessionid(col.getAuthorizedSession().getId());
			    		audit.setBrowser(col.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(col.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(col.getAuthorizedSession().getPortalverion());
					    audit.setOs(col.getAuthorizedSession().getOs());
					    audit.setUserslevel(col.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setRequest(this.params.toString());
					    audit.setData("Display Details");
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
		return "MERCHANTPNGCOLLECTION";
	}

	@Override
	public int getId() {
		return 1107;
	}

}
