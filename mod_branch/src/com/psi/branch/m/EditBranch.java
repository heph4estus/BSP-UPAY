package com.psi.branch.m;

import java.text.ParseException;
import java.util.ArrayList;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.audit.trail.m.GetAuditPreviousData;
import com.psi.branch.utils.Branch;
import com.tlc.common.DataRow;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class EditBranch extends Branch{
	public void getData(){
		  //****AUDITTRAIL PREVIOUS DATA********
		  GetAuditPreviousData previous = new GetAuditPreviousData();
		  ArrayList<Object> parameters = new ArrayList<Object>();  
		  parameters.add(this.accountnumber);
		  previous.setQuery("SELECT BRANCH,B.ADDRESS,B.CITY,B.PROVINCE,B.COUNTRY,B.ZIPCODE,B.CONTACTNUMBER,B.XORDINATES,B.YORDINATES,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,B.ACCOUNTNUMBER,B.RAFILELOCATION,B.RAFILENAME,M.TIN,M.NATUREOFBUSINESS,M.ESTIMATEDGROSSSALE,M.BANKNAME,M.BANKACCOUNTTYPE,M.BANKACCOUNTNUMBER,M.MODEOFPAYMENT,M.PAYMENTTERMS,M.MAXCREDITAMOUNT,M.BANKBRANCH FROM TBLBRANCHES B INNER JOIN TBLMERCHANTDETAILS M ON B.ACCOUNTNUMBER = M.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER=?");	  
		  previous.setParam(parameters);
		  this.setAuditdata(previous.getData());
		  //****END OF AUDITTRAIL PREVIOUS DATA********
	}
	public boolean update() throws ParseException{
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLBRANCHES SET BRANCH=?,ADDRESS=?,LOCATION=?,CITY=?,CONTACTNUMBER=?,PROOFADDRESS=?,PROVINCE=?,XORDINATES=?,YORDINATES=?,ZIPCODE=?,MONDAY=?,TUESDAY=?,WEDNESDAY=?,THURSDAY=?,FRIDAY=?,SATURDAY=?,SUNDAY=?,COUNTRY=?,RAFILENAME=?,TIN=?,NATUREOFBUSINESS=?,GROSSSALES=?,BANKNAME=?,BANKTYPE=?,BANKACCOUNTNUMBER=? WHERE ACCOUNTNUMBER=?; \n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET DATEUPDATED=SYSDATE,MSISDN=?, TIN=?, CITY=?, SPECIFICADDRESS=?, POSTALCODE=?, COORDINATES=? WHERE ACCOUNTNUMBER = ?;\n");
		query.append("UPDATE TBLMERCHANTDETAILS SET OWNERFIRSTNAME=?,OWNERMIDDLENAME=?,OWNERLASTNAME=?,OWNERSPECIFICADDRESS=?,OWNERCITY=?,OWNERPROVINCE=?,OWNERCOUNTRY=?,OWNERZIPCODE=?,LANDLINE=?,EMAIL=?,SECONDARYMSISDN=?,NATUREOFBUSINESS=?,NOOFBRANCH=?,ESTIMATEDGROSSSALE=?,BANKNAME=?,BANKACCOUNTNUMBER=?,BANKBRANCH=?,BANKACCOUNTTYPE=?,TIN=?,BRGYPERMITNO=?,DATEISSUEDBRGYPERMIT=TO_DATE(?,'YYYY-MM-DD'),BUSINESSPERMT=?,DATEISSUEDBUSINESSPERMIT=TO_DATE(?,'YYYY-MM-DD'),BUSINESSTYPE=?,CATEGORY=?,MODIFIEDBY=?,MODEOFPAYMENT=?,PAYMENTTERMS=?,MAXCREDITAMOUNT=? WHERE ACCOUNTNUMBER=?;\n");
		query.append("UPDATE TBLAMLACCOUNTTYPERECEIVE SET MAXAMOUNT=?,MAXAMOUNTDAY=?,MAXAMOUNTWEEK=?,MAXAMOUNTMONTH=? WHERE ACCOUNTNUMBER=?; \n");			
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
				 									  this.branchname, this.specificaddress,this.address,this.city,this.contactnumber,this.image,this.province,this.xordinate,this.yordinate,this.zipcode,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,this.rafilename,this.tin,this.natureofbusiness,this.grosssales,this.bankname,this.banktype,this.banknumber,this.accountnumber,
				 									  this.contactnumber,this.tin,this.city,this.specificaddress,this.zipcode,this.xordinate+","+this.yordinate,this.accountnumber,
				 									  "","","","","","","","","","","",this.natureofbusiness,"",this.grosssales,this.bankname,this.banknumber,this.bankbranch,this.banktype,this.tin,"","","","","","",sess.getAccount().getUsername(),this.paymentmode,this.paymentterms,LongUtil.toLong(this.maxamount),this.accountnumber,
				 									 LongUtil.toLong(this.maxamount),LongUtil.toLong(this.maxamount),LongUtil.toLong(this.maxamount),LongUtil.toLong(this.maxamount),this.accountnumber)>0;	
		
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber).size()>0;
	}
	public boolean existmsisdn(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN=? AND ACCOUNTNUMBER<>?", this.contactnumber,this.accountnumber).size()>0;
	}
	public boolean existname(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE BRANCH=? AND ACCOUNTNUMBER<> ?", this.branchname,this.accountnumber).size()>0;
	}
	public boolean existpndgbusiness(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE FQN = ENCRYPT(?,?,ACCOUNTNUMBER) AND ACCOUNTNUMBER<> ?", this.branchname,SystemInfo.getDb().getCrypt(),this.accountnumber).size()>0;
	}
}
