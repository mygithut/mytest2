package com.dhcc.ftp.entity;

/**
 * Ftp1LdRatioAdjust entity. @author MyEclipse Persistence Tools
 */

public class Ftp1LdRatioAdjust implements java.io.Serializable {

	// Fields

	private String id;
	private Double minRatio;
	private Double maxRatio;
	private Double adjustValue;
	private String brNo;
	private BrMst brMst;
	

	// Constructors

	public BrMst getBrMst() {
		return brMst;
	}

	public void setBrMst(BrMst brMst) {
		this.brMst = brMst;
	}

	/** default constructor */
	public Ftp1LdRatioAdjust() {
	}

	/** minimal constructor */
	public Ftp1LdRatioAdjust(String id) {
		this.id = id;
	}

	/** full constructor */
	public Ftp1LdRatioAdjust(String id, Double minRatio, Double maxRatio,
			Double adjustValue, String brNo) {
		this.id = id;
		this.minRatio = minRatio;
		this.maxRatio = maxRatio;
		this.adjustValue = adjustValue;
		this.brNo = brNo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMinRatio() {
		return this.minRatio;
	}

	public void setMinRatio(Double minRatio) {
		this.minRatio = minRatio;
	}

	public Double getMaxRatio() {
		return this.maxRatio;
	}

	public void setMaxRatio(Double maxRatio) {
		this.maxRatio = maxRatio;
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

}