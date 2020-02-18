package com.psi.aml.settings.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class CashierAmlSendCollection extends ModelCollection{

	protected String accountnumber;
	
	@Override
	public boolean hasRows() {
		DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLAMLACCOUNTTYPESEND WHERE ACCOUNTNUMBER = ?",this.accountnumber);
		if (!rows.isEmpty())
	     {
	    	 for(DataRow row: rows){	
		         ReportItem m = new ReportItem();
		         m.setProperty("ID", row.getString("ID").toString());
		        	m.setProperty("ACCOUNTNUMBER", row.getString("ACCOUNTNUMBER").toString());
		        	m.setProperty("KEY", row.getString("KEY").toString());
		 	        m.setProperty("USERSLEVEL", row.getString("USERSLEVEL").toString());
		 	        m.setProperty("MAXAMOUNT", LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT"))));
		 	        m.setProperty("MINAMOUNT", LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT"))));
		 	        m.setProperty("MAXAMOUNTDAY", LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTDAY"))));
		 	        m.setProperty("MAXAMOUNTWEEK", LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTWEEK"))));
		 	        m.setProperty("MAXAMOUNTMONTH", LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTMONTH"))));
		 	        m.setProperty("MAXTRANSDAY", row.getString("MAXTRANSDAY").toString());
		 	        m.setProperty("MAXTRANSWEEK", row.getString("MAXTRANSWEEK").toString());
		 	        m.setProperty("MAXTRANSMONTH", row.getString("MAXTRANSMONTH").toString());
		 	      add(m);
	    	 }
	     }
	     return rows.size() > 0;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

}
