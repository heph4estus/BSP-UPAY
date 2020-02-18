package com.psi.keyaccount.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class KeyAccountCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLKEYACCOUNT ");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		        
		        	 m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
		        	 m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		        	 m.setProperty("AccountName", row.getString("ACCOUNTNAME") == null ? "" : row.getString("ACCOUNTNAME").toString());
			         m.setProperty("ContactNumber", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
			         m.setProperty("SpecificAddress", row.getString("SPECIFICADDRESS") == null ? "" : row.getString("SPECIFICADDRESS").toString());
			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
			         m.setProperty("XOrdinate", row.getString("XORDINATES") == null ? "" : row.getString("XORDINATES").toString());
			         m.setProperty("YOrdinate", row.getString("YORDINATES") == null ? "" : row.getString("YORDINATES").toString());
			         m.setProperty("BusinessName", row.getString("BUSINESSNAME") == null ? "" : row.getString("BUSINESSNAME").toString());
			         m.setProperty("FirstName", row.getString("FIRSTNAME") == null ? "" : row.getString("FIRSTNAME").toString());
			         m.setProperty("MiddleName", row.getString("MIDDLENAME") == null ? "" : row.getString("MIDDLENAME").toString());
			         m.setProperty("LastName", row.getString("LASTNAME") == null ? "" : row.getString("LASTNAME").toString());
			         m.setProperty("TimesTamp", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
			         m.setProperty("RegDate", row.getString("REGDATE") == null ? "" : row.getString("REGDATE").toString());
			         m.setProperty("TinNo", row.getString("TINNUMBER") == null ? "" : row.getString("TINNUMBER").toString());
			         m.setProperty("PostalCode", row.getString("ZIPCODE") == null ? "" : row.getString("ZIPCODE").toString());
			         
			         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

}
