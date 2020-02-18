package com.psi.subs.management.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class BlackListedCollection extends ModelCollection
{
  public boolean hasRows()
  {
    DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM VWBLACKLIST ORDER BY TIMESTAMP DESC", new Object[0]);

    if (!r.isEmpty())
    {
      for (DataRow row : r) {
        ReportItem m = new ReportItem();
        for (String key : row.keySet()) {
          m.setProperty(key, row.getString(key).toString());
        }
        add(m);
      }
    }
    return r.size() > 0;
  }
}