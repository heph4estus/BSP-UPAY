package com.psi.tariff.config.c;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.config.m.EditTariffPlansGroup;
import com.psi.tariff.config.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditTariffPlansGroupCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				
				EditTariffPlansGroup pingenGroup = new EditTariffPlansGroup();
				
				String groupname = this.params.get("GROUPNAME");
				String type = this.params.get("TYPE");
			
				
				try {
					JSONParser planParser = new JSONParser();
					JSONArray plans;
					Logger.LogServer(this.params.get("PLANID"));
					try{
						if(this.params.get("PLANID")!=null){
							plans = (JSONArray) planParser.parse(this.params.get("PLANID"));
							String[] arrPlans = new String[plans.size()];
							int x=0;
								for (Object m : plans){
									Logger.LogServer(m.toString());
									String i = m.toString();
									arrPlans[x++]=i;
								}
							pingenGroup.setPlanid(arrPlans);
						}
					}
					catch(Exception e){
						Logger.LogServer("NO PLANID SELECTED\n"+e);
					}
						
						pingenGroup.setGroupname(groupname);
						pingenGroup.setType(type);
						pingenGroup.setAuthorizedSession(sess);
						
						if(pingenGroup.update()){
							ObjectState state = new ObjectState("00","Tariff Plans Group Successfully Updated");
							pingenGroup.setState(state);
							JsonView view = new JsonView(pingenGroup);
							return view;
						}
						else{
							ObjectState state = new ObjectState("01","Add Unsuccessfull");
							pingenGroup.setState(state);
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(groupname);
				    		audit.setLog(pingenGroup.getState().getMessage());
				    		audit.setStatus(pingenGroup.getState().getCode());
				    		audit.setUserid(pingenGroup.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(pingenGroup.getAuthorizedSession().getAccount().getUserName());
				    		audit.setSessionid(pingenGroup.getAuthorizedSession().getId());
				    		audit.setBrowser(pingenGroup.getAuthorizedSession().getBrowser());
						    audit.setBrowserversion(pingenGroup.getAuthorizedSession().getBrowserversion());
						    audit.setPortalversion(pingenGroup.getAuthorizedSession().getPortalverion());
						    audit.setOs(pingenGroup.getAuthorizedSession().getOs());
						    audit.setUserslevel(pingenGroup.getAuthorizedSession().getAccount().getGroup().getName());
						    audit.setRequest(this.params.toString());
						    audit.setData(groupname+"|"+type+"|"+this.params.get("PLANID"));
				    		audit.insert();
							JsonView view = new JsonView(pingenGroup);
							return view;
						}
				}catch(Exception e){
					Logger.LogServer(e);
					ObjectState state = new ObjectState("02","Add Unsuccessfull");
					pingenGroup.setState(state);
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(groupname);
		    		audit.setLog(pingenGroup.getState().getMessage());
		    		audit.setStatus(pingenGroup.getState().getCode());
		    		audit.setUserid(pingenGroup.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(pingenGroup.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(pingenGroup.getAuthorizedSession().getId());
		    		audit.setBrowser(pingenGroup.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(pingenGroup.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(pingenGroup.getAuthorizedSession().getPortalverion());
				    audit.setOs(pingenGroup.getAuthorizedSession().getOs());
				    audit.setUserslevel(pingenGroup.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
				    audit.setData(groupname+"|"+type+"|"+this.params.get("PLANID"));
		    		audit.insert();
					JsonView view = new JsonView(pingenGroup);
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
			Logger.LogServer(e);
	} catch (Exception e) {
		UISession u = new UISession(null);
	    u.setState(new ObjectState("TLC-3902-01"));
	    v = new SessionView(u);
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "EDITTARIFFPLANSGROUP";
	}

	@Override
	public int getId() {
		return 8040;
	}

}
