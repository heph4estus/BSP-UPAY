package com.psi.keyaccount.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.keyaccount.util.HttpClientHelper;
import com.psi.keyaccount.util.KeyAccounts;
import com.psi.keyaccount.util.OtherProperties;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;

public class NewKeyAccount extends KeyAccounts{
	public static final String PROP_ACCOUNTNUMBER="ACCOUNTNUMBER";
	protected String accountnumber;
	
	@SuppressWarnings("unchecked")
	public boolean register() throws IOException, ParseException{
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONObject request4 = new JSONObject();
		JSONObject request5 = new JSONObject();
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(3500) FROM DUAL", "0");
	    		
		request.put("request-id", reqid);
		request.put("user-id", "POLEN");
			request2.put("password", "PASSWORD");
		request.put("auth", request2);
				request3.put("password", "1234");
				request3.put("account-name", this.accountname);
				request3.put("business-name", this.businessname);
				request3.put("first-name", this.firstname);
				request3.put("middle-name", this.middlename);
				request3.put("last-name", this.lastname);
					request4.put("registration-date", this.regdate);
					request4.put("tin", this.tinno);
						request5.put("region-code", this.region);
						request5.put("city-code", this.city);
						request5.put("postal-code", this.postalcode);
						request5.put("coordinates", this.xcoordinate +","+this.ycoordinate);
						request5.put("specific-address", this.specificaddress);
					request4.put("business-address", request5);
				request3.put("corporate-info", request4);
				request3.put("authorized-mobile", this.msisdn);
				request3.put("valid-id", this.email);
				request3.put("valid-id-desc","EMAIL ADDRESS");
		request.put("subscriber", request3);
		
		Logger.LogServer("business request:"+request.toString());
		Logger.LogServer("url:"+prop.getUrl()+"ibayad/keyaccounts");
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpPost(prop.getUrl()+"ibayad/keyaccounts", null, headers, null, entity);

	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    	StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("INSERT INTO TBLKEYACCOUNT(BUSINESSNAME,ACCOUNTNUMBER,ADDRESS,ACCOUNTNAME,CITY,CONTACTNUMBER,PROVINCE,XORDINATES,YORDINATES,ZIPCODE,COUNTRY,FIRSTNAME,MIDDLENAME,LASTNAME,REGDATE,TINNUMBER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); \n");
				query.append("INSERT INTO TBLUSERSLEVEL(USERSLEVEL,SESSIONTIMEOUT,PASSWORDCHANGE,PASSWORDEXPIRY,MINPASSWORD,PASSWORDHISTORY,MAXALLOCUSER,SEARCHRANGE,NEWPASSWORDEXPIRY,ACCOUNTSTATUS,HOMEPAGE,ISOPERATOR) VALUES(?,?,?,?,?,?,?,?,?,?,?,?); \n");
				query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,ACCOUNTNUMBER,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL) VALUES(?,?,?,?,'MANAGER','ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39'); \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				SystemInfo.getDb().QueryUpdate(query.toString(),this.businessname, object.get("account-number").toString(), this.specificaddress,this.accountname,this.city,this.msisdn,this.region,this.xcoordinate,this.ycoordinate,this.postalcode,this.country,this.firstname,this.middlename,this.lastname,this.regdate,this.tinno
														,this.accountname,"10","0","10","6","0","10","10","10","ACTIVE","myaccount","0"
														,this.email, this.firstname, this.lastname,this.msisdn,this.accountname,object.get("account-number").toString(),"123456789",SystemInfo.getDb().getCrypt(),this.accountname
					 									  );		
		    	this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
		    	return true;
			  }else{
			    this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
		    	return false;
			  }
	    }else{
	    	this.setState(new ObjectState("99","System is busy"));
	    	return false;
	    }
	    
		
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLKEYACCOUNT WHERE ACCOUNTNAME=? ", this.accountname).size()>0;
	}

	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.props.put(PROP_ACCOUNTNUMBER,accountnumber);
		this.accountnumber = accountnumber;
	}
}
