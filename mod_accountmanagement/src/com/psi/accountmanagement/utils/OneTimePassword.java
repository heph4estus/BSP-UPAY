package com.psi.accountmanagement.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.ibayad.transmitters.client.SmsMessage;
import com.tlc.common.DataRow;
import com.tlc.common.DateFormat;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.encryption.PasswordGenerator;

public class OneTimePassword {

	protected String destination;
	protected int module;
	protected String account;
	protected String value;
	protected Date created;
	private boolean disabled;
	public OneTimePassword(){
		super();
	}
	
	public OneTimePassword(String value){
		this.value = value;
	}
	public OneTimePassword forMobile(String msisdn){
		this.destination = msisdn;
		return this;
	}
	
	public OneTimePassword andModule(int id){
		this.module=id;
		return this;
	}
	
	public OneTimePassword andAccount(String acct){
		this.account=acct;
		return this;
	}
	
	public boolean send(String otp,String userid){
		boolean set =false;
		if(!exists()){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET PASSWORD=ENCRYPT(?,?,USERNAME) WHERE USERID = ?;\n");
			query.append("UPDATE TBLPOSUSERS SET PASSWORD=? WHERE USERID = (SELECT USERNAME FROM TBLUSERS WHERE USERID = ?);\n");
			query.append("INSERT INTO TBLMOBILEOTP(ACCOUNTNUMBER,MSISDN,MODULE,OTP) VALUES(?,?,?,?);\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			set= SystemInfo.getDb().QueryUpdate(query.toString()
					, otp
					,SystemInfo.getDb().getCrypt()
					,userid
					,otp
					,userid
					, this.account
					, this.destination
					, this.module
					, otp)>0;
		}else{
				if(within(3)){
					otp=this.value;
					set = true;
				}else{
					StringBuilder query = new StringBuilder("BEGIN\n");
					query.append("UPDATE TBLUSERS SET PASSWORD=ENCRYPT(?,?,USERNAME) WHERE USERID = ?;\n");
					query.append("UPDATE TBLPOSUSERS SET PASSWORD=? WHERE USERID = (SELECT USERNAME FROM TBLUSERS WHERE USERID = ?);\n");
					query.append("UPDATE TBLMOBILEOTP SET OTP=?,DATECREATED=SYSDATE,REQCNT=REQCNT-1 WHERE ACCOUNTNUMBER=? AND MSISDN=? AND MODULE=? AND REQCNT>0;\n");
					query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
					set = SystemInfo.getDb().QueryUpdate(query.toString()
							, otp
							,SystemInfo.getDb().getCrypt()
							,userid
							,otp
							,userid
							, otp
							, this.account
							, this.destination
							, this.module)>0;
					if(!set){
						this.disabled=true;
						//cleanup expired otp
						set= SystemInfo.getDb().QueryUpdate("DELETE FROM TBLMOBILEOTP WHERE ACCOUNTNUMBER=? AND MSISDN=? AND TRUNC(DATECREATED)<TRUNC(SYSDATE)"
								, this.account
								, this.destination)>0;
					}
				}
		}
			
		SmsMessage sms = new SmsMessage();
		sms.setDestination(this.destination);
		sms.setSender("UPay");
		sms.setMessage("Ang iyong ABS Pay One Time PIN ay "+otp+". Paki-enter sa app within 5 minutes.");
		sms.send();
		
		return set;							
		
		
	}
	
	public boolean disabled(){
		return this.disabled;
	}
	
	DateFormat dbdf = new DateFormat("yyyy-MM-dd HH:mm:ss");
	public boolean exists(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT OTP,TO_CHAR(DATECREATED,'yyyy-MM-dd hh24:mi:ss') DATECREATED FROM TBLMOBILEOTP WHERE ACCOUNTNUMBER=? AND MSISDN=? AND MODULE=? AND TRUNC(DATECREATED)=TRUNC(SYSDATE)"
				, this.account
				, this.destination
				, this.module);
		
		if(!row.isEmpty()){
			this.value = row.getString("OTP");
			try {
				this.created = dbdf.parse(row.getString("DATECREATED"));
				
			} catch (ParseException e) {
				Logger.LogServer(e);
			}
		}
		return !row.isEmpty();
	}
	
	static DateFormat nowdf = new DateFormat("yyyy-MM-dd HH:mm:ss");	
	public boolean within(int minutes){
		
		Calendar c = Calendar.getInstance();
		c.setTime(created);
		c.add(Calendar.MINUTE, minutes);
		String sysdate = SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE,'yyyy-MM-dd HH24:mi:ss') FROM DUAL", null);
		if(sysdate!=null){
			Date now;
			try {
				now = nowdf.parse(sysdate);
				return c.getTime().after(now);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
			
		}
		return c.getTime().after(new Date());
	}
	
	public boolean consume(){
		return SystemInfo.getDb().QueryUpdate("DELETE FROM TBLMOBILEOTP WHERE ACCOUNTNUMBER=? AND MSISDN=? AND MODULE=?"
				, this.account
				, this.destination
				, this.module)>0;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		OneTimePassword otp = (OneTimePassword) obj;
		return this.value.equals(otp.value);
	}
}
