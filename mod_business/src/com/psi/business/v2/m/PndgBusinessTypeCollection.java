package com.psi.business.v2.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class PndgBusinessTypeCollection extends ModelCollection{
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT AI.*,CREATEDBY,REQUESTS,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) ALIAS FROM ADMDBMC.TBLACCOUNTINFOPNDG AI INNER JOIN TBLMERCHANTPNDG M ON AI.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE M.STATUS = 'PNDG' AND M.ACCOUNTTYPE IS NULL ORDER BY REGDATE DESC",SystemInfo.getDb().getCrypt());
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
