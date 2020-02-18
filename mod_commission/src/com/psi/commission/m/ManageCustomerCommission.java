package com.psi.commission.m;

import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tlc.common.DataRow;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ManageCustomerCommission extends Model{
	protected String type;
	protected String accountnumber;
	protected String category;
	protected JSONArray products = new JSONArray();
	protected String product;
	protected String tariff;
	
	public boolean setrevenueairtime() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String df = "",dt="";
		String insert = "INSERT INTO TBLAIRTCOMMISSION (CREATEDBY, IBAYADCODE, BASEPRICE, SELLINGPRICE, FIXED, PERCENTAGE, ACCOUNTNUMBER, CATEGORY, DATEFROM, DATETO, ACCOUNTTYPE,TARIFF ) VALUES";
		String update = "UPDATE TBLAIRTCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONHISTORY (CREATEDBY, CATEGORY, DATEFROM, DATETO, COMMISSION,ACCOUNTNUMBER,ACCOUNTTYPE ) VALUES (?,?,TO_DATE(?,'YYYY-MM-DD'),TO_DATE(?,'YYYY-MM-DD'),?,?,'CUSTOMER')";
		
		for (Object prods : products) {
				JSONObject prod = (JSONObject) prods;
				df = prod.get("datefrom").toString();
				dt = prod.get("dateto").toString();
				DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAIRTCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ? AND ACCOUNTTYPE = 'CUSTOMER'",prod.get("productcode").toString(),prod.get("category").toString());
				if(row.isEmpty()){
					insertbld.append(insert);
					insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("baseprice").toString())+","+LongUtil.toLong(prod.get("buyingprice").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'','"+prod.get("category").toString()+"',TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'),TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'),'CUSTOMER','"+this.tariff+"')");
					insertbld.append(";\n");
				}else{
					updatebld.append(update);
					updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', BASEPRICE = "+LongUtil.toLong(prod.get("baseprice").toString())+", SELLINGPRICE = "+LongUtil.toLong(prod.get("buyingprice").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+", DATEFROM = TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'), DATETO = TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'),ACCOUNTTYPE = 'CUSTOMER', TARIFF = '"+this.tariff+"' WHERE ACCOUNTTYPE = 'CUSTOMER' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
					updatebld.append(";\n");
				}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.category,df,dt,this.product,"")>0;
	}
	public boolean setrevenueemoney() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String df = "",dt="";
		String insert = "INSERT INTO TBLEMONEYCOMMISSION (CREATEDBY, IBAYADCODE, MINAMOUNT, MAXAMOUNT, FIXED, PERCENTAGE, ACCOUNTNUMBER, CATEGORY, DATEFROM,DATETO,ACCOUNTTYPE,TARIFF ) VALUES";
		String update = "UPDATE TBLEMONEYCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONHISTORY (CREATEDBY, CATEGORY, DATEFROM, DATETO, COMMISSION,ACCOUNTNUMBER,ACCOUNTTYPE ) VALUES (?,?,TO_DATE(?,'YYYY-MM-DD'),TO_DATE(?,'YYYY-MM-DD'),?,?,'CUSTOMER')";
		
		for (Object prods : products) {
			JSONObject prod = (JSONObject) prods;
			df = prod.get("datefrom").toString();
			dt = prod.get("dateto").toString();
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLEMONEYCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ? AND ACCOUNTTYPE = 'CUSTOMER'",prod.get("productcode").toString(),prod.get("category").toString());
			if(row.isEmpty()){
				insertbld.append(insert);
				insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("minamount").toString())+","+LongUtil.toLong(prod.get("maxamount").toString())+","+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'','"+prod.get("category").toString()+"',TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'),TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'),'CUSTOMER','"+this.tariff+"')");
				insertbld.append(";\n");
			}else{
				updatebld.append(update);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', MINAMOUNT = "+LongUtil.toLong(prod.get("minamount").toString())+", MAXAMOUNT = "+LongUtil.toLong(prod.get("maxamount").toString())+", FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+", DATEFROM = TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'), DATETO = TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'),ACCOUNTTYPE = 'CUSTOMER', TARIFF = '"+this.tariff+"' WHERE ACCOUNTTYPE = 'CUSTOMER' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatebld.append(";\n");
			}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.category,df,dt,this.product,"")>0;
	}
	public boolean setrevenuebills() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder insertbld = new StringBuilder();
		StringBuilder updatebld = new StringBuilder();
		String df = "",dt="";
		String insert = "INSERT INTO TBLBILLSCOMMISSION (CREATEDBY, IBAYADCODE, FIXED, PERCENTAGE, ACCOUNTNUMBER, CATEGORY, DATEFROM, DATETO,ACCOUNTTYPE,TARIFF ) VALUES";
		String update = "UPDATE TBLBILLSCOMMISSION SET ";
		String inserthistory = "INSERT INTO TBLCOMMISSIONHISTORY (CREATEDBY, CATEGORY, DATEFROM, DATETO, COMMISSION,ACCOUNTNUMBER,ACCOUNTTYPE ) VALUES (?,?,TO_DATE(?,'YYYY-MM-DD'),TO_DATE(?,'YYYY-MM-DD'),?,?,'CUSTOMER')";
		
		for (Object prods : products) {
			JSONObject prod = (JSONObject) prods;
			df = prod.get("datefrom").toString();
			dt = prod.get("dateto").toString();
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBILLSCOMMISSION WHERE IBAYADCODE = ? AND CATEGORY= ? AND ACCOUNTTYPE = 'CUSTOMER'",prod.get("productcode").toString(),prod.get("category").toString());
			if(row.isEmpty()){
				insertbld.append(insert);
				insertbld.append("('"+sess.getAccount().getUserName()+"','"+prod.get("productcode").toString()+"',"+LongUtil.toLong(prod.get("fixed").toString())+","+LongUtil.toLong(prod.get("percent").toString())+",'','"+prod.get("category").toString()+"',TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'),TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'),'CUSTOMER','"+this.tariff+"')");
				insertbld.append(";\n");
			}else{
				updatebld.append(update);
				updatebld.append(" MODIFIEDDATE=SYSDATE, MODIFIEDBY = '"+sess.getAccount().getUserName()+"', FIXED = "+LongUtil.toLong(prod.get("fixed").toString())+",PERCENTAGE = "+LongUtil.toLong(prod.get("percent").toString())+", DATEFROM = TO_DATE('"+prod.get("datefrom")+"','YYYY-MM-DD'), DATETO = TO_DATE('"+prod.get("dateto")+"','YYYY-MM-DD'),ACCOUNTTYPE = 'CUSTOMER', TARIFF = '"+this.tariff+"' WHERE ACCOUNTTYPE = 'CUSTOMER' AND CATEGORY = '"+prod.get("category").toString()+"' AND IBAYADCODE = '"+prod.get("productcode").toString()+"' ");
				updatebld.append(";\n");
			}
		}
		String sql = "BEGIN\n"
			    + insertbld
			    + updatebld
			    + inserthistory
			    + ";\n"
			    + "COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n";

		return SystemInfo.getDb().QueryUpdate(sql.toString(),sess.getAccount().getUserName(),this.category,df,dt,this.product,"")>0;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
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
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff;
	}
}
