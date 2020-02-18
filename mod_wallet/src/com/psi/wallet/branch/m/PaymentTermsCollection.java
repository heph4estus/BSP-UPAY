package com.psi.wallet.branch.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class PaymentTermsCollection extends ModelCollection{
	protected String accountnumber;
	@Override
	public boolean hasRows() {

		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT MODEOFPAYMENT,PAYMENTTERMS,MAXCREDITAMOUNT FROM TBLMERCHANTDETAILS WHERE ACCOUNTNUMBER =?",this.accountnumber);	     
		     
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("MODEOFPAYMENT", row.getString("MODEOFPAYMENT") == null ? "" : row.getString("MODEOFPAYMENT"));
			         m.setProperty("PAYMENTTERMS", row.getString("PAYMENTTERMS") == null ? "" : row.getString("PAYMENTTERMS"));
			         m.setProperty("MAXCREDITAMOUNT", row.getString("MAXCREDITAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXCREDITAMOUNT").toString())));		         
			         
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
	

}
