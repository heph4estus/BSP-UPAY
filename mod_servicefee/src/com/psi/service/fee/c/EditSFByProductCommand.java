package com.psi.service.fee.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.service.fee.m.ManageSFByProduct;
import com.psi.service.fee.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditSFByProductCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				
				Long minamount = LongUtil.toLong(this.params.get("MinAmount").toString());
				Long maxamount = LongUtil.toLong(this.params.get("MaxAmount").toString());
				String percentage = this.params.get("Percentage").toString();
				Long fixed = LongUtil.toLong(this.params.get("Fixed").toString());
				String type = this.params.get("Type").toString();
				String id = this.params.get("Id").toString();
				String password = this.params.get("Password").toString();
				ManageSFByProduct create = new ManageSFByProduct();
				create.setMaxamount(maxamount);
				create.setMinamount(minamount);
				create.setPercentage(percentage);
				create.setFixed(fixed);
				create.setIbayadcode(type);
				create.setId(id);
				create.setPassword(password);
				create.setAuthorizedSession(sess);
					
					try {
						if(!create.validate()){
							create.setState(new ObjectState("03", "Incorrect Password"));
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
						    audit.setData(id+"|"+type+"|"+minamount+"|"+maxamount+"|"+fixed+"|"+percentage);
				    		audit.insert();
							return new JsonView(create); 
						}
						if(!create.authorize()){
							create.setState(new ObjectState("04", "Updating entry created by the other user is not allowed"));
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
						    audit.setData(id+"|"+type+"|"+minamount+"|"+maxamount+"|"+fixed+"|"+percentage);
				    		audit.insert();
							return new JsonView(create); 
						}
						if(!create.exist()){
							create.setState(new ObjectState("01", "Service Fee Doesn't Exist"));
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
						    audit.setData(id+"|"+type+"|"+minamount+"|"+maxamount+"|"+fixed+"|"+percentage);
				    		audit.insert();
							return new JsonView(create); 

						}
//						if(create.existalready()){
//							create.setState(new ObjectState("02", "Service Fee Already Exist"));
//							AuditTrail audit  = new AuditTrail();
//				    		audit.setIp(create.getAuthorizedSession().getIpAddress());
//				    		audit.setModuleid(String.valueOf(this.getId()));
//				    		audit.setEntityid(type);
//				    		audit.setLog(create.getState().getMessage());
//				    		audit.setStatus(create.getState().getCode());
//				    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
//				    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
//				    		audit.setSessionid(create.getAuthorizedSession().getId());
//				    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
//						    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
//						    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
//						    audit.setOs(create.getAuthorizedSession().getOs());
//						    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
//						    audit.setRequest(this.params.toString());
//						    audit.setData(id+"|"+type+"|"+minamount+"|"+maxamount+"|"+fixed+"|"+percentage);
//				    		audit.insert();
//							return new JsonView(create); 
//
//						}
						if(create.update()){
								create.setState(new ObjectState("00", "Successfully updated Service Fee"));
								
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
							    audit.setData(id+"|"+type+"|"+minamount+"|"+maxamount+"|"+fixed+"|"+percentage);
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
		return "EDITSERVICEFEEBYPRODUCT";
	}

	@Override
	public int getId() {
		return 9526;
	}

}
