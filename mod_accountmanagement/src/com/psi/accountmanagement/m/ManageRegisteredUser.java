package com.psi.accountmanagement.m;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.ibayad.transmitters.client.SmsMessage;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.utils.Users;
import com.psi.audit.trail.m.AuditTrail;
import com.psi.audit.trail.m.GetAuditPreviousData;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class ManageRegisteredUser extends Users{

	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.userid);
		  previous.setQuery("SELECT EMAIL,MSISDN FROM TBLUSERS WHERE USERID=?");
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public void getDataDefault(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.userid);
		  previous.setQuery("SELECT U.USERID,EMAIL,MSISDN,FIRSTNAME,MIDDLENAME,LASTNAME,USERSLEVEL,U.DEPARTMENT,EMPLOYMENTSTATUS,EMPLOYEENUMBER FROM TBLUSERS U INNER JOIN TBLUSERSTITLE US ON U.USERNAME = US.USERID WHERE U.USERID=?");
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	
	public boolean update(){
	     	UISession sess = this.getAuthorizedSession();
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=?,MSISDN=?,DATEMODIFIED=SYSDATE,EMAIL=? WHERE USERID = ?; \n");
			query.append("UPDATE TBLUSERSTITLE SET DEPARTMENT=?,EMPLOYMENTSTATUS=?,EMPLOYEENUMBER=? WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			EmailUtils.sendUpdate(this.email, this.firstname, this.lastname,this.userid,sess.getIpAddress(),sess.getAccount().getUserName());	
			
			return SystemInfo.getDb().QueryUpdate(query.toString(), 
					this.firstname,this.lastname,this.midname,this.msisdn,this.email,this.userid,
					this.department,this.employmentstatus,this.employeenumber,SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID =?", "", this.userid))>0;

	}
	
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID = ?", this.userid).size()>0;
	}
	
	
	public boolean validate(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET AUTHCODE = 'PASS' , ISFIRSTLOGON = 0 WHERE USERID = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.id )>0;
	}
	
	public boolean isValid(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND AUTHCODE = ?", this.id,this.code).size()>0;	
	}
	
	public boolean isValidated(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND AUTHCODE = 'PASS'", this.id).size()>0;	
	}
	
	public String getEmail(){
		 DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.id);	
		 if(!row.isEmpty()){
			return row.getString("EMAIL");
		 }else{
			 return "";
		 }
	}
	public boolean isEmailExist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL) = ? AND USERID <> ?", "~"+this.email.toUpperCase(),this.userid).size()>0;	
	}
	public boolean isMsisdnExist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN = ? AND USERID <> ?", this.msisdn,this.userid).size()>0;	
	}	 
	@SuppressWarnings("unchecked")
	public boolean requestmsisdnotp(){
		DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '96063' AND PORTAL = 'OPERATOR'");
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET MSISDNOTP = ?,TEMPEMAIL=?,ISOTPVERIFIED=0 WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			
			if(SystemInfo.getDb().QueryUpdate(query.toString(),this.token,"~"+this.email,this.userid )>0){
				DataRow r = SystemInfo.getDb().QueryDataRow("SELECT MSISDNOTP,FIRSTNAME,MSISDN FROM TBLUSERS WHERE USERID=?",this.userid);
				
				SmsMessage sms = new SmsMessage();
				sms.setSender("UPay");
				sms.setDestination(r.getString("MSISDN").replace("+", ""));
				String msg = message.get("MESSAGE").toString();
				msg = msg.replace("<firstname>", r.getString("FIRSTNAME").toString());
				msg = msg.replace("<otp>", r.getString("MSISDNOTP").toString());
				sms.setMessage(msg);
				JSONObject metadata=new JSONObject();
				metadata.put("initiator", "lucena");
				sms.setMetadata(metadata);
				sms.send();		 
				return true;
			}else{
				return false;
			}
	}
			 
	public boolean validateOtp(){
		DataRow res = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND MSISDNOTP = ?", this.userid,this.token);
		if(!res.isEmpty()){
			return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET ISOTPVERIFIED = 1 WHERE USERID = ?", this.userid)>0;
		}else{
			return false;
		}
	}	
	
	public boolean editEmailMsisdn(){
		DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '96063' AND PORTAL = 'OPERATOR'");
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET MSISDNOTP = ?,TEMPEMAIL=?,TEMPMSISDN=? WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			if(SystemInfo.getDb().QueryUpdate(query.toString(),this.token,"~"+this.email,this.msisdn,this.userid )>0){
				DataRow r = SystemInfo.getDb().QueryDataRow("SELECT MSISDNOTP,FIRSTNAME,MSISDN FROM TBLUSERS WHERE USERID=?",this.userid);
				
			SmsMessage sms = new SmsMessage();
			sms.setSender("UPay");
			sms.setDestination(r.getString("MSISDN").replace("+", ""));
			String msg = message.get("MESSAGE").toString();
			msg = msg.replace("<firstname>", r.getString("FIRSTNAME").toString());
			msg = msg.replace("<otp>", r.getString("MSISDNOTP").toString());
			sms.setMessage(msg);
			JSONObject metadata=new JSONObject();
			metadata.put("initiator", "lucena");
			sms.setMetadata(metadata);
			sms.send();			 
			return true;
			}else{
				return false;
			}
	}
	
	public boolean editMsisdn(){
		StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET TEMPMSISDN=? WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
					 
			return SystemInfo.getDb().QueryUpdate(query.toString(),this.msisdn,this.userid )>0;
	}
}
