package com.psi.role.management.c;
import com.psi.role.management.m.GroupCollection;
import com.psi.role.management.v.JsonCollectionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class SearchGroupCommand extends UICommand{

	@Override
	public IView execute() {
			ModelCollection ret = null;
			GroupCollection d = new GroupCollection();
			String portal = this.params.get("PORTAL");
			d.setPortal(portal);
			if(d.hasRows()){
				ret = d;
			}
			JsonCollectionView res = new JsonCollectionView("00",ret);
			return res;
	}

	@Override
	public String getKey() {
		return "REGISTEREDROLE";
	}

	@Override
	public int getId() {
		return 3000;
	}




}
