package com.psi.business.v2.m;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.psi.audit.trail.m.GetAuditPreviousData;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class ManageRequiredDocuments extends Model{
	
	protected String type;
	protected String image;
	protected String accountnumber;
	protected String olddataimage;
	protected String olddata;
	protected String status;
	
	public void getOldImage(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  this.setOlddataimage(SystemInfo.getDb().QueryScalar("SELECT DOCUMENT FROM TBLDOCUMENTS WHERE ACCOUNTNUMBER=?", "", this.accountnumber));
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public void getOldDate(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(SystemInfo.getDb().getCrypt().toString());
		  parameters.add(this.accountnumber);
		  parameters.add(SystemInfo.getDb().getCrypt().toString());
		  parameters.add(this.accountnumber);
		  previous.setQuery("SELECT D.DATEREGISTERED,DECRYPT(FQN,?,B.ACCOUNTNUMBER) FQN FROM TBLDOCUMENTS D INNER JOIN ADMDBMC.TBLACCOUNTINFOPNDG B ON B.ACCOUNTNUMBER = D.ACCOUNTNUMBER WHERE D.ACCOUNTNUMBER=? UNION ALL SELECT D.DATEREGISTERED,DECRYPT(FQN,?,B.ACCOUNTNUMBER) FQN FROM TBLDOCUMENTS D INNER JOIN ADMDBMC.TBLACCOUNTINFOPNDG B ON B.ACCOUNTNUMBER = D.ACCOUNTNUMBER WHERE D.ACCOUNTNUMBER=?");
		  previous.setParam(parameters);
		  this.setOlddata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public boolean update(){
		if(this.status.equals("1")){
			return SystemInfo.getDb().QueryUpdate("UPDATE TBLDOCUMENTS SET DOCUMENT=? WHERE DOCTYPE = ? AND ACCOUNTNUMBER=? ", this.image,this.type,this.accountnumber)>0;
		}else{
			return SystemInfo.getDb().QueryUpdate("INSERT INTO TBLDOCUMENTS (DOCUMENT,DOCTYPE,ACCOUNTNUMBER) VALUES(?,?,?)", this.image,this.type,this.accountnumber)>0;
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getOlddataimage() {
		return olddataimage;
	}
	public void setOlddataimage(String olddataimage) {
		this.olddataimage = olddataimage;
	}
	public String getOlddata() {
		return olddata;
	}
	public void setOlddata(String olddata) {
		this.olddata = olddata;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * MVO 18-02-2020
	 */
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
				if (f.get(this) != null && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
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

				if (f.get(this) != null && !f.getName().equalsIgnoreCase("auditdata") && !f.getName().equalsIgnoreCase("password") && !f.getName().equalsIgnoreCase("authorizedSession") && !f.getName().equalsIgnoreCase("serialVersionUID"))
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
