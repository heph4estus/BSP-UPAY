package com.psi.business.v2.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class PndgBusinessTypeCollection extends ModelCollection{
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT AI.*,CREATEDBY,REQUESTS,DECRYPT(FQN,?,AI.ACCOUNTNUMBER) ALIAS FROM ADMDBMC.TBLACCOUNTINFOPNDG AI INNER JOIN TBLMERCHANTPNDG M ON AI.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE M.STATUS = 'PNDG' AND M.ACCOUNTTYPE IS NULL ORDER BY REGDATE DESC",SystemInfo.getDb().getCrypt());
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }		        
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public boolean hasRowsSpecific() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT IMAGE FROM ADMDBMC.TBLACCOUNTINFOPNDG AI INNER JOIN TBLMERCHANTPNDG M ON AI.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE M.ACCOUNTNUMBER = ? ORDER BY REGDATE DESC",this.accountnumber);
     if (!r.isEmpty())
     {
    	 for(DataRow row: r){
	         ReportItem m = new ReportItem();
	         for (String key : row.keySet()) {
		 	        m.setProperty(key, row.getString(key).toString());
		 	 }		        
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
	
	/**
	 * MVO 18-02-2020
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();

		/*
		 * Super Class fields
		 */
		for (Field f : superFields) {
			try {
				f.setAccessible(true);
				if (f.get(this) != null && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}

		}
		/*
		 * Class fields
		 */
		Field[] classFields = this.getClass().getDeclaredFields();
		for (Field f : classFields) {
			f.setAccessible(true);
			try {

				if (f.get(this) != null && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}

		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.lastIndexOf("|"));
		return sb.toString();
	}
}
