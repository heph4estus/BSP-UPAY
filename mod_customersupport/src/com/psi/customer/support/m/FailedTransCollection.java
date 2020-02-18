package com.psi.customer.support.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class FailedTransCollection extends ModelCollection{
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLFAILEDTRANSACTIONS ORDER BY TIMESTAMP DESC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}


}
