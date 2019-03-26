package com.dhcc.ftp.entity;

import java.util.Date;

/**
 * FtpCreditRating entity. @author MyEclipse Persistence Tools
 */

public class FtpCreditRating implements java.io.Serializable {

	// Fields

	private String ratingId;
	private String ratingNo;
	private String creditRating;
	private Double pd;
	private Double lgd;
	private String creditType;
	private String ratingDate;

	// Constructors

	/** default constructor */
	public FtpCreditRating() {
	}

	/** minimal constructor */
	public FtpCreditRating(String ratingId) {
		this.ratingId = ratingId;
	}

	/** full constructor */
	public FtpCreditRating(String ratingId, String ratingNo, String creditRating, Double pd,
			Double lgd, String creditType, String ratingDate) {
		this.ratingId = ratingId;
		this.ratingNo = ratingNo;
		this.creditRating = creditRating;
		this.pd = pd;
		this.lgd = lgd;
		this.creditType = creditType;
		this.ratingDate = ratingDate;
	}

	// Property accessors

	public String getRatingId() {
		return this.ratingId;
	}

	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	public String getCreditRating() {
		return this.creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public Double getPd() {
		return this.pd;
	}

	public void setPd(Double pd) {
		this.pd = pd;
	}

	public Double getLgd() {
		return this.lgd;
	}

	public void setLgd(Double lgd) {
		this.lgd = lgd;
	}

	public String getCreditType() {
		return this.creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getRatingDate() {
		return this.ratingDate;
	}

	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}

	public String getRatingNo() {
		return ratingNo;
	}

	public void setRatingNo(String ratingNo) {
		this.ratingNo = ratingNo;
	}
	
}