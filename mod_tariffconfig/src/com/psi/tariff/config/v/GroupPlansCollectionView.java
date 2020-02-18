package com.psi.tariff.config.v;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.psi.tariff.config.util.Tariffs;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportView;

public class GroupPlansCollectionView extends ReportView{
		public GroupPlansCollectionView(String code, ModelCollection data) {
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
				Tariffs i = (Tariffs) m;
				JSONObject item = new JSONObject();
					JSONArray modules = new JSONArray();
					JSONArray notifications = new JSONArray();
					for(String intModule : i.getPlanid()){
						modules.add(intModule);
					}
					item.put("PLANID",modules);
				
				arr.add(item);
			}
			
			obj.put("Code", 00);
			obj.put("Message", "Success");
			obj.put("Data",arr.toString());
			return obj.toString();
		}

	}
