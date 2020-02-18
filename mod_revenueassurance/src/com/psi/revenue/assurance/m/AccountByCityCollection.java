package com.psi.revenue.assurance.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AccountByCityCollection extends ModelCollection {
	protected String city;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT BRANCH,B.CONTACTNUMBER,CASE WHEN B.STATUS = 2 THEN 'ACTIVE' WHEN B.STATUS = 3 THEN 'PENDING' WHEN B.STATUS = 0 THEN 'INACTIVE' ELSE 'REJECTED' END STATUS,B.CITY,B.PROVINCE,B.COUNTRY,B.LOCATION FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE AI.ROOT = 2 AND UPPER(B.CITY) =? ORDER BY TYPE ASC",this.city);
	     
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
	
	public boolean hasRowsByCom() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT BUSINESS,B.CONTACTNUMBER,CASE WHEN B.STATUS = 2 THEN 'ACTIVE' WHEN B.STATUS = 3 THEN 'PENDING' WHEN B.STATUS = 0 THEN 'INACTIVE' ELSE 'REJECTED' END STATUS,B.CITY,B.PROVINCE,B.COUNTRY,B.LOCATION FROM TBLBUSINESS B INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE AI.ROOT = 2 AND UPPER(B.CITY) =? ORDER BY TYPE ASC",this.city);
	     
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
