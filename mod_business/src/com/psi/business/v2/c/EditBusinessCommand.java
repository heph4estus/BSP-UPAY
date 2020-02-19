package com.psi.business.v2.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.business.v.JsonView;
import com.psi.business.v2.m.ManageMerchant;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditBusinessCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String ownerfirstname = this.params.get("OwnerFirstName").toString();
				String ownermiddlename = this.params.get("OwnerMiddleName").toString();
				String ownerlastname = this.params.get("OwnerLastName").toString();
				String ownerspecificaddress = this.params.get("OwnerSpecificAddress").toString();
				String ownercity = this.params.get("OwnerCity").toString();
				String ownerprovince = this.params.get("OwnerProvince").toString();
				String ownercountry = this.params.get("OwnerCountry").toString();
				String ownerzipcode = this.params.get("OwnerZipCode").toString();
				String msisdn = this.params.get("Msisdn").toString();
				String landline = this.params.get("LandLine").toString();
				String email = this.params.get("Email").toString();
				String secondarymsisdn = this.params.get("SecondaryMsisdn").toString();
				String businessname = this.params.get("BusinessName").toString();
				String specificaddress = this.params.get("SpecificAddress").toString();
				String city = this.params.get("City").toString();
				String province = this.params.get("Province").toString();
				String country = this.params.get("Country").toString();
				String zipcode = this.params.get("ZipCode").toString();
				String image = this.params.get("ImgProof");
				String natureofbusiness = this.params.get("NatureOfBusiness").toString();
				String noofbranches = this.params.get("NoOfBranches").toString();
				String estimatedgrosssale = this.params.get("EstimatedGrossSale").toString();
				String bankname = this.params.get("BankName").toString();
				String bankaccountno = this.params.get("BankAccountNo").toString();
				String bankbranch = this.params.get("BankBranch").toString();
				String bankaccounttype = this.params.get("BankAccountType").toString();
				String tin = this.params.get("Tin").toString();
				String dtino = this.params.get("DTINo").toString();
				String dateissueddti = this.params.get("DateIssuedDTI").toString();
				String businesspermitno = this.params.get("BusinessPermitNo").toString();				
				String dateissuedbusinesspermit = this.params.get("DateIssuedBusinessPermit").toString();
				String authfirstname = this.params.get("AuthFirstName").toString();
				String authmiddlename = this.params.get("AuthMiddleName").toString();
				String authlastname = this.params.get("AuthLastName").toString();
				String authdesignation = this.params.get("AuthDesignation").toString();
				String authmsisdn = this.params.get("AuthMsisdn").toString();
				String authemail = this.params.get("AuthEmail").toString();
				String username = this.params.get("UserName").toString();
				String monday = this.params.get("Monday").toString();
				String tuesday = this.params.get("Tuesday").toString();
				String wednesday = this.params.get("Wednesday").toString();
				String thursday = this.params.get("Thursday").toString();
				String friday = this.params.get("Friday").toString();
				String saturday = this.params.get("Saturday").toString();
				String sunday = this.params.get("Sunday").toString();
				String xcoordinate = this.params.get("XCoordinate").toString();	
				String ycoordinate = this.params.get("YCoordinate").toString();
				//accountnumber of merchant
				String accountnumber = this.params.get("AccountNumber").toString();
				String businesstype = this.params.get("BusinessType").toString();
				String category = this.params.get("Category").toString();
				String paymentmode = this.params.get("PaymentMode").toString();
				String paymentterms = this.params.get("PaymentTerm").toString();	
				String maxamount = this.params.get("MaxAmount").toString();
				String adminsetup = this.params.get("AdminSetup").toString();
				String merchantlevel = this.params.get("MerchantLevel").toString();
				
				ManageMerchant reg = new ManageMerchant();
				
				reg.setOwnerfirstname(ownerfirstname);
				reg.setOwnermiddlename(ownermiddlename);
				reg.setOwnerlastname(ownerlastname);
				reg.setOwnerspecificaddress(ownerspecificaddress);
				reg.setOwnercity(ownercity);
				reg.setOwnerprovince(ownerprovince);
				reg.setOwnercountry(ownercountry);
				reg.setOwnerzipcode(ownerzipcode);
				reg.setMsisdn(msisdn);
				reg.setLandline(landline);
				reg.setEmail(email);
				reg.setSecondarymsisdn(secondarymsisdn);
				reg.setBusinessname(businessname);
				reg.setSpecificaddress(specificaddress);
				reg.setCity(city);
				reg.setProvince(province);
				reg.setCountry(country);
				reg.setZipcode(zipcode);
				reg.setImage(image);
				reg.setNatureofbusiness(natureofbusiness);
				reg.setNoofbranches(noofbranches);
				reg.setEstimatedgrosssale(estimatedgrosssale);
				reg.setBankname(bankname);
				reg.setBankaccountno(bankaccountno);
				reg.setBankbranch(bankbranch);
				reg.setBankaccounttype(bankaccounttype);
				reg.setTin(tin);
				reg.setDtino(dtino);
				reg.setDateissueddti(dateissueddti);
				reg.setBusinesspermitno(businesspermitno);
				reg.setDateissuedbusinesspermit(dateissuedbusinesspermit);
				reg.setAuthfirstname(authfirstname);
				reg.setAuthmiddlename(authmiddlename);
				reg.setAuthlastname(authlastname);
				reg.setAuthdesignation(authdesignation);
				reg.setAuthmsisdn(authmsisdn);
				reg.setAuthemail(authemail);
				reg.setUsername(username);
				reg.setMonday(monday);
				reg.setTuesday(tuesday);
				reg.setWednesday(wednesday);
				reg.setThursday(thursday);
				reg.setFriday(friday);
				reg.setSaturday(saturday);
				reg.setSunday(sunday);
				reg.setXcoordinate(xcoordinate);
				reg.setYcoordinate(ycoordinate);
				reg.setAccountnumber(accountnumber);
				reg.setBusinesstype(businesstype);
				reg.setCategory(category);
				reg.setPaymentmode(paymentmode);
				reg.setPaymentterms(paymentterms);
				reg.setMaxamount(maxamount);
				reg.setAdminsetup(adminsetup);
				reg.setMerchantlevel(merchantlevel);
			    reg.setAuthorizedSession(sess);			    
			    reg.getData();
				    if(!reg.exist()){
						reg.setState(new ObjectState("01", "Merchant account do not exist"));
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
//					    audit.setData(ownerfirstname+"|"+ownermiddlename+"|"+ownerlastname+"|"+ownerspecificaddress+"|"+ownercity+"|"+ownerprovince+"|"+ownercountry+"|"+ownerzipcode+"|"+msisdn+"|"+landline+"|"+email+"|"+secondarymsisdn+"|"+businessname+"|"+specificaddress+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+natureofbusiness+"|"+noofbranches+"|"+estimatedgrosssale+"|"+bankname+"|"+bankaccountno+"|"+bankbranch+"|"+bankaccounttype+"|"+tin+"|"+dtino+"|"+dateissueddti+"|"+businesspermitno+"|"+dateissuedbusinesspermit+"|"+authfirstname+"|"+authmiddlename+"|"+authlastname+"|"+authdesignation+"|"+authmsisdn+"|"+authemail+"|"+username+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+businesstype+"|"+category+"|"+paymentmode+"|"+paymentterms+"|"+maxamount+"|"+adminsetup+"|"+merchantlevel);
						audit.setData2(reg.toString());
					    audit.setImage(image);
						audit.setRequest(reg.json(reg));
						audit.setOlddata(reg.getAuditdata());
			    		audit.insert();
						return new JsonView(reg);
					}
				    if(reg.existmsisdnreg()){
						reg.setState(new ObjectState("01", "Primary Contact Number already registered"));
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
//					    audit.setData(ownerfirstname+"|"+ownermiddlename+"|"+ownerlastname+"|"+ownerspecificaddress+"|"+ownercity+"|"+ownerprovince+"|"+ownercountry+"|"+ownerzipcode+"|"+msisdn+"|"+landline+"|"+email+"|"+secondarymsisdn+"|"+businessname+"|"+specificaddress+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+natureofbusiness+"|"+noofbranches+"|"+estimatedgrosssale+"|"+bankname+"|"+bankaccountno+"|"+bankbranch+"|"+bankaccounttype+"|"+tin+"|"+dtino+"|"+dateissueddti+"|"+businesspermitno+"|"+dateissuedbusinesspermit+"|"+authfirstname+"|"+authmiddlename+"|"+authlastname+"|"+authdesignation+"|"+authmsisdn+"|"+authemail+"|"+username+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+businesstype+"|"+category+"|"+paymentmode+"|"+paymentterms+"|"+maxamount+"|"+adminsetup+"|"+merchantlevel);
					    audit.setData2(reg.toString());
					    audit.setImage(image);
						audit.setRequest(reg.json(reg));
						audit.setOlddata(reg.getAuditdata());
			    		audit.insert();
						return new JsonView(reg);
					}
				    if(reg.existemailreg()){
						reg.setState(new ObjectState("01", "Email Address already registered"));
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
//					    audit.setData(ownerfirstname+"|"+ownermiddlename+"|"+ownerlastname+"|"+ownerspecificaddress+"|"+ownercity+"|"+ownerprovince+"|"+ownercountry+"|"+ownerzipcode+"|"+msisdn+"|"+landline+"|"+email+"|"+secondarymsisdn+"|"+businessname+"|"+specificaddress+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+natureofbusiness+"|"+noofbranches+"|"+estimatedgrosssale+"|"+bankname+"|"+bankaccountno+"|"+bankbranch+"|"+bankaccounttype+"|"+tin+"|"+dtino+"|"+dateissueddti+"|"+businesspermitno+"|"+dateissuedbusinesspermit+"|"+authfirstname+"|"+authmiddlename+"|"+authlastname+"|"+authdesignation+"|"+authmsisdn+"|"+authemail+"|"+username+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+businesstype+"|"+category+"|"+paymentmode+"|"+paymentterms+"|"+maxamount+"|"+adminsetup+"|"+merchantlevel);
					    audit.setData2(reg.toString());
					    audit.setImage(image);
						audit.setRequest(reg.json(reg));
						audit.setOlddata(reg.getAuditdata());
			    		audit.insert();
						return new JsonView(reg);
					}
