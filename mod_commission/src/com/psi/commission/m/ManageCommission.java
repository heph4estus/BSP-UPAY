package com.psi.commission.m;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.simple.JSONObject;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ManageCommission extends Model{
	protected String accountnumber;
	protected Long  fixed;
	protected Long percent;
	protected String expirydate;
	protected String type;
	
	@SuppressWarnings("unchecked")
	public boolean create(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCOMMISSIONCONFIG WHERE TRANSTYPE = ?", this.type);
		JSONObject config = new JSONObject();
		BigDecimal bg1, bg2, bg3;
		bg1 = new BigDecimal(LongUtil.toString(Long.parseLong(this.percent.toString())));
	    bg2 = new BigDecimal("100");
	    bg3 = bg1.divide(bg2, 4, RoundingMode.CEILING);
		config.put("fixed", LongUtil.toString(Long.parseLong(this.fixed.toString())));
		config.put("percent",bg3 );
		config.put("dest-account", row.getString("DEST"));
		config.put("pocket", 1);
		
		String branch = SystemInfo.getDb().QueryScalar("SELECT BRANCH FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", "", this.accountnumber);
		if(this.type.equals("POSTPAID")){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("INSERT INTO TBLCOMMISSION(ACCOUNTNUMBER,TYPE,FIXED,PERCENT,EXPIRYDATE,CREATEDBY,TIMESTAMP) VALUES(?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,SYSDATE); \n");
			query.append("INSERT INTO ADMDBMC.TBLADJUSTMENTS(CLASS, CONFIG, DESCRIPTION, SOURCE, SOURCETYPE, DEST, DESTTYPE, MODULE, TYPE, DATEFROM,DATETO)Values(?,?,?,?,?,?,?,?,?,SYSDATE,TO_DATE(?,'YYYY-MM-DD')); \n");
			query.append("INSERT INTO ADMDBMC.TBLADJUSTMENTS(CLASS, CONFIG, DESCRIPTION, SOURCE, SOURCETYPE, DEST, DESTTYPE, MODULE, TYPE, DATEFROM,DATETO)Values(?,?,?,?,?,?,?,?,?,SYSDATE,TO_DATE(?,'YYYY-MM-DD')); \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.type,this.fixed,this.percent,this.expirydate,sess.getAccount().getUserName(),
																	row.getString("CLASS"),config.toString(),this.fixed+" "+this.percent+" "+row.getString("DESCRIPTION")+" "+branch,this.accountnumber,row.getString("SOURCETYPE"),row.getString("DEST"),row.getString("DESTTYPE"),row.getString("MODULE"),row.getString("TYPE"),this.expirydate,
																	row.getString("CLASS"),config.toString(),this.fixed+" "+this.percent+" "+row.getString("DESCRIPTION")+" "+branch,this.accountnumber,row.getString("SOURCETYPE"),row.getString("DEST"),row.getString("DESTTYPE"),"1103",row.getString("TYPE"),this.expirydate)>0;
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("INSERT INTO TBLCOMMISSION(ACCOUNTNUMBER,TYPE,FIXED,PERCENT,EXPIRYDATE,CREATEDBY,TIMESTAMP) VALUES(?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,SYSDATE); \n");
			query.append("INSERT INTO ADMDBMC.TBLADJUSTMENTS(CLASS, CONFIG, DESCRIPTION, SOURCE, SOURCETYPE, DEST, DESTTYPE, MODULE, TYPE, DATEFROM,DATETO)Values(?,?,?,?,?,?,?,?,?,SYSDATE,TO_DATE(?,'YYYY-MM-DD')); \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.type,this.fixed,this.percent,this.expirydate,sess.getAccount().getUserName(),
																	row.getString("CLASS"),config.toString(),this.fixed+" "+this.percent+" "+row.getString("DESCRIPTION")+" "+branch,this.accountnumber,row.getString("SOURCETYPE"),row.getString("DEST"),row.getString("DESTTYPE"),row.getString("MODULE"),row.getString("TYPE"),this.expirydate)>0;
		}	
	}
	@SuppressWarnings("unchecked")
	public boolean edit(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCOMMISSIONCONFIG WHERE TRANSTYPE = ?", this.type);
		JSONObject config = new JSONObject();
		BigDecimal bg1, bg2, bg3;
		bg1 = new BigDecimal(LongUtil.toString(Long.parseLong(this.percent.toString())));
	    bg2 = new BigDecimal("100");
	    bg3 = bg1.divide(bg2, 4, RoundingMode.CEILING);
		config.put("fixed", LongUtil.toString(Long.parseLong(this.fixed.toString())));
		config.put("percent",bg3 );
		config.put("dest-account", row.getString("DEST"));
		config.put("pocket", 1);
		
		String branch = SystemInfo.getDb().QueryScalar("SELECT BRANCH FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", "", this.accountnumber);
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLCOMMISSION SET FIXED=?,PERCENT=?,EXPIRYDATE=TO_DATE(?,'YYYY-MM-DD'),MODIFIEDBY=?,MODIFIEDDATE=SYSDATE WHERE ACCOUNTNUMBER = ? AND TYPE =?; \n");
			query.append("UPDATE ADMDBMC.TBLADJUSTMENTS SET CONFIG=?,DESCRIPTION=?,DATETO=TO_DATE(?,'YYYY-MM-DD') WHERE CLASS = ? AND SOURCE = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");

			return SystemInfo.getDb().QueryUpdate(query.toString(),this.fixed,this.percent,this.expirydate,sess.getAccount().getUserName(),this.accountnumber,this.type,
																	config.toString(),this.fixed+" "+this.percent+" "+row.getString("DESCRIPTION")+" "+branch,this.expirydate,row.getString("CLASS"),this.accountnumber)>0;
			
			
		
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCOMMISSION WHERE ACCOUNTNUMBER=? AND TYPE = ?",this.accountnumber,this.type).size()>0;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public Long getFixed() {
		return fixed;
	}
	public void setFixed(Long fixed) {
		this.fixed = fixed;
	}
	public Long getPercent() {
		return percent;
	}
	public void setPercent(Long percent) {
		this.percent = percent;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
