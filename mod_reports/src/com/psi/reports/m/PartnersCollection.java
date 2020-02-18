package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.DbWrapper;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class PartnersCollection extends ModelCollection
{
  public boolean hasRows()
  {
    DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT PARTNER FROM  IBAYADPH.TBLPREFUNDBALANCE3PP ORDER BY PARTNER ASC", new Object[0]);

    if (!r.isEmpty())
    {
      for (DataRow row : r) {
        ReportItem m = new ReportItem();
        m.setProperty("PARTNER", row.getString("PARTNER") == null ? "" : row.getString("PARTNER").toString());

        add(m);
      }
    }
    return r.size() > 0;
  }
}