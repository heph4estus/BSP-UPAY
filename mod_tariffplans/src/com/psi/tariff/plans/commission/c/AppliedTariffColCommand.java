package com.psi.tariff.plans.commission.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.plans.commission.m.AppliedTariffCollection;
import com.psi.tariff.plans.v.CollectionView;
import com.psi.tariff.plans.v.JsonView;
import com.psi.tariff.plans.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class AppliedTariffColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String tariff = this.params.get("TariffGroup").toString();
				String type = this.params.get("Type").toString();
				AppliedTariffCollection model = new AppliedTariffCollection();
				model.setTariff(tariff);
				model.setType(type);
					model.setAuthorizedSession(sess);
					
						if(model.hasRows()){
							ObjectState state = new ObjectState("00","Successfully fetched applied tariff group data");
							model.setState(state);
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(tariff+"|"+type);
				    		audit.setLog("Successfully fetched applied tariff group data");
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
						    audit.setData(tariff+"|"+type);
				    		audit.insert();
				    		JsonView view = new JsonView(model);
							return view;  
						}else{
								ObjectState state = new ObjectState("01", "No data found");
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(tariff+"|"+type);
					    		audit.setLog("No applied tariff group found");
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
							    audit.setData(tariff+"|"+type);
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
		return "APPLIEDTARIFF";
	}

	@Override
	public int getId() {
		return 9541;
	}

}
