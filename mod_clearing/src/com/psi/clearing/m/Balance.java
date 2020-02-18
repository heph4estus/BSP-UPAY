package com.psi.clearing.m;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.clearing.utils.HttpClientHelper;
import com.psi.clearing.utils.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.DbWrapper;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class Balance extends Model{
	public static final String PROP_CURRENT = "CURRENT";
	public static final String PROP_AVAILABLE = "AVAILABLE";
	  String currentbal;
	  String availablebal;
	  
	  public boolean balance(String accntno)
	    throws ParseException, Exception
	  {
	    OtherProperties prop = new OtherProperties();
	    
	    //DataRow res = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER=?", new Object[] { accntno });
	    
	    HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token", prop.getToken());
	    headers.put("X-Forwarded-For", "127.0.0.1");
	    byte[] apiResponse = client.httpGet(prop.getUrl() + accntno + "/wallet", null, headers, null);
	    Logger.LogServer(" response : " + new String(apiResponse));
	    if (apiResponse.length > 0)
	    {
	      JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
	      
	      JSONParser p = new JSONParser();
	      JSONObject bal = (JSONObject)p.parse(object.toJSONString());
	      if ((bal.get("code").toString().equals("0")) || (bal.get("code").toString().equals(Integer.valueOf(0))))
	      {
	        ArrayList<Object> pockets = (ArrayList)bal.get("pockets");
	        HashMap<String, Object> pocket = (HashMap)pockets.get(0);
	        if (!((pocket.get("pocket-id").toString().equals("0")) || (pocket.get("pocket-id").toString().equals(Integer.valueOf(0)))))
	        {
	          setCurrentbal(pocket.get("current-balance").toString());
	          setAvailablebal(pocket.get("available-balance").toString());
	          setState(new ObjectState("00", bal.get("message").toString()));
	          return true;
	        }
	        HashMap<String, Object> pocket1 = (HashMap)pockets.get(1);
	        if (!((pocket1.get("pocket-id").toString().equals("0")) || (pocket1.get("pocket-id").toString().equals(Integer.valueOf(0)))))
	        {
	          setCurrentbal(pocket1.get("current-balance").toString());
	          setAvailablebal(pocket1.get("available-balance").toString());
	          setState(new ObjectState("00", bal.get("message").toString()));
	          return true;
	        }
	        setCurrentbal(pocket.get("current-balance").toString());
	        setAvailablebal(pocket.get("available-balance").toString());
	        setState(new ObjectState("00", bal.get("message").toString()));
	        return true;
	      }
	      setState(new ObjectState(bal.get("code").toString(), bal.get("message").toString()));
	      return false;
	    } else {
	    	setState(new ObjectState("99", "System is busy"));
		    return false;
	    }
	    
	  }
	  
	  public String getCurrentbal()
	  {
	    return this.currentbal;
	  }
	  
	  public void setCurrentbal(String currentbal)
	  {
	    this.props.put("CURRENT", currentbal);
	    this.currentbal = currentbal;
	  }
	  
	  public String getAvailablebal()
	  {
	    return this.availablebal;
	  }
	  
	  public void setAvailablebal(String availablebal)
	  {
	    this.props.put("AVAILABLE", availablebal);
	    this.availablebal = availablebal;
	  }
}
