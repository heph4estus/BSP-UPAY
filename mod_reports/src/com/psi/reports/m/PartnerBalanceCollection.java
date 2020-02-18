package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.DbWrapper;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class PartnerBalanceCollection extends ModelCollection
{
  protected String partner;

  public boolean hasRows()
  {
    if (this.partner.equalsIgnoreCase("ALL")) {
      DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM  IBAYADPH.TBLPREFUNDBALANCE3PP ORDER BY TIMESTAMP ASC", new Object[0]);

      if (!r.isEmpty())
      {
        for (DataRow row : r) {
          ReportItem m = new ReportItem();
          m.setProperty("TIMESTAMP", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
          m.setProperty("PARTNER", row.getString("PARTNER") == null ? "" : row.getString("PARTNER").toString());
          m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : row.getString("AMOUNT").toString());

          add(m);
        }
      }
      return r.size() > 0;
    }
    DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM  IBAYADPH.TBLPREFUNDBALANCE3PP WHERE PARTNER=? ORDER BY TIMESTAMP ASC", new Object[] { this.partner });

    if (!r.isEmpty())
    {
      for (DataRow row : r) {
        ReportItem m = new ReportItem();
        m.setProperty("TIMESTAMP", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
        m.setProperty("PARTNER", row.getString("PARTNER") == null ? "" : row.getString("PARTNER").toString());
        m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : row.getString("AMOUNT").toString());

        add(m);
      }
    }
    return r.size() > 0;
  }

  public String getPartner()
  {
    return this.partner;
  }
  public void setPartner(String partner) {
    this.partner = partner;
  }
}