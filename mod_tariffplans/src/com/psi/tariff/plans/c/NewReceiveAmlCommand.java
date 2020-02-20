package com.psi.tariff.plans.c;

import java.text.ParseException;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.plans.m.NewReceiveAml;
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

public class NewReceiveAmlCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
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
					 	
				NewReceiveAml reg = new NewReceiveAml();
						
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
				
				if(reg.exist()){
					reg.setState(new ObjectState("01", "Aml Receive Setting already registered"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(key);
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
				    audit.setRequest(this.params.toString());
//				    audit.setData(key+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxamountday+"|"+maxamountweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
		    		audit.setData(reg.toString());
				    audit.insert();
					return new JsonView(reg); 
				}
				if(!reg.register()){
					reg.setState(new ObjectState("99", "System busy"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(key);
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
				    audit.setRequest(this.params.toString());
//				    audit.setData(key+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxamountday+"|"+maxamountweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
				    audit.setData(reg.toString());
		    		audit.insert();
					return new JsonView(reg);  
				}
				
				reg.setState(new ObjectState("00", "Receive AML Setting Added Succesfully"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(key);
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
			    audit.setRequest(this.params.toString());
//			    audit.setData(key+"|"+minamount+"|"+maxamount+"|"+maxamountday+"|"+maxamountday+"|"+maxamountweek+"|"+maxamountmonth+"|"+maxtransday+"|"+maxtransweek+"|"+maxtransmonth);
			    audit.setData(reg.toString());
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
	}
		return v;		  
	}

	@Override
	public String getKey() {
		return "NEWRECEIVEAML";
	}

	@Override
	public int getId() {
		return 9524;
	}

}
