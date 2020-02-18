package com.psi.keyaccount.util;


import com.tlc.common.Logger;
import com.tlc.smtp.EmailAddress;
import com.tlc.smtp.EmailMessage;

public class EmailUtils
{

	public static boolean sendEmail(String email,String fname, String lname, String password,String username) {
		  try{
			 
			       
			         EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 7010, "701000");
			 	    emailMessage.replace(new String []{"<firstname>",fname, 
			 	    		"<lastname>",lname,
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
  

}