//				    if(reg.existemailregdetails()){
//						reg.setState(new ObjectState("01", "Email Address already registered"));
//						AuditTrail audit  = new AuditTrail();
//			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
//			    		audit.setModuleid(String.valueOf(this.getId()));
//			    		audit.setEntityid(accountnumber);
//			    		audit.setLog(reg.getState().getMessage());
//			    		audit.setStatus(reg.getState().getCode());
//			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
//			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
//			    		audit.setSessionid(reg.getAuthorizedSession().getId());
//			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
//					    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
//					    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
//					    audit.setOs(reg.getAuthorizedSession().getOs());
//					    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
//					    audit.setData(ownerfirstname+"|"+ownermiddlename+"|"+ownerlastname+"|"+ownerspecificaddress+"|"+ownercity+"|"+ownerprovince+"|"+ownercountry+"|"+ownerzipcode+"|"+msisdn+"|"+landline+"|"+email+"|"+secondarymsisdn+"|"+businessname+"|"+specificaddress+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+natureofbusiness+"|"+noofbranches+"|"+estimatedgrosssale+"|"+bankname+"|"+bankaccountno+"|"+bankbranch+"|"+bankaccounttype+"|"+tin+"|"+dtino+"|"+dateissueddti+"|"+businesspermitno+"|"+dateissuedbusinesspermit+"|"+authfirstname+"|"+authmiddlename+"|"+authlastname+"|"+authdesignation+"|"+authmsisdn+"|"+authemail+"|"+username+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+businesstype+"|"+category+"|"+paymentmode+"|"+paymentterms+"|"+maxamount+"|"+adminsetup+"|"+merchantlevel);
//						audit.setImage(image);
//						audit.setRequest(reg.json(reg));
//						audit.setOlddata(reg.getAuditdata());
//			    		audit.insert();
//						return new JsonView(reg);
//					}

					if(reg.editreg()){
						reg.setState(new ObjectState("00", "Successfully updated partner merchant account"));
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
//					    audit.setData(ownerfirstname+"|"+ownermiddlename+"|"+ownerlastname+"|"+ownerspecificaddress+"|"+ownercity+"|"+ownerprovince+"|"+ownercountry+"|"+ownerzipcode+"|"+msisdn+"|"+landline+"|"+email+"|"+secondarymsisdn+"|"+businessname+"|"+specificaddress+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+natureofbusiness+"|"+noofbranches+"|"+estimatedgrosssale+"|"+bankname+"|"+bankaccountno+"|"+bankbranch+"|"+bankaccounttype+"|"+tin+"|"+dtino+"|"+dateissueddti+"|"+businesspermitno+"|"+dateissuedbusinesspermit+"|"+authfirstname+"|"+authmiddlename+"|"+authlastname+"|"+authdesignation+"|"+authmsisdn+"|"+authemail+"|"+username+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+businesstype+"|"+category+"|"+paymentmode+"|"+paymentterms+"|"+maxamount+"|"+adminsetup+"|"+merchantlevel);
					    audit.setData2(reg.toString());
					    audit.setImage(image);
						audit.setRequest(reg.json(reg));
						audit.setOlddata(reg.getAuditdata());
						audit.insert();
						return new JsonView(reg);
					}else{
						reg.setState(new ObjectState("99", "Failed! Unable to update partner merchant account"));
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
//					    audit.setData(ownerfirstname+"|"+ownermiddlename+"|"+ownerlastname+"|"+ownerspecificaddress+"|"+ownercity+"|"+ownerprovince+"|"+ownercountry+"|"+ownerzipcode+"|"+msisdn+"|"+landline+"|"+email+"|"+secondarymsisdn+"|"+businessname+"|"+specificaddress+"|"+city+"|"+province+"|"+country+"|"+zipcode+"|"+natureofbusiness+"|"+noofbranches+"|"+estimatedgrosssale+"|"+bankname+"|"+bankaccountno+"|"+bankbranch+"|"+bankaccounttype+"|"+tin+"|"+dtino+"|"+dateissueddti+"|"+businesspermitno+"|"+dateissuedbusinesspermit+"|"+authfirstname+"|"+authmiddlename+"|"+authlastname+"|"+authdesignation+"|"+authmsisdn+"|"+authemail+"|"+username+"|"+monday+"|"+tuesday+"|"+wednesday+"|"+thursday+"|"+friday+"|"+saturday+"|"+sunday+"|"+businesstype+"|"+category+"|"+paymentmode+"|"+paymentterms+"|"+maxamount+"|"+adminsetup+"|"+merchantlevel);
					    audit.setData2(reg.toString());
					    audit.setImage(image);
						audit.setRequest(reg.json(reg));
						audit.setOlddata(reg.getAuditdata());
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
		UISession u = new UISession(null);
	    u.setState(new ObjectState("TLC-3902-01"));
	    v = new SessionView(u);
		Logger.LogServer(e);
	}return v;
	}


	@Override
	public String getKey() {
		return "EDITBUSINESSV2";
	}

	@Override
	public int getId() {
		return 1102;
	}

}
