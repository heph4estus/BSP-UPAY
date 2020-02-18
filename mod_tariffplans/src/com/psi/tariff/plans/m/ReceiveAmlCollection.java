package com.psi.tariff.plans.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class ReceiveAmlCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLAMLACCOUNTTYPERECEIVE ");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("MAXAMOUNT", row.getString("MAXAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT").toString())));
	    		 m.setProperty("MINAMOUNT", row.getString("MINAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT").toString())));
	    		 m.setProperty("MAXAMOUNTDAY", row.getString("MAXAMOUNTDAY") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTDAY").toString())));
	    		 m.setProperty("MAXAMOUNTMONTH", row.getString("MAXAMOUNTMONTH") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTMONTH").toString())));
	    		 m.setProperty("MAXAMOUNTWEEK", row.getString("MAXAMOUNTWEEK") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTWEEK").toString())));
	    		 
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

}
