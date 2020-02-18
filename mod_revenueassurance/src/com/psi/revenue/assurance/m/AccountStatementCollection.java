package com.psi.revenue.assurance.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AccountStatementCollection extends ModelCollection {
	protected String accountnumber;
	protected String datefrom;
	protected String dateto;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLACCOUNTSTATEMENT WHERE ACCOUNTNUMBER=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ? ORDER BY TIMESTAMP ASC",this.accountnumber,this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("CREDIT", row.getString("CREDIT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("CREDIT").toString())));
	    		 m.setProperty("DEBIT", row.getString("DEBIT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("DEBIT").toString())));
	    		 m.setProperty("BALANCEBEFORE", row.getString("BALANCEBEFORE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORE").toString())));
	    		 m.setProperty("BALANCEAFTER", row.getString("BALANCEAFTER") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
		         
		         
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
	public String getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}
	public String getDateto() {
		return dateto;
	}
	public void setDateto(String dateto) {
		this.dateto = dateto;
	}
	
}
