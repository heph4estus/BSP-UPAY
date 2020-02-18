package com.psi.branch.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.branch.m.EditManager;
import com.psi.branch.utils.EmailUtils;
import com.psi.branch.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditManagerCommand extends UICommand{

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
				//String password = PasswordGenerator.generatePassword(5, PasswordGenerator.NUMERIC_CHAR);
				String password = "123456789";
				String midname = this.params.get("MiddleName").toString();
					 	
				EditManager reg = new EditManager();
						
				reg.setEmail(email);
				reg.setFirstname(firstname);
				reg.setLastname(lastname);
				reg.setMsisdn(msisdn);
				reg.setUsername(username);
				reg.setPassword(password);
				reg.setAccountnumber(accountnumber);
				reg.setMidname(midname);
			    reg.setAuthorizedSession(sess);
				
				if(reg.exist()){
					if(reg.update()){
						EmailUtils.sendUpdate(email, firstname, lastname,username,reg.getAuthorizedSession().getIpAddress(),reg.getAuthorizedSession().getAccount().getUserName());
						reg.setState(new ObjectState("00", "Succesfully updated branch manager user"));
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
					    audit.setData(email+"|"+firstname+"|"+midname+"|"+lastname+"|"+msisdn+"|"+username);
			    		audit.insert();
					}
				}else{
					reg.setState(new ObjectState("01", "Account does not exist"));
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
				    audit.setData(email+"|"+firstname+"|"+midname+"|"+lastname+"|"+msisdn+"|"+username);
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
		Logger.LogServer(e);
	}
		return v;		  
	}

	@Override
	public int getId() {	
		return 1205;
	}

	@Override
	public String getKey() {
		return "EDITMANAGER";
	}

}
