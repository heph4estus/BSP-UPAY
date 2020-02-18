package com.psi.clearing.utils;


import com.tlc.common.Logger;
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
 

}
