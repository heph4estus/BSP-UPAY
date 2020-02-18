package com.psi.business.v2.c;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.business.util.RedisClient;
import com.psi.business.v.JsonView;
import com.psi.business.v2.m.InquireData;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class RequiredDocsCommand extends UICommand{
	static RedisClient redis = new RedisClient();
	@SuppressWarnings("unchecked")
	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String msisdn = this.params.get("Msisdn");
				String document = this.params.get("Document");
				 	
				InquireData reg = new InquireData();
				
				reg.setAuthorizedSession(sess);

				if(redis.exists("/"+msisdn+"/required/doc")){
					redis.del("/"+msisdn+"/required/doc");
				}

				String rstatus = redis.setex("/"+msisdn+"/required/doc", 1800, document);
				String json = redis.get("/"+msisdn+"/required/doc");
				if(rstatus.equalsIgnoreCase("OK")){
					reg.setState(new ObjectState("00", "Successfully"));
					return new JsonView(reg);
				}else{
					reg.setState(new ObjectState("01", "Unable to Upload image"));
					return new JsonView(reg);
				}
				
			}else{		
				UISession u = new UISession(null);
			    u.setState(new ObjectState("TLC-3902-01"));
			    v = new SessionView(u);
			}
		}catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
		    u.setState(new ObjectState("TLC-3902-01"));
		    v = new SessionView(u);
			Logger.LogServer(e);
	} catch (Exception e) {
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "UPLOADREQUIREDDOC";
	}

	@Override
	public int getId() {
		return 0;
	}

}
