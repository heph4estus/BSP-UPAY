package com.psi.tariff.plans.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.plans.m.ManageReceiveAml;
import com.psi.tariff.plans.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditReceiveAmlCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				
				Long minamount = LongUtil.toLong(this.params.get("MinAmount").toString());
				Long maxamount = LongUtil.toLong(this.params.get("MaxAmount").toString());
				Long maxamountday = LongUtil.toLong(this.params.get("MaxAmountDay").toString());
				Long maxamountweek = LongUtil.toLong(this.params.get("MaxAmountWeek").toString());
				Long maxamountmonth = LongUtil.toLong(this.params.get("MaxAmountMonth").toString());
				String maxtransday = this.params.get("MaxTransDay").toString();
				String maxtransweek = this.params.get("MaxTransWeek").toString();
				String maxtransmonth = this.params.get("MaxTransMonth").toString();
				String type = this.params.get("Key").toString();
				String id = this.params.get("Id").toString();
				ManageReceiveAml create = new ManageReceiveAml();
				create.setMaxamount(maxamount);
				create.setMinamount(minamount);
				create.setMaxamountday(maxamountday);
				create.setMaxamountweek(maxamountweek);
				create.setMaxamountmonth(maxamountmonth);
				create.setMaxtransday(maxtransday);
				create.setMaxtransweek(maxtransweek);
				create.setMaxtransmonth(maxtransmonth);
				create.setType(type);
				create.setId(id);
				create.setAuthorizedSession(sess);
					
					try {
						if(!create.exist()){
							create.setState(new ObjectState("01", "AML Receive Doesn't Exist"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(create.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(type);
				    		audit.setLog(create.getState().getMessage());
				    		audit.setStatus(create.getState().getCode());
				    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(create.getAuthorizedSession().getId());
				    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
						    audit.setOs(create.getAuthorizedSession().getOs());
						    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData("NEW DETAILS: "+type+"|"+id+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxamountweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
				    		
				    		audit.insert();
							return new JsonView(create); 

						}
						if(create.existalready()){
							create.setState(new ObjectState("02", "AML Receive Already Exist"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(create.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(type);
				    		audit.setLog(create.getState().getMessage());
				    		audit.setStatus(create.getState().getCode());
				    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(create.getAuthorizedSession().getId());
				    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
						    audit.setOs(create.getAuthorizedSession().getOs());
						    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData("NEW DETAILS: "+type+"|"+id+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxamountweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
				    		
				    		audit.insert();
							return new JsonView(create); 

						}
						if(create.update()){
								create.setState(new ObjectState("00", "Successfully updated AML Receive"));
								
								return new JsonView(create); 

							}else{
								create.setState(new ObjectState("99", "System busy"));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(create.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(type);
					    		audit.setLog(create.getState().getMessage());
					    		audit.setStatus(create.getState().getCode());
					    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(create.getAuthorizedSession().getId());
					    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
							    audit.setOs(create.getAuthorizedSession().getOs());
							    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData("NEW DETAILS: "+type+"|"+id+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxamountweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
					    		
					    		audit.insert();
								return new JsonView(create); 
							}
						} catch (Exception e) {
							create.setState(new ObjectState("99", "System busy"));
							return new JsonView(create); 
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
		return "EDITAMLRECEIVE";
	}

	@Override
	public int getId() {
		return 9532;
	}

}
