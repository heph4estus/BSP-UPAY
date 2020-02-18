package com.psi.dashboard.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class PartnersCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLPARTNERS ORDER BY PARTNER ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		         m.setProperty("company", row.getString("PARTNER").toString());
		         m.setProperty("branches", row.getString("COUNT").toString());

		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

}
