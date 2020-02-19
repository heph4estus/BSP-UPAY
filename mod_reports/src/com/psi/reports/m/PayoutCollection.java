package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class PayoutCollection extends ModelCollection{

	protected String accountnumber;
	protected String accounttype;
	protected String datefrom;
	protected String dateto;
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSPAYOUT WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES) AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
		         m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean getPrepaidCol() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSPAYOUT WHERE ACCOUNTNUMBER=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.accountnumber,this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
		         m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

	
	public String getBranch() {
		return accountnumber;
	}
	public void setBranch(String branch) {
		this.accountnumber = branch;
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

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	
}
