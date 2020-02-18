package com.psi.clearing.v;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.NormalView;

public class BalanceResponseView
extends NormalView
implements IView
{
public BalanceResponseView(Model m)
{
  super(m);
}

public String render()
{
  JSONObject obj = new JSONObject();
  JSONObject jsonGroup = new JSONObject();
  obj.put("Code", this.data.getState().getCode().toString());
  obj.put("Message", this.data.getState().getMessage().toString());
  for (String key : this.data.keys()) {
    jsonGroup.put(key, this.data.getObject(key).toString());
  }
  obj.put("Data", jsonGroup);
  return obj.toJSONString();
}
}