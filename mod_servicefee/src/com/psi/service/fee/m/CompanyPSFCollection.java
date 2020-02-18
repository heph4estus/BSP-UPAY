package com.psi.service.fee.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class CompanyPSFCollection extends ModelCollection{
	protected String product;
	protected Long amount;
	protected String accountnumber;
	protected String moduleid;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT PF.* FROM TBLPRODUCTFEES PF INNER JOIN TBLTARIFFCONFIG TC ON TC.PLANID = PF.ID INNER JOIN TBLTARIFFGROUP TG ON TG.GROUPNAME = TC.TARIFFGROUP INNER JOIN TBLCOMPANYTARIFF CT ON CT.TARIFFGROUP = TG.ID WHERE PF.IBAYADCODE = ? AND  PF.MINAMOUNT <= ? AND PF.MAXAMOUNT >=? AND CT.ACCOUNTNUMBER = ? AND PF.MODULEID = ?",this.product,this.amount,this.amount,this.accountnumber,this.moduleid);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("MAXAMOUNT", row.getString("MAXAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT").toString())));
	    		 m.setProperty("MINAMOUNT", row.getString("MINAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT").toString())));
	    		 m.setProperty("FIXED", row.getString("FIXED") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("FIXED").toString())));
	    		 //m.setProperty("PERCENTAGE", row.getString("PERCENTAGE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("PERCENTAGE").toString())));
	    		 
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

}
