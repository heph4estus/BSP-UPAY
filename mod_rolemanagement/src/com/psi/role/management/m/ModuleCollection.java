package com.psi.role.management.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class ModuleCollection extends ModelCollection{
private String portal;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean hasRows() {
		DataRowCollection modules =  SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLMODULE WHERE STATUS=1 AND UPPER(PORTAL)=? ORDER BY ID ASC",this.portal.toUpperCase());
		if( modules!=null && modules.size()>0){
			
			for(DataRow r: modules){ 
		        ReportItem i = new ReportItem();
		        i.setProperty(r.getKey(0), r.getString(0));
		        i.setProperty(r.getKey(1), r.getString(1));
		        this.add(i);
		        
			}
			return true;
		}
		return false;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

}
