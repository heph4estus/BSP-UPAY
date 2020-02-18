package com.psi.tariff.group.c;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.tariff.group.m.ConfigTariffGroup;
import com.psi.tariff.group.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ConfigTariffGroupCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				
				ConfigTariffGroup pingenGroup = new ConfigTariffGroup();
				
				String company = this.params.get("Company");
				String branch = this.params.get("Branches");
				
				try {
					JSONParser planParser = new JSONParser();
					JSONParser brancparser = new JSONParser();
					JSONArray plans,branches;
					try{
						if(this.params.get("TariffGroup")!=null){
							plans = (JSONArray) planParser.parse(this.params.get("TariffGroup"));
							String[] arrPlans = new String[plans.size()];
							int x=0;
								for (Object m : plans){
									Logger.LogServer(m.toString());
									String i = m.toString();
									arrPlans[x++]=i;
								}
							pingenGroup.setTariffgroup(arrPlans);
						}
					}
					catch(Exception e){
						Logger.LogServer("NO TARIFF GROUP SELECTED\n"+e);
					}
					
					try{
						if(this.params.get("Branches")!=null){
							branches = (JSONArray) brancparser.parse(this.params.get("Branches"));
							String[] arrBranches = new String[branches.size()];
							int x=0;
								for (Object m : branches){
									Logger.LogServer(m.toString());
									String i = m.toString();
									arrBranches[x++]=i;
								}
							pingenGroup.setBranches(arrBranches);
						}
					}
					catch(Exception e){
						Logger.LogServer("NO BRANCHES SELECTED\n"+e);
					}
						
						pingenGroup.setCompany(company);
						pingenGroup.setBranch(branch);
						pingenGroup.setTariffaudit(this.params.get("TariffGroup"));
						pingenGroup.setBranchesaudit(this.params.get("Branches"));
						pingenGroup.setAuthorizedSession(sess);
						
						if(pingenGroup.update()){
							ObjectState state = new ObjectState("00","Tariff Group Successfully Updated");
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
				    		audit.setEntityid(company);
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
						    audit.setData(company+"|"+branch+"|"+this.params.get("TariffGroup")+"|"+this.params.get("Branches"));
				    		
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
		    		audit.setEntityid(company);
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
				    audit.setData(company+"|"+branch+"|"+this.params.get("TariffGroup")+"|"+this.params.get("Branches"));
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
		return "CONFIGTARIFFGROUP";
	}

	@Override
	public int getId() {
		return 1789;
	}

}
