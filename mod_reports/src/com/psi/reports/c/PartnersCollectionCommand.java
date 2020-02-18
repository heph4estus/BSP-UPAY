package com.psi.reports.c;

import com.psi.reports.m.PartnersCollection;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class PartnersCollectionCommand extends UICommand
{
  public IView execute()
  {
    PartnersCollection col = new PartnersCollection();
    if (col.hasRows()) {
      return new CollectionView("00", col);
    }
    ObjectState state = new ObjectState("01", "NO data found");
    return new NoDataFoundView(state);
  }

  public String getKey()
  {
    return "THIRDPARTYPARTNERSCOLLECTION";
  }

  public int getId()
  {
    return 0;
  }
}