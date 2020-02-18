package com.psi.tariff.plans.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class LoyaltyCollection extends ModelCollection{

	protected String type;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLLOYALTYSETTINGS WHERE TYPE = ?",this.type);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("FOREVERY", row.getString("FOREVERY") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("FOREVERY").toString())));
	    		 m.setProperty("EARN", row.getString("EARN") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("EARN").toString())));
	    		
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
