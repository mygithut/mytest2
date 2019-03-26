package com.dhcc.ftp.entity;


/**
 * FtpStock entity. @author MyEclipse Persistence Tools
 */

public class FtpStockYield implements java.io.Serializable {

	// Fields

	private String stockId;
	private Double stockYield;
	private String stockTerm;
	private Integer stockDate;

	// Constructors

	/** default constructor */
	public FtpStockYield() {
	}

	/** minimal constructor */
	public FtpStockYield(String stockId) {
		this.stockId = stockId;
	}

	/** full constructor */
	public FtpStockYield(String stockId, Double stockYield, String stockTerm, Integer stockDate) {
		this.stockId = stockId;
		this.stockYield = stockYield;
		this.stockTerm = stockTerm;
		this.stockDate = stockDate;
	}

	// Property accessors

	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Double getStockYield() {
		return this.stockYield;
	}

	public void setStockYield(Double stockYield) {
		this.stockYield = stockYield;
	}

	public String getStockTerm() {
		return this.stockTerm;
	}

	public void setStockTerm(String stockTerm) {
		this.stockTerm = stockTerm;
	}

	public Integer getStockDate() {
		return this.stockDate;
	}

	public void setStockDate(Integer stockDate) {
		this.stockDate = stockDate;
	}

}