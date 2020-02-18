package com.psi.branch.m;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.audit.trail.m.GetAuditPreviousData;
import com.psi.branch.utils.Branch;
import com.psi.branch.utils.EmailUtils;
import com.psi.wallet.branch.m.VipWalletRequest;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class ManageBranch extends Branch{
	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.accountnumber);
		  previous.setQuery("SELECT BRANCH,B.ADDRESS,B.CITY,B.PROVINCE,B.COUNTRY,B.ZIPCODE,B.CONTACTNUMBER,B.XORDINATES,B.YORDINATES,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,B.ACCOUNTNUMBER,B.RAFILELOCATION,B.RAFILENAME,M.TIN,M.NATUREOFBUSINESS,M.ESTIMATEDGROSSSALE,M.BANKNAME,M.BANKACCOUNTTYPE,M.BANKACCOUNTNUMBER FROM TBLBRANCHES B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER=?");	  
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public void mechantdetails() throws ParseException{
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE ACCOUNTNUMBER=?", this.accountnumber);
	    JSONParser parser = new JSONParser();
	    JSONObject req = (JSONObject) parser.parse(row.getString("REQUESTS"));
	    this.setBranchname(req.get("branchname").toString());
	    this.setAddress(req.get("address").toString());
	    this.setCity(req.get("city").toString());
	    this.setProvince(req.get("province").toString());
	    this.setCountry(req.get("country").toString());
	    this.setZipcode(req.get("zipcode").toString());
	    this.setContactnumber(req.get("contactnumber").toString());
	    this.setXordinate(req.get("xcoordinate").toString());
	    this.setYordinate(req.get("ycoordinate").toString());
	    this.setImage(row.getString("IMAGE"));
	    this.setNatureofbusiness(req.get("natureofbusiness").toString());
	    this.setGrosssales(req.get("grosssales").toString());
	    this.setBankname(req.get("bankname").toString());
	    this.setBanknumber(req.get("banknumber").toString());
	    this.setBanktype(req.get("banktype").toString());
	    this.setTin(req.get("tin").toString());
	    this.setRafilelocation(req.get("refilelocation").toString());
	    this.setRafilename(req.get("rafilename").toString());
	    this.setMonday(req.get("monday").toString());
	    this.setTuesday(req.get("tuesday").toString());
	    this.setWednesday(req.get("wednesday").toString());
	    this.setThursday(req.get("thursday").toString());
	    this.setFriday(req.get("friday").toString());
	    this.setSaturday(req.get("saturday").toString());
	    this.setSunday(req.get("sunday").toString());
	    this.setKeyaccount(req.get("ACCOUNTNUMBER").toString());
	    this.setPaymentmode(req.get("paymentmode").toString());
	    this.setPaymentterms(req.get("paymentterms").toString());
	    this.setBankbranch(req.get("bankbranch").toString());
	    this.setMaxamount(req.get("maxmount").toString());
	    this.setSpecificaddress(req.get("specificaddress").toString());
	}
	@SuppressWarnings("unchecked")
	public boolean approve() throws java.text.ParseException, ParseException, IOException{
		UISession sess = this.getAuthorizedSession();
		String code = SystemInfo.getDb().QueryScalar("SELECT TBLBRANCHESCODE_SEQ.NEXTVAL FROM DUAL", "0");

		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO ADMDBMC.TBLACCOUNTINFO (ID, ACCOUNTNUMBER, STATUS, LOCKED, EMAIL, FQN, NETWORK, MSISDN, TYPE,REGDATE, LANGUAGE, PASSWORD, OTP, LASTNAME, FIRSTNAME, MIDDLENAME, TIN,SSS, ROOT, PARENT, CITY, SPECIFICADDRESS, POSTALCODE, COORDINATES)");
		query.append(" (SELECT ID, ACCOUNTNUMBER, STATUS, LOCKED, EMAIL, FQN, NETWORK,MSISDN, TYPE,REGDATE, LANGUAGE, PASSWORD, OTP, LASTNAME, FIRSTNAME, MIDDLENAME, TIN,SSS, ROOT, PARENT, CITY, SPECIFICADDRESS, POSTALCODE, COORDINATES FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER = ?);\n");
		query.append("INSERT INTO TBLBRANCHES(BRANCH,ACCOUNTNUMBER,ADDRESS,BRANCHCODE,CITY,CONTACTNUMBER,PROOFADDRESS,PROVINCE,XORDINATES,YORDINATES,ZIPCODE,LOCATION,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,COUNTRY,KEYACCOUNT,RAFILELOCATION,RAFILENAME,STATUS,TIN,NATUREOFBUSINESS,GROSSSALES,BANKNAME,BANKTYPE,BANKACCOUNTNUMBER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1,?,?,?,?,?,?); \n");
		query.append("INSERT INTO TBLMERCHANTDETAILS(ACCOUNTNUMBER,OWNERFIRSTNAME,OWNERMIDDLENAME,OWNERLASTNAME,OWNERSPECIFICADDRESS,OWNERCITY,OWNERPROVINCE,OWNERCOUNTRY,OWNERZIPCODE,LANDLINE,EMAIL,SECONDARYMSISDN,NATUREOFBUSINESS,NOOFBRANCH,ESTIMATEDGROSSSALE,BANKNAME,BANKACCOUNTNUMBER,BANKBRANCH,BANKACCOUNTTYPE,TIN,BRGYPERMITNO,DATEISSUEDBRGYPERMIT,BUSINESSPERMT,DATEISSUEDBUSINESSPERMIT,AUTHFIRSTNAME,AUTHMIDDLENAME,AUTHLASTNAME,AUTHDESIGNATION,AUTHMSISDN,AUTHEMAIL,AUTHUSERNAME,BUSINESSTYPE,CATEGORY,MODEOFPAYMENT,PAYMENTTERMS,MAXCREDITAMOUNT,UPGRADED) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,TO_DATE(?,'YYYY-MM-DD'),?,?,?,?,?,?,?,?,?,?,?,?,'YES'); \n");									
		query.append("UPDATE TBLMERCHANTPNDG SET STATUS = 'APPROVED',DATEMODIFIED=SYSDATE,MODIFIEDBY=? WHERE ACCOUNTNUMBER= ?;\n");
		query.append("INSERT INTO TBLAMLACCOUNTTYPERECEIVE (ACCOUNTNUMBER,USERSLEVEL,KEY,MINAMOUNT,MAXAMOUNT,MAXAMOUNTDAY,MAXAMOUNTWEEK,MAXAMOUNTMONTH,MAXTRANSDAY,MAXTRANSWEEK,MAXTRANSMONTH,ALERTLEVEL,SCOPE,STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,'','CREDIT',0); \n");
		query.append("DELETE FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER =?;\n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(), this.accountnumber,
									this.branchname,this.accountnumber,this.specificaddress,"BAYAD"+code,this.city,this.contactnumber,this.image,this.province,this.xordinate,this.yordinate,this.zipcode,this.address,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,this.keyaccount,this.rafilelocation,this.rafilename,this.tin,this.natureofbusiness,this.grosssales,this.bankname,this.banktype,this.banknumber,
									this.accountnumber,"","","","","","","","","","","",this.natureofbusiness,"",this.grosssales,this.bankname,this.banknumber,this.bankbranch,this.banktype,this.tin,"","","","","","","","","","","","","",this.paymentmode,this.paymentterms,LongUtil.toLong(this.maxamount),
									sess.getAccount().getUsername(),this.accountnumber,
									this.accountnumber,"MANAGER","ALLOC",1000,LongUtil.toLong(this.maxamount),LongUtil.toLong(this.maxamount),LongUtil.toLong(this.maxamount),LongUtil.toLong(this.maxamount),100,100,100,
									this.accountnumber);
		DataRow main = SystemInfo.getDb().QueryDataRow("SELECT B.ACCOUNTNUMBER,BUSINESSTYPE,CATEGORY,MD.ADMINSETUP,MD.MERCHANTLEVEL FROM TBLBRANCHES B INNER JOIN TBLMERCHANTDETAILS MD ON B.ACCOUNTNUMBER = MD.ACCOUNTNUMBER WHERE MAINBRANCH=1 AND KEYACCOUNT = ?",this.keyaccount);
		if(!main.isEmpty()){
			StringBuilder querytariff = new StringBuilder("BEGIN\n");
			querytariff.append("INSERT INTO TBLCOMPANYTARIFF (ACCOUNTNUMBER, TARIFFGROUP,COMPANY) SELECT ?,TARIFFGROUP,COMPANY FROM TBLCOMPANYTARIFF WHERE ACCOUNTNUMBER = ?; \n");
			querytariff.append("INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) SELECT SERVICES,?,CATEGORY FROM TBLSERVICESCONFIG WHERE ACCOUNTNUMBER=?; \n");
			querytariff.append("INSERT INTO TBLAIRTCOMMISSION (CREATEDBY, IBAYADCODE, BASEPRICE, SELLINGPRICE, FIXED, PERCENTAGE, ACCOUNTNUMBER, CATEGORY, DATEFROM, DATETO, ACCOUNTTYPE,TARIFF ) SELECT ?, IBAYADCODE, BASEPRICE, SELLINGPRICE, FIXED, PERCENTAGE, ?, CATEGORY, DATEFROM, DATETO, ACCOUNTTYPE,TARIFF FROM TBLAIRTCOMMISSION WHERE ACCOUNTNUMBER = ?;\n");
			querytariff.append("INSERT INTO TBLBILLSCOMMISSION (CREATEDBY, IBAYADCODE, FIXED, PERCENTAGE, ACCOUNTNUMBER, CATEGORY, DATEFROM, DATETO,ACCOUNTTYPE,TARIFF ) SELECT ?, IBAYADCODE, FIXED, PERCENTAGE, ?, CATEGORY, DATEFROM, DATETO,ACCOUNTTYPE,TARIFF FROM TBLBILLSCOMMISSION WHERE ACCOUNTNUMBER=?;\n");
			querytariff.append("UPDATE TBLMERCHANTDETAILS SET BUSINESSTYPE=?, CATEGORY=?, ADMINSETUP=?, MERCHANTLEVEL=? WHERE ACCOUNTNUMBER = ?;\n");
			querytariff.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n");
			SystemInfo.getDb().QueryUpdate(querytariff.toString(),this.accountnumber,main.getString("ACCOUNTNUMBER").toString(),
					this.accountnumber,main.getString("ACCOUNTNUMBER").toString(),
					sess.getAccount().getUsername(),this.accountnumber,main.getString("ACCOUNTNUMBER").toString(),
					sess.getAccount().getUsername(),this.accountnumber,main.getString("ACCOUNTNUMBER").toString(),
					main.getString("BUSINESSTYPE").toString(),main.getString("CATEGORY").toString(),main.getString("ADMINSETUP").toString(),main.getString("MERCHANTLEVEL").toString(),this.accountnumber);
		}
		if(res>0){
				DataRow mgr = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(PASSWORD,?,USERNAME) PASS,U.* FROM TBLUSERS U WHERE ACCOUNTNUMBER = ? AND USERSLEVEL = 'MANAGER'", SystemInfo.getDb().getCrypt(),this.accountnumber);
				if(!mgr.isEmpty()){
					JSONObject urltoken = new JSONObject();
					urltoken.put("Id", mgr.getString("USERID"));
					urltoken.put("Token", mgr.getString("TOKEN"));
					urltoken.put("Exp", mgr.getString("TOKENEXPIRY"));					
					
					byte[] contoken = null;
					try {						
						contoken = urltoken.toJSONString().getBytes("UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					String encodetoken = DatatypeConverter.printBase64Binary(contoken);
						try{
								EmailUtils.send(mgr.getString("EMAIL"), mgr.getString("FIRSTNAME"), mgr.getString("LASTNAME"), mgr.getString("PASS"),mgr.getString("USERNAME"),"authenticate?q="+encodetoken);
						}catch (Exception e) { 
								Logger.LogServer(e);
						}
				}
				return true;
//				VipWalletRequest alloc = new VipWalletRequest();
//		    	alloc.setAccountnumber(this.accountnumber);
//		    	alloc.setAmount("300.00");
//		    	alloc.setReferencenumber("300"+this.accountnumber);
//		    	alloc.setRemarks("0");
//		    	alloc.setStatus("PAID");
//		    	alloc.setDescription("PROMO PREFUND 300");
//		    	alloc.setId(String.valueOf(sess.getAccount().getId()));
//		    	if(alloc.topup()){
//		    		return true;
//		    	}else{
//		    		return false;
//		    	}
			}else{
				return false;
			}		
	}
	public boolean preapprove(){
		
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLMERCHANTPNDG SET STATUS = 'PRE-APPROVED',PREDATEMODIFIED=SYSDATE,PREMODIFIEDBY=? WHERE ACCOUNTNUMBER= ?;\n");
			query.append("UPDATE ADMDBMC.TBLACCOUNTINFOPNDG SET KYCSTATUS = 'PRE-APPROVED' WHERE ACCOUNTNUMBER = ?;\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername(), this.accountnumber,this.accountnumber)>0;
		
	}
	public boolean reject(){
				
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLMERCHANTPNDG SET STATUS = 'REJECTED',REMARKS=?,DATEMODIFIED=SYSDATE,MODIFIEDBY=? WHERE ACCOUNTNUMBER= ?;\n");
			query.append("UPDATE ADMDBMC.TBLACCOUNTINFOPNDG SET KYCSTATUS = 'REJECTED' WHERE ACCOUNTNUMBER = ?;\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.remarks,sess.getAccount().getUsername(), this.accountnumber,this.accountnumber)>0;
		
	}
	public boolean prereject(){
		
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLMERCHANTPNDG SET STATUS = 'FOR ESCALATION',REMARKS=?,PREDATEMODIFIED=SYSDATE,PREMODIFIEDBY=? WHERE ACCOUNTNUMBER= ?;\n");
			query.append("UPDATE ADMDBMC.TBLACCOUNTINFOPNDG SET KYCSTATUS = 'FOR ESCALATION' WHERE ACCOUNTNUMBER = ?;\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.remarks,sess.getAccount().getUsername(), this.accountnumber,this.accountnumber)>0;
		
	}
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE PASSWORD=ENCRYPT(?,?,USERNAME) AND USERNAME =?",this.password,SystemInfo.getDb().getCrypt(),sess.getAccount().getUserName() ).size()>0;
	}
	
	public boolean exist(){
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber);
		if(!rr.getString("KEYACCOUNT").equals("834591471124")){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;	
		}else{
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;
		}
	}
}
