package com.psi.customer.support.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.customer.support.m.EditCustomer;
import com.psi.customer.support.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.utils.EmailUtils;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditCustomerCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				EditCustomer reg = new EditCustomer();
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String middlename = this.params.get("MiddleName").toString();
				String username = this.params.get("UserName").toString();
					reg.setFirstname(firstname);
					reg.setLastname(lastname);
					reg.setMiddlename(middlename);
					reg.setUsername(username);
					reg.setAuthorizedSession(sess);
				
					if(reg.update()){
						reg.setState(new ObjectState("00", "Updated Succesfully"));
						return new JsonView(reg);  
					}else{
						reg.setState(new ObjectState("99", "System busy"));
						AuditTrail audit  = new AuditTrail();
						audit.setIp(sess.getAuthorizedSession().getIpAddress());
						audit.setModuleid(String.valueOf(this.getId()));
						audit.setEntityid(username);
						audit.setLog("System is busy, please try again later");
						audit.setStatus("99");
						audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
						audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
						audit.setSessionid(reg.getAuthorizedSession().getId());
						audit.setBrowser(reg.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
					    audit.setOs(reg.getAuthorizedSession().getOs());
					    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setData("NEW DETAILS: "+firstname+"|"+middlename+"|"+lastname+"|"+username);
					    audit.setRequest(this.params.toString());
						audit.insert();
						return new JsonView(reg);  
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
		Logger.LogServer(e);
	}
		return v;
	}

	@Override
	public int getId() {
		return 1926;
	}

	@Override
	public String getKey() {
		return "EDITUSERFULLNAME";
	}

}
