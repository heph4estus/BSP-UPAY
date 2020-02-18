package com.psi.dashboard.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class EndOfDateTransaction extends ModelCollection{
protected String accountnumber;
	@Override
	public boolean hasRows() {
		if (this.accountnumber.equals("ALL")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLENDOFDAYTRANS");
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         
			         m.setProperty("Amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("BalanceBefore", LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORETWO").toString())));
			         m.setProperty("BalanceAfter", LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
			         m.setProperty("Timestamp", row.getString("TIMESTAMP").toString());
			         m.setProperty("Branch", row.getString("BRANCH").toString());

			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLENDOFDAYTRANS WHERE ACCOUNTNUMBER=?",this.accountnumber);
		     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
			         m.setProperty("Amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("BalanceBefore", LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORETWO").toString())));
			         m.setProperty("BalanceAfter", LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
			         m.setProperty("Timestamp", row.getString("TIMESTAMP").toString());
			         m.setProperty("Branch", row.getString("BRANCH").toString());
			         
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
