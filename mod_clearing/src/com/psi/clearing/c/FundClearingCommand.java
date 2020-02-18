package com.psi.clearing.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.clearing.m.FundClearing;
import com.psi.clearing.v.JsonView;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.UISession;

public class FundClearingCommand extends UICommand{

	@Override
	public IView execute() {
		
		ExistingSession sess = null;
	    
	    SessionView v = null;
	    FundClearing clearing = new FundClearing();
	    try
	    {
	      sess = ExistingSession.parse(this.reqHeaders);
	      if (sess.exists())
	      {
	        String accountnumber = this.params.get("AccountNumber").toString();
			String amount = this.params.get("Amount").toString();
			String password = this.params.get("Password");
			
	          clearing.setAccountnumber(accountnumber);
	          clearing.setAmount(amount);
	          clearing.setPassword(password);
	          clearing.setAuthorizedSession(sess);
          
			if(!clearing.validate()){
				clearing.setState(new ObjectState("01", "Invalid Password"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(clearing.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(accountnumber);
	    		audit.setLog(clearing.getState().getMessage());
	    		audit.setStatus(clearing.getState().getCode());
	    		audit.setUserid(clearing.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
	    		audit.setSessionid(clearing.getAuthorizedSession().getId());
	    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(clearing.getAuthorizedSession().getPortalverion());
			    audit.setOs(clearing.getAuthorizedSession().getOs());
			    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.setRequest(this.params.toString());
			    audit.setData(accountnumber+"|"+amount);
	    		audit.insert();
				return new JsonView(clearing); 
			}

		    if (clearing.clear()){
		            ObjectState state = new ObjectState("00", "Fund Successfully Cleared");
		            clearing.setState(state);
		            AuditTrail audit = new AuditTrail();
		            audit.setIp(clearing.getAuthorizedSession().getIpAddress());
		            audit.setModuleid(String.valueOf(getId()));
		            audit.setEntityid(accountnumber);
		            audit.setLog(clearing.getState().getMessage());
		            audit.setStatus(clearing.getState().getCode());
		            audit.setUserid(Integer.valueOf(clearing.getAuthorizedSession().getAccount().getId()));
		            audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
		            audit.setSessionid(clearing.getAuthorizedSession().getId());
		    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(clearing.getAuthorizedSession().getPortalverion());
				    audit.setOs(clearing.getAuthorizedSession().getOs());
				    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(accountnumber+"|"+amount);
		            audit.insert();
		            return new JsonView(clearing);	          
	          }else{
		          ObjectState state = new ObjectState("01", "Fund Clearing Unsuccessfull");
		          clearing.setState(state);
		          AuditTrail audit = new AuditTrail();
		            audit.setIp(clearing.getAuthorizedSession().getIpAddress());
		            audit.setModuleid(String.valueOf(getId()));
		            audit.setEntityid(accountnumber);
		            audit.setLog(clearing.getState().getMessage());
		            audit.setStatus(clearing.getState().getCode());
		            audit.setUserid(Integer.valueOf(clearing.getAuthorizedSession().getAccount().getId()));
		            audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
		            audit.setSessionid(clearing.getAuthorizedSession().getId());
		    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(clearing.getAuthorizedSession().getPortalverion());
				    audit.setOs(clearing.getAuthorizedSession().getOs());
				    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(accountnumber+"|"+amount);
		            audit.insert();
		          return new JsonView(clearing);
	          }
	      }else{
	    	  UISession u = new UISession(null);
	    	  u.setState(new ObjectState("TLC-3902-01"));
		      v = new SessionView(u);
	      } }
	        catch (Exception e)
	        {
	        	UISession u = new UISession(null);
	   	      u.setState(new ObjectState("TLC-3902-01"));
	   	      v = new SessionView(u);
	        }	      
	    return v;
	}

	@Override
	public String getKey() {
		return "CLEARFUND";
	}

	@Override
	public int getId() {
		return 1400;
	}

}
