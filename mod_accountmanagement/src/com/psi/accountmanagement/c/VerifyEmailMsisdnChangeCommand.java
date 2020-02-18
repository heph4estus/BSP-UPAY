package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.VerifyChanges;
import com.psi.accountmanagement.v.JsonView;
import com.psi.audit.trail.m.AuditTrail;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class VerifyEmailMsisdnChangeCommand extends UICommand{

	@Override
	public IView execute() {
		String userid = this.params.get("UserId").toString();
		String type = this.params.get("type").toString();
		String ip = this.params.get("ipaddress").toString();
		String browsername = this.params.get("browsername").toString();
		String browserversion = this.params.get("browserversion").toString();
		String osversion = this.params.get("osversion").toString();
		String portalversion = this.params.get("portalversion").toString();
		String token = this.params.get("Token").toString();
		
		
		VerifyChanges verify = new VerifyChanges();
		verify.setUserid(userid);
		verify.setToken(token);
		verify.getData();
		
		if(type.equals("EMAIL")){
			if(verify.isAlreadyVerifiedEmail()){
				verify.setState(new ObjectState("01","Email Address Change Already Verified"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(ip);
	    		audit.setModuleid("9001");
	    		audit.setEntityid(userid);
	    		audit.setLog("Email Address Change Already Verified");
	    		audit.setStatus("01");
	    		audit.setUserid(Integer.parseInt(userid));
	    		audit.setUsername(verify.getUsername());
	    		audit.setBrowser(browsername);
	    		audit.setBrowserversion(browserversion);
	    		audit.setOs(osversion);
	    		audit.setPortalversion(portalversion);
	    		audit.setUserslevel(verify.getUserslevel());
	    		audit.setSessionid("");
	    		audit.setData(verify.getMsisdn()+"|"+verify.getEmail());
	    		audit.setOlddata(verify.getAuditdata());
	    		
	    		audit.insert();
				return new JsonView(verify);  
			}
			
			if(!verify.validateToken()){
				verify.setState(new ObjectState("02","Invalid token"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(ip);
	    		audit.setModuleid("9001");
	    		audit.setEntityid(userid);
	    		audit.setLog("Invalid token");
	    		audit.setStatus("02");
	    		audit.setUserid(Integer.parseInt(userid));
	    		audit.setUsername(verify.getUsername());
	    		audit.setBrowser(browsername);
	    		audit.setBrowserversion(browserversion);
	    		audit.setOs(osversion);
	    		audit.setPortalversion(portalversion);
	    		audit.setUserslevel(verify.getUserslevel());
	    		audit.setSessionid("");
	    		audit.setData(verify.getMsisdn()+"|"+verify.getEmail());
	    		audit.setOlddata(verify.getAuditdata());
	    		
	    		audit.insert();
				return new JsonView(verify);  
			}
			
			if(verify.updateEmail()){
				verify.setState(new ObjectState("00", "Email Address Successfully Updated"));
				
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(ip);
	    		audit.setModuleid("9001");
	    		audit.setEntityid(userid);
	    		audit.setLog("Email Address Successfully Updated");
	    		audit.setStatus("00");
	    		audit.setUserid(Integer.parseInt(userid));
	    		audit.setUsername(verify.getUsername());
	    		audit.setBrowser(browsername);
	    		audit.setBrowserversion(browserversion);
	    		audit.setOs(osversion);
	    		audit.setPortalversion(portalversion);
	    		audit.setUserslevel(verify.getUserslevel());
	    		audit.setSessionid("");
	    		audit.setData(verify.getMsisdn()+"|"+verify.getEmail());
	    		audit.setOlddata(verify.getAuditdata());
	    		
	    		audit.insert();
				return new JsonView(verify);  
			} else {
				verify.setState(new ObjectState("99","Unable to update information. Please try again later"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(ip);
	    		audit.setModuleid("9001");
	    		audit.setEntityid(userid);
	    		audit.setLog("Unable to update information. Please try again later");
	    		audit.setStatus("99");
	    		audit.setUserid(Integer.parseInt(userid));
	    		audit.setUsername(verify.getUsername());
	    		audit.setBrowser(browsername);
	    		audit.setBrowserversion(browserversion);
	    		audit.setOs(osversion);
	    		audit.setPortalversion(portalversion);
	    		audit.setUserslevel(verify.getUserslevel());
	    		audit.setSessionid("");
	    		audit.setData(verify.getMsisdn()+"|"+verify.getEmail());
	    		audit.setOlddata(verify.getAuditdata());
	    		
	    		audit.insert();
				return new JsonView(verify);  
			}
		}
		
		if(type.equals("MSISDN")){
			if(verify.isAlreadyVerifiedMobile()){
				verify.setState(new ObjectState("01","Mobile Number Change Already Verified"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(ip);
	    		audit.setModuleid("9001");
	    		audit.setEntityid(userid);
	    		audit.setLog("Mobile Number Change Already Verified");
	    		audit.setStatus("01");
	    		audit.setUserid(Integer.parseInt(userid));
	    		audit.setUsername(verify.getUsername());
	    		audit.setBrowser(browsername);
	    		audit.setBrowserversion(browserversion);
	    		audit.setOs(osversion);
	    		audit.setPortalversion(portalversion);
	    		audit.setUserslevel(verify.getUserslevel());
	    		audit.setSessionid("");
	    		audit.setData(verify.getMsisdn()+"|"+verify.getEmail());
	    		audit.setOlddata(verify.getAuditdata());
	    		
	    		audit.insert();
				return new JsonView(verify);  
			}
			
			if(verify.updateMobile()){
				verify.setState(new ObjectState("00", "Mobile Number Successfully Updated"));
				
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(ip);
	    		audit.setModuleid("9002");
	    		audit.setEntityid(userid);
	    		audit.setLog("Email Address Successfully Updated");
	    		audit.setStatus("00");
	    		audit.setUserid(Integer.parseInt(userid));
	    		audit.setUsername(verify.getUsername());
	    		audit.setBrowser(browsername);
	    		audit.setBrowserversion(browserversion);
	    		audit.setOs(osversion);
	    		audit.setPortalversion(portalversion);
	    		audit.setUserslevel(verify.getUserslevel());
	    		audit.setSessionid("");
	    		audit.setData(verify.getMsisdn()+"|"+verify.getEmail());
	    		audit.setOlddata(verify.getAuditdata());
	    		audit.insert();
				return new JsonView(verify);  
			} else {
				verify.setState(new ObjectState("99","Unable to update information. Please try again later"));
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(ip);
	    		audit.setModuleid("9001");
	    		audit.setEntityid(userid);
	    		audit.setLog("Unable to update information. Please try again later");
	    		audit.setStatus("99");
	    		audit.setUserid(Integer.parseInt(userid));
	    		audit.setUsername(verify.getUsername());
	    		audit.setBrowser(browsername);
	    		audit.setBrowserversion(browserversion);
	    		audit.setOs(osversion);
	    		audit.setPortalversion(portalversion);
	    		audit.setUserslevel(verify.getUserslevel());
	    		audit.setSessionid("");
	    		audit.setData(verify.getMsisdn()+"|"+verify.getEmail());
	    		audit.setOlddata(verify.getAuditdata());
	    		
	    		audit.insert();
				return new JsonView(verify);  
			}
		}
		return null;
	}

	@Override
	public String getKey() {
		return "VERIFYMSISDNEMAILCHANGE";
	}

	@Override
	public int getId() {
		return 9607;
	}

}
