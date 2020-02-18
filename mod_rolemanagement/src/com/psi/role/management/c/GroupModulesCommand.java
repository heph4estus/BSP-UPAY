package com.psi.role.management.c;

import com.psi.role.management.m.GroupModulesCollection;
import com.psi.role.management.v.GroupModulesCollectionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;
public class GroupModulesCommand extends UICommand{

	@Override
	public IView execute() {
	
				String id = this.params.get("GROUPID");
				
				ModelCollection ret = null;
				GroupModulesCollection d = new GroupModulesCollection(id);
				if(d.hasRows()){
					ret = d;
				}
				GroupModulesCollectionView res = new GroupModulesCollectionView("00",ret);
				return res;
    }		

	@Override
	public String getKey() {
		return "ROLEMODULES";
	}

	@Override
	public int getId() {
		return 3030;
	}




}
