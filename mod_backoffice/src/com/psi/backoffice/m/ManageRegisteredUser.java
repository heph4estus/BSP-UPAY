package com.psi.backoffice.m;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.audit.trail.m.GetAuditPreviousData;
import com.psi.backoffice.util.EmailUtils;
import com.psi.backoffice.util.Users;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.modules.session.UISession;

public class ManageRegisteredUser extends Users{

	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.id);
		  previous.setQuery("SELECT EMAIL,MSISDN,U.USERID,FIRSTNAME,MIDDLENAME,LASTNAME,USERSLEVEL,US.DEPARTMENT,EMPLOYMENTSTATUS,EMPLOYEENUMBER FROM TBLUSERS U INNER JOIN TBLUSERSTITLE US ON U.USERNAME = US.USERID WHERE U.USERID=?");
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public boolean update(){
	    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT U.*,UT.DEPARTMENT,UT.EMPLOYMENTSTATUS,UT.EMPLOYEENUMBER FROM TBLUSERS U LEFT JOIN TBLUSERSTITLE UT ON UT.USERID = U.USERNAME WHERE U.USERID=?", new Object[] { this.id });
	    StringBuilder query = new StringBuilder("BEGIN\n");
	    query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,MSISDN=?,DATEMODIFIED=SYSDATE, EMAIL = ?,USERSLEVEL=? WHERE USERID = ?; \n");
	    query.append("UPDATE TBLUSERSTITLE SET DEPARTMENT=?,EMPLOYMENTSTATUS=?,EMPLOYEENUMBER=? WHERE USERID=?;\n");
	    query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n\tROLLBACK;\n RAISE;\nEND;");

	    return SystemInfo.getDb().QueryUpdate(query.toString(), new Object[] { 
	      this.firstname, this.lastname, this.msisdn, this.email, this.userslevel, this.id, 
	      this.department, this.employmentstatus, this.employeenumber, row.getString("USERNAME") })>0;
					
}
	
	
	public boolean exist(){
		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID = ?", this.id).size()>0;
	}
