package com.psi.tariff.config.test;

import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.tariff.config.c.TariffPlansGroupColCommand;
import com.tlc.common.Logger;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.PluginHeaders;


public class main {
	
	
	public static void main(String[] args){
		
		
		//regUser();
		JSONParser planParser = new JSONParser();
		JSONArray plans = null;
		try {
			plans = (JSONArray) planParser.parse("[\"141-TBLAMLACCOUNTTYPERECEIVE\",\"202|TBLAMLACCOUNTTYPESEND\",\"161|TBLAMLACCOUNTTYPERECEIVE\"]");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] arrPlans = new String[plans.size()];
		int x=0;
			for (Object m : plans){
				Logger.LogServer(m.toString());
				String i = m.toString();
				arrPlans[x++]=i;
			}
		
		String[] plansArr = arrPlans;
		JSONArray plansJsonArr = new JSONArray();
		
		for(String m : plansArr){
			plansJsonArr.add(m);
		}
		//System.out.println(plansJsonArr);
		
		 for(int i = 0; i < plansJsonArr.size() ; i++){
			 String t = plansArr[i];
			 String[] parts = t.split("-");
			
			 System.out.println("one"+parts[0]);
			 System.out.println("two"+parts[1]);
			}
		 
		
		
		
	}
	
	
	public static void regUser(){
		System.out.println("START");
		String data = "{\"GroupName\":\"TEST\",\"DateTo\":\"2016-06-22\",\"BranchCode\":\"ALL\"}";
		JsonRequest json = new JsonRequest(data);

		String auth = "VExDLlNIRVJXSU46OXZwaWRkY2Y0bnI3MTdzcDhyMDEyNTQzbTU6ck8wQUJYTnlBQ0ZqYjIwdWRHeGpMbWQxYVM1dGIyUjFiR1Z6TG5ObGMzTnBiMjR1Vkc5clpXNnoyK1NBQnNBOGxnSUFCVWtBQmxWelpYSkpaRXdBRGtWNGNHbHlZWFJwYjI1RVlYUmxkQUFRVEdwaGRtRXZkWFJwYkM5RVlYUmxPMHdBQ1Vsd1FXUmtjbVZ6YzNRQUVreHFZWFpoTDJ4aGJtY3ZVM1J5YVc1bk8wd0FDVk5sYzNOcGIyNUpSSEVBZmdBQ1RBQUlWWE5sY201aGJXVnhBSDRBQW5od0FBQUFBWE55QUE1cVlYWmhMblYwYVd3dVJHRjBaV2hxZ1FGTFdYUVpBd0FBZUhCM0NBQUFBVXNFQjl5RmVIUUFDVEV5Tnk0d0xqQXVNWFFBR2psMmNHbGtaR05tTkc1eU56RTNjM0E0Y2pBeE1qVTBNMjAxZEFBTFZFeERMbE5JUlZKWFNVND1AMTI3LjAuMC4x";

		PluginHeaders h = new PluginHeaders();

		TariffPlansGroupColCommand pndgmessage = new TariffPlansGroupColCommand();
		//h.put("authorization", auth);
		pndgmessage.setRequest(json);
		pndgmessage.setHeaders(h);

		IView v = pndgmessage.execute();
		System.out.println(v.render());
		System.out.println("END");
		
	}
	
	
	
	
	

}
