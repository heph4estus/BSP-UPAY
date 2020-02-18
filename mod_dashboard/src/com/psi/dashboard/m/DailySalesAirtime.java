package com.psi.dashboard.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class DailySalesAirtime extends ModelCollection{
protected String accountnumber;
	@Override
	public boolean hasRows() {
		if (this.accountnumber.equals("ALL")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLDAILYAIRTIMEALL ORDER BY MONTH ASC");
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         
			         m.setProperty("at_amnt", LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("at_date", row.getString("MONTH").toString());

			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT NVL(SUM(T1.AMOUNT),0) AMOUNT,T1.FRACCOUNT,T2.MONTH FROM "
															  +" (SELECT SUM(AMOUNT) AMOUNT,MONTH,FRACCOUNT FROM TBLDAILYAIRTIME DA RIGHT JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = DA.FRACCOUNT WHERE AI.ACCOUNTNUMBER = ? GROUP BY MONTH,FRACCOUNT  ) T1 "
															  +" RIGHT JOIN " 
															  +" (SELECT 0,'', MONTH FROM TBLDAILYAIRTIMEALL ) T2 "
															  +" ON T1.MONTH = T2.MONTH GROUP BY T1.FRACCOUNT,T2.MONTH ORDER BY T2.MONTH ASC",this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		        
			        	 m.setProperty("at_amnt", LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			        	 m.setProperty("at_date", row.getString("MONTH").toString());
			         
			         add(m);
		         
	    	 }
	     }
	     return r.size() > 0;
		}
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

}
