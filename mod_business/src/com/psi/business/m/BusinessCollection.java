package com.psi.business.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class BusinessCollection extends ModelCollection{
protected String channel;
	@Override
	public boolean hasRows() {
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM VWPARTNERMERCHANT WHERE REGTYPE = ? ORDER BY BUSINESS ASC",this.channel);
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();		         
		         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }	
		         m.setProperty("MAXCREDITAMOUNT", row.getString("MAXCREDITAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXCREDITAMOUNT").toString())));		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
