package com.psi.clearing.c;

import java.text.ParseException;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.clearing.m.Balance;
import com.psi.clearing.v.BalanceResponseView;
import com.psi.clearing.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class BalanceInquiryCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
	    SessionView v = null;
	    Balance reg = new Balance();
	    String accntno = this.params.get("AccountNumber").toString();
	    try
	    {
	      sess = ExistingSession.parse(this.reqHeaders);
	      if (sess.exists())
	      {
	        reg.setAuthorizedSession(sess);
	        try
	        {
	          if (reg.balance(accntno))
	          {
	            AuditTrail audit = new AuditTrail();
	            audit.setIp(reg.getAuthorizedSession().getIpAddress());
	            audit.setModuleid(String.valueOf(getId()));
	            audit.setEntityid("");
	            audit.setLog(reg.getState().getMessage());
	            audit.setStatus(reg.getState().getCode());
	            audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
	            audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
	            audit.setSessionid(reg.getAuthorizedSession().getId());
	    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
			    audit.setOs(reg.getAuthorizedSession().getOs());
			    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.setRequest(this.params.toString());
			    audit.setData("ACCOUNTNUMBER:" + accntno);
	            audit.insert();
	            return new BalanceResponseView(reg);
	          }
	          AuditTrail audit = new AuditTrail();
	          audit.setIp(reg.getAuthorizedSession().getIpAddress());
	          audit.setModuleid(String.valueOf(getId()));
	          audit.setEntityid("");
	          audit.setLog(reg.getState().getMessage());
	          audit.setStatus(reg.getState().getCode());
	          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
	          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
	          audit.setSessionid(reg.getAuthorizedSession().getId());
	    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
			    audit.setOs(reg.getAuthorizedSession().getOs());
			    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.setRequest(this.params.toString());
//			    audit.setData(accntno);
			    audit.setData("ACCOUNTNUMBER:" + accntno);
	          audit.insert();
	          return new JsonView(reg);
	        }
	        catch (ParseException e)
	        {
	          e.printStackTrace();
	          reg.setState(new ObjectState("99", "System busy"));
	          return new JsonView(reg);
	        }
	        catch (Exception e)
	        {
	          e.printStackTrace();
	          reg.setState(new ObjectState("99", "System busy"));
	          return new JsonView(reg);
	        }
	      }
	      UISession u = new UISession(null);
	      u.setState(new ObjectState("TLC-3902-01"));
	      v = new SessionView(u);
	    }
	    catch (SessionNotFoundException e)
	    {
	      UISession u = new UISession(null);
	      u.setState(new ObjectState("TLC-3902-01"));
	      v = new SessionView(u);
	      Logger.LogServer(e);
	    }
	    catch (Exception e)
	    {
	      Logger.LogServer(e);
	    }
	    return v;
	}

	@Override
	public int getId() {
		return 1402;
	}

	@Override
	public String getKey() {
		return "OUTLETBALN";
	}

}
