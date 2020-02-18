package com.psi.backoffice.c;
import com.psi.audit.trail.m.AuditTrail;
import com.psi.backoffice.m.ManageRegisteredUser;
import com.psi.backoffice.util.EmailUtils;
import com.psi.backoffice.v.JsonView;
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
				String email = this.params.get("Email").toString();
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String msisdn = this.params.get("MSISDN").toString();
				String id = this.params.get("Id").toString();
				String userslevel = this.params.get("UsersLevel").toString();
				String department = this.params.get("Department").toString();
				String employmentstatus = this.params.get("EmploymentStatus").toString();
				String employeenumber = this.params.get("EmployeeNumber").toString();
				
				ManageRegisteredUser reg = new ManageRegisteredUser();

				reg.setDepartment(department);
				reg.setEmploymentstatus(employmentstatus);
				reg.setEmployeenumber(employeenumber);
				reg.setEmail(email);
				reg.setFirstname(firstname);
				reg.setLastname(lastname);
				reg.setMsisdn(msisdn);
				reg.setId(id);
				reg.setUserslevel(userslevel);
			    reg.setAuthorizedSession(sess);
				reg.getData();
			    if(reg.isEmailExist()){
			    	reg.setState(new ObjectState("PSI-02", "Email address already registered."));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog("Email Address already registered.");
		    		audit.setStatus("01");
		    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(reg.getAuthorizedSession().getId());
		    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
				    audit.setOs(reg.getAuthorizedSession().getOs());
				    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
				   // audit.setData("EMAIL:"+email+"|MSISDN:"+msisdn+"|USERID:"+id+"|FIRSTNAME:"+firstname+"|LASTNAME:"+lastname+"|USERSLEVEL:"+userslevel+"|DEPARTMENT:"+department+"|EMPLOYEMENTSTATUS:"+employmentstatus+"|EMPLOYEENUMBER:"+employeenumber);
				    audit.setData(reg.toString());				 
				    audit.setOlddata(reg.getAuditdata());
		    		audit.insert();
					return new JsonView(reg);
			    }
				if(reg.exist()){
					if(reg.update()){
						EmailUtils.sendUpdate(email, firstname, lastname,id,reg.getAuthorizedSession().getIpAddress(),reg.getAuthorizedSession().getAccount().getUserName());							
						reg.setState(new ObjectState("00", "Updated Succesfully"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog("Updated Successfully");
			    		audit.setStatus("00");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
					    audit.setOs(reg.getAuthorizedSession().getOs());
					    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
					   // audit.setData("EMAIL:"+email+"|MSISDN:"+msisdn+"|USERID:"+id+"|FIRSTNAME:"+firstname+"|LASTNAME:"+lastname+"|USERSLEVEL:"+userslevel+"|DEPARTMENT:"+department+"|EMPLOYEMENTSTATUS:"+employmentstatus+"|EMPLOYEENUMBER:"+employeenumber);					    audit.setRequest(this.params.toString());
					    audit.setData(reg.toString());			
					    audit.setOlddata(reg.getAuditdata());
			    		audit.insert();
			    		return new JsonView(reg);
					}else{
						reg.setState(new ObjectState("99", "System busy"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog("System is currently busy, please try again ");
			    		audit.setStatus("99");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		audit.setSessionid(reg.getAuthorizedSession().getId());
			    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
					    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
					    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
					    audit.setOs(reg.getAuthorizedSession().getOs());
					    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
					    audit.setRequest(this.params.toString());
					    //audit.setData("EMAIL:"+email+"|MSISDN:"+msisdn+"|USERID:"+id+"|FIRSTNAME:"+firstname+"|LASTNAME:"+lastname+"|USERSLEVEL:"+userslevel+"|DEPARTMENT:"+department+"|EMPLOYEMENTSTATUS:"+employmentstatus+"|EMPLOYEENUMBER:"+employeenumber);					    audit.setOlddata(reg.getAuditdata());
					    audit.setData(reg.toString());			
					    audit.insert();
						return new JsonView(reg);
					}
				}else{
					reg.setState(new ObjectState("PSI-01", "Account do not exist"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog("Account do not exist ");
		    		audit.setStatus("01");
		    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
		    		audit.setSessionid(reg.getAuthorizedSession().getId());
		    		audit.setBrowser(reg.getAuthorizedSession().getBrowser());
				    audit.setBrowserversion(reg.getAuthorizedSession().getBrowserversion());
				    audit.setPortalversion(reg.getAuthorizedSession().getPortalverion());
				    audit.setOs(reg.getAuthorizedSession().getOs());
				    audit.setUserslevel(reg.getAuthorizedSession().getAccount().getGroup().getName());
				    //audit.setData("EMAIL:"+email+"|MSISDN:"+msisdn+"|USERID:"+id+"|FIRSTNAME:"+firstname+"|LASTNAME:"+lastname+"|USERSLEVEL:"+userslevel+"|DEPARTMENT:"+department+"|EMPLOYEMENTSTATUS:"+employmentstatus+"|EMPLOYEENUMBER:"+employeenumber);				    audit.setRequest(this.params.toString());
				    audit.setData(reg.toString());			
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
		return 1002;
	}

	@Override
	public String getKey() {
		return "EDITBACKOFFICEUSER";
	}

}
