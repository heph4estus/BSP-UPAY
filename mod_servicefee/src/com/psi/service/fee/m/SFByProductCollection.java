package com.psi.service.fee.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class SFByProductCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLPRODUCTFEES ORDER BY ID DESC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("MAXAMOUNT", row.getString("MAXAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT").toString())));
	    		 m.setProperty("MINAMOUNT", row.getString("MINAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT").toString())));
	    		 m.setProperty("FIXED", row.getString("FIXED") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("FIXED").toString())));
	    		// m.setProperty("PERCENTAGE", row.getString("PERCENTAGE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("PERCENTAGE").toString())));
	    		 
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

}
