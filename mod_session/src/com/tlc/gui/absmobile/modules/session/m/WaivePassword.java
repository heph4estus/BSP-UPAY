package com.tlc.gui.absmobile.modules.session.m;

import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class WaivePassword extends Model{
	protected String username;
	protected String userslevel;
	protected String userid;
	protected String passwordchange;

	public boolean waive(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT U.USERID,U.USERSLEVEL,L.PASSWORDCHANGE FROM TBLUSERS U INNER JOIN TBLUSERSLEVEL L ON U.USERSLEVEL=L.USERSLEVEL WHERE UPPER(USERNAME) = UPPER(?)", this.username);
		if(row.isEmpty()){
			return false;
		} else {
			this.setUserslevel(row.getString("USERSLEVEL"));
			this.setUserid(row.getString("USERID"));
			this.setPasswordchange(row.getString("PASSWORDCHANGE"));
		}
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET LASTPWDCHANGE=SYSDATE WHERE UPPER(USERNAME) = UPPER(?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");

		return SystemInfo.getDb().QueryUpdate(query.toString(), 
				this.username)>0;
	}

	public boolean existing(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME) = UPPER(?)", this.username).size()>0;	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserslevel() {
		return userslevel;
	}

	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswordchange() {
		return passwordchange;
	}

	public void setPasswordchange(String passwordchange) {
		this.passwordchange = passwordchange;
	}
	
	
	
	
}
