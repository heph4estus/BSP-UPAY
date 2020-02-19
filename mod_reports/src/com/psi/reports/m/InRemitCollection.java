package com.psi.reports.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class InRemitCollection extends ModelCollection {
	protected String accountnumber;
	protected String datefrom;
	protected String dateto;
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSINREMIT WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("REFERENCEID", row.getString("REFERENCEID") == null ? "" : row.getString("REFERENCEID").toString());
		         m.setProperty("TIMESTAMP", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
		         m.setProperty("ORDERNUMBER", row.getString("ORDERNUMBER") == null ? "" : row.getString("ORDERNUMBER").toString());
		         m.setProperty("TRACKINGNUMBER", row.getString("TRACKINGNUMBER") == null ? "" : row.getString("TRACKINGNUMBER").toString());
		         m.setProperty("SOURCEACCOUNT", row.getString("SOURCEACCOUNT") == null ? "" : row.getString("SOURCEACCOUNT").toString());
		         m.setProperty("DESTINATIONACCOUNT", row.getString("DESTINATIONACCOUNT") == null ? "" : row.getString("DESTINATIONACCOUNT").toString());
		         m.setProperty("STATUS", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
		         m.setProperty("NAME", row.getString("NAME") == null ? "" : row.getString("NAME").toString());
		         m.setProperty("MODULE", row.getString("MODULE") == null ? "" : row.getString("MODULE").toString());
		         m.setProperty("TYPE", row.getString("TYPE") == null ? "" : row.getString("TYPE").toString());
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
		         m.setProperty("SOURCEBRANCH", row.getString("SOURCEBRANCH") == null ? "" : row.getString("SOURCEBRANCH").toString());
		         m.setProperty("SOURCEBRANCHCODE", row.getString("SOURCEBRANCHCODE") == null ? "" : row.getString("SOURCEBRANCHCODE").toString());
		         m.setProperty("DESTBRANCH", row.getString("DESTBRANCH") == null ? "" : row.getString("DESTBRANCH").toString());
		         m.setProperty("DESTBRANCHCODE", row.getString("DESTBRANCHCODE") == null ? "" : row.getString("DESTBRANCHCODE").toString());
		         m.setProperty("CASHIER", row.getString("CASHIER") == null ? "" : row.getString("CASHIER").toString());
		         m.setProperty("MESSAGE", row.getString("MESSAGE") == null ? "" : row.getString("MESSAGE").toString());
			     
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean getInRemitBranches() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSINREMIT WHERE SOURCEACCOUNT=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.accountnumber,this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("REFERENCEID", row.getString("REFERENCEID") == null ? "" : row.getString("REFERENCEID").toString());
		         m.setProperty("TIMESTAMP", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
		         m.setProperty("ORDERNUMBER", row.getString("ORDERNUMBER") == null ? "" : row.getString("ORDERNUMBER").toString());
		         m.setProperty("TRACKINGNUMBER", row.getString("TRACKINGNUMBER") == null ? "" : row.getString("TRACKINGNUMBER").toString());
		         m.setProperty("SOURCEACCOUNT", row.getString("SOURCEACCOUNT") == null ? "" : row.getString("SOURCEACCOUNT").toString());
		         m.setProperty("DESTINATIONACCOUNT", row.getString("DESTINATIONACCOUNT") == null ? "" : row.getString("DESTINATIONACCOUNT").toString());
		         m.setProperty("STATUS", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
		         m.setProperty("NAME", row.getString("NAME") == null ? "" : row.getString("NAME").toString());
		         m.setProperty("MODULE", row.getString("MODULE") == null ? "" : row.getString("MODULE").toString());
		         m.setProperty("TYPE", row.getString("TYPE") == null ? "" : row.getString("TYPE").toString());
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
		         m.setProperty("SOURCEBRANCH", row.getString("SOURCEBRANCH") == null ? "" : row.getString("SOURCEBRANCH").toString());
		         m.setProperty("SOURCEBRANCHCODE", row.getString("SOURCEBRANCHCODE") == null ? "" : row.getString("SOURCEBRANCHCODE").toString());
		         m.setProperty("DESTBRANCH", row.getString("DESTBRANCH") == null ? "" : row.getString("DESTBRANCH").toString());
		         m.setProperty("DESTBRANCHCODE", row.getString("DESTBRANCHCODE") == null ? "" : row.getString("DESTBRANCHCODE").toString());
		         m.setProperty("CASHIER", row.getString("CASHIER") == null ? "" : row.getString("CASHIER").toString());
		         m.setProperty("MESSAGE", row.getString("MESSAGE") == null ? "" : row.getString("MESSAGE").toString());
			     
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
