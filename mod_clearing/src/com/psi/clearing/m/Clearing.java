package com.psi.clearing.m;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.clearing.utils.HttpClientHelper;
import com.psi.clearing.utils.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class Clearing extends Model{

	protected String operatoraccountnumber;
	protected String outletaccountnumber;
	protected String companyaccountnumber;
	protected String clearingtype;
	protected String id;
	protected String password;
	protected String[] referenceids;

	@SuppressWarnings("unchecked")
	public boolean cleartrans() throws ParseException, Exception {

		OtherProperties prop = new OtherProperties();
		String username = SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID=?", "", this.id);
		
	    String auth = username+":"+this.password;
	    byte[] encodedBytes = Base64.encodeBase64(auth.getBytes());
		
		HttpClientHelper client = new HttpClientHelper();
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", prop.getType());
	    headers.put("token", prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    headers.put("Authorization", "basic "+new String(encodedBytes));
	    
	    System.out.println("basic "+new String(encodedBytes));
	    
		String[] refids = getReferenceids();
		JSONArray refArr = new JSONArray();
		int dr = 0;
		
		try {
			String[] arrayOfRefs;
			int j = (arrayOfRefs = refids).length;
			for (int i = 0; i < j; i++)
			{
				String s = arrayOfRefs[i];
				refArr.add(s);
			}
			for (int i = 0; i < refArr.size(); i++)
			{
				String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(9000) FROM DUAL", "0");
								
				byte[] apiResponse = client.httpPut(prop.getUrl()+this.outletaccountnumber+"/funds/clearing?request-id="+reqid+"&clearing-id="+refids[i], null, headers, null, null);
				Logger.LogServer("clear url:"+prop.getUrl()+this.outletaccountnumber+"/funds/clearing?request-id="+reqid+"&clearing-id="+refids[i]);
				Logger.LogServer("clear response:"+new String(apiResponse, "UTF-8"));
					    
				if(apiResponse.length>0){
					JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
					if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
						//this.setState(new ObjectState("00",object.get("message").toString()));
						//return true;
						DataRow res = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTRANSACTIONSPOS TP INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON TP.REFERENCEID=T.REFERENCEID WHERE BRAND IN (SELECT ACCOUNTNUMBER FROM TBLCOMPANYTARIFF CT INNER JOIN TBLTARIFFGROUP TG ON CT.TARIFFGROUP=TG.ID WHERE GROUPNAME='shops') AND TOACCOUNT=? AND T.REFERENCEID=?", new Object[] { this.outletaccountnumber, refids[i] });
						
						StringBuilder query = new StringBuilder("BEGIN\n");
						query.append("INSERT INTO TBLCLEARING (REFERENCEID,AMOUNT,OUTLET,COMPANY,REQUESTID,CLEARINGTYPE) VALUES(?,?,?,?,?,?);\n");
						query.append("END;\n");
				        dr = SystemInfo.getDb().QueryUpdate(query.toString(),refids[i], res.getString("AMOUNT"),this.outletaccountnumber, this.companyaccountnumber, reqid, this.clearingtype);

					}else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
						this.setState(new ObjectState("05",object.get("message").toString()));
						return false;
					}else{
						this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
						return false;
					}
				}else{
					this.setState(new ObjectState("99","System is busy"));
					return false;
				}

				
			}

			if (dr > 0) {
				return true;
			}

		} catch (Exception e){}
		return false;


	}
	
	@SuppressWarnings("unchecked")
	public boolean deallocate() throws ParseException, Exception{
		System.out.println("deallocation");
		OtherProperties prop = new OtherProperties();
		String username = SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID=?", "", this.id);
		
	    String auth = username+":"+this.password;
	    byte[] encodedBytes = Base64.encodeBase64(auth.getBytes());
		
		HttpClientHelper client = new HttpClientHelper();
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", prop.getType());
	    headers.put("token", prop.getToken());
	    headers.put("Authorization", "basic "+new String(encodedBytes));
	    headers.put("X-Forwarded-For","127.0.0.1");
	    
	    
	    String[] refids = getReferenceids();
		JSONArray refArr = new JSONArray();
		int dr = 0;
		
		try {
			String[] arrayOfRefs;
			int j = (arrayOfRefs = refids).length;
			for (int i = 0; i < j; i++)
			{
				String s = arrayOfRefs[i];
				refArr.add(s);
			}
			for (int i = 0; i < refArr.size(); i++)
			{
				String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(9000) FROM DUAL", "0");
			
				byte[] apiResponse = client.httpDeleteWithBody(prop.getUrl()+this.outletaccountnumber+"/funds/clearing?request-id="+reqid+"&clearing-id="+refids[i], null, headers, null, null);
				Logger.LogServer("deallocate url:"+prop.getUrl()+this.outletaccountnumber+"/funds/clearing?request-id="+reqid+"&clearing-id="+refids[i]);
				Logger.LogServer("deallocate response:"+new String(apiResponse, "UTF-8"));
				
				if(apiResponse.length>0){
					JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
					if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			
						DataRow res = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLTRANSACTIONSPOS TP INNER JOIN ADMDBMC.TBLTRANSACTIONS T ON TP.REFERENCEID=T.REFERENCEID WHERE BRAND IN (SELECT ACCOUNTNUMBER FROM TBLCOMPANYTARIFF CT INNER JOIN TBLTARIFFGROUP TG ON CT.TARIFFGROUP=TG.ID WHERE GROUPNAME='shops') AND TOACCOUNT=? AND T.REFERENCEID=?", new Object[] { this.outletaccountnumber, refids[i] });
				
						StringBuilder query = new StringBuilder("BEGIN\n");
						query.append("INSERT INTO TBLCLEARING (REFERENCEID,AMOUNT,OUTLET,COMPANY,REQUESTID,CLEARINGTYPE) VALUES(?,?,?,?,?,?);\n");
						query.append("END;\n");
				        dr = SystemInfo.getDb().QueryUpdate(query.toString(),refids[i], res.getString("AMOUNT"),this.outletaccountnumber, this.companyaccountnumber, reqid, this.clearingtype);

					}else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
						this.setState(new ObjectState("05",object.get("message").toString()));
						return false;
					}else{
						this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
						return false;
					}
				}else{
					this.setState(new ObjectState("99","System is busy"));
					return false;
				}
			}

			if (dr > 0) {
				return true;
			}

		} catch (Exception e){}
		return false;
	}
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.id,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}

	public String getOperatorAccountNumber(){
		return this.operatoraccountnumber;
	}

	public void setOperatorAccountNumber(String accntno){
		this.operatoraccountnumber = accntno;
	}
	
	

	public String getCompanyaccountnumber() {
		return companyaccountnumber;
	}

	public void setCompanyaccountnumber(String companyaccountnumber) {
		this.companyaccountnumber = companyaccountnumber;
	}

	public String getOutletaccountnumber() {
		return outletaccountnumber;
	}

	public void setOutletaccountnumber(String outletaccountnumber) {
		this.outletaccountnumber = outletaccountnumber;
	}

	public String getClearingtype() {
		return clearingtype;
	}

	public void setClearingtype(String clearingtype) {
		this.clearingtype = clearingtype;
	}

	public String[] getReferenceids() {
		return referenceids;
	}

	public void setReferenceids(String[] referenceids) {
		this.referenceids = referenceids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
