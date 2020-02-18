package com.ibayad.customer.m;


import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ibayad.transmitters.client.SmsMessage;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;


public class EmailUtils
{

  
  @SuppressWarnings("unchecked")
public static boolean sendApprove(String business,String branch,String approvedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1208' AND PORTAL = 'OPERATOR'");
			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '1208' AND PORTAL = 'OPERATOR'", "");
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<branch>", branch);
			msg = msg.replace("<company>", business);
			msg = msg.replace("<approvedby>", approvedby);
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			
			Logger.LogServer("Message replace: " + msg);
			request.put("auth", request2);
			request2.put("host", row.get("HOST"));
			request2.put("port", row.get("PORT").toString());
			request2.put("username", row.get("USERNAME"));
			request2.put("password", row.get("PASSWORD"));
			request.put("to", email);
			request.put("subject", message.get("DESCRIPTION"));
			request.put("body", msg);

			Logger.LogServer("Request: " + request.toString());
			StringEntity entity = new StringEntity(request.toJSONString());

			HttpClientHelper client = new HttpClientHelper();
			HashMap headers = new HashMap();
			headers.put("Content-Type", prop.getType());

			byte[] apiResponse = client.httpPost(row.getString("URL"), null, headers, null,
					entity);
			Logger.LogServer(" response : " + new String(apiResponse));
			if (apiResponse.length > 0) {
				JSONObject object = (JSONObject) new JSONParser().parse(new String(apiResponse, "UTF-8"));
				Logger.LogServer(" response1 : " + new String(apiResponse));

				if ((object.get("code").toString().equals("1"))
						|| (object.get("code").toString().equals(Integer.valueOf(0)))) {
					Logger.LogServer("Email Sent to: " + email.toString());
					return true;
				} else {
					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
							+ object.get("message").toString() + " " + email.toString());
					return false;
				}
			} else {
				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email.toString());
				return false;
			}
		} catch (Exception e) {
			Logger.LogServer("error: " + e);
			return false;
		}
	  }
  public static boolean sendReject(String business,String branch,String rejectedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1207' AND PORTAL = 'OPERATOR'");
			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '1207' AND PORTAL = 'OPERATOR'", "");
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<branch>", branch);
			msg = msg.replace("<company>", business);
			msg = msg.replace("<rejectedby>", rejectedby);
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			
			Logger.LogServer("Message replace: " + msg);
			request.put("auth", request2);
			request2.put("host", row.get("HOST"));
			request2.put("port", row.get("PORT").toString());
			request2.put("username", row.get("USERNAME"));
			request2.put("password", row.get("PASSWORD"));
			request.put("to", email);
			request.put("subject", message.get("DESCRIPTION"));
			request.put("body", msg);

			Logger.LogServer("Request: " + request.toString());
			StringEntity entity = new StringEntity(request.toJSONString());

			HttpClientHelper client = new HttpClientHelper();
			HashMap headers = new HashMap();
			headers.put("Content-Type", prop.getType());

			byte[] apiResponse = client.httpPost(row.getString("URL"), null, headers, null,
					entity);
			Logger.LogServer(" response : " + new String(apiResponse));
			if (apiResponse.length > 0) {
				JSONObject object = (JSONObject) new JSONParser().parse(new String(apiResponse, "UTF-8"));
				Logger.LogServer(" response1 : " + new String(apiResponse));

				if ((object.get("code").toString().equals("1"))
						|| (object.get("code").toString().equals(Integer.valueOf(0)))) {
					Logger.LogServer("Email Sent to: " + email.toString());
					return true;
				} else {
					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
							+ object.get("message").toString() + " " + email.toString());
					return false;
				}
			} else {
				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email.toString());
				return false;
			}
		} catch (Exception e) {
			Logger.LogServer("error: " + e);
			return false;
		}
	  }
  
  @SuppressWarnings("unchecked")
