package com.psi.business.v2.m;

import java.lang.reflect.Field;

import com.psi.business.util.Business;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;

public class InquireData extends Business{
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE FQN = ENCRYPT(?,?,ACCOUNTNUMBER) ", this.businessname,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean existmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN=? ", this.msisdn).size()>0;
	}
	public boolean existauthmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN=? ", this.authmsisdn).size()>0;
	}
	public boolean existemail(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE UPPER(EMAIL)=UPPER(?) ", this.email).size()>0;
	}
	public boolean existemaildetails(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTDETAILS WHERE UPPER(EMAIL)=UPPER(?) ", this.email).size()>0;
	}
	public boolean existauthemail(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL)=? ", this.authemail.toUpperCase()).size()>0;
	}
	public boolean existauthusername(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME)=? ", this.username.toUpperCase()).size()>0;
	}
	public boolean existpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG A INNER JOIN TBLMERCHANTPNDG M ON A.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE A.FQN = ENCRYPT(?,?,A.ACCOUNTNUMBER) AND M.STATUS = 'PNDG'", this.businessname,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean existmsisdnpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG A INNER JOIN TBLMERCHANTPNDG M ON A.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE A.MSISDN=? AND M.STATUS = 'PNDG' ", this.msisdn).size()>0;
	}
	public boolean existemailpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG A INNER JOIN TBLMERCHANTPNDG M ON A.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE UPPER(M.REQUESTS) LIKE ? AND M.STATUS = 'PNDG' ", "%"+this.email+"%").size()>0;
	}
	public boolean existauthmsisdnpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE REQUESTS LIKE '%'||?||'%' AND STATUS = 'PNDG'", this.authmsisdn).size()>0;
	}
	public boolean existauthemailpndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE UPPER(REQUESTS) LIKE '%'||?||'%' AND STATUS = 'PNDG'", this.authemail.toUpperCase()).size()>0;
	}
	public boolean existauthusernamepndg(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMERCHANTPNDG WHERE REQUESTS LIKE '%'||?||'%' AND STATUS = 'PNDG'", this.username.toUpperCase()).size()>0;
	}
}
