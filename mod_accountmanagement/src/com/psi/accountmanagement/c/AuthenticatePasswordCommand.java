package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.ForgotPassword;
import com.psi.accountmanagement.v.JsonView;
import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.StringUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.PluginHeaders;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;

public class AuthenticatePasswordCommand extends UICommand {

	@Override
	public IView execute() {
		PluginHeaders h = this.reqHeaders;
		byte[] auth = StringUtil.base64Decode(h.get("authorization"));
		String auths[] = new String(auth).split(":");
		
		String browser = auths[0];
		String browserversion = auths[1];
		String portalversion = auths[2];
		String os = auths[3];
		String ip = auths[4];
		
				String url = this.params.get("Url").toString();
				String token = this.params.get("Token").toString();
				String newpassword = this.params.get("NewPassword").toString();
				String userid = this.params.get("UserId").toString();
				ForgotPassword model = new ForgotPassword();
						
			
				model.setUrl(url);
				model.setToken(token);
				model.setUserid(userid);
				model.setPassword(newpassword);
					if(!model.existById()){
						model.setState(new ObjectState("02", "Invalid token"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("No data found");
			    		audit.setStatus("02");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
					if(model.notValidPassword()){
						model.setState(new ObjectState("TLC-3904-07"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("Your new entered password is invalid. Please choose a password that does not match with your previously used password and try again.");
			    		audit.setStatus("TLC-3904-07");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
								
					if(!model.isValid2()){
						model.setState(new ObjectState("03", "Unauthorized Account"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("Unauthorized Account");
			    		audit.setStatus("03");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
					
					if(!model.isTokenExpired()){
						model.setState(new ObjectState("04", "Token is expired"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("Token is expired");
			    		audit.setStatus("04");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
					if(!model.isTokenValid()){
						model.setState(new ObjectState("05", "Invalid token"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("Invalid token");
			    		audit.setStatus("05");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model); 
					}
					
					if(model.isPassHistory()){
						model.setState(new ObjectState("TLC-3904-07"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("Your new entered password is invalid. Please choose a password that does not match with your previously used password and try again.");
			    		audit.setStatus("TLC-3904-07");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
					
					
					if(model.sameWithCurrent()){
						model.setState(new ObjectState("TLC-3904-07"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("Your new entered password is invalid. Please choose a password that does not match with your previously used password and try again.");
			    		audit.setStatus("TLC-3904-07");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
					
					
					if(model.resetWithNewPassword()){
						model.setState(new ObjectState("00", "Success"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("Successfully Authenticate Password");
			    		audit.setStatus("00");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}else{
						model.setState(new ObjectState("06", "Something went wrong, please try again later"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(userid);
			    		audit.setLog("Something went wrong, please try again later");
			    		audit.setStatus("06");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData(userid+"|"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
				

	}

	@Override
	public String getKey() {
		return "AUTHENTICATEPASSWORD";
	}

	@Override
	public int getId() {
		return 3909;
	}

}
