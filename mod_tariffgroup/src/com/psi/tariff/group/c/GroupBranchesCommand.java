package com.psi.tariff.group.c;

import com.psi.tariff.group.m.GroupBranchesCollection;
import com.psi.tariff.group.v.GroupModulesCollectionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.UICommand;

public class GroupBranchesCommand extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
	//    
//	    SessionView v = null;
	    
//	    try{
	     
	     
	//   sess = ExistingSession.parse(this.reqHeaders);
	//    
//		    
//		    
//		if(sess.exists()) {
					String accountnumber = this.params.get("AccountNumber");
					
					ModelCollection ret = null;
					GroupBranchesCollection s = new GroupBranchesCollection(accountnumber);
		//			d.setAuthorizedSession(sess);
					if(s.hasRows()){
						ret = s;
					}
					GroupModulesCollectionView res = new GroupModulesCollectionView("00",ret);
					return res;
	    }		
		
	//   }else{
//	        UISession u = new UISession(null);
//	     u.setState(new ObjectState("TLC-3902-01"));
//	     v = new SessionView(u);
//	      
	//   }
	//   
	//}catch (SessionNotFoundException e) {
//	    UISession u = new UISession(null);
//	    u.setState(new ObjectState("TLC-3902-01"));
//	    v = new SessionView(u);
	//   
	//  }
	//return v;
//		}

	@Override
	public String getKey() {
		return "GROUPBRANCHES";
	}

	@Override
	public int getId() {
		return 0;
	}

}
