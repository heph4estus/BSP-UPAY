package com.psi.business.v2.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class RequiredDocCollection extends ModelCollection{
	protected String accountnumber;
	protected String type;
	protected String category;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT RD.*,DATEREGISTERED,ID,ACCOUNTNUMBER FROM TBLREQUIREDDOCUMENTS RD INNER JOIN TBLDOCUMENTS D  ON D.DOCTYPE = RD.TYPE WHERE STATUS = 1 AND ACCOUNTNUMBER = ? AND SERVICETYPE=? AND CATEGORY=?"+
															   " UNION ALL "+
															   " SELECT RD.*,SYSDATE ,0 ID,'' ACCOUNTNUMBER FROM TBLREQUIREDDOCUMENTS RD WHERE TYPE NOT IN (SELECT DOCTYPE FROM TBLDOCUMENTS WHERE ACCOUNTNUMBER = ?) AND SERVICETYPE=? AND CATEGORY=?",this.accountnumber,this.type,this.category,this.accountnumber,this.type,this.category);
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
