package com.psi.service.fee.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.service.fee.m.CompanyPSFCollection;
import com.psi.service.fee.v.CollectionView;
import com.psi.service.fee.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class CompanyPSFColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				CompanyPSFCollection model = new CompanyPSFCollection();
				String accountnumber = this.params.get("AccountNumber");
				long amount = LongUtil.toLong(this.params.get("Amount").toString());
				String product = this.params.get("Product");
				String moduleid = this.params.get("ModuleId").toString();
				model.setAccountnumber(accountnumber);
				model.setAmount(amount);
				model.setProduct(product);
				model.setModuleid(moduleid);
				model.setAuthorizedSession(sess);
					
						if(model.hasRows()){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid("");
				    		audit.setLog("Successfully fetched data");
				    		audit.setStatus("00");
				    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(sess.getAuthorizedSession().getId());
				    		audit.setBrowser(sess.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(sess.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(sess.getAuthorizedSession().getPortalverion());
						    audit.setOs(sess.getAuthorizedSession().getOs());
						    audit.setUserslevel(sess.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData(accountnumber+"|"+amount+"|"+product+"|"+moduleid);
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
					    		audit.setSessionid(sess.getAuthorizedSession().getId());
					    		audit.setBrowser(sess.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(sess.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(sess.getAuthorizedSession().getPortalverion());
							    audit.setOs(sess.getAuthorizedSession().getOs());
							    audit.setUserslevel(sess.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(accountnumber+"|"+amount+"|"+product+"|"+moduleid);
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
		return "SERVICEFEEBYCOMPANY";
	}

	@Override
	public int getId() {
		return 1010;
	}

}
