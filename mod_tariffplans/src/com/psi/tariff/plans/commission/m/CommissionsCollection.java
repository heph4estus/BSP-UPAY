package com.psi.tariff.plans.commission.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class CommissionsCollection extends ModelCollection{
	protected String provider;
	protected String type;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT DISTINCT 'AIRTIME' TYPE,PROVIDER,MAX(TIMESTAMP) TIMESTAMP,MAX(CREATEDBY) CREATEDBY,TARIFF FROM TBLTARIFFAIRTCOMMISSION GROUP BY PROVIDER,TARIFF "
																+" UNION ALL "
																+" SELECT DISTINCT 'CASHCARD' TYPE,PROVIDER,MAX(TIMESTAMP) TIMESTAMP,MAX(CREATEDBY) CREATEDBY,TARIFF FROM TBLTARIFFEMONEYCOMMISSION GROUP BY PROVIDER,TARIFF "
																+" UNION ALL "
																+" SELECT DISTINCT 'BILLER' TYPE,PROVIDER,MAX(TIMESTAMP) TIMESTAMP,MAX(CREATEDBY) CREATEDBY,TARIFF FROM TBLTARIFFBILLSCOMMISSION GROUP BY PROVIDER,TARIFF");
	     
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
