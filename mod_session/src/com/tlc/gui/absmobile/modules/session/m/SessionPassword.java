package com.tlc.gui.absmobile.modules.session.m;


import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UIPassword;
import com.tlc.smtp.EmailAddress;
import com.tlc.smtp.EmailMessage;

public class SessionPassword extends UIPassword {
	
	public SessionPassword(){}
	public SessionPassword(String value){this.value=value;}
	protected String email;
	protected String token;
	protected String userslevel;
	public boolean requestChange(SessionPassword newPass){	
		 return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET TOKENEXPIRY=(SYSDATE + INTERVAL '0 00:05:00.0' DAY TO SECOND),TOKEN=?,TEMPPASSWORD=ENCRYPT(?,?,USERNAME),ISFIRSTLOGON=0 WHERE USERID = ?",this.getToken().toString(),newPass.toString(),SystemInfo.getDb().getCrypt(), this.getAuthorizedSession().getToken().UserId)>0;
	}
	
	public boolean change(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		//query.append("UPDATE TBLUSERPASSWORDHISTORY SET USERNAME=?,PREVIOUSPASSWORD=(SELECT PASSWORD FROM TBLUSERPASSWORDHISTORY WHERE USERNAME=?),PASSWORD=(SELECT TEMPPASSWORD FROM TBLUSERS WHERE USERID=?),TIMESTAMP=SYSDATE WHERE USERNAME=?; \n");
		
		query.append("INSERT INTO TBLUSERPASSWORDHISTORY (USERNAME,PASSWORD) VALUES (?,(SELECT PASSWORD FROM TBLUSERS WHERE USERID=?)); \n");
		query.append("UPDATE TBLUSERS SET LASTLOGIN=SYSDATE,LASTPWDCHANGE=SYSDATE,PASSWORD=(SELECT TEMPPASSWORD FROM TBLUSERS WHERE USERID=?),TEMPPASSWORD='',TOKENEXPIRY=SYSDATE,TOKEN='',ISFIRSTLOGON=0 WHERE USERID=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
				this.getAuthorizedSession().getToken().Username,
				this.getAuthorizedSession().getToken().UserId,
				this.getAuthorizedSession().getToken().UserId,
				this.getAuthorizedSession().getToken().UserId)>0;
		//return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET PASSWORD=(SELECT TEMPPASSWORD FROM TBLUSERS WHERE USERID=?),TEMPPASSWORD='',TOKENEXPIRY='',TOKEN='',ISFIRSTLOGON=0 WHERE USERID=?",this.getAuthorizedSession().getToken().UserId, this.getAuthorizedSession().getToken().UserId)>0;								
	}
	
	public boolean forceChange(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		//query.append("UPDATE TBLUSERPASSWORDHISTORY SET USERNAME=?,PREVIOUSPASSWORD=(SELECT PASSWORD FROM TBLUSERPASSWORDHISTORY WHERE USERNAME=?),PASSWORD=(SELECT TEMPPASSWORD FROM TBLUSERS WHERE USERID=?),TIMESTAMP=SYSDATE WHERE USERNAME=?; \n");
		
		query.append("UPDATE TBLUSERS SET LASTLOGIN=SYSDATE,LASTPWDCHANGE=SYSDATE,PASSWORD=(SELECT TEMPPASSWORD FROM TBLUSERS WHERE USERID=?),TEMPPASSWORD='',TOKENEXPIRY=SYSDATE,TOKEN='',ISFIRSTLOGON=0 WHERE USERID=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		return SystemInfo.getDb().QueryUpdate(query.toString(),
				this.getAuthorizedSession().getToken().UserId,
				this.getAuthorizedSession().getToken().UserId)>0;
		//return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET PASSWORD=(SELECT TEMPPASSWORD FROM TBLUSERS WHERE USERID=?),TEMPPASSWORD='',TOKENEXPIRY='',TOKEN='',ISFIRSTLOGON=0 WHERE USERID=?",this.getAuthorizedSession().getToken().UserId, this.getAuthorizedSession().getToken().UserId)>0;								
	}
	
	public boolean isPassHistory(String newPass){		
		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM (SELECT PASSWORD,USERNAME,TIMESTAMP FROM (SELECT * FROM TBLUSERPASSWORDHISTORY WHERE USERNAME=? ORDER BY TIMESTAMP DESC) WHERE ROWNUM<= (SELECT PASSWORDHISTORY FROM TBLUSERSLEVEL WHERE USERSLEVEL=?)) WHERE PASSWORD=ENCRYPT(?,?,USERNAME)",0,
				this.getAuthorizedSession().getToken().Username,
				this.getUserslevel().toString(),
				newPass.toString(),
				SystemInfo.getDb().getCrypt())>0;		
				this.setState(ret?new ObjectState("TLC-3904-07"):new ObjectState("00"));
				return ret;
	}
	

//	public boolean sameWithPreviousPassword(String newPass){
//		//if password is not the same with previous password
//		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM TBLUSERPASSWORDHISTORY WHERE PREVIOUSPASSWORD=ENCRYPT(?,?,USERNAME) AND USERNAME=?",0,newPass.toString(),SystemInfo.getDb().getCrypt(),this.getAuthorizedSession().getToken().Username)>0;
//		this.setState(ret?new ObjectState("TLC-3904-06"):new ObjectState("00"));
//		return ret;
//	}
	
	public boolean correct() {
		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM TBLUSERS WHERE PASSWORD = ENCRYPT(?,?,USERNAME) AND USERID=?",0,this.getValue(),db.getCrypt(),this.getAuthorizedSession().getToken().UserId)>0;
		this.setState(ret?new ObjectState("00"):new ObjectState("TLC-3904-02"));
		return ret;
	}
		
	public boolean isTokenValid(){
		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM TBLUSERS  WHERE USERID=? AND TOKEN=?",0,this.getAuthorizedSession().getToken().UserId,this.token)>0;
		this.setState(ret?new ObjectState("00"):new ObjectState("TLC-3904-04"));
		return ret;
	}
	public boolean isTokenExpired(){
		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM TBLUSERS  WHERE USERID=? AND (TOKENEXPIRY > SYSDATE)",0,this.getAuthorizedSession().getToken().UserId)>0;
		this.setState(ret?new ObjectState("00"):new ObjectState("TLC-3904-05"));
		return ret;
	}
	
	public boolean isExpired() {
		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM TBLUSERS WHERE TOKEN =? AND USERID=?",0,this.getToken().toString(),this.getValue(),db.getCrypt(),this.getAuthorizedSession().getToken().UserId)>0;
		this.setState(ret?new ObjectState("00"):new ObjectState("TLC-3904-04"));
		return ret;
	}
	
	public boolean getDetails() {
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?",this.getAuthorizedSession().getToken().UserId);
		this.setEmail(r.getString("EMAIL"));
		this.setToken(r.getString("TOKEN"));
		this.setState(r.size()>0?new ObjectState("00"):new ObjectState("TLC-3904-03"));
		return r.size()>0;
	}
	
	public void send(){
		String day = SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE,'Day') TODAY FROM DUAL", "");
		String curdate = SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", "");
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.getAuthorizedSession().getToken().UserId);
		EmailMessage emailMessage = new EmailMessage(new EmailAddress(row.getString("EMAIL")), 1025, "102500");
 	    emailMessage.replace(new String []{"<firstname>",row.getString("FIRSTNAME"),"<day>",day,"<date>",curdate});
 	    emailMessage.send();  
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserslevel() {
		return userslevel;
	}
	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
	}

}