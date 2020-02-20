package com.psi.tariff.group.m;

import java.lang.reflect.Field;

import org.json.simple.JSONArray;

import com.psi.audit.trail.m.AuditTrail;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class ConfigTariffGroup extends Model{
	
	protected String[] tariffgroup;
	protected String[] branches;
	protected String company;
	protected String branch;
	protected String tariffaudit;
	protected String branchesaudit;
	
	@SuppressWarnings("unchecked")
	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		String[] plansArr = this.getTariffgroup();
		String[] branchArr = this.getBranches();
		JSONArray plansJsonArr = new JSONArray();
		JSONArray branchesJsonArr = new JSONArray();
		StringBuilder planBuilder = new StringBuilder();
		String plans = "INSERT INTO TBLCOMPANYTARIFF (ACCOUNTNUMBER, TARIFFGROUP,COMPANY) VALUES";
		
		try{
			for(String m : plansArr){
				plansJsonArr.add(m);
			}
			for(String b : branchArr){
				branchesJsonArr.add(b);
			}
			 for(int i = 0; i < plansJsonArr.size() ; i++){
				 for(int j = 0; j < branchesJsonArr.size() ; j++){
					 planBuilder.append(plans);
					 planBuilder.append("('"+branchArr[j]+"',"+plansArr[i]+",'"+this.company+"')");
					 planBuilder.append(";\n");
				 }
				}
		}
		 catch(Exception e){
			 
		 }
		
		 String sql = "BEGIN\n"
				    + "DELETE FROM TBLCOMPANYTARIFF WHERE COMPANY = ? AND ACCOUNTNUMBER=?;\n"
				    + planBuilder
				   + "END;\n";
		 
		 int res =  SystemInfo.getDb().QueryUpdate(sql.toString(),this.company,this.branch);
		 if(res>0){
			 AuditTrail audit  = new AuditTrail();
	    		audit.setIp(sess.getIpAddress());
	    		audit.setModuleid("1789");
	    		audit.setEntityid(this.company);
	    		audit.setLog("Tariff Group Successfully Updated");
	    		audit.setStatus("00");
	    		audit.setUserid(sess.getAccount().getId());
	    		audit.setUsername(sess.getAccount().getUserName());
	    		audit.setSessionid(sess.getId());
	    		audit.setBrowser(sess.getBrowser());
			    audit.setBrowserversion(sess.getBrowserversion());
			    audit.setPortalversion(sess.getPortalverion());
			    audit.setOs(sess.getOs());
			    audit.setUserslevel(sess.getAccount().getGroup().getName());
			    audit.setData("NEW DETAILS: "+this.company+"|"+branch+"|"+this.tariffaudit+"|"+this.branchesaudit
			    		+"OLD DETAILS: "+this.company+"|"+branch+"|"+SystemInfo.getDb().QueryScalar("SELECT LISTAGG(TARIFFGROUP,',') WITHIN GROUP (ORDER BY TARIFFGROUP) TARIFFGROUP FROM TBLCOMPANYTARIFF WHERE COMPANY = ? AND ACCOUNTNUMBER=?","", this.company,this.branch));
	    		audit.insert();
	    		return true;
		 }else{
			 return false;
		 }
		
		
	}
	
	public String[] getTariffgroup() {
		return tariffgroup;
	}
	public void setTariffgroup(String[] tariffgroup) {
		this.tariffgroup = tariffgroup;
	}

	public String[] getBranches() {
		return branches;
	}

	public void setBranches(String[] branches) {
		this.branches = branches;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getTariffaudit() {
		return tariffaudit;
	}

	public void setTariffaudit(String tariffaudit) {
		this.tariffaudit = tariffaudit;
	}

	public String getBranchesaudit() {
		return branchesaudit;
	}

	public void setBranchesaudit(String branchesaudit) {
		this.branchesaudit = branchesaudit;
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
