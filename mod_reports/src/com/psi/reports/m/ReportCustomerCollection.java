package com.psi.reports.m;

import org.json.simple.JSONArray;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class ReportCustomerCollection extends ModelCollection {
	
protected String accountnumber;
	@Override
	public boolean hasRows() {		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER,DECRYPT(FQN,?,ACCOUNTNUMBER) FQN FROM ADMDBMC.TBLACCOUNTINFO WHERE STATUS='ACTIVE' AND ACCOUNTNUMBER = ? ORDER BY DECRYPT(FQN,?,ACCOUNTNUMBER) ASC",SystemInfo.getDb().getCrypt(),this.accountnumber,SystemInfo.getDb().getCrypt());
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("BRANCHCODE", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BRANCH", row.getString("FQN") == null ? "" : row.getString("FQN").toString());
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean hasRowsAll() {		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER,DECRYPT(FQN,?,ACCOUNTNUMBER) FQN FROM ADMDBMC.TBLACCOUNTINFO WHERE STATUS='ACTIVE' AND TYPE = ENCRYPT('subscriber',?,ACCOUNTNUMBER) ORDER BY DECRYPT(FQN,?,ACCOUNTNUMBER) ASC",SystemInfo.getDb().getCrypt(),SystemInfo.getDb().getCrypt(),SystemInfo.getDb().getCrypt());
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("BRANCHCODE", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("BRANCH", row.getString("FQN") == null ? "" : row.getString("FQN").toString());
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
