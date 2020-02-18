package com.psi.business.util;


import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ibayad.sms.email.common.EmailMessagev2;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.smtp.EmailAddress;
import com.tlc.smtp.EmailMessage;

public class EmailUtils
{

  public static boolean send(String email,String fname, String lname, String password,String username,String url) {
	  try {
		    OtherProperties prop = new OtherProperties();
		      JSONObject request = new JSONObject();
		      JSONObject request2 = new JSONObject();

		      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
		      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1104' AND PORTAL='OPERATOR'");

		 	String msg = message.get("MESSAGE").toString();
			      msg = msg.replace("<firstname>", fname);
			      msg = msg.replace("<lastname>", lname);
			      msg = msg.replace("<password>", password);
			      msg = msg.replace("<username>", username);
			      msg = msg.replace("<url>", url);
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
		      headers.put("token", prop.getToken());

		      byte[] apiResponse = client.httpPost(row.getString("URL").toString(), null, headers, null, 
		        entity);
		      Logger.LogServer(row.getString("URL").toString());
		      

		      if (apiResponse.length > 0) {
		        JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
		        Logger.LogServer(" response : " + new String(apiResponse));

		        if ((object.get("code").toString().equals("1")) || 
		          (object.get("code").toString().equals(Integer.valueOf(0)))) {
		          Logger.LogServer("Email Sent to: " + email);
		          return true;
		        }
		        Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " " + 
		          object.get("message").toString() + " " + email);
		        return false;
		      }

		      Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email);
		      return false;
		    }
		    catch (Exception e) {
		      Logger.LogServer("error: " + e);
		    }return false;
	  }
  public static boolean sendUpdate(String email,String fname, String lname,String username,String ip, String updatedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1105' AND PORTAL = 'OPERATOR'");

			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<firstname>", fname);
			msg = msg.replace("<lastname>", lname);
			msg = msg.replace("<username>", username);
			msg = msg.replace("<ip>", ip);
			msg = msg.replace("<updatedby>", updatedby);
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
public static void sendMechantCreation(String business,String fname, String lname) {
	  DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1100' AND PORTAL='OPERATOR'");
	  String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '1100' AND PORTAL = 'OPERATOR'", "");
		 
	  EmailMessagev2 msg = new EmailMessagev2();
	    msg.addDestination(email);
	    msg.withSubject(message.getString("DESCRIPTION").toString());
	    String msg1 = message.get("MESSAGE").toString();
		    msg1 = msg1.replace("<firstname>", fname);
		    msg1 = msg1.replace("<lastname>", lname);
		    msg1 = msg1.replace("<company>", business);
		    msg1 = msg1.replace("<branch>", business + " HEAD OFFICE");		
	    msg.withMessage(msg1);
	    msg.send();
	  }
  public static boolean sendmanagercreds(String email,String fname, String lname, String password,String username,String url) {

	  try {
		    OtherProperties prop = new OtherProperties();
		      JSONObject request = new JSONObject();
		      JSONObject request2 = new JSONObject();

		      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
		      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1204' AND PORTAL='OPERATOR'");

		 	String msg = message.get("MESSAGE").toString();
			      msg = msg.replace("<firstname>", fname);
			      msg = msg.replace("<lastname>", lname);
			      msg = msg.replace("<password>", password);
			      msg = msg.replace("<username>", username);
			      msg = msg.replace("<url>", url);
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
		      headers.put("token", prop.getToken());

		      byte[] apiResponse = client.httpPost(row.getString("URL").toString(), null, headers, null, 
		        entity);
		      Logger.LogServer(row.getString("URL").toString());
		      

		      if (apiResponse.length > 0) {
		        JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
		        Logger.LogServer(" response : " + new String(apiResponse));

		        if ((object.get("code").toString().equals("1")) || 
		          (object.get("code").toString().equals(Integer.valueOf(0)))) {
		          Logger.LogServer("Email Sent to: " + email);
		          return true;
		        }
		        Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " " + 
		          object.get("message").toString() + " " + email);
		        return false;
		      }

		      Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email);
		      return false;
		    }
		    catch (Exception e) {
		      Logger.LogServer("error: " + e);
		    }return false;
	  }
  @SuppressWarnings("unchecked")
public static boolean sendApproveMerch(String branch,String username) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1108' AND PORTAL = 'OPERATOR'");
			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '1108' AND PORTAL = 'OPERATOR'", "");
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<branch>", branch + " HEAD OFFICE");
			msg = msg.replace("<company>", branch);
			msg = msg.replace("<approvedby>", username);
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
  public static boolean sendPreApproveMerch(String branch,String username) {
  	  try {
  			OtherProperties prop = new OtherProperties();
  			JSONObject request = new JSONObject();
  			JSONObject request2 = new JSONObject();

  			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
  			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1112' AND PORTAL = 'OPERATOR'");
  			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '1112' AND PORTAL = 'OPERATOR'", "");
  			String msg = message.get("MESSAGE").toString();
  			msg = msg.replace("<branch>", branch + " HEAD OFFICE");
  			msg = msg.replace("<company>", branch);
  			msg = msg.replace("<approvedby>", username);
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
  public static boolean sendRejectMerch(String branch,String username) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1109' AND PORTAL = 'OPERATOR'");
			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '1109' AND PORTAL = 'OPERATOR'", "");
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<branch>", branch + " HEAD OFFICE");
			msg = msg.replace("<company>", branch);
			msg = msg.replace("<approvedby>", username);
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
  public static boolean sendPreRejectMerch(String branch,String username) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1113' AND PORTAL = 'OPERATOR'");
			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID = '1113' AND PORTAL = 'OPERATOR'", "");
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<branch>", branch + " HEAD OFFICE");
			msg = msg.replace("<company>", branch);
			msg = msg.replace("<approvedby>", username);
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
  public static boolean sendEditMerch(String branch,String username) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1111' AND PORTAL = 'OPERATOR'");
			String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM TBLALERTRECIPIENT WHERE MODULEID = '1111' AND PORTAL = 'OPERATOR'", "");
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<branch>", branch);
			msg = msg.replace("<approvedby>", username);
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
}
