package com.psi.role.management.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.role.management.m.NewPingenGroup;
import com.psi.role.management.m.RegisteredPingenGroup;
import com.psi.role.management.v.JsonView;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class NewPingenCommand extends UICommand{

	@Override
	public IView execute() {
		String name = this.params.get("GROUPNAME");
		int sessionTimeout = (Integer.parseInt(this.params.get("SESSIONTIMEOUT")));
		int passwordExpiry = (Integer.parseInt(this.params.get("PASSWORDEXPIRY")));
		int minPassword = (Integer.parseInt(this.params.get("MINPASSWORD")));
		int passwordHistory = (Integer.parseInt(this.params.get("PASSWORDHISTORY")));
		int maxAllocation = (Integer.parseInt(this.params.get("MAXALLOCATIONPERDAY")));
		int searchRange = (Integer.parseInt(this.params.get("SEARCHRANGE")));
		String homepage = this.params.get("HOMEPAGE");
		String portal = this.params.get("PORTAL");
				
		  ExistingSession sess = null;
		    
		    SessionView v = null;
		    
		    try{
		     
		     
		    sess = ExistingSession.parse(this.reqHeaders);
		   		    
		    
        if(sess.exists()) {
			NewPingenGroup pingenGroup = new NewPingenGroup(name);
			RegisteredPingenGroup group = new RegisteredPingenGroup(name);
			pingenGroup.setAuthorizedSession(sess);
			group.setPortal(portal);
			if(group.duplicateEntry()){
				ObjectState state = new ObjectState("10","User level is already registered.");
				pingenGroup.setState(state);
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(name);
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
			    audit.setData("USERSLEVEL:"+name+"|HOMEPAGE:"+homepage+"|MINPASSWORD:"+minPassword);
	    		audit.insert();
				JsonView view = new JsonView(pingenGroup);
				return view;
			}
			
			
					try{
						pingenGroup.setSessionTimeout(sessionTimeout);
						pingenGroup.setPasswordExpiry(passwordExpiry);
						pingenGroup.setMinPassword(minPassword);
						pingenGroup.setPasswordHistory(passwordHistory);
						pingenGroup.setMaxAllocactionPerDay(maxAllocation);
						pingenGroup.setSearchRange(searchRange);
						pingenGroup.setHomePage(homepage);
						pingenGroup.setPortal(portal);
						
						
						if(pingenGroup.register()){
							ObjectState state = new ObjectState("00","Role Successfully Added");
							pingenGroup.setState(state);
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(name);
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
						    audit.setData("USERSLEVEL:"+name+"|HOMEPAGE:"+homepage+"|MINPASSWORD:"+minPassword);
				    		audit.insert();
							JsonView view = new JsonView(pingenGroup);
							return view;
						}
						else{
							ObjectState state = new ObjectState("01","Add Unsuccessfull");
							pingenGroup.setState(state);
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(name);
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
						    audit.setData("USERSLEVEL:"+name+"|HOMEPAGE:"+homepage+"|MINPASSWORD:"+minPassword);
				    		audit.insert();
							JsonView view = new JsonView(pingenGroup);
							return view;
						}
					}catch(Exception e){
						ObjectState state = new ObjectState("02","Add Unsuccessfull");
						pingenGroup.setState(state);
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(name);
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
					    audit.setData("USERSLEVEL:"+name+"|HOMEPAGE:"+homepage+"|MINPASSWORD:"+minPassword);
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
    
   }
return v;

	}

	@Override
	public String getKey() {
		return "REGISTERROLE";
	}

	@Override
	public int getId() {
		return 3030;
	}




}
