package com.psi.tariff.plans.commission.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AppliedTariffCollection extends Model{
	protected String tariff;
	protected String type;

	public boolean hasRows() {
		String query="";
		if(this.type.equals("AIRTIME")){
			query = "SELECT * FROM  TBLTARIFFAIRTCOMMISSION WHERE UPPER(TARIFF) = ? ";
		}else if(this.type.equals("BILLER")){
			query = "SELECT * FROM  TBLTARIFFBILLSCOMMISSION WHERE UPPER(TARIFF) = ?  ";
		}else{
			query = " SELECT * FROM TBLTARIFFEMONEYCOMMISSION  WHERE UPPER(TARIFF) = ?)";
		}
		DataRow r = SystemInfo.getDb().QueryDataRow(query,this.tariff.toUpperCase());
	     
	     
	     return r.size() > 0;
	}
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
