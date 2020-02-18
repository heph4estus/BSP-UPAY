package com.psi.accountmanagement.c;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;

import com.psi.accountmanagement.m.ForgotPassword;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.v.JsonView;
import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.Logger;
import com.tlc.common.StringUtil;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.PluginHeaders;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;

public class ForgotPasswordCommand extends UICommand {

	@SuppressWarnings("unchecked")
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
		
				String email = this.params.get("Email").toString();
				String url = this.params.get("Url").toString();
				String token = PasswordGenerator.generatePassword(9, PasswordGenerator.NUMERIC_CHAR);
	 	
				ForgotPassword model = new ForgotPassword();
						
				model.setEmail(email);
				model.setUrl(url);
				model.setToken(token);
				
					if(!model.exist()){
						model.setState(new ObjectState("01", "Email address is not registered to any user account"));
						AuditTrail audit  = new AuditTrail();
						audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(email);
			    		audit.setLog("Email is not registered");
			    		audit.setStatus("01");
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData("EMAIL:"+email+"|URL:"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
					if(!model.isValid()){
						model.setState(new ObjectState("02", "Unauthorized Account"));
						Logger.LogServer("Unathorized"+ip+browser);
						AuditTrail audit  = new AuditTrail();
						audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(email);
			    		audit.setLog("Unauthorized Account");
			    		audit.setStatus("02");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData("EMAIL:"+email+"|URL:"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
					
					
					if(model.requestreset()){
						if(!model.getDetails()){
							model.setState(new ObjectState("03", "Something went wrong, please try again later"));
							return new JsonView(model);  
						}
						model.setState(new ObjectState("00", "Forgot password, successfully requested. Please check your email for authentication."));
						JSONObject urltoken = new JSONObject();
						urltoken.put("Id", model.getUserid());
						urltoken.put("Token", model.getToken());
						urltoken.put("Exp", model.getExpirydate());
						
						
						byte[] contoken = null;
						try {						
							contoken = urltoken.toJSONString().getBytes("UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
						String encodetoken = DatatypeConverter.printBase64Binary(contoken);
							try{
									 EmailUtils.sendAuthenticationEmail(email,url+"authenticate?q="+encodetoken);
								}catch (Exception e) { 
									Logger.LogServer(e);
							}
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(ip);
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(email);
				    		audit.setLog("Success");
				    		audit.setStatus("00");
				    		audit.setUserid(Integer.parseInt(model.getUserid()));
				    		audit.setUsername(model.getUsername());
				    		audit.setBrowser(browser);
						    audit.setBrowserversion(browserversion);
						    audit.setPortalversion(portalversion);
						    audit.setOs(os);
						    audit.setUserslevel(model.getUserslevel());
						    audit.setData("EMAIL:"+email+"|URL:"+url);
						    audit.setRequest(this.params.toString());
				    		audit.insert();
				    		
						return new JsonView(model);  
					}else{
						model.setState(new ObjectState("04", "Something went wrong, please try again later"));
						AuditTrail audit  = new AuditTrail();
						audit.setIp(ip);
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(email);
			    		audit.setLog("Something went wrong, please try again later");
			    		audit.setStatus("04");
			    		audit.setUserid(Integer.parseInt(model.getUserid()));
			    		audit.setUsername(model.getUsername());
			    		audit.setBrowser(browser);
					    audit.setBrowserversion(browserversion);
					    audit.setPortalversion(portalversion);
					    audit.setOs(os);
					    audit.setUserslevel(model.getUserslevel());
					    audit.setData("EMAIL:"+email+"|URL:"+url);
					    audit.setRequest(this.params.toString());
					    audit.insert();
						return new JsonView(model);  
					}
				

	}

	@Override
	public String getKey() {
		return "FORGOTPASSWORD";
	}

	@Override
	public int getId() {
		return 3908;
	}

}
