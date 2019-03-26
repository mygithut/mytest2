package com.dhcc.ftp.entity;

import java.util.Date;

/**
 * FtpStock entity. @author MyEclipse Persistence Tools
 */

public class FtpGkh implements java.io.Serializable {

	// Fields

	private Integer gkhId;
	private Double gkhYield;
	private String gkhTerm;
	private String gkhNo;
	private Date gkhDate;

	// Constructors

	/** default constructor */
	public FtpGkh() {
	}

	/** minimal constructor */
	public FtpGkh(Integer gkhId) {
		this.gkhId = gkhId;
	}

	/** full constructor 
	 * @return */
	public void FtpStock(Integer gkhId, Double gkhYield, String gkhTerm,String gkhNo, Date gkhDate) {
		this.gkhId = gkhId;
		this.gkhYield = gkhYield;
		this.gkhTerm = gkhTerm;
		this.gkhNo = gkhNo;
		this.gkhDate = gkhDate;
	}

	public Integer getGkhId() {
		return gkhId;
	}

	public void setGkhId(Integer gkhId) {
		this.gkhId = gkhId;
	}

	public Double getGkhYield() {
		return gkhYield;
	}

	public void setGkhYield(Double gkhYield) {
		this.gkhYield = gkhYield;
	}

	public String getGkhTerm() {
		return gkhTerm;
	}

	public void setGkhTerm(String gkhTerm) {
		this.gkhTerm = gkhTerm;
	}

	public String getGkhNo() {
		return gkhNo;
	}

	public void setGkhNo(String gkhNo) {
		this.gkhNo = gkhNo;
	}

	public Date getGkhDate() {
		return gkhDate;
	}

	public void setGkhDate(Date gkhDate) {
		this.gkhDate = gkhDate;
	}

	// Property accessors


}