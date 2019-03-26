package com.dhcc.ftp.entity;

/**
 * FtpAverateBasic entity. @author MyEclipse Persistence Tools
 */

public class FtpAverateBasic implements java.io.Serializable {

	// Fields

	private Integer averateId;
	private String prdtNo;
	private String brNo;
	private Integer cycDate;
	private Double rate;
	private Double bal;
	private String termType;

	// Constructors

	/** default constructor */
	public FtpAverateBasic() {
	}

	/** minimal constructor */
	public FtpAverateBasic(Integer averateId) {
		this.averateId = averateId;
	}

	/** full constructor */
	public FtpAverateBasic(Integer averateId, String prdtNo, String brNo,
			Integer cycDate, Double rate, Double bal, String termType) {
		this.averateId = averateId;
		this.prdtNo = prdtNo;
		this.brNo = brNo;
		this.cycDate = cycDate;
		this.rate = rate;
		this.bal = bal;
		this.termType = termType;
	}

	// Property accessors

	public Integer getAverateId() {
		return this.averateId;
	}

	public void setAverateId(Integer averateId) {
		this.averateId = averateId;
	}

	public String getPrdtNo() {
		return this.prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public Integer getCycDate() {
		return this.cycDate;
	}

	public void setCycDate(Integer cycDate) {
		this.cycDate = cycDate;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getBal() {
		return this.bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

}