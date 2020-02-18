package com.psi.revenue.assurance.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;
import com.tlc.gui.modules.session.UISession;

@SuppressWarnings("serial")
public class MerchantRegCollection extends ModelCollection {
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLMERCHANTREGISTRATION ORDER BY BRANCH ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
}
