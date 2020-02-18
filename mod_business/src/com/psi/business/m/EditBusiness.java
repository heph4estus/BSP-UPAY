package com.psi.business.m;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.business.util.Branch;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class EditBusiness extends Branch{

	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBUSINESS WHERE ACCOUNTNUMBER = ?", this.accountnumber);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLBUSINESS SET BUSINESS=?,ADDRESS=?,CITY=?,CONTACTNUMBER=?,PROOFADDRESS=?,PROVINCE=?,XORDINATES=?,YORDINATES=?,ZIPCODE=?,MONDAY=?,TUESDAY=?,WEDNESDAY=?,THURSDAY=?,FRIDAY=?,SATURDAY=?,SUNDAY=?,COUNTRY=?,ISWITHHOLDINGTAX=? WHERE ACCOUNTNUMBER=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(), 
				  this.branchname, this.address,this.city,this.contactnumber,this.image,this.province,this.xordinate,this.yordinate,this.zipcode,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,this.iswithholdingtax,this.accountnumber);
		
		if(res>0){
			AuditTrail audit  = new AuditTrail();
			audit.setIp(sess.getIpAddress());
			audit.setModuleid(String.valueOf("1102"));
			audit.setEntityid(this.branchname);
			audit.setLog("Updated successfully");
			audit.setStatus("00");
			audit.setData(this.branchname+"|"+this.address+"|"+this.city+"|"+this.province+"|"+this.country+"|"+this.zipcode+"|"+this.contactnumber+"|"+this.xordinate+"|"+this.yordinate+"|"+this.monday+"|"+this.tuesday+"|"+this.wednesday+"|"+this.thursday+"|"+this.friday+"|"+this.saturday+"|"+this.sunday+"|"+this.accountnumber+"|"+this.iswithholdingtax);
			audit.setImage(this.image);
			audit.setUserid(sess.getAccount().getId());
			audit.setUsername(sess.getAccount().getUserName());
			audit.setSessionid(sess.getId());
    		audit.setBrowser(sess.getBrowser());
		    audit.setBrowserversion(sess.getBrowserversion());
		    audit.setPortalversion(sess.getPortalverion());
		    audit.setOs(sess.getOs());
		    audit.setUserslevel(sess.getAccount().getGroup().getName());
		    audit.setOlddata(row.getString("BUSINESS")+"|"+row.getString("ADDRESS")+"|"+row.getString("CITY")+"|"+row.getString("PROVINCE")+"|"+row.getString("COUNTRY")+"|"+row.getString("ZIPCODE")+"|"+row.getString("CONTACTNUMBER")+"|"+row.getString("XORDINATES")+"|"+row.getString("YORDINATES")+"|"+row.getString("MONDAY")+"|"+row.getString("TUESDAY")+"|"+row.getString("WEDNESDAY")+"|"+
					row.getString("THURSDAY")+"|"+row.getString("FRIDAY")+"|"+row.getString("SATURDAY")+"|"+row.getString("SUNDAY")+"|"+row.getString("ACCOUNTNUMBER")+"|"+row.getString("ISWITHHOLDINGTAX"));
			
			audit.insert();
			return true;
		}else{
			return false;
		}
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBUSINESS WHERE ACCOUNTNUMBER=?", this.accountnumber).size()>0;
	}
	
}
