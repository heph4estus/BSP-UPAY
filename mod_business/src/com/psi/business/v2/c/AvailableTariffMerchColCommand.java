package com.psi.business.v2.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.business.v.CollectionView;
import com.psi.business.v.NoDataFoundView;
import com.psi.business.v2.m.TariffGrouCollection;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class AvailableTariffMerchColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				TariffGrouCollection model = new TariffGrouCollection();
				model.setAuthorizedSession(sess);
					
						if(model.hasRows()){							
							return new CollectionView("00",model);  
						}else{
								ObjectState state = new ObjectState("01", "No data found");
								return new NoDataFoundView(state); 
						}
					
			}else{		
				UISession u = new UISession(null);
			    u.setState(new ObjectState("TLC-3902-01"));
			    v = new SessionView(u);
			}		
		}catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
		    u.setState(new ObjectState("TLC-3902-01"));
		    v = new SessionView(u);
			Logger.LogServer(e);
	} catch (Exception e) {
		UISession u = new UISession(null);
	    u.setState(new ObjectState("TLC-3902-01"));
	    v = new SessionView(u);
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "AVAILTARIFFMERCCOLLECTION";
	}

	@Override
	public int getId() {
		return 0;
	}

}
