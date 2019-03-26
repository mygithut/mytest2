package com.dhcc.ftp.entity;

import java.util.Date;

/**
 * FtpStock entity. @author MyEclipse Persistence Tools
 */

public class FtpZqhg implements java.io.Serializable {

	// Fields
	private Integer zqhgId;
	private Double zqhgYield;
	private Date zqhgDate;

	// Constructors

	/** default constructor */
	public FtpZqhg() {
	}
	public FtpZqhg(Integer zqhgId) {
		this.zqhgId = zqhgId;
	}
	public void FtpZqhg(Integer zqhgId,Double zqhgYield,Date zqhgDate) {
		this.zqhgId = zqhgId;
		this.zqhgYield = zqhgYield;
		this.zqhgDate = zqhgDate;
	}
	public Double getZqhgYield() {
		return zqhgYield;
	}
	public void setZqhgYield(Double zqhgYield) {
		this.zqhgYield = zqhgYield;
	}
	public Date getZqhgDate() {
		return zqhgDate;
	}
	public void setZqhgDate(Date zqhgDate) {
		this.zqhgDate = zqhgDate;
	}
	public Integer getZqhgId() {
		return zqhgId;
	}
	public void setZqhgId(Integer zqhgId) {
		this.zqhgId = zqhgId;
	}

	
}