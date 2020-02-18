package com.psi.branch.utils;


import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.Model;

public class Branch extends Model{
	
	protected String branchname;
	protected String address;
	protected String city;
	protected String province;
	protected String country;
	protected String zipcode;
	protected String contactnumber;
	protected String storehours;
	protected String operatinghour;
	protected String proofaddress;
	protected String xordinate;
	protected String yordinate;
	protected String image;
	protected String id;
	protected String accountnumber;
	protected String status;
	protected String monday;
	protected String tuesday;
	protected String wednesday;
	protected String thursday;
	protected String friday;
	protected String saturday;
	protected String sunday;
	protected String rafilelocation;
	protected String rafilename;
	protected String tin;
	protected String natureofbusiness;
	protected String password;
	protected String remarks;
	protected String grosssales;
	protected String bankname;
	protected String banktype;
	protected String banknumber;
	protected String keyaccount;
	protected String auditdata;
	protected String paymentmode;
	protected String paymentterms;
	protected String maxamount;
	protected String bankbranch;
	protected String reason;
	protected String notify;
	protected String specificaddress;
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.props.put("branchname", branchname);
		this.branchname = branchname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.props.put("address", address);
		this.address = address;
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
	public String getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(String contactnumber) {
		this.props.put("contactnumber", contactnumber);
		this.contactnumber = contactnumber;
	}
	public String getStorehours() {
		return storehours;
	}
	public void setStorehours(String storehours) {
		this.storehours = storehours;
	}
	public String getOperatinghour() {
		return operatinghour;
	}
	public void setOperatinghour(String operatinghour) {
		this.operatinghour = operatinghour;
	}
	public String getProofaddress() {
		return proofaddress;
	}
	public void setProofaddress(String proofaddress) {
		this.proofaddress = proofaddress;
	}
	public String getXordinate() {
		return xordinate;
	}
	public void setXordinate(String xordinate) {
		this.props.put("xcoordinate", xordinate);
		this.xordinate = xordinate;
	}
	public String getYordinate() {
		return yordinate;
	}
	public void setYordinate(String yordinate) {
		this.props.put("ycoordinate", yordinate);
		this.yordinate = yordinate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.props.put("id", id);
		this.id = id;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.props.put("accountnumber", accountnumber);
		this.accountnumber = accountnumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.props.put("status", status);
		this.status = status;
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
	public String getRafilelocation() {
		return rafilelocation;
	}
	public void setRafilelocation(String rafilelocation) {
		this.props.put("refilelocation", rafilelocation);
		this.rafilelocation = rafilelocation;
	}
	public String getRafilename() {
		return rafilename;
	}
	public void setRafilename(String rafilename) {
		this.props.put("rafilename", rafilename);
		this.rafilename = rafilename;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.props.put("tin", tin);
		this.tin = tin;
	}
	public String getNatureofbusiness() {
		return natureofbusiness;
	}
	public void setNatureofbusiness(String natureofbusiness) {
		this.props.put("natureofbusiness", natureofbusiness);
		this.natureofbusiness = natureofbusiness;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.props.put("remarks", remarks);
		this.remarks = remarks;
	}
	public String getGrosssales() {
		return grosssales;
	}
	public void setGrosssales(String grosssales) {
		this.props.put("grosssales", grosssales);
		this.grosssales = grosssales;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.props.put("bankname", bankname);
		this.bankname = bankname;
	}
	public String getBanktype() {
		return banktype;
	}
	public void setBanktype(String banktype) {
		this.props.put("banktype", banktype);
		this.banktype = banktype;
	}
	public String getBanknumber() {
		return banknumber;
	}
	public void setBanknumber(String banknumber) {
		this.props.put("banknumber", banknumber);
		this.banknumber = banknumber;
	}
	public String getKeyaccount() {
		return keyaccount;
	}
	public void setKeyaccount(String keyaccount) {
		this.keyaccount = keyaccount;
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
	public String getBankbranch() {
		return bankbranch;
	}
	public void setBankbranch(String bankbranch) {
		this.props.put("bankbranch", bankbranch);
		this.bankbranch = bankbranch;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
	}
	public String getSpecificaddress() {
		return specificaddress;
	}
	public void setSpecificaddress(String specificaddress) {
		this.props.put("specificaddress", specificaddress);
		this.specificaddress = specificaddress;
	}
	@SuppressWarnings("unchecked")
	public String json(Branch model) {
			
			JSONObject json = new JSONObject();
			for(String key: model.keys()){
				     json.put(key,model.getObject(key));
			}
			
			return json.toString();
		}
	

}
