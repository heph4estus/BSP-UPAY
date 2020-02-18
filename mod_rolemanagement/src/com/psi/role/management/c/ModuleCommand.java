package com.psi.role.management.c;

import com.psi.role.management.m.ModuleCollection;
import com.psi.role.management.v.ModuleCollectionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ModuleCommand extends UICommand{

	@Override
	public IView execute() {
			ModelCollection ret = null;
			String portal = this.params.get("PORTAL");
			ModuleCollection d = new ModuleCollection();
			d.setPortal(portal);
			if(d.hasRows()){
				ret = d;
			}
			ModuleCollectionView res = new ModuleCollectionView("00",ret);
			return res;
    
	}

	@Override
	public String getKey() {
		return "MODULES";
	}

	@Override
	public int getId() {
		return 3010;
	}




}
