package com.psi.backoffice.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class PndgUserCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERSSTATUSPNDG");
		if (!rows.isEmpty())
	     {
	    	 for(DataRow row: rows){	
		         ReportItem m = new ReportItem();
		         for (String key : row.keySet()) {
		 	        m.setProperty(key, row.getString(key).toString());
		 	      }
		 	      add(m);
	    	 }
	     }
	     return rows.size() > 0;

	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
		
		for(Field f : fields) {
			try {
				f.setAccessible(true);
				if(f.get(this) != null && !f.getName().equalsIgnoreCase("AUDITDATA") && !f.getName().equalsIgnoreCase("PASSWORD") && !f.getName().equalsIgnoreCase("SERIALVERSIONUID") && !f.getName().equalsIgnoreCase("AUTHORIZEDSESSION"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}
			
		}
		Field[] fieldss = this.getClass().getDeclaredFields();
		for(Field f : fieldss) {
			f.setAccessible(true);
			try {
				if(f.get(this) != null && !f.getName().equalsIgnoreCase("AUDITDATA") && !f.getName().equalsIgnoreCase("PASSWORD") && !f.getName().equalsIgnoreCase("SERIALVERSIONUID") && !f.getName().equalsIgnoreCase("AUTHORIZEDSESSION"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}
			
		}
		if(sb.length() > 0)
		sb.deleteCharAt(sb.lastIndexOf("|"));
		return sb.toString();
	}
}
