package com.psi.tariff.group.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.group.m.ManageTariffGroup;
import com.psi.tariff.group.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditTariffGroupCommand extends UICommand {
	@Override
	public IView execute() {
		ExistingSession sess = null;
		IView v = null;
		ManageTariffGroup create = new ManageTariffGroup();
		AuditTrail audit = new AuditTrail();
		String groupname = "";
		try {
			sess = ExistingSession.parse(this.reqHeaders);
			if (sess.exists()) {

				groupname = this.params.get("GroupName");
				String description = this.params.get("Description");
				String password = this.params.get("Password");
				String id = this.params.get("Id");

				create.setGroupname(groupname);
				create.setDescription(description);
				create.setPassword(password);
				create.setId(id);
				create.setAuthorizedSession(sess);

				try {
					if (!create.validate()) {
						create.setState(new ObjectState("02", "Incorrect Password"));
						v = new JsonView(create);
					}
					if (!create.authorize()) {
						create.setState(
								new ObjectState("03", "Updating entry created by the other user is not allowed"));
						v = new JsonView(create);
					}
					if (!create.existid()) {
						create.setState(new ObjectState("01", "Group Name Doesn't Exist"));
						v = new JsonView(create);

					}
					if (create.update()) {
						create.setState(new ObjectState("00", "Successfully updated Tariff Group"));
						return new JsonView(create);

					} else {
						create.setState(new ObjectState("99", "System busy"));
						return new JsonView(create);
					}
				} catch (Exception e) {
					create.setState(new ObjectState("99", "System busy"));
					return new JsonView(create);
				}
			} else {
				UISession u = new UISession(null);
				u.setState(new ObjectState("TLC-3902-01"));
				v = new SessionView(u);
			}
		} catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("TLC-3902-01"));
			v = new SessionView(u);
			Logger.LogServer(e);
		} catch (Exception e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("TLC-3902-01"));
			v = new SessionView(u);
			Logger.LogServer(e);
		} finally {
			audit.setIp(create.getAuthorizedSession().getIpAddress());
			audit.setModuleid(String.valueOf(this.getId()));
			audit.setEntityid(groupname);
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
			audit.setData(create.toString());
			audit.insert();
		}
		return v;
	}

	@Override
	public String getKey() {
		return "EDITTARIFFGROUP";
	}

	@Override
	public int getId() {
		return 1784;
	}

}
//public class EditTariffGroupCommand extends UICommand{
//	@Override
//	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
//		
//				String groupname = this.params.get("GroupName");
//				String description = this.params.get("Description");
//				String password = this.params.get("Password");
//				String id = this.params.get("Id");
//				
//				ManageTariffGroup create = new ManageTariffGroup();
//				create.setGroupname(groupname);
//				create.setDescription(description);	
//				create.setPassword(password);
//				create.setId(id);
//				create.setAuthorizedSession(sess);
//					
//					try {
//						if(!create.validate()){
//							create.setState(new ObjectState("02", "Incorrect Password"));
//							AuditTrail audit  = new AuditTrail();
//				    		audit.setIp(create.getAuthorizedSession().getIpAddress());
//				    		audit.setModuleid(String.valueOf(this.getId()));
//				    		audit.setEntityid(groupname);
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
//						    audit.setData(groupname+"|"+description);
//				    		audit.insert();
//							return new JsonView(create); 
//						}
//						if(!create.authorize()){
//							create.setState(new ObjectState("03", "Updating entry created by the other user is not allowed"));
//							AuditTrail audit  = new AuditTrail();
//				    		audit.setIp(create.getAuthorizedSession().getIpAddress());
//				    		audit.setModuleid(String.valueOf(this.getId()));
//				    		audit.setEntityid(groupname);
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
//						    audit.setData(groupname+"|"+description);
//						    
//				    		audit.insert();
//							return new JsonView(create); 
//						}
//						if(!create.existid()){
//							create.setState(new ObjectState("01", "Group Name Doesn't Exist"));
//							AuditTrail audit  = new AuditTrail();
//				    		audit.setIp(create.getAuthorizedSession().getIpAddress());
//				    		audit.setModuleid(String.valueOf(this.getId()));
//				    		audit.setEntityid(groupname);
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
//						    audit.setData(groupname+"|"+description);
//						    
//				    		audit.insert();
//							return new JsonView(create); 
//
//						}
//						if(create.update()){
//								create.setState(new ObjectState("00", "Successfully updated Tariff Group"));
//								
//								return new JsonView(create); 
//
//							}else{
//								create.setState(new ObjectState("99", "System busy"));
//								AuditTrail audit  = new AuditTrail();
//					    		audit.setIp(create.getAuthorizedSession().getIpAddress());
//					    		audit.setModuleid(String.valueOf(this.getId()));
//					    		audit.setEntityid(groupname);
//					    		audit.setLog(create.getState().getMessage());
//					    		audit.setStatus(create.getState().getCode());
//					    		audit.setUserid(create.getAuthorizedSession().getAccount().getId());
//					    		audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
//					    		audit.setSessionid(create.getAuthorizedSession().getId());
//					    		audit.setBrowser(create.getAuthorizedSession().getBrowser());
//							    audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
//							    audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
//							    audit.setOs(create.getAuthorizedSession().getOs());
//							    audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
//							    audit.setRequest(this.params.toString());
//							    audit.setData(groupname+"|"+description);
//							    
//					    		audit.insert();
//								return new JsonView(create); 
//							}
//						} catch (Exception e) {
//							create.setState(new ObjectState("99", "System busy"));
//							return new JsonView(create); 
//						}
//			}else{	
//				UISession u = new UISession(null);
//			    u.setState(new ObjectState("TLC-3902-01"));
//			    v = new SessionView(u);
//			}				 
//		}catch (SessionNotFoundException e) {
//			UISession u = new UISession(null);
//		    u.setState(new ObjectState("TLC-3902-01"));
//		    v = new SessionView(u);
//			Logger.LogServer(e);
//	} catch (Exception e) {
//		UISession u = new UISession(null);
//	    u.setState(new ObjectState("TLC-3902-01"));
//	    v = new SessionView(u);
//		Logger.LogServer(e);
//	}return v;
//	}
//
//	@Override
//	public String getKey() {
//		return "EDITTARIFFGROUP";
//	}
//
//	@Override
//	public int getId() {
//		return 1784;
//	}
//
//}
