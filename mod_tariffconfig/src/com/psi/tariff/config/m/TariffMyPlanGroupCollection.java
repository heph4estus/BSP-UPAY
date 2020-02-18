package com.psi.tariff.config.m;

import com.psi.tariff.config.util.Tariffs;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class TariffMyPlanGroupCollection extends ModelCollection{

	private String groupname;
	protected String type;
	
	public TariffMyPlanGroupCollection(String groupname) {
		this.groupname = groupname;
	}
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT TP.*,TF.TARIFFGROUP FROM TBLTARIFFCONFIG TF INNER JOIN TBLTARIFFPLANS TP ON TP.ID = TF.PLANID WHERE TARIFFGROUP=? AND TP.TYPE=? ORDER BY TP.ID DESC",this.groupname,this.type);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("MAXAMOUNT", row.getString("MAXAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT").toString())));
	    		 m.setProperty("MINAMOUNT", row.getString("MINAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT").toString())));
	    		if(row.getString("TYPE").equals("FEE")){
	    			m.setProperty("MAXAMOUNTDAY", row.getString("MAXAMOUNTDAY").toString());
	    		}else{
	    			m.setProperty("MAXAMOUNTDAY", row.getString("MAXAMOUNTDAY") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTDAY").toString())));
	    		}
	    		 m.setProperty("MAXAMOUNTWEEK", row.getString("MAXAMOUNTWEEK") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTWEEK").toString())));
	    		 m.setProperty("MAXAMOUNTMONTH", row.getString("MAXAMOUNTMONTH") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNTMONTH").toString())));
	    		 
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
		
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
