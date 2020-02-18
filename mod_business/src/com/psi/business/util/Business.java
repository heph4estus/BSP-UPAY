package com.psi.business.util;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.Model;

public class Business extends Model {

	protected String ownerfirstname;
	protected String ownermiddlename;
	protected String ownerlastname;
	protected String ownerspecificaddress;
	protected String ownercity;
	protected String ownerprovince;
	protected String ownercountry;
	protected String ownerzipcode;
	protected String msisdn;
	protected String landline;
	protected String email;
	protected String secondarymsisdn;
	protected String businessname;
	protected String specificaddress;
	protected String city;
	protected String province;
	protected String country;
	protected String zipcode;
	protected String image;
	protected String natureofbusiness;
	protected String noofbranches;
	protected String estimatedgrosssale;
	protected String bankname;
	protected String bankaccountno;
	protected String bankbranch;
	protected String bankaccounttype;
	protected String tin;
	protected String dtino;
	protected String dateissueddti;
	protected String businesspermitno;
	protected String dateissuedbusinesspermit;
	protected String authfirstname;
	protected String authmiddlename;
	protected String authlastname;
	protected String authdesignation;
	protected String authmsisdn;
	protected String authemail;
	protected String username;
	protected String monday;
	protected String tuesday;
	protected String wednesday;
	protected String thursday;
	protected String friday;
	protected String saturday;
	protected String sunday;
	protected String xcoordinate;
	protected String ycoordinate;
	protected String accountnumber;
	protected String category;
	protected String businesstype;
	protected String auditdata;
	protected String paymentmode;
	protected String paymentterms;
	protected String maxamount;
	protected String adminsetup;
	protected String tariff;
	protected String servicesairt;
	protected String servicesbill;
	protected String commissionairt;
	protected String commissionbill;
	protected String merchantlevel;
	protected String servicescashin;
	protected String serviceskapamilya;
	protected String servicesabscbn;
	protected String servicescashout;
	
