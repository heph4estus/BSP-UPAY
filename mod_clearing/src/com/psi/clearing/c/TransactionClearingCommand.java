package com.psi.clearing.c;

import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.clearing.m.Clearing;
import com.psi.clearing.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class TransactionClearingCommand extends UICommand{

	@Override
	public IView execute() {
		
		ExistingSession sess = null;
	    
	    SessionView v = null;
		Clearing clearing = new Clearing();
	    try
	    {
	      sess = ExistingSession.parse(this.reqHeaders);
	      if (sess.exists())
	      {
			String id = this.params.get("LinkId");
	        String outletaccntno = this.params.get("Outlet").toString();
			String clearingtype = this.params.get("Type").toString();
			String companyaccntno = this.params.get("Company").toString();
			String password = this.params.get("Password");
			
	          clearing.setCompanyaccountnumber(companyaccntno);
	          clearing.setOutletaccountnumber(outletaccntno);
	          clearing.setClearingtype(clearingtype);
	          clearing.setId(id);
	          clearing.setPassword(password);
	          clearing.setAuthorizedSession(sess);
          
			
			
	        String ref = "";
	        try
	        {
	          JSONParser addrefParser = new JSONParser();
	          try
	          {
	            if (this.params.get("ReferenceIds") != null)
	            {
	              JSONArray addrefs = (JSONArray)addrefParser.parse(this.params.get("ReferenceIds"));
	              String[] arrRefs = new String[addrefs.size()];
	              int x = 0;
	              for (Object m : addrefs)
	              {
	                String i = m.toString();
	                arrRefs[(x++)] = i;
	              }
	              ref = Arrays.toString(arrRefs);
	              System.out.println("array for: "+Arrays.toString(arrRefs));
	              clearing.setReferenceids(arrRefs);
	            }
	          }
	          catch (Exception e)
	          {
	            Logger.LogServer("NO REFERENCE IDs SELECTED\n" + e);
	          }
	          
	          if(!clearing.validate()){
					clearing.setState(new ObjectState("01", "Invalid Password"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(clearing.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog(clearing.getState().getMessage());
		    		audit.setStatus(clearing.getState().getCode());
		    		audit.setUserid(clearing.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
		    		audit.setData(ref+"|"+id+"|"+outletaccntno+"|"+clearingtype+"|"+companyaccntno);
		    		audit.setSessionid(clearing.getAuthorizedSession().getId());
		    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(clearing.getAuthorizedSession().getPortalverion());
				    audit.setOs(clearing.getAuthorizedSession().getOs());
				    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
		    		audit.insert();
					return new JsonView(clearing); 
				}
	          
	          if(clearingtype.equals("CLAIM")){
		          if (clearing.cleartrans()){
		            ObjectState state = new ObjectState("00", "Transaction Successfully Cleared");
		            clearing.setState(state);
		            AuditTrail audit = new AuditTrail();
		            audit.setIp(clearing.getAuthorizedSession().getIpAddress());
		            audit.setModuleid(String.valueOf(getId()));
		            audit.setEntityid(companyaccntno);
		            audit.setLog(clearing.getState().getMessage());
		            audit.setStatus(clearing.getState().getCode());
		            audit.setUserid(Integer.valueOf(clearing.getAuthorizedSession().getAccount().getId()));
		            audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
		            audit.setData(ref+"|"+id+"|"+outletaccntno+"|"+clearingtype+"|"+companyaccntno);
		            audit.setSessionid(clearing.getAuthorizedSession().getId());
		    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(clearing.getAuthorizedSession().getPortalverion());
				    audit.setOs(clearing.getAuthorizedSession().getOs());
				    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
		            audit.insert();
		            return new JsonView(clearing);
		          	}
	          }else if (clearingtype.equals("DEALLOCATE")){
	        	  if (clearing.deallocate()){
			            ObjectState state = new ObjectState("00", "Transaction Successfully Cleared");
			            clearing.setState(state);
			            AuditTrail audit = new AuditTrail();
			            audit.setIp(clearing.getAuthorizedSession().getIpAddress());
			            audit.setModuleid(String.valueOf(getId()));
			            audit.setEntityid(companyaccntno);
			            audit.setLog(clearing.getState().getMessage());
			            audit.setStatus(clearing.getState().getCode());
			            audit.setUserid(Integer.valueOf(clearing.getAuthorizedSession().getAccount().getId()));
			            audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
			            audit.setData(ref+"|"+id+"|"+outletaccntno+"|"+clearingtype+"|"+companyaccntno);
			            audit.setSessionid(clearing.getAuthorizedSession().getId());
			    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(sess.getAuthorizedSession().getPortalverion());
					    audit.setOs(clearing.getAuthorizedSession().getOs());
					    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setRequest(this.params.toString());
			            
			            audit.insert();
			            return new JsonView(clearing);
			          	}
	          }else{
		          ObjectState state = new ObjectState("01", "Clearing Unsuccessfull");
		          clearing.setState(state);
		          AuditTrail audit  = new AuditTrail();
		    		audit.setIp(clearing.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog(clearing.getState().getMessage());
		    		audit.setStatus(clearing.getState().getCode());
		    		audit.setUserid(clearing.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
		    		audit.setData(ref+"|"+id+"|"+outletaccntno+"|"+clearingtype+"|"+companyaccntno);
		    		audit.setSessionid(clearing.getAuthorizedSession().getId());
		    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(clearing.getAuthorizedSession().getPortalverion());
				    audit.setOs(clearing.getAuthorizedSession().getOs());
				    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
				    audit.setRequest(this.params.toString());
		    		audit.insert();
		          return new JsonView(clearing);
	          }
	          ObjectState state = new ObjectState("01", "Clearing Unsuccessfull");
	          clearing.setState(state);
	          AuditTrail audit  = new AuditTrail();
	    		audit.setIp(clearing.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(id);
	    		audit.setLog(clearing.getState().getMessage());
	    		audit.setStatus(clearing.getState().getCode());
	    		audit.setUserid(clearing.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
	    		audit.setData(ref+"|"+id+"|"+outletaccntno+"|"+clearingtype+"|"+companyaccntno);
	    		audit.setSessionid(clearing.getAuthorizedSession().getId());
	    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(clearing.getAuthorizedSession().getPortalverion());
			    audit.setOs(clearing.getAuthorizedSession().getOs());
			    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.setRequest(this.params.toString());
	    		audit.insert();
	          return new JsonView(clearing);
	          

	        }
	        catch (Exception e)
	        {
	          Logger.LogServer(e);
	          ObjectState state = new ObjectState("01", "Clearing Unsuccessful");
	          clearing.setState(state);
	          AuditTrail audit  = new AuditTrail();
	    		audit.setIp(clearing.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(id);
	    		audit.setLog(clearing.getState().getMessage());
	    		audit.setStatus(clearing.getState().getCode());
	    		audit.setUserid(clearing.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(clearing.getAuthorizedSession().getAccount().getUserName());
	    		audit.setData(ref+"|"+id+"|"+outletaccntno+"|"+clearingtype+"|"+companyaccntno);
	    		audit.setSessionid(clearing.getAuthorizedSession().getId());
	    		audit.setBrowser(clearing.getAuthorizedSession().getBrowser());
			    audit.setBrowserversion(clearing.getAuthorizedSession().getBrowserversion());
			    audit.setPortalversion(clearing.getAuthorizedSession().getPortalverion());
			    audit.setOs(clearing.getAuthorizedSession().getOs());
			    audit.setUserslevel(clearing.getAuthorizedSession().getAccount().getGroup().getName());
			    audit.setRequest(this.params.toString());
	    		audit.insert();
	          return new JsonView(clearing);
	        }
	      }
	      UISession u = new UISession(null);
	      u.setState(new ObjectState("TLC-3902-01"));
	      v = new SessionView(u);
	   }
	    catch (SessionNotFoundException e)
	    {
	      UISession u = new UISession(null);
	      u.setState(new ObjectState("TLC-3902-01"));
	      v = new SessionView(u);
	    }
	    return v;
	}

	@Override
	public String getKey() {
		return "TRANSACTIONCLEAR";
	}

	@Override
	public int getId() {
		return 1400;
	}

}
