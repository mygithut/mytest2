package com.dhcc.ftp.entity;

import java.math.BigDecimal;

/**
 * Ftp1CommonYield entity. @author MyEclipse Persistence Tools
 */

public class Ftp1CommonYield implements java.io.Serializable {

	// Fields

	private String commonId;
	private String termType;
	private Double commonRate;
	private String commonDate;

	// Constructors

	/** default constructor */
	public Ftp1CommonYield() {
	}

	/** minimal constructor */
	public Ftp1CommonYield(String commonId) {
		this.commonId = commonId;
	}

	/** full constructor */
	public Ftp1CommonYield(String commonId, String termType,
			Double commonRate, String commonDate) {
		this.commonId = commonId;
		this.termType = termType;
		this.commonRate = commonRate;
		this.commonDate = commonDate;
	}

	// Property accessors

	public String getCommonId() {
		return this.commonId;
	}

	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public Double getCommonRate() {
		return this.commonRate;
	}

	public void setCommonRate(Double commonRate) {
		this.commonRate = commonRate;
	}

	public String getCommonDate() {
		return this.commonDate;
	}

	public void setCommonDate(String commonDate) {
		this.commonDate = commonDate;
	}

}