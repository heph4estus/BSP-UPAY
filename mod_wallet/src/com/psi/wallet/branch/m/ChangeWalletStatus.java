package com.psi.wallet.branch.m;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class ChangeWalletStatus extends Model
{
  protected String id;
  protected String linkid;
  protected String reference;
  protected String dateofdeposit;
  protected String timeofdeposit;
  protected String receipt;
  protected String bankname;
  protected String bankcode;
  protected String password;
  protected String details;
  protected String depositchannel;

  public boolean update()
  {
    return SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET DEPOSITCHANNEL=?, DESCRIPTION=?, STATUS='PAID',BANKNAME=?,BANKBRANCHCODE=?,DATEOFDEPOSIT = TO_DATE(?,'YYYY-MM-DD'),TIMEOFDEPOSIT=?,REFERENCEIMAGE=? WHERE TYPE='DIRECALLOC' AND ID = ?", new Object[] { this.depositchannel, this.details, this.bankname, this.bankcode, this.dateofdeposit, this.timeofdeposit, this.receipt, this.id }) > 0;
  }

  public boolean exist() {
    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE TYPE='DIRECALLOC' AND ID = ?", new Object[] { this.id }).size() > 0;
  }
  public boolean validate() {
    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)", new Object[] { this.linkid, this.password, SystemInfo.getDb().getCrypt() }).size() > 0;
  }
  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getReference() {
    return this.reference;
  }
  public void setReference(String reference) {
    this.reference = reference;
  }
  public String getLinkid() {
    return this.linkid;
  }
  public void setLinkid(String linkid) {
    this.linkid = linkid;
  }

  public String getDateofdeposit() {
    return this.dateofdeposit;
  }

  public void setDateofdeposit(String dateofdeposit) {
    this.dateofdeposit = dateofdeposit;
  }

  public String getTimeofdeposit() {
    return this.timeofdeposit;
  }

  public void setTimeofdeposit(String timeofdeposit) {
    this.timeofdeposit = timeofdeposit;
  }

  public String getReceipt() {
    return this.receipt;
  }

  public void setReceipt(String receipt) {
    this.receipt = receipt;
  }

  public String getBankname() {
    return this.bankname;
  }

  public void setBankname(String bankname) {
    this.bankname = bankname;
  }

  public String getBankcode() {
    return this.bankcode;
  }

  public void setBankcode(String bankcode) {
    this.bankcode = bankcode;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDetails() {
    return this.details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getDepositchannel() {
    return this.depositchannel;
  }

  public void setDepositchannel(String depositchannel) {
    this.depositchannel = depositchannel;
  }
}