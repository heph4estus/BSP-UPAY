package com.psi.tariff.plans.commission.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AvailComByTariffCollection extends ModelCollection{
	protected String service;
	protected String category;
	protected String provider;
	protected String tariff;
	@Override
	public boolean hasRows() {
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPROVIDERPRODUCTS WHERE TYPE='TARIFF' AND PROVIDER = ? AND CATEGORY = ? AND SERVICE = ?",this.provider,this.category,this.service);
		DataRowCollection r = SystemInfo.getDb().QueryDataRows(rr.getString("QUERY").toString(),this.tariff);
	    
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }	
	    		 m.setProperty("BUYINGPRICE", row.getString("BUYINGPRICE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("BUYINGPRICE").toString())));		         
	    		 m.setProperty("BASEPRICE", row.getString("BASEPRICE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("BASEPRICE").toString())));
	    		 m.setProperty("FIXED", row.getString("FIXED") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("FIXED").toString())));		         
	    		 m.setProperty("PERCENTAGE", row.getString("PERCENTAGE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("PERCENTAGE").toString())));
		         
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
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff;
	}
}
