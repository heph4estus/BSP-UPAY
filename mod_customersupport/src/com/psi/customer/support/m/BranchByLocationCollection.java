package com.psi.customer.support.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class BranchByLocationCollection extends ModelCollection{
	protected String region;
	protected String city;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT BRANCH,ACCOUNTNUMBER FROM TBLBRANCHES WHERE UPPER(PROVINCE)=? AND UPPER(CITY)=?",this.region,this.city);
	     
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
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
				if (f.get(this) != null && !f.getName().equalsIgnoreCase("servicescashout") && !f.getName().equalsIgnoreCase("servicesabscbn") && !f.getName().equalsIgnoreCase("serviceskapamilya") && !f.getName().equalsIgnoreCase("servicescashin") && !f.getName().equalsIgnoreCase("commissionbill") && !f.getName().equalsIgnoreCase("commissionairt") && !f.getName().equalsIgnoreCase("servicesbill") && !f.getName().equalsIgnoreCase("servicesairt") && !f.getName().equalsIgnoreCase("tariff") && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
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

				if (f.get(this) != null && !f.getName().equalsIgnoreCase("servicescashout") && !f.getName().equalsIgnoreCase("servicesabscbn") && !f.getName().equalsIgnoreCase("serviceskapamilya") && !f.getName().equalsIgnoreCase("servicescashin") && !f.getName().equalsIgnoreCase("commissionbill") && !f.getName().equalsIgnoreCase("commissionairt") && !f.getName().equalsIgnoreCase("servicesbill") && !f.getName().equalsIgnoreCase("servicesairt") && !f.getName().equalsIgnoreCase("tariff") && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
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
