package com.psi.clearing.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class TransactionCollection extends ModelCollection{

	protected String accountnumber;
	@Override
	public boolean hasRows() {
		
		 //DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSPOS TP INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON TP.REFERENCEID=T.REFERENCEID WHERE BRAND IN (SELECT ACCOUNTNUMBER FROM TBLCOMPANYTARIFF CT INNER JOIN TBLTARIFFGROUP TG ON CT.TARIFFGROUP=TG.ID WHERE GROUPNAME='shops') AND TOACCOUNT=? AND T.REFERENCEID IN (SELECT REFERENCEID FROM ADMDBMC.TBLVERIFICATIONS) ORDER BY T.TIMESTAMP DESC", new Object[] { this.accountnumber });
			
		 DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSPOS TP INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON TP.REFERENCEID=T.REFERENCEID WHERE ACCOUNTNUMBER=?  AND T.REFERENCEID IN (SELECT REFERENCEID FROM ADMDBMC.TBLVERIFICATIONS) ORDER BY T.TIMESTAMP DESC", new Object[] {  this.accountnumber });
		 if (!r.isEmpty()) {
		      for (DataRow row : r)
		      {
		        ReportItem m = new ReportItem();
		        m.setProperty("ReferenceId", row.getString("REFERENCEID") == null ? "" : row.getString("REFERENCEID").toString());
		        m.setProperty("Amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))));
		        m.setProperty("FromAccount", row.getString("FRACCOUNT") == null ? "" : row.getString("FRACCOUNT").toString());
		        m.setProperty("ToAccount", row.getString("TOACCOUNT") == null ? "" : row.getString("TOACCOUNT").toString());
		        m.setProperty("FromAlias", row.getString("FRALIAS") == null ? "" : row.getString("FRALIAS").toString());
		        m.setProperty("ToAlias", row.getString("TOALIAS") == null ? "" : row.getString("TOALIAS").toString());
		        m.setProperty("Timestamp", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
		        m.setProperty("Cashier", row.getString("CASHIER") == null ? "" : row.getString("CASHIER").toString());
		        m.setProperty("Brand", row.getString("BRAND") == null ? "" : row.getString("BRAND").toString());
		        m.setProperty("Product", row.getString("PRODUCT") == null ? "" : row.getString("PRODUCT").toString());
		        m.setProperty("Dest", row.getString("DEST") == null ? "" : row.getString("DEST").toString());
		        m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
		        m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
		        m.setProperty("SRCBALANCEBEFORE", row.getString("SRCBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE").toString())));
		        m.setProperty("SRCBALANCEAFTER", row.getString("SRCBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER").toString())));
		       
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
}
