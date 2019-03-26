package com.dhcc.ftp.entity;

/**
 * FtpPrdtPricePublishMain entity. @author MyEclipse Persistence Tools
 */

public class FtpPrdtPricePublishMain implements java.io.Serializable {

	// Fields

	private String publishId;
	private String publishTime;
	private Integer publishNum;
	private TelMst telMst;

	// Constructors

	/** default constructor */
	public FtpPrdtPricePublishMain() {
	}

	/** minimal constructor */
	public FtpPrdtPricePublishMain(String publishId) {
		this.publishId = publishId;
	}

	/** full constructor */
	public FtpPrdtPricePublishMain(String publishId, String publishTime,
			Integer publishNum, TelMst telMst) {
		this.publishId = publishId;
		this.publishTime = publishTime;
		this.publishNum = publishNum;
		this.telMst = telMst;
	}

	// Property accessors

	public String getPublishId() {
		return this.publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public String getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getPublishNum() {
		return this.publishNum;
	}

	public void setPublishNum(Integer publishNum) {
		this.publishNum = publishNum;
	}

	public TelMst getTelMst() {
		return telMst;
	}

	public void setTelMst(TelMst telMst) {
		this.telMst = telMst;
	}


}