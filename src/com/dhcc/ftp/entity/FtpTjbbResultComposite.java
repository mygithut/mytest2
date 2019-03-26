package com.dhcc.ftp.entity;


public class FtpTjbbResultComposite implements java.io.Serializable {

	private String ID;
	private String acId;
	private String cumNo;
	private String cycDate;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getAcId() {
		return acId;
	}
	public void setAcId(String acId) {
		this.acId = acId;
	}
	public String getCumNo() {
		return cumNo;
	}
	public void setCumNo(String cumNo) {
		this.cumNo = cumNo;
	}
	public String getCycDate() {
		return cycDate;
	}
	public void setCycDate(String cycDate) {
		this.cycDate = cycDate;
	}
}