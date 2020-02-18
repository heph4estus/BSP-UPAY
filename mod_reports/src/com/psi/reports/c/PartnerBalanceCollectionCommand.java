package com.psi.reports.c;

import com.psi.reports.m.PartnerBalanceCollection;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class PartnerBalanceCollectionCommand extends UICommand
{
  public IView execute()
  {
    String partner = this.params.get("Partner").toString();

    PartnerBalanceCollection col = new PartnerBalanceCollection();
    col.setPartner(partner);

    if (col.hasRows()) {
      return new CollectionView("00", col);
    }
    ObjectState state = new ObjectState("01", "NO data found");
    return new NoDataFoundView(state);
  }

  public String getKey()
  {
    return "THIRDPARTYPARTNERSBALANCECOLLECTION";
  }

  public int getId()
  {
    return 1508;
  }
}