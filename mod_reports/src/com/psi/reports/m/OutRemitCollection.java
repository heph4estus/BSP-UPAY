package com.psi.reports.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class OutRemitCollection extends ModelCollection {
	protected String accountnumber;
	protected String datefrom;
	protected String dateto;
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONS WHERE TYPE='OUT REMITTANCE' AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.datefrom,this.dateto);
	     
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
		         m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
			     m.setProperty("CHARGES", row.getString("CHARGES") == null || row.getString("CHARGES") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean getOutRemit() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONS WHERE TYPE='OUT REMITTANCE' AND FRACCOUNT=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.accountnumber,this.datefrom,this.dateto);
	     
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
		         m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
			     m.setProperty("CHARGES", row.getString("CHARGES") == null || row.getString("CHARGES") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
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
