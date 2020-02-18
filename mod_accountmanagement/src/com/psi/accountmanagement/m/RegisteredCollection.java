package com.psi.accountmanagement.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.DbWrapper;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class RegisteredCollection extends ModelCollection{
	
	protected String id;
	protected String userslevel;
	

	@Override
	public boolean hasRows() {

		if(this.userslevel.equals("CUSTOMER")){
		     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT U.*,UT.DEPARTMENT,UT.EMPLOYMENTSTATUS,UT.EMPLOYEENUMBER,UPPER(US.PORTAL) PORTAL,UT.IMMEDIATEHEAD,REPLACE(EMAIL,'~','') EMAILADDRESS,REPLACE(U.TEMPEMAIL,'~','') TEMPEMAILADDRESS FROM TBLUSERS U INNER JOIN TBLUSERSTITLE UT ON U.USERNAME = UT.USERID INNER JOIN TBLUSERSLEVEL US ON US.USERSLEVEL = U.USERSLEVEL WHERE U.USERID=? ", this.id);
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("UserId", row.getString("USERID") == null ? "" : row.getString("USERID").toString());
			         m.setProperty("Email", row.getString("EMAILADDRESS") == null ? "" : row.getString("EMAILADDRESS").toString());
			         m.setProperty("Msisdn", row.getString("MSISDN") == null ? "" : row.getString("MSISDN").toString());
			         m.setProperty("Firstname", row.getString("FIRSTNAME") == null ? "" : row.getString("FIRSTNAME").toString());
			         m.setProperty("Lastname", row.getString("LASTNAME") == null ? "" : row.getString("LASTNAME").toString());
			         m.setProperty("Middlename", row.getString("MIDDLENAME") == null ? "" : row.getString("MIDDLENAME").toString());
			         m.setProperty("UsersLevel", row.getString("USERSLEVEL") == null ? "" : row.getString("USERSLEVEL").toString());
			         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			         m.setProperty("Department", row.getString("DEPARTMENT") == null ? "" : row.getString("DEPARTMENT").toString());
			         m.setProperty("EmploymentStatus", row.getString("EMPLOYMENTSTATUS") == null ? "" : row.getString("EMPLOYMENTSTATUS").toString());
			         m.setProperty("EmployeeNumber", row.getString("EMPLOYEENUMBER") == null ? "" : row.getString("EMPLOYEENUMBER").toString());
			         m.setProperty("Portal", row.getString("PORTAL") == null ? "" : row.getString("PORTAL").toString());
			         m.setProperty("UserName", row.getString("USERNAME") == null ? "" : row.getString("USERNAME").toString());
			         m.setProperty("TempEmail", row.getString("TEMPEMAILADDRESS") == null ? "" : row.getString("TEMPEMAILADDRESS").toString());
			         m.setProperty("TempMsisdn", row.getString("TEMPMSISDN") == null ? "" : row.getString("TEMPMSISDN").toString());
			         m.setProperty("ImmediateHead", row.getString("IMMEDIATEHEAD") == null ? "" : row.getString("IMMEDIATEHEAD").toString());
			         m.setProperty("IsOtpVerified", row.getString("ISOTPVERIFIED") == null ? "" : row.getString("ISOTPVERIFIED").toString());
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT U.*,UT.DEPARTMENT,UT.EMPLOYMENTSTATUS,UT.EMPLOYEENUMBER,UPPER(US.PORTAL) PORTAL,UT.IMMEDIATEHEAD,REPLACE(EMAIL,'~','') EMAILADDRESS,REPLACE(U.TEMPEMAIL,'~','') TEMPEMAILADDRESS FROM TBLUSERS U INNER JOIN TBLUSERSTITLE UT ON U.USERNAME = UT.USERID INNER JOIN TBLUSERSLEVEL US ON US.USERSLEVEL = U.USERSLEVEL WHERE U.USERID=? ", this.id);
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("UserId", row.getString("USERID") == null ? "" : row.getString("USERID").toString());
			         m.setProperty("Email", row.getString("EMAILADDRESS") == null ? "" : row.getString("EMAILADDRESS").toString());
			         m.setProperty("Msisdn", row.getString("MSISDN") == null ? "" : row.getString("MSISDN").toString());
			         m.setProperty("Firstname", row.getString("FIRSTNAME") == null ? "" : row.getString("FIRSTNAME").toString());
			         m.setProperty("Lastname", row.getString("LASTNAME") == null ? "" : row.getString("LASTNAME").toString());
			         m.setProperty("Middlename", row.getString("MIDDLENAME") == null ? "" : row.getString("MIDDLENAME").toString());
			         m.setProperty("UsersLevel", row.getString("USERSLEVEL") == null ? "" : row.getString("USERSLEVEL").toString());
			         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			         m.setProperty("Department", row.getString("DEPARTMENT") == null ? "" : row.getString("DEPARTMENT").toString());
			         m.setProperty("EmploymentStatus", row.getString("EMPLOYMENTSTATUS") == null ? "" : row.getString("EMPLOYMENTSTATUS").toString());
			         m.setProperty("EmployeeNumber", row.getString("EMPLOYEENUMBER") == null ? "" : row.getString("EMPLOYEENUMBER").toString());
			         m.setProperty("Portal", row.getString("PORTAL") == null ? "" : row.getString("PORTAL").toString());
			         m.setProperty("UserName", row.getString("USERNAME") == null ? "" : row.getString("USERNAME").toString());
			         m.setProperty("TempEmail", row.getString("TEMPEMAILADDRESS") == null ? "" : row.getString("TEMPEMAILADDRESS").toString());
			         m.setProperty("TempMsisdn", row.getString("TEMPMSISDN") == null ? "" : row.getString("TEMPMSISDN").toString());
			         m.setProperty("ImmediateHead", row.getString("IMMEDIATEHEAD") == null ? "" : row.getString("IMMEDIATEHEAD").toString());
			         m.setProperty("IsOtpVerified", row.getString("ISOTPVERIFIED") == null ? "" : row.getString("ISOTPVERIFIED").toString());
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserslevel() {
		return userslevel;
	}

	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
	}

	

}
