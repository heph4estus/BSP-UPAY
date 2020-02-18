package com.psi.reports.m;

import org.json.simple.JSONArray;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class GetAllBillersCollection extends ModelCollection {
	

	@Override
	public boolean hasRows() {
		
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT VALUE FROM TBLSERVICES WHERE TYPE = 'BILLER' AND STATUS =0 ORDER BY VALUE ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("VALUE", row.getString("VALUE") == null ? "" : row.getString("VALUE").toString());
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
}
