package com.psi.subs.management.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.subs.management.m.ManageBlackList;
import com.psi.subs.management.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class NewAlertParamsCommand extends UICommand
{
  public IView execute()
  {
    ExistingSession sess = null;
    SessionView v = null;
    try
    {
      sess = ExistingSession.parse(this.reqHeaders);
      if (sess.exists())
      {
        String type = this.params.get("Type").toString();
        String firstname = this.params.get("FirstName").toString();
        String middlename = this.params.get("MiddleName").toString();
        String lastname = this.params.get("LastName").toString();
        String dateofbirth = this.params.get("DateOfBirth").toString();
        String username = this.params.get("UserName").toString();
        String password = this.params.get("Password").toString();
        String reason = this.params.get("Reason").toString();
        

        ManageBlackList create = new ManageBlackList();
        create.setUsername(username);
        create.setPassword(password);
        create.setType(type);
        create.setFirstname(firstname);
        create.setMiddlename(middlename);
        create.setLastname(lastname);
        create.setDateofbirth(dateofbirth);
        create.setReason(reason);
        create.setAuthorizedSession(sess);
        try
        {
          if (!create.isvalid()) {
            create.setState(new ObjectState("03", "Incorrect username/password"));
            AuditTrail audit = new AuditTrail();
            audit.setIp(create.getAuthorizedSession().getIpAddress());
            audit.setModuleid(String.valueOf(getId()));
            audit.setEntityid(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
            audit.setLog(create.getState().getMessage());
            audit.setStatus(create.getState().getCode());
            audit.setUserid(create.getAuthorizedSession().getAccount().getId());
            audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
            audit.setBrowser(create.getAuthorizedSession().getBrowser());
  		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
  		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
  		  	audit.setOs(create.getAuthorizedSession().getOs());
  		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
  		  	audit.setData(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
  		  	audit.setRequest(this.params.toString());
            audit.insert();
            return new JsonView(create);
          }

          if(type.equals("BLACKLIST")){

        	  if (create.existblack()) {

	            create.setState(new ObjectState("04", "Customer already added"));
	            AuditTrail audit = new AuditTrail();
	            audit.setIp(create.getAuthorizedSession().getIpAddress());
	            audit.setModuleid(String.valueOf(getId()));
	            audit.setEntityid(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
	            audit.setLog(create.getState().getMessage());
	            audit.setStatus(create.getState().getCode());
	            audit.setUserid(create.getAuthorizedSession().getAccount().getId());
	            audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
	            audit.setBrowser(create.getAuthorizedSession().getBrowser());
	  		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
	  		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
	  		  	audit.setOs(create.getAuthorizedSession().getOs());
	  		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
	  		  	audit.setData(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
	  		  	audit.setRequest(this.params.toString());
	            audit.insert();
	            return new JsonView(create);
	          }
	          if (create.createblack()) {
	            create.setState(new ObjectState("00", "Successfully added new " + type + " customers."));
	            AuditTrail audit = new AuditTrail();
	            audit.setIp(create.getAuthorizedSession().getIpAddress());
	            audit.setModuleid(String.valueOf(getId()));
	            audit.setEntityid(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
	            audit.setLog(create.getState().getMessage());
	            audit.setStatus(create.getState().getCode());
	            audit.setUserid(create.getAuthorizedSession().getAccount().getId());
	            audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
	            audit.setBrowser(create.getAuthorizedSession().getBrowser());
	  		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
	  		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
	  		  	audit.setOs(create.getAuthorizedSession().getOs());
	  		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
	  		  	audit.setData(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
	  		  	audit.setRequest(this.params.toString());
	            audit.insert();
	            return new JsonView(create);
	          }
          }
          
          if(type.equals("WATCHLIST")){
	          if (create.existwhite()) {
		            create.setState(new ObjectState("04", "Customer already added"));
		            AuditTrail audit = new AuditTrail();
		            audit.setIp(create.getAuthorizedSession().getIpAddress());
		            audit.setModuleid(String.valueOf(getId()));
		            audit.setEntityid(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
		            audit.setLog(create.getState().getMessage());
		            audit.setStatus(create.getState().getCode());
		            audit.setUserid(create.getAuthorizedSession().getAccount().getId());
		            audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
		            audit.setBrowser(create.getAuthorizedSession().getBrowser());
		  		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
		  		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
		  		  	audit.setOs(create.getAuthorizedSession().getOs());
		  		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
		  		  	audit.setData(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
		  		  	audit.setRequest(this.params.toString());
		            audit.insert();
		            return new JsonView(create);
		          }
	          if (create.createwhite()) {
	            create.setState(new ObjectState("00", "Successfully added new " + type + " customers."));
	            AuditTrail audit = new AuditTrail();
	            audit.setIp(create.getAuthorizedSession().getIpAddress());
	            audit.setModuleid(String.valueOf(getId()));
	            audit.setEntityid(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
	            audit.setLog(create.getState().getMessage());
	            audit.setStatus(create.getState().getCode());
	            audit.setUserid(create.getAuthorizedSession().getAccount().getId());
	            audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
	            audit.setBrowser(create.getAuthorizedSession().getBrowser());
	  		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
	  		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
	  		  	audit.setOs(create.getAuthorizedSession().getOs());
	  		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
	  		  	audit.setData(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
	  		  	audit.setRequest(this.params.toString());
	            audit.insert();
	            return new JsonView(create);
	          }
          }

          create.setState(new ObjectState("01", create.getState().getMessage()));
          AuditTrail audit = new AuditTrail();
          audit.setIp(create.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
          audit.setLog(create.getState().getMessage());
          audit.setStatus(create.getState().getCode());
          audit.setUserid(create.getAuthorizedSession().getAccount().getId());
          audit.setUsername(create.getAuthorizedSession().getAccount().getUserName());
          audit.setBrowser(create.getAuthorizedSession().getBrowser());
		  	audit.setBrowserversion(create.getAuthorizedSession().getBrowserversion());
		  	audit.setPortalversion(create.getAuthorizedSession().getPortalverion());
		  	audit.setOs(create.getAuthorizedSession().getOs());
		  	audit.setUserslevel(create.getAuthorizedSession().getAccount().getGroup().getName());
		  	audit.setData(username + "|" + type + "|" + firstname + "|" + middlename + "|" + lastname + "|" + dateofbirth);
		  	audit.setRequest(this.params.toString());
          audit.insert();
          return new JsonView(create);
        }
        catch (Exception e)
        {
          create.setState(new ObjectState("99", "System busy"));
          return new JsonView(create);
        }
      }
      UISession u = new UISession(null);
      u.setState(new ObjectState("TLC-3902-01"));
      v = new SessionView(u);
    }
    catch (SessionNotFoundException e) {
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

  public String getKey()
  {
    return "NEWALERTPARAMS";
  }

  public int getId()
  {
    return 1008;
  }
}