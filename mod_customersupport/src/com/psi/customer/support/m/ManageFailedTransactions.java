package com.psi.customer.support.m;

import java.text.ParseException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class ManageFailedTransactions extends Model{
	protected String traceid;
	protected String referenceid;
	protected String accountnumber;
	protected String receiptvalidation;
	protected String remarks;
	protected String username;
	protected String password;
	
	@SuppressWarnings("unchecked")
	public boolean voidtrans() throws ParseException, Exception{
		OtherProperties prop = new OtherProperties();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(9000) FROM DUAL", "0");
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		
		request.put("request-id", reqid);
	    request2.put("business",SystemInfo.getDb().QueryScalar("SELECT DECRYPT(PASSWORD,?,ACCOUNTNUMBER) PASSWORD FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?","", SystemInfo.getDb().getCrypt(),this.accountnumber) );
	    request.put("auth", request2);

	    StringEntity entity = new StringEntity(request.toJSONString());
		Logger.LogServer("void request:"+request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", prop.getType());
	    headers.put("token", prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    //"http://192.168.1.20:8080/hermes/core/svc/"
		byte[] apiResponse = client.httpDeleteWithBody(prop.getUrl()+this.accountnumber+"/transactions/"+this.referenceid, entity, headers, null, null);
		Logger.LogServer("void url:"+prop.getUrl()+this.accountnumber+"/transactions/"+this.referenceid);
		Logger.LogServer("void response:"+new String(apiResponse, "UTF-8"));
			    
		if(apiResponse.length>0){
			JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
			if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
				this.setState(new ObjectState("00",object.get("message").toString()));
				return true;
			}else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
				this.setState(new ObjectState("05",object.get("message").toString()));
				return false;
			}else{
				this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
				return false;
			}
		}else{
			this.setState(new ObjectState("99","System is busy"));
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean fulfill() throws ParseException, Exception {
		OtherProperties prop = new OtherProperties();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(9000) FROM DUAL", "0");
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONArray jarray = new JSONArray();
		
		request.put("request-id", reqid);
		request.put("remarks", this.remarks);
	    request2.put("trace-id",this.traceid);
	    request2.put("receipt-validation", this.receiptvalidation);
	    request2.put("response-id", this.referenceid);
	    jarray.add(request2);
	    request.put("values", jarray);
		
		StringEntity entity = new StringEntity(request.toJSONString());
		Logger.LogServer("fulfillment request:"+request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", prop.getType());
	    headers.put("token", prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
			    //"http://192.168.1.20:8080/hermes/core/svc/"
		byte[] apiResponse = client.httpPut(prop.getUrl()+this.accountnumber+"/fulfillments/"+this.referenceid, null, headers, null, entity);
		Logger.LogServer("fulfillment url:"+prop.getUrl()+this.accountnumber+"/fulfillments/"+this.referenceid);
		Logger.LogServer("fulfillment response:"+new String(apiResponse, "UTF-8"));
			    
		if(apiResponse.length>0){
			JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
			if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
				this.setState(new ObjectState("00",object.get("message").toString()));
				return true;
			}else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
				this.setState(new ObjectState("05",object.get("message").toString()));
				return false;
			}else{
				this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
				return false;
			}
		}else{
			this.setState(new ObjectState("99","System is busy"));
			return false;
		}
}

	@SuppressWarnings("unchecked")
	public boolean reprocess() throws ParseException, Exception {
		OtherProperties prop = new OtherProperties();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(9000) FROM DUAL", "0");
		String url = SystemInfo.getDb().QueryScalar("SELECT URL FROM TBLFAILEDTRANSACTIONS WHERE REFERENCEID = ?", "", this.referenceid);
		String pass = SystemInfo.getDb().QueryScalar("SELECT PASSWORD FROM TBLPOSUSERS WHERE USERID = ?", "", this.username);
		JSONParser parser = new JSONParser();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLFULLFILLMENTPNDG WHERE REFERENCEID = ?", this.referenceid);
		JSONObject req = (JSONObject) parser.parse(row.getString("PAYMENTDATA"));
		JSONObject paymentd = (JSONObject) parser.parse(req.get("payment-data").toString());
		JSONObject auth = new JSONObject();
		req.put("request-id", reqid);
		auth.put("business", pass);
		req.put("auth", auth);
		if(paymentd.containsKey("force-timeout")){
			paymentd.remove("force-timeout");
		}

		paymentd.put("cashier-id", this.username);
		paymentd.put("terminal-id", SystemInfo.getDb().QueryScalar("SELECT TERMINAL FROM TBLUSERS WHERE USERNAME = ?", "", this.username));
		paymentd.put("branch-id", SystemInfo.getDb().QueryScalar("SELECT BRANCHCODE FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?", "", SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERNAME = ?", "", this.username)));
		req.put("payment-data", paymentd);
		StringEntity entity = new StringEntity(req.toJSONString());
		Logger.LogServer("reprocess request:"+req.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", prop.getType());
	    headers.put("token", prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
			    //"http://192.168.1.20:8080/hermes/core/svc/"
		byte[] apiResponse = client.httpPost(prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERNAME = ?", "", this.username)+"/"+url+"?reprocess="+this.referenceid, null, headers, null, entity);
		Logger.LogServer("reprocess url:"+prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERNAME = ?", "", this.username)+"/"+url+"?reprocess="+this.referenceid);
		Logger.LogServer("reprocess response:"+new String(apiResponse, "UTF-8"));
			    
		if(apiResponse.length>0){
			JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
			if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
				this.setState(new ObjectState("00",object.get("message").toString()));
				return true;
			}else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
				this.setState(new ObjectState("05",object.get("message").toString()));
				return false;
			}else{
				this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
				return false;
			}
		}else{
			this.setState(new ObjectState("99","System is busy"));
			return false;
		}
}
	public boolean isallow(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ? AND USERSLEVEL='CASHIER'", this.username).size()>0;
	}
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.username,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public String getTraceid() {
		return traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getReferenceid() {
		return referenceid;
	}
	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getReceiptvalidation() {
		return receiptvalidation;
	}
	public void setReceiptvalidation(String receiptvalidation) {
		this.receiptvalidation = receiptvalidation;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}

