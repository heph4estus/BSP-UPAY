package com.provider.management.c;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.provider.management.m.AuditTrail;
import com.provider.management.m.ManageProvider;
import com.provider.management.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class SetProviderRevenueCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;	    
	    SessionView v = null;	    
	    try{
	    	sess = ExistingSession.parse(this.reqHeaders);	    
			  if(sess.exists()) {
					String type = this.params.get("Type");
					String provider = this.params.get("Provider");
					String category = this.params.get("Category");
					String revenue = this.params.get("Products");
					ManageProvider changestat = new ManageProvider();

					changestat.setAuthorizedSession(sess);
					
					try {
						JSONParser addserviceParser = new JSONParser();
						JSONArray addproducts;
						try{
							if(this.params.get("Products")!=null){
								addproducts = (JSONArray) addserviceParser.parse(this.params.get("Products"));
								changestat.setProducts(addproducts);
							}
						}
						catch(Exception e){
							Logger.LogServer("NO ADD SERVICES SELECTED\n"+e);
						}
						
						changestat.setRevenue(revenue);
						changestat.setType(type);
						changestat.setProvider(provider);
						changestat.setCategory(category);	
						if(type.equals("AIRTIME")){
							if(changestat.setrevenueairtime()){
								ObjectState state = new ObjectState("00","Successfully set provider revenue");
								changestat.setState(state);
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(changestat.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(type);
					    		audit.setLog(changestat.getState().getMessage());
					    		audit.setStatus(changestat.getState().getCode());
					    		audit.setUserid(changestat.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(changestat.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(changestat.getAuthorizedSession().getId());
					    		audit.setBrowser(changestat.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(changestat.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(changestat.getAuthorizedSession().getPortalverion());
							    audit.setOs(changestat.getAuthorizedSession().getOs());
							    audit.setUserslevel(changestat.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(type+"|"+provider+"|"+category+"|"+revenue);
							    audit.insert();
								JsonView view = new JsonView(changestat);
								return view;
							}
							else{
								ObjectState state = new ObjectState("01","Unable to set provider revenue");
								changestat.setState(state);
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(changestat.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(type);
					    		audit.setLog(changestat.getState().getMessage());
					    		audit.setStatus(changestat.getState().getCode());
					    		audit.setUserid(changestat.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(changestat.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(changestat.getAuthorizedSession().getId());
					    		audit.setBrowser(changestat.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(changestat.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(changestat.getAuthorizedSession().getPortalverion());
							    audit.setOs(changestat.getAuthorizedSession().getOs());
							    audit.setUserslevel(changestat.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(type+"|"+provider+"|"+category+"|"+revenue);
							    audit.insert();
								JsonView view = new JsonView(changestat);
								return view;
							}
						}else if(type.equals("CASHCARD")){
							if(changestat.setrevenueemoney()){
								ObjectState state = new ObjectState("00","Successfully set provider revenue");
								changestat.setState(state);
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(changestat.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(type);
					    		audit.setLog(changestat.getState().getMessage());
					    		audit.setStatus(changestat.getState().getCode());
					    		audit.setUserid(changestat.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(changestat.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(changestat.getAuthorizedSession().getId());
					    		audit.setBrowser(changestat.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(changestat.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(changestat.getAuthorizedSession().getPortalverion());
							    audit.setOs(changestat.getAuthorizedSession().getOs());
							    audit.setUserslevel(changestat.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(type+"|"+provider+"|"+category+"|"+revenue);
							    audit.insert();
								JsonView view = new JsonView(changestat);
								return view;
							}
							else{
								ObjectState state = new ObjectState("01","Unable to set provider revenue");
								changestat.setState(state);
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(changestat.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(type);
					    		audit.setLog(changestat.getState().getMessage());
					    		audit.setStatus(changestat.getState().getCode());
					    		audit.setUserid(changestat.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(changestat.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(changestat.getAuthorizedSession().getId());
					    		audit.setBrowser(changestat.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(changestat.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(changestat.getAuthorizedSession().getPortalverion());
							    audit.setOs(changestat.getAuthorizedSession().getOs());
							    audit.setUserslevel(changestat.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(type+"|"+provider+"|"+category+"|"+revenue);
							    audit.insert();
								JsonView view = new JsonView(changestat);
								return view;
							}
						}else{
							if(changestat.setrevenuebills()){
								ObjectState state = new ObjectState("00","Successfully set provider revenue");
								changestat.setState(state);
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(changestat.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(type);
					    		audit.setLog(changestat.getState().getMessage());
					    		audit.setStatus(changestat.getState().getCode());
					    		audit.setUserid(changestat.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(changestat.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(changestat.getAuthorizedSession().getId());
					    		audit.setBrowser(changestat.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(changestat.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(changestat.getAuthorizedSession().getPortalverion());
							    audit.setOs(changestat.getAuthorizedSession().getOs());
							    audit.setUserslevel(changestat.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(type+"|"+provider+"|"+category+"|"+revenue);
							    audit.insert();
								JsonView view = new JsonView(changestat);
								return view;
							}
							else{
								ObjectState state = new ObjectState("01","Unable to set provider revenue");
								changestat.setState(state);
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(changestat.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(type);
					    		audit.setLog(changestat.getState().getMessage());
					    		audit.setStatus(changestat.getState().getCode());
					    		audit.setUserid(changestat.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(changestat.getAuthorizedSession().getAccount().getUserName());
					    		audit.setSessionid(changestat.getAuthorizedSession().getId());
					    		audit.setBrowser(changestat.getAuthorizedSession().getBrowser());
							    audit.setBrowserversion(changestat.getAuthorizedSession().getBrowserversion());
							    audit.setPortalversion(changestat.getAuthorizedSession().getPortalverion());
							    audit.setOs(changestat.getAuthorizedSession().getOs());
							    audit.setUserslevel(changestat.getAuthorizedSession().getAccount().getGroup().getName());
							    audit.setRequest(this.params.toString());
							    audit.setData(type+"|"+provider+"|"+category+"|"+revenue);
							    audit.insert();
								JsonView view = new JsonView(changestat);
								return view;
							}
						}
					}catch(Exception e){
						Logger.LogServer(e);
						ObjectState state = new ObjectState("99","System is busy");
						changestat.setState(state);
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(changestat.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(type);
			    		audit.setLog(changestat.getState().getMessage());
			    		audit.setStatus(changestat.getState().getCode());
			    		audit.setUserid(changestat.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(changestat.getAuthorizedSession().getAccount().getUserName());
			    		audit.setSessionid(changestat.getAuthorizedSession().getId());
			    		audit.setBrowser(changestat.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(changestat.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(changestat.getAuthorizedSession().getPortalverion());
					    audit.setOs(changestat.getAuthorizedSession().getOs());
					    audit.setUserslevel(changestat.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setRequest(this.params.toString());
					    audit.setData(type+"|"+provider+"|"+category+"|"+revenue);
					    audit.insert();
						JsonView view = new JsonView(changestat);
						return view;
						
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
 
}
return v;
	}

	@Override
	public String getKey() {
		return "SETPROVIDERREVENUE";
	}

	@Override
	public int getId() {
		return 5505;
	}

}
