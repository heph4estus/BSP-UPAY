package com.psi.tariff.group.v;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.psi.tariff.group.m.Branches;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportView;

public class GroupModulesCollectionView extends ReportView{
		public GroupModulesCollectionView(String code, ModelCollection data) {
		super(code, data);
		// TODO Auto-generated constructor stub
	}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		
		@Override
		public String render() {
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			if(this.rows!=null)
			for(Model m: this.rows){
				Branches i = (Branches) m;
				JSONObject item = new JSONObject();
					JSONArray modules = new JSONArray();
					JSONArray notifications = new JSONArray();
					for(String intModule : i.getBranches()){
						modules.add(intModule);
					}
					item.put("BRANCHES",modules);
					
					
				arr.add(item);
			}
			
			obj.put("Code", 00);
			obj.put("Message", "Success");
			obj.put("Data",arr.toString());
			return obj.toString();
		}

	}
