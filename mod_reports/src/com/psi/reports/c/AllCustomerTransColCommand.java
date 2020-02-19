package com.psi.reports.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.reports.m.AllCustomerTransCollection;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

/**
 * 19/02/2020
 * Remodel audit trail - MVO
 *
 */
public class AllCustomerTransColCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		IView v = null;
		AllCustomerTransCollection model = new AllCustomerTransCollection();
		String code = "";
		AuditTrail audit = new AuditTrail();
		try {
			sess = ExistingSession.parse(this.reqHeaders);
			if (sess.exists()) {

				code = this.params.get("BranchCode").toString();
				String datefrom = this.params.get("DateFrom").toString();
				String dateto = this.params.get("DateTo").toString();

				model.setBranch(code);
				model.setDatefrom(datefrom);
				model.setDateto(dateto);
				model.setAuthorizedSession(sess);
				
				if ((code.equals("ALL"))) {
					if (model.hasRows()) {
						audit.setLog("Successfully fetched data");
						audit.setStatus("00");
						v = new CollectionView("00", model);
					} else {
						ObjectState state = new ObjectState("01", "No data found");
						audit.setLog("No data found");
						audit.setStatus("01");
						v = new NoDataFoundView(state);
					}
				} else {
					if (model.getTransCol()) {
						audit.setLog("Successfully fetched data");
						audit.setStatus("00");
						v = new CollectionView("00", model);
					} else {
						ObjectState state = new ObjectState("01", "No data found");
						audit.setLog("No data found");
						audit.setStatus("01");
						v = new NoDataFoundView(state);
					}
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
			audit.setIp(model.getAuthorizedSession().getIpAddress());
			audit.setModuleid(String.valueOf(this.getId()));
			audit.setEntityid(code);
			audit.setUserid(model.getAuthorizedSession().getAccount().getId());
			audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
			audit.setSessionid(model.getAuthorizedSession().getId());
			audit.setBrowser(model.getAuthorizedSession().getBrowser());
			audit.setBrowserversion(model.getAuthorizedSession().getBrowserversion());
			audit.setPortalversion(model.getAuthorizedSession().getPortalverion());
			audit.setOs(model.getAuthorizedSession().getOs());
			audit.setUserslevel(model.getAuthorizedSession().getAccount().getGroup().getName());
			audit.setRequest(this.params.toString());
			audit.setData(model.toString());
			audit.insert();
		}
		return v;
	}

	@Override
	public String getKey() {
		return "CUSTOMERTRANSACTIONHISTORY";
	}

	@Override
	public int getId() {
		return 1512;
	}

}
//public class AllCustomerTransColCommand extends UICommand{
//
//	@Override
//	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
//		
//		
//		String code = this.params.get("BranchCode").toString();
//		String datefrom = this.params.get("DateFrom").toString();
//		String dateto = this.params.get("DateTo").toString();
//		
//		AllCustomerTransCollection model = new AllCustomerTransCollection();
//						model.setBranch(code);
//						model.setDatefrom(datefrom);
//						model.setDateto(dateto);
//						model.setAuthorizedSession(sess);
//				if((code.equals("ALL"))){
//						if(model.hasRows()){
//							AuditTrail audit  = new AuditTrail();
//				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
//				    		audit.setModuleid(String.valueOf(this.getId()));
//				    		audit.setEntityid(code);
//				    		audit.setLog("Successfully fetched data");
//				    		audit.setStatus("00");
//				    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
//				    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
//				    		audit.setSessionid(model.getAuthorizedSession().getId());
//				    		audit.setBrowser(model.getAuthorizedSession().getBrowser());
//						    audit.setBrowserversion(model.getAuthorizedSession().getBrowserversion());
//						    audit.setPortalversion(model.getAuthorizedSession().getPortalverion());
//						    audit.setOs(model.getAuthorizedSession().getOs());
//						    audit.setUserslevel(model.getAuthorizedSession().getAccount().getGroup().getName());
//						    audit.setRequest(this.params.toString());
//						    audit.setData(code+"|"+datefrom+"|"+dateto);
//				    		audit.insert();
//							return new CollectionView("00",model);  
//						}else{
//								ObjectState state = new ObjectState("01", "No data found");
//								AuditTrail audit  = new AuditTrail();
//					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
//					    		audit.setModuleid(String.valueOf(this.getId()));
//					    		audit.setEntityid(code);
//					    		audit.setLog("No data found");
//					    		audit.setStatus("01");
//					    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
//					    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
//					    		audit.setSessionid(model.getAuthorizedSession().getId());
//					    		audit.setBrowser(model.getAuthorizedSession().getBrowser());
//							    audit.setBrowserversion(model.getAuthorizedSession().getBrowserversion());
//							    audit.setPortalversion(model.getAuthorizedSession().getPortalverion());
//							    audit.setOs(model.getAuthorizedSession().getOs());
//							    audit.setUserslevel(model.getAuthorizedSession().getAccount().getGroup().getName());
//							    audit.setRequest(this.params.toString());
//							    audit.setData(code+"|"+datefrom+"|"+dateto);
//					    		audit.insert();
//								return new NoDataFoundView(state); 
//						}
//				}else{
//					if(model.getTransCol()){
//						AuditTrail audit  = new AuditTrail();
//			    		audit.setIp(model.getAuthorizedSession().getIpAddress());
//			    		audit.setModuleid(String.valueOf(this.getId()));
//			    		audit.setEntityid(code);
//			    		audit.setLog("Successfully fetched data");
//			    		audit.setStatus("00");
//			    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
//			    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
//			    		audit.setSessionid(model.getAuthorizedSession().getId());
//			    		audit.setBrowser(model.getAuthorizedSession().getBrowser());
//					    audit.setBrowserversion(model.getAuthorizedSession().getBrowserversion());
//					    audit.setPortalversion(model.getAuthorizedSession().getPortalverion());
//					    audit.setOs(model.getAuthorizedSession().getOs());
//					    audit.setUserslevel(model.getAuthorizedSession().getAccount().getGroup().getName());
//					    audit.setRequest(this.params.toString());
//					    audit.setData(code+"|"+datefrom+"|"+dateto);
//			    		audit.insert();
//						return new CollectionView("00",model);  
//					}else{
//							ObjectState state = new ObjectState("01", "No data found");
//							AuditTrail audit  = new AuditTrail();
//				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
//				    		audit.setModuleid(String.valueOf(this.getId()));
//				    		audit.setEntityid(code);
//				    		audit.setLog("No data found");
//				    		audit.setStatus("01");
//				    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
//				    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
//				    		audit.setSessionid(model.getAuthorizedSession().getId());
//				    		audit.setBrowser(model.getAuthorizedSession().getBrowser());
//						    audit.setBrowserversion(model.getAuthorizedSession().getBrowserversion());
//						    audit.setPortalversion(model.getAuthorizedSession().getPortalverion());
//						    audit.setOs(model.getAuthorizedSession().getOs());
//						    audit.setUserslevel(model.getAuthorizedSession().getAccount().getGroup().getName());
//						    audit.setRequest(this.params.toString());
//						    audit.setData(code+"|"+datefrom+"|"+dateto);
//				    		audit.insert();
//							return new NoDataFoundView(state); 
//					}
//				}
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
//}
//
//	@Override
//	public String getKey() {
//		return "CUSTOMERTRANSACTIONHISTORY";
//	}
//
//	@Override
//	public int getId() {
//		return 1512;
//	}
//
//}
