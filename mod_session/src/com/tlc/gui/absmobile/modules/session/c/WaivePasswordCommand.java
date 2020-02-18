package com.tlc.gui.absmobile.modules.session.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.audit.trail.v.JsonView;
import com.tlc.gui.absmobile.modules.session.m.WaivePassword;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class WaivePasswordCommand extends UICommand{

	@Override
	public IView execute() {
		String username = this.params.get("username").toString();
		String ip = this.params.get("ipaddress").toString();
		String browsername = this.params.get("browsername").toString();
		String browserversion = this.params.get("browserversion").toString();
		String osversion = this.params.get("osversion").toString();
		String portalversion = this.params.get("portalversion").toString();
		
		WaivePassword waive = new WaivePassword();
		waive.setUsername(username);
		if(!waive.existing()) {
			waive.setState(new ObjectState("01","User does not exist"));
			return new JsonView(waive);  
		}
		
		if(waive.waive()) {
			waive.setState(new ObjectState("00", "Password successfully waived for "+waive.getPasswordchange()+" days"));
			
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(ip);
    		audit.setModuleid("3905");
    		audit.setEntityid(waive.getUserid());
    		audit.setLog(waive.getState().getMessage());
    		audit.setStatus("00");
    		audit.setUserid(Integer.parseInt(waive.getUserid()));
    		audit.setUsername(username);
    		audit.setBrowser(browsername);
    		audit.setBrowserversion(browserversion);
    		audit.setOs(osversion);
    		audit.setPortalversion(portalversion);
    		audit.setUserslevel(waive.getUserslevel());
    		audit.setSessionid("");
    		audit.insert();
			return new JsonView(waive);  
		} else {
			waive.setState(new ObjectState("99"," System is busy"));
			return new JsonView(waive);  
		}
	}

	@Override
	public String getKey() {
		return "WAIVEPASSWORD";
	}

	@Override
	public int getId() {
		return 3905;
	}

}
