package com.psi.accountmanagement.m;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;

import com.ibayad.transmitters.client.SmsMessage;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.utils.OneTimePassword;
import com.psi.accountmanagement.utils.Users;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.encryption.PasswordGenerator;

public class ManageAccount extends Users{
	
	public boolean update(){
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		if(this.locked.equals("YES")){
				query.append("UPDATE TBLUSERS SET LOCKED=?,INVALIDPASSWORDCOUNT=0,SESSIONID='' WHERE USERID = ?; \n");
		}else{
			query.append("UPDATE TBLUSERS SET LOCKED=?,INVALIDPASSWORDCOUNT=3,SESSIONID='' WHERE USERID = ?; \n");
		}
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.locked, this.userid)>0;

	}
	
	public boolean activate(){
		StringBuilder query2 = new StringBuilder("BEGIN\n");
		query2.append("UPDATE TBLUSERS SET STATUS=?,SESSIONID='' WHERE USERID = ?; \n");
		query2.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query2.toString(), this.status, this.userid)>0;
	}
	
	@SuppressWarnings("unchecked")
	public boolean resetpassword(String userid){
// if manager is not sms uncomment 
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.userid);
		if(!r.getString("USERSLEVEL").equals("MANAGER")){
			OneTimePassword onetime = new OneTimePassword();
			onetime.andAccount(r.getString("ACCOUNTNUMBER").toString());
			onetime.forMobile(r.getString("MSISDN").toString());
			onetime.andModule(9605);
			String otp = PasswordGenerator.generatePassword(6, PasswordGenerator.NUMERIC_CHAR);
			if(onetime.send(otp,this.userid)){
				return true;
			}else{
				return false;
			}
		}else{
			String token = PasswordGenerator.generatePassword(9, PasswordGenerator.NUMERIC_CHAR);
			int res = SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET TOKENEXPIRY=(SYSDATE + 7),TOKEN=? WHERE USERID = ?", token,this.userid);
			if(res>0){
				JSONObject urltoken = new JSONObject();
				urltoken.put("Id", r.getString("USERID"));
				urltoken.put("Token", r.getString("TOKEN"));
				urltoken.put("Exp", r.getString("TOKENEXPIRY"));
				
				
				byte[] contoken = null;
				try {						
					contoken = urltoken.toJSONString().getBytes("UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				String encodetoken = DatatypeConverter.printBase64Binary(contoken);
					try{
							 EmailUtils.sendResetManagerPass(r.getString("EMAIL").toString(),"authenticate?q="+encodetoken);
						}catch (Exception e) { 
							Logger.LogServer(e);
					}
					return true;
			}else{
				return false;
			}
		}
	}
	@SuppressWarnings("unchecked")
	public boolean resenduserpassword(String userid){
		DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19341' AND PORTAL = 'OPERATOR'");
		DataRow row =SystemInfo.getDb().QueryDataRow("SELECT EMAIL,USERNAME,DECRYPT(PASSWORD,?,USERNAME) PASSWORD FROM TBLUSERS WHERE USERID = ?",SystemInfo.getDb().getCrypt(), this.userid);
		if(!row.isEmpty()){
			if(EmailUtils.resendusernamepassword(row.getString("EMAIL").toString(), row.getString("PASSWORD").toString(), row.getString("USERNAME").toString())){
				SmsMessage sms = new SmsMessage();
				sms.setSender("UPay");
				sms.setDestination(row.getString("MSISDN").toString());
				String msg = message.get("MESSAGE").toString();
				msg = msg.replace("<username>", row.getString("USERNAME").toString());
				msg = msg.replace("<password>", row.getString("PASSWORD").toString());
				sms.setMessage(msg);
				JSONObject metadata=new JSONObject();
				metadata.put("initiator", "lucena");
				sms.setMetadata(metadata);
				sms.send();	
				return true;
				}else{
					return false;
					}
		}else{
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public boolean resenduser(String userid){
		DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19342' AND PORTAL = 'OPERATOR'");
		DataRow row =SystemInfo.getDb().QueryDataRow("SELECT EMAIL,USERNAME,DECRYPT(PASSWORD,?,USERNAME) PASSWORD FROM TBLUSERS WHERE USERID = ?",SystemInfo.getDb().getCrypt(), this.userid);
		if(!row.isEmpty()){
			if(EmailUtils.resenduser(row.getString("EMAIL").toString(), row.getString("PASSWORD").toString(), row.getString("EMAIL").toString())){
				SmsMessage sms = new SmsMessage();
				sms.setSender("UPay");
				sms.setDestination(row.getString("MSISDN").toString());
				String msg = message.get("MESSAGE").toString();
				msg = msg.replace("<username>", row.getString("USERNAME").toString());
				msg = msg.replace("<password>", row.getString("PASSWORD").toString());
				sms.setMessage(msg);
				JSONObject metadata=new JSONObject();
				metadata.put("initiator", "lucena");
				sms.setMetadata(metadata);
				sms.send();
				return true;
				}else{
					return false;
				}
		}else{
			return false;
		}
	}

}
