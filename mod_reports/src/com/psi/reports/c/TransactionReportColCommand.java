package com.psi.reports.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.reports.m.TransactionReportCollection;
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

public class TransactionReportColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
		
		String code = this.params.get("BranchCode").toString();
		String datefrom = this.params.get("DateFrom").toString();
		String dateto = this.params.get("DateTo").toString();
		String accounttype = this.params.get("AccountType");
		
		TransactionReportCollection model = new TransactionReportCollection();
						model.setBranch(code);
						model.setDatefrom(datefrom);
						model.setDateto(dateto);
						model.setAuthorizedSession(sess);
				if((code.equals("ALL") && !accounttype.equals("CUSTOMER"))){					
						if(model.hasRows()){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(code);
				    		audit.setLog("Successfully fetched data");
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
						    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
				    		audit.insert();
							return new CollectionView("00",model);  
						}else{
								ObjectState state = new ObjectState("01", "No data found");
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(code);
					    		audit.setLog("No data found");
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
							    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
					    		audit.insert();
								return new NoDataFoundView(state); 
						}						

				}else{
					if(accounttype.equals("BRANCH")){
						if(model.getTransCol()){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(code);
				    		audit.setLog("Successfully fetched data");
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
						    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
				    		audit.insert();
							return new CollectionView("00",model);  
						}else{
								ObjectState state = new ObjectState("01", "No data found");
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(code);
					    		audit.setLog("No data found");
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
							    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
					    		audit.insert();
								return new NoDataFoundView(state); 
						}
					}else if(accounttype.equals("CUSTOMER")){
						if(code.equals("ALL")){
							if(model.hasRowsCustomerAll()){
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(code);
					    		audit.setLog("Successfully fetched data");
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
							    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
					    		audit.insert();
								return new CollectionView("00",model);  
							}else{
									ObjectState state = new ObjectState("01", "No data found");
									AuditTrail audit  = new AuditTrail();
						    		audit.setIp(model.getAuthorizedSession().getIpAddress());
						    		audit.setModuleid(String.valueOf(this.getId()));
						    		audit.setEntityid(code);
						    		audit.setLog("No data found");
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
								    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
						    		audit.insert();
									return new NoDataFoundView(state); 
							}
						}else{
							if(model.hasRowsCustomer()){
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(code);
					    		audit.setLog("Successfully fetched data");
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
							    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
					    		audit.insert();
								return new CollectionView("00",model);  
							}else{
									ObjectState state = new ObjectState("01", "No data found");
									AuditTrail audit  = new AuditTrail();
						    		audit.setIp(model.getAuthorizedSession().getIpAddress());
						    		audit.setModuleid(String.valueOf(this.getId()));
						    		audit.setEntityid(code);
						    		audit.setLog("No data found");
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
								    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
						    		audit.insert();
									return new NoDataFoundView(state); 
							}
						}
					}else{
						if(model.hasRowsAllCom()){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(code);
				    		audit.setLog("Successfully fetched data");
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
						    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
				    		audit.insert();
							return new CollectionView("00",model);  
						}else{
								ObjectState state = new ObjectState("01", "No data found");
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(code);
					    		audit.setLog("No data found");
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
							    audit.setData(code+"|"+accounttype+"|"+datefrom+"|"+dateto);
					    		audit.insert();
								return new NoDataFoundView(state); 
						}
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
		return "TRANSACTIONHISTORY";
	}

	@Override
	public int getId() {
		return 3012;
	}

}