	public String getOwnerfirstname() {
		return ownerfirstname;
	}
	public void setOwnerfirstname(String ownerfirstname) {
		this.props.put("ownerfirstname", ownerfirstname);
		this.ownerfirstname = ownerfirstname;
	}
	public String getOwnermiddlename() {
		return ownermiddlename;
	}
	public void setOwnermiddlename(String ownermiddlename) {
		this.props.put("ownermiddlename", ownermiddlename);
		this.ownermiddlename = ownermiddlename;
	}
	public String getOwnerlastname() {
		return ownerlastname;
	}
	public void setOwnerlastname(String ownerlastname) {
		this.props.put("ownerlastname", ownerlastname);
		this.ownerlastname = ownerlastname;
	}
	public String getOwnerspecificaddress() {
		return ownerspecificaddress;
	}
	public void setOwnerspecificaddress(String ownerspecificaddress) {
		this.props.put("ownerspecificaddress", ownerspecificaddress);
		this.ownerspecificaddress = ownerspecificaddress;
	}
	public String getOwnercity() {
		return ownercity;
	}
	public void setOwnercity(String ownercity) {
		this.props.put("ownercity", ownercity);
		this.ownercity = ownercity;
	}
	public String getOwnerprovince() {
		return ownerprovince;
	}
	public void setOwnerprovince(String ownerprovince) {
		this.props.put("ownerprovince", ownerprovince);
		this.ownerprovince = ownerprovince;
	}
	public String getOwnercountry() {
		return ownercountry;
	}
	public void setOwnercountry(String ownercountry) {
		this.props.put("ownercountry", ownercountry);
		this.ownercountry = ownercountry;
	}
	public String getOwnerzipcode() {
		return ownerzipcode;
	}
	public void setOwnerzipcode(String ownerzipcode) {
		this.props.put("ownerzipcode", ownerzipcode);
		this.ownerzipcode = ownerzipcode;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.props.put("msisdn", msisdn);
		this.msisdn = msisdn;
	}
	public String getLandline() {
		return landline;
	}
	public void setLandline(String landline) {
		this.props.put("landline", landline);
		this.landline = landline;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.props.put("email", email);
		this.email = email;
	}
	public String getSecondarymsisdn() {
		return secondarymsisdn;
	}
	public void setSecondarymsisdn(String secondarymsisdn) {
		this.props.put("secondarymsisdn", secondarymsisdn);
		this.secondarymsisdn = secondarymsisdn;
	}
	public String getBusinessname() {
		return businessname;
	}
	public void setBusinessname(String businessname) {
		this.props.put("businessname", businessname);
		this.businessname = businessname;
	}
	public String getSpecificaddress() {
		return specificaddress;
	}
	public void setSpecificaddress(String specificaddress) {
		this.props.put("specificaddress", specificaddress);
		this.specificaddress = specificaddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.props.put("city", city);
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.props.put("province", province);
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.props.put("country", country);
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.props.put("zipcode", zipcode);
		this.zipcode = zipcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNatureofbusiness() {
		return natureofbusiness;
	}
	public void setNatureofbusiness(String natureofbusiness) {
		this.props.put("natureofbusiness", natureofbusiness);
		this.natureofbusiness = natureofbusiness;
	}
	public String getNoofbranches() {
		return noofbranches;
	}
	public void setNoofbranches(String noofbranches) {
		this.props.put("noofbranches", noofbranches);
		this.noofbranches = noofbranches;
	}
	public String getEstimatedgrosssale() {
		return estimatedgrosssale;
	}
	public void setEstimatedgrosssale(String estimatedgrosssale) {
		this.props.put("estimatedgrosssale", estimatedgrosssale);
		this.estimatedgrosssale = estimatedgrosssale;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.props.put("bankname", bankname);
		this.bankname = bankname;
	}
	public String getBankaccountno() {
		return bankaccountno;
	}
	public void setBankaccountno(String bankaccountno) {
		this.props.put("bankaccountno", bankaccountno);
		this.bankaccountno = bankaccountno;
	}
	public String getBankbranch() {
		return bankbranch;
	}
	public void setBankbranch(String bankbranch) {
		this.props.put("bankbranch", bankbranch);
		this.bankbranch = bankbranch;
	}
	public String getBankaccounttype() {
		return bankaccounttype;
	}
	public void setBankaccounttype(String bankaccounttype) {
		this.props.put("bankaccounttype", bankaccounttype);
		this.bankaccounttype = bankaccounttype;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.props.put("tin", tin);
		this.tin = tin;
	}
	public String getDtino() {
		return dtino;
	}
	public void setDtino(String dtino) {
		this.props.put("dtino", dtino);
		this.dtino = dtino;
	}
	public String getDateissueddti() {
		return dateissueddti;
	}
	public void setDateissueddti(String dateissueddti) {
		this.props.put("dateissueddti", dateissueddti);
		this.dateissueddti = dateissueddti;
	}
	public String getBusinesspermitno() {
		return businesspermitno;
	}
	public void setBusinesspermitno(String businesspermitno) {
		this.props.put("businesspermitno", businesspermitno);
		this.businesspermitno = businesspermitno;
	}
	public String getDateissuedbusinesspermit() {
		return dateissuedbusinesspermit;
	}
	public void setDateissuedbusinesspermit(String dateissuedbusinesspermit) {
		this.props.put("dateissuedbusinesspermit", dateissuedbusinesspermit);
		this.dateissuedbusinesspermit = dateissuedbusinesspermit;
	}
	public String getAuthfirstname() {
		return authfirstname;
	}
	public void setAuthfirstname(String authfirstname) {
		this.props.put("authfirstname", authfirstname);
		this.authfirstname = authfirstname;
	}
	public String getAuthmiddlename() {
		return authmiddlename;
	}
	public void setAuthmiddlename(String authmiddlename) {
		this.props.put("authmiddlename", authmiddlename);
		this.authmiddlename = authmiddlename;
	}
	public String getAuthlastname() {
		return authlastname;
	}
	public void setAuthlastname(String authlastname) {
		this.props.put("authlastname", authlastname);
		this.authlastname = authlastname;
	}
	public String getAuthdesignation() {
		return authdesignation;
	}
	public void setAuthdesignation(String authdesignation) {
		this.props.put("authdesignation", authdesignation);
		this.authdesignation = authdesignation;
	}
	public String getAuthmsisdn() {
		return authmsisdn;
	}
	public void setAuthmsisdn(String authmsisdn) {
		this.props.put("authmsisdn", authmsisdn);
		this.authmsisdn = authmsisdn;
	}
	public String getAuthemail() {
		return authemail;
	}
	public void setAuthemail(String authemail) {
		this.props.put("authemail", authemail);
		this.authemail = authemail;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.props.put("username", username);
		this.username = username;
	}
	public String getMonday() {
		return monday;
	}
	public void setMonday(String monday) {
		this.props.put("monday", monday);
		this.monday = monday;
	}
	public String getTuesday() {
		return tuesday;
	}
	public void setTuesday(String tuesday) {
		this.props.put("tuesday", tuesday);
		this.tuesday = tuesday;
	}
	public String getWednesday() {
		return wednesday;
	}
	public void setWednesday(String wednesday) {
		this.props.put("wednesday", wednesday);
		this.wednesday = wednesday;
	}
	public String getThursday() {
		return thursday;
	}
	public void setThursday(String thursday) {
		this.props.put("thursday", thursday);
		this.thursday = thursday;
	}
	public String getFriday() {
		return friday;
	}
	public void setFriday(String friday) {
		this.props.put("friday", friday);
		this.friday = friday;
	}
	public String getSaturday() {
		return saturday;
	}
	public void setSaturday(String saturday) {
		this.props.put("saturday", saturday);
		this.saturday = saturday;
	}
	public String getSunday() {
		return sunday;
	}
	public void setSunday(String sunday) {
		this.props.put("sunday", sunday);
		this.sunday = sunday;
	}
	public String getXcoordinate() {
		return xcoordinate;
	}
	public void setXcoordinate(String xcoordinate) {
		this.props.put("xcoordinate", xcoordinate);
		this.xcoordinate = xcoordinate;
	}
	public String getYcoordinate() {
		return ycoordinate;
	}
	public void setYcoordinate(String ycoordinate) {
		this.props.put("ycoordinate", ycoordinate);
		this.ycoordinate = ycoordinate;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.props.put("accountnumber", accountnumber);
		this.accountnumber = accountnumber;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.props.put("category", category);
		this.category = category;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.props.put("businesstype", businesstype);
		this.businesstype = businesstype;
	}
	public String getAuditdata() {
		return auditdata;
	}
	public void setAuditdata(String auditdata) {
		this.auditdata = auditdata;
	}	
	public String getPaymentmode() {
		return paymentmode;
	}
	public void setPaymentmode(String paymentmode) {
		this.props.put("paymentmode", paymentmode);
		this.paymentmode = paymentmode;
	}
	public String getPaymentterms() {
		return paymentterms;
	}
	public void setPaymentterms(String paymentterms) {
		this.props.put("paymentterms", paymentterms);
		this.paymentterms = paymentterms;
	}	
	public String getMaxamount() {
		return maxamount;
	}
	public void setMaxamount(String maxamount) {
		this.props.put("maxmount", maxamount);
		this.maxamount = maxamount;
	}
	public String getAdminsetup() {
		return adminsetup;
	}
	public void setAdminsetup(String adminsetup) {
		this.props.put("adminsetup", adminsetup);
		this.adminsetup = adminsetup;
	}
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.props.put("tariff", tariff);
		this.tariff = tariff;
	}
	public String getServicesairt() {
		return servicesairt;
	}
	public void setServicesairt(String servicesairt) {
		this.props.put("servicesairt", servicesairt);
		this.servicesairt = servicesairt;
	}
	public String getServicesbill() {
		return servicesbill;
	}
	public void setServicesbill(String servicesbill) {
		this.props.put("servicesbill", servicesbill);
		this.servicesbill = servicesbill;
	}
	public String getCommissionairt() {
		return commissionairt;
	}
	public void setCommissionairt(String commissionairt) {
		this.props.put("commissionairt", commissionairt);
		this.commissionairt = commissionairt;
	}
	public String getCommissionbill() {
		return commissionbill;
	}
	public void setCommissionbill(String commissionbill) {
		this.props.put("commissionbill", commissionbill);
		this.commissionbill = commissionbill;
	}
	public String getMerchantlevel() {
		return merchantlevel;
	}
	public void setMerchantlevel(String merchantlevel) {
		this.props.put("merchantlevel", merchantlevel);
		this.merchantlevel = merchantlevel;
	}
	public String getServicescashin() {
		return servicescashin;
	}
	public void setServicescashin(String servicescashin) {
		this.props.put("servicescashin", servicescashin);
		this.servicescashin = servicescashin;
	}
	public String getServiceskapamilya() {
		return serviceskapamilya;
	}
	public void setServiceskapamilya(String serviceskapamilya) {
		this.props.put("serviceskapamilya", serviceskapamilya);
		this.serviceskapamilya = serviceskapamilya;
	}
	public String getServicesabscbn() {
		return servicesabscbn;
	}
	public void setServicesabscbn(String servicesabscbn) {
		this.props.put("servicesabscbn", servicesabscbn);
		this.servicesabscbn = servicesabscbn;
	}
	public String getServicescashout() {
		return servicescashout;
	}
	public void setServicescashout(String servicescashout) {
		this.props.put("servicescashout", servicescashout);
		this.servicescashout = servicescashout;
	}
@SuppressWarnings("unchecked")
public String json(Business model) {
		
		JSONObject json = new JSONObject();
		for(String key: model.keys()){
			     json.put(key,model.getObject(key));
		}
		
		return json.toString();
	}
}