public static boolean sendApproveUpgrade(String accountnumber,String approvedby,String customer) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19522' AND PORTAL = 'OPERATOR'");
			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '19522' AND PORTAL = 'OPERATOR'", "");
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<customer>", customer);
			msg = msg.replace("<approvedby>", approvedby);
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			
			Logger.LogServer("Message replace: " + msg);
			request.put("auth", request2);
			request2.put("host", row.get("HOST"));
			request2.put("port", row.get("PORT").toString());
			request2.put("username", row.get("USERNAME"));
			request2.put("password", row.get("PASSWORD"));
			request.put("to", email);
			request.put("subject", message.get("DESCRIPTION"));
			request.put("body", msg);

			Logger.LogServer("Request: " + request.toString());
			StringEntity entity = new StringEntity(request.toJSONString());

			HttpClientHelper client = new HttpClientHelper();
			HashMap headers = new HashMap();
			headers.put("Content-Type", prop.getType());

			byte[] apiResponse = client.httpPost(row.getString("URL"), null, headers, null,
					entity);
			Logger.LogServer(" response : " + new String(apiResponse));
			if (apiResponse.length > 0) {
				JSONObject object = (JSONObject) new JSONParser().parse(new String(apiResponse, "UTF-8"));
				Logger.LogServer(" response1 : " + new String(apiResponse));

				if ((object.get("code").toString().equals("1"))
						|| (object.get("code").toString().equals(Integer.valueOf(0)))) {
					Logger.LogServer("Email Sent to: " + email.toString());
					return true;
				} else {
					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
							+ object.get("message").toString() + " " + email.toString());
					return false;
				}
			} else {
				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email.toString());
				return false;
			}
		} catch (Exception e) {
			Logger.LogServer("error: " + e);
			return false;
		}
	  }
  
  @SuppressWarnings("unchecked")
  public static boolean sendApproveUpgrade(String branch,String approvedby,String accountinfoemail,String accountnumber,String emptystringlng) {
  	  try {
  			OtherProperties prop = new OtherProperties();
  			JSONObject request = new JSONObject();
  			JSONObject request2 = new JSONObject();
  			DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ? ",accountnumber);
  			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
  			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19523' AND PORTAL = 'OPERATOR'");
  			String email = user.getString("EMAIL").toString();
  			String msg = message.get("MESSAGE").toString();
  			msg = msg.replace("<branch>", branch);
  			msg = msg.replace("<firstname>", user.getString("FIRSTNAME").toString());
  			msg = msg.replace("<lastname>", user.getString("LASTNAME").toString());
  			msg = msg.replace("<approvedby>", approvedby);
  			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
  			
  			Logger.LogServer("Message replace: " + msg);
  			request.put("auth", request2);
  			request2.put("host", row.get("HOST"));
  			request2.put("port", row.get("PORT").toString());
  			request2.put("username", row.get("USERNAME"));
  			request2.put("password", row.get("PASSWORD"));
  			request.put("to", email);
  			request.put("subject", message.get("DESCRIPTION"));
  			request.put("body", msg);

  			Logger.LogServer("Request: " + request.toString());
  			StringEntity entity = new StringEntity(request.toJSONString());

  			HttpClientHelper client = new HttpClientHelper();
  			HashMap headers = new HashMap();
  			headers.put("Content-Type", prop.getType());

  			byte[] apiResponse = client.httpPost(row.getString("URL"), null, headers, null,
  					entity);
  			Logger.LogServer(" response : " + new String(apiResponse));
  			if (apiResponse.length > 0) {
  				JSONObject object = (JSONObject) new JSONParser().parse(new String(apiResponse, "UTF-8"));
  				Logger.LogServer(" response1 : " + new String(apiResponse));

  				if ((object.get("code").toString().equals("1"))
  						|| (object.get("code").toString().equals(Integer.valueOf(0)))) {
  					Logger.LogServer("Email Sent to: " + email.toString());
  					return true;
  				} else {
  					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
  							+ object.get("message").toString() + " " + email.toString());
  					return false;
  				}
  			} else {
  				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email.toString());
  				return false;
  			}
  		} catch (Exception e) {
  			Logger.LogServer("error: " + e);
  			return false;
  		}
  	  }
  @SuppressWarnings("unchecked")
