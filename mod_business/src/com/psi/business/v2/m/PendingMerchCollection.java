package com.psi.business.v2.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class PendingMerchCollection extends ModelCollection{
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT AI.*,CREATEDBY,REQUESTS,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) ALIAS,M.TIMESTAMP,M.MODIFIEDBY,M.DATEMODIFIED,M.REMARKS,M.DATEUPDATED,M.UPDATEDBY,M.PREDATEMODIFIED,M.PREMODIFIEDBY,M.STATUS STATUSPNDG FROM ADMDBMC.TBLACCOUNTINFOPNDG AI INNER JOIN TBLMERCHANTPNDG M ON AI.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE M.STATUS IN ('PNDG','PRE-APPROVED','FOR ESCALATION') AND M.ACCOUNTTYPE IS NULL ORDER BY REGDATE DESC",SystemInfo.getDb().getCrypt());
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
	public boolean hasRowsrejected() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT AI.*,CREATEDBY,REQUESTS,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) ALIAS,M.TIMESTAMP,M.MODIFIEDBY,M.DATEMODIFIED,M.REMARKS,M.DATEUPDATED,M.UPDATEDBY,M.PREDATEMODIFIED,M.PREMODIFIEDBY,M.STATUS STATUSPNDG FROM ADMDBMC.TBLACCOUNTINFOPNDG AI INNER JOIN TBLMERCHANTPNDG M ON AI.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE M.STATUS = 'REJECTED' AND M.ACCOUNTTYPE IS NULL ORDER BY REGDATE DESC",SystemInfo.getDb().getCrypt());
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
	public boolean hasRowsSpecific() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT IMAGE FROM ADMDBMC.TBLACCOUNTINFOPNDG AI INNER JOIN TBLMERCHANTPNDG M ON AI.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE M.ACCOUNTNUMBER = ? ORDER BY REGDATE DESC",this.accountnumber);
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
}
