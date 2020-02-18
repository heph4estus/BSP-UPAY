package com.psi.business.v2.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class MercServicesStatCollection extends ModelCollection{
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE KEYACCOUNT = ?", this.accountnumber);
		if(acct.isEmpty()){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM VWSERVICESAVAIL WHERE ACCOUNTNUMBER = ?",this.accountnumber);
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
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM VWSERVICESAVAIL WHERE ACCOUNTNUMBER = ?",acct.getString("ACCOUNTNUMBER").toString());
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
	
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
}
