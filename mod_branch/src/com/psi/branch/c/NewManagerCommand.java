package com.psi.branch.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.branch.m.NewManager;
import com.psi.branch.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class NewManagerCommand extends UICommand{

	@Override
	public IView execute() {	
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String email = this.params.get("Email").toString();
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String msisdn = this.params.get("MSISDN").toString();
				String username = this.params.get("UserName").toString();
				String accountnumber = this.params.get("AccountNumber");
				String midname = this.params.get("MiddleName").toString();
				String password = PasswordGenerator.generatePassword(5, PasswordGenerator.NUMERIC_CHAR);
				//String password = "123456789";
					 	
				NewManager reg = new NewManager();
						
				reg.setEmail(email);
				reg.setFirstname(firstname);
				reg.setLastname(lastname);
				reg.setMsisdn(msisdn);
				reg.setUsername(username);
				reg.setPassword(password);
				reg.setMidname(midname);
				reg.setAccountnumber(accountnumber);
			    reg.setAuthorizedSession(sess);
			    if(reg.isMsisdnExist()){
			    	reg.setState(new ObjectState("PSI-03", "Mobile Number already registered."));
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
				    audit.setRequest(this.params.toString());
				    audit.setData("EMAIL:"+email+"|FIRSTNAME:"+firstname+"|MIDDLENAME:"+midname+"|LASTNAME:"+lastname+"|MSISDN:"+msisdn+"|USERNAME:"+username);
		    		audit.insert();
		    		return new JsonView(reg);  
			    }
				if(!reg.exist()){
					if(!reg.isEmailExist()){
						if(reg.register()){
							reg.setState(new ObjectState("00", "Register Succesfully"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		audit.setData("EMAIL:"+email+"|FIRSTNAME:"+firstname+"|MIDDLENAME:"+midname+"|LASTNAME:"+lastname+"|MSISDN:"+msisdn+"|USERNAME:"+username);
				    		audit.setSessionid(reg.getAuthorizedSession().getId());
				    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
						    audit.setOs(reg.getAuthorizedSession().getOs());
						    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
				    		audit.insert();
								
						}else{
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
						    audit.setRequest(this.params.toString());
						    audit.setData("EMAIL:"+email+"|FIRSTNAME:"+firstname+"|MIDDLENAME:"+midname+"|LASTNAME:"+lastname+"|MSISDN:"+msisdn+"|USERNAME:"+username);
				    		audit.insert();
						}
					}else{
						reg.setState(new ObjectState("PSI-02", "Email adress already registered"));
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
					    audit.setRequest(this.params.toString());
					    audit.setData("EMAIL:"+email+"|FIRSTNAME:"+firstname+"|MIDDLENAME:"+midname+"|LASTNAME:"+lastname+"|MSISDN:"+msisdn+"|USERNAME:"+username);
			    		audit.insert();
					}
				}else{
					reg.setState(new ObjectState("PSI-01", "Account already registered"));
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
				    audit.setRequest(this.params.toString());
				    audit.setData("EMAIL:"+email+"|FIRSTNAME:"+firstname+"|MIDDLENAME:"+midname+"|LASTNAME:"+lastname+"|MSISDN:"+msisdn+"|USERNAME:"+username);
		    		audit.insert();
				}				
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
		UISession u = new UISession(null);
	    u.setState(new ObjectState("TLC-3902-01"));
	    v = new SessionView(u);
		Logger.LogServer(e);
	}return v;
				  
	}

	@Override
	public int getId() {	
		return 1204;
	}

	@Override
	public String getKey() {
		return "NEWMANAGER";
	}

}
