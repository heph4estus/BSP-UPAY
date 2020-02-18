package com.psi.tariff.plans.commission.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class TariffGroupComCollection extends ModelCollection{
	protected String provider;
	protected String type;
	@Override
	public boolean hasRows() {
		String query="";
		if(this.type.equals("AIRTIME")){
			query = "SELECT * FROM TBLTARIFFGROUP WHERE GROUPNAME NOT IN (SELECT GROUPNAME FROM TBLTARIFFGROUP TG INNER JOIN TBLTARIFFAIRTCOMMISSION TC ON TG.GROUPNAME = TC.TARIFF WHERE PROVIDER = ?)";
		}else if(this.type.equals("BILLER")){
			query = "SELECT * FROM TBLTARIFFGROUP WHERE GROUPNAME NOT IN (SELECT GROUPNAME FROM TBLTARIFFGROUP TG INNER JOIN TBLTARIFFBILLSCOMMISSION TC ON TG.GROUPNAME = TC.TARIFF WHERE PROVIDER = ?) ";
		}else{
			query = " SELECT * FROM TBLTARIFFGROUP WHERE GROUPNAME NOT IN (SELECT GROUPNAME FROM TBLTARIFFGROUP TG INNER JOIN TBLTARIFFEMONEYCOMMISSION TC ON TG.GROUPNAME = TC.TARIFF WHERE PROVIDER = ?)";
		}
		DataRowCollection r = SystemInfo.getDb().QueryDataRows(query,this.provider);
	     
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
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
