package com.psi.role.management.v;

import org.json.simple.JSONObject;

import com.psi.role.management.m.RegisteredPingenGroup;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.NormalView;
import com.tlc.gui.modules.session.UIGroup;

public class JsonView extends NormalView{
	
		public JsonView(Model m) {
		super(m);
		
		// TODO Auto-generated constructor stub
		}

		@Override
		public String render() {
			JSONObject obj = new JSONObject();
			obj.put("Code",this.data.getState().getCode().toString());
			obj.put("Message",this.data.getState().getMessage().toString());
			
			
			return obj.toJSONString();
		}

	}

