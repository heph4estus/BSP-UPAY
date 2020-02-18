package com.psi.business.v2.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class ProductServicesCollection extends ModelCollection{
	protected String service;
	protected String category;
	@Override
	public boolean hasRows() {
		DataRow rr;DataRowCollection r;
		if(!this.category.equals("ALL")){rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHPRODUCTS WHERE TYPE='PRODUCTMERC' AND SERVICE = ? AND CATEGORY IS NULL",this.service);}
		else{rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHPRODUCTS WHERE TYPE='PRODUCTMERC' AND SERVICE = ? AND CATEGORY = 'ALL'",this.service);}
		if(!this.category.equals("ALL")){r = SystemInfo.getDb().QueryDataRows(rr.getString("QUERY").toString(),this.category);}
		else{r = SystemInfo.getDb().QueryDataRows(rr.getString("QUERY").toString());}
	    
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