public boolean exist2(){
		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", this.username).size()>0;
	}
	 public boolean isEmailExist(){
			
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID <> ? AND EMAIL = ?", this.id,this.email).size()>0;
		}
	
	public boolean delete(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.id);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("DELETE FROM TBLUSERS WHERE USERID = ?; \n");
		query.append("DELETE FROM TBLPOSUSERS WHERE USERID = ?; \n");
		query.append("DELETE FROM TBLUSERSTITLE WHERE USERID = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 this.setUsername(row.getString("USERNAME"));
		 this.setMsisdn(row.getString("MSISDN"));
		 this.setEmail(row.getString("EMAIL"));
		 this.setFirstname(row.getString("FIRSTNAME"));
		 this.setLastname(row.getString("LASTNAME"));
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.id,row.getString("USERNAME"),row.getString("USERNAME"))>0;
	}
	public boolean lockunlock(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", this.username);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET LOCKED = ?,SESSIONID='',INVALIDPASSWORDCOUNT=0 WHERE USERNAME = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 this.setUsername(row.getString("USERNAME"));
		 this.setMsisdn(row.getString("MSISDN"));
		 this.setEmail(row.getString("EMAIL"));
		 this.setFirstname(row.getString("FIRSTNAME"));
		 this.setLastname(row.getString("LASTNAME"));
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.lock!="" && this.lock.equals("NO")?"YES":"NO",this.username)>0;
	}
	public boolean requestsuspendactivate(){
//		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", this.username);
//		StringBuilder query = new StringBuilder("BEGIN\n");
//		query.append("INSERT INTO TBLUSERSSTATUSPNDG (USERNAME,STATUS,TIMESTAMP,CREATEDBY) VALUES(?,?,SYSDATE,?); \n");
//		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
//		 this.setUsername(row.getString("USERNAME"));
//		 this.setMsisdn(row.getString("MSISDN"));
//		 this.setEmail(row.getString("EMAIL"));
//		 this.setFirstname(row.getString("FIRSTNAME"));
//		 this.setLastname(row.getString("LASTNAME"));
//		 EmailUtils.sendsuspendadmin(row.getString("FIRSTNAME"), row.getString("LASTNAME"), this.status!="" || this.status.equals("ACTIVE")?"INACTIVE":"ACTIVE",this.username);	
//		 EmailUtils.sendsuspendemployee(row.getString("EMAIL"), row.getString("FIRSTNAME"), row.getString("LASTNAME"), this.status!="" || this.status.equals("ACTIVE")?"INACTIVE":"ACTIVE",this.username);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET STATUS = ?,SESSIONID='',INVALIDPASSWORDCOUNT=0 WHERE USERNAME = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 this.setUsername(row.getString("USERNAME"));
		 this.setMsisdn(row.getString("MSISDN"));
		 this.setEmail(row.getString("EMAIL"));
		 this.setFirstname(row.getString("FIRSTNAME"));
		 this.setLastname(row.getString("LASTNAME"));
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.status!="" && this.status.equals("ACTIVE")?"INACTIVE":"ACTIVE",this.username)>0;
	}
	@SuppressWarnings("unchecked")
	public boolean resetpass(){
		String pass= PasswordGenerator.generatePassword(6, PasswordGenerator.NUMERIC_CHAR);
		String token= PasswordGenerator.generatePassword(9, PasswordGenerator.NUMERIC_CHAR);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET ISFIRSTLOGON=0,PASSWORD = ENCRYPT(?,?,USERNAME),LASTPWDCHANGE=SYSDATE,TOKENEXPIRY=SYSDATE+7,TOKEN=?,SESSIONID='',INVALIDPASSWORDCOUNT=0 WHERE USERNAME = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		if(SystemInfo.getDb().QueryUpdate(query.toString(),pass,SystemInfo.getDb().getCrypt(),token,this.username)>0){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", this.username);		
		 this.setUsername(row.getString("USERNAME"));
		 this.setMsisdn(row.getString("MSISDN"));
		 this.setEmail(row.getString("EMAIL"));
		 this.setFirstname(row.getString("FIRSTNAME"));
		 this.setLastname(row.getString("LASTNAME"));
		 JSONObject urltoken = new JSONObject();
			urltoken.put("Id", row.getString("USERID"));
			urltoken.put("Token", row.getString("TOKEN"));
			urltoken.put("Exp", row.getString("TOKENEXPIRY"));
			
			
			byte[] contoken = null;
			try {						
				contoken = urltoken.toJSONString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String encodetoken = DatatypeConverter.printBase64Binary(contoken);
				try{
					EmailUtils.sendrestpass(row.getString("EMAIL"), row.getString("FIRSTNAME"), row.getString("LASTNAME"), pass,this.username,"authenticate?q="+encodetoken);
					}catch (Exception e) { 
						Logger.LogServer(e);
				}
		 	
		return true;
		}else{
			return false;
		}
	}
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND PASSWORD = ENCRYPT(?,?,USERNAME)", sess.getAccount().getId(),this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
		
		for(Field f : fields) {
			try {
				f.setAccessible(true);
				if(f.get(this) != null && !f.getName().equalsIgnoreCase("AUDITDATA") && !f.getName().equalsIgnoreCase("PASSWORD") && !f.getName().equalsIgnoreCase("SERIALVERSIONUID") && !f.getName().equalsIgnoreCase("AUTHORIZEDSESSION"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}
			
		}
		Field[] fieldss = this.getClass().getDeclaredFields();
		for(Field f : fieldss) {
			f.setAccessible(true);
			try {
				if(f.get(this) != null && !f.getName().equalsIgnoreCase("AUDITDATA") && !f.getName().equalsIgnoreCase("PASSWORD") && !f.getName().equalsIgnoreCase("SERIALVERSIONUID") && !f.getName().equalsIgnoreCase("AUTHORIZEDSESSION"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}
			
		}
		if(sb.length() > 0)
		sb.deleteCharAt(sb.lastIndexOf("|"));
		return sb.toString();
	}
}
