package com.psi.aml.settings.c;

import java.text.ParseException;

import com.psi.aml.settings.m.CashierAmlReceive;
import com.psi.aml.settings.v.JsonView;
import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditCashierAmlReceiveCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String accountnumber = this.params.get("AccountNumber");
				String userslevel = this.params.get("UsersLevel");
				String key = this.params.get("Key");
				Long minamount = null;
				Long maxamount = null;
				Long maxamountday = null;
				Long maxamountweek = null;
				Long maxamountmonth = null;
				try {
					 minamount = LongUtil.toLong(this.params.get("MinAmount").toString());
					 maxamount = LongUtil.toLong(this.params.get("MaxAmount").toString());
					 maxamountday = LongUtil.toLong(this.params.get("MaxAmountDay").toString());
					 maxamountweek = LongUtil.toLong(this.params.get("MaxAmountWeek").toString());
					 maxamountmonth = LongUtil.toLong(this.params.get("MaxAmountMonth").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String maxtransday = this.params.get("MaxTransDay");
				String maxtransweek = this.params.get("MaxTransWeek");
				String maxtransmonth = this.params.get("MaxTransMonth");
					 	
				CashierAmlReceive reg = new CashierAmlReceive();
						
				reg.setAccountnumber(accountnumber);
				reg.setUserslevel(userslevel);
				reg.setKey(key);
				reg.setMinamount(minamount);
				reg.setMaxamount(maxamount);
				reg.setMaxamountday(maxamountday);
				reg.setMaxamountweek(maxamountweek);
				reg.setMaxamountmonth(maxamountmonth);
				reg.setMaxtransday(maxtransday);
				reg.setMaxtransweek(maxtransweek);
				reg.setMaxtransmonth(maxtransmonth);
			    reg.setAuthorizedSession(sess);
				
				if(!reg.exist()){
					reg.setState(new ObjectState("01", "Aml Setting not registered"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(accountnumber);
		    		audit.setLog(reg.getState().getMessage());
		    		audit.setStatus(reg.getState().getCode());
		    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(reg.getAuthorizedSession().getId());
		    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
				    audit.setOs(reg.getAuthorizedSession().getOs());
				    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setData("NEW DETAILS:"+accountnumber+"|"+userslevel+"|"+key+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxtransweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
				    audit.setRequest(this.params.toString());
		    		audit.insert();
					return new JsonView(reg); 
				}
				if(!reg.update()){
					reg.setState(new ObjectState("99", "System busy"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(accountnumber);
		    		audit.setLog(reg.getState().getMessage());
		    		audit.setStatus(reg.getState().getCode());
		    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(reg.getAuthorizedSession().getId());
		    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
				    audit.setOs(reg.getAuthorizedSession().getOs());
				    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setData("NEW DETAILS:"+accountnumber+"|"+userslevel+"|"+key+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxtransweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
				    audit.setRequest(this.params.toString());
		    		audit.insert();
					return new JsonView(reg);  
				}
				
				reg.setState(new ObjectState("00", "Updated Succesfully"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(accountnumber);
	    		audit.setLog(reg.getState().getMessage());
	    		audit.setStatus(reg.getState().getCode());
	    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
	    		audit.setSessionid(reg.getAuthorizedSession().getId());
	    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
			    audit.setOs(reg.getAuthorizedSession().getOs());
			    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.setData("OLD DETAILS:"+accountnumber+"|"+userslevel+"|"+reg.getMinamount()+"|"+reg.getMaxamount()+"|"+reg.getMaxamountday()+"|"+reg.getMaxtransweek()+"|"+reg.getMaxamountmonth()+"|"+reg.getMaxtransday()+"|"+reg.getMaxtransweek()+"|"+reg.getMaxtransmonth()+" NEW DETAILS:"+accountnumber+"|"+userslevel+"|"+key+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxtransweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
			    audit.setRequest(this.params.toString());
	    		audit.insert();
				return new JsonView(reg);  
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
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "EDITCASHIERAMLRECEIVE";
	}

	@Override
	public int getId() {
		return 8002;
	}

}
