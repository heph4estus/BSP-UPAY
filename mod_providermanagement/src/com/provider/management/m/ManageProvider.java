package com.provider.management.m;

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

public class ManageProvider extends Model{
	protected String type;
	protected String provider;
	protected String category;
	protected JSONArray products = new JSONArray();
	protected String revenue;
	
	public boolean setrevenueairtime() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String df = "",dt="";
		String insert = "INSERT INTO TBL3PPAIRTREVENUE (CREATEDBY, IBAYADCODE, BASEPRICE, BUYINGPRICE, FIXED, PERCENTAGE, PROVIDER, CATEGORY, DATEFROM, DATETO ) VALUES";
		String update = "UPDATE TBL3PPAIRTREVENUE SET ";
		String inserthistory = "INSERT INTO TBL3PPREVENUEHISTORY (CREATEDBY, PROVIDER, CATEGORY, DATEFROM, DATETO, COMMISSION ) VALUES (?,?,?,TO_DATE(?,'YYYY-MM-DD'),TO_DATE(?,'YYYY-MM-DD'),?)";
		for (Object prods : products) {
				JSONObject prod = (JSONObject) prods;
				df = prod.get("datefrom").toString();
				dt = prod.get("dateto").toString();
				DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBL3PPAIRTREVENUE WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
				if(row.isEmpty()){
					insertbld.append(insert);
					insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("baseprice").toString())+","+LongUtil.toLong(prod.get("buyingprice").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"',TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'),TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'))");
					insertbld.append(";\n");
				}else{
					updatebld.append(update);
					updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', BASEPRICE = "+LongUtil.toLong(prod.get("baseprice").toString())+", BUYINGPRICE = "+LongUtil.toLong(prod.get("buyingprice").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+", DATEFROM = TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'), DATETO = TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD') WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
					updatebld.append(";\n");
				}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,df,dt,this.revenue)>0;
	}
	public boolean setrevenueemoney() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String df = "",dt="";
		String insert = "INSERT INTO TBL3PPEMONEYREVENUE (CREATEDBY, IBAYADCODE, MINAMOUNT, MAXAMOUNT, FIXED, PERCENTAGE, PROVIDER, CATEGORY, DATEFROM,DATETO ) VALUES";
		String update = "UPDATE TBL3PPEMONEYREVENUE SET ";
		String inserthistory = "INSERT INTO TBL3PPREVENUEHISTORY (CREATEDBY, PROVIDER, CATEGORY, DATEFROM, DATETO, COMMISSION ) VALUES (?,?,?,TO_DATE(?,'YYYY-MM-DD'),TO_DATE(?,'YYYY-MM-DD'),?)";

		for (Object prods : products) {
			JSONObject prod = (JSONObject) prods;
			df = prod.get("datefrom").toString();
			dt = prod.get("dateto").toString();
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBL3PPEMONEYREVENUE WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
			if(row.isEmpty()){
				insertbld.append(insert);
				insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("minamount").toString())+","+LongUtil.toLong(prod.get("maxamount").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"',TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'),TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'))");
				insertbld.append(";\n");
			}else{
				updatebld.append(update);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', MINAMOUNT = "+LongUtil.toLong(prod.get("minamount").toString())+", MAXAMOUNT = "+LongUtil.toLong(prod.get("maxamount").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+", DATEFROM = TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'), DATETO = TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD') WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatebld.append(";\n");
			}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,df,dt,this.revenue)>0;
	}
	public boolean setrevenuebills() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String df = "",dt="";
		String insert = "INSERT INTO TBL3PPBILLSREVENUE (CREATEDBY, IBAYADCODE, FIXED, PERCENTAGE, PROVIDER, CATEGORY, DATEFROM, DATETO ) VALUES";
		String update = "UPDATE TBL3PPBILLSREVENUE SET ";
		String inserthistory = "INSERT INTO TBL3PPREVENUEHISTORY (CREATEDBY, PROVIDER, CATEGORY, DATEFROM, DATETO, COMMISSION ) VALUES (?,?,?,TO_DATE(?,'YYYY-MM-DD'),TO_DATE(?,'YYYY-MM-DD'),?)";

		for (Object prods : products) {
			JSONObject prod = (JSONObject) prods;
			df = prod.get("datefrom").toString();
			dt = prod.get("dateto").toString();
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBL3PPBILLSREVENUE WHERE IBAYADCODE = ? AND CATEGORY= ?",prod.get("productcode").toString(),prod.get("category").toString());
			if(row.isEmpty()){
				insertbld.append(insert);
				insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'"+this.provider+"','"+prod.get("category").toString()+"',TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'),TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'))");
				insertbld.append(";\n");
			}else{
				updatebld.append(update);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+", DATEFROM = TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'), DATETO = TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD') WHERE PROVIDER = '"+this.provider+"' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatebld.append(";\n");
			}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.provider,this.category,df,dt,this.revenue)>0;
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
	
	
}
