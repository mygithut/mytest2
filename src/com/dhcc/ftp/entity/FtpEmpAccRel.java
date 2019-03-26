package com.dhcc.ftp.entity;

/**
 * FtpEmpAccRel entity. @author MyEclipse Persistence Tools
 */

public class FtpEmpAccRel implements java.io.Serializable {

	// Fields

	private String id;
	private String accId;
	private String empNo;
	private String rate;
	private String relTime;
	private String relTelNo;
	private String relType;
	private String prdtNo;

	// Constructors

	/** default constructor */
	public FtpEmpAccRel() {
	}

	/** minimal constructor */
	public FtpEmpAccRel(String id) {
		this.id = id;
	}

	/** full constructor */
	public FtpEmpAccRel(String id, String accId, String empNo, String rate,
			String relTime, String relTelNo, String relType, String prdtNo) {
		this.id = id;
		this.accId = accId;
		this.empNo = empNo;
		this.rate = rate;
		this.relTime = relTime;
		this.relTelNo = relTelNo;
		this.relType = relType;
		this.prdtNo = prdtNo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccId() {
		return this.accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRelTime() {
		return this.relTime;
	}

	public void setRelTime(String relTime) {
		this.relTime = relTime;
	}

	public String getRelTelNo() {
		return this.relTelNo;
	}

	public void setRelTelNo(String relTelNo) {
		this.relTelNo = relTelNo;
	}

	public String getRelType() {
		return this.relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public String getPrdtNo() {
		return this.prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

}