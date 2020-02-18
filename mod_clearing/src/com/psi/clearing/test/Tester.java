package com.psi.clearing.test;

import com.psi.clearing.c.BalanceInquiryCommand;
import com.psi.clearing.c.ClearedCollectionCommand;
import com.psi.clearing.c.TransactionCollectionCommand;
import com.psi.clearing.c.TransactionClearingCommand;
import com.tlc.gui.modules.common.IView;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String json = "{\"AccountNumber\":\"444841234651\",\"DateFrom\":\"2018-06-22\",\"DateTo\":\"2018-06-25\"}";
		JsonRequest jsonreq = new JsonRequest(json);
		
	/*	String json2 = "{\"LinkId\":\"2364\",\"Password\":\"123456789\",\"Company\":\"181234591346\",\"ReferenceIds\":[\"1051806080000525\",\"1051806050000593\"],\"Type\":\"CLEARING\",\"Outlet\":\"444841234651\"}";
		JsonRequest jsonreq2 = new JsonRequest(json2);*/
		
		Cleared(jsonreq);
	}
	
	public static void BalanceInquiry(JsonRequest json)
	  {
	    BalanceInquiryCommand bal = new BalanceInquiryCommand();
	    bal.setRequest(json);
	    
	    IView view = bal.execute();
	    System.out.println(view.render());
	  }
	
	public static void RemitClaimsCollection(JsonRequest json)
	  {
		TransactionClearingCommand col = new TransactionClearingCommand();
	    col.setRequest(json);
	    
	    IView view = col.execute();
	    System.out.println(view.render());
	  }
	
	public static void RemittanceClearing(JsonRequest json)
	  {
		TransactionCollectionCommand claim = new TransactionCollectionCommand();
	    claim.setRequest(json);
	    
	    IView view = claim.execute();
	    System.out.println(view.render());
	  }
	
	public static void Cleared(JsonRequest json)
	  {
		ClearedCollectionCommand claim = new ClearedCollectionCommand();
	    claim.setRequest(json);
	    
	    IView view = claim.execute();
	    System.out.println(view.render());
	  }

}
