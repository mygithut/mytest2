package com.dhcc.ftp.entity;

/**
 * Ftp1DkAdjust entity. @author MyEclipse Persistence Tools
 */

public class Ftp1DkAdjust implements java.io.Serializable {

	// Fields

	private String id;
	private Double minAmt;
	private Double maxAmt;
	private Double adjustValue;
	private String brNo;
	private String lastModifyTime;
	private String lastModifyTelno;
	private Double minPercent;
	private Double maxPercent;

	// Constructors

	/** default constructor */
	public Ftp1DkAdjust() {
	}

	/** minimal constructor */
	public Ftp1DkAdjust(String id) {
		this.id = id;
	}

	/** full constructor */
	public Ftp1DkAdjust(String id, Double minAmt, Double maxAmt,
			Double adjustValue, String brNo, String lastModifyTime,
			String lastModifyTelno, Double minPercent, Double maxPercent) {
		this.id = id;
		this.minAmt = minAmt;
		this.maxAmt = maxAmt;
		this.adjustValue = adjustValue;
		this.brNo = brNo;
		this.lastModifyTime = lastModifyTime;
		this.lastModifyTelno = lastModifyTelno;
		this.minPercent = minPercent;
		this.maxPercent = maxPercent;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMinAmt() {
		return this.minAmt;
	}

	public void setMinAmt(Double minAmt) {
		this.minAmt = minAmt;
	}

	public Double getMaxAmt() {
		return this.maxAmt;
	}

	public void setMaxAmt(Double maxAmt) {
		this.maxAmt = maxAmt;
	}

	public Double getAdjustValue() {
		return this.adjustValue;
	}

	public void setAdjustValue(Double adjustValue) {
		this.adjustValue = adjustValue;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getLastModifyTelno() {
		return this.lastModifyTelno;
	}

	public void setLastModifyTelno(String lastModifyTelno) {
		this.lastModifyTelno = lastModifyTelno;
	}

	public Double getMinPercent() {
		return minPercent;
	}

	public void setMinPercent(Double minPercent) {
		this.minPercent = minPercent;
	}

	public Double getMaxPercent() {
		return maxPercent;
	}

	public void setMaxPercent(Double maxPercent) {
		this.maxPercent = maxPercent;
	}
	
	

}