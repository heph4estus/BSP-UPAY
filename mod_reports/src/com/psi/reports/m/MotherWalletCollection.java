package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class MotherWalletCollection extends ModelCollection{
	protected String datefrom;
	protected String dateto;
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLMOTHERWALLETHISTORY WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ? ",this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("BALANCEBEFORE", row.getString("BALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORE").toString())));
		         m.setProperty("BALANCEAFTER", row.getString("BALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
		         m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
			     m.setProperty("CHARGES", row.getString("CHARGES") == null || row.getString("CHARGES") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
		         add(m);
	    	 }
	    	 
	    	 
	     }
	     return r.size() > 0;
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

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
	

}
