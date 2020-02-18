package com.psi.commission.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class BranchProductCollection extends ModelCollection{
	protected String service;
	protected String category;
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		DataRow rr;DataRowCollection r;
		if(!this.category.equals("ALL")){rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHPRODUCTS WHERE TYPE='PRODUCT' AND SERVICE = ? AND CATEGORY IS NULL",this.service);}
		else{rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHPRODUCTS WHERE TYPE='PRODUCT' AND SERVICE = ? AND CATEGORY = 'ALL'",this.service);}
		if(!this.category.equals("ALL")){r = SystemInfo.getDb().QueryDataRows(rr.getString("QUERY").toString(),this.accountnumber,this.category);}
		else{r = SystemInfo.getDb().QueryDataRows(rr.getString("QUERY").toString(),this.accountnumber);}
	    
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }	
	    		 m.setProperty("MINAMOUNT", row.getString("MINAMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT").toString())));		         
	    		 m.setProperty("MAXAMOUNT", row.getString("MAXAMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT").toString())));
		         
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
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
