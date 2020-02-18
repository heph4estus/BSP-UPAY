package com.psi.backoffice.c;


import com.psi.backoffice.m.PndgUserCollection;
import com.psi.backoffice.v.CollectionView;
import com.psi.backoffice.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class PndgUserCollectionCommand extends UICommand{

	@Override
	public IView execute() {
				
		PndgUserCollection col = new PndgUserCollection();
				
				if(col.hasRows()){
					return new CollectionView("00",col);  
				}else{
						ObjectState state = new ObjectState("01", "NO data found");
						return new NoDataFoundView(state); 
				}
	}

	@Override
	public String getKey() {
		return "BACKOFFICEPNDG";
	}

	@Override
	public int getId() {
		return 1007;
	}

}
