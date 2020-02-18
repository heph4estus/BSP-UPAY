 package com.psi.dashboard.test;

import com.psi.dashboard.c.DailySalesAirtimeCommand;
import com.psi.dashboard.c.DailySalesBillPaymentCommand;
import com.tlc.gui.modules.common.IView;
 

 
 public class Tester
 {
   public static void main(String[] args)
   {
	
	String req = "{\"AccountNumber\":\"141234589775\",\"da\":\"2\",\"Id\":\"2\"}";
		   
     JsonRequest json = new JsonRequest(req);
     RegisteredProduct(json);
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
	   DailySalesBillPaymentCommand rpndgac = new DailySalesBillPaymentCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }

   
   
 }
