package com.provider.management.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class ProviderProductCollection extends ModelCollection{
	protected String service;
	protected String category;
	protected String provider;
	@Override
	public boolean hasRows() {
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPROVIDERPRODUCTS WHERE TYPE='PRODUCT' AND PROVIDER = ? AND CATEGORY = ? AND SERVICE = ?",this.provider,this.category,this.service);
		DataRowCollection r = SystemInfo.getDb().QueryDataRows(rr.getString("QUERY").toString());
	    
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
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
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
