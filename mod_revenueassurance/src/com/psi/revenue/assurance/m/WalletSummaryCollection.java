package com.psi.revenue.assurance.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class WalletSummaryCollection extends ModelCollection {
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLMERCHANTWALLETSUMMARYV2");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
//	    		 for (String key : row.keySet()) {
//			 	        m.setProperty(key, row.getString(key).toString());
//			 	 }
	    		 m.setProperty("TYPE", row.getString("TYPE").toString());
	    		 if(!row.getString("TYPE").equals("DATE") && !row.getString("TYPE").equals("NOOFCOMPANIES")
	    				 && !row.getString("TYPE").equals("NOOFMERCHANTS") && !row.getString("TYPE").equals("NOOFSUCCESSFULTRANS")
	    				 && !row.getString("TYPE").equals("NOOFFAILEDTRANS") && !row.getString("TYPE").equals("CASHINTRANSACTION")
	    				 && !row.getString("TYPE").equals("CASHOUTTRANSACTION") && !row.getString("TYPE").equals("BILLSTRANSACTION")
	    				 && !row.getString("TYPE").equals("ECASHTRANSACTION") && !row.getString("TYPE").equals("AIRTIMETRANSACTION")){
	    			 m.setProperty("VALUE", row.getString("VALUE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("VALUE").toString())));

	    		 }else{
	    			 m.setProperty("VALUE", row.getString("VALUE"));
	    		 }
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
}
