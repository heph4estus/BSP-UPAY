package com.psi.audit.trail.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AuditTrailCollection extends ModelCollection{

	protected String userslevel;
	protected String datefrom;
	protected String dateto;
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLOPAUDITTRAIL WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.datefrom,this.dateto);
	     
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
	
	public boolean getPrepaidCol() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT A.USERNAME,A.USERID,A.LOG,IP,TIMESTAMP,A.STATUS,M.MODULE,A.SESSIONID,A.ENTITYID,A.DATA,A.REMARKS,A.BROWSER,A.BROWSERVERSION,A.REQUEST,A.OS,A.PORTALVERSION,A.USERSLEVEL FROM TBLAUDITTRAIL A INNER JOIN TBLUSERS U ON U.USERNAME = A.USERNAME INNER JOIN TBLUSERSLEVEL UL ON UL.USERSLEVEL = U.USERSLEVEL INNER JOIN TBLMODULE M ON TRIM(TO_CHAR(M.ID,'9999999')) = A.MODULE WHERE UL.PORTAL ='operator' AND U.USERSLEVEL = ? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.userslevel,this.datefrom,this.dateto);
	     
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

	
	
	public String getUserslevel() {
		return userslevel;
	}

	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
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
}
