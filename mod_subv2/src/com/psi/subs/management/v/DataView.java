package com.psi.subs.management.v;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.NormalView;

public class DataView extends NormalView implements IView {

	public DataView(Model m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String render() {
		JSONObject obj = new JSONObject();
		obj.put("Code", this.data.getState().getCode().toString());
		obj.put("Message", this.data.getState().getMessage().toString());

		try {

			obj.put("Data", this.data.getObject("Details").toString());
		} catch (NullPointerException e) {

		}
		return obj.toJSONString();
	}

}


