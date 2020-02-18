package com.psi.branch.m;

import java.util.ArrayList;
import com.psi.audit.trail.m.GetAuditPreviousData;
import com.psi.branch.utils.Branch;
import com.psi.branch.utils.EmailUtils;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class ManageUpgrade extends Branch{
	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.accountnumber);
		  previous.setQuery("SELECT BRANCH FROM TBLBRANCHES B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER=?");	  
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	
	@SuppressWarnings("unchecked")
	public boolean approve() throws java.text.ParseException{
		UISession sess = this.getAuthorizedSession();
		DataRow branch = SystemInfo.getDb().QueryDataRow("SELECT BRANCH,AI.EMAIL,B.ACCOUNTNUMBER FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER = ?", this.accountnumber);
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLMERCHANTDETAILS SET UPGRADED = 'YES',DATEAPPROVED=SYSDATE,APPROVEDBY=? WHERE ACCOUNTNUMBER= ?;\n");		
		query.append("INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) SELECT ID,?,CATEGORY FROM TBLSERVICES WHERE TYPE = 'BILLER';\n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername().toString(), this.accountnumber,this.accountnumber);					
			if(res>0){
					EmailUtils.sendapprovenotif(branch.getString("ACCOUNTNUMBER").toString());
					EmailUtils.sendapprovesms(branch.getString("ACCOUNTNUMBER").toString());
					//EmailUtils.sendApproveUpgrade(branch.getString("BRANCH").toString(), sess.getAccount().getUsername().toString());
					EmailUtils.sendApproveUpgrade(branch.getString("BRANCH").toString(), sess.getAccount().getUsername().toString(),branch.getString("EMAIL").toString(),branch.getString("ACCOUNTNUMBER").toString(),"");
				return true;
			}else{
				return false;
			}		
	}
	
	public boolean reject(){
				
		UISession sess = this.getAuthorizedSession();
		DataRow branch = SystemInfo.getDb().QueryDataRow("SELECT BRANCH,AI.EMAIL,B.ACCOUNTNUMBER FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER = ?", this.accountnumber);
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLMERCHANTDETAILS SET UPGRADED = 'REJECTED',DATEAPPROVED=SYSDATE,APPROVEDBY=?,REASON=? WHERE ACCOUNTNUMBER= ?;\n");		
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername().toString(),this.reason, this.accountnumber);					
			if(res>0){
					EmailUtils.sendrejectnotif(branch.getString("ACCOUNTNUMBER").toString());
					//EmailUtils.sendRejectUpgrade(branch.getString("BRANCH").toString(), sess.getAccount().getUsername().toString());
					EmailUtils.sendRejectUpgrade(branch.getString("BRANCH").toString(), sess.getAccount().getUsername().toString(),branch.getString("EMAIL").toString(),branch.getString("ACCOUNTNUMBER").toString());
				return true;
			}else{
				return false;
			}			
	}
	
	public boolean notifyt(){
	
		UISession sess = this.getAuthorizedSession();
		DataRow branch = SystemInfo.getDb().QueryDataRow("SELECT BRANCH,AI.EMAIL FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER = ?", this.accountnumber);
		DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = ?", this.accountnumber);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLMERCHANTDETAILS SET DATEAPPROVED=SYSDATE,APPROVEDBY=?,NOTIFY=?,REASON=? WHERE ACCOUNTNUMBER= ?;\n");		
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		int res = SystemInfo.getDb().QueryUpdate(query.toString(),sess.getAccount().getUsername().toString(),this.notify,this.reason,this.accountnumber);					
		if(res>0){
				EmailUtils.sendnotifysms(this.accountnumber);
			    
			    String not = this.notify.replace("[", "");
			    not = not.replace("]", "");
			    not = not.replace(",", "\n");
			    not = not.replace("\"", "");
			    EmailUtils.sendnotifynotif(this.accountnumber,not,user.getString("FIRSTNAME").toString(),user.getString("LASTNAME").toString(),this.reason);
				EmailUtils.sendNotifyUpgrade(branch.getString("BRANCH").toString(), sess.getAccount().getUsername().toString(),user.getString("EMAIL").toString(),user.getString("FIRSTNAME").toString(),user.getString("LASTNAME").toString(),not,this.reason);
				return true;
		}else{
			return false;
		}
		
	}
	
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE PASSWORD=ENCRYPT(?,?,USERNAME) AND USERNAME =?",this.password,SystemInfo.getDb().getCrypt(),sess.getAccount().getUserName() ).size()>0;
	}
	
	public boolean exist(){
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber);
		if(!rr.getString("KEYACCOUNT").equals("834591471124")){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;	
		}else{
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;
		}
	}
	
	public boolean existupgrade(){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;

	}
}
