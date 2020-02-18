package com.psi.wallet.util;


import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.smtp.EmailAddress;
import com.tlc.smtp.EmailMessage;
public class EmailUtils
{

  public static boolean sendClaim(String email,String track,String ordernumber,String fname) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1013, "101300");
		 	    emailMessage.replace(new String []{"<trackingnumber>",track,"<ordernumber>",ordernumber,"<firstname>",fname});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean sendEmail(String email,String track,String ordernumber) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1021, "102100");
		 	    emailMessage.replace(new String []{"<trackingnumber>",track,"<ordernumber>",ordernumber});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean send(String email, String lastname, String firstname,String amount) {
	  try{
		  		EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1014, "101400");
		 	    emailMessage.replace(new String []{"<amount>",LongUtil.toString(Long.parseLong(amount)),"<firstname>",firstname
		 	    		,"<lastname>",lastname});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
	  }
  public static boolean send(String email, String lastname, String firstname,String amount,String operator) {
	  try{
		  		EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1015, "101500");
		 	    emailMessage.replace(new String []{"<amount>",LongUtil.toString(Long.parseLong(amount)),"<firstname>",firstname
		 	    		,"<lastname>",lastname});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean send(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1016, "101600");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean sendCashier(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1017, "101700");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
/*  public static boolean sendApprovedCashier(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1018, "101800");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  */
  public static boolean sendApprovedCashier(String email) {
  try {
  OtherProperties prop = new OtherProperties();
    JSONObject request = new JSONObject();
    JSONObject request2 = new JSONObject();

    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG", new Object[0]);
    DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1018'");


    String msg = message.get("MESSAGE").toString();

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
  
/*  public static boolean sendApproved(String email,String reference) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1019, "101900");
		 	    emailMessage.replace(new String []{"<referencenumber>",reference});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}*/
  
  @SuppressWarnings("unchecked")
public static boolean sendApproved(String email,String reference,String merchantname, String requestor,String amount) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1302' AND PORTAL ='OPERATOR'");


	      String msg = message.get("MESSAGE").toString();
	      msg = msg.replace("<referencenumber>", reference);
	      msg = msg.replace("<merchantname>", merchantname);
	      msg = msg.replace("<requestor>", requestor);
	      msg = msg.replace("<amount>", amount);

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
  public static boolean sendApprovedPayout(String email,String reference,String amount,String fname, String lname) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '13221' AND PORTAL ='OPERATOR'");


  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<referencenumber>", reference);
  	      msg = msg.replace("<amount>", amount);
  	      msg = msg.replace("<firstname>", fname);
  	      msg = msg.replace("<lastname>", lname);
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
  public static boolean sendRejectedPayout(String email,String reference,String firstname, String lastname,String amount) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1323' AND PORTAL ='OPERATOR'");


  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<referencenumber>", reference);
  	      msg = msg.replace("<amount>", amount);
	      msg = msg.replace("<firstname>", firstname);
	      msg = msg.replace("<lastname>", lastname); 
	      
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
/*  public static boolean sendReject(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1022, "102200");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}*/
  
  public static boolean sendReject(String email,String reference, String amount,String rejectedby,String requestor) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1303' AND PORTAL='OPERATOR'");


	      String msg = message.get("MESSAGE").toString();
	      msg = msg.replace("<referencenumber>", reference);
	      msg = msg.replace("<amount>", amount);
	      msg = msg.replace("<rejectedby>", rejectedby);
	      msg = msg.replace("<requestor>", requestor);

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
  
  public static boolean sendRejectManager(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1023, "102300");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean sendCancel(String email,String refnum) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1024, "102400");
		 	    emailMessage.replace(new String []{"<referencenumber>",refnum});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  
  public static boolean sendAllocManager(String accountnumber,String amount,String referenceid) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '13091' AND PORTAL ='OPERATOR'");
	      String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM TBLUSERS WHERE ACCOUNTNUMBER = ? AND USERSLEVEL = 'MANAGER'", "", accountnumber);

	      String msg = message.get("MESSAGE").toString();
	      msg = msg.replace("<referencenumber>", referenceid);
	      msg = msg.replace("<amount>", amount);

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
  
  public static boolean sendAllocFinance(String accountnumber,String amount,String referenceid) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '13092' AND PORTAL ='OPERATOR'");
	      String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWEMAILNOTIF WHERE TYPE ='DIRECALLOC'","");


	      String msg = message.get("MESSAGE").toString();
	      msg = msg.replace("<amount>", amount);
	      msg = msg.replace("<referenceid>", referenceid);
	      msg = msg.replace("<branch>", SystemInfo.getDb().QueryScalar("SELECT BRANCH FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?", "", accountnumber));

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
  public static boolean sendDealloc(String email,String reference,String merchantname, String requestor,String amount) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1310' AND PORTAL ='OPERATOR'");


  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<referencenumber>", reference);
  	      msg = msg.replace("<merchantname>", merchantname);
  	      msg = msg.replace("<requestor>", requestor);
  	      msg = msg.replace("<amount>", amount);

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
  public static boolean sendDeallocOperator(String reference,String merchantname, String requestor,String amount) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '13101' AND PORTAL ='OPERATOR'");
  	     String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID='13101' AND PORTAL = 'OPERATOR'", "");

  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<referencenumber>", reference);
  	      msg = msg.replace("<merchantname>", merchantname);
  	      msg = msg.replace("<requestor>", requestor);
  	      msg = msg.replace("<amount>", amount);

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
  public static boolean sendWalletrequest(String reference,String merchantname, String requestor,String amount) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '13021' AND PORTAL ='OPERATOR'");
  	      String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID='13021' AND PORTAL = 'OPERATOR'", "");

  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<referencenumber>", reference);
  	      msg = msg.replace("<merchantname>", merchantname);
  	      msg = msg.replace("<requestor>", requestor);
  	      msg = msg.replace("<amount>", amount);
  	      msg = msg.replace("<daterequested>", SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD hh:mm') FROM DUAL", ""));

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
  public static boolean sendreversal(String email,String reference,String merchantname, String requestor,String amount) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1312' AND PORTAL ='OPERATOR'");
  	     
  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<referencenumber>", reference);
  	      msg = msg.replace("<merchantname>", merchantname);
  	      msg = msg.replace("<requestor>", requestor);
  	      msg = msg.replace("<amount>", amount);

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
  public static boolean sendreversaloperator(String reference,String merchantname, String requestor,String amount) {
  	    try {
  	    OtherProperties prop = new OtherProperties();
  	      JSONObject request = new JSONObject();
  	      JSONObject request2 = new JSONObject();

  	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1 ", new Object[0]);
  	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '13121' AND PORTAL ='OPERATOR'");
  	     String email = SystemInfo.getDb().QueryScalar("SELECT EMAIL FROM VWNOTIFRECIPIENTS WHERE MODULEID='13121' AND PORTAL = 'OPERATOR'", "");

  	      String msg = message.get("MESSAGE").toString();
  	      msg = msg.replace("<referencenumber>", reference);
  	      msg = msg.replace("<merchantname>", merchantname);
  	      msg = msg.replace("<requestor>", requestor);
  	      msg = msg.replace("<amount>", amount);

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
}
