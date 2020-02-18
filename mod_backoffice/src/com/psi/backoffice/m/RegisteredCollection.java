package com.psi.backoffice.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class RegisteredCollection extends ModelCollection{
	
	protected String accountnumber;
	
	@Override
	public boolean hasRows() {
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT DISTINCT U.*,UT.DEPARTMENT,UT.EMPLOYMENTSTATUS,UT.EMPLOYEENUMBER FROM TBLUSERS U LEFT JOIN TBLUSERSTITLE UT ON U.USERNAME = UT.USERID  WHERE ACCOUNTNUMBER =? AND USERSLEVEL IN ( SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL = 'operator')", this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){	
		         ReportItem m = new ReportItem();
		         m.setProperty("Id", row.getString("USERID") == null ? "" : row.getString("USERID").toString());
		         m.setProperty("Email", row.getString("EMAILADDRESS") == null ? "" : row.getString("EMAIL").toString());
		         m.setProperty("Msisdn", row.getString("MSISDN") == null ? "" : row.getString("MSISDN").toString());
		         m.setProperty("Firstname", row.getString("FIRSTNAME") == null ? "" : row.getString("FIRSTNAME").toString());
		         m.setProperty("Lastname", row.getString("LASTNAME") == null ? "" : row.getString("LASTNAME").toString());
		         m.setProperty("Username", row.getString("USERNAME") == null ? "" : row.getString("USERNAME").toString());
		         m.setProperty("UsersLevel", row.getString("USERSLEVEL") == null ? "" : row.getString("USERSLEVEL").toString());
		         m.setProperty("Department", row.getString("DEPARTMENT") == null ? "" : row.getString("DEPARTMENT").toString());
		         m.setProperty("EmploymentStatus", row.getString("EMPLOYMENTSTATUS") == null ? "" : row.getString("EMPLOYMENTSTATUS").toString());
		         m.setProperty("EmployeeNumber", row.getString("EMPLOYEENUMBER") == null ? "" : row.getString("EMPLOYEENUMBER").toString());
		         
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




	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
		
		for(Field f : fields) {
			try {
				f.setAccessible(true);
				if(f.get(this) != null && !f.getName().equalsIgnoreCase("PASSWORD") && !f.getName().equalsIgnoreCase("SERIALVERSIONUID") && !f.getName().equalsIgnoreCase("AUTHORIZEDSESSION"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}
			
		}
		Field[] fieldss = this.getClass().getDeclaredFields();
		for(Field f : fieldss) {
			f.setAccessible(true);
			try {
				if(f.get(this) != null && !f.getName().equalsIgnoreCase("PASSWORD") && !f.getName().equalsIgnoreCase("SERIALVERSIONUID") && !f.getName().equalsIgnoreCase("AUTHORIZEDSESSION"))
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
