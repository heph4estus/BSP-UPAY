package com.psi.revenue.assurance.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class PartnerTransactionSummaryCollection extends ModelCollection {
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLPROVIDERTRANSSUMMARY ORDER BY PROVIDERNAME ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("SUCCESSTRANSAMOUNT", row.getString("SUCCTRANSACTIONAMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("SUCCTRANSACTIONAMOUNT").toString())));
	    		 m.setProperty("FAILEDTRANSAMOUNT", row.getString("FAILEDTRANSACTIONAMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("FAILEDTRANSACTIONAMOUNT").toString())));
	    		 m.setProperty("TOTALTRANSACTIONAMOUNT", row.getString("TOTALTRANSACTIONAMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("TOTALTRANSACTIONAMOUNT").toString())));
		         
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
}
