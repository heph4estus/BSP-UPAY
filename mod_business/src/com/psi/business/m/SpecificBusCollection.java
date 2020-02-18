package com.psi.business.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class SpecificBusCollection extends ModelCollection{
	protected String id;
	@Override
	public boolean hasRows() {
		
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT PROOFADDRESS FROM TBLBUSINESS WHERE ACCOUNTNUMBER = ? UNION ALL SELECT PROOFADDRESS FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?",this.id,this.id);
	     
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
