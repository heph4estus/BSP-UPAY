package com.psi.reports.c;

import com.psi.reports.m.ReportCompanyCollection;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class ReportCompanyColCommand extends UICommand{

	@Override
	public IView execute() {

		ReportCompanyCollection model = new ReportCompanyCollection();

				if(model.hasRows()){
					return new CollectionView("00",model);  
				}else{
						ObjectState state = new ObjectState("01", "No data found");
						return new NoDataFoundView(state); 
				}
	}

	@Override
	public String getKey() {
		return "REPORTCOMPANYCOLLECTION";
	}

	@Override
	public int getId() {
		return 0;
	}

}
