package com.psi.tariff.group.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class MyGroupCollection extends ModelCollection{
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT DISTINCT TG.* FROM TBLCOMPANYTARIFF CT INNER JOIN TBLTARIFFGROUP TG ON CT.TARIFFGROUP = TG.ID WHERE ACCOUNTNUMBER = ? ORDER BY TG.ID DESC",this.accountnumber);
	     
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
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

}
