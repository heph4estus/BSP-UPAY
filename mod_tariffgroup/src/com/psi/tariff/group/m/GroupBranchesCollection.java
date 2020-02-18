package com.psi.tariff.group.m;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;

@SuppressWarnings("serial")
public class GroupBranchesCollection extends ModelCollection{
	private String accountnumber;
	
	public GroupBranchesCollection(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	@Override
	public boolean hasRows() {
		Branches service = new Branches();
		service.setBranches(SystemInfo.getDb().QueryArray("SELECT DISTINCT ACCOUNTNUMBER FROM TBLCOMPANYTARIFF WHERE COMPANY = ?","", this.accountnumber));
		this.add(service);
		return true;
	}
	

}
