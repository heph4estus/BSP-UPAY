package com.psi.business.v2.m;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ibayad.transmitters.client.SmsMessage;
import com.psi.audit.trail.m.GetAuditPreviousData;
import com.psi.business.util.Business;
import com.psi.business.util.EmailUtils;
import com.psi.business.util.HttpClientHelper;
import com.psi.business.util.OtherProperties;
import com.psi.wallet.branch.m.VipWalletRequest;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class ManageMerchant extends Business{
	protected String password;
	protected String remarks;
	protected String merchantlevel;
	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.accountnumber);
		  previous.setQuery("SELECT M.OWNERFIRSTNAME,M.OWNERMIDDLENAME,M.OWNERLASTNAME,M.OWNERSPECIFICADDRESS,M.OWNERCITY,M.OWNERPROVINCE,M.OWNERCOUNTRY,M.OWNERZIPCODE,B.CONTACTNUMBER MSISDN,M.LANDLINE,M.EMAIL,M.SECONDARYMSISDN,BUSINESS,B.ADDRESS,B.CITY,B.PROVINCE,B.COUNTRY,B.ZIPCODE,M.NATUREOFBUSINESS,M.NOOFBRANCH,M.ESTIMATEDGROSSSALE,M.BANKNAME,M.BANKACCOUNTNUMBER,M.BANKBRANCH,M.BANKACCOUNTTYPE,M.TIN,M.DTINO,M.DATEISSUEDDTI,M.BUSINESSPERMT,M.DATEISSUEDBUSINESSPERMIT,AUTHFIRSTNAME,AUTHMIDDLENAME,AUTHLASTNAME,AUTHDESIGNATION,AUTHMSISDN,AUTHEMAIL,AUTHUSERNAME,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,M.MODEOFPAYMENT,M.PAYMENTTERMS,M.MAXCREDITAMOUNT,M.ADMINSETUP,M.MERCHANTLEVEL FROM TBLBUSINESS B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER=?");	  
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public void getDataMerchantlevel(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.accountnumber);
		  previous.setQuery("SELECT M.MERCHANTLEVEL,M.ACCOUNTNUMBER FROM TBLBUSINESS B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER=?");	  
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public void getDataPndg(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.accountnumber);
		  previous.setQuery("SELECT REQUESTS FROM TBLMERCHANTPNDG WHERE ACCOUNTNUMBER=?");
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public void mechantdetails() throws ParseException{
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE ACCOUNTNUMBER=?", this.accountnumber);
	    JSONParser parser = new JSONParser();
	    JSONObject req = (JSONObject) parser.parse(row.getString("REQUESTS"));
	    this.setBusinessname(req.get("businessname").toString());
	    this.setOwnerfirstname(req.get("ownerfirstname").toString());
	    this.setOwnermiddlename(req.get("ownermiddlename").toString());
	    this.setOwnerlastname(req.get("ownerlastname").toString());
	    this.setOwnerspecificaddress(req.get("ownerspecificaddress").toString());
	    this.setOwnercity(req.get("ownercity").toString());
	    this.setOwnerprovince(req.get("ownerprovince").toString());
	    this.setOwnercountry(req.get("ownercountry").toString());	
	    this.setOwnerzipcode(req.get("ownerzipcode").toString());
	    this.setMsisdn(req.get("msisdn").toString());
	    this.setLandline(req.get("landline").toString());
	    this.setEmail(req.get("email").toString());
	    this.setSecondarymsisdn(req.get("secondarymsisdn").toString());
	    this.setSpecificaddress(req.get("specificaddress").toString());
	    this.setCity(req.get("city").toString());
	    this.setProvince(req.get("province").toString());
	    this.setCountry(req.get("country").toString());
	    this.setZipcode(req.get("zipcode").toString());
	    this.setImage(row.getString("IMAGE"));
	    this.setNatureofbusiness(req.get("natureofbusiness").toString());
	    this.setNoofbranches(req.get("noofbranches").toString());
	    this.setEstimatedgrosssale(req.get("estimatedgrosssale").toString());
	    this.setBankname(req.get("bankname").toString());
	    this.setBankaccountno(req.get("bankaccountno").toString());
	    this.setBankbranch(req.get("bankbranch").toString());
	    this.setBankaccounttype(req.get("bankaccounttype").toString());
	    this.setTin(req.get("tin").toString());
	    this.setDtino(req.get("dtino").toString());
	    this.setDateissueddti(req.get("dateissueddti").toString());
	    this.setBusinesspermitno(req.get("businesspermitno").toString());
	    this.setDateissuedbusinesspermit(req.get("dateissuedbusinesspermit").toString());
	    this.setAuthfirstname(req.get("authfirstname").toString());
	    this.setAuthmiddlename(req.get("authmiddlename").toString());
	    this.setAuthlastname(req.get("authlastname").toString());
	    this.setAuthdesignation(req.get("authdesignation").toString());
	    this.setAuthmsisdn(req.get("authmsisdn").toString());
	    this.setAuthemail(req.get("authemail").toString());
	    this.setUsername(req.get("username").toString());
	    this.setMonday(req.get("monday").toString());
	    this.setTuesday(req.get("tuesday").toString());
	    this.setWednesday(req.get("wednesday").toString());
	    this.setThursday(req.get("thursday").toString());
	    this.setFriday(req.get("friday").toString());
	    this.setSaturday(req.get("saturday").toString());
	    this.setSunday(req.get("sunday").toString());
	    this.setXcoordinate(req.get("xcoordinate").toString());
	    this.setYcoordinate(req.get("ycoordinate").toString());
	    this.setBusinesstype(req.get("businesstype").toString());
	    this.setCategory(req.get("category").toString());
	    this.setPaymentmode(req.get("paymentmode").toString());
	    this.setPaymentterms(req.get("paymentterms").toString());
	    this.setAdminsetup(req.get("adminsetup").toString());
	    this.setMaxamount(req.get("maxmount").toString());
	    this.setTariff(req.containsKey("tariff")?req.get("tariff").toString():"");
	    this.setServicesairt(req.containsKey("servicesairt")?req.get("servicesairt").toString():"");
	    this.setServicesbill(req.containsKey("servicesbill")?req.get("servicesbill").toString():"");
	    this.setCommissionairt(req.containsKey("commissionairt")?req.get("commissionairt").toString():"");
	    this.setCommissionbill(req.containsKey("commissionbill")?req.get("commissionbill").toString():"");
	    this.setMerchantlevel(req.containsKey("merchantlevel")?req.get("merchantlevel").toString():"");
	    this.setServicescashin(req.containsKey("servicescashin")?req.get("servicescashin").toString():"");
	    this.setServiceskapamilya(req.containsKey("serviceskapamilya")?req.get("serviceskapamilya").toString():"");
	    this.setServicesabscbn(req.containsKey("servicesabscbn")?req.get("servicesabscbn").toString():"");
	    this.setServicescashout(req.containsKey("servicescashout")?req.get("servicescashout").toString():"");
	}
	@SuppressWarnings("unchecked")
	public boolean approve() throws IOException, ParseException, java.text.ParseException{
		UISession sess = this.getAuthorizedSession();
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONObject request4 = new JSONObject();
		JSONObject request5 = new JSONObject();
		StringBuilder query1 = new StringBuilder("BEGIN\n");
		query1.append("INSERT INTO ADMDBMC.TBLACCOUNTINFO (ID, ACCOUNTNUMBER, STATUS, LOCKED, EMAIL, FQN, NETWORK, MSISDN, TYPE,REGDATE, LANGUAGE, PASSWORD, OTP, LASTNAME, FIRSTNAME, MIDDLENAME, TIN,SSS, ROOT, PARENT, CITY, SPECIFICADDRESS, POSTALCODE, COORDINATES)");
		query1.append(" (SELECT ID, ACCOUNTNUMBER, STATUS, LOCKED, EMAIL, FQN, NETWORK, '+'||MSISDN, TYPE,REGDATE, LANGUAGE, PASSWORD, OTP, LASTNAME, FIRSTNAME, MIDDLENAME, TIN,SSS, ROOT, PARENT, CITY, SPECIFICADDRESS, POSTALCODE, COORDINATES FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER = ?);\n");
		query1.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		if(SystemInfo.getDb().QueryUpdate(query1.toString(),this.accountnumber)>0){
		
			String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(1108) FROM DUAL", "0");	    
		    String code = SystemInfo.getDb().QueryScalar("SELECT TBLBRANCHESCODE_SEQ.NEXTVAL FROM DUAL", "0");
		    String branchcode = SystemInfo.getDb().QueryScalar("SELECT TBLBRANCHESCODE_SEQ.NEXTVAL FROM DUAL", "0");
		    String date = SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", "0");
		    
			request.put("type", "merchant");
			request.put("request-id", reqid);
			request.put("user-id", "POLEN");
			request.put("valid-id-desc","COMPANY");
				request2.put("password", "1234");
			request.put("auth", request2);
					request3.put("password", "1234");
					request3.put("account-name", this.businessname+" HEAD OFFICE");
					request3.put("business-name", this.businessname+" HEAD OFFICE");
					request3.put("first-name", this.ownerfirstname);
					request3.put("middle-name", this.ownermiddlename);
					request3.put("last-name", this.ownerlastname);
					request3.put("authorized-mobile", this.msisdn);
					request3.put("valid-id", "BAYAD"+code);
						request4.put("registration-date", date);
						request4.put("tin", this.tin);
							request5.put("region-code", this.province);
							request5.put("city-code", this.city);
							request5.put("postal-code", this.zipcode);
							request5.put("coordinates", this.xcoordinate +","+this.ycoordinate);
							request5.put("specific-address", this.specificaddress);
						request4.put("business-address", request5);
					request3.put("corporate-info", request4);
			request.put("subscriber", request3);
			
			Logger.LogServer(request.toString());
			Logger.LogServer("Merch Reg URL:"+prop.getUrl()+this.accountnumber+prop.getBranch_url());
			
			StringEntity entity = new StringEntity(request.toJSONString());
			
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token",prop.getToken());
		    byte[] apiResponse = client.httpPost(prop.getUrl()+this.accountnumber+prop.getBranch_url(), null, headers, null, entity);
		    
		    if(apiResponse.length>0){
		    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
			    Logger.LogServer(" response : " + new String(apiResponse));
			   
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			    	String tariffgroup = SystemInfo.getDb().QueryScalar("SELECT ID FROM TBLTARIFFGROUP WHERE GROUPNAME = ?", "", this.merchantlevel);
			    	String token = PasswordGenerator.generatePassword(9, PasswordGenerator.NUMERIC_CHAR);
			    	String password = PasswordGenerator.generatePassword(5, PasswordGenerator.NUMERIC_CHAR);
			    	StringBuilder query = new StringBuilder("BEGIN\n");
						query.append("INSERT INTO TBLBUSINESS(BUSINESS,ACCOUNTNUMBER,ADDRESS,BUSINESSCODE,CITY,CONTACTNUMBER,PROOFADDRESS,PROVINCE,XORDINATES,YORDINATES,ZIPCODE,LOCATION,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,COUNTRY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); \n");					
						query.append("INSERT INTO TBLBRANCHES(BRANCH,ACCOUNTNUMBER,ADDRESS,BRANCHCODE,CITY,CONTACTNUMBER,PROOFADDRESS,PROVINCE,XORDINATES,YORDINATES,ZIPCODE,LOCATION,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,COUNTRY,KEYACCOUNT,RAFILELOCATION,RAFILENAME,STATUS,TIN,NATUREOFBUSINESS,GROSSSALES,BANKNAME,BANKTYPE,BANKACCOUNTNUMBER,MAINBRANCH) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1,?,?,?,?,?,?,1); \n");
						query.append("INSERT INTO TBLMERCHANTDETAILS(ACCOUNTNUMBER,OWNERFIRSTNAME,OWNERMIDDLENAME,OWNERLASTNAME,OWNERSPECIFICADDRESS,OWNERCITY,OWNERPROVINCE,OWNERCOUNTRY,OWNERZIPCODE,LANDLINE,EMAIL,SECONDARYMSISDN,NATUREOFBUSINESS,NOOFBRANCH,ESTIMATEDGROSSSALE,BANKNAME,BANKACCOUNTNUMBER,BANKBRANCH,BANKACCOUNTTYPE,TIN,DTINO,DATEISSUEDDTI,BUSINESSPERMT,DATEISSUEDBUSINESSPERMIT,AUTHFIRSTNAME,AUTHMIDDLENAME,AUTHLASTNAME,AUTHDESIGNATION,AUTHMSISDN,AUTHEMAIL,AUTHUSERNAME,BUSINESSTYPE,CATEGORY,MODEOFPAYMENT,PAYMENTTERMS,MAXCREDITAMOUNT,ADMINSETUP,UPGRADED,MERCHANTLEVEL) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,TO_DATE(?,'YYYY-MM-DD'),?,?,?,?,?,?,?,?,?,?,?,?,?,'YES',?); \n");					
						query.append("INSERT INTO TBLMERCHANTDETAILS(ACCOUNTNUMBER,OWNERFIRSTNAME,OWNERMIDDLENAME,OWNERLASTNAME,OWNERSPECIFICADDRESS,OWNERCITY,OWNERPROVINCE,OWNERCOUNTRY,OWNERZIPCODE,LANDLINE,EMAIL,SECONDARYMSISDN,NATUREOFBUSINESS,NOOFBRANCH,ESTIMATEDGROSSSALE,BANKNAME,BANKACCOUNTNUMBER,BANKBRANCH,BANKACCOUNTTYPE,TIN,DTINO,DATEISSUEDDTI,BUSINESSPERMT,DATEISSUEDBUSINESSPERMIT,AUTHFIRSTNAME,AUTHMIDDLENAME,AUTHLASTNAME,AUTHDESIGNATION,AUTHMSISDN,AUTHEMAIL,AUTHUSERNAME,BUSINESSTYPE,CATEGORY,MODEOFPAYMENT,PAYMENTTERMS,MAXCREDITAMOUNT,ADMINSETUP,UPGRADED,MERCHANTLEVEL) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,TO_DATE(?,'YYYY-MM-DD'),?,?,?,?,?,?,?,?,?,?,?,?,?,'YES',?); \n");									
						query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,ACCOUNTNUMBER,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL,TOKENEXPIRY,TOKEN,MIDDLENAME) VALUES(?,?,?,?,'MANAGER','ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39',SYSDATE+7,?,?); \n");
						query.append("INSERT INTO TBLPOSUSERS(ACCOUNTNUMBER, TERMINALID, USERID, PASSWORD, BRANCHCODE, TYPE, MSISDN, DEFAULTPWD, FIRSTNAME, LASTNAME)Values(?, '4339D22FA2180E39', ?, ?, ?, 'manager', ?, 0, ?,?); \n");
						query.append("UPDATE TBLMERCHANTPNDG SET STATUS = 'APPROVED',DATEMODIFIED=SYSDATE,MODIFIEDBY=? WHERE ACCOUNTNUMBER= ?;\n");
						query.append("INSERT INTO TBLAMLACCOUNTTYPERECEIVE (ACCOUNTNUMBER,USERSLEVEL,KEY,MINAMOUNT,MAXAMOUNT,MAXAMOUNTDAY,MAXAMOUNTWEEK,MAXAMOUNTMONTH,MAXTRANSDAY,MAXTRANSWEEK,MAXTRANSMONTH,ALERTLEVEL,SCOPE,STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,'','CREDIT',0); \n");
						query.append("INSERT INTO TBLAMLACCOUNTTYPERECEIVE (ACCOUNTNUMBER,USERSLEVEL,KEY,MINAMOUNT,MAXAMOUNT,MAXAMOUNTDAY,MAXAMOUNTWEEK,MAXAMOUNTMONTH,MAXTRANSDAY,MAXTRANSWEEK,MAXTRANSMONTH,ALERTLEVEL,SCOPE,STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,'','CREDIT',0); \n");
						query.append("INSERT INTO TBLCOMPANYTARIFF (COMPANY,ACCOUNTNUMBER,TARIFFGROUP,PRIORITY) VALUES(?,?,?,1); \n");
						query.append("DELETE FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER =?;\n");
						query.append("DELETE FROM TBLDOCUMENTS WHERE ACCOUNTNUMBER = ? AND DOCTYPE NOT IN (SELECT TYPE FROM TBLREQUIREDDOCUMENTS WHERE SERVICETYPE=? AND CATEGORY=?);\n");
						query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
					SystemInfo.getDb().QueryUpdate(query.toString(),
									this.businessname, this.accountnumber, this.specificaddress,"BAYAD"+code,this.city,"+"+this.msisdn,this.image,this.province,this.xcoordinate,this.ycoordinate,this.zipcode,this.specificaddress,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,
									this.businessname+" HEAD OFFICE", object.get("account-number").toString(), this.specificaddress,"BAYAD"+branchcode,this.city,this.msisdn,this.image,this.province,this.xcoordinate,this.ycoordinate,this.zipcode,this.specificaddress,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,this.accountnumber,"","",this.tin,this.natureofbusiness,this.estimatedgrosssale,this.bankname,this.bankaccounttype,this.bankaccountno,
									this.accountnumber,this.ownerfirstname,this.ownermiddlename,this.ownerlastname,this.ownerspecificaddress,this.ownercity,this.ownerprovince,this.ownercountry,this.ownerzipcode,this.landline,this.email,this.secondarymsisdn,this.natureofbusiness,this.noofbranches,this.estimatedgrosssale,this.bankname,this.bankaccountno,this.bankbranch,this.bankaccounttype,this.tin,this.dtino,this.dateissueddti,this.businesspermitno,this.dateissuedbusinesspermit,this.authfirstname,this.authmiddlename,this.authlastname,this.authdesignation,this.authmsisdn,this.authemail,this.username,this.businesstype,this.category,this.paymentmode,this.paymentterms,LongUtil.toLong(this.maxamount),this.adminsetup,this.merchantlevel,
									object.get("account-number").toString(),this.ownerfirstname,this.ownermiddlename,this.ownerlastname,this.ownerspecificaddress,this.ownercity,this.ownerprovince,this.ownercountry,this.ownerzipcode,this.landline,this.email,this.secondarymsisdn,this.natureofbusiness,this.noofbranches,this.estimatedgrosssale,this.bankname,this.bankaccountno,this.bankbranch,this.bankaccounttype,this.tin,this.dtino,this.dateissueddti,this.businesspermitno,this.dateissuedbusinesspermit,this.authfirstname,this.authmiddlename,this.authlastname,this.authdesignation,this.authmsisdn,this.authemail,this.username,this.businesstype,this.category,this.paymentmode,this.paymentterms,LongUtil.toLong(this.maxamount),this.adminsetup,this.merchantlevel,
									this.authemail, this.authfirstname, this.authlastname,this.authmsisdn,this.username,object.get("account-number").toString(),password,SystemInfo.getDb().getCrypt(),this.username,token,this.authmiddlename,
									object.get("account-number").toString(),this.username,password,"BAYAD"+branchcode,this.authmsisdn,this.authfirstname,this.authlastname,
									sess.getAccount().getUsername(),this.accountnumber,
									this.accountnumber,"MANAGER","ALLOC",1000,this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),100,100,100,
									object.get("account-number").toString(),"MANAGER","ALLOC",1000,this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),100,100,100,
									this.accountnumber,object.get("account-number").toString(),tariffgroup,
									this.accountnumber,
									this.accountnumber,this.businesstype,this.category);	
					if(!this.tariff.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLCOMPANYTARIFF (ACCOUNTNUMBER, TARIFFGROUP,COMPANY) VALUES";
						String strArray[] = this.tariff.split(",");
						for(int i=0; i < strArray.length; i++){
							querytariff.append(plans);
							querytariff.append("('"+object.get("account-number").toString()+"',"+strArray[i]+",'"+this.accountnumber+"')");
							querytariff.append(";\n");
						}
						 String sql =  querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					if(!this.servicesairt.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) VALUES";
						String strArray[] = this.servicesairt.split(",");
						for(int i=0; i < strArray.length; i++){
							DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSERVICES WHERE ID = ?", strArray[i]);
							querytariff.append(plans);
							querytariff.append("('"+strArray[i]+"',"+object.get("account-number").toString()+",'"+row.getString("CATEGORY")+"')");
							querytariff.append(";\n");
						}
						 String sql =  querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					if(!this.servicesbill.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) VALUES";
						String strArray[] = this.servicesbill.split(",");
						for(int i=0; i < strArray.length; i++){
							DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSERVICES WHERE ID = ?", strArray[i]);
							querytariff.append(plans);
							querytariff.append("('"+strArray[i]+"',"+object.get("account-number").toString()+",'"+row.getString("CATEGORY")+"')");
							querytariff.append(";\n");
						}
						 String sql = querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					if(!this.servicescashin.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) VALUES";
						String strArray[] = this.servicescashin.split(",");
						for(int i=0; i < strArray.length; i++){
							DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSERVICES WHERE ID = ?", strArray[i]);
							querytariff.append(plans);
							querytariff.append("('"+strArray[i]+"',"+object.get("account-number").toString()+",'"+row.getString("CATEGORY")+"')");
							querytariff.append(";\n");
						}
						 String sql = querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					if(!this.serviceskapamilya.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) VALUES";
						String strArray[] = this.serviceskapamilya.split(",");
						for(int i=0; i < strArray.length; i++){
							DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSERVICES WHERE ID = ?", strArray[i]);
							querytariff.append(plans);
							querytariff.append("('"+strArray[i]+"',"+object.get("account-number").toString()+",'"+row.getString("CATEGORY")+"')");
							querytariff.append(";\n");
						}
						 String sql = querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					if(!this.servicesabscbn.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) VALUES";
						String strArray[] = this.servicesabscbn.split(",");
						for(int i=0; i < strArray.length; i++){
							DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSERVICES WHERE ID = ?", strArray[i]);
							querytariff.append(plans);
							querytariff.append("('"+strArray[i]+"',"+object.get("account-number").toString()+",'"+row.getString("CATEGORY")+"')");
							querytariff.append(";\n");
						}
						 String sql = querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					if(!this.servicescashout.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) VALUES";
						String strArray[] = this.servicescashout.split(",");
						for(int i=0; i < strArray.length; i++){
							DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSERVICES WHERE ID = ?", strArray[i]);
							querytariff.append(plans);
							querytariff.append("('"+strArray[i]+"',"+object.get("account-number").toString()+",'"+row.getString("CATEGORY")+"')");
							querytariff.append(";\n");
						}
						 String sql = querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					if(!this.commissionairt.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLAIRTCOMMISSION (CREATEDBY, IBAYADCODE, BASEPRICE, SELLINGPRICE, FIXED, PERCENTAGE, ACCOUNTNUMBER, CATEGORY, DATEFROM, DATETO, ACCOUNTTYPE,TARIFF ) VALUES";
						
						JSONArray strArray = (JSONArray)new JSONParser().parse(this.commissionairt);
						for(int i=0; i < strArray.size(); i++){
							HashMap<String,Object> prod = (HashMap<String, Object>) strArray.get(i);
							querytariff.append(plans);
							querytariff.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("baseprice").toString())+","+LongUtil.toLong(prod.get("buyingprice").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+object.get("account-number").toString()+"','"+prod.get("category").toString()+"',TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'),TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'),'MERCHANT','')");
							querytariff.append(";\n");
						}
						 String sql = querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					if(!this.commissionbill.isEmpty()){
						StringBuilder querytariff = new StringBuilder("BEGIN\n");
						String plans = "INSERT INTO TBLBILLSCOMMISSION (CREATEDBY, IBAYADCODE, FIXED, PERCENTAGE, ACCOUNTNUMBER, CATEGORY, DATEFROM, DATETO,ACCOUNTTYPE,TARIFF ) VALUES";
						
						JSONArray strArray = (JSONArray)new JSONParser().parse(this.commissionbill);
						for(int i=0; i < strArray.size(); i++){
							HashMap<String,Object> prod = (HashMap<String, Object>) strArray.get(i);
							querytariff.append(plans);
							querytariff.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+object.get("account-number").toString()+"','"+prod.get("category").toString()+"',TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'),TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'),'MERCHANT','')");
							querytariff.append(";\n");
						}
						 String sql = querytariff
								    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
						 SystemInfo.getDb().QueryUpdate(sql.toString());
					}
					DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", this.username);
					   JSONObject urltoken = new JSONObject();
						urltoken.put("Id", user.getString("USERID"));
						urltoken.put("Token", user.getString("TOKEN"));
						urltoken.put("Exp", user.getString("TOKENEXPIRY"));
						
						
						byte[] contoken = null;
						try {						
							contoken = urltoken.toJSONString().getBytes("UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
						String encodetoken = DatatypeConverter.printBase64Binary(contoken);
							try{
								EmailUtils.sendApproveMerch(this.businessname,sess.getAccount().getUsername());
								EmailUtils.sendmanagercreds(this.authemail, this.authfirstname, this.authlastname, password,this.username,"authenticate?q="+encodetoken);
								SmsMessage sms = new SmsMessage();
								sms.setSender("UPay");
								sms.setDestination(this.authmsisdn);
								String msg = "Approved na ang iyong Kapamilya Rewards Wallet application. Please expect the onboarding kit in 3 to 5 days.";
								sms.setMessage(msg);
								JSONObject metadata=new JSONObject();
								metadata.put("initiator", "lucena");
								sms.setMetadata(metadata);
								sms.send();		
							}catch (Exception e) { 
									Logger.LogServer(e);
							}
					this.setAccountnumber(object.get("account-number").toString());
			    	this.setState(new ObjectState("0",object.get("message").toString()));
//			    	VipWalletRequest alloc = new VipWalletRequest();
//			    	alloc.setAccountnumber(object.get("account-number").toString());
//			    	alloc.setAmount("300.00");
//			    	alloc.setReferencenumber("300"+object.get("account-number").toString());
//			    	alloc.setRemarks("0");
//			    	alloc.setStatus("PAID");
//			    	alloc.setDescription("PROMO PREFUND 300");
//			    	alloc.setId(String.valueOf(sess.getAccount().getId()));
//			    	if(alloc.topup()){
//			    		return true;
//			    	}else{
//			    		return false;
//			    	}
			    	return true;
				  }else{
				    this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
			    	return false;
				  }
		    }else{
		    	this.setState(new ObjectState("99","System is busy"));
		    	return false;
		    }
		}else{
			this.setState(new ObjectState("99","System is busy"));
	    	return false;
		}
		
	}
	public boolean preapprove(){
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLMERCHANTPNDG SET STATUS = 'PRE-APPROVED',PREDATEMODIFIED=SYSDATE,PREMODIFIEDBY=? WHERE ACCOUNTNUMBER= ?;\n");
			query.append("UPDATE ADMDBMC.TBLACCOUNTINFOPNDG SET KYCSTATUS = 'PRE-APPROVED' WHERE ACCOUNTNUMBER = ?;\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			EmailUtils.sendPreApproveMerch(this.businessname, sess.getAccount().getUsername());
		return SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername(), this.accountnumber,this.accountnumber)>0;
	}
	public boolean forescalition(){
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLMERCHANTPNDG SET STATUS = 'FOR ESCALATION',REMARKS=?,PREDATEMODIFIED=SYSDATE,PREMODIFIEDBY=? WHERE ACCOUNTNUMBER= ?;\n");
			query.append("UPDATE ADMDBMC.TBLACCOUNTINFOPNDG SET KYCSTATUS = 'FOR ESCALATION' WHERE ACCOUNTNUMBER = ?;\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			EmailUtils.sendPreRejectMerch(this.businessname, sess.getAccount().getUsername());
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.remarks,sess.getAccount().getUsername(), this.accountnumber,this.accountnumber)>0;
	}
	public boolean reject(){
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLMERCHANTPNDG SET STATUS = 'REJECTED',REMARKS=?,DATEMODIFIED=SYSDATE,MODIFIEDBY=? WHERE ACCOUNTNUMBER= ?;\n");
			query.append("UPDATE ADMDBMC.TBLACCOUNTINFOPNDG SET KYCSTATUS = 'REJECTED' WHERE ACCOUNTNUMBER = ?;\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			EmailUtils.sendRejectMerch(this.businessname, sess.getAccount().getUsername());
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.remarks,sess.getAccount().getUsername(), this.accountnumber,this.accountnumber)>0;
	}
	public boolean edit(){
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLMERCHANTPNDG SET DATEUPDATED=SYSDATE,UPDATEDBY=?,REQUESTS=?,IMAGE=? WHERE ACCOUNTNUMBER= ?;\n");
			query.append("UPDATE ADMDBMC.TBLACCOUNTINFOPNDG SET EMAIL=?, FQN=ENCRYPT(?,?,ACCOUNTNUMBER), MSISDN=?, LASTNAME=?, FIRSTNAME=?, MIDDLENAME=?, TIN=?, CITY=?, SPECIFICADDRESS=?, POSTALCODE=?, COORDINATES=? WHERE ACCOUNTNUMBER = ?;\n");
			query.append("DELETE FROM TBLDOCUMENTS WHERE ACCOUNTNUMBER = ? AND DOCTYPE NOT IN (SELECT TYPE FROM TBLREQUIREDDOCUMENTS WHERE SERVICETYPE=? AND CATEGORY=?);\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			EmailUtils.sendEditMerch(this.businessname, sess.getAccount().getUsername());
		return SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername(),this.json(this),this.image,this.accountnumber,
								this.email,this.businessname,SystemInfo.getDb().getCrypt(),this.msisdn,this.ownerlastname,this.ownerfirstname,this.ownermiddlename,this.tin,this.city,this.specificaddress,this.zipcode,this.xcoordinate +","+ this.ycoordinate,this.accountnumber,
									this.accountnumber,this.businesstype,this.category)>0;

	}
	public boolean editreg() throws java.text.ParseException{
		UISession sess = this.getAuthorizedSession();
		String tariffgroup = SystemInfo.getDb().QueryScalar("SELECT ID FROM TBLTARIFFGROUP WHERE GROUPNAME = ?", "", this.merchantlevel);
		StringBuilder query = new StringBuilder("BEGIN\n");
		StringBuilder merchlevelinsertb = new StringBuilder();
		DataRowCollection rr = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT = ?", this.accountnumber);
		if(!rr.isEmpty()){
			for(DataRow row:rr){
				DataRow exist = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCOMPANYTARIFF WHERE ACCOUNTNUMBER = ? AND TARIFFGROUP IN (SELECT ID FROM TBLTARIFFGROUP WHERE GROUPNAME IN ('sme_merchant','corp_merchant'))", row.getString("ACCOUNTNUMBER").toString());
				if(exist.isEmpty()){
					merchlevelinsertb.append("INSERT INTO TBLCOMPANYTARIFF (COMPANY,ACCOUNTNUMBER,TARIFFGROUP,PRIORITY) VALUES('"+this.accountnumber+"','"+row.getString("ACCOUNTNUMBER")+"','"+tariffgroup+"',1); \n ");
				}else{
					merchlevelinsertb.append("UPDATE TBLCOMPANYTARIFF SET TARIFFGROUP='"+tariffgroup+"' WHERE ACCOUNTNUMBER='"+row.getString("ACCOUNTNUMBER").toString()+"' AND TARIFFGROUP='"+exist.getString("TARIFFGROUP").toString()+"';\n");
				}
			}
		}
			query.append("UPDATE TBLBUSINESS SET ADDRESS=?,CITY=?,CONTACTNUMBER=?,PROOFADDRESS=?,PROVINCE=?,XORDINATES=?,YORDINATES=?,ZIPCODE=?,MONDAY=?,TUESDAY=?,WEDNESDAY=?,THURSDAY=?,FRIDAY=?,SATURDAY=?,SUNDAY=?,COUNTRY=? WHERE ACCOUNTNUMBER=?;\n");
			query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET DATEUPDATED=SYSDATE,MSISDN=?,EMAIL=?, LASTNAME=?, FIRSTNAME=?, MIDDLENAME=?, TIN=?, CITY=?, SPECIFICADDRESS=?, POSTALCODE=?, COORDINATES=? WHERE ACCOUNTNUMBER = ?;\n");
			query.append("UPDATE TBLMERCHANTDETAILS SET OWNERFIRSTNAME=?,OWNERMIDDLENAME=?,OWNERLASTNAME=?,OWNERSPECIFICADDRESS=?,OWNERCITY=?,OWNERPROVINCE=?,OWNERCOUNTRY=?,OWNERZIPCODE=?,LANDLINE=?,EMAIL=?,SECONDARYMSISDN=?,NATUREOFBUSINESS=?,NOOFBRANCH=?,ESTIMATEDGROSSSALE=?,BANKNAME=?,BANKACCOUNTNUMBER=?,BANKBRANCH=?,BANKACCOUNTTYPE=?,TIN=?,DTINO=?,DATEISSUEDDTI=TO_DATE(?,'YYYY-MM-DD'),BUSINESSPERMT=?,DATEISSUEDBUSINESSPERMIT=TO_DATE(?,'YYYY-MM-DD'),BUSINESSTYPE=?,CATEGORY=?,MODIFIEDBY=?,MODEOFPAYMENT=?,PAYMENTTERMS=?,MAXCREDITAMOUNT=?,ADMINSETUP=?,MERCHANTLEVEL=? WHERE ACCOUNTNUMBER=?;\n");
			query.append("UPDATE TBLAMLACCOUNTTYPERECEIVE SET MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=? WHERE ACCOUNTNUMBER=?; \n");			
			query.append(merchlevelinsertb);
			query.append("DELETE FROM TBLDOCUMENTS WHERE ACCOUNTNUMBER = ? AND DOCTYPE NOT IN (SELECT TYPE FROM TBLREQUIREDDOCUMENTS WHERE SERVICETYPE=? AND CATEGORY=?);\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");

		return SystemInfo.getDb().QueryUpdate(query.toString(),this.specificaddress,this.city,"+"+this.msisdn,this.image,this.province,this.xcoordinate,this.ycoordinate,this.zipcode,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,this.accountnumber,
											"+"+this.msisdn,this.email,this.ownerlastname,this.ownerfirstname,this.ownermiddlename,this.tin,this.city,this.specificaddress,this.zipcode,this.xcoordinate+","+this.ycoordinate,this.accountnumber,
											this.ownerfirstname,this.ownermiddlename,this.ownerlastname,this.ownerspecificaddress,this.ownercity,this.ownerprovince,this.ownercountry,this.ownerzipcode,this.landline,this.email,this.secondarymsisdn,this.natureofbusiness,this.noofbranches,this.estimatedgrosssale,this.bankname,this.bankaccountno,this.bankbranch,this.bankaccounttype,this.tin,this.dtino,this.dateissueddti,this.businesspermitno,this.dateissuedbusinesspermit,this.businesstype,this.category,sess.getAccount().getUsername(),this.paymentmode,this.paymentterms,LongUtil.toLong(this.maxamount),this.adminsetup,this.merchantlevel,this.accountnumber,
											this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.paymentterms==null || this.paymentterms.isEmpty() ?  LongUtil.toLong(this.estimatedgrosssale):LongUtil.toLong(this.maxamount),this.accountnumber,
											this.accountnumber,this.businesstype,this.category)>0;

	}
	public boolean updatemerchantlevel() throws java.text.ParseException{
		String tariffgroup = SystemInfo.getDb().QueryScalar("SELECT ID FROM TBLTARIFFGROUP WHERE GROUPNAME = ?", "", this.merchantlevel);
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * TBLCOMPANYTARIFF WHERE ACCOUNTNUMBER = ? AND COMPANY=? AND TARIFFGROUP IN (SELECT ID FROM TBLTARIFFGROUP WHERE GROUPNAME IN ('sme_merchant','corp_merchant'))", this.accountnumber,this.accountnumber);
		if(r.isEmpty()){
			StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLMERCHANTDETAILS SET MERCHANTLEVEL=? WHERE ACCOUNTNUMBER=?;\n");
				query.append("INSERT INTO TBLCOMPANYTARIFF (COMPANY,ACCOUNTNUMBER,TARIFFGROUP,PRIORITY) VALUES(?,?,?,1); \n");			
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.merchantlevel,this.accountnumber,
									this.accountnumber,this.accountnumber,tariffgroup)>0;
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLMERCHANTDETAILS SET MERCHANTLEVEL=? WHERE ACCOUNTNUMBER=?;\n");
			query.append("UPDATE TBLCOMPANYTARIFF SET TARIFFGROUP=? WHERE ACCOUNTNUMBER=? AND COMPANY=? ; \n");			
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.merchantlevel,this.accountnumber,
								tariffgroup,this.accountnumber,this.accountnumber)>0;
		}
	}
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE PASSWORD=ENCRYPT(?,?,USERNAME) AND USERNAME =?",this.password,SystemInfo.getDb().getCrypt(),sess.getAccount().getUserName() ).size()>0;
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;			
	}
	public boolean existpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;			
	}
	public boolean existbusiness(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE FQN = ENCRYPT(?,?,ACCOUNTNUMBER) ", this.businessname,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean existmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN=? ", this.msisdn).size()>0;
	}
	public boolean existauthmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN=? ", this.authmsisdn).size()>0;
	}
	public boolean existauthemail(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL)=? ", this.authemail.toUpperCase()).size()>0;
	}
	public boolean existauthusername(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME)=? ", this.username.toUpperCase()).size()>0;
	}
