package com.dhcc.ftp.entity;

/**
 * Ftp1OfRateSpread entity. @author MyEclipse Persistence Tools
 */

public class Ftp1OfRateSpread implements java.io.Serializable {

	// Fields

	private String spreadId;
	private String termType;
	private Double spreadRate;
	private String spreadDate;

	// Constructors

	/** default constructor */
	public Ftp1OfRateSpread() {
	}

	/** minimal constructor */
	public Ftp1OfRateSpread(String spreadId) {
		this.spreadId = spreadId;
	}

	/** full constructor */
	public Ftp1OfRateSpread(String spreadId, String termType,
			Double spreadRate, String spreadDate) {
		this.spreadId = spreadId;
		this.termType = termType;
		this.spreadRate = spreadRate;
		this.spreadDate = spreadDate;
	}

	// Property accessors

	public String getSpreadId() {
		return this.spreadId;
	}

	public void setSpreadId(String spreadId) {
		this.spreadId = spreadId;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public Double getSpreadRate() {
		return this.spreadRate;
	}

	public void setSpreadRate(Double spreadRate) {
		this.spreadRate = spreadRate;
	}

	public String getSpreadDate() {
		return this.spreadDate;
	}

	public void setSpreadDate(String spreadDate) {
		this.spreadDate = spreadDate;
	}

}