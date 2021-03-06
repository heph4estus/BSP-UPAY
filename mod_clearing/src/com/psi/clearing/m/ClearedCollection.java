package com.psi.clearing.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class ClearedCollection extends ModelCollection{

	protected String accountnumber;
	protected String company;
	protected String datefrom;
	protected String dateto;
	@Override
	public boolean hasRows() {
		 	 DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT C.*, SRCBALANCEBEFORE, SRCBALANCEAFTER, DESTBALANCEBEFORE, DESTBALANCEAFTER FROM ADMDBMC.TBLTRANSACTIONSCLEARED C INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON C.REFERENCEID=T.REFERENCEID WHERE ACCOUNTNUMBER=? AND TO_CHAR(DATECLEARED,'YYYY-MM-DD') BETWEEN ? AND ?", new Object[] { this.accountnumber, this.datefrom, this.dateto });
				
		 
		 if (!r.isEmpty()) {
		      for (DataRow row : r)
		      {
		        ReportItem m = new ReportItem();
		        for (String key : row.keySet()) {
	    	          m.setProperty(key, row.getString(key).toString());
	    	     }
		        m.setProperty("AMOUNT", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))));
		        m.setProperty("SRCBALANCEBEFORE", LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE"))));
		        m.setProperty("SRCBALANCEAFTER", LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER"))));
		        m.setProperty("DESTBALANCEBEFORE", LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE"))));
		        m.setProperty("DESTBALANCEAFTER", LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER"))));
		             
		        add(m);
		      }
		    }
		    return r.size() > 0;
	}
	public boolean hasRowsCompany() {
	 	 DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT C.*, SRCBALANCEBEFORE, SRCBALANCEAFTER, DESTBALANCEBEFORE, DESTBALANCEAFTER FROM ADMDBMC.TBLTRANSACTIONSCLEARED C INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON C.REFERENCEID=T.REFERENCEID WHERE ACCOUNTNUMBER=? AND TO_CHAR(DATECLEARED,'YYYY-MM-DD') BETWEEN ? AND ?", new Object[] { this.company, this.datefrom, this.dateto });
			
	 
	 if (!r.isEmpty()) {
	      for (DataRow row : r)
	      {
	        ReportItem m = new ReportItem();
	        for (String key : row.keySet()) {
   	          m.setProperty(key, row.getString(key).toString());
   	     }
	        m.setProperty("AMOUNT", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))));
	        m.setProperty("SRCBALANCEBEFORE", LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE"))));
	        m.setProperty("SRCBALANCEAFTER", LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER"))));
	        m.setProperty("DESTBALANCEBEFORE", LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE"))));
	        m.setProperty("DESTBALANCEAFTER", LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER"))));
	             
	        add(m);
	      }
	    }
	    return r.size() > 0;
}
	public boolean all() {
	 	 DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT C.*, SRCBALANCEBEFORE, SRCBALANCEAFTER, DESTBALANCEBEFORE, DESTBALANCEAFTER FROM ADMDBMC.TBLTRANSACTIONSCLEARED C INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON C.REFERENCEID=T.REFERENCEID INNER JOIN  TBLBRANCHES B ON C.ACCOUNTNUMBER=B.ACCOUNTNUMBER WHERE TO_CHAR(DATECLEARED,'YYYY-MM-DD') BETWEEN ? AND ?", new Object[] { this.datefrom, this.dateto });
			
	 
	 if (!r.isEmpty()) {
	      for (DataRow row : r)
	      {
	        ReportItem m = new ReportItem();
	        for (String key : row.keySet()) {
   	          m.setProperty(key, row.getString(key).toString());
   	     }
	        m.setProperty("AMOUNT", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))));
	        m.setProperty("SRCBALANCEBEFORE", LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE"))));
	        m.setProperty("SRCBALANCEAFTER", LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER"))));
	        m.setProperty("DESTBALANCEBEFORE", LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE"))));
	        m.setProperty("DESTBALANCEAFTER", LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER"))));     
	        add(m);
	      }
	    }
	    return r.size() > 0;
}

	public String getAccountnumber(){
		return this.accountnumber;
	}
	
	public void setAccountnumber(String accntno){
		this.accountnumber = accntno;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
	
}
