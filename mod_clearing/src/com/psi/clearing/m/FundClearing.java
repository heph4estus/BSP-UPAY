package com.psi.clearing.m;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.clearing.utils.HttpClientHelper;
import com.psi.clearing.utils.OtherProperties;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class FundClearing extends Model{
	protected String amount;
	protected String password;
	protected String accountnumber;
	
	public boolean clear(){
		try{
		OtherProperties prop = new OtherProperties();
		
		HttpClientHelper client = new HttpClientHelper();
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", prop.getType());
	    headers.put("token", prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");	    	    
	    
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(1400) FROM DUAL", "0");
			
		byte[] apiResponse = client.httpDeleteWithBody(prop.getUrl()+this.accountnumber+"/funds/payout?request-id="+reqid+"&amount="+amount, null, headers, null, null);
				Logger.LogServer("fund clearing url:"+prop.getUrl()+this.accountnumber+"/funds/payout?request-id="+reqid+"&amount="+amount);
				Logger.LogServer("fund clearinf response:"+new String(apiResponse, "UTF-8"));
				
				if(apiResponse.length>0){
					JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
					if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			
						StringBuilder query = new StringBuilder("BEGIN\n");
						query.append("INSERT INTO TBLCLEARING (REFERENCEID,AMOUNT,OUTLET,COMPANY,REQUESTID,CLEARINGTYPE) VALUES(?,?,?,?,?,?);\n");
						query.append("END;\n");
				        SystemInfo.getDb().QueryUpdate(query.toString(),object.get("response-id"), LongUtil.toLong(this.amount),this.accountnumber, SystemInfo.getDb().QueryScalar("SELECT KEYACCOUNT FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", "", this.accountnumber), reqid, "PAYOUT");
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
			}catch (Exception e){
				this.setState(new ObjectState("99","System is busy"));
				return false;
			}
	}
	
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", sess.getAccount().getId(),this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
}
