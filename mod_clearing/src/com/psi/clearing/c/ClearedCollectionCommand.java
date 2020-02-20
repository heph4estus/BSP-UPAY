package com.psi.clearing.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.clearing.m.ClearedCollection;
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

public class ClearedCollectionCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
			 String outletaccntno = this.params.get("AccountNumber").toString();
			 String datefrom = this.params.get("DateFrom");
			 String dateto = this.params.get("DateTo");
			 
			 ClearedCollection clrd = new ClearedCollection();
			 
			 clrd.setAccountnumber(outletaccntno);
			 clrd.setDatefrom(datefrom);
			 clrd.setDateto(dateto);
			 clrd.setAuthorizedSession(sess);
			 if((outletaccntno.equals("ALL"))){				 
				    if (clrd.all()) {
				    	AuditTrail audit  = new AuditTrail();
			    		audit.setIp(clrd.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid("");
			    		audit.setLog("Successfully fetched data");
			    		audit.setStatus("00");
			    		audit.setUserid(clrd.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(clrd.getAuthorizedSession().getAccount().getUserName());
			    		audit.setSessionid(clrd.getAuthorizedSession().getId());
			    		audit.setBrowser(clrd.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(clrd.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(clrd.getAuthorizedSession().getPortalverion());
					    audit.setOs(clrd.getAuthorizedSession().getOs());
					    audit.setUserslevel(clrd.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setRequest(this.params.toString());
					    audit.setData(clrd.toString());
			    		audit.insert();
				      return new CollectionView("00", clrd);
				    }else{
				      ObjectState state = new ObjectState("01", "NO data found");
				      AuditTrail audit  = new AuditTrail();
			    		audit.setIp(clrd.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid("");
			    		audit.setLog("No data found");
			    		audit.setStatus("01");
			    		audit.setUserid(clrd.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(clrd.getAuthorizedSession().getAccount().getUserName());
			    		audit.setSessionid(clrd.getAuthorizedSession().getId());
			    		audit.setBrowser(clrd.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(clrd.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(clrd.getAuthorizedSession().getPortalverion());
					    audit.setOs(clrd.getAuthorizedSession().getOs());
					    audit.setUserslevel(clrd.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setRequest(this.params.toString());
					    audit.setData(clrd.toString());
			    		audit.insert();
				      return new NoDataFoundView(state);
				    }
				 
			 }else{
			    if (clrd.hasRowsCompany()) {
			    	AuditTrail audit  = new AuditTrail();
		    		audit.setIp(clrd.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid("");
		    		audit.setLog("Successfully fetched data");
		    		audit.setStatus("00");
		    		audit.setUserid(clrd.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(clrd.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(clrd.getAuthorizedSession().getId());
		    		audit.setBrowser(clrd.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(clrd.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(clrd.getAuthorizedSession().getPortalverion());
				    audit.setOs(clrd.getAuthorizedSession().getOs());
				    audit.setUserslevel(clrd.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(clrd.toString());
		    		audit.insert();
			      return new CollectionView("00", clrd);
			    }else{
			      ObjectState state = new ObjectState("01", "NO data found");
			      AuditTrail audit  = new AuditTrail();
		    		audit.setIp(clrd.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid("");
		    		audit.setLog("No data found");
		    		audit.setStatus("01");
		    		audit.setUserid(clrd.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(clrd.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(clrd.getAuthorizedSession().getId());
		    		audit.setBrowser(clrd.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(clrd.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(clrd.getAuthorizedSession().getPortalverion());
				    audit.setOs(clrd.getAuthorizedSession().getOs());
				    audit.setUserslevel(clrd.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(clrd.toString());
		    		audit.insert();
			      return new NoDataFoundView(state);
			    }
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
		return "CLEAREDCOLLECTIONS";
	}

	@Override
	public int getId() {
		return 1401;
	}

}
