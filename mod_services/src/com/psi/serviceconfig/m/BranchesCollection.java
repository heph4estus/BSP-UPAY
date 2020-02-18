package com.psi.serviceconfig.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class BranchesCollection extends ModelCollection{
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		if(!this.accountnumber.equals("834591471124")){
			DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT B.ID,CASE WHEN BRANCH IS NULL THEN DECRYPT(FQN,?,A.ACCOUNTNUMBER) ELSE BRANCH END BRANCH,B.ACCOUNTNUMBER,B.BRANCHCODE FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFO A ON B.ACCOUNTNUMBER = A.ACCOUNTNUMBER WHERE B.STATUS = 1 AND B.KEYACCOUNT = ? ",SystemInfo.getDb().getCrypt(),this.accountnumber);
			if (!rows.isEmpty())
		     {
		    	 for(DataRow row: rows){	
			         ReportItem m = new ReportItem();
			         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	      }
			 	      add(m);
		    	 }
		     }
		     return rows.size() > 0;
		}else{
			DataRowCollection rows = SystemInfo.getDb().QueryDataRows("B.ID,CASE WHEN BRANCH IS NULL THEN DECRYPT(FQN,?,A.ACCOUNTNUMBER) ELSE BRANCH END BRANCH,B.ACCOUNTNUMBER,B.BRANCHCODE FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFO A ON B.ACCOUNTNUMBER = A.ACCOUNTNUMBER WHERE B.NATUREOFBUSINESS IS NOT NULL AND B.STATUS = 1 AND B.KEYACCOUNT = ? ",SystemInfo.getDb().getCrypt(),this.accountnumber);
			if (!rows.isEmpty())
		     {
		    	 for(DataRow row: rows){	
			         ReportItem m = new ReportItem();
			         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	      }
			 	      add(m);
		    	 }
		     }
		     return rows.size() > 0;
		}
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

}
