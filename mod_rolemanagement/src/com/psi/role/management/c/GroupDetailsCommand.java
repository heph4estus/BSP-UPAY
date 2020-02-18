package com.psi.role.management.c;

import com.psi.role.management.m.RegisteredPingenGroup;
import com.psi.role.management.v.SearchView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class GroupDetailsCommand extends UICommand {

	@Override
	public IView execute() {
		String name = this.params.get("GROUPNAME");
		RegisteredPingenGroup pingenGroup = new RegisteredPingenGroup(name);
		
		try{
			
			if(pingenGroup.exists()){

				ObjectState state = new ObjectState("00","Successfull");
				pingenGroup.setState(state);
				SearchView view = new SearchView(pingenGroup);
				return view;
			}
			else{
				ObjectState state = new ObjectState("01","Unsuccessfull");
				pingenGroup.setState(state);
				SearchView view = new SearchView(pingenGroup);
				return view;
			}
		}catch(Exception e){
			ObjectState state = new ObjectState("02","Unsuccessfull");
			pingenGroup.setState(state);
			SearchView view = new SearchView(pingenGroup);
			return view;
			
		}

	}

	@Override
	public String getKey() {
		return "GROUPDETAILS";
	}

	@Override
	public int getId() {
		return 3020;
	}

}
