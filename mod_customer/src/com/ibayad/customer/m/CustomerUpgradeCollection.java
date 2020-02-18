package com.ibayad.customer.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class CustomerUpgradeCollection extends ModelCollection{
protected String accountnumber;
protected String id;
	@Override
	public boolean hasRows() {
		if(this.accountnumber.equals("ALL")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows(" SELECT U.*,TO_CHAR(DATEOFBIRTH,'YYYY-MM-DD') DOB,UC.SOURCEOFFUNDS,UC.NATUREOFWORK,UC.CREDITLIMIT,UC.MODIFIEDBY UPGRADEMODIFIEDBY,UC.MODIFIEDDATE UPGRADEMODIFIEDDATE,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) FQN,UC.STATUS UPGRADESTATUS,TIMESTAMP,MODIFIEDBYVO,MODIFIEDDATEVO FROM TBLUSERS U  INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER LEFT JOIN  TBLUPGRADEDCUSTOMER UC ON UC.ACCOUNTNUMBER = UC.ACCOUNTNUMBER WHERE U.USERSLEVEL = 'CUSTOMER'",SystemInfo.getDb().getCrypt());
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }	
		         m.setProperty("CREDITLIMIT", row.getString("CREDITLIMIT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("CREDITLIMIT").toString())));		         
		     	 String balance = SystemInfo.getDb().QueryScalar("SELECT DECRYPT(AMOUNT,?,ACCOUNTNUMBER) AMOUNT FROM ADMDBMC.TBLCURRENTSTOCK WHERE ACCOUNTNUMBER =? ", "", SystemInfo.getDb().getCrypt(),row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BALANCE", balance == null ? "" : LongUtil.toString(Long.parseLong(balance)));		         
		             
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
		}else{
			DataRowCollection r = SystemInfo.getDb().QueryDataRows(" SELECT U.*,TO_CHAR(DATEOFBIRTH,'YYYY-MM-DD') DOB,UC.SOURCEOFFUNDS,UC.NATUREOFWORK,UC.CREDITLIMIT,UC.MODIFIEDBY UPGRADEMODIFIEDBY,UC.MODIFIEDDATE UPGRADEMODIFIEDDATE,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) FQN,UC.STATUS UPGRADESTATUS,TIMESTAMP,MODIFIEDBYVO,MODIFIEDDATEVO FROM TBLUSERS U  INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER LEFT JOIN  TBLUPGRADEDCUSTOMER UC ON UC.ACCOUNTNUMBER = UC.ACCOUNTNUMBER WHERE U.USERSLEVEL = 'CUSTOMER'",SystemInfo.getDb().getCrypt());

				if (!r.isEmpty())
				{
				for(DataRow row: r){
				ReportItem m = new ReportItem();
				
				for (String key : row.keySet()) {
				m.setProperty(key, row.getString(key).toString());
				}	
				 m.setProperty("CREDITLIMIT", row.getString("CREDITLIMIT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("CREDITLIMIT").toString())));		         
		     	 String balance = SystemInfo.getDb().QueryScalar("SELECT DECRYPT(AMOUNT,?,ACCOUNTNUMBER) AMOUNT FROM ADMDBMC.TBLCURRENTSTOCK WHERE ACCOUNTNUMBER =? ", "", SystemInfo.getDb().getCrypt(),row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BALANCE", balance == null ? "" : LongUtil.toString(Long.parseLong(balance)));			         
				
				add(m);
				}
				}
				return r.size() > 0;
		}
	}
	public boolean hasRowsPndgUpgrade() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT U.*,TO_CHAR(DATEOFBIRTH,'YYYY-MM-DD') DOB,UC.SOURCEOFFUNDS,UC.NATUREOFWORK,UC.CREDITLIMIT,UC.MODIFIEDBY UPGRADEMODIFIEDBY,UC.MODIFIEDDATE UPGRADEMODIFIEDDATE,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) FQN,UC.STATUS UPGRADESTATUS,TIMESTAMP,MODIFIEDBYVO,MODIFIEDDATEVO,UC.REMARKS,UC.UPGRADEINFO FROM TBLUSERS U INNER JOIN  TBLUPGRADEDCUSTOMER UC ON UC.ACCOUNTNUMBER = U.ACCOUNTNUMBER INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL = 'CUSTOMER' AND UC.STATUS IN ('PENDING','VO-APPROVED')",SystemInfo.getDb().getCrypt());
     
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
	public boolean hasRowsRejectUpgrade() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT U.*,TO_CHAR(DATEOFBIRTH,'YYYY-MM-DD') DOB,UC.SOURCEOFFUNDS,UC.NATUREOFWORK,UC.CREDITLIMIT,UC.MODIFIEDBY UPGRADEMODIFIEDBY,UC.MODIFIEDDATE UPGRADEMODIFIEDDATE,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) FQN,UC.STATUS UPGRADESTATUS,TIMESTAMP,MODIFIEDBYVO,MODIFIEDDATEVO,UC.REMARKS,UC.UPGRADEINFO FROM TBLUSERS U INNER JOIN  TBLUPGRADEDCUSTOMER UC ON UC.ACCOUNTNUMBER = U.ACCOUNTNUMBER INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL = 'CUSTOMER' AND UC.STATUS='REJECTED'",SystemInfo.getDb().getCrypt());
     
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
