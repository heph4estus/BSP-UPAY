package com.psi.wallet.branch.c;

import java.text.NumberFormat;
import java.util.Locale;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.wallet.branch.m.WalletTopUp;
import com.psi.wallet.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class WalletTopupRequestCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String id = this.params.get("LinkId");
				String remark = this.params.get("Remarks");
				String status = this.params.get("Status");
				Long amount = LongUtil.toLong(this.params.get("Amount"));	
				String password = this.params.get("Password");
				String accountnumber = this.params.get("AccountNumber");
				String referencenumber = this.params.get("ReferenceNumber"); //to Bank branch code
				String depositchannel = this.params.get("DepositChannel");
				String timeofdeposit = this.params.get("TimeOfDeposit");
				String dateofdeposit = this.params.get("DateOfDeposit");
				String bankcode = this.params.get("BankCode");
				String bankname = this.params.get("BankName");
				String image = this.params.get("Image");
				String description = this.params.get("Description");
				
				
				WalletTopUp topup = new WalletTopUp();
				
				topup.setAmount(amount);
				topup.setPassword(password);
				topup.setAccountnumber(accountnumber);
				topup.setId(id);
				topup.setReferencenumber(referencenumber);
				topup.setRemarks(remark);
				topup.setStatus(status);
				topup.setDepositchannel(depositchannel);
				topup.setTimeofdeposit(timeofdeposit);
				topup.setDateofdeposit(dateofdeposit);
				topup.setBankcode(bankcode);
				topup.setBankname(bankname);
				topup.setImage(image);
				topup.setDescription(description);
				topup.setAuthorizedSession(sess);
				
						if(!topup.validate()){
							topup.setState(new ObjectState("01", "Incorrect Password"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(topup.getState().getMessage());
				    		audit.setStatus(topup.getState().getCode());
				    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(topup.getAuthorizedSession().getId());
				    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
						    audit.setOs(topup.getAuthorizedSession().getOs());
						    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData("MODE OF PAYMENT: "+remark+" | STATUS: "+status+" | AMOUNT: "+amount+"| ACCOUNTNUMBER: "+accountnumber+" | DATEOFDEPOSIT: "+dateofdeposit+" | TIMEOFDEPOSIT: "+timeofdeposit+" | BANKNAME: "+bankname+" | DETAILS: "+description);
						    audit.setRemarks(remark);
						    audit.setImage(image);
				    		audit.insert();
							return new JsonView(topup); 
						}
					
						try {
							if(topup.topup()){
								topup.setState(new ObjectState("00", "Allocated Successfully"));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(id);
					    		audit.setLog(topup.getState().getMessage());
					    		audit.setStatus(topup.getState().getCode());
					    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(topup.getAuthorizedSession().getId());
					    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
							    audit.setOs(topup.getAuthorizedSession().getOs());
							    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData("MODE OF PAYMENT: "+remark+" | STATUS: "+status+" | AMOUNT: "+amount+"| ACCOUNTNUMBER: "+accountnumber+" | DATEOFDEPOSIT: "+dateofdeposit+" | TIMEOFDEPOSIT: "+timeofdeposit+" | BANKNAME: "+bankname+" | DETAILS: "+description);
							    audit.setRemarks(remark);
							    audit.setImage(image);
					    		audit.insert();
								return new JsonView(topup); 

							}else{
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(id);
					    		audit.setLog(topup.getState().getMessage());
					    		audit.setStatus(topup.getState().getCode());
					    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(topup.getAuthorizedSession().getId());
					    		audit.setBrowser(topup.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(topup.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(topup.getAuthorizedSession().getPortalverion());
							    audit.setOs(topup.getAuthorizedSession().getOs());
							    audit.setUserslevel(topup.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData("MODE OF PAYMENT: "+remark+" | STATUS: "+status+" | AMOUNT: "+amount+"| ACCOUNTNUMBER: "+accountnumber+" | DATEOFDEPOSIT: "+dateofdeposit+" | TIMEOFDEPOSIT: "+timeofdeposit+" | BANKNAME: "+bankname+" | DETAILS: "+description);
							    audit.setRemarks(remark);
							    audit.setImage(image);
					    		audit.insert();
								return new JsonView(topup); 
							}
						} catch (Exception e) {
							topup.setState(new ObjectState("99", "System busy"));
							return new JsonView(topup); 
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
		return "WALLETTOPUPREQUEST";
	}

	@Override
	public int getId() {
		return 1309;
	}

}
