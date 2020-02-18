package com.psi.revenue.assurance.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class MerchantSalesComCollection extends ModelCollection {
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLMERCHANTSALES ORDER BY BRANCH ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("TOTALCASHINAMOUNT", row.getString("TOTALCASHINAMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("TOTALCASHINAMOUNT").toString())));
	    		 m.setProperty("TOTALCASHOUTAMOUNT", row.getString("TOTALCASHOUTAMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("TOTALCASHOUTAMOUNT").toString())));
	    		 m.setProperty("AMOUNTCHARGES", row.getString("AMOUNTCHARGES") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("AMOUNTCHARGES").toString())));
	    		 m.setProperty("AMOUNTCOMMISSION", row.getString("AMOUNTCOMMISSION") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("AMOUNTCOMMISSION").toString())));
		         
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
}
