package com.psi.audit.trail.m;

import java.util.ArrayList;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;

public class GetAuditPreviousData {
	private String query;
	private ArrayList<Object> param = new ArrayList<Object>();
	
	public String getData() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows(this.query,param.toArray());
		StringBuilder m = new StringBuilder("");
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 	
	    		 for (String key : row.keySet()) {
	    			 m.append(key +":"+row.getString(key).toString() + "|");
			 	 }
	    	 }
	     }
	     return m.toString();
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public ArrayList<Object> getParam() {
		return param;
	}
	public void setParam(ArrayList<Object> param) {
		this.param = param;
	}
}
