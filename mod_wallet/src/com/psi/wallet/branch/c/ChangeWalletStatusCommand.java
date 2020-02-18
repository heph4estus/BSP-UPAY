package com.psi.wallet.branch.c;

import com.psi.audit.trail.m.AuditTrail;
import com.psi.wallet.branch.m.ChangeWalletStatus;
import com.psi.wallet.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.RequestParameters;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UIAccount;
import com.tlc.gui.modules.session.UIGroup;
import com.tlc.gui.modules.session.UISession;

public class ChangeWalletStatusCommand extends UICommand
{
  public IView execute()
  {
    ExistingSession sess = null;
    SessionView v = null;
    try
    {
      sess = ExistingSession.parse(this.reqHeaders);
      if (sess.exists())
      {
        String id = this.params.get("Id");
        String linkid = this.params.get("LinkId");
        String bankname = this.params.get("BankName");
        String bankcode = this.params.get("BankCode");
        String dateofdeposit = this.params.get("DateOfDeposit");
        String timeofdeposit = this.params.get("TimeOfDeposit");
        String receipt = this.params.get("Receipt");
        String password = this.params.get("Password");
        String details = this.params.get("Details");
        String depositchannel = this.params.get("DepositChannel");

        ChangeWalletStatus change = new ChangeWalletStatus();
        change.setId(id);
        change.setLinkid(linkid);
        change.setBankname(bankname);
        change.setBankcode(bankcode);
        change.setDateofdeposit(dateofdeposit);
        change.setTimeofdeposit(timeofdeposit);
        change.setReceipt(receipt);
        change.setPassword(password);
        change.setDetails(details);
        change.setDepositchannel(depositchannel);
        change.setAuthorizedSession(sess);
        if (!change.validate()) {
          change.setState(new ObjectState("01", "Incorrect Password"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(change.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(id);
          audit.setLog(change.getState().getMessage());
          audit.setStatus(change.getState().getCode());
          audit.setUserid(Integer.valueOf(change.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(change.getAuthorizedSession().getAccount().getUserName());
          audit.setSessionid(change.getAuthorizedSession().getId());
          audit.setBrowser(change.getAuthorizedSession().getBrowser());
          audit.setBrowserversion(change.getAuthorizedSession().getBrowserversion());
          audit.setPortalversion(change.getAuthorizedSession().getPortalverion());
          audit.setOs(change.getAuthorizedSession().getOs());
          audit.setUserslevel(change.getAuthorizedSession().getAccount().getGroup().getName());
          audit.setRequest(this.params.toString());
          audit.setData(id + "|" + linkid + "|" + bankcode + "|" + bankname + "|" + dateofdeposit + "|" + timeofdeposit + "|" + depositchannel + "|" + details);
          audit.setImage(receipt);
          audit.insert();
          return new JsonView(change);
        }
        if (!change.exist()) {
          change.setState(new ObjectState("01", "Wallet Allocation does not exist"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(change.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(id);
          audit.setLog(change.getState().getMessage());
          audit.setStatus(change.getState().getCode());
          audit.setUserid(Integer.valueOf(change.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(change.getAuthorizedSession().getAccount().getUserName());
          audit.setSessionid(change.getAuthorizedSession().getId());
          audit.setBrowser(change.getAuthorizedSession().getBrowser());
          audit.setBrowserversion(change.getAuthorizedSession().getBrowserversion());
          audit.setPortalversion(change.getAuthorizedSession().getPortalverion());
          audit.setOs(change.getAuthorizedSession().getOs());
          audit.setUserslevel(change.getAuthorizedSession().getAccount().getGroup().getName());
          audit.setRequest(this.params.toString());
          audit.setData(id + "|" + linkid + "|" + bankcode + "|" + bankname + "|" + dateofdeposit + "|" + timeofdeposit + "|" + depositchannel + "|" + details);
          audit.setImage(receipt);
          audit.insert();
          return new JsonView(change);
        }
        if (change.update()) {
          change.setState(new ObjectState("00", "Successfully update Wallet Allocation Status"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(change.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(id);
          audit.setLog(change.getState().getMessage());
          audit.setStatus(change.getState().getCode());
          audit.setUserid(Integer.valueOf(change.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(change.getAuthorizedSession().getAccount().getUserName());
          audit.setSessionid(change.getAuthorizedSession().getId());
          audit.setBrowser(change.getAuthorizedSession().getBrowser());
          audit.setBrowserversion(change.getAuthorizedSession().getBrowserversion());
          audit.setPortalversion(change.getAuthorizedSession().getPortalverion());
          audit.setOs(change.getAuthorizedSession().getOs());
          audit.setUserslevel(change.getAuthorizedSession().getAccount().getGroup().getName());
          audit.setRequest(this.params.toString());
          audit.setData(id + "|" + linkid + "|" + bankcode + "|" + bankname + "|" + dateofdeposit + "|" + timeofdeposit + "|" + depositchannel + "|" + details);
          audit.setImage(receipt);

          audit.insert();
          return new JsonView(change);
        }

        change.setState(new ObjectState("99", "System is busy"));
        AuditTrail audit = new AuditTrail();
        audit.setIp(change.getAuthorizedSession().getIpAddress());
        audit.setModuleid(String.valueOf(getId()));
        audit.setEntityid(id);
        audit.setLog(change.getState().getMessage());
        audit.setStatus(change.getState().getCode());
        audit.setUserid(Integer.valueOf(change.getAuthorizedSession().getAccount().getId()));
        audit.setUsername(change.getAuthorizedSession().getAccount().getUserName());
        audit.setSessionid(change.getAuthorizedSession().getId());
        audit.setBrowser(change.getAuthorizedSession().getBrowser());
        audit.setBrowserversion(change.getAuthorizedSession().getBrowserversion());
        audit.setPortalversion(change.getAuthorizedSession().getPortalverion());
        audit.setOs(change.getAuthorizedSession().getOs());
        audit.setUserslevel(change.getAuthorizedSession().getAccount().getGroup().getName());
        audit.setRequest(this.params.toString());
        audit.setData(id + "|" + linkid + "|" + bankcode + "|" + bankname + "|" + dateofdeposit + "|" + timeofdeposit + "|" + depositchannel + "|" + details);
        audit.setImage(receipt);
        audit.insert();
        return new JsonView(change);
      }

      UISession u = new UISession(null);
      u.setState(new ObjectState("TLC-3902-01"));
      v = new SessionView(u);
    }
    catch (SessionNotFoundException e) {
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

  public String getKey()
  {
    return "CHANGEWALLETALLOCSTATUS";
  }

  public int getId()
  {
    return 1304;
  }
}