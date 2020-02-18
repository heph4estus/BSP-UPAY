package com.psi.wallet.branch.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.wallet.util.HttpClientHelper;
import com.psi.wallet.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class PayoutCollection extends Model{
	protected String id;

	public static final String PROP_DATA = "DATA";
	public String data;
	

	@SuppressWarnings("unchecked")
	public boolean reports() throws ParseException, IOException, java.text.ParseException{
		OtherProperties prop = new OtherProperties();

	    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AI.PASSWORD,?,AI.ACCOUNTNUMBER) PASSWORD,U.USERNAME,AI.ROOT,U.ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE U.USERID = ?",SystemInfo.getDb().getCrypt(), this.id);

	    Logger.LogServer("URL PENDING PAYOUT COLLECTION:"+prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/payout/request/pending");
	    HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpGet(prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/payout/request/pending", null, headers, null);
	    Logger.LogServer(" response : " + new String(apiResponse));

	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    		this.setData(object.get("data").toString());
		    		this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
					return true;
		    
			  } else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
				  {
					  this.setState(new ObjectState("99",object.get("message").toString()));
				    	return false;
					  }
				  }
		    else{
				   this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
			    	return false;
			  }
	    }else{
	    	this.setState(new ObjectState("99","System is busy"));
	    	return false;
	    }
	}
	
	public boolean reportscompleted() throws ParseException, IOException, java.text.ParseException{
		OtherProperties prop = new OtherProperties();

	    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AI.PASSWORD,?,AI.ACCOUNTNUMBER) PASSWORD,U.USERNAME,AI.ROOT,U.ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE U.USERID = ?",SystemInfo.getDb().getCrypt(), this.id);

	    Logger.LogServer("URL COMPLETED PAYOUT COLLECTION:"+prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/payout/request/completed");
	    HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpGet(prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/payout/request/completed", null, headers, null);
	    Logger.LogServer(" response : " + new String(apiResponse));

	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    		this.setData(object.get("data").toString());
		    		this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
					return true;
		    
			  } else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
				  {
					  this.setState(new ObjectState("99",object.get("message").toString()));
				    	return false;
					  }
				  }
		    else{
				   this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
			    	return false;
			  }
	    }else{
	    	this.setState(new ObjectState("99","System is busy"));
	    	return false;
	    }
	}
	public boolean reportsrejected() throws ParseException, IOException, java.text.ParseException{
		OtherProperties prop = new OtherProperties();

	    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AI.PASSWORD,?,AI.ACCOUNTNUMBER) PASSWORD,U.USERNAME,AI.ROOT,U.ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE U.USERID = ?",SystemInfo.getDb().getCrypt(), this.id);

	    Logger.LogServer("URL PAYOUT COLLECTION:"+prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/payout/request/rejected");
	    HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpGet(prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/payout/request/rejected", null, headers, null);
	    Logger.LogServer(" response : " + new String(apiResponse));

	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    		this.setData(object.get("data").toString());
		    		this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
					return true;
		    
			  } else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
				  {
					  this.setState(new ObjectState("99",object.get("message").toString()));
				    	return false;
					  }
				  }
		    else{
				   this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
			    	return false;
			  }
	    }else{
	    	this.setState(new ObjectState("99","System is busy"));
	    	return false;
	    }
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		 this.props.put("DATA", data);
		this.data = data;
	}


	
	
	
	
	
	

}
