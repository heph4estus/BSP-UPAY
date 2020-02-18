package com.psi.business.c;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.business.m.NewBusiness;
import com.psi.business.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class NewBusinessCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String branchname = this.params.get("BranchName");
				String address = this.params.get("Address");
				String city = this.params.get("City");
				String province = this.params.get("Province");
				String country = this.params.get("Country");
				String zipcode = this.params.get("ZipCode");
				String contactnumber = this.params.get("ContactNumber");
				//String storehours = this.params.get("OperatingHours");
				String image = this.params.get("ImgProof");
				String xcoordinate = this.params.get("XCoordinate");
				String ycoordinate = this.params.get("YCoordinate");
				String monday = this.params.get("Monday");
				String tuesday = this.params.get("Tuesday");
				String wednesday = this.params.get("Wednesday");
				String thursday = this.params.get("Thursday");
				String friday = this.params.get("Friday");
				String saturday = this.params.get("Saturday");
				String sunday = this.params.get("Sunday");
				String accountnumber = this.params.get("AccountNumber");
				String iswithholdingtax = this.params.get("IsWithHoldingTax");
				 	
				NewBusiness reg = new NewBusiness();
				reg.setIsWithholdingtax(iswithholdingtax);
				reg.setBranchname(branchname);
				reg.setAddress(address);
				reg.setCity(city);
				reg.setProvince(province);
				reg.setCountry(country);
				reg.setZipcode(zipcode);
				reg.setContactnumber(contactnumber);
				//reg.setStorehours(storehours);
				reg.setImage(image);
				reg.setXordinate(xcoordinate);
				reg.setYordinate(ycoordinate);
				reg.setMonday(monday);
				reg.setTuesday(tuesday);
				reg.setWednesday(wednesday);
				reg.setThursday(thursday);
				reg.setFriday(friday);
				reg.setSaturday(saturday);		
				reg.setSunday(sunday);
				reg.setAccountnumber(accountnumber);
			    reg.setAuthorizedSession(sess);
					try {
						if(reg.exist()){
							reg.setState(new ObjectState("01", "Account already registered"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(branchname);
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
						    audit.setData(branchname+"|"+address+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+contactnumber+"|"+xcoordinate+"|"+ycoordinate+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+accountnumber+"|"+iswithholdingtax);
				    		audit.setImage(image);
				    		audit.insert();
							return new JsonView(reg);
						}
						if(reg.contactexist()){
							reg.setState(new ObjectState("01", "Contact Number already registered"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(branchname);
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
						    audit.setData(branchname+"|"+address+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+contactnumber+"|"+xcoordinate+"|"+ycoordinate+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+accountnumber+"|"+iswithholdingtax);
				    		audit.setImage(image);
				    		audit.insert();
							return new JsonView(reg);
						}
						if(reg.register(accountnumber)){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(branchname);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setData(branchname+"|"+address+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+contactnumber+"|"+xcoordinate+"|"+ycoordinate+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+accountnumber+"|"+iswithholdingtax);
				    		audit.setImage(image);
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(reg.getAuthorizedSession().getId());
				    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
						    audit.setOs(reg.getAuthorizedSession().getOs());
						    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
				    		audit.insert();
							return new JsonView(reg);
						}else{
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(branchname);
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
						    audit.setData(branchname+"|"+address+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+contactnumber+"|"+xcoordinate+"|"+ycoordinate+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+accountnumber+"|"+iswithholdingtax);
				    		audit.setImage(image);
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
		return "NEWBUSINESS";
	}

	@Override
	public int getId() {
		return 1100;
	}

}
