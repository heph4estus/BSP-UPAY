package com.psi.reports.c;

import com.psi.reports.m.ReportBranchCollection;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class ReportBranchColCommand extends UICommand{

	@Override
	public IView execute() {
		String accountnumber = this.params.get("AccountNumber").toString();
		ReportBranchCollection model = new ReportBranchCollection();
				model.setAccountnumber(accountnumber);
			if(accountnumber.equals("ALL")){
				if(model.hasRowsAll()){
					return new CollectionView("00",model);  
				}else{
						ObjectState state = new ObjectState("01", "No data found");
						return new NoDataFoundView(state); 
				}
			}else{
				if(model.hasRows()){
					return new CollectionView("00",model);  
				}else{
						ObjectState state = new ObjectState("01", "No data found");
						return new NoDataFoundView(state); 
				}
			}
	}

	@Override
	public String getKey() {
		return "REPORTBRANCHCOLLECTION";
	}

	@Override
	public int getId() {
		return 0;
	}

}
