package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class ReportCompanyCollection extends ModelCollection {
	

	@Override
	public boolean hasRows() {
		
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER,BUSINESS,CONTACTNUMBER FROM TBLBUSINESS ORDER BY BUSINESS ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("ACCOUNTUMBER", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BUSINESS", row.getString("BUSINESS") == null ? "" : row.getString("BUSINESS").toString());
		         m.setProperty("CONTACTNUMBER", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
}
