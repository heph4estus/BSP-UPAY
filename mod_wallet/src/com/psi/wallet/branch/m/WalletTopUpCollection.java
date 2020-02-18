package com.psi.wallet.branch.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class WalletTopUpCollection extends ModelCollection{
	protected String id;
	protected String type;
	@Override
	public boolean hasRows() {
		DataRowCollection r = null;
		DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.id);			    
				r = SystemInfo.getDb().QueryDataRows("SELECT CT.ID,CT.ACCOUNTNUMBER,CT.REFERENCE,CT.AMOUNT,CT.TIMESTAMP,CT.STATUS,B.BRANCH,CT.CREATEDBY,CT.REMARKS,CT.DEPOSITCHANNEL,CT.EXTENDEDDATA FROM TBLCASHINTRANSACTIONS CT INNER JOIN TBLBRANCHES B ON CT.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE CT.TYPE=? AND CT.STATUS IN ('APPROVED','PNDG','PAID','UNPAID') AND CT.LEVELOFAPPROVAL=1 AND CT.ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ROOT = ?) "
														+ "UNION ALL "
														+ "SELECT CT.ID,CT.ACCOUNTNUMBER,CT.REFERENCE,CT.AMOUNT,CT.TIMESTAMP,CT.STATUS,B.BUSINESS, CT.CREATEDBY,CT.REMARKS,CT.DEPOSITCHANNEL,CT.EXTENDEDDATA FROM TBLCASHINTRANSACTIONS CT INNER JOIN TBLBUSINESS B ON CT.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE CT.TYPE=? AND CT.STATUS IN ('APPROVED','PNDG','PAID','UNPAID') AND CT.LEVELOFAPPROVAL=1 AND CT.ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ROOT = ?) ORDER BY TIMESTAMP DESC",
														this.type,SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", user.getString("ACCOUNTNUMBER")),
														this.type,SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", user.getString("ACCOUNTNUMBER")));
//				r = SystemInfo.getDb().QueryDataRows("SELECT CT.ID,CT.ACCOUNTNUMBER,CT.REFERENCE,CT.AMOUNT,CT.TIMESTAMP,CT.STATUS,B.BUSINESS, CT.CREATEDBY,CT.REMARKS,CT.DEPOSITCHANNEL FROM TBLCASHINTRANSACTIONS CT INNER JOIN TBLBUSINESS B ON CT.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE CT.TYPE=? AND CT.STATUS IN ('APPROVED','PNDG') AND CT.LEVELOFAPPROVAL=1 AND CT.ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ROOT = ?) ORDER BY TIMESTAMP DESC",
//						this.type,SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", user.getString("ACCOUNTNUMBER")));

		if (!r.isEmpty())
			     {
			    	 for(DataRow row: r){
				         ReportItem m = new ReportItem();
				         DataRow req = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", row.getString("CREATEDBY"));
				         m.setProperty("CreatedBy", row.getString("CREATEDBY") == null ? "" : row.getString("CREATEDBY"));
				         m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
				         m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
				         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
				         m.setProperty("Reference", row.getString("REFERENCE") == null ? "" : row.getString("REFERENCE").toString());
				         m.setProperty("Amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
				         m.setProperty("Timestamp", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
				         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
				         m.setProperty("FirstName", req.getString("FIRSTNAME") == null ? "" : req.getString("FIRSTNAME").toString());
				         m.setProperty("LastName", req.getString("LASTNAME") == null ? "" : req.getString("LASTNAME").toString());
				         m.setProperty("Email", req.getString("EMAIL") == null ? "" : req.getString("EMAIL").toString());
				         m.setProperty("Remarks", row.getString("REMARKS") == null ? "" : row.getString("REMARKS").toString());
				         m.setProperty("DepositChannel", row.getString("DEPOSITCHANNEL") == null ? "" : row.getString("DEPOSITCHANNEL").toString());
				         m.setProperty("ExtendedData", row.getString("EXTENDEDDATA") == null ? "" : row.getString("EXTENDEDDATA").toString());
				         
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
