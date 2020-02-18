package com.psi.customer.support.m;

import java.util.Map;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.Model;

public class ReportItemv2 extends Model{
	
	public void setProperty(String key,JSONObject value){
		this.props.put(key, value);
	}
	public void setPropertyv2( Map<? extends String, ? extends Object> value){
		this.props.putAll(value);
	}
	
	public void setPropertyv3( String key,Map<? extends String, ? extends Object> value){
		this.props.put(key,value);
	}
}
