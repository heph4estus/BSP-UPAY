package com.provider.management.m;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class ProviderAvailability extends Model{
	protected String status;
	protected String provider;
	
	public boolean setAvailability(){
			String query = SystemInfo.getDb().QueryScalar("SELECT SCRIPT FROM TBL3PPAVAILABILITY WHERE STATUS=? AND PROVIDER=?", "", this.status,this.provider);
			return SystemInfo.getDb().QueryUpdate(query.toString())>0;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}	
}
