package com.psi.accountmanagement.c;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;

import com.ibayad.transmitters.client.SmsMessage;
import com.psi.accountmanagement.m.ManageRegisteredUser;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.v.JsonView;
import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditMsisdnEmailAddressCommand extends UICommand{

	@SuppressWarnings("unchecked")
	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String action = this.params.get("Action").toString();
				String userslevel = this.params.get("UsersLevel").toString();
				String email = this.params.get("Email").toString();
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String msisdn = this.params.get("MSISDN").toString();
				String userid = this.params.get("UserId").toString();
				String midname = this.params.get("MiddleName").toString();
				String department = this.params.get("Department").toString();
				String portal = this.params.get("Portal").toString();
				String employmentstatus = this.params.get("EmploymentStatus").toString();
				String employeenumber = this.params.get("EmployeeNumber").toString();
				String immediatehead = this.params.get("ImmediateHead").toString();
				
				ManageRegisteredUser reg = new ManageRegisteredUser();
				
				reg.setImmediatehead(immediatehead);
				reg.setDepartment(department);
				reg.setPortal(portal);
				reg.setEmploymentstatus(employmentstatus);
				reg.setEmployeenumber(employeenumber);
				reg.setEmail(email);
				reg.setFirstname(firstname);
				reg.setLastname(lastname);
				reg.setMsisdn(msisdn);
				reg.setUserid(userid);
				reg.setUserslevel(userslevel);
				reg.setMidname(midname);
				reg.setAuthorizedSession(sess);
				reg.getData();
				if(reg.isEmailExist()){
					reg.setState(new ObjectState("01","Email Address already registered."));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(msisdn);
		    		audit.setLog("Email Address already registered");
		    		audit.setStatus("01");
		    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
		    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
		    		audit.setOlddata(reg.getAuditdata());
		    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
		    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
		    		audit.setOs(reg.getAuthorizedSession().getOs());
		    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
		    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
		    		audit.setSessionid(reg.getAuthorizedSession().getId());
		    		audit.insert();
					return new JsonView(reg);
			    }
				
				if(reg.isMsisdnExist()){
			    	reg.setState(new ObjectState("02","Mobile number already registered"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(msisdn);
		    		audit.setLog("Mobile number already registered");
		    		audit.setStatus("02");
		    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
		    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());		    		audit.setOlddata(reg.getAuditdata());
		    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
		    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
		    		audit.setOs(reg.getAuthorizedSession().getOs());
		    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
		    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
		    		audit.setSessionid(reg.getAuthorizedSession().getId());
		    		audit.insert();
					return new JsonView(reg);
			    }
				
				
				//if email address only is changed:
				if(action.equals("REQUESTOTP")){
					String token = PasswordGenerator.generatePassword(5,PasswordGenerator.NUMERIC_CHAR);
					reg.setToken(token);
					
					if(reg.requestmsisdnotp()){
						//HERE GOES THE CODE TO SEND SMS:
						
						reg.setState(new ObjectState("00", "Change Email Address OTP Sent to Registered Mobile Number"));
						//EmailUtils.sendEditAccountEmail(email, firstname, lastname, reg.getAuthorizedSession().getIpAddress());	
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog("Change Email Address OTP Sent to Registered Mobile Number");
			    		audit.setStatus("00");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    		audit.setOs(reg.getAuthorizedSession().getOs());
			    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
			    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
			    		audit.setOlddata(reg.getAuditdata());
			    		
			    		audit.insert();
			    		
						return new JsonView(reg);  
					}else{
						reg.setState(new ObjectState("99","Unable to send OTP to registered mobile number"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog("Unable to send OTP to registered mobile number");
			    		audit.setStatus("99");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    		audit.setOs(reg.getAuthorizedSession().getOs());
			    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
			    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
			    		audit.setOlddata(reg.getAuditdata());
			    		
			    		audit.insert();
						return new JsonView(reg);  
					}
				}
				
				if(action.equals("VALIDATEOTP")){
					String token = this.params.get("Token").toString();
					String url = this.params.get("Url").toString();
					reg.setToken(token);
					if(reg.validateOtp()){
						reg.update();
						JSONObject urltoken = new JSONObject();
						urltoken.put("Id", reg.getUserid());
						urltoken.put("Type", "EMAIL");
						urltoken.put("Token", reg.getToken());

						byte[] contoken = null;
						try {
							contoken = urltoken.toJSONString().getBytes("UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
						String encodetoken = DatatypeConverter.printBase64Binary(contoken);
						EmailUtils.sendEditAccountVerifyEmail(SystemInfo.getDb().QueryScalar("SELECT REPLACE(TEMPEMAIL,'~','') TEMPEMAIL FROM TBLUSERS WHERE USERID = ?", "", userid), firstname, url+"verify?q="+encodetoken);
						
						reg.setState(new ObjectState("00", "Verification Link Sent to Email Address"));	
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog("Verification Link Sent to Email Address");
			    		audit.setStatus("00");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    		audit.setOs(reg.getAuthorizedSession().getOs());
			    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
			    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
			    		audit.setOlddata(reg.getAuditdata());
			    		
			    		audit.insert();
						return new JsonView(reg);  
					} else {
						reg.setState(new ObjectState("99","Unable to send verfication link to registered email address"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog("Unable to send verfication link to registered email address");
			    		audit.setStatus("99");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    		audit.setOs(reg.getAuthorizedSession().getOs());
			    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
			    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
			    		audit.setOlddata(reg.getAuditdata());
			    		
			    		audit.insert();
						return new JsonView(reg);  
					}
				}
				
				
				//msisdn only updated
				if(action.equals("MSISDN")){
					if(reg.editMsisdn()){
						reg.update();
						String url = this.params.get("Url").toString();
						JSONObject urltoken = new JSONObject();
						urltoken.put("Id", reg.getUserid());
						urltoken.put("Type", "MSISDN");

						byte[] contoken = null;
						try {
							contoken = urltoken.toJSONString().getBytes("UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
						String encodetoken = DatatypeConverter.printBase64Binary(contoken);
						EmailUtils.sendEditMsisdnVerifyEmail(email, reg.getFirstname(), url+"verify?q="+encodetoken);
						
						reg.setState(new ObjectState("00", "Verification Link Sent to Registered Email Address"));
					
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog("Verification Link Sent to Registered Email Address");
			    		audit.setStatus("00");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    		audit.setOs(reg.getAuthorizedSession().getOs());
			    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
			    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
			    		audit.setOlddata(reg.getAuditdata());
			    		
			    		audit.insert();
						return new JsonView(reg);  
					}else{
						reg.setState(new ObjectState("99","Unable to send verfication link to registered email address"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog("Unable to send verfication link to registered email address");
			    		audit.setStatus("99");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    		audit.setOs(reg.getAuthorizedSession().getOs());
			    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
			    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
			    		audit.setOlddata(reg.getAuditdata());
			    		
			    		audit.insert();
						return new JsonView(reg);  
					}
				}
				
				//email and msisdn are updated
				if(action.equals("BOTH")){
					String token = PasswordGenerator.generatePassword(5,PasswordGenerator.NUMERIC_CHAR);
					reg.setToken(token);
					String url = this.params.get("Url").toString();
					
					if(reg.editEmailMsisdn()){
						//HERE GOES THE CODE TO SEND SMS:
						//code......
						
						JSONObject urltoken = new JSONObject();
						urltoken.put("Id", reg.getUserid());
						urltoken.put("Type", "MSISDN");

						byte[] contoken = null;
						try {
							contoken = urltoken.toJSONString().getBytes("UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
						String encodetoken = DatatypeConverter.printBase64Binary(contoken);
						EmailUtils.sendEditMsisdnVerifyEmail(email, reg.getFirstname(), url+"verify?q="+encodetoken);
						
						reg.setState(new ObjectState("00", "Verification Link and OTP Sent"));
					
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog("Verification Link and OTP Sent");
			    		audit.setStatus("00");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    		audit.setOs(reg.getAuthorizedSession().getOs());
			    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
			    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
			    		audit.setOlddata(reg.getAuditdata());
			    		
			    		audit.insert();
						return new JsonView(reg);  
					}else{
						reg.setState(new ObjectState("99","Unable to send OTP and verification link"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog("Unable to send OTP and verification link");
			    		audit.setStatus("99");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
			    		audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
			    		audit.setOs(reg.getAuthorizedSession().getOs());
			    		audit.setPortalversion(reg.getAuthorizedSession().getAccount().getPortalversion());
			    		audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setData("FIRSTNAME:"+reg.getFirstname()+"|MIDDLENAME:"+reg.getMidname()+"|LASTNAME:"+reg.getLastname()+"|EMAIL:"+reg.getEmail()+"|MSISDN:"+reg.getMsisdn());
			    		audit.setOlddata(reg.getAuditdata());
			    		
			    		audit.insert();
						return new JsonView(reg);  
					}
				}
			}		
		}catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
		    u.setState(new ObjectState("99","System is busy"));
		    v = new SessionView(u);
			Logger.LogServer(e);
	} catch (Exception e) {
		Logger.LogServer(e);
	}
		return v;
	}

	@Override
	public String getKey() {
		return "EDITMSISDNEMAILADDRESS";
	}

	@Override
	public int getId() {
		return 9606;
	}

}
