package com.psi.business.v2.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.business.v.JsonView;
import com.psi.business.v2.m.InquireData;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class InquireDataCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String businessname = this.params.get("BusinessName");
				String msisdn = this.params.get("Msisdn");
				String authmsisdn = this.params.get("AuthMsisdn");
				String authemail = this.params.get("AuthEmail");
				String username = this.params.get("UserName");
				String type = this.params.get("Type");
				String email = this.params.get("Email");
				 	
				InquireData reg = new InquireData();
				
				reg.setBusinessname(businessname);
				reg.setMsisdn(msisdn);
				reg.setAuthmsisdn(authmsisdn);
				reg.setAuthemail(authemail);
				reg.setUsername(username);
				reg.setEmail(email);
				reg.setAuthorizedSession(sess);
				if(type.equals("BUSINESS")){
					if(reg.exist()){
						reg.setState(new ObjectState("01", "Account already registered"));
						return new JsonView(reg);
					}
					if(reg.existpndg()){
						reg.setState(new ObjectState("06", "Account already registered in pending merchants"));
						return new JsonView(reg);
					}
				}else if(type.equals("OWNER")){
					if(reg.existmsisdn()){
						reg.setState(new ObjectState("02", "Primary Mobile Number already registered"));
						return new JsonView(reg);
					}
					if(reg.existmsisdnpndg()){
						reg.setState(new ObjectState("07", "Primary Mobile Number already registered in pending merchants"));
						return new JsonView(reg);
					}
					if(reg.existemail()){
						reg.setState(new ObjectState("11", "Email Address already registered"));
						return new JsonView(reg);
					}
					if(reg.existemaildetails()){
						reg.setState(new ObjectState("11", "Email Address already registered"));
						return new JsonView(reg);
					}
					if(reg.existemailpndg()){
						reg.setState(new ObjectState("12", "Email Address already registered in pending merchants"));
						return new JsonView(reg);
					}
				}else if(type.equals("OPERATION")){
					if(reg.existauthmsisdn()){
						reg.setState(new ObjectState("03", "Authorized Mobile Number already exist"));
						return new JsonView(reg);
					}
					if(reg.existauthmsisdnpndg()){
						reg.setState(new ObjectState("08", "Authorized Mobile Number already exist in pending merchants"));
						return new JsonView(reg);
					}
					if(reg.existauthemail()){
						reg.setState(new ObjectState("04", "Authorized Email Address already exist"));
						return new JsonView(reg);
					}
					if(reg.existauthemailpndg()){
						reg.setState(new ObjectState("09", "Authorized Email Address already exist in pending merchants"));
						return new JsonView(reg);
					}
					if(reg.existauthusername()){
						reg.setState(new ObjectState("05", "User Name already exist"));
						return new JsonView(reg);
					}					
					if(reg.existauthusernamepndg()){
						reg.setState(new ObjectState("10", "User Name already exist in pending merchants"));
						return new JsonView(reg);
					}
				}
				reg.setState(new ObjectState("00", "Success"));
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
		return "INQUIREMERCHANTDATA";
	}

	@Override
	public int getId() {
		return 0;
	}

}
