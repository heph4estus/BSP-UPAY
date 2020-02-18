package com.psi.business.v2.m;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import com.psi.business.util.Business;
import com.psi.business.util.EmailUtils;
import com.psi.business.util.HttpClientHelper;
import com.psi.business.util.OtherProperties;
import com.psi.business.util.RedisClient;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class NewBusiness extends Business{
	public static final String PROP_ACCOUNTNUMBER="ACCOUNTNUMBER";
	protected String accountnumber;
	static RedisClient redis = new RedisClient();
	@SuppressWarnings("unchecked")
	public boolean register(String acct) throws IOException, ParseException{
		UISession sess = this.getAuthorizedSession();
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONObject request4 = new JSONObject();
		JSONObject request5 = new JSONObject();
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(4001) FROM DUAL", "0");	    
	    String code = SystemInfo.getDb().QueryScalar("SELECT TBLBRANCHESCODE_SEQ.NEXTVAL FROM DUAL", "0");
	    String date = SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", "0");
	   		
		request.put("type", "merchant");
		request.put("request-id", reqid);
		request.put("user-id", "POLEN");
		request.put("valid-id-desc","COMPANY");
		request.put("for-kyc", "true");
		request.put("kyc", "true");
			request2.put("password", "1234");
		request.put("auth", request2);
				request3.put("password", "1234");
				request3.put("account-name", this.businessname);
				request3.put("business-name", this.businessname);
				request3.put("first-name", this.ownerfirstname);
				request3.put("middle-name", this.ownermiddlename);
				request3.put("last-name", this.ownerlastname);
				request3.put("authorized-mobile", this.msisdn);
				request3.put("valid-id", "BAYAD"+code);
					request4.put("registration-date", date);
					request4.put("tin", this.tin);
						request5.put("region-code", this.province);
						request5.put("city-code", this.city);
						request5.put("postal-code", this.zipcode);
						request5.put("coordinates", this.xcoordinate +","+this.ycoordinate);
						request5.put("specific-address", this.specificaddress);
					request4.put("business-address", request5);
				request3.put("corporate-info", request4);
		request.put("subscriber", request3);
		
		Logger.LogServer(request.toString());
		Logger.LogServer("Merch Reg URL:"+prop.getUrl()+acct+"/corporates");
		
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    byte[] apiResponse = client.httpPost(prop.getUrl()+acct+"/corporates", null, headers, null, entity);
	    
	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		    ArrayList params = new ArrayList();
		    if(object.get("code").toString().equals("100") || object.get("code").toString().equals(100)){
		    	String accts = SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE MSISDN = ? ORDER BY REGDATE DESC", "", this.msisdn);
		    	StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("INSERT INTO TBLMERCHANTPNDG(ACCOUNTNUMBER,REQUESTS,STATUS,CREATEDBY,IMAGE) VALUES(?,?,?,?,?); \n");
				params.addAll(Arrays.asList(new String[] { 
						accts,this.json(this),
						"PNDG",
						sess.getAccount().getUsername(),
						this.image }));
				JSONArray images = new JSONArray();
				if(redis.exists("/"+this.msisdn+"/required/doc")){
					String json = redis.get("/"+this.msisdn+"/required/doc");
					JSONParser p = new JSONParser();
					try {
						images = (JSONArray) p.parse(json);
						for (int i = 0; i < images.size(); i++) {
						    JSONObject jsonobject = (org.json.simple.JSONObject) images.get(i);
						    if (!jsonobject.isEmpty()) {
						    	String file = jsonobject.get("Document").toString();
						    	String type = jsonobject.get("Type").toString();
						    	query.append("INSERT INTO TBLDOCUMENTS (ACCOUNTNUMBER,DOCTYPE,DOCUMENT,DATEREGISTERED) VALUES(?,?,?,SYSDATE);\n");
						    	params.addAll(Arrays.asList(new String[] { 
						    			accts, 
								          type, 
								          file }));
						    }
						}
						
						
					} catch (ParseException e) {
						Logger.LogServer(e);
					}
				}
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				SystemInfo.getDb().QueryUpdate(query.toString(),params.toArray());		
				EmailUtils.sendMechantCreation(this.businessname, this.ownerfirstname, this.ownerlastname);
				this.setAccountnumber(accts);
		    	this.setState(new ObjectState("00",object.get("message").toString()));
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE FQN = ENCRYPT(?,?,ACCOUNTNUMBER) ", this.businessname,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean existmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN=? ", this.msisdn).size()>0;
	}
	public boolean existauthmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN=? ", this.authmsisdn).size()>0;
	}
	public boolean existauthemail(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL)=? ", this.authemail.toUpperCase()).size()>0;
	}
	public boolean existauthusername(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME)=? ", this.username.toUpperCase()).size()>0;
	}
	public boolean existpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG A INNER JOIN TBLMERCHANTPNDG M ON A.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE A.FQN = ENCRYPT(?,?,A.ACCOUNTNUMBER) AND M.STATUS = 'PNDG'", this.businessname,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean existmsisdnpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG A INNER JOIN TBLMERCHANTPNDG M ON A.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE A.MSISDN=? AND M.STATUS = 'PNDG' ", this.msisdn).size()>0;
	}
	public boolean existauthmsisdnpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE REQUESTS LIKE '%'||?||'%' AND STATUS = 'PNDG'", this.authmsisdn).size()>0;
	}
	public boolean existauthemailpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE UPPER(REQUESTS) LIKE '%'||?||'%' AND STATUS = 'PNDG'", this.authemail.toUpperCase()).size()>0;
	}
	public boolean existauthusernamepndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE REQUESTS LIKE '%'||?||'%' AND STATUS = 'PNDG'", this.username.toUpperCase()).size()>0;
	}
	public boolean existemail(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE UPPER(EMAIL)=UPPER(?) ", this.email).size()>0;
	}
	public boolean existemaildetails(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTDETAILS WHERE UPPER(EMAIL)=UPPER(?) ", this.email).size()>0;
	}
	public boolean existemailpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG A INNER JOIN TBLMERCHANTPNDG M ON A.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE UPPER(M.REQUESTS) LIKE ? AND M.STATUS = 'PNDG' ", "%"+this.email+"%").size()>0;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.props.put(PROP_ACCOUNTNUMBER,accountnumber);
		this.accountnumber = accountnumber;
	}


	
}
