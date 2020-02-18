package com.psi.dashboard.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class DailySalesRemittance extends ModelCollection{

	protected String accountnumber;
	@Override
	public boolean hasRows() {
		if (this.accountnumber.equals("ALL")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLDAILYREMITTANCEALL ORDER BY MONTH ASC");
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         
			         m.setProperty("amnt", LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("remdate", row.getString("MONTH").toString());

			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT NVL(SUM(T1.AMOUNT),0) AMOUNT,T1.FROUTLET,T2.MONTH FROM "
															  +" (SELECT NVL(SUM(DR.AMOUNT),0) AMOUNT ,DR.FROUTLET, DR.MONTH FROM TBLDAILYREMITTANCE DR RIGHT JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = DR.FROUTLET  WHERE DR.FROUTLET =? GROUP BY DR.FROUTLET,DR.MONTH ) T1 "
															  +" RIGHT JOIN "
															  +" (SELECT 0,'', MONTH FROM TBLDAILYREMITTANCEALL ) T2 "
															  +" ON T1.MONTH = T2.MONTH GROUP BY T1.FROUTLET,T2.MONTH ORDER BY T2.MONTH ASC",this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();

		        		 m.setProperty("amnt", LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
				         m.setProperty("remdate", row.getString("MONTH").toString());
				        
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
