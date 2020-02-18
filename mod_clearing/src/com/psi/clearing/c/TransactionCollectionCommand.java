package com.psi.clearing.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.clearing.m.TransactionCollection;
import com.psi.clearing.v.CollectionView;
import com.psi.clearing.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class TransactionCollectionCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
			 String outletaccntno = this.params.get("AccountNumber").toString();
			 
			 TransactionCollection claimcol = new TransactionCollection();
			 
			 claimcol.setAccountnumber(outletaccntno);
			 claimcol.setAuthorizedSession(sess);
			 
			    if (claimcol.hasRows()) {
			    	AuditTrail audit  = new AuditTrail();
		    		audit.setIp(claimcol.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid("");
		    		audit.setLog("Successfully fetched data");
		    		audit.setStatus("00");
		    		audit.setUserid(claimcol.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(claimcol.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(claimcol.getAuthorizedSession().getId());
		    		audit.setBrowser(claimcol.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(claimcol.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(claimcol.getAuthorizedSession().getPortalverion());
				    audit.setOs(claimcol.getAuthorizedSession().getOs());
				    audit.setUserslevel(claimcol.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(outletaccntno);
		    		audit.insert();
			      return new CollectionView("00", claimcol);
			    }else{
			      ObjectState state = new ObjectState("01", "NO data found");
			      AuditTrail audit  = new AuditTrail();
		    		audit.setIp(claimcol.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid("");
		    		audit.setLog("No data found");
		    		audit.setStatus("01");
		    		audit.setUserid(claimcol.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(claimcol.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(claimcol.getAuthorizedSession().getId());
		    		audit.setBrowser(claimcol.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(claimcol.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(claimcol.getAuthorizedSession().getPortalverion());
				    audit.setOs(claimcol.getAuthorizedSession().getOs());
				    audit.setUserslevel(claimcol.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(outletaccntno);
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
		return "CUSTOMERTOMERCHANTTRANSCOL";
	}

	@Override
	public int getId() {
		return 1403;
	}

}
