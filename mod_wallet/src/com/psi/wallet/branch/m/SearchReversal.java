package com.psi.wallet.branch.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class SearchReversal extends ModelCollection{
	protected String referenceid;
	@Override
	public boolean hasRows() {
		DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT T.*  FROM ADMDBMC.TBLTRANSACTIONS T WHERE T.MODULE IN (1007, 1001, 1201,1109)  AND (T.TOACCOUNT IN  (SELECT ACCOUNTNUMBER  FROM ADMDBMC.TBLACCOUNTINFO WHERE TYPE =ENCRYPT ('subscriber', ?,ACCOUNTNUMBER)) OR T.FRACCOUNT IN  (SELECT ACCOUNTNUMBER  FROM ADMDBMC.TBLACCOUNTINFO WHERE TYPE =ENCRYPT ('subscriber', ?,ACCOUNTNUMBER))) AND REFERENCEID NOT IN (SELECT VOIDREFERENCEID FROM TBLREVERSALTRANSACTIONS) AND T.REFERENCEID = ? ",SystemInfo.getDb().getCrypt(),SystemInfo.getDb().getCrypt(), this.referenceid);
		if (!rows.isEmpty())
	     {
	    	 for(DataRow row: rows){	
		         ReportItem m = new ReportItem();
		         for (String key : row.keySet()) {
		 	        m.setProperty(key, row.getString(key).toString());
		 	      }
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("SRCBALANCEBEFORE", row.getString("SRCBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE").toString())));
		         m.setProperty("SRCBALANCEAFTER", row.getString("SRCBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER").toString())));
		         m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
		         m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
		         
		         add(m);
	    	 }
	     }
	     return rows.size() > 0;
	}
	public String getReferenceid() {
		return referenceid;
	}
	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}

}
