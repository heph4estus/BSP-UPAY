package com.psi.reports.m;

import org.json.simple.JSONArray;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class ReportBranchCollection extends ModelCollection {
	
protected String accountnumber;
	@Override
	public boolean hasRows() {		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER,BRANCH FROM TBLBRANCHES WHERE KEYACCOUNT = ? ORDER BY BRANCH ASC",this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("BRANCHCODE", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BRANCH", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean hasRowsAll() {		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT B.ACCOUNTNUMBER,BRANCH FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON B.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE TYPE <> ENCRYPT('subscriber',?,AI.ACCOUNTNUMBER) ORDER BY BRANCH ASC",SystemInfo.getDb().getCrypt());
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("BRANCHCODE", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BRANCH", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
}
