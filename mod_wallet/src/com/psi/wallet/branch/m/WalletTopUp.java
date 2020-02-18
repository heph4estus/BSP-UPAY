package com.psi.wallet.branch.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
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
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class WalletTopUp extends Model{
	protected String accountnumber;
	protected String referencenumber;
	protected Long amount;
	protected String password;
	protected String id;
	protected String remarks;
	protected String status;
	protected String depositchannel;
	protected String timeofdeposit;
	protected String dateofdeposit;
	protected String bankcode;
	protected String bankname;
	protected String image;
	protected String description;
	
	@SuppressWarnings("unchecked")
	public boolean topup() throws ParseException, IOException{
		UISession sess = this.getAuthorizedSession();
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(1309) FROM DUAL", "0");
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AI.PASSWORD,?,AI.ACCOUNTNUMBER) PASSWORD,U.USERNAME,AI.ROOT,U.ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE U.USERID = ?",SystemInfo.getDb().getCrypt(), this.id);
		
			request.put("destination",row.getString("ACCOUNTNUMBER"));
			request.put("request-id", reqid);
			request.put("amount", LongUtil.toString(this.amount));
			request.put("reference", this.referencenumber);
				request2.put("password", row.getString("PASSWORD"));
			request.put("auth",request2);
			
			Logger.LogServer(request.toString());
		
				StringEntity entity = new StringEntity(request.toJSONString());
				
				HttpClientHelper client = new HttpClientHelper();
			    HashMap<String, String> headers = new HashMap<String, String>();
			    headers.put("Content-Type", prop.getType());
			    headers.put("token", prop.getToken());
			    headers.put("X-Forwarded-For","127.0.0.1");
			    byte[] apiResponse = client.httpPost(prop.getUrl()+this.accountnumber+prop.getCashin_url(), null, headers, null, entity);
	 
			    if(apiResponse.length>0){
			    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
				    Logger.LogServer(" response : " + new String(apiResponse));
				   
				    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
				    	
				    	SystemInfo.getDb().QueryUpdate("INSERT INTO TBLCASHINTRANSACTIONS (TYPE,LEVELOFAPPROVAL,REQUESTID,ACCOUNTNUMBER,CREATEDBY,AMOUNT,STATUS,REMARKS,DEPOSITCHANNEL,TIMEOFDEPOSIT, DATEOFDEPOSIT,BANKBRANCHCODE,BANKNAME,REFERENCEIMAGE,DESCRIPTION,REFERENCE,EXTENDEDDATA) VALUES ('ALLOC','1',?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY/MM/DD'),?,?,?,?,?,?)",reqid,this.accountnumber,row.getString("USERNAME"),this.amount,this.status,this.remarks,this.depositchannel, this.timeofdeposit, this.dateofdeposit, this.bankcode, this.bankname, this.image, this.description, this.referencenumber,object.get("response-id").toString());
				    	EmailUtils.sendWalletrequest( this.referencenumber, SystemInfo.getDb().QueryScalar("SELECT BRANCH FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?", "", this.accountnumber), sess.getAccount().getUsername(), LongUtil.toString(this.amount));
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.id,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean isUnique(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE REFERENCE =?", this.referencenumber).size()>0;
	}
	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getReferencenumber() {
		return referencenumber;
	}

	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDepositchannel() {
		return depositchannel;
	}
	public void setDepositchannel(String depositchannel) {
		this.depositchannel = depositchannel;
	}
	public String getTimeofdeposit() {
		return timeofdeposit;
	}
	public void setTimeofdeposit(String timeofdeposit) {
		this.timeofdeposit = timeofdeposit;
	}
	public String getDateofdeposit() {
		return dateofdeposit;
	}
	public void setDateofdeposit(String dateofdeposit) {
		this.dateofdeposit = dateofdeposit;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
