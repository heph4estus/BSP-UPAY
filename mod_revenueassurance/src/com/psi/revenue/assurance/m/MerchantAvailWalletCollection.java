package com.psi.revenue.assurance.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class MerchantAvailWalletCollection extends ModelCollection{
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLMERCHANTAVAILWALLET ORDER BY BRANCH ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("AVAILABLEBALANCE", row.getString("AVAILABLEBALANCE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("AVAILABLEBALANCE").toString())));
	    		 m.setProperty("PAYMENTBALANCE", row.getString("PAYMENTBALANCE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("PAYMENTBALANCE").toString())));
		         
	    		 add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
}
