package com.psi.accountmanagement.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.smtp.EmailAddress;
import com.tlc.smtp.EmailMessage;

public class EmailUtils
{

  public static boolean send(String email,String fname, String lname, String authcode,String password,String username) {
	  try{
		 
		       
		         EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1000, "100000");
		 	    emailMessage.replace(new String []{"<firstname>",fname, 
		 	    		"<email>",lname,
		 	    		"<authcode>",authcode,
		 	    		"<password>",password,
		 	    		"<username>",username});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
	  }
  public static boolean sendPassword(String email, String password,String url) {
	  try{
	
		         EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1020, "102000");
		 	    emailMessage.replace(new String []{"<password>",password,"<url>",url});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
	  }
  
  public static boolean sendEmailResetPassword(String userid){
	    try {

	    	OtherProperties prop = new OtherProperties();

	        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date now = new Date();
	        String strDate = sdfDate.format(now);
	        String requestid = strDate + "01";
	        DataRow username = SystemInfo.getDb().QueryDataRow("SELECT USERNAME FROM TBLUSERS WHERE USERID=?", new Object[]  {userid });
	        String uname = username.get("USERNAME").toString();
	        
	        Logger.LogServer(prop.getUrl()+"ibayad/pos/password?cashier-id="+uname+"&"+"request-id="+requestid);

		    
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("X-Forwarded-For","127.0.0.1");
		    
		    byte[] apiResponse = client.httpDelete(prop.getUrl()+"ibayad/pos/password?cashier-id="+uname+"&"+"request-id="+requestid, null, headers, null);
		    JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	 
	    	
		    Logger.LogServer(" response : " + new String(apiResponse));

	        if (apiResponse.length > 0) {
	          Logger.LogServer(" response : " + new String(apiResponse));

	          if ((object.get("code").toString().equals("0")) || (object.get("code").toString().equals(Integer.valueOf(0)))) {
	            Logger.LogServer("Email Sent to: " + userid.toString());
	            return true;
	          }
	          Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " " + 
	            object.get("message").toString() + " " + userid.toString());
	          return false;
	        }

	        Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + userid.toString());
	        return false;
	      }
	      catch (Exception e) {
	        Logger.LogServer("error: " + e);
	      }return false;
	  
  }
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static boolean sendEmailForgotPassword(String email, String password,String url) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG", new Object[0]);
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1020'");


	      String msg = message.get("MESSAGE").toString();
	      msg = msg.replace("<password>", password);
	      msg = msg.replace("<url>", url);

	      Logger.LogServer("Message replace: " + msg);
	      request.put("auth", request2);
	      request2.put("host", row.get("HOST"));
	      request2.put("port", row.get("PORT").toString());
	      request2.put("username", row.get("USERNAME"));
	      request2.put("password", row.get("PASSWORD"));
	      request.put("to", email);
	      request.put("subject", "Forgot Password");
	      request.put("body", msg);

	      Logger.LogServer("Request: " + request.toString());
	      StringEntity entity = new StringEntity(request.toJSONString());

	      HttpClientHelper client = new HttpClientHelper();
	      HashMap headers = new HashMap();
	      headers.put("Content-Type", prop.getType());
	      headers.put("token", prop.getToken());

	      byte[] apiResponse = client.httpPost("http://192.168.7.11:8080/miline/email/sendmail", null, headers, null, 
	        entity);
	      Logger.LogServer("http://192.168.7.11:8080/miline/email/sendmail");
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
 
  @SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean sendAuthenticationEmail(String email,String url) {
		try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '3908' AND PORTAL = 'OPERATOR'");

			String msg = message.get("MESSAGE").toString();
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
  @SuppressWarnings({ "unchecked", "rawtypes" })
 	public static boolean sendResetManagerPass(String email,String url) {
 		try {
 			OtherProperties prop = new OtherProperties();
 			JSONObject request = new JSONObject();
 			JSONObject request2 = new JSONObject();

 			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
 			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '39081' AND PORTAL = 'OPERATOR'");

 			String msg = message.get("MESSAGE").toString();
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
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static boolean sendUpdate(String email,String fname, String lname,String userid,String ip,String updatedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '3907' AND PORTAL = 'OPERATOR'");

			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<firstname>", fname);
			msg = msg.replace("<lastname>", lname);
			msg = msg.replace("<username>", SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID = ?", "", userid));
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			msg = msg.replace("<ip>", ip);
			msg = msg.replace("<updatedby>", updatedby);
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
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static boolean sendLock(String userid,String ip,String updatedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19321' AND PORTAL = 'OPERATOR'");
			DataRow r = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(PASSWORD,?,USERNAME)PASS,U.* FROM TBLUSERS U WHERE USERID = ?", SystemInfo.getDb().getCrypt(),userid);
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<firstname>", r.getString("FIRSTNAME"));
			msg = msg.replace("<lastname>", r.getString("LASTNAME"));
			msg = msg.replace("<password>", r.getString("PASS"));
			msg = msg.replace("<username>", SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID = ?", "", userid));
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			msg = msg.replace("<ip>", ip);
			msg = msg.replace("<updatedby>", updatedby);
			Logger.LogServer("Message replace: " + msg);
			request.put("auth", request2);
			request2.put("host", row.get("HOST"));
			request2.put("port", row.get("PORT").toString());
			request2.put("username", row.get("USERNAME"));
			request2.put("password", row.get("PASSWORD"));
			request.put("to", r.getString("EMAIL"));
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
					Logger.LogServer("Email Sent to: " + r.getString("EMAIL").toString());
					return true;
				} else {
					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
							+ object.get("message").toString() + " " + r.getString("EMAIL").toString());
					return false;
				}
			} else {
				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + r.getString("EMAIL").toString());
				return false;
			}
		} catch (Exception e) {
			Logger.LogServer("error: " + e);
			return false;
		}
	  }
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static boolean sendunlock(String userid,String ip,String updatedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19322' AND PORTAL = 'OPERATOR'");
			DataRow r = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(PASSWORD,?,USERNAME)PASS,U.* FROM TBLUSERS U WHERE USERID = ?", SystemInfo.getDb().getCrypt(),userid);
			
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<firstname>", r.getString("FIRSTNAME"));
			msg = msg.replace("<lastname>", r.getString("LASTNAME"));
			msg = msg.replace("<password>", r.getString("PASS"));
			msg = msg.replace("<username>", SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID = ?", "", userid));
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			msg = msg.replace("<ip>", ip);
			msg = msg.replace("<updatedby>", updatedby);
			Logger.LogServer("Message replace: " + msg);
			request.put("auth", request2);
			request2.put("host", row.get("HOST"));
			request2.put("port", row.get("PORT").toString());
			request2.put("username", row.get("USERNAME"));
			request2.put("password", row.get("PASSWORD"));
			request.put("to", r.getString("EMAIL"));
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
					Logger.LogServer("Email Sent to: " + r.getString("EMAIL").toString());
					return true;
				} else {
					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
							+ object.get("message").toString() + " " + r.getString("EMAIL").toString());
					return false;
				}
			} else {
				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + r.getString("EMAIL").toString());
				return false;
			}
		} catch (Exception e) {
			Logger.LogServer("error: " + e);
			return false;
		}
	  }
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static boolean sendActive(String userid,String ip,String updatedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19331' AND PORTAL = 'OPERATOR'");
			DataRow r = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(PASSWORD,?,USERNAME)PASS,U.* FROM TBLUSERS U WHERE USERID = ?", SystemInfo.getDb().getCrypt(),userid);
			
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<firstname>", r.getString("FIRSTNAME"));
			msg = msg.replace("<lastname>", r.getString("LASTNAME"));
			msg = msg.replace("<password>", r.getString("PASS"));
			msg = msg.replace("<username>", SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID = ?", "", userid));
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			msg = msg.replace("<ip>", ip);
			msg = msg.replace("<updatedby>", updatedby);
			Logger.LogServer("Message replace: " + msg);
			request.put("auth", request2);
			request2.put("host", row.get("HOST"));
			request2.put("port", row.get("PORT").toString());
			request2.put("username", row.get("USERNAME"));
			request2.put("password", row.get("PASSWORD"));
			request.put("to", r.getString("EMAIL"));
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
					Logger.LogServer("Email Sent to: " + r.getString("EMAIL").toString());
					return true;
				} else {
					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
							+ object.get("message").toString() + " " + r.getString("EMAIL").toString());
					return false;
				}
			} else {
				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + r.getString("EMAIL").toString());
				return false;
			}
		} catch (Exception e) {
			Logger.LogServer("error: " + e);
			return false;
		}
	  }
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static boolean sendDeactive(String userid,String ip,String updatedby) {
	  try {
			OtherProperties prop = new OtherProperties();
			JSONObject request = new JSONObject();
			JSONObject request2 = new JSONObject();

			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1 ");
			DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '19332' AND PORTAL = 'OPERATOR'");
			DataRow r = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(PASSWORD,?,USERNAME)PASS,U.* FROM TBLUSERS U WHERE USERID = ?", SystemInfo.getDb().getCrypt(),userid);
			
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<firstname>", r.getString("FIRSTNAME"));
			msg = msg.replace("<lastname>", r.getString("LASTNAME"));
			msg = msg.replace("<password>", r.getString("PASS"));
			msg = msg.replace("<username>", SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID = ?", "", userid));
			msg = msg.replace("<date>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE, 'Day,Mon DD, YYYY HH:MI:SS') DATES FROM dual", ""));
			msg = msg.replace("<ip>", ip);
			msg = msg.replace("<updatedby>", updatedby);
			Logger.LogServer("Message replace: " + msg);
			request.put("auth", request2);
			request2.put("host", row.get("HOST"));
			request2.put("port", row.get("PORT").toString());
			request2.put("username", row.get("USERNAME"));
			request2.put("password", row.get("PASSWORD"));
			request.put("to", r.getString("EMAIL"));
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
					Logger.LogServer("Email Sent to: " + r.getString("EMAIL").toString());
					return true;
				} else {
					Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " "
							+ object.get("message").toString() + " " + r.getString("EMAIL").toString());
					return false;
				}
			} else {
				Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + r.getString("EMAIL").toString());
				return false;
			}
		} catch (Exception e) {
			Logger.LogServer("error: " + e);
			return false;
		}
	  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static boolean resendusernamepassword(String email, String password,String username) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1934' AND PORTAL='OPERATOR'");


  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<password>", password);
  	      msg = msg.replace("<username>", username);

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
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static boolean resenduser(String email, String password,String username) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1935' AND PORTAL='OPERATOR'");


  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<password>", password);
  	      msg = msg.replace("<username>", username);

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
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static boolean sendEditAccountVerifyEmail(String email,String fname, String url) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1", new Object[0]);
	      String msg="";

	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '96061' AND PORTAL='OPERATOR'");
	      	  msg = message.get("MESSAGE").toString();
		      msg = msg.replace("<firstname>", fname);
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

	      byte[] apiResponse = client.httpPost((String) row.get("URL"), null,
					headers, null, entity);
			Logger.LogServer((String) row.get("URL"));
	      
	     
	      
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

@SuppressWarnings({ "unchecked", "rawtypes" })
public static boolean sendEditMsisdnVerifyEmail(String email,String fname, String url) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1", new Object[0]);
	      String msg="";

	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '96062' AND PORTAL='OPERATOR'");
	      	  msg = message.get("MESSAGE").toString();
		      msg = msg.replace("<firstname>", fname);
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

	      byte[] apiResponse = client.httpPost((String) row.get("URL"), null,
					headers, null, entity);
			Logger.LogServer((String) row.get("URL"));
	      
	     
	      
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
}
