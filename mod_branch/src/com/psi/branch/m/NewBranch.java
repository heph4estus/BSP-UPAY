package com.psi.branch.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.branch.utils.Branch;
import com.psi.branch.utils.HttpClientHelper;
import com.psi.branch.utils.OtherProperties;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class NewBranch extends Branch{
	public static final String PROP_ACCOUNTNUMBER="ACCOUNTNUMBER";
	protected String accountnumber;
	
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
				request3.put("account-name", this.branchname);
				request3.put("business-name", this.branchname);
				request3.put("first-name", this.branchname);
				request3.put("middle-name", this.branchname);
				request3.put("last-name", this.branchname);
				request3.put("authorized-mobile", this.contactnumber);
				request3.put("valid-id", "BAYAD"+code);
					request4.put("registration-date", date);
					request4.put("tin", this.tin);
						request5.put("region-code", this.province);
						request5.put("city-code", this.city);
						request5.put("postal-code", this.zipcode);
						request5.put("coordinates", this.xordinate +","+this.yordinate);
						request5.put("specific-address", this.specificaddress);
					request4.put("business-address", request5);
				request3.put("corporate-info", request4);
		request.put("subscriber", request3);
		
		Logger.LogServer(request.toString());
		Logger.LogServer("Merch Reg URL:"+prop.getUrl()+acct+prop.getBranch_url());
		
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    byte[] apiResponse = client.httpPost(prop.getUrl()+acct+prop.getBranch_url(), null, headers, null, entity);
	    
	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("100") || object.get("code").toString().equals(100)){
		    	String accts = SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE MSISDN = ? ORDER BY REGDATE DESC", "", this.contactnumber);
		    	StringBuilder query = new StringBuilder("BEGIN\n");
		    	query.append("INSERT INTO TBLMERCHANTPNDG(ACCOUNTNUMBER,REQUESTS,STATUS,CREATEDBY,IMAGE,ACCOUNTTYPE,BUSINESSACCOUNT) VALUES(?,?,?,?,?,'BRANCH',?); \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				SystemInfo.getDb().QueryUpdate(query.toString(),accts,this.json(this),"PNDG",sess.getAccount().getUsername(),this.image,this.accountnumber);
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE BRANCH=? ", this.branchname).size()>0;
	}
	public boolean existpndgbusiness(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE FQN = ENCRYPT(?,?,ACCOUNTNUMBER) ", this.branchname,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean existmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN=? ", this.contactnumber).size()>0;
	}

	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.props.put(PROP_ACCOUNTNUMBER,accountnumber);
		this.accountnumber = accountnumber;
	}


	
}
