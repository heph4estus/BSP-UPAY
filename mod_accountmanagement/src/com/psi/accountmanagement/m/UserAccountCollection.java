package com.psi.accountmanagement.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;
import com.tlc.gui.modules.session.UISession;

public class UserAccountCollection extends ModelCollection {
	protected String username;
	protected String userslevel;
	
	@Override
	public boolean hasRows() {
		UISession sess = this.getAuthorizedSession();
		String userslevel = SystemInfo.getDb().QueryScalar("SELECT USERSLEVEL FROM TBLUSERS WHERE UPPER(USERNAME) = ? AND USERSLEVEL IN ('CASHIER','CUSTOMER','MANAGER','COMPANY','BUSINESSOWNER')","", this.username.toUpperCase());
		DataRowCollection rows = null;
		if(userslevel.equals("MANAGER") || userslevel.equals("CASHIER") || userslevel.equals("BUSINESSOWNER")){
			rows = SystemInfo.getDb().QueryDataRows("SELECT BR.BRANCH,U.* FROM TBLUSERS U INNER JOIN TBLBRANCHES BR ON U.ACCOUNTNUMBER = BR.ACCOUNTNUMBER WHERE  UPPER(USERNAME) = ? AND USERSLEVEL IN ('CASHIER','CUSTOMER','MANAGER','COMPANY','BUSINESSOWNER')", new Object[]{this.username.toUpperCase()});
		}else if(userslevel.equals("COMPANY")){
			rows = SystemInfo.getDb().QueryDataRows("SELECT BR.BUSINESS BRANCH,U.* FROM TBLUSERS U INNER JOIN TBLBUSINESS BR ON U.ACCOUNTNUMBER = BR.ACCOUNTNUMBER WHERE UPPER(USERNAME) = ? AND USERSLEVEL IN ('CASHIER','CUSTOMER','MANAGER','COMPANY','BUSINESSOWNER')", new Object[]{this.username.toUpperCase()});
		}else{
			rows = SystemInfo.getDb().QueryDataRows("SELECT '' BRANCH,U.* FROM TBLUSERS U WHERE UPPER(USERNAME) = ? AND USERSLEVEL IN ('CASHIER','CUSTOMER','MANAGER','COMPANY','BUSINESSOWNER')", new Object[]{this.username.toUpperCase()});
		}
		if(!rows.isEmpty()){
			for(DataRow row:rows){
				
				ReportItem m = new ReportItem();
				m.setProperty("FirstName", row.getString("FIRSTNAME")==null ? "" : row.getString("FIRSTNAME").toString().toUpperCase());
				m.setProperty("LastName", row.getString("LASTNAME")==null ? "" : row.getString("LASTNAME").toString().toUpperCase());
				m.setProperty("Email", row.getString("EMAIL")==null ? "" : row.getString("EMAIL").toString());
				m.setProperty("Status", row.getString("STATUS")==null ? "" : row.getString("STATUS").toString());
				m.setProperty("Locked", row.getString("LOCKED")==null ? "" : row.getString("LOCKED").toString());
				m.setProperty("MiddleName", row.getString("MIDDLENAME")==null ? "" : row.getString("MIDDLENAME").toString().toUpperCase());
				m.setProperty("Msisdn", row.getString("MSISDN")==null ? "" : row.getString("MSISDN").toString());
				m.setProperty("City", row.getString("CITY")==null ? "" : row.getString("CITY").toString());
				m.setProperty("Province", row.getString("PROVINCE")==null ? "" : row.getString("PROVINCE").toString());
				m.setProperty("Country", row.getString("COUNTRY")==null ? "" : row.getString("COUNTRY").toString());
				m.setProperty("UserId", row.getString("USERID")==null ? "" : row.getString("USERID").toString());
				m.setProperty("BranchName", row.getString("BRANCH")==null? "" : row.getString("BRANCH").toString());
				
				add(m);
			}
		}
		return rows.size()>0;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	
	public String getUserslevel(){
		return userslevel;
	}
	public void setUserslevel(String usersLevel){
		this.userslevel = usersLevel;
	}
}
