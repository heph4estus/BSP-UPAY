package com.psi.customer.support.m;

import org.json.simple.JSONObject;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;

@SuppressWarnings("serial")
public class SearchByTypeDateCollection extends ModelCollection{
	protected String type;
	protected String account;
	protected String datefrom;
	protected String dateto;
	protected String username;
	@SuppressWarnings("unchecked")
	@Override
	public boolean hasRows() {
		
		String t = "";
		
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT C.TYPE HTYPE,C.REFERENCEID HREFERENCEID,C.THIRDPARTYACCOUNT HTHIRDPARTYACCOUNT,C.ACCOUNTNUMBER HACCOUNTNUMBER,C.TIMESTAMP HTIMESTAMP,T.TIMESTAMP TTIMESTAMP,T.BRAND TBRAND,T.TRACEID TTRACEID,T.TRANSACTIONTYPE TTRANSACTIONTYPE,T.REFERENCEID TREFERENCEID,T.TYPE TTYPE,CASE WHEN T.CREDIT IS NULL THEN 0 ELSE T.CREDIT END TCREDIT,CASE WHEN T.DEBIT IS NULL THEN 0 ELSE T.DEBIT END TDEBIT,CASE WHEN T.BALANCEBEFORE IS NULL THEN 0 ELSE T.BALANCEBEFORE END TBALANCEBEFORE,CASE WHEN T.BALANCEAFTER IS NULL THEN 0 ELSE T.BALANCEAFTER END TBALANCEAFTER,T.BRANCH TBRANCH,T.LOCATION TLOCATION,T.THIRDPARTYACCOUNT TTHIRDPARTYACCOUNT,P.THIRDPARTYACCOUNT PTHIRDPARTYACCOUNT,T.TYPE PTYPE,P.REFERENCEID PREFERENCEID,P.ACCOUNTNUMBER PACCOUNTNUMBER,P.TIMESTAMP PTIMESTAMP,P.MESSAGE PMESSAGE,P.STATUS PSTATUS,P.OBJECT POBJECT FROM TBLCSHITS C LEFT JOIN TBLCSTRANSACTIONDETAILS T ON C.REFERENCEID = T.REFERENCEID AND C.THIRDPARTYACCOUNT = T.THIRDPARTYACCOUNT  LEFT JOIN TBLCSPULL P ON C.REFERENCEID = P.REFERENCEID AND C.THIRDPARTYACCOUNT=P.THIRDPARTYACCOUNT  WHERE TO_CHAR(C.TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ? AND UPPER(C.THIRDPARTYACCOUNT) LIKE '%' || ? || '%' AND C.TYPEDESC = ? AND UPPER(C.REQUEST) LIKE '%' || ? || '%' ORDER BY C.TIMESTAMP DESC",this.datefrom,this.dateto,this.account.toString(),this.type,this.username.toString());
	 
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItemv2 m = new ReportItemv2();
	    		 JSONObject h = new JSONObject();
	    			JSONObject tt = new JSONObject();
	    			JSONObject p = new JSONObject();
	    			JSONObject json = new JSONObject();
	    			JSONObject json2 = new JSONObject();
	    			JSONObject json3 = new JSONObject();
	    			JSONObject all = new JSONObject();
			 	 json.put("TYPE", row.getString("HTYPE").toString());
			 	 json.put("REFERENCEID", row.getString("HREFERENCEID").toString());
			 	 json.put("THIRDPARTYACCOUNT", row.getString("HTHIRDPARTYACCOUNT").toString());
			 	 json.put("ACCOUNTNUMBER", row.getString("HACCOUNTNUMBER").toString());
			 	 json.put("TIMESTAMP", row.getString("HTIMESTAMP").toString());
			 	 h.put("HITS", json);
			 	 
			 	 json2.put("TIMESTAMP", row.getString("TTIMESTAMP").toString());
			 	 json2.put("BRAND", row.getString("TBRAND").toString());
			 	 json2.put("TRACEID", row.getString("TTRACEID").toString());
			 	 json2.put("TRANSACTIONTYPE", row.getString("TTRANSACTIONTYPE").toString());
			 	 json2.put("REFERENCEID", row.getString("TREFERENCEID").toString());
			 	 json2.put("TYPE", row.getString("TTYPE").toString());
			 	 //json2.put("GLID", row.getString("TGLID").toString());
			 	 //json2.put("WALLETID", row.getString("TWALLETID").toString());
			 	 //json2.put("ALIAS", row.getString("TALIAS").toString());
			 	 //json2.put("REMARKS", row.getString("TREMARKS").toString());
			 	 //json2.put("ACCOUNTTYPE", row.getString("TACCOUNTTYPE").toString());
			 	 json2.put("BRANCH", row.getString("TBRANCH").toString());
			 	 json2.put("LOCATION", row.getString("TLOCATION").toString());
			 	 json2.put("THIRDPARTYACCOUNT", row.getString("TTHIRDPARTYACCOUNT").toString());
	    		 json2.put("CREDIT", row.getString("TCREDIT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("TCREDIT").toString())));
	    		 json2.put("DEBIT", row.getString("TDEBIT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("TDEBIT").toString())));
	    		 json2.put("BALANCEBEFORE", row.getString("TBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("TBALANCEBEFORE").toString())));
	    		 json2.put("BALANCEAFTER", row.getString("TBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("TBALANCEAFTER").toString())));
		         tt.put("TRANSACTIONDETAILS", json2);
		         
		         json3.put("TYPE", row.getString("PTYPE").toString());
			 	 json3.put("REFERENCEID", row.getString("PREFERENCEID").toString());
			 	 json3.put("THIRDPARTYACCOUNT", row.getString("PTHIRDPARTYACCOUNT").toString());
			 	 json3.put("ACCOUNTNUMBER", row.getString("PACCOUNTNUMBER").toString());
			 	 json3.put("TIMESTAMP", row.getString("PTIMESTAMP").toString());
			 	 json3.put("MESSAGE", row.getString("PMESSAGE").toString());
			 	 json3.put("STATUS", row.getString("PSTATUS").toString());
			 	 json3.put("OBJECT", row.getString("POBJECT").toString());
			 	 p.put("PULL", json3);
			 	 all.putAll(h);
			 	 all.putAll(tt);
			 	 all.putAll(p);
			 	 m.setPropertyv2(all);
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
