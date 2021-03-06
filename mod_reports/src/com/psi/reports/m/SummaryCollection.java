package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class SummaryCollection extends ModelCollection{

	 protected String accountnumber;
	 protected String datefrom;
	 protected String dateto;
	
	@Override
	public boolean hasRows() {
		String df = "";
		String dt = "";
		if (StringUtil.isNullOrEmpty(this.datefrom)){
			df = SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(REGDATE,'YYYY-MM-DD') REGDATE FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", this.accountnumber);
		}else{
			df = this.datefrom;
		}
		if (StringUtil.isNullOrEmpty(this.dateto)){
		    dt = SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(MAX(TIMESTAMP),'YYYY-MM-DD') TIMESTAMP FROM ADMDBMC.TBLTRANSACTIONDETAILS WHERE ACCOUNTNUMBER = ?", "", this.accountnumber);
		}else{
			dt = this.dateto;
		}
		StringBuilder query = new StringBuilder();
		query.append("SELECT  'WALLET' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(CREDIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1001 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL \n");
		query.append("SELECT 'COMMISSION' TYPE, ACCOUNTTYPE,COUNT(1) COUNT,SUM(CREDIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE='COMMISSION' ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3  GROUP BY ACCOUNTTYPE\n");
		query.append("UNION ALL \n");
		query.append("SELECT  'OTHERCREDIT' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(CREDIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1009 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT 'WALLETCREDIT' TYPE,'WALLETCREDIT' ACCOUNTTYPE, COUNT(COUNT) COUNT,SUM(TOTAL) TOTAL FROM( SELECT 'COMMISSION' TYPE, ACCOUNTTYPE,COUNT(1) COUNT,SUM(CREDIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE='COMMISSION' ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3  GROUP BY ACCOUNTTYPE  UNION ALL  SELECT  'WALLET' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(CREDIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1001 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE UNION ALL SELECT  'OTHERCREDIT' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(CREDIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1009 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE)\n");
		query.append("UNION ALL\n");
		query.append("SELECT 'CHARGES' TYPE, ACCOUNTTYPE,COUNT(1) COUNT,SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE='CHARGES' ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT  'AIRTIME' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1002 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT  'ECASH' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1102 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT 'BILLS' TYPE,ACCOUNTTYPE,COUNT(COUNT) COUNT,SUM(TOTAL) TOTAL FROM ( SELECT  'BILLS' TYPE , ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE IN (1103,1003) ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE) GROUP BY TYPE, ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT 'WALLETDEBIT' TYPE,'WALLETDEBIT' ACCOUNTTYPE, COUNT(COUNT) COUNT, SUM(TOTAL) TOTAL FROM(\n");
		query.append("SELECT 'CHARGES' TYPE, ACCOUNTTYPE,COUNT(1) COUNT,SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE='CHARGES' ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY ACCOUNTTYPE\n");
	    query.append("UNION ALL\n");
		query.append("SELECT  'AIRTIME' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1002 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT  'ECASH' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1102 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT 'BILLS' TYPE,ACCOUNTTYPE,COUNT(COUNT) COUNT,SUM(TOTAL) TOTAL FROM ( SELECT  'BILLS' TYPE , ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE IN (1103,1003) ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE) GROUP BY TYPE, ACCOUNTTYPE)\n");
		query.append("UNION ALL\n");
		query.append("SELECT 'BEGINNINGBAL' TYPE,'BEGINNINGBALANCE' ACCOUNTTYPE, 0 COUNT,BALANCEBEFORE TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) WHERE RID=1\n");
		query.append("UNION ALL \n");
		query.append("SELECT 'ENDINGBAL' TYPE,'ENDINGBALANCE' ACCOUNTTYPE, 0 COUNT,BALANCEAFTER TOTAL FROM ((SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 ORDER BY T.TIMESTAMP ASC,GLID ASC)  D2) ORDER BY RID DESC) WHERE ROWNUM =1\n");
		query.append("UNION ALL\n");
		query.append("SELECT 'TOTALNUMBEROFTRANS' TYPE,'TOTALNUMBEROFTRANS' ACCOUNTTYPE, SUM(COUNT),SUM(TOTAL) TOTAL FROM( \n");  
		query.append("SELECT  'WALLET' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(CREDIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1001 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL \n");
		query.append("SELECT  'OTHERCREDIT' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(CREDIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1009 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT  'AIRTIME' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1002 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL\n");
		query.append("SELECT  'ECASH' TYPE ,ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE = 1102 ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE\n");
		query.append("UNION ALL\n"); 
		query.append("SELECT 'BILLS' TYPE,ACCOUNTTYPE,COUNT(COUNT) COUNT,SUM(TOTAL) TOTAL FROM ( SELECT  'BILLS' TYPE , ACCOUNTTYPE,COUNT(1) COUNT, SUM(DEBIT) TOTAL FROM (SELECT ROWNUM RID, D2.* FROM (SELECT D1.* FROM ADMDBMC.TBLTRANSACTIONDETAILS D1 INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON T.REFERENCEID = D1.REFERENCEID WHERE T.VOIDID IS NULL  AND ACCOUNTNUMBER = ? AND T.TIMESTAMP BETWEEN TO_DATE(?,'yyyy-MM-dd') AND TO_DATE(?,'yyyy-MM-dd') + 1 AND ACCOUNTTYPE NOT IN ('COMMISSION','CHARGES') AND TYPE IN (1103,1003) ORDER BY T.TIMESTAMP ASC,GLID ASC) D2) D3 GROUP BY TYPE,ACCOUNTTYPE) GROUP BY TYPE, ACCOUNTTYPE)\n");
		        
		DataRowCollection r = SystemInfo.getDb().QueryDataRows(query.toString(),this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt,this.accountnumber,df,dt);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("TOTAL", row.getString("TOTAL") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("TOTAL").toString())));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}	
	
	public String getAccountNumber() {
		return accountnumber;
	}
	public void setAccountNumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return dateto;
	}

	public void setDateto(String dateto) {
		this.dateto = dateto;
	}
	
}

