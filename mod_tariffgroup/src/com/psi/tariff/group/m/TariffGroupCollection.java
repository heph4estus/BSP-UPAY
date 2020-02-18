package com.psi.tariff.group.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class TariffGroupCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTARIFFGROUP ORDER BY ID DESC ");
	     
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
	public boolean hasRowscommission() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT DISTINCT GROUPNAME,DESCRIPTION,CREATEDBY,ID FROM ( "
											                   +"  SELECT DISTINCT TG.GROUPNAME,DESCRIPTION,TG.CREATEDBY,TG.ID FROM TBLTARIFFGROUP TG INNER JOIN TBLTARIFFAIRTCOMMISSION TC ON TG.GROUPNAME = TC.TARIFF "
											                   +"   UNION ALL "
											                   +"   SELECT DISTINCT TG.GROUPNAME,DESCRIPTION,TG.CREATEDBY,TG.ID FROM TBLTARIFFGROUP TG INNER JOIN TBLTARIFFBILLSCOMMISSION TC ON TG.GROUPNAME = TC.TARIFF "
											                   +"   UNION ALL "
											                   +"   SELECT DISTINCT TG.GROUPNAME,DESCRIPTION,TG.CREATEDBY,TG.ID FROM TBLTARIFFGROUP TG INNER JOIN TBLTARIFFEMONEYCOMMISSION TC ON TG.GROUPNAME = TC.TARIFF) ORDER BY GROUPNAME ASC ");
	     
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
}
