package com.psi.accountmanagement.m;

import java.lang.reflect.Field;

import com.psi.accountmanagement.utils.Users;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.DbWrapper;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.absmobile.modules.session.m.SessionPassword;
import com.tlc.gui.modules.common.ObjectState;

public class ForgotPassword extends Users{
	
	public boolean reset(){	
		return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET TOKENEXPIRY='',TOKEN='',PASSWORD=ENCRYPT(?,'sunev8clt1234567890',USERNAME) WHERE UPPER(EMAIL) = ?", this.password, this.email ) > 0;
	}
	
	public boolean resetWithNewPassword(){	
		StringBuilder query = new StringBuilder("BEGIN\n");
		//query.append("UPDATE TBLUSERPASSWORDHISTORY SET USERNAME=?,PREVIOUSPASSWORD=(SELECT PASSWORD FROM TBLUSERPASSWORDHISTORY WHERE USERNAME=?),PASSWORD=ENCRYPT(?,?,USERNAME),TIMESTAMP=SYSDATE WHERE USERNAME=?; \n");
		//this.getAuthorizedSession().getAccount().getUserName(),this.getAuthorizedSession().getAccount().getUserName(),this.password,SystemInfo.getDb().getCrypt(),this.getAuthorizedSession().getAccount().getUserName(),
		query.append("INSERT INTO TBLUSERPASSWORDHISTORY (USERNAME,PASSWORD) VALUES ((SELECT USERNAME FROM TBLUSERS WHERE USERID=?),(SELECT PASSWORD FROM TBLUSERS WHERE USERID=?)); \n");
		query.append("UPDATE TBLUSERS SET LASTLOGIN=SYSDATE,LASTPWDCHANGE=SYSDATE,PASSWORD=ENCRYPT(?,?,USERNAME),ISFIRSTLOGON=0,TOKENEXPIRY=SYSDATE,TOKEN='' WHERE USERID = ?;\n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.userid,this.userid,
				this.password,SystemInfo.getDb().getCrypt(), this.userid)>0;
	
		//	 return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET PASSWORD=ENCRYPT(?,?,USERNAME),ISFIRSTLOGON=0,TOKENEXPIRY='',TOKEN='' WHERE USERID = ?",this.password,SystemInfo.getDb().getCrypt(), this.userid)>0;
	}
	
