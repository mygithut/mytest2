package com.dhcc.ftp.entity;

/**
 * FtpInterestMarginDivide entity. @author MyEclipse Persistence Tools
 */

public class FtpInterestMarginDivide implements java.io.Serializable {

	// Fields

	private String id;
	private String termType;
	private String assetsType;
	private Double rate;
	private String lastModifyTime;
	private String lastModifyTelNo;

	// Constructors

	/** default constructor */
	public FtpInterestMarginDivide() {
	}

	/** minimal constructor */
	public FtpInterestMarginDivide(String id) {
		this.id = id;
	}

	/** full constructor */
	public FtpInterestMarginDivide(String id, String termType,
			String assetsType, Double rate, String lastModifyTime,
			String lastModifyTelNo) {
		this.id = id;
		this.termType = termType;
		this.assetsType = assetsType;
		this.rate = rate;
		this.lastModifyTime = lastModifyTime;
		this.lastModifyTelNo = lastModifyTelNo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getAssetsType() {
		return this.assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getLastModifyTelNo() {
		return this.lastModifyTelNo;
	}

	public void setLastModifyTelNo(String lastModifyTelNo) {
		this.lastModifyTelNo = lastModifyTelNo;
	}

}