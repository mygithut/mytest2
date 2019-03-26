package com.dhcc.ftp.entity;

/**
 * Ftp1BankBillsRate entity. @author MyEclipse Persistence Tools
 */

public class Ftp1BankBillsRate implements java.io.Serializable {

	// Fields

	private String billsId;
	private String termType;
	private Double billsRate;
	private String billsDate;

	// Constructors

	/** default constructor */
	public Ftp1BankBillsRate() {
	}

	/** minimal constructor */
	public Ftp1BankBillsRate(String billsId) {
		this.billsId = billsId;
	}

	/** full constructor */
	public Ftp1BankBillsRate(String billsId, String termType, Double billsRate,
			String billsDate) {
		this.billsId = billsId;
		this.termType = termType;
		this.billsRate = billsRate;
		this.billsDate = billsDate;
	}

	// Property accessors

	public String getBillsId() {
		return this.billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public Double getBillsRate() {
		return this.billsRate;
	}

	public void setBillsRate(Double billsRate) {
		this.billsRate = billsRate;
	}

	public String getBillsDate() {
		return this.billsDate;
	}

	public void setBillsDate(String billsDate) {
		this.billsDate = billsDate;
	}

}