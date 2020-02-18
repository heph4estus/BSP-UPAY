package com.psi.tariff.plans.commission.m;

import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ManageCommission extends Model{
	protected String type;
	protected String provider;
	protected String category;
	protected JSONArray products = new JSONArray();
	protected String revenue;
	protected String tariff;
	protected String flag;
	public boolean setrevenueairtime() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String insert = "INSERT INTO TBLTARIFFAIRTCOMMISSION (CREATEDBY, IBAYADCODE, BASEPRICE, BUYINGPRICE, FIXED, PERCENTAGE, PROVIDER, CATEGORY, TARIFF ) VALUES";
		String update = "UPDATE TBLTARIFFAIRTCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONTARIFFHISTORY (CREATEDBY, PROVIDER, CATEGORY, COMMISSION ) VALUES (?,?,?,?)";
		for (Object prods : products) {
				JSONObject prod = (JSONObject) prods;
				DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFAIRTCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
				if(row.isEmpty()){
					insertbld.append(insert);
					insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("baseprice").toString())+","+LongUtil.toLong(prod.get("buyingprice").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"','"+this.tariff+"')");
					insertbld.append(";\n");
				}else{
					updatebld.append(update);
					updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', BASEPRICE = "+LongUtil.toLong(prod.get("baseprice").toString())+", BUYINGPRICE = "+LongUtil.toLong(prod.get("buyingprice").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+", TARIFF = '"+this.tariff+"' WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
					updatebld.append(";\n");
				}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue)>0;
	}
	public boolean setrevenueairtimeall() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		StringBuilder updatecommissionbld = new StringBuilder();
		String insert = "INSERT INTO TBLTARIFFAIRTCOMMISSION (CREATEDBY, IBAYADCODE, BASEPRICE, BUYINGPRICE, FIXED, PERCENTAGE, PROVIDER, CATEGORY, TARIFF ) VALUES";
		String update = "UPDATE TBLTARIFFAIRTCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONTARIFFHISTORY (CREATEDBY, PROVIDER, CATEGORY, COMMISSION ) VALUES (?,?,?,?)";
		String updatecommission = "UPDATE TBLAIRTCOMMISSION SET ";
		String inserthistorycommission = "INSERT INTO TBLCOMMISSIONHISTORY (CREATEDBY, CATEGORY, COMMISSION,ACCOUNTNUMBER,ACCOUNTTYPE ) VALUES (?,?,?,?,'MERCHANT|CUSTOMER')";
		String history="";
		for (Object prods : products) {
				JSONObject prod = (JSONObject) prods;
				DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFAIRTCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
				if(row.isEmpty()){
					insertbld.append(insert);
					insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("baseprice").toString())+","+LongUtil.toLong(prod.get("buyingprice").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"','"+this.tariff+"')");
					insertbld.append(";\n");
				}else{
					updatebld.append(update);
					updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', BASEPRICE = "+LongUtil.toLong(prod.get("baseprice").toString())+", BUYINGPRICE = "+LongUtil.toLong(prod.get("buyingprice").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+", TARIFF = '"+this.tariff+"' WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
					updatebld.append(";\n");
				}
				DataRow rowcom = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAIRTCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ? AND TARIFF = ?",prod.get("productcode").toString(),prod.get("category").toString(),this.tariff);
				if(!rowcom.isEmpty()){
					history = "1";
					updatecommissionbld.append(updatecommission);
					updatecommissionbld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', BASEPRICE = "+LongUtil.toLong(prod.get("baseprice").toString())+", SELLINGPRICE = "+LongUtil.toLong(prod.get("buyingprice").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+" WHERE TARIFF = '"+this.tariff+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
					updatecommissionbld.append(";\n");
				}
		}
		if(history.isEmpty()){
			String sql = "BEGIN\n"
				    + insertbld
				    + updatebld
				    + inserthistory
				    + ";\n"
				    + updatecommissionbld
				    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
	
			return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue)>0;
		}else{
			String sql = "BEGIN\n"
				    + insertbld
				    + updatebld
				    + inserthistory
				    + ";\n"	
				    + updatecommissionbld
				    + inserthistorycommission
				    + ";\n"	
				    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
	
			return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue,sess.getAccount().getUserName(),this.category,this.revenue,"")>0;
		}
	}
	public boolean setrevenueemoney() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String insert = "INSERT INTO TBLTARIFFEMONEYCOMMISSION (CREATEDBY, IBAYADCODE, MINAMOUNT, MAXAMOUNT, FIXED, PERCENTAGE, PROVIDER, CATEGORY,TARIFF ) VALUES";
		String update = "UPDATE TBLTARIFFEMONEYCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONTARIFFHISTORY (CREATEDBY, PROVIDER, CATEGORY, COMMISSION ) VALUES (?,?,?,?)";
		
		for (Object prods : products) {
			JSONObject prod = (JSONObject) prods;
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFEMONEYCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
			if(row.isEmpty()){
				insertbld.append(insert);
				insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("minamount").toString())+","+LongUtil.toLong(prod.get("maxamount").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"','"+this.tariff+"')");
				insertbld.append(";\n");
			}else{
				updatebld.append(update);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', MINAMOUNT = "+LongUtil.toLong(prod.get("minamount").toString())+", MAXAMOUNT = "+LongUtil.toLong(prod.get("maxamount").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+",TARIFF = '"+this.tariff+"' WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatebld.append(";\n");
			}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue)>0;
	}
	public boolean setrevenueemoneyall() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		StringBuilder updatecommissionbld = new StringBuilder();
		String insert = "INSERT INTO TBLTARIFFEMONEYCOMMISSION (CREATEDBY, IBAYADCODE, MINAMOUNT, MAXAMOUNT, FIXED, PERCENTAGE, PROVIDER, CATEGORY,TARIFF ) VALUES";
		String update = "UPDATE TBLTARIFFEMONEYCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONTARIFFHISTORY (CREATEDBY, PROVIDER, CATEGORY, COMMISSION ) VALUES (?,?,?,?)";
		String updatecommission = "UPDATE TBLEMONEYCOMMISSION SET ";
		String inserthistorycommission = "INSERT INTO TBLCOMMISSIONHISTORY (CREATEDBY, CATEGORY, COMMISSION,ACCOUNTNUMBER,ACCOUNTTYPE ) VALUES (?,?,?,?,'MERCHANT|CUSTOMER')";
		String history="";
		for (Object prods : products) {
			JSONObject prod = (JSONObject) prods;
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFEMONEYCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
			if(row.isEmpty()){
				insertbld.append(insert);
				insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("minamount").toString())+","+LongUtil.toLong(prod.get("maxamount").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"','"+this.tariff+"')");
				insertbld.append(";\n");
			}else{
				updatebld.append(update);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', MINAMOUNT = "+LongUtil.toLong(prod.get("minamount").toString())+", MAXAMOUNT = "+LongUtil.toLong(prod.get("maxamount").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+",TARIFF = '"+this.tariff+"' WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatebld.append(";\n");
			}
			DataRow rowcom = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLEMONEYCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ? AND TARIFF = ?",prod.get("productcode").toString(),prod.get("category").toString(),this.tariff);
			if(!rowcom.isEmpty()){
				history = "1";
				updatecommissionbld.append(updatecommission);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', MINAMOUNT = "+LongUtil.toLong(prod.get("minamount").toString())+", MAXAMOUNT = "+LongUtil.toLong(prod.get("maxamount").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+" WHERE TARIFF = '"+this.tariff+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatecommissionbld.append(";\n");
			}
		}
		if(history.isEmpty()){
			String sql = "BEGIN\n"
				    + insertbld
				    + updatebld
				    + inserthistory
				    + ";\n"
				    + updatecommissionbld
				    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
	
			return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue)>0;
		}else{
			String sql = "BEGIN\n"
				    + insertbld
				    + updatebld
				    + inserthistory
				    + ";\n"
				    + updatecommissionbld
				    + inserthistorycommission
				    + ";\n"
				    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
	
			return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue,sess.getAccount().getUserName(),this.category,this.revenue,"")>0;
		}
	}
	public boolean setrevenuebills() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String insert = "INSERT INTO TBLTARIFFBILLSCOMMISSION (CREATEDBY, IBAYADCODE, FIXED, PERCENTAGE, PROVIDER, CATEGORY,TARIFF ) VALUES";
		String update = "UPDATE TBLTARIFFBILLSCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONTARIFFHISTORY (CREATEDBY, PROVIDER, CATEGORY, COMMISSION ) VALUES (?,?,?,?)";

		for (Object prods : products) {
			JSONObject prod = (JSONObject) prods;
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFBILLSCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
			if(row.isEmpty()){
				insertbld.append(insert);
				insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"','"+this.tariff+"')");
				insertbld.append(";\n");
			}else{
				updatebld.append(update);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+",TARIFF = '"+this.tariff+"' WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatebld.append(";\n");
			}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue)>0;
	}
	public boolean setrevenuebillsall() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		StringBuilder updatecommissionbld = new StringBuilder();
		String insert = "INSERT INTO TBLTARIFFBILLSCOMMISSION (CREATEDBY, IBAYADCODE, FIXED, PERCENTAGE, PROVIDER, CATEGORY,TARIFF ) VALUES";
		String update = "UPDATE TBLTARIFFBILLSCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONTARIFFHISTORY (CREATEDBY, PROVIDER, CATEGORY, COMMISSION ) VALUES (?,?,?,?)";
		String updatecommission = "UPDATE TBLBILLSCOMMISSION SET ";
		String inserthistorycommission = "INSERT INTO TBLCOMMISSIONHISTORY (CREATEDBY, CATEGORY, COMMISSION,ACCOUNTNUMBER,ACCOUNTTYPE ) VALUES (?,?,?,?,'MERCHANT|CUSTOMER')";
		String history="";
		for (Object prods : products) {
			JSONObject prod = (JSONObject) prods;
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTARIFFBILLSCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
			if(row.isEmpty()){
				insertbld.append(insert);
				insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"','"+this.tariff+"')");
				insertbld.append(";\n");
			}else{
				updatebld.append(update);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+",TARIFF = '"+this.tariff+"' WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatebld.append(";\n");
			}
			DataRow rowcom = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBILLSCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ? AND TARIFF = ?",prod.get("productcode").toString(),prod.get("category").toString(),this.tariff);
			if(!rowcom.isEmpty()){
				history = "1";
				updatecommissionbld.append(updatecommission);
				updatecommissionbld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+" WHERE  TARIFF = '"+this.tariff+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatecommissionbld.append(";\n");
			}
		}
		if(history.isEmpty()){
			String sql = "BEGIN\n"
				    + insertbld
				    + updatebld
				    + inserthistory
				    + ";\n"
				    + updatecommissionbld
				    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
	
			return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue)>0;
		}else{
			String sql = "BEGIN\n"
				    + insertbld
				    + updatebld
				    + inserthistory
				    + ";\n"
				    + updatecommissionbld
				    + inserthistorycommission
				    + ";\n"
				    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";
	
			return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,this.revenue,sess.getAccount().getUserName(),this.category,this.revenue,"")>0;
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public JSONArray getProducts() {
		return products;
	}
	public void setProducts(JSONArray products) {
		this.products = products;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
