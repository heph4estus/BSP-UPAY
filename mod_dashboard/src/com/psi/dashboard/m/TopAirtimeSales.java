package com.psi.dashboard.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class TopAirtimeSales extends ModelCollection{

	protected String accountnumber;
	@Override
	public boolean hasRows() {
		if (this.accountnumber.equals("ALL")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTOPPREPAIDSALES ORDER BY AMOUNT DESC");
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         
			         m.setProperty("telco", row.getString("PRODUCT").toString());
			         m.setProperty("amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));

			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTOPPREPAIDSALES WHERE ACCOUNTNUMBER = ? ORDER BY AMOUNT DESC",this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		         m.setProperty("telco", row.getString("PRODUCT").toString());
		         m.setProperty("amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));

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