//	for registered validation
	public boolean existbusinessreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE FQN = ENCRYPT(?,?,ACCOUNTNUMBER) AND ACCOUNTNUMBER<>?", this.businessname,SystemInfo.getDb().getCrypt(),this.accountnumber).size()>0;
	}
	public boolean existpndgbusiness(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE FQN = ENCRYPT(?,?,ACCOUNTNUMBER) AND ACCOUNTNUMBER<>?", this.businessname,SystemInfo.getDb().getCrypt(),this.accountnumber).size()>0;
	}
	public boolean existauthmsisdnreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN=? AND ACCOUNTNUMBER <> ?", this.authmsisdn,this.accountnumber).size()>0;
	}
	public boolean existmsisdnpndgreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE MSISDN =? AND ACCOUNTNUMBER<>?", this.msisdn,this.accountnumber).size()>0;
	}
	public boolean existauthemailreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL)=? AND ACCOUNTNUMBER <> ?", this.authemail.toUpperCase(),this.accountnumber).size()>0;
	}
	public boolean existauthemailpndgreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE UPPER(REQUESTS) LIKE UPPER(?) AND ACCOUNTNUMBER<>?", "%"+this.authemail+"%",this.accountnumber).size()>0;
	}
	public boolean existauthusernamereg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME)=? AND ACCOUNTNUMBER <> ?", this.username.toUpperCase(),this.accountnumber).size()>0;
	}
	public boolean existauthusernamepndgreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE UPPER(REQUESTS) LIKE UPPER(?) AND ACCOUNTNUMBER<>?", "%"+this.username+"%",this.accountnumber).size()>0;
	}
	public boolean existmsisdnreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN = ? AND ACCOUNTNUMBER <> ?", "+"+this.msisdn,this.accountnumber).size()>0;			
	}
	public boolean existauthmsisdnpndgreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE REQUESTS LIKE ? AND ACCOUNTNUMBER <> ?", "%+"+this.msisdn+"%",this.accountnumber).size()>0;			
	}
	public boolean existemailreg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE UPPER(EMAIL) = ? AND ACCOUNTNUMBER <> ?", this.email.toUpperCase(),this.accountnumber).size()>0;			
	}
	public boolean existemailregdetails(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTDETAILS WHERE UPPER(EMAIL) = ? AND ACCOUNTNUMBER <> ?", this.email.toUpperCase(),this.accountnumber).size()>0;			
	}
	public boolean existemailregpndgdetails(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE UPPER(REQUESTS) LIKE ? AND ACCOUNTNUMBER <> ?", "%"+this.email.toUpperCase()+"%",this.accountnumber).size()>0;			
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
		this.remarks = remarks;
	}
	public String getMerchantlevel() {
		return merchantlevel;
	}
	public void setMerchantlevel(String merchantlevel) {
		this.props.put("merchantlevel", merchantlevel);
		this.merchantlevel = merchantlevel;
	}
	
	/**
	 * MVO 18-02-2020
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();

		/*
		 * Super Class fields
		 */
		for (Field f : superFields) {
			try {
				f.setAccessible(true);
				if (f.get(this) != null && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}

		}
		/*
		 * Class fields
		 */
		Field[] classFields = this.getClass().getDeclaredFields();
		for (Field f : classFields) {
			f.setAccessible(true);
			try {

				if (f.get(this) != null && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}

		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.lastIndexOf("|"));
		return sb.toString();
	}
}
