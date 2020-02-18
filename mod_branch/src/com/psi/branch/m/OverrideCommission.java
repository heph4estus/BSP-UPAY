package com.psi.branch.m;

import java.util.ArrayList;

import com.psi.audit.trail.m.GetAuditPreviousData;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class OverrideCommission extends Model{
	protected String accountnumber;
	protected String tariff;
	protected String status;
	protected String auditdata;
	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.accountnumber);
		  previous.setQuery("SELECT OVERRIDECOMMISSION FROM TBLBRANCHES B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER=?");	  
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public boolean override(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCOMPANYTARIFF CT INNER JOIN TBLTARIFFGROUP TG ON CT.TARIFFGROUP = TG.ID WHERE OVERRIDECOMMISSION = 1");
		String company = SystemInfo.getDb().QueryScalar("SELECT KEYACCOUNT FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?", "", this.accountnumber);
		String tariff = SystemInfo.getDb().QueryScalar("SELECT ID FROM TBLTARIFFGROUP WHERE UPPER(GROUPNAME) = ?", "", this.tariff.toUpperCase());
		if(this.status.equals("YES")){
			if(row.isEmpty()){
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("INSERT INTO TBLCOMPANYTARIFF (ACCOUNTNUMBER,TARIFFGROUP,PRIORITY,COMPANY) VALUES(?,?,1,?);\n");
				query.append("UPDATE TBLMERCHANTDETAILS SET OVERRIDECOMMISSION = ? WHERE ACCOUNTNUMBER = ?;\n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				
				return SystemInfo.getDb().QueryUpdate(query.toString(), this.accountnumber,tariff,company,this.tariff,this.accountnumber)>0;
			}else{
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLCOMPANYTARIFF SET TARIFFGROUP=? WHERE ACCOUNTNUMBER = ?;\n");
				query.append("UPDATE TBLMERCHANTDETAILS SET OVERRIDECOMMISSION = ? WHERE ACCOUNTNUMBER = ?;\n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				
				return SystemInfo.getDb().QueryUpdate(query.toString(), tariff,this.accountnumber,this.tariff,this.accountnumber)>0;
			}
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("DELETE TBLCOMPANYTARIFF WHERE ACCOUNTNUMBER = ? AND TARIFFGROUP=?;\n");
			query.append("UPDATE TBLMERCHANTDETAILS SET OVERRIDECOMMISSION = '' WHERE ACCOUNTNUMBER = ?;\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,tariff,this.accountnumber)>0;

		}
		
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber).size()>0;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuditdata() {
		return auditdata;
	}
	public void setAuditdata(String auditdata) {
		this.auditdata = auditdata;
	}
}
