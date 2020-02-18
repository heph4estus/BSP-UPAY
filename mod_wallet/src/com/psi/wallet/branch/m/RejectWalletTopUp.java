package com.psi.wallet.branch.m;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.audit.trail.m.GetAuditPreviousData;
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

public class RejectWalletTopUp extends Model{
	protected String id;
	protected String linkid;
	protected String password;
	protected String remarks;
	protected String auditdata;
	
	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.id);
		  previous.setQuery("SELECT ID,AMOUNT/100 AMOUNT,EXTENDEDDATA REFERENCENUMBER,CREATEDBY,BANKNAME,DEPOSITCHANNEL,REMARKS,DATEOFDEPOSIT,TIMEOFDEPOSIT FROM TBLCASHINTRANSACTIONS WHERE ID=?");
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public boolean reject() throws ParseException, IOException{
		UISession sess = this.getAuthorizedSession();
		OtherProperties prop = new OtherProperties();
			DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT U.ACCOUNTNUMBER,U.EMAIL,CT.EXTENDEDDATA,U.USERSLEVEL,CT.REQUESTID,CT.AMOUNT FROM TBLCASHINTRANSACTIONS CT INNER JOIN  TBLUSERS U ON U.USERNAME = CT.CREATEDBY WHERE ID = ?", this.id);			
			DataRow email = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ? AND USERSLEVEL = 'MANAGER'", acct.getString("ACCOUNTNUMBER"));
			DataRow pass = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT (AI.PASSWORD,?, AI.ACCOUNTNUMBER) as PASSWORD FROM TBLUSERS U INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON U.ACCOUNTNUMBER=AI.ACCOUNTNUMBER WHERE U.USERID=?",SystemInfo.getDb().getCrypt(), this.linkid);
			String password = pass.getString("PASSWORD");
			
			 byte[] encodedToken = Base64.encodeBase64(password.toString().getBytes());
	         String uPPasswordString = new String(encodedToken);
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("authorization", uPPasswordString);
		    headers.put("X-Forwarded-For","127.0.0.1");
		    byte[] apiResponse = client.httpDelete(prop.getUrl()+acct.getString("ACCOUNTNUMBER")+prop.getCashinreject_url()+acct.getString("EXTENDEDDATA"), null, headers, null);
		    JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	 
		    
		    Logger.LogServer(" request : " + prop.getUrl()+acct.getString("ACCOUNTNUMBER")+prop.getCashinreject_url()+acct.getString("EXTENDEDDATA"));
		    Logger.LogServer(" response : " + new String(apiResponse));
		   Logger.LogServer("response:"+object.get("response-id").toString());
		    if(apiResponse.length>0){
		    	
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			    	EmailUtils.sendReject(email.getString("EMAIL").toString(),acct.getString("REQUESTID").toString(),LongUtil.toString(Long.parseLong(acct.getString("AMOUNT").toString())),sess.getAccount().getUsername(),email.getString("FIRSTNAME").toString()+" "+email.getString("LASTNAME").toString());
			    	SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET STATUS='REJECT',OPERATORID=?,EXTENDEDDATA2=?,REMARKS=? WHERE ID=?",this.linkid,object.get("response-id").toString(),this.remarks, this.id);
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
	public boolean isExist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=? ", this.id).size()>0;
	}
	public boolean isrejected(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=? AND STATUS='REJECT'", this.id).size()>0;
	}
	public boolean sendMail(){
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=?", this.id);
		DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", rr.getString("CREATEDBY"));	
		if(user.getString("USERSLEVEL").equals("MANAGER")){
			EmailUtils.send(user.getString("EMAIL"));
			return true;
		}else{
			EmailUtils.sendCashier(user.getString("EMAIL"));
			return true;
		}
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAuditdata() {
		return auditdata;
	}
	public void setAuditdata(String auditdata) {
		this.auditdata = auditdata;
	}
}
