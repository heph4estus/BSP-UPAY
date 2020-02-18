package com.psi.keyaccount.c;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.keyaccount.m.NewKeyAccount;
import com.psi.keyaccount.util.EmailUtils;
import com.psi.keyaccount.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class NewKeyAccountCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String accountname = this.params.get("AccountName");
				String businessname = this.params.get("BusinessName");
				String specificaddress = this.params.get("SpecificAddress");
				String city = this.params.get("City");
				String region = this.params.get("Province");
				String country = this.params.get("Country");
				String postalcode = this.params.get("PostalCode");
				String msisdn = this.params.get("ContactNumber");
				String email = this.params.get("Email");
				String firstname = this.params.get("FirstName");
				String middlename = this.params.get("Middlename");
				String lastname = this.params.get("LastName");
				String regdate = this.params.get("RegDate");
				String tinno = this.params.get("TinNo");
				String xcoordinate = this.params.get("XCoordinate");
				String ycoordinate = this.params.get("YCoordinate");
				 	
				NewKeyAccount reg = new NewKeyAccount();
				reg.setAccountname(accountname);
				reg.setBusinessname(businessname);
				reg.setSpecificaddress(specificaddress);
				reg.setCity(city);
				reg.setRegion(region);
				reg.setCountry(country);
				reg.setPostalcode(postalcode);
				reg.setMsisdn(msisdn);
				reg.setEmail(email);
				reg.setFirstname(firstname);
				reg.setMiddlename(middlename);
				reg.setLastname(lastname);
				reg.setRegdate(regdate);
				reg.setTinno(tinno);
				reg.setXcoordinate(xcoordinate);
				reg.setYcoordinate(ycoordinate);
			    reg.setAuthorizedSession(sess);
					try {
						if(reg.exist()){
							reg.setState(new ObjectState("01", "Account Name already registered"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountname);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
							return new JsonView(reg);
						}
						if(reg.register()){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountname);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
				    		EmailUtils.sendEmail(email, firstname, lastname, "123456789",accountname);
							return new JsonView(reg);
							
						}else{
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountname);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
							return new JsonView(reg);
						}
					} catch (IOException e) {
						e.printStackTrace();
						reg.setState(new ObjectState("99", "System busy"));
						return new JsonView(reg);
					} catch (ParseException e) {
						e.printStackTrace();
						reg.setState(new ObjectState("99", "System busy"));
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
		UISession u = new UISession(null);
	    u.setState(new ObjectState("TLC-3902-01"));
	    v = new SessionView(u);
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "NEWKEYACCOUNT";
	}

	@Override
	public int getId() {
		return 3500;
	}

}