public static boolean sendapprovenotif(String accountnumber) {
	  DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1952' AND PORTAL = 'OPERATOR'");
	   
	  DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ?",accountnumber);
		
	  SmsMessage sms = new SmsMessage();
		sms.setSender("UPay");
		sms.setDestination(user.getString("USERNAME").toUpperCase());
		String msg = message.get("MESSAGE").toString();
		sms.setMessage(msg);
		JSONObject metadata=new JSONObject();
		metadata.put("title", message.get("DESCRIPTION").toString());
		metadata.put("body", message.get("MESSAGE").toString());
		metadata.put("message", message.get("MESSAGE").toString());
		metadata.put("mode", "inbox");
		metadata.put("type", "admin");
		metadata.put("initiator", "lucena");
		sms.setMetadata(metadata);
		sms.sendnotif();	
		return true;
	  }
  @SuppressWarnings("unchecked")
public static boolean sendapprovesms(String accountnumber) {
	  DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19521' AND PORTAL = 'OPERATOR'");
	   
	  DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ?",accountnumber);
		
	  SmsMessage sms = new SmsMessage();
		sms.setSender("UPay");
		sms.setDestination(user.getString("MSISDN").toString());
		String msg = message.get("MESSAGE").toString();
		sms.setMessage(msg);
		JSONObject metadata=new JSONObject();
		metadata.put("title", message.get("DESCRIPTION").toString());
		metadata.put("body", message.get("MESSAGE").toString());
		metadata.put("message", message.get("MESSAGE").toString());
		metadata.put("mode", "inbox");
		metadata.put("type", "admin");
		metadata.put("initiator", "lucena");
		sms.setMetadata(metadata);
		sms.send();	
		return true;
	  }
  public static boolean sendRejectUpgrade(String branch,String approvedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1214' AND PORTAL = 'OPERATOR'");
			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '1214' AND PORTAL = 'OPERATOR'", "");
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<branch>", branch);
			msg = msg.replace("<approvedby>", approvedby);
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			
			Logger.LogServer("Message replace: " + msg);
			request.put("auth", request2);
			request2.put("host", row.get("HOST"));
			request2.put("port", row.get("PORT").toString());
			request2.put("username", row.get("USERNAME"));
			request2.put("password", row.get("PASSWORD"));
			request.put("to", email);
			request.put("subject", message.get("DESCRIPTION"));
			request.put("body", msg);

			Logger.LogServer("Request: " + request.toString());
			StringEntity entity = new StringEntity(request.toJSONString());

			HttpClientHelper client = new HttpClientHelper();
			HashMap headers = new HashMap();
			headers.put("Content-Type", prop.getType());

			byte[] apiResponse = client.httpPost(row.getString("URL"), null, headers, null,
					entity);
			Logger.LogServer(" response : " + new String(apiResponse));
			if (apiResponse.length > 0) {
				JSONObject object = (JSONObject) new JSONParser().parse(new String(apiResponse, "UTF-8"));
				Logger.LogServer(" response1 : " + new String(apiResponse));

				if ((object.get("code").toString().equals("1"))
						|| (object.get("code").toString().equals(Integer.valueOf(0)))) {
					Logger.LogServer("Email Sent to: " + email.toString());
					return true;
				} else {
					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
							+ object.get("message").toString() + " " + email.toString());
					return false;
				}
			} else {
				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email.toString());
				return false;
			}
		} catch (Exception e) {
			Logger.LogServer("error: " + e);
			return false;
		}
	  }
  @SuppressWarnings("unchecked")
  public static boolean sendRejectUpgrade(String customer,String approvedby,String accountnumber) {
  	  try {
  			OtherProperties prop = new OtherProperties();
  			JSONObject request = new JSONObject();
  			JSONObject request2 = new JSONObject();
  			DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ? ",accountnumber);
  			
  			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
  			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19532' AND PORTAL = 'OPERATOR'");
  			String msg = message.get("MESSAGE").toString();
  			msg = msg.replace("<customer>", customer);
  			msg = msg.replace("<approvedby>", approvedby);
  			msg = msg.replace("<firstname>", user.getString("FIRSTNAME").toString());
  			msg = msg.replace("<lastname>", user.getString("LASTNAME").toString());
  			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
  			String email = user.getString("EMAIL").toString();
  			Logger.LogServer("Message replace: " + msg);
  			request.put("auth", request2);
  			request2.put("host", row.get("HOST"));
  			request2.put("port", row.get("PORT").toString());
  			request2.put("username", row.get("USERNAME"));
  			request2.put("password", row.get("PASSWORD"));
  			request.put("to", email);
  			request.put("subject", message.get("DESCRIPTION"));
  			request.put("body", msg);

  			Logger.LogServer("Request: " + request.toString());
  			StringEntity entity = new StringEntity(request.toJSONString());

  			HttpClientHelper client = new HttpClientHelper();
  			HashMap headers = new HashMap();
  			headers.put("Content-Type", prop.getType());

  			byte[] apiResponse = client.httpPost(row.getString("URL"), null, headers, null,
  					entity);
  			Logger.LogServer(" response : " + new String(apiResponse));
  			if (apiResponse.length > 0) {
  				JSONObject object = (JSONObject) new JSONParser().parse(new String(apiResponse, "UTF-8"));
  				Logger.LogServer(" response1 : " + new String(apiResponse));

  				if ((object.get("code").toString().equals("1"))
  						|| (object.get("code").toString().equals(Integer.valueOf(0)))) {
  					Logger.LogServer("Email Sent to: " + email.toString());
  					return true;
  				} else {
  					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
  							+ object.get("message").toString() + " " + email.toString());
  					return false;
  				}
  			} else {
  				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email.toString());
  				return false;
  			}
  		} catch (Exception e) {
  			Logger.LogServer("error: " + e);
  			return false;
  		}
  	  }
  @SuppressWarnings("unchecked")
