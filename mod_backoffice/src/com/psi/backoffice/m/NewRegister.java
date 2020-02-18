package com.psi.backoffice.m;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;

import com.psi.backoffice.util.EmailUtils;
import com.psi.backoffice.util.Users;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.encryption.PasswordGenerator;

public class NewRegister extends Users{
	
	@SuppressWarnings("unchecked")
	public boolean register(){
	    StringBuilder query = new StringBuilder("BEGIN\n");
	    query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,STORE,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL,ACCOUNTNUMBER) VALUES(?,?,?,?,?,'ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39',?); \n");
	    query.append("Insert into TBLPOSUSERS(ACCOUNTNUMBER, TERMINALID, USERID, PASSWORD, BRANCHCODE, TYPE, MSISDN, DEFAULTPWD, FIRSTNAME, LASTNAME)Values(?, '4339D22FA2180E39', ?, ?, ?, ?, ?, 0, ?,?); \n");
	    query.append("INSERT INTO TBLUSERSTITLE (USERID,DEPARTMENT,EMPLOYMENTSTATUS,EMPLOYEENUMBER) VALUES(?,?,?,?);\n");
	    query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n\tROLLBACK;\n RAISE;\nEND;");
	    return SystemInfo.getDb().QueryUpdate(query.toString(), new Object[] { 
	      this.email, this.firstname, this.lastname, this.msisdn, this.userslevel, this.username, this.manager, this.password, SystemInfo.getDb().getCrypt(), this.username, this.accountnumber, 
	      this.accountnumber, this.username, this.password, "", this.userslevel.toLowerCase(), this.msisdn, this.firstname, this.lastname, 
	      this.username, this.department, this.employmentstatus, this.employeenumber }) > 0;
	}
	public boolean exist(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME) = UPPER(?)", this.username).size()>0;
	}
	public boolean isEmailExist(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL) = ?", this.email.toUpperCase()).size()>0;
	}
	public boolean isMsisdnExist(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN = ?", this.msisdn).size()>0;
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
