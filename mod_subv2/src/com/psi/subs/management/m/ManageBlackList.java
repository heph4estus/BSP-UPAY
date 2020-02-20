package com.psi.subs.management.m;
import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;


public class ManageBlackList extends Model{
	protected String filename;
	protected String password;
	protected String username;
	  protected String type;
	  protected String firstname;
	  protected String middlename;
	  protected String lastname;
	  protected String dateofbirth;
	  protected String reason;
	  protected String id;

	
	public boolean delete(String batch){
		StringBuilder sql = new StringBuilder("BEGIN\n");
		sql.append("DELETE FROM TBLBLACKLIST WHERE BATCH = ? AND FILENAME = ?;\n");
		sql.append("DELETE FROM TBLFILERECORDS WHERE BATCH = ? AND FILENAME = ?;\n");
		sql.append("END;\n");
		return SystemInfo.getDb().QueryUpdate(sql.toString(), batch,this.filename,batch,this.filename)>0;
	}
	
	public boolean deleteblack(String id){
		StringBuilder sql = new StringBuilder("BEGIN\n");
		sql.append("DELETE FROM TBLBCBLACKLIST WHERE ID= ?;\n");
		sql.append("END;\n");
		return SystemInfo.getDb().QueryUpdate(sql.toString(), id)>0;
	}
	
	public boolean addtoblacklist(String id){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT TYPE, FIRSTNAME, MIDDLENAME, LASTNAME, TO_CHAR(DATEOFBIRTH, 'YYYY-MM-DD') AS DATEOFBIRTH FROM TBLBCWATCHLIST WHERE ID=?", id);

		StringBuilder sql = new StringBuilder("BEGIN\n");
		sql.append("INSERT INTO TBLBCBLACKLIST (TYPE,FIRSTNAME,MIDDLENAME,LASTNAME,DATEOFBIRTH) VALUES(?,?,?,?,TO_DATE(?,'YYYY-MM-DD'));\n");
		sql.append("DELETE FROM TBLBCWATCHLIST WHERE ID= ?;\n");
		sql.append("END;\n");
		
		Logger.LogServer(row.getString("TYPE")+" "+row.getString("FIRSTNAME")+" "+row.getString("MIDDLENAME")+" "+row.getString("LASTNAME")+" "+row.getString("DATEOFBIRTH")+" "+id);
		return SystemInfo.getDb().QueryUpdate(sql.toString(), row.getString("TYPE"),row.getString("FIRSTNAME"),row.getString("MIDDLENAME"),row.getString("LASTNAME"),row.getString("DATEOFBIRTH"), id)>0;
	}
	public boolean deletewatch(String id){
		StringBuilder sql = new StringBuilder("BEGIN\n");
		sql.append("DELETE FROM TBLBCWATCHLIST WHERE ID= ?;\n");
		sql.append("END;\n");
		return SystemInfo.getDb().QueryUpdate(sql.toString(), id)>0;
	}
	public boolean isvalid(){	
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(USERNAME) = ? AND PASSWORD = ADMDBMC.ENCRYPT(?,?,USERNAME)", this.username.toUpperCase(),this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean isallow(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLAUTHORIZEDUSERS WHERE MODULEID = 1004 AND USERSLEVEL = ?",SystemInfo.getDb().QueryScalar("SELECT USERSLEVEL FROM TBLUSERS WHERE UPPER(USERNAME) = ?", "", this.username.toUpperCase()));
		
		if(row.getString("USERNAME").equals("ALL")){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS U INNER JOIN TBLAUTHORIZEDUSERS A ON U.USERSLEVEL = A.USERSLEVEL WHERE MODULEID = 1004 AND A.USERSLEVEL = ?", SystemInfo.getDb().QueryScalar("SELECT USERSLEVEL FROM TBLUSERS WHERE UPPER(USERNAME) = ?", "", this.username.toUpperCase())).size()>0;
		}else{
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS U INNER JOIN TBLAUTHORIZEDUSERS A ON U.USERSLEVEL = A.USERSLEVEL WHERE MODULEID = 1004 AND A.USERSLEVEL = ? AND UPPER(A.USERNAME) = ?", SystemInfo.getDb().QueryScalar("SELECT USERSLEVEL FROM TBLUSERS WHERE UPPER(USERNAME) = ?", "", this.username.toUpperCase()),this.username.toUpperCase()).size()>0;			
		}
		
	}
	  public boolean createblack()
	  {
	    StringBuilder sql = new StringBuilder("BEGIN\n");
	    sql.append("INSERT INTO TBLBCBLACKLIST (TYPE,FIRSTNAME,MIDDLENAME,LASTNAME,DATEOFBIRTH) VALUES(?,?,?,?,TO_DATE(?,'YYYY-MM-DD'));\n");
	    sql.append("END;\n");
	    return SystemInfo.getDb().QueryUpdate(sql.toString(), new Object[] { this.reason, this.firstname, this.middlename, this.lastname, this.dateofbirth }) > 0;
	  }
	  
	  public boolean createwhite()
	  {
	    StringBuilder sql = new StringBuilder("BEGIN\n");
	    sql.append("INSERT INTO TBLBCWATCHLIST (TYPE,FIRSTNAME,MIDDLENAME,LASTNAME,DATEOFBIRTH) VALUES(?,?,?,?,TO_DATE(?,'YYYY-MM-DD'));\n");
	    sql.append("END;\n");
	    return SystemInfo.getDb().QueryUpdate(sql.toString(), new Object[] { this.reason, this.firstname, this.middlename, this.lastname, this.dateofbirth }) > 0;
	  }

	  public boolean existblack() {
		 
		 
	    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBCBLACKLIST WHERE FIRSTNAME=?  AND MIDDLENAME=? AND LASTNAME=? AND DATEOFBIRTH=TO_DATE(?,'YYYY-MM-DD')", new Object[] {  this.firstname, this.middlename, this.lastname, this.dateofbirth }).size() > 0;
	  }
	  
	  public boolean existwhite() {
		    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBCWATCHLIST WHERE FIRSTNAME=? AND MIDDLENAME=? AND LASTNAME=? AND DATEOFBIRTH=TO_DATE(?,'YYYY-MM-DD')", new Object[] {  this.firstname, this.middlename, this.lastname, this.dateofbirth }).size() > 0;
		  }
	  
	  public boolean existblackid() {
		  
	    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBCBLACKLIST WHERE ID=?", new Object[] {  this.id}).size() > 0;
	  }
	  
	  public boolean existwhiteid() {
		    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBCWATCHLIST WHERE ID=?", new Object[] {  this.id }).size() > 0;
		  }
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();

		/*
		 * Super Class fields
		 */
		for (Field f : superFields) {
			try {
				f.setAccessible(true);
				if (f.get(this) != null && !f.getName().equalsIgnoreCase("servicescashout") && !f.getName().equalsIgnoreCase("servicesabscbn") && !f.getName().equalsIgnoreCase("serviceskapamilya") && !f.getName().equalsIgnoreCase("servicescashin") && !f.getName().equalsIgnoreCase("commissionbill") && !f.getName().equalsIgnoreCase("commissionairt") && !f.getName().equalsIgnoreCase("servicesbill") && !f.getName().equalsIgnoreCase("servicesairt") && !f.getName().equalsIgnoreCase("tariff") && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}

		}
		/*
		 * Class fields
		 */
		Field[] classFields = this.getClass().getDeclaredFields();
		for (Field f : classFields) {
			f.setAccessible(true);
			try {

				if (f.get(this) != null && !f.getName().equalsIgnoreCase("servicescashout") && !f.getName().equalsIgnoreCase("servicesabscbn") && !f.getName().equalsIgnoreCase("serviceskapamilya") && !f.getName().equalsIgnoreCase("servicescashin") && !f.getName().equalsIgnoreCase("commissionbill") && !f.getName().equalsIgnoreCase("commissionairt") && !f.getName().equalsIgnoreCase("servicesbill") && !f.getName().equalsIgnoreCase("servicesairt") && !f.getName().equalsIgnoreCase("tariff") && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
					sb.append(f.getName().toUpperCase() + ":" + f.get(this) + "|");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.LogServer(this.getClass().getSimpleName(), e);
			}

		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.lastIndexOf("|"));
		return sb.toString();
	}
	
}
