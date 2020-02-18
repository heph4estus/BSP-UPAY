package com.tlc.gui.absmobile.modules.session.m;

import java.util.Date;

import com.tlc.absmobile.bp.session.AbstractExistingSession;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.PluginHeaders;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.Token;
import com.tlc.gui.modules.session.UIAccount;
import com.tlc.gui.modules.session.UIGroup;

public class ExistingSession extends AbstractExistingSession {

	public static ExistingSession parse(PluginHeaders h) throws SessionNotFoundException{
		String auth = h.get("authorization");
		String decoded = new String(StringUtil.base64Decode(auth));
		String contextInfo[] = decoded.split(":");
		String[] authInfo = contextInfo[6].split("@");
		ExistingSession ret = new ExistingSession(contextInfo[1],authInfo[1],contextInfo[2],contextInfo[3],contextInfo[4],contextInfo[5],Token.parse(authInfo[0]));
		return ret;
	}
	public ExistingSession(String id,String ip,String browser,String browserversion,String portalversion, String os,Token t) throws SessionNotFoundException {
		super(id,ip,t);
		this.browser = browser;
		this.browserversion=browserversion;
		this.portalverion = portalversion;
		this.os = os;
	}

	@Override
	public boolean exists() {
		boolean ret = false;
		StringBuilder query = new StringBuilder();
		query.append("SELECT WU.USERID,WU.USERNAME,LASTNAME,FIRSTNAME,LASTLOGIN,U.ID AS GROUPID,U.USERSLEVEL AS GROUPNAME,ALLOWEDIP\n" );
		query.append("FROM TBLUSERS WU \n");
		query.append("    INNER JOIN TBLUSERSLEVEL U ON U.USERSLEVEL=WU.USERSLEVEL\n");
		query.append("WHERE USERID=? AND SESSIONID=? AND U.ACCOUNTSTATUS='ACTIVE'\n");
		DataRow r = db.QueryDataRow(query.toString(),this.token.UserId,this.id);
		if(r!=null && !r.isEmpty()){
			this.setAccount(new UIAccount());
			this.getAccount().setId((Integer.parseInt(r.get("USERID").toString())));
			this.getAccount().setUsername(r.get("USERNAME").toString());
			this.getAccount().setLastName(r.get("LASTNAME").toString());
			this.getAccount().setFirstName(r.get("FIRSTNAME").toString());
			this.getAccount().setLastLogin((Date)r.get("LASTLOGIN"));
			this.getAccount().setGroup(new UIGroup(r.get("GROUPNAME").toString()));
			this.getAccount().getGroup().setId(Integer.parseInt(r.get("GROUPID").toString()));
			this.getAccount().setAllowedIp(r.get("ALLOWEDIP").toString());
			this.getAccount().getGroup().setModules(db.QueryArray("SELECT MODULEID FROM TBLGROUPMODULES WHERE GROUPID=?",new Integer(0),this.getAccount().getGroup().getId()));
			this.getAccount().getGroup().setSessionTimeout(db.QueryScalar("SELECT SESSIONTIMEOUT FROM TBLUSERSLEVEL WHERE USERSLEVEL=?",0,r.getString("GROUPNAME")));
			this.getAccount().setBrowser(this.browser);
			this.getAccount().setBrowserversion(this.browserversion);
			this.getAccount().setPortalversion(this.portalverion);
			this.getAccount().setOs(this.os);
			ret = true;
		}
		return ret;
	}
	

	@Override
	public boolean monitor() {
		
		if(!this.valid()){
			this.setState(new ObjectState("TLC-3902-01"));
			Logger.LogServer("Invalid");
			//this.setState(new ObjectState("Invalid"));
			return false;
		}
		if(this.expired()){
			this.setState(new ObjectState("TLC-3902-011"));
			Logger.LogServer("Expired");
			//this.setState(new ObjectState("Expired"));
			return false;
		} 
		if(!this.exists()){
			this.setState(new ObjectState("TLC-3902-012"));
			Logger.LogServer("not exist");
			//this.setState(new ObjectState("not exists"));
			return false;
		}
	
		Logger.LogServer("monitor username:"+this.getAccount().getUserName());
		this.token.extend(this.getAccount().getGroup().getSessionTimeout());
		this.removeProperty(PROP_ACCOUNT);
		this.setState(new ObjectState("00"));
		return true;
	}
	
	

	@Override
	public boolean terminate() {
		this.setState(new ObjectState("TLC-3903-01"));
		if(this.valid() && this.exists()){
			StringBuilder query = new StringBuilder();
			
			query.append("DECLARE\n");
			query.append("	vMODULEID 				NUMBER;\n");
			query.append("	vUSERNAME				VARCHAR(50);\n");
			query.append("	vUSERID 				NUMBER;\n");
			query.append("	vSESSIONID				VARCHAR(50);\n");
			query.append("	vIPADDRESS				VARCHAR(50);\n");
			query.append("	vBROWSER				VARCHAR(50);\n");
			query.append("	vBROWSERVERSION			VARCHAR(50);\n");
			query.append("	vPORTALVERSION			VARCHAR(50);\n");
			query.append("	vOS						VARCHAR(50);\n");
			query.append("	vUSERSLEVEL				VARCHAR(50);\n");
			query.append("BEGIN\n");
			query.append("	vMODULEID	:=3903;\n");
			query.append("	vUSERID	:=?;\n");
			query.append("	vUSERNAME	:=?;\n");
			query.append("	vSESSIONID	:=?;\n");
			query.append("	vIPADDRESS	:=?;\n");
			query.append("	vBROWSER	:=?;\n");
			query.append("	vBROWSERVERSION	:=?;\n");
			query.append("	vPORTALVERSION	:=?;\n");
			query.append("	vOS	:=?;\n");
			query.append("	vUSERSLEVEL	:=?;\n");
			query.append("	UPDATE TBLUSERS SET SESSIONID=NULL,LASTLOGIN=SYSDATE WHERE USERID=vUSERID;\n");
			query.append("	INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,SESSIONID,MODULE,IP,STATUS,BROWSER,BROWSERVERSION,PORTALVERSION,OS,USERSLEVEL) VALUES(vUSERNAME,vUSERID,vSESSIONID,vMODULEID,vIPADDRESS,'00',vBROWSER,vBROWSERVERSION,vPORTALVERSION,vOS,vUSERSLEVEL);\n");
			query.append(SQL_FOOTER);
			if(this.db.QueryUpdate(query.toString(), this.getAccount().getId(),this.getAccount().getUserName(),this.id,this.token.IpAddress,this.getAccount().getBrowser(),this.getAccount().getBrowserversion(),this.getAccount().getPortalversion(),this.getAccount().getOs(),this.getAccount().getGroup().getName())>0){
				this.setState(new ObjectState("00"));
				return true;	
			}
		}
		return false;
	}

}
