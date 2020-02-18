package com.ibayad.customer.m;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class ManageUpgradeCustomer extends Customers{
protected String accountnumber;
protected String remarks;
protected String password;
public String getDataFqn(){  
	  return  SystemInfo.getDb().QueryScalar("SELECT FIRSTNAME ||' '||LASTNAME FQN FROM TBLUSERS WHERE ACCOUNTNUMBER=?","",this.accountnumber);	
}
public void getCustomersData() throws ParseException{
	String data = SystemInfo.getDb().QueryScalar("SELECT UPGRADEINFO FROM TBLUPGRADEDCUSTOMER WHERE ACCOUNTNUMBER = ?", "", this.accountnumber);
	JSONParser parser = new JSONParser();
    JSONObject req = (JSONObject) parser.parse(data.toString());
    
    this.setBarangay(req.get("barangay").toString());
    this.setBirthdate(req.get("birthdate").toString());
    this.setCashierid(req.get("cashier-id").toString());
    this.setCity(req.get("city").toString());
    this.setFirstname(req.get("first-name").toString());
    this.setGender(req.get("gender").toString());
    this.setLastname(req.get("last-name").toString());
    this.setNationality(req.get("nationality").toString());
    this.setMiddlename(req.get("middle-name").toString());
    this.setNatureofwork(req.get("nature-of-work").toString());
    this.setPlaceofbirth(req.get("place-of-birth").toString());
    this.setProvince(req.get("province").toString());
    this.setSourceoffund(req.get("source-of-fund").toString());
    this.setStreetname(req.get("street-name").toString());
    this.setZipcode(req.get("zip-code").toString());
	
}
	public boolean approvevo() throws java.text.ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUPGRADEDCUSTOMER SET STATUS = 'VO-APPROVED',MODIFIEDDATEVO=SYSDATE,MODIFIEDBYVO=?,CREDITLIMIT=10000000 WHERE ACCOUNTNUMBER= ?;\n");		
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername().toString(), this.accountnumber);					
			if(res>0){
				EmailUtils.sendApproveUpgrade(this.accountnumber, sess.getAccount().getUsername().toString(),this.getDataFqn());	
				return true;
			}else{
				return false;
			}		
	}
	public boolean approve() throws java.text.ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUPGRADEDCUSTOMER SET STATUS = 'APPROVED',MODIFIEDDATE=SYSDATE,MODIFIEDBY=?,SOURCEOFFUNDS=?, NATUREOFWORK=? WHERE ACCOUNTNUMBER= ?;\n");		
		query.append("UPDATE TBLUSERS SET SPECIFICADDRESS=?, BARANGAY=?, CITY =?, PROVINCE=?, GENDER=?,PLACEOFBIRTH=?,NATIONALITY=?,DATEOFBIRTH=TO_DATE(?,'Month DD, YYYY'), FIRSTNAME=?, MIDDLENAME=?, LASTNAME=?, POSTALCODE=? WHERE ACCOUNTNUMBER =?;\n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET FIRSTNAME=?,MIDDLENAME=?,LASTNAME=?,SPECIFICADDRESS=?,CITY=?,POSTALCODE=? WHERE ACCOUNTNUMBER=?;\n");
		query.append("UPDATE TBLPOSUSERS SET FIRSTNAME=?, LASTNAME=? WHERE ACCOUNTNUMBER=?;\n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername().toString(),this.sourceoffund,this.natureofwork, this.accountnumber,
												this.streetname,this.barangay,this.city,this.province,this.gender,this.placeofbirth,this.nationality,this.birthdate,this.firstname,this.middlename,this.lastname, this.zipcode,this.accountnumber,
												this.firstname,this.middlename,this.lastname,this.streetname,this.city,this.zipcode,this.accountnumber,
												this.firstname,this.lastname,this.accountnumber);					
			if(res>0){
					EmailUtils.sendapprovenotif(this.accountnumber);
					EmailUtils.sendapprovesms(this.accountnumber);
					EmailUtils.sendApproveUpgrade(this.getDataFqn(), sess.getAccount().getUsername().toString(),"",this.accountnumber,"");				
				return true;
			}else{
				return false;
			}		
	}
	
	public boolean reject(){
				
		UISession sess = this.getAuthorizedSession();
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUPGRADEDCUSTOMER SET STATUS = 'REJECTED',MODIFIEDDATE=SYSDATE,MODIFIEDBY=?,REMARKS=? WHERE ACCOUNTNUMBER= ?;\n");		
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername().toString(),this.remarks, this.accountnumber);					
			if(res>0){
					EmailUtils.sendrejectnotif(this.accountnumber);
					//EmailUtils.sendRejectUpgrade(branch.getString("BRANCH").toString(), sess.getAccount().getUsername().toString());
					EmailUtils.sendRejectUpgrade(this.getDataFqn(), sess.getAccount().getUsername().toString(),this.accountnumber);
				return true;
			}else{
				return false;
			}			
	}
	public boolean existupgraded(){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUPGRADEDCUSTOMER WHERE ACCOUNTNUMBER = ? AND STATUS = 'APPROVED'", this.accountnumber).size()>0;

	}
	
	public boolean existupgradedpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUPGRADEDCUSTOMER WHERE ACCOUNTNUMBER = ? AND STATUS = 'PENDING'", this.accountnumber).size()>0;

	}
	
	public boolean existupgradedpndgvo(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUPGRADEDCUSTOMER WHERE ACCOUNTNUMBER = ? AND STATUS = 'VO-APPROVED'", this.accountnumber).size()>0;

	}
	
	public boolean existupgradedpndgreject(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUPGRADEDCUSTOMER WHERE ACCOUNTNUMBER = ? AND STATUS IN ('PENDING','VO-APPROVED')", this.accountnumber).size()>0;

	}
	
	public boolean blacklisted(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBLACKLIST WHERE UPPER(FIRSTNAME) = UPPER(?) AND UPPER(MIDDLENAME) = UPPER(?) AND UPPER(LASTNAME) = UPPER(?) AND DATEOFBIRTH = TO_DATE(?,'MonthDD,YYYY')", this.firstname,this.middlename,this.lastname,this.birthdate.trim()).size()>0;
	}
	
	public boolean bcblacklisted(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBCBLACKLIST WHERE UPPER(FIRSTNAME) = UPPER(?) AND UPPER(MIDDLENAME) = UPPER(?) AND UPPER(LASTNAME) = UPPER(?) AND DATEOFBIRTH = TO_DATE(?,'MonthDD,YYYY')", this.firstname,this.middlename,this.lastname,this.birthdate.trim()).size()>0;
	}
	
	public boolean watchlisted(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBCWATCHLIST WHERE UPPER(FIRSTNAME) = UPPER(?) AND UPPER(MIDDLENAME) = UPPER(?) AND UPPER(LASTNAME) = UPPER(?) AND DATEOFBIRTH = TO_DATE(?,'MonthDD,YYYY')", this.firstname,this.middlename,this.lastname,this.birthdate.trim()).size()>0;
	}
	
	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
