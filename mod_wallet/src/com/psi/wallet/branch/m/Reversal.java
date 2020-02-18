package com.psi.wallet.branch.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.wallet.util.EmailUtils;
import com.psi.wallet.util.HttpClientHelper;
import com.psi.wallet.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class Reversal extends Model{
	
	protected String referenceid;
	protected String remarks;
	protected String amount;
	protected String password;
	protected String linkid;
	
	@SuppressWarnings("unchecked")
	public boolean topup() throws ParseException, IOException, java.text.ParseException{
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		UISession sess = this.getAuthorizedSession();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(1312) FROM DUAL", "0");
		String accountnumber = SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERID =?", "", this.linkid);
		DataRow customer = SystemInfo.getDb().QueryDataRow("SELECT EMAIL,TOACCOUNT FROM TBLUSERS U INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON U.ACCOUNTNUMBER = T.TOACCOUNT WHERE REFERENCEID =?", this.referenceid);
		String amount = SystemInfo.getDb().QueryScalar("SELECT AMOUNT FROM ADMDBMC.TBLTRANSACTIONS WHERE REFERENCEID=?", "", this.referenceid);
		if(this.amount.equals("0") || this.amount.equals(0) || this.amount.equals("0.00") || this.amount.equals(0.00)){
			Logger.LogServer("pasok amount 0");
			
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token",prop.getToken());
		    headers.put("X-Forwarded-For","127.0.0.1");
		    
		    byte[] apiResponse = client.httpDelete(prop.getUrl()+accountnumber+ "/transfers" + "?request-id="+reqid+"&previous="+this.referenceid, null, headers, null);
		    Logger.LogServer("url: " + prop.getUrl()+accountnumber+ "/transfers" + "?request-id="+reqid+"&previous="+this.referenceid);
		    Logger.LogServer(" response : " + new String(apiResponse));
		    JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    
		    if(apiResponse.length>0){
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			    		int res = SystemInfo.getDb().QueryUpdate("INSERT INTO TBLREVERSALTRANSACTIONS (TIMESTAMP,MSISDN,VOIDREFERENCEID,REFERENCEID,STATUS,CASHIER,ACCOUNTNUMBER,PREVIOUSAMOUNT,AMOUNT,MESSAGE,FEE,REMARKS) VALUES(SYSDATE,?,?,?,'COMPLETED',?,?,?,?,?,0,?)", 
			    						customer.getString("TOACCOUNT"),this.referenceid,object.get("response-id").toString(),sess.getAccount().getUserName(),accountnumber,amount,0,object.get("message").toString(), this.remarks);
			    		if(res>0){	
			    			DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLTRANSACTIONS WHERE REFERENCEID=?", "", this.referenceid);
			    			
			    			EmailUtils.sendreversal(SystemInfo.getDb().QueryScalar("SELECT LISTAGG(EMAIL,';') WITHIN GROUP(ORDER BY EMAIL)  EMAIL FROM TBLUSERS WHERE ACCOUNTNUMBER=? AND USERSLEVEL IN ('MANAGER','BUSINESSOWNER','CUSTOMER')", "", rr.getString("FRACCOUNT")),
			    									this.referenceid, rr.getString("FRALIAS"), sess.getAccount().getUsername(),LongUtil.toString(Long.parseLong(amount)));
			    			EmailUtils.sendreversaloperator(this.referenceid, rr.getString("FRALIAS"), sess.getAccount().getUsername(),LongUtil.toString(Long.parseLong(amount)));
							this.setState(new ObjectState("00",object.get("message").toString()));
							return true;
						}else{
							 this.setState(new ObjectState("01","Unable to process reversal"));
							 Logger.LogServer("Failed");
							 return false;
						}
			    
				  } else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
					  {
						  this.setState(new ObjectState("05","Sorry, we are unable to process your request. Please check if you have sufficient balance"));
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
		else{
			Logger.LogServer("pasok amount hindi 0");
					
			StringEntity entity = new StringEntity(request.toJSONString());
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token",prop.getToken());
		    headers.put("X-Forwarded-For","127.0.0.1");
		    
		    byte[] apiResponse = client.httpDelete(prop.getUrl()+accountnumber+"/transfers?request-id="+reqid+"&previous="+this.referenceid+"&amount="+this.amount, null, headers, null);
		    Logger.LogServer("url : " + prop.getUrl()+accountnumber+"/transfers?request-id="+reqid+"&previous="+this.referenceid+"&amount="+this.amount);
		    Logger.LogServer("reversal response : " + new String(apiResponse));
		    JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    
		    if(apiResponse.length>0){	    	
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			    		
			    	int res = SystemInfo.getDb().QueryUpdate("INSERT INTO TBLREVERSALTRANSACTIONS (TIMESTAMP,MSISDN,VOIDREFERENCEID,REFERENCEID,STATUS,CASHIER,ACCOUNTNUMBER,PREVIOUSAMOUNT,AMOUNT,MESSAGE,FEE,REMARKS) VALUES(SYSDATE,?,?,?,'COMPLETED',?,?,?,?,?,0,?)", 
    						customer.getString("TOACCOUNT"),this.referenceid,object.get("response-id").toString(),sess.getAccount().getUserName(),accountnumber,amount,LongUtil.toLong(this.amount),object.get("message").toString(), this.remarks);
    
			    	    if(res>0){			    	    								
							this.setState(new ObjectState("00","Successfully Approve Adjustment Request"));
							Logger.LogServer("Success");
							return true;
						}else{
							 this.setState(new ObjectState("01","Unable to process reversal"));
							 Logger.LogServer("Failed");
							 return false;
						}
			    
				  } else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
					  {
						  this.setState(new ObjectState("05","Sorry, we are unable to process your request. Please check if you have sufficient balance"));
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
		

	}
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.linkid,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean isExist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLREVERSALTRANSACTIONS WHERE VOIDREFERENCEID =?", this.referenceid).size()>0;
	}
	public boolean isapproved(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLREVERSALTRANSACTIONS WHERE VOIDREFERENCEID=? AND STATUS='APPROVED' ", this.referenceid).size()>0;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLinkid() {
		return linkid;
	}
	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReferenceid() {
		return referenceid;
	}
	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	
}