	public boolean isPassHistory(){	
		
		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM (SELECT PASSWORD,USERNAME,TIMESTAMP FROM (SELECT * FROM TBLUSERPASSWORDHISTORY WHERE USERNAME=(SELECT USERNAME FROM TBLUSERS WHERE USERID=?) ORDER BY TIMESTAMP DESC) WHERE ROWNUM<= (SELECT PASSWORDHISTORY FROM TBLUSERSLEVEL WHERE USERSLEVEL=(SELECT USERSLEVEL FROM TBLUSERS WHERE USERID=?))) WHERE PASSWORD=ENCRYPT(?,?,USERNAME)",0,
				this.userid,
				this.userid,
				this.password,
				SystemInfo.getDb().getCrypt())>0;		
				return ret;
	}
	
	public boolean sameWithCurrent(){	
		
		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM TBLUSERS WHERE USERID=? AND PASSWORD=ENCRYPT(?,?,USERNAME)",0,
				this.userid,
				this.password,
				SystemInfo.getDb().getCrypt())>0;
				return ret;
	}
	
	
	public boolean requestreset(){	
		return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET TOKENEXPIRY=(SYSDATE + 7),TOKEN=? WHERE UPPER(EMAIL) = UPPER(?)", this.token,"~"+this.email) > 0;
		//return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET TOKENEXPIRY=(SYSDATE + INTERVAL '0 23:59:59.0' DAY TO SECOND),TOKEN=? WHERE UPPER(EMAIL) = UPPER(?)", this.token,this.email) > 0;
	}
	
	public boolean isTokenValid(){
		return SystemInfo.getDb().QueryDataRow("SELECT TOKEN FROM TBLUSERS  WHERE USERID=? AND TOKEN=?",this.userid,this.token).size()>0;
	}
	
	public boolean isTokenExpired(){
		return SystemInfo.getDb().QueryDataRow("SELECT TOKEN FROM TBLUSERS  WHERE USERID=? AND (TOKENEXPIRY > SYSDATE)",this.userid).size()>0;
	}
	public boolean exist(){
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL) = UPPER(?)", "~"+this.email);
		this.setUserslevel(r.getString("USERSLEVEL"));
		this.setUserid(r.getString("USERID"));
		this.setUsername(r.getString("USERNAME"));
		return r.size()>0;
	}
	
	public boolean getDetails(){
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL) = UPPER(?)", "~"+this.email);
		this.setUserid(r.getString("USERID"));
		this.setExpirydate(r.getString("TOKENEXPIRY"));
		this.setUsername(r.getString("USERNAME"));
		return r.size()>0;
	}
	
	public boolean existById(){
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.userid);
		this.setUserid(r.getString("USERID"));
		this.setUsername(r.getString("USERNAME"));
		this.setUserslevel(r.getString("USERSLEVEL"));
		return r.size()>0;
	}
	public boolean isValid(){
		if(this.url.equals("http://upay.ibayad.com:8080/outlet/") || this.url.equals("http://upay.ibayad.com:8443/outlet/")  ){
			Logger.LogServer("pasok outlet");			
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS U INNER JOIN TBLUSERSLEVEL UL ON U.USERSLEVEL = UL.USERSLEVEL WHERE EMAIL = ? AND PORTAL='outlet'", "~"+this.email).size()>0;
		}else if(this.url.equals("http://upay.ibayad.com:8080/company/") || this.url.equals("http://upay.ibayad.com:8443/company/")  ){
			Logger.LogServer("pasok company");
		    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS U INNER JOIN TBLUSERSLEVEL UL ON U.USERSLEVEL = UL.USERSLEVEL WHERE EMAIL = ? AND PORTAL='company'", "~"+this.email).size()>0;
		}else if(this.url.equals("http://upay.ibayad.com:8080/operator/") || this.url.equals("https://upay.ibayad.com:8443/operator/") || this.url.equals("https://upay.ibayad.com:443/operator/") || this.url.equals("http://localhost:60/repo/abs/prod/operator/") || this.url.equals("https://upayuat.ibayad.com/operatordev/") || this.url.equals("https://upayuat.ibayad.com/operator/")){
			Logger.LogServer("pasok operator1");
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS U INNER JOIN TBLUSERSLEVEL UL ON U.USERSLEVEL = UL.USERSLEVEL WHERE UPPER(EMAIL) = ? AND PORTAL='operator'", "~"+this.email.toUpperCase()).size()>0;
		}
		else {
			return false;
		}
	}
	
	public boolean isValid2(){
		if(this.url.equals("http://upay.ibayad.com:8080/outlet/") || this.url.equals("http://upay.ibayad.com:8443/outlet/")  ){
			Logger.LogServer("pasok outlet");			
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS U INNER JOIN TBLUSERSLEVEL UL ON U.USERSLEVEL = UL.USERSLEVEL WHERE USERID = ? AND PORTAL='outlet'", this.userid).size()>0;
		}else if(this.url.equals("http://upay.ibayad.com:8080/company/") || this.url.equals("http://upay.ibayad.com:8443/company/")  ){
			Logger.LogServer("pasok company");
		    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS U INNER JOIN TBLUSERSLEVEL UL ON U.USERSLEVEL = UL.USERSLEVEL WHERE USERID = ? AND PORTAL='company'", this.userid).size()>0;
		}else if(this.url.equals("http://upay.ibayad.com:8080/operator/") || this.url.equals("https://upay.ibayad.com:8443/operator/") || this.url.equals("https://upay.ibayad.com:443/operator/") || this.url.equals("http://localhost:60/repo/abs/prod/operator/") || this.url.equals("https://upayuat.ibayad.com/operatordev/") || this.url.equals("https://upayuat.ibayad.com/operator/")){
			Logger.LogServer("pasok operator1");
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS U INNER JOIN TBLUSERSLEVEL UL ON U.USERSLEVEL = UL.USERSLEVEL WHERE USERID = ? AND PORTAL='operator'", this.userid).size()>0;
		}
		else {
			return false;
		}
}
	public String userslevel(){
		return SystemInfo.getDb().QueryScalar("SELECT USERSLEVEL FROM TBLUSERS WHERE EMAIL = ?", "",this.email);
	}

	public boolean notValidPassword() {
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.userid,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
//	RESENT REQUEST
	public boolean withPendingRequest(){	
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ?",this.email).size()>0;
	}
	
	public boolean existUser(){
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.userid);
		return r.size()>0;
	}
	
	
	public boolean isTokenExpired2(){
		return SystemInfo.getDb().QueryDataRow("SELECT TOKEN FROM TBLUSERS  WHERE EMAIL=? AND (TOKENEXPIRY > SYSDATE)",this.email).size()>0;
	}

}
