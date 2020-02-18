package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.ManageRegisteredUser;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.v.JsonView;
import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditRegisteredCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				
				ManageRegisteredUser reg = new ManageRegisteredUser();
				String userslevel = this.params.get("UsersLevel").toString();
				String email = this.params.get("Email").toString();
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String msisdn = this.params.get("MSISDN").toString();
				String userid = this.params.get("UserId").toString();
				String midname = this.params.get("MiddleName").toString();
				String department = this.params.get("Department").toString();
				String employmentstatus = this.params.get("EmploymentStatus").toString();
				String employeenumber = this.params.get("EmployeeNumber").toString();
					reg.setDepartment(department);
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
					reg.getDataDefault();
					if(reg.update()){						
						reg.setState(new ObjectState("00", "Successfully updated account details"));
						AuditTrail audit  = new AuditTrail();
						audit.setIp(sess.getIpAddress());
						audit.setModuleid(String.valueOf(this.getId()));
						audit.setEntityid(userid);
						audit.setLog("Successfully updated account details");
						audit.setStatus("00");
						audit.setData("USERID:"+userid+"|EMAIL:"+email+"|MSISDN:"+msisdn+"|FIRSTNAME:"+firstname+"|MIDDLENAME:"+midname+"|LASTNAME:"+lastname+"|USERSLEVEL:"+userslevel+"|DEPARTMENT:"+department+"|EMPLOYMENTSTATUS:"+employmentstatus+"|EMPLOYEENUMBER:"+employeenumber);
						audit.setUserid(sess.getAccount().getId());
						audit.setUsername(sess.getAccount().getUserName());
						audit.setSessionid(sess.getId());
						audit.setBrowser(sess.getAccount().getBrowser());
					    audit.setBrowserversion(sess.getAccount().getBrowserversion());
					    audit.setPortalversion(sess.getAccount().getPortalversion());
					    audit.setOs(sess.getAccount().getOs());
					    audit.setUserslevel(sess.getAccount().getGroup().getName());
					    audit.setOlddata(reg.getAuditdata());

						audit.insert();
						return new JsonView(reg);  
					}else{
						reg.setState(new ObjectState("99", "System busy"));
						AuditTrail audit  = new AuditTrail();
						audit.setIp(sess.getAuthorizedSession().getIpAddress());
						audit.setModuleid(String.valueOf(this.getId()));
						audit.setEntityid(userid);
						audit.setLog("System is busy, please try again later");
						audit.setStatus("99");
						audit.setData("");
						audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
						audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
						audit.setSessionid(reg.getAuthorizedSession().getId());
						audit.setBrowser(reg.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
					    audit.setOs(reg.getAuthorizedSession().getOs());
					    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setData("USERID:"+userid+"|EMAIL:"+email+"|MSISDN:"+msisdn+"|FIRSTNAME:"+firstname+"|MIDDLENAME:"+midname+"|LASTNAME:"+lastname+"|USERSLEVEL:"+userslevel+"|DEPARTMENT:"+department+"|EMPLOYMENTSTATUS:"+employmentstatus+"|EMPLOYEENUMBER:"+employeenumber);
					    audit.setOlddata(reg.getAuditdata());
						audit.insert();
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
	}
		return v;
	}

	@Override
	public int getId() {
		return 3907;
	}

	@Override
	public String getKey() {
		return "EDITREGISTEREDUSER";
	}

}
