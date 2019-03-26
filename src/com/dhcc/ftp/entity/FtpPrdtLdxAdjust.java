package com.dhcc.ftp.entity;

/**
 * FtpPrdtLdxAdjust entity. @author MyEclipse Persistence Tools
 */

public class FtpPrdtLdxAdjust implements java.io.Serializable {

	// Fields

	private String adjustId;
	private String termType;
	private String brNo;
	private String adjustDate;
	private Double adjustValue;

	// Constructors

	/** default constructor */
	public FtpPrdtLdxAdjust() {
	}

	/** minimal constructor */
	public FtpPrdtLdxAdjust(String adjustId) {
		this.adjustId = adjustId;
	}

	/** full constructor */
	public FtpPrdtLdxAdjust(String adjustId, String termType, String brNo,
			String adjustDate, Double adjustValue) {
		this.adjustId = adjustId;
		this.termType = termType;
		this.brNo = brNo;
		this.adjustDate = adjustDate;
		this.adjustValue = adjustValue;
	}

	// Property accessors

	public String getAdjustId() {
		return this.adjustId;
	}

	public void setAdjustId(String adjustId) {
		this.adjustId = adjustId;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getAdjustDate() {
		return this.adjustDate;
	}

	public void setAdjustDate(String adjustDate) {
		this.adjustDate = adjustDate;
	}

	public Double getAdjustValue() {
		return this.adjustValue;
	}

	public void setAdjustValue(Double adjustValue) {
		this.adjustValue = adjustValue;
	}

}