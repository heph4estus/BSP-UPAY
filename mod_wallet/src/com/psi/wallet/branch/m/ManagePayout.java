package com.psi.wallet.branch.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ibayad.transmitters.client.SmsMessage;
import com.psi.wallet.util.EmailUtils;
import com.psi.wallet.util.HttpClientHelper;
import com.psi.wallet.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class ManagePayout extends Model{
	protected String id;
	protected String linkid;
	protected String password;
	protected String depochannel;
	protected String remarks;
	protected String image;
	protected String requestby;
	protected String amount;
	public boolean approved() throws ParseException, IOException{
		
		OtherProperties prop = new OtherProperties();		
		UISession sess = this.getAuthorizedSession();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(1322) FROM DUAL", "0");
		Logger.LogServer(" approve payout url : PUT " + prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERID = ?", "",this.linkid )+"/payout/request/"+this.id+"?request-id="+reqid);			  

		String auth = sess.getAccount().getUserName()+":"+this.password.toString();
			byte[] encodedToken = Base64.encodeBase64(auth.toString().getBytes());
	        String uPPasswordString = new String(encodedToken);
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("Authorization", "Basic "+uPPasswordString);
		    headers.put("X-Forwarded-For", "127.0.0.1");
		    byte[] apiResponse = client.httpPut(prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERID = ?", "",this.linkid )+"/payout/request/"+this.id+"?request-id="+reqid, null, headers, null, null);
		    Logger.LogServer(" approve payout response : " + new String(apiResponse));
		    if(apiResponse.length>0){
		    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    			   
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
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
	public boolean reject() throws ParseException, IOException{
		OtherProperties prop = new OtherProperties();		
		UISession sess = this.getAuthorizedSession();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(1323) FROM DUAL", "0");
		String auth = sess.getAccount().getUserName()+":"+this.password.toString();
			byte[] encodedToken = Base64.encodeBase64(auth.toString().getBytes());
	        String uPPasswordString = new String(encodedToken);
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("Authorization", "Basic "+uPPasswordString);
		    headers.put("X-Forwarded-For", "127.0.0.1");
		    Logger.LogServer(" reject payout url : DELETE " + prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERID = ?", "",this.linkid )+"/payout/request/"+this.id+"?request-id="+reqid);

		    byte[] apiResponse = client.httpDeleteWithBody(prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERID = ?", "",this.linkid )+"/payout/request/"+this.id+"?request-id="+reqid, null, headers, null, null);
		    Logger.LogServer(" reject payout response : " + new String(apiResponse));
		    if(apiResponse.length>0){
		    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
			   
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
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
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.linkid,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}

	@SuppressWarnings("unchecked")
	public boolean sendMail(){
		DataRow manager = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME) = ?", this.requestby.toUpperCase());
		DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '13222' AND PORTAL ='OPERATOR'");

		SmsMessage sms = new SmsMessage();
		sms.setSender("UPay");
		sms.setDestination(manager.getString("MSISDN").toString());
		String msg = message.get("MESSAGE").toString();
		 msg = msg.replace("<referencenumber>", this.id);
		 msg = msg.replace("<amount>", this.amount);
		 msg = msg.replace("<balance>", SystemInfo.getDb().QueryScalar("SELECT DECRYPT(AMOUNT,?,ACCOUNTNUMBER)/100 BALANCE FROM ADMDBMC.TBLCURRENTSTOCK WHERE ACCOUNTNUMBER = ?", "",SystemInfo.getDb().getCrypt(), manager.getString("ACCOUNTNUMBER").toString()));
		 msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE,'MM/DD/YYYY') DATE FROM DUAL", ""));
		sms.setMessage(msg);
		JSONObject metadata=new JSONObject();
		metadata.put("initiator", "lucena");
		sms.setMetadata(metadata);
		sms.send();
			EmailUtils.sendApprovedPayout(manager.getString("EMAIL"),this.id,this.amount,manager.getString("FIRSTNAME").toString(),manager.getString("LASTNAME").toString());
			return true;
	}
	public boolean sendRejectMail(){
		DataRow manager = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME) = ?", this.requestby.toUpperCase());
	
			EmailUtils.sendRejectedPayout(manager.getString("EMAIL"),this.id,manager.getString("FIRSTNAME").toString(),manager.getString("LASTNAME").toString(),this.amount);
			return true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDepochannel() {
		return depochannel;
	}
	public void setDepochannel(String depochannel) {
		this.depochannel = depochannel;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRequestby() {
		return requestby;
	}
	public void setRequestby(String requestby) {
		this.requestby = requestby;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
