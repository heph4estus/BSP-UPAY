package com.psi.reports.m;

import org.json.simple.JSONArray;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class AllBranches extends ModelCollection {
	

	@Override
	public boolean hasRows() {
		
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER,BRANCH,CONTACTNUMBER FROM TBLBRANCHES ORDER BY BRANCH ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("BRANCHCODE", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BRANCH", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
		         m.setProperty("CONTACTNUMBER", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
}
