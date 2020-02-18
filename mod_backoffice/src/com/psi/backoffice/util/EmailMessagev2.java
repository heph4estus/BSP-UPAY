package com.psi.backoffice.util;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.psi.http.rest.common.RestClient;
import com.psi.http.rest.common.RestResponse;
import com.psi.http.rest.common.RestSettings;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;

public class EmailMessagev2 {
	protected ArrayList<String> destination = new ArrayList<String>();
	protected String title="Semen Indonesia";
	protected String body;
	protected Server server = new Server();
	protected String sender;
	static RestClient proxy =null;
	
	public EmailMessagev2(){
		super();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS=1", new Object[0]);
		RestSettings cfg= new RestSettings();
		cfg.setUrl("");
		cfg.setHost("localhost");
		cfg.setTimeout(10000);
		cfg.setUrl(row.getString("URL").toString());
		this.fromSender(row.getString("SENDERDETAILS").toString());
		this.server.host = row.getString("HOST").toString();
		this.server.port = Integer.parseInt(row.getString("PORT").toString());
		this.server.username = row.getString("USERNAME").toString();
		this.server.password = row.getString("PASSWORD").toString();
		try {
			proxy= new RestClient(cfg);
			proxy.addHeader("Content-Type", "application/json");
		} catch (Exception e) {
			proxy=null;
		}
	}
	public EmailMessagev2  addDestination(String dest){
		this.destination.add(dest);
		return this;
	}
	
	public EmailMessagev2 forDestination(ArrayList<String> dest){
		this.destination = dest;
		return this;
	}
	
	public EmailMessagev2 fromSender(String sender){
		this.sender=sender;
		return this;
	}
	
	public EmailMessagev2 withSubject(String title){
		this.title=title;
		return this;
	}
	
	public EmailMessagev2 withMessage(String message){
		this.body=message;
		return this;
	}
	
	public boolean send() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(String dest: destination){
					sendProxy(dest);	
				}
				
				
			}
		});
		t.start();
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private void sendProxy(String dest){
		JSONObject req = new JSONObject();
		JSONObject req2 = new JSONObject();
		
		  req.put("auth", req2);
	      req2.put("host", this.server.host);
	      req2.put("port", this.server.port);
	      req2.put("username", this.server.username);
	      req2.put("password", this.server.password);
	      req.put("to", dest);
	      req.put("subject", this.title);
	      req.put("body", this.body);

		proxy.addHeader("Content-Type","application/json");
		
		try {
			Logger.LogMt(req.toJSONString());
			RestResponse res = proxy.post(req.toJSONString());
			Logger.LogMt( " " + res.getData());
		} catch (Exception e) {
			Logger.LogServer(e);
		}
	}
	
	
}
