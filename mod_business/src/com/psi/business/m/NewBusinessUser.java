package com.psi.business.m;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;

import com.psi.business.util.EmailUtils;
import com.psi.business.util.Manager;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.encryption.PasswordGenerator;

public class NewBusinessUser extends Manager{
	
	@SuppressWarnings("unchecked")
	public boolean register(){	
		String token = PasswordGenerator.generatePassword(9, PasswordGenerator.NUMERIC_CHAR);
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBUSINESS WHERE ACCOUNTNUMBER = ?", this.accountnumber);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,ACCOUNTNUMBER,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL,TOKENEXPIRY,TOKEN) VALUES(?,?,?,?,'COMPANY','ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39',SYSDATE+7,?); \n");
		query.append("Insert into TBLPOSUSERS(ACCOUNTNUMBER, TERMINALID, USERID, PASSWORD, BRANCHCODE, TYPE, MSISDN, DEFAULTPWD, FIRSTNAME, LASTNAME)Values(?, '4339D22FA2180E39', ?, ?, ?, 'company', ?, 0, ?,?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		int res = SystemInfo.getDb().QueryUpdate(query.toString(), 
				 									  this.email, this.firstname, this.lastname,this.msisdn,this.username,this.accountnumber,this.password,SystemInfo.getDb().getCrypt(),this.username,token
				 									 ,this.accountnumber,this.username,this.password,row.getString("BUSINESSCODE"),this.msisdn,this.firstname,this.lastname);	
		if(res>0){
			DataRow mgr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", this.username);

			JSONObject urltoken = new JSONObject();
			urltoken.put("Id", mgr.getString("USERID"));
			urltoken.put("Token", mgr.getString("TOKEN"));
			urltoken.put("Exp", mgr.getString("TOKENEXPIRY"));
			byte[] contoken = null;
			try {						
				contoken = urltoken.toJSONString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String encodetoken = DatatypeConverter.printBase64Binary(contoken);
				try{
					EmailUtils.send(this.email, this.firstname, this.lastname, this.password,this.username,"authenticate?q="+encodetoken);
				}catch (Exception e) { 
						Logger.LogServer(e);
				}
				
			return true;
		}else{
			return false;
		}

		
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username).size()>0;
	}
	
	public boolean emailexist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL=?", this.email).size()>0;
	}
	public boolean existmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN=?", this.msisdn).size()>0;
	}
}
