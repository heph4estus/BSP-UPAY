 package com.psi.customer.support.test;
 

import com.psi.customer.support.c.VoidFailedTransCommand;
import com.tlc.gui.modules.common.IView;
 
 public class Tester
 {
   public static void main(String[] args)
   {
	    String req = "{\"ReferenceId\":\"1234567890\",\"AccountNumber\":\"478921234568\"}";
	   //String req = "{\"Region\":\"AGUSAN DEL NORTE\",\"City\":\"BUENAVISTA\"}";
	  //String req = "{\"TransactionType\":\"AIRTIME\",\"ThirdPartyAccountNumber\":\"09564751930\",\"BranchAccountNumber\":\"923459134915\"}"; 
	  // String req = "{\"ReferenceId\":\"1051234567890\",\"TraceId\":\"1234567890\",\"ReceiptValidation\":\"1234567890\",\"AccountNumber\":\"1234567890\"}";  
     JsonRequest json = new JsonRequest(req);

    RegisteredProduct(json);

     
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
	   //SearchByTypeColCommand rpndgac = new SearchByTypeColCommand();
	   //SearchByReferenceColCommand rpndgac = new SearchByReferenceColCommand();
	   VoidFailedTransCommand rpndgac = new VoidFailedTransCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
   
  
 }
