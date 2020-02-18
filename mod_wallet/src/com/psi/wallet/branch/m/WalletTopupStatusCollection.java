package com.psi.wallet.branch.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class WalletTopupStatusCollection extends ModelCollection{
	protected String id;
	protected String type;
	@Override
	public boolean hasRows() {

		DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.id);
		StringBuilder query = new StringBuilder();
			query.append("SELECT * FROM TBLALLOCTRACKING WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ROOT = ?) ORDER BY TIMESTAMP DESC");
			DataRowCollection r = SystemInfo.getDb().QueryDataRows(query.toString(),SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", user.getString("ACCOUNTNUMBER")));	     
		     
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("CreatedBy", row.getString("CREATEDBY") == null ? "" : row.getString("CREATEDBY"));
			         m.setProperty("Email", user.getString("EMAIL") == null ? "" : user.getString("EMAIL").toString());
			         m.setProperty("LastName", user.getString("LASTNAME") == null ? "" : user.getString("LASTNAME").toString());
			         m.setProperty("FirstName", user.getString("FIRSTNAME") == null ? "" : user.getString("FIRSTNAME").toString());
			         m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
			         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			         m.setProperty("Reference", row.getString("REFERENCE") == null ? "" : row.getString("REFERENCE").toString());
			         m.setProperty("Amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("Timestamp", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
			         if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("APPROVED")){
			        	 m.setProperty("Status", "APPROVED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(0) || row.getString("LEVELOFAPPROVAL").equals("0") && row.getString("STATUS").equals("APPROVED")){
			        	 m.setProperty("Status", "COMPLETED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") && row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") && row.getString("STATUS").equals("CANCEL")){
			        	 m.setProperty("Status", "CANCELLED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") || row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("REJECT")){
			        	 m.setProperty("Status", "REJECTED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("CANCEL")){
			        	 m.setProperty("Status", "CANCELLED");
			         }
			         else if(row.getString("LEVELOFAPPROVAL").equals(0) || row.getString("STATUS").equals("PAID")){
			        	 m.setProperty("Status", "COMPLETED-PAID");
			         }
			         else if(row.getString("LEVELOFAPPROVAL").equals(0) ||  row.getString("STATUS").equals("NOTPAID") || row.getString("STATUS").equals("UNPAID")){
			        	 m.setProperty("Status", "COMPLETED-UNPAID");
			         }
			         else if(row.getString("LEVELOFAPPROVAL").equals(1) ||  row.getString("DEPOSITCHANNEL").equals("CREDIT") ||  row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING-UNPAID");
			         }
			         m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
			         m.setProperty("Remarks", row.getString("REMARKS") == null ? "" : row.getString("REMARKS").toString());
			         m.setProperty("DepositChannel", row.getString("DEPOSITCHANNEL") == null ? "" : row.getString("DEPOSITCHANNEL").toString());
			         m.setProperty("ExtendedData", row.getString("EXTENDEDDATA") == null ? "" : row.getString("EXTENDEDDATA").toString());
			         m.setProperty("ExtendedData2", row.getString("EXTENDEDDATA2") == null ? "" : row.getString("EXTENDEDDATA2").toString());
			         m.setProperty("Type", row.getString("TYPE") == null ? "" : row.getString("TYPE").toString());
			         
			         add(m);
		    	 }
		     }
			 return r.size() > 0;		   
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
