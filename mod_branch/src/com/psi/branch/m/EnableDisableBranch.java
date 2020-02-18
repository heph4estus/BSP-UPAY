package com.psi.branch.m;

import com.psi.branch.utils.Branch;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class EnableDisableBranch extends Branch{
	
	public boolean update (){
		if(this.status.equals("DISABLE")){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLBRANCHES SET STATUS=0,REMARKS=? WHERE ACCOUNTNUMBER=?; \n");
		query.append("UPDATE TBLUSERS SET LOCKED = 'YES',STATUS='INACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.remarks,this.accountnumber,this.accountnumber)>0;	
		 
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLBRANCHES SET STATUS=1,REMARKS=? WHERE ACCOUNTNUMBER=?; \n");
			query.append("UPDATE TBLUSERS SET LOCKED = 'NO',STATUS='ACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 return SystemInfo.getDb().QueryUpdate(query.toString(),this.remarks,this.accountnumber,this.accountnumber)>0;	
		}
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber).size()>0;
	}
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ? AND PASSWORD=ENCRYPT(?,?,USERNAME)", sess.getAccount().getUserName(),this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}

}
