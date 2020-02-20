package com.psi.tariff.config.m;

import java.lang.reflect.Field;

import com.psi.tariff.config.util.Tariffs;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class TariffPlansGroupCollection extends ModelCollection{

	private String tariffGroup;
	protected String type;
	
	public TariffPlansGroupCollection(String groupname) {
		this.tariffGroup = groupname;
	}
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTARIFFPLANS WHERE ID NOT IN (SELECT PLANID FROM TBLTARIFFCONFIG WHERE TARIFFGROUP=? AND TYPE=?)  AND TYPE = ? ORDER BY ID DESC",this.tariffGroup,this.type,this.type);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("MAXAMOUNT", row.getString("MAXAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT").toString())));
	    		 m.setProperty("MINAMOUNT", row.getString("MINAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT").toString())));
	    		 if(row.getString("TYPE").equals("FEE")){
		    			m.setProperty("MAXAMOUNTDAY", row.getString("MAXAMOUNTDAY").toString());
		    	}else{
		    			m.setProperty("MAXAMOUNTDAY", row.getString("MAXAMOUNTDAY") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTDAY").toString())));
		    	}
	    		 m.setProperty("MAXAMOUNTWEEK", row.getString("MAXAMOUNTWEEK") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTWEEK").toString())));
	    		 m.setProperty("MAXAMOUNTMONTH", row.getString("MAXAMOUNTMONTH") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTMONTH").toString())));
	    		 
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
		
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
