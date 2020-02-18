package com.psi.customer.support.c;

import com.psi.customer.support.m.CustomerCollection;
import com.psi.customer.support.v.CollectionView;
import com.psi.customer.support.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class CustomerColCommand extends UICommand{

	@Override
	public IView execute() {
		String accountnumber = this.params.get("AccountNumber").toString();
		CustomerCollection model = new CustomerCollection();
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
		return "CUSTOMERCOLLECTION";
	}

	@Override
	public int getId() {
		return 0;
	}

}
