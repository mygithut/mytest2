package com.dhcc.ftp.entity;


/**
 * FtpFinacialRate entity. @author MyEclipse Persistence Tools
 */

public class FtpFinacialRate implements java.io.Serializable {

	// Fields

	private String finacialId;
	private Double finacialRate;
	private String finacialTerm;
	private Integer finacialDate;

	// Constructors

	/** default constructor */
	public FtpFinacialRate() {
	}

	/** minimal constructor */
	public FtpFinacialRate(String finacialId) {
		this.finacialId = finacialId;
	}

	/** full constructor */
	public FtpFinacialRate(String finacialId, Double finacialRate, String finacialTerm, Integer finacialDate) {
		this.finacialId = finacialId;
		this.finacialRate = finacialRate;
		this.finacialTerm = finacialTerm;
		this.finacialDate = finacialDate;
	}

	public String getFinacialId() {
		return finacialId;
	}

	public void setFinacialId(String finacialId) {
		this.finacialId = finacialId;
	}

	public Double getFinacialRate() {
		return finacialRate;
	}

	public void setFinacialRate(Double finacialRate) {
		this.finacialRate = finacialRate;
	}

	public String getFinacialTerm() {
		return finacialTerm;
	}

	public void setFinacialTerm(String finacialTerm) {
		this.finacialTerm = finacialTerm;
	}

	public Integer getFinacialDate() {
		return finacialDate;
	}

	public void setFinacialDate(Integer finacialDate) {
		this.finacialDate = finacialDate;
	}

	// Property accessors


}