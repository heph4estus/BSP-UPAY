package com.psi.customer.support.m;

import java.lang.reflect.Field;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class SearchByAccntInfoCollection extends ModelCollection{
	protected String accountnumber;
	protected String fname;
	protected String mname;
	protected String lname;
	protected String dob;
	protected String username;
	protected String msisdn;

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE AI.ACCOUNTNUMBER=?",this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
				        m.setProperty(key, row.getString(key).toString());
				}
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public boolean hasRowsUsername() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.USERNAME)=?",this.username.toUpperCase());
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
				        m.setProperty(key, row.getString(key).toString());
				}
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public boolean hasRowsnamedob() {
		DataRowCollection r = null;
		if(!StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.mname) && StringUtil.isNullOrEmpty(this.lname) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND  UPPER(U.FIRSTNAME) LIKE ?","%"+this.fname.toUpperCase()+"%");	
			Logger.LogServer("firstname:"+r.toString() + this.fname.toUpperCase());
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;	     
		}else if(StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.mname) && StringUtil.isNullOrEmpty(this.lname) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.MIDDLENAME) LIKE ?","%"+this.mname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.mname) && !StringUtil.isNullOrEmpty(this.lname) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(AI.LASTNAME) LIKE ?","%"+this.lname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.mname) && StringUtil.isNullOrEmpty(this.lname) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(U.MIDDLENAME) LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.mname.toUpperCase()+"%");	
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.mname) && !StringUtil.isNullOrEmpty(this.lname)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.MIDDLENAME) LIKE ? AND UPPER(AI.LASTNAME) LIKE ?","%"+this.mname.toUpperCase()+"%","%"+this.lname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.mname) && !StringUtil.isNullOrEmpty(this.lname)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(AI.LASTNAME) LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.lname.toUpperCase()+"%");	
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			          
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else {
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(U.MIDDLENAME) LIKE ? AND UPPER(AI.LASTNAME) LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.mname.toUpperCase()+"%","%"+this.lname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}
	     
	}
	public boolean hasRowsnamemsisdn() {
		DataRowCollection r = null;
		if(!StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ?","%"+this.fname.toUpperCase()+"%");	
			Logger.LogServer("firstname:"+r.toString() + this.fname.toUpperCase());
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;	     
		}else if(StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND U.MSISDN LIKE ?","%"+this.msisdn+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.LASTNAME) LIKE ?","%"+this.lname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHEREU.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND U.MSISDN LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.msisdn+"%");	
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.MIDDLENAME) LIKE ? AND U.MSISDN LIKE ?","%"+this.msisdn+"%","%"+this.lname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(U.LASTNAME) LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.lname.toUpperCase()+"%");	
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			          
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else {
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND U.MSISDN LIKE ? AND UPPER(U.LASTNAME) LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.msisdn+"%","%"+this.lname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}
	     
	}
	public boolean hasRowsnamemsisdnuser() {
		DataRowCollection r = null;
		if(!StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) && StringUtil.isNullOrEmpty(this.username) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ?","%"+this.fname.toUpperCase()+"%");	
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;	     
		}else if(StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) && StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND U.MSISDN LIKE ?","%"+this.msisdn+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname) && StringUtil.isNullOrEmpty(this.username) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.LASTNAME) LIKE ?","%"+this.lname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) && !StringUtil.isNullOrEmpty(this.username) ){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.USERNAME) LIKE ?","%"+this.username.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) && StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHEREU.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND U.MSISDN LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.msisdn+"%");	
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname) && StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(U.LASTNAME) LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.lname.toUpperCase()+"%");	
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			          
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) && !StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(U.USERNAME) LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.username.toUpperCase()+"%");	
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			          
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname) && StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.LASTNAME) LIKE ? AND U.MSISDN LIKE ?","%"+this.lname+"%","%"+this.msisdn.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) && !StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.USERNAME) LIKE ? AND U.MSISDN LIKE ?","%"+this.username+"%","%"+this.msisdn.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname) && !StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.LASTNAME) LIKE ? AND UPPER(U.USERNAME) LIKE ?","%"+this.lname+"%","%"+this.username.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname) && StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(U.MSISDN) LIKE ? AND UPPER(U.LASTNAME) LIKE ?","%"+this.fname+"%","%"+this.msisdn.toUpperCase()+"%","%"+this.lname.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && StringUtil.isNullOrEmpty(this.lname) && !StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(U.MSISDN) LIKE ? AND UPPER(U.USERNAME) LIKE ?","%"+this.fname+"%","%"+this.msisdn.toUpperCase()+"%","%"+this.username.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(StringUtil.isNullOrEmpty(this.fname) && !StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname) && !StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.LASTNAME) LIKE ? AND UPPER(U.MSISDN) LIKE ? AND UPPER(U.USERNAME) LIKE ?","%"+this.lname+"%","%"+this.msisdn.toUpperCase()+"%","%"+this.username.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else if(!StringUtil.isNullOrEmpty(this.fname) && StringUtil.isNullOrEmpty(this.msisdn) && !StringUtil.isNullOrEmpty(this.lname) && !StringUtil.isNullOrEmpty(this.username)){
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.LASTNAME) LIKE ? AND UPPER(U.FIRSTNAME) LIKE ? AND UPPER(U.USERNAME) LIKE ?","%"+this.lname+"%","%"+this.fname.toUpperCase()+"%","%"+this.username.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else {
			r = SystemInfo.getDb().QueryDataRows("SELECT AI.SPECIFICADDRESS, U.* FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON AI.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE U.USERSLEVEL NOT IN (SELECT USERSLEVEL FROM TBLUSERSLEVEL WHERE PORTAL='operator') AND UPPER(U.FIRSTNAME) LIKE ? AND U.MSISDN LIKE ? AND UPPER(U.LASTNAME) LIKE ? AND UPPER(U.USERNAME) LIKE ?","%"+this.fname.toUpperCase()+"%","%"+this.msisdn+"%","%"+this.lname.toUpperCase()+"%","%"+this.username.toUpperCase()+"%");
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
					        m.setProperty(key, row.getString(key).toString());
					}
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}
	     
	}
	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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
