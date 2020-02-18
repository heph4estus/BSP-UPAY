package com.psi.subs.management.m;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.axis.encoding.Base64;

import com.psi.recon.batch.add.bcwhite.listed.ExcelFileBcWhiteList;
import com.psi.recon.batch.add.black.listed.ExcelFileBcBlackList;
import com.psi.recon.batch.add.bspblack.listed.ExcelFileBspBlackList;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class NewUploadFile extends Model{
	protected String encodedfile;
	protected String filename;
	protected String filepath;
	protected String provider;
	protected long totalprefund;
	protected long remainingbalance;
	protected long discount;
	protected String cutoffmonth;
	protected String password;
	protected String username;
	protected String type;
	protected String reason;
	
	public boolean uploadbcblack(String batch){
		UISession sess = this.getAuthorizedSession();
		  try {
			  String pathbase = SystemInfo.getDb().QueryScalar("SELECT PATH FROM TBLFILEPATHUPLOAD WHERE STATUS = 1", "");
			  writeBytesToFileNio(convertToByte(this.encodedfile),  pathbase+"/blacklisted/bc/black/"+this.filename);
			  String FilePathAndName = pathbase+"/blacklisted/bc/black/"+this.filename;
	      ExcelFileBcBlackList f = ExcelFileBcBlackList.from(new File(FilePathAndName),batch,this.reason);
	      if (f.valid()) {
	          f.send();
	          SystemInfo.getDb().QueryUpdate("INSERT INTO TBLFILERECORDS (FILENAME,DATEUPLOADED,UPLOADEDBY,APPROVEDBY,BATCH,TYPE,FILESTATUS) VALUES(?,SYSDATE,?,?,?,'BCBLOCKLIST','PROCESSING')", this.filename,sess.getAccount().getUserName(),this.username,batch);
	          Logger.LogServer(this.provider);
	          return true;	          
	      }else{
	    	  this.setState(new ObjectState("01","Invalid File"));
	    	  return false;
	      }
		} catch (IOException e) {
			this.setState(new ObjectState("02",e.getMessage()));
			e.printStackTrace();
			return false;
		}
			  
	}
	public boolean uploadbcwhite(String batch){
		UISession sess = this.getAuthorizedSession();
		  try {
			  String pathbase = SystemInfo.getDb().QueryScalar("SELECT PATH FROM TBLFILEPATHUPLOAD WHERE STATUS = 1", "");			  
			  writeBytesToFileNio(convertToByte(this.encodedfile),  pathbase+"/blacklisted/bc/white/"+this.filename);
			  String FilePathAndName = pathbase+"/blacklisted/bc/white/"+this.filename;
			  ExcelFileBcWhiteList f = ExcelFileBcWhiteList.from(new File(FilePathAndName),batch,this.reason);
	      if (f.valid()) {
	          f.send();
	          SystemInfo.getDb().QueryUpdate("INSERT INTO TBLFILERECORDS (FILENAME,DATEUPLOADED,UPLOADEDBY,APPROVEDBY,BATCH,TYPE,FILESTATUS) VALUES(?,SYSDATE,?,?,?,'BCWHITELIST','PROCESSING')", this.filename,sess.getAccount().getUserName(),this.username,batch);
	          Logger.LogServer(this.provider);
	          return true;	          
	      }else{
	    	  this.setState(new ObjectState("01","Invalid File"));
	    	  return false;
	      }
		} catch (IOException e) {
			this.setState(new ObjectState("02",e.getMessage()));
			e.printStackTrace();
			return false;
		}
			  
	}
	public boolean uploadbspblack(String batch){
		UISession sess = this.getAuthorizedSession();
		  try {
			  String pathbase = SystemInfo.getDb().QueryScalar("SELECT PATH FROM TBLFILEPATHUPLOAD WHERE STATUS = 1", "");			  
			  writeBytesToFileNio(convertToByte(this.encodedfile),  pathbase+"/blacklisted/bsp/black/"+this.filename);
			  String FilePathAndName = pathbase+"/blacklisted/bsp/black/"+this.filename;
			  ExcelFileBspBlackList f = ExcelFileBspBlackList.from(new File(FilePathAndName),batch);
	      if (f.valid()) {
	          f.send();
	          SystemInfo.getDb().QueryUpdate("INSERT INTO TBLFILERECORDS (FILENAME,DATEUPLOADED,UPLOADEDBY,APPROVEDBY,BATCH,TYPE,FILESTATUS) VALUES(?,SYSDATE,?,?,?,'BSPBLACKLIST','PROCESSING')", this.filename,sess.getAccount().getUserName(),this.username,batch);
	          Logger.LogServer(this.provider);
	          return true;	          
	      }else{
	    	  this.setState(new ObjectState("01","Invalid File"));
	    	  return false;
	      }
		} catch (IOException e) {
			this.setState(new ObjectState("02",e.getMessage()));
			e.printStackTrace();
			return false;
		}
			  
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
	private static void writeBytesToFileNio(byte[] bFile, String fileDest) {

        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
        	Logger.LogServer(e);
            e.printStackTrace();
        }

    }
	public static byte[] convertToByte(String base64) throws IOException  
    {  
         return Base64.decode(base64);  
    }  
	
	
	public String getEncodedfile() {
		return encodedfile;
	}

	public void setEncodedfile(String encodedfile) {
		this.encodedfile = encodedfile;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public long getTotalprefund() {
		return totalprefund;
	}
	public void setTotalprefund(long totalprefund) {
		this.totalprefund = totalprefund;
	}
	public long getRemainingbalance() {
		return remainingbalance;
	}
	public void setRemainingbalance(long remainingbalance) {
		this.remainingbalance = remainingbalance;
	}
	public long getDiscount() {
		return discount;
	}
	public void setDiscount(long discount) {
		this.discount = discount;
	}
	public String getCutoffmonth() {
		return cutoffmonth;
	}
	public void setCutoffmonth(String cutoffmonth) {
		this.cutoffmonth = cutoffmonth;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