public static boolean sendrejectnotif(String accountnumber) {
	  DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1953' AND PORTAL = 'OPERATOR'");
	   
	  DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ?",accountnumber);
		
	  SmsMessage sms = new SmsMessage();
		sms.setSender("UPay");
		sms.setDestination(user.getString("USERNAME").toUpperCase());
		String msg = message.get("MESSAGE").toString();
		sms.setMessage(msg);
		JSONObject metadata=new JSONObject();
		metadata.put("title", message.get("DESCRIPTION").toString());
		metadata.put("body", message.get("MESSAGE").toString());
		metadata.put("message", message.get("MESSAGE").toString());
		metadata.put("mode", "inbox");
		metadata.put("type", "admin");
		metadata.put("initiator", "lucena");
		sms.setMetadata(metadata);
		sms.sendnotif();	
		return true;
	  }
  @SuppressWarnings("unchecked")
public static boolean sendrejectsms(String accountnumber) {
	  DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19531' AND PORTAL = 'OPERATOR'");
	   
	  DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ?",accountnumber);
		
	  SmsMessage sms = new SmsMessage();
		sms.setSender("UPay");
		sms.setDestination(user.getString("MSISDN").toString());
		String msg = message.get("MESSAGE").toString();
		sms.setMessage(msg);
		JSONObject metadata=new JSONObject();
		metadata.put("title", message.get("DESCRIPTION").toString());
		metadata.put("body", message.get("MESSAGE").toString());
		metadata.put("message", message.get("MESSAGE").toString());
		metadata.put("mode", "inbox");
		metadata.put("type", "admin");
		metadata.put("initiator", "lucena");
		sms.setMetadata(metadata);
		sms.send();	
		return true;
	  }
}
