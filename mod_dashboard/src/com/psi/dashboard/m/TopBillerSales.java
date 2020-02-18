package com.psi.dashboard.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class TopBillerSales extends ModelCollection{

	protected String accountnumber;
	@Override
	public boolean hasRows() {
		if (this.accountnumber.equals("ALL")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTOPBILLERALL ORDER BY COUNT DESC");
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         
			         m.setProperty("biller", row.getString("BRAND").toString());
			         m.setProperty("count", row.getString("COUNT").toString());

			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTOPBILLER WHERE ACCOUNTNUMBER = ? ORDER BY COUNT DESC",this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		         m.setProperty("biller", row.getString("BRAND").toString());
		         m.setProperty("count", row.getString("COUNT").toString());

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
