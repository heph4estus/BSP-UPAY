package com.psi.branch.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;
import com.tlc.gui.modules.session.UISession;

@SuppressWarnings("serial")
public class BranchCollection extends ModelCollection{
protected String accountnumber;
protected String id;
	@Override
	public boolean hasRows() {
		
		if(this.accountnumber.equals("ALL")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows(" SELECT * FROM VWOUTLETS");
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }	
		         m.setProperty("MAXCREDITAMOUNT", row.getString("MAXCREDITAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXCREDITAMOUNT").toString())));		         
		         
		         DataRow mgr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ? AND USERSLEVEL IN ('MANAGER','BUSINESSOWNER')", row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("MANAGERFIRSTNAME", mgr.getString("FIRSTNAME").toString());
		         m.setProperty("MANAGERMIDDLENAME", mgr.getString("MIDDLENAME").toString());
		         m.setProperty("MANAGERLASTNAME", mgr.getString("LASTNAME").toString());
		         m.setProperty("MANAGERMSISDN", mgr.getString("MSISDN").toString());
		         m.setProperty("MANAGEREMAIL", mgr.getString("EMAIL").toString());
		         m.setProperty("MANAGERUSERNAME", mgr.getString("USERNAME").toString());
		         String adminsetup = SystemInfo.getDb().QueryScalar("SELECT ADMINSETUP FROM TBLMERCHANTDETAILS WHERE ACCOUNTNUMBER =? ", "", this.accountnumber);
		         m.setProperty("ADMINSETUP", adminsetup);
		         String balance = SystemInfo.getDb().QueryScalar("SELECT DECRYPT(AMOUNT,?,CS.ACCOUNTNUMBER) AMOUNT FROM ADMDBMC.TBLCURRENTSTOCK CS INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON CS.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE CS.WALLETID = AI.ROOT AND CS.ACCOUNTNUMBER = ? ", "", SystemInfo.getDb().getCrypt(),row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BALANCE", balance == null ? "" : LongUtil.toString(Long.parseLong(balance)));		         
		             
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
		}else{
			DataRowCollection r = SystemInfo.getDb().QueryDataRows(" SELECT AI.REGDATE MERCHDATEREG, B.ID,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) BRANCH,CONTACTNUMBER,B.ACCOUNTNUMBER,B.BRANCHCODE,XORDINATES,YORDINATES,ADDRESS,B.CITY,PROVINCE,COUNTRY,ZIPCODE,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,"
			          +" OWNERFIRSTNAME,OWNERMIDDLENAME,OWNERLASTNAME,OWNERSPECIFICADDRESS,OWNERCITY,OWNERPROVINCE,OWNERCOUNTRY,OWNERZIPCODE,LANDLINE,M.EMAIL,SECONDARYMSISDN,"
			          +" M.NATUREOFBUSINESS,NOOFBRANCH,ESTIMATEDGROSSSALE,M.BANKNAME,M.BANKACCOUNTNUMBER,BANKBRANCH,BANKACCOUNTTYPE,M.TIN,DTINO,TO_CHAR(DATEISSUEDDTI,'YYYY-MM-DD') DATEISSUEDDTI,"
			          +" BUSINESSPERMT,TO_CHAR(DATEISSUEDBUSINESSPERMIT,'YYYY-MM-DD') DATEISSUEDBUSINESSPERMIT,AUTHFIRSTNAME,AUTHMIDDLENAME,AUTHLASTNAME,AUTHDESIGNATION,AUTHMSISDN,AUTHEMAIL,AUTHUSERNAME,BUSINESSTYPE,CATEGORY,AI.STATUS,M.MODEOFPAYMENT,M.PAYMENTTERMS,CASE WHEN M.MAXCREDITAMOUNT IS NULL THEN 0 ELSE M.MAXCREDITAMOUNT END MAXCREDITAMOUNT,M.ADMINSETUP,M.OVERRIDECOMMISSION" 
			          +" FROM TBLBRANCHES B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER"
			          +" WHERE B.KEYACCOUNT=? AND UPGRADED = 'YES' AND AI.TYPE = ENCRYPT('dealer',?,AI.ACCOUNTNUMBER) "
			          +" UNION ALL "
			          +" SELECT AI.REGDATE MERCHDATEREG, B.ID,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) BRANCH,CONTACTNUMBER,B.ACCOUNTNUMBER,B.BRANCHCODE,XORDINATES,YORDINATES,ADDRESS,B.CITY,PROVINCE,COUNTRY,ZIPCODE,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY, "
			          +" OWNERFIRSTNAME, OWNERMIDDLENAME,OWNERLASTNAME, OWNERSPECIFICADDRESS,OWNERCITY,OWNERPROVINCE,OWNERCOUNTRY,OWNERZIPCODE,LANDLINE,M.EMAIL,SECONDARYMSISDN, "
                    +" M.NATUREOFBUSINESS,CASE WHEN NOOFBRANCH IS NULL THEN 0 ELSE NOOFBRANCH END NOOFBRANCH,ESTIMATEDGROSSSALE,M.BANKNAME,M.BANKACCOUNTNUMBER,BANKBRANCH,BANKACCOUNTTYPE,M.TIN,DTINO,TO_CHAR(DATEISSUEDDTI,'YYYY-MM-DD') DATEISSUEDDTI, "
                    +" BUSINESSPERMT,TO_CHAR(DATEISSUEDBUSINESSPERMIT,'YYYY-MM-DD') DATEISSUEDBUSINESSPERMIT,AUTHFIRSTNAME,AUTHMIDDLENAME,AUTHLASTNAME,AUTHDESIGNATION,AUTHMSISDN,AUTHEMAIL,AUTHUSERNAME,BUSINESSTYPE,CATEGORY,AI.STATUS,MODEOFPAYMENT,PAYMENTTERMS,CASE WHEN MAXCREDITAMOUNT IS NULL THEN 0 ELSE MAXCREDITAMOUNT END MAXCREDITAMOUNT,ADMINSETUP,OVERRIDECOMMISSION " 
                    +" FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER LEFT JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE B.KEYACCOUNT=? AND B.ACCOUNTNUMBER NOT IN (SELECT ACCOUNTNUMBER FROM TBLMERCHANTDETAILS WHERE UPGRADED = 'YES') AND AI.TYPE = ENCRYPT('dealer',?,AI.ACCOUNTNUMBER)",SystemInfo.getDb().getCrypt(),this.accountnumber,SystemInfo.getDb().getCrypt(),SystemInfo.getDb().getCrypt(),this.accountnumber,SystemInfo.getDb().getCrypt());

				if (!r.isEmpty())
				{
				for(DataRow row: r){
				ReportItem m = new ReportItem();
				
				for (String key : row.keySet()) {
				m.setProperty(key, row.getString(key).toString());
				}	
				m.setProperty("MAXCREDITAMOUNT", row.getString("MAXCREDITAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXCREDITAMOUNT").toString())));		         
				
				DataRow mgr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ? AND USERSLEVEL IN ('MANAGER','BUSINESSOWNER')", row.getString("ACCOUNTNUMBER").toString());
				m.setProperty("MANAGERFIRSTNAME", mgr.getString("FIRSTNAME").toString());
				m.setProperty("MANAGERMIDDLENAME", mgr.getString("MIDDLENAME").toString());
				m.setProperty("MANAGERLASTNAME", mgr.getString("LASTNAME").toString());
				m.setProperty("MANAGERMSISDN", mgr.getString("MSISDN").toString());
				m.setProperty("MANAGEREMAIL", mgr.getString("EMAIL").toString());
				m.setProperty("MANAGERUSERNAME", mgr.getString("USERNAME").toString());
				String adminsetup = SystemInfo.getDb().QueryScalar("SELECT ADMINSETUP FROM TBLMERCHANTDETAILS WHERE ACCOUNTNUMBER =? ", "", this.accountnumber);
				m.setProperty("ADMINSETUP", adminsetup);
//				String balance = SystemInfo.getDb().QueryScalar("SELECT DECRYPT(AMOUNT,?,ACCOUNTNUMBER) AMOUNT FROM ADMDBMC.TBLCURRENTSTOCK WHERE ACCOUNTNUMBER =? ", "", SystemInfo.getDb().getCrypt(),row.getString("ACCOUNTNUMBER").toString());
				String balance = SystemInfo.getDb().QueryScalar("SELECT DECRYPT(AMOUNT,?,CS.ACCOUNTNUMBER) AMOUNT FROM ADMDBMC.TBLCURRENTSTOCK CS INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON CS.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE CS.WALLETID = AI.ROOT AND CS.ACCOUNTNUMBER = ? ", "", SystemInfo.getDb().getCrypt(),row.getString("ACCOUNTNUMBER").toString());
				m.setProperty("BALANCE", balance == null ? "" : LongUtil.toString(Long.parseLong(balance)));		         
				
				add(m);
				}
				}
				return r.size() > 0;
		}
	}
	public boolean hasRowsPndgUpgrade() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT B.ID,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) BRANCH,CONTACTNUMBER,B.ACCOUNTNUMBER,B.BRANCHCODE,XORDINATES,YORDINATES,ADDRESS,B.CITY,PROVINCE,COUNTRY,ZIPCODE,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,"
													          +" OWNERFIRSTNAME,OWNERMIDDLENAME,OWNERLASTNAME,OWNERSPECIFICADDRESS,OWNERCITY,OWNERPROVINCE,OWNERCOUNTRY,OWNERZIPCODE,LANDLINE,M.EMAIL,SECONDARYMSISDN,"
													          +" M.NATUREOFBUSINESS,NOOFBRANCH,ESTIMATEDGROSSSALE,M.BANKNAME,M.BANKACCOUNTNUMBER,BANKBRANCH,BANKACCOUNTTYPE,M.TIN,DTINO,TO_CHAR(DATEISSUEDDTI,'YYYY-MM-DD') DATEISSUEDDTI,"
													          +" BUSINESSPERMT,TO_CHAR(DATEISSUEDBUSINESSPERMIT,'YYYY-MM-DD') DATEISSUEDBUSINESSPERMIT,AUTHFIRSTNAME,AUTHMIDDLENAME,AUTHLASTNAME,AUTHDESIGNATION,AUTHMSISDN,AUTHEMAIL,AUTHUSERNAME,BUSINESSTYPE,CATEGORY,AI.STATUS,M.MODEOFPAYMENT,M.PAYMENTTERMS,CASE WHEN M.MAXCREDITAMOUNT IS NULL THEN 0 ELSE M.MAXCREDITAMOUNT END MAXCREDITAMOUNT ,M.ADMINSETUP,M.OVERRIDECOMMISSION,"
													          +" AI.REGDATE, M.MERCHANTLEVEL,M.REASON,M.NOTIFY"
													          +" FROM TBLBRANCHES B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER "
													          +" WHERE UPGRADED = 'PENDING' AND AI.TYPE = ENCRYPT('dealer',?,AI.ACCOUNTNUMBER)",SystemInfo.getDb().getCrypt(),SystemInfo.getDb().getCrypt());
     
     if (!r.isEmpty())
     {
    	 for(DataRow row: r){
	         ReportItem m = new ReportItem();
	         
	         for (String key : row.keySet()) {
		 	        m.setProperty(key, row.getString(key).toString());
		 	 }	
	         m.setProperty("MAXCREDITAMOUNT", row.getString("MAXCREDITAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXCREDITAMOUNT").toString())));		         
	         
	         DataRow mgr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ? AND USERSLEVEL IN ('MANAGER','BUSINESSOWNER')", row.getString("ACCOUNTNUMBER").toString());
	         m.setProperty("MANAGERFIRSTNAME", mgr.getString("FIRSTNAME").toString());
	         m.setProperty("MANAGERMIDDLENAME", mgr.getString("MIDDLENAME").toString());
	         m.setProperty("MANAGERLASTNAME", mgr.getString("LASTNAME").toString());
	         m.setProperty("MANAGERMSISDN", mgr.getString("MSISDN").toString());
	         m.setProperty("MANAGEREMAIL", mgr.getString("EMAIL").toString());
	         m.setProperty("MANAGERUSERNAME", mgr.getString("USERNAME").toString());
	         add(m);
    	 }
     }
     return r.size() > 0;
}
	public boolean hasRowsRejectUpgrade() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT B.ID,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) BRANCH,CONTACTNUMBER,B.ACCOUNTNUMBER,B.BRANCHCODE,XORDINATES,YORDINATES,ADDRESS,B.CITY,PROVINCE,COUNTRY,ZIPCODE,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,"
													          +" OWNERFIRSTNAME,OWNERMIDDLENAME,OWNERLASTNAME,OWNERSPECIFICADDRESS,OWNERCITY,OWNERPROVINCE,OWNERCOUNTRY,OWNERZIPCODE,LANDLINE,M.EMAIL,SECONDARYMSISDN,"
													          +" M.NATUREOFBUSINESS,NOOFBRANCH,ESTIMATEDGROSSSALE,M.BANKNAME,M.BANKACCOUNTNUMBER,BANKBRANCH,BANKACCOUNTTYPE,M.TIN,DTINO,TO_CHAR(DATEISSUEDDTI,'YYYY-MM-DD') DATEISSUEDDTI,"
													          +" BUSINESSPERMT,TO_CHAR(DATEISSUEDBUSINESSPERMIT,'YYYY-MM-DD') DATEISSUEDBUSINESSPERMIT,AUTHFIRSTNAME,AUTHMIDDLENAME,AUTHLASTNAME,AUTHDESIGNATION,AUTHMSISDN,AUTHEMAIL,AUTHUSERNAME,BUSINESSTYPE,CATEGORY,AI.STATUS,M.MODEOFPAYMENT,M.PAYMENTTERMS,CASE WHEN M.MAXCREDITAMOUNT IS NULL THEN 0 ELSE M.MAXCREDITAMOUNT END MAXCREDITAMOUNT ,M.ADMINSETUP,M.OVERRIDECOMMISSION,"
													          +" AI.REGDATE, M.MERCHANTLEVEL,M.REASON,M.NOTIFY"
													          +" FROM TBLBRANCHES B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER "
													          +" WHERE UPGRADED = 'REJECTED' AND AI.TYPE = ENCRYPT('dealer',?,AI.ACCOUNTNUMBER)",SystemInfo.getDb().getCrypt(),SystemInfo.getDb().getCrypt());
     
     if (!r.isEmpty())
     {
    	 for(DataRow row: r){
	         ReportItem m = new ReportItem();
	         
	         for (String key : row.keySet()) {
		 	        m.setProperty(key, row.getString(key).toString());
		 	 }	
	         m.setProperty("MAXCREDITAMOUNT", row.getString("MAXCREDITAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXCREDITAMOUNT").toString())));		         
	         
	         DataRow mgr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ? AND USERSLEVEL IN ('MANAGER','BUSINESSOWNER')", row.getString("ACCOUNTNUMBER").toString());
	         m.setProperty("MANAGERFIRSTNAME", mgr.getString("FIRSTNAME").toString());
	         m.setProperty("MANAGERMIDDLENAME", mgr.getString("MIDDLENAME").toString());
	         m.setProperty("MANAGERLASTNAME", mgr.getString("LASTNAME").toString());
	         m.setProperty("MANAGERMSISDN", mgr.getString("MSISDN").toString());
	         m.setProperty("MANAGEREMAIL", mgr.getString("EMAIL").toString());
	         m.setProperty("MANAGERUSERNAME", mgr.getString("USERNAME").toString());
	         add(m);
    	 }
     }
     return r.size() > 0;
}
	public boolean hasRowsPndgUpgradeDoc() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLDOCUMENTS WHERE ACCOUNTNUMBER = ? AND ID = ?",this.accountnumber,this.id);
     
     if (!r.isEmpty())
     {
    	 for(DataRow row: r){
	         ReportItem m = new ReportItem();
	         
	         for (String key : row.keySet()) {
		 	        m.setProperty(key, row.getString(key).toString());
		 	 }	
	         add(m);
    	 }
     }
     return r.size() > 0;
}
	public boolean hasRowsPndgUpgradeDocNoimage() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT ID,ACCOUNTNUMBER,DOCTYPE,DATEREGISTERED FROM TBLDOCUMENTS WHERE ACCOUNTNUMBER = ?",this.accountnumber);
     
     if (!r.isEmpty())
     {
    	 for(DataRow row: r){
	         ReportItem m = new ReportItem();
	         
	         for (String key : row.keySet()) {
		 	        m.setProperty(key, row.getString(key).toString());
		 	 }	
	         add(m);
    	 }
     }
     return r.size() > 0;
}
	public boolean hasPendingRows() {
		if(this.accountnumber.equals("ALL")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT AI.*,CREATEDBY,REQUESTS,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) ALIAS,M.TIMESTAMP,M.MODIFIEDBY,M.DATEMODIFIED,M.REMARKS,M.DATEUPDATED,M.UPDATEDBY,M.PREDATEMODIFIED,M.PREMODIFIEDBY,M.STATUS STATUSPNDG FROM ADMDBMC.TBLACCOUNTINFOPNDG AI INNER JOIN TBLMERCHANTPNDG M ON AI.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE M.STATUS IN ('PNDG','PRE-APPROVED','FOR ESCALATION') AND M.ACCOUNTTYPE = 'BRANCH' ORDER BY REGDATE DESC",SystemInfo.getDb().getCrypt());
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         for (String key : row.keySet()) {
				 	        m.setProperty(key, row.getString(key).toString());
				 	 }		        
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT AI.*,CREATEDBY,REQUESTS,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) ALIAS,M.TIMESTAMP,M.MODIFIEDBY,M.DATEMODIFIED,M.REMARKS,M.DATEUPDATED,M.UPDATEDBY,M.PREDATEMODIFIED,M.PREMODIFIEDBY,M.STATUS STATUSPNDG FROM ADMDBMC.TBLACCOUNTINFOPNDG AI INNER JOIN TBLMERCHANTPNDG M ON AI.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE M.STATUS IN ('PNDG','PRE-APPROVED','FOR ESCALATION') AND M.ACCOUNTTYPE = 'BRANCH' AND REQUESTS LIKE ? ORDER BY REGDATE DESC",SystemInfo.getDb().getCrypt(),"%"+this.accountnumber+"%");
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         for (String key : row.keySet()) {
				 	        m.setProperty(key, row.getString(key).toString());
				 	 }		        
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}
	} 
	public boolean hasPendingMercRows() {

			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT B.*,AI.ACCOUNTNUMBER ACCOUNTNUMBERONE FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFOPNDG AI ON B.CONTACTNUMBER = AI.MSISDN AND B.BRANCH = DECRYPT(FQN,?,AI.ACCOUNTNUMBER) WHERE B.STATUS = 3 AND B.ACCOUNTNUMBER IS NULL "
																	+"UNION ALL "
																	+"SELECT B.*,B.ACCOUNTNUMBER ACCOUNTNUMBERONE FROM TBLBRANCHES B WHERE B.STATUS = 3 AND B.ACCOUNTNUMBER IS NOT NULL ",SystemInfo.getDb().getCrypt());
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		        	 m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
		        	 m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBERONE") == null ? "" : row.getString("ACCOUNTNUMBERONE").toString());
		        	 m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
			         m.setProperty("ContactNumber", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
			         m.setProperty("Location", row.getString("ADDRESS") == null ? "" : row.getString("ADDRESS").toString());
			         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
			        // m.setProperty("StoreHours", row.getString("STOREHOURS") == null ? "" : row.getString("STOREHOURS").toString());
			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
			         m.setProperty("XOrdinate", row.getString("XORDINATES") == null ? "" : row.getString("XORDINATES").toString());
			         m.setProperty("YOrdinate", row.getString("YORDINATES") == null ? "" : row.getString("YORDINATES").toString());
			         m.setProperty("BranchCode", row.getString("BRANCHCODE") == null ? "" : row.getString("BRANCHCODE").toString());
			         m.setProperty("Monday", row.getString("MONDAY") == null ? "" : row.getString("MONDAY").toString());
			         m.setProperty("Tuesday", row.getString("TUESDAY") == null ? "" : row.getString("TUESDAY").toString());
			         m.setProperty("Wednesday", row.getString("WEDNESDAY") == null ? "" : row.getString("WEDNESDAY").toString());
			         m.setProperty("Thursday", row.getString("THURSDAY") == null ? "" : row.getString("THURSDAY").toString());
			         m.setProperty("Friday", row.getString("FRIDAY") == null ? "" : row.getString("FRIDAY").toString());
			         m.setProperty("Saturday", row.getString("SATURDAY") == null ? "" : row.getString("SATURDAY").toString());
			         m.setProperty("Sunday", row.getString("SUNDAY") == null ? "" : row.getString("SUNDAY").toString());
			         m.setProperty("ZipCode", row.getString("ZIPCODE") == null ? "" : row.getString("ZIPCODE").toString());
		        	 m.setProperty("FileLocation", row.getString("RAFILELOCATION") == null ? "" : row.getString("RAFILELOCATION").toString());
		        	 m.setProperty("FileName", row.getString("RAFILENAME") == null ? "" : row.getString("RAFILENAME").toString());
		        	 m.setProperty("Tin", row.getString("TIN") == null ? "" : row.getString("TIN").toString());
		        	 m.setProperty("NatureOfBusiness", row.getString("NATUREOFBUSINESS") == null ? "" : row.getString("NATUREOFBUSINESS").toString());
		        	 m.setProperty("GrossSales", row.getString("GROSSSALES") == null ? "" : row.getString("GROSSSALES").toString());
		        	 m.setProperty("BankName", row.getString("BANKNAME") == null ? "" : row.getString("BANKNAME").toString());
		        	 m.setProperty("BankType", row.getString("BANKTYPE") == null ? "" : row.getString("BANKTYPE").toString());
		        	 m.setProperty("BankNumber", row.getString("BANKACCOUNTNUMBER") == null ? "" : row.getString("BANKACCOUNTNUMBER").toString());
		        	 
		        	 add(m);
	    	 }
	     }
	    	 return r.size() > 0;

}
	
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
