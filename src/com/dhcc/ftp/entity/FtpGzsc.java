package com.dhcc.ftp.entity;

import java.util.Date;

/**
 * FtpStock entity. @author MyEclipse Persistence Tools
 */

public class FtpGzsc implements java.io.Serializable {

	private Integer gzId;
	private Double gzYield;
	private Date gzDate;

	// Constructors

	/** default constructor */
	public FtpGzsc() {
	}
	public FtpGzsc(Integer gzId) {
		this.gzId = gzId;
	}
	public void FtpGzsc(Integer gzId,Double gzYield,Date gzDate) {
		this.gzId = gzId;
		this.gzYield = gzYield;
		this.gzYield = gzYield;
	}
	public Double getGzYield() {
		return gzYield;
	}
	public void setGzYield(Double gzYield) {
		this.gzYield = gzYield;
	}
	public Date getGzDate() {
		return gzDate;
	}
	public void setGzDate(Date gzDate) {
		this.gzDate = gzDate;
	}
	public Integer getGzId() {
		return gzId;
	}
	public void setGzId(Integer gzId) {
		this.gzId = gzId;
	}

	

}