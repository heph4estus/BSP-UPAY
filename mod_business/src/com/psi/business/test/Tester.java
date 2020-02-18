 package com.psi.business.test;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.business.c.NewBusinessCommand;
import com.psi.business.util.Business;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.UICommand;
 

 
 public class Tester extends UICommand
 {
   public static void main(String[] args)
   {
	   //newdebt 
	   String req = "{\"TO\":\"HOMEKING\",\"BranchName\":\"HOMEKING\",\"Address\":\"1st, Mezzanine & 2nd Floors, Filhigh Trading Bldg, 1923-25 Angel Linao St.\",\"City\":\"ERMITA\",\"Province\":\"NCR CITY OF MANILA FIRST DISTRICT\",\"Country\":\"PHILIPPINES\","
	   		+ "\"ZipCode\":\"\",\"ContactNumber\":\"(02) 242 7801\",\"ImgProof\":\"\",\"XCoordinate\":\"\",\"YCoordinate\":\"\","
	   		+ "\"Monday\":\"\",\"Tuesday\":\"\",\"Wednesday\":\"\",\"Thursday\":\"\",\"Friday\":\"\",\"Saturday\":\"\",\"Sunday\":\"\",\"AccountNumber\":\"478921234568\",\"IsWithHoldingTax\":\"\"}";
		   	   
	   
	   //fulfilldebt
	  // String req = "{\"REFERENCEID\":\"1234567890\"}";
	/*   System.out.println(SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", ""));
		
	   DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLBUSINESS WHERE ACCOUNTNUMBER='349312345918'");
	   //  System.out.println(""+r.size());
	     if(r.isEmpty()){
	    	 System.out.println("empty");
	     }else{
	    	 for(DataRow rr : r){
	    		 System.out.println(rr.getString("PROOFADDRESS"));
	    	 }
	     }*/
//	   JsonRequest json = new JsonRequest(req);
//     RegisteredProduct(json);
//	   Tester rpndgac = new Tester();
//	   JsonRequest json = new JsonRequest(req);
//	     rpndgac.setRequest(json); 
//	 
//	     IView view = rpndgac.execute();
//	     System.out.println(view.render());
//Business b = new Business();
//b.setYcoordinate("test");
//b.setXcoordinate("test1");
//System.out.print(b.json(b));
	   String strArray = "[{\"percent\":\"1.50\",\"id\":\"4\",\"productname\":\"Cotabato Light and Power Co. : Aboitiz Power (ABPWR)\",\"productcode\":\"COTABATO-POWER\",\"category\":\"ELECTRICITY\",\"datefrom\":\"2019-09-10\",\"dateto\":\"2019-09-10\",\"fixed\":\"\"},{\"percent\":\"1.50\",\"id\":\"12\",\"productname\":\"STERLING BANK OF ASIA\",\"productcode\":\"STERLINGBANK\",\"category\":\"FINANCIAL SERVICES\",\"datefrom\":\"2019-09-10\",\"dateto\":\"2019-09-10\",\"fixed\":\"\"}]";
		
	   try {
		   JSONArray object = (JSONArray)new JSONParser().parse(strArray);
		   for(int i = 0; i < object.size(); i++ ){
			   HashMap<String,Object> com = (HashMap<String, Object>) object.get(i);
			   System.out.println(com.get("percent").toString());
			   System.out.println(com.get("productcode").toString());
		   }
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		

   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
	   NewBusinessCommand rpndgac = new NewBusinessCommand();

     rpndgac.setRequest(json); 
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }

@Override
public IView execute() {
	// TODO Auto-generated method stub
	System.out.println(this.params.get("TO"));

	return null;
}

@Override
public String getKey() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int getId() {
	// TODO Auto-generated method stub
	return 0;
}

   
   
 }
