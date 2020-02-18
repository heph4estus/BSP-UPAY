package com.psi.tariff.plans.commission.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.plans.commission.m.AvailComByTariffCollection;
import com.psi.tariff.plans.v.CollectionView;
import com.psi.tariff.plans.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class AvailComByTariffColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String service = this.params.get("Type").toString();
				String provider = this.params.get("Provider").toString();
				String category = this.params.get("Category").toString();
				String tariff = this.params.get("TariffGroup").toString();
				AvailComByTariffCollection useraccounts = new AvailComByTariffCollection();
				useraccounts.setService(service);
				useraccounts.setProvider(provider);
				useraccounts.setCategory(category);
				useraccounts.setTariff(tariff);
				useraccounts.setAuthorizedSession(sess);
				if(useraccounts.hasRows()){
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(useraccounts.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(provider);
		    		audit.setLog("Successfully fecth available revenue data");
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
				    audit.setData(service+"|"+provider+"|"+category);
				    audit.insert();
					return new CollectionView("00",useraccounts);
				} else {
					ObjectState state = new ObjectState("01","No provider revenue found");
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(useraccounts.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(provider);
		    		audit.setLog("No provider revenue found");
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
				    audit.setData(service+"|"+provider+"|"+category);
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
		return "AVAILABLETARIFFCOM";
	}

	@Override
	public int getId() {
		return 9540;
	}

}
