package com.psi.dashboard.c;

import com.psi.dashboard.m.DailySalesAirtime;
import com.psi.dashboard.m.DailySalesBillPayment;
import com.psi.dashboard.v.CollectionView;
import com.psi.dashboard.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class DailySalesBillPaymentCommand extends UICommand{

	@Override
	public IView execute() {
		String accountnumber = this.params.get("AccountNumber").toString();
		DailySalesBillPayment report = new DailySalesBillPayment();
		report.setAccountnumber(accountnumber);
		if(report.hasRows()){
			return new CollectionView("00",report);  
		}else{
				ObjectState state = new ObjectState("01", "No data found");
				return new NoDataFoundView(state); 
		}
	}

	@Override
	public String getKey() {
		return "DAILYSALESBILLSPAYMENT";
	}

	@Override
	public int getId() {
		return 0;
	}

}
