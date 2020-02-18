package com.psi.wallet.branch.m;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.audit.trail.m.GetAuditPreviousData;
import com.psi.wallet.util.EmailUtils;
import com.psi.wallet.util.HttpClientHelper;
import com.psi.wallet.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class ApprovedWalletTopup extends Model{
	protected String id;
	protected String linkid;
	protected String password;
	protected String depochannel;
	protected String remarks;
	protected String image;
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
	public boolean approved() throws ParseException, IOException{
		OtherProperties prop = new OtherProperties();		
			    
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=?", this.id);

			String password = "1234";
			 byte[] encodedToken = Base64.encodeBase64(password.toString().getBytes());
	         String uPPasswordString = new String(encodedToken);
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("authorization", uPPasswordString);
		    byte[] apiResponse = client.httpPut(prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ID = ?", "", new Object[] { SystemInfo.getDb().QueryScalar("SELECT ROOT FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", new Object[] { row.getString("ACCOUNTNUMBER") }) })+prop.getCashinapproved_url()+row.getString("EXTENDEDDATA"), null, headers, null, null);

		    if(apiResponse.length>0){
		    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
			    Logger.LogServer(" response : " + new String(apiResponse));
			   
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			    	SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET LEVELOFAPPROVAL=0, STATUS='APPROVED',OPERATORID=?,EXTENDEDDATA2=? WHERE ID=?",this.linkid,object.get("response-id").toString(), this.id);
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID = ?", this.id).size()>0;
	}
	
	public boolean isapproved(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=? AND STATUS='APPROVED' AND LEVELOFAPPROVAL=0", this.id).size()>0;
	}
	public boolean sendMail() throws java.text.ParseException{
		DataRow user =  SystemInfo.getDb().QueryDataRow("SELECT CT.ACCOUNTNUMBER,U.FIRSTNAME,U.LASTNAME,AMOUNT,REFERENCE,CT.CREATEDBY,U.USERSLEVEL FROM TBLCASHINTRANSACTIONS CT INNER JOIN TBLUSERS U ON CT.CREATEDBY = U.USERNAME WHERE ID=?", this.id);
		String email="";
		if(user.getString("USERSLEVEL").equals("MANAGER")){
			email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM TBLUSERS WHERE USERNAME = ?","", user.getString("CREATEDBY"));
		}else if(user.getString("USERSLEVEL").equals("CASHIER")){
			email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM TBLUSERS WHERE USERNAME = ?","", user.getString("CREATEDBY"))+";"+SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM TBLUSERS WHERE USERSLEVEL='MANAGER' AND ACCOUNTNUMBER = ?","", user.getString("ACCOUNTNUMBER"));
		}else{
			email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM TBLUSERS WHERE USERSLEVEL='MANAGER' AND ACCOUNTNUMBER = ?","", user.getString("ACCOUNTNUMBER"));
		}
		EmailUtils.sendApproved(email,
								user.getString("REFERENCE"),
								SystemInfo.getDb().QueryScalar("SELECT BRANCH FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?","", user.getString("ACCOUNTNUMBER")),
								user.getString("FIRSTNAME")+" "+user.getString("LASTNAME"),
								LongUtil.toString(Long.parseLong(user.getString("AMOUNT").toString())));
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
	public String getAuditdata() {
		return auditdata;
	}
	public void setAuditdata(String auditdata) {
		this.auditdata = auditdata;
	}

}
