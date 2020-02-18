package com.psi.commission.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class SearchCommissionCollection extends ModelCollection{

	protected String accountnumber;
	protected String type;

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLCOMMISSION WHERE ACCOUNTNUMBER = ? AND TYPE = ? ORDER BY TIMESTAMP DESC",this.accountnumber,this.type);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }	
	    		 m.setProperty("FIXED", row.getString("FIXED") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("FIXED").toString())));		         
	    		 m.setProperty("PERCENT", row.getString("PERCENT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("PERCENT").toString())));
		         
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
