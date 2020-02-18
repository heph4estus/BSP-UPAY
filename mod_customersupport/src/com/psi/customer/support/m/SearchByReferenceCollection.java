package com.psi.customer.support.m;

import org.json.simple.JSONObject;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;

@SuppressWarnings("serial")
public class SearchByReferenceCollection extends ModelCollection{
	protected String referenceid;
	@SuppressWarnings("unchecked")
	@Override
	public boolean hasRows() {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();

		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLCSHITS WHERE REFERENCEID=? OR TRACEID LIKE ?",this.referenceid,"%"+this.referenceid+"%");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItemv2 m = new ReportItemv2();
	    		 for (String key : row.keySet()) {
			 	        json.put(key, row.getString(key).toString());
			 	 }
		         m.setProperty("HITS", json);
		         add(m);
	    	}
	     }
	     
	     DataRowCollection rr = SystemInfo.getDb().QueryDataRows("SELECT TD.*,CASE WHEN TYPE=1001 THEN 'MERCHANT TOPUP' WHEN TYPE =1002 THEN 'AIRTIME RELOAD' WHEN TYPE IN (1003,1103) THEN 'BILLS PAYMENT' WHEN TYPE = 1102 THEN 'E-MONEY TOPUP' END TRANSACTIONTYPE FROM TBLCSTRANSACTIONDETAILS TD WHERE REFERENCEID=? OR TRACEID LIKE ?",this.referenceid,"%"+this.referenceid+"%");
	     
	     if (!rr.isEmpty())
	     {
	    	 for(DataRow row: rr){
	    		 ReportItemv2 m = new ReportItemv2();
	    		 for (String key : row.keySet()) {
			 	        json2.put(key, row.getString(key).toString());
			 	 }
	    		 json2.put("CREDIT", row.getString("CREDIT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("CREDIT").toString())));
	    		 json2.put("DEBIT", row.getString("DEBIT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DEBIT").toString())));
	    		 json2.put("BALANCEBEFORE", row.getString("BALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORE").toString())));
	    		 json2.put("BALANCEAFTER", row.getString("BALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
		         
		         m.setProperty("TRANSACTIONDETAILS", json2);
		         add(m);
	    	 }
	     }
	     
	     DataRowCollection rrr = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLCSPULL WHERE REFERENCEID=? OR TRACEID LIKE ?",this.referenceid,"%"+this.referenceid+"%");
	     
	     if (!rrr.isEmpty())
	     {
	    	 for(DataRow row: rrr){
	    		 ReportItemv2 m = new ReportItemv2();
	    		 for (String key : row.keySet()) {
			 	        json3.put(key, row.getString(key).toString());
			 	 }
		         m.setProperty("PULL", json3);
		         add(m);
	    	 }
	     }
	     return r.size() > 0 || rr.size()>0 || rrr.size()>0 ;
	}
	public String getReferenceid() {
		return referenceid;
	}
	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}


}
