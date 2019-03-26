package com.dhcc.ftp.entity;

import java.util.Date;

/**
 * FtpStock entity. @author MyEclipse Persistence Tools
 */

public class FtpShibor implements java.io.Serializable {

	// Fields

	private String shiborId;
	private Double shiborRate;
	private String shiborTerm;
	private Integer shiborDate;

	// Constructors

	/** default constructor */
	public FtpShibor() {
	}

	/** minimal constructor */
	public FtpShibor(String shiborId) {
		this.shiborId = shiborId;
	}

	/** full constructor 
	 * @return */
	public void FtpShibor(String shiborId,String shiborTerm,Double shiborRate,Integer shiborDate) {
		this.shiborId = shiborId;
		this.shiborTerm = shiborTerm;
		this.shiborRate = shiborRate;
		this.shiborDate = shiborDate;
	}

	public String getShiborId() {
		return shiborId;
	}

	public void setShiborId(String shiborId) {
		this.shiborId = shiborId;
	}

	public Double getShiborRate() {
		return shiborRate;
	}

	public void setShiborRate(Double shiborRate) {
		this.shiborRate = shiborRate;
	}

	public String getShiborTerm() {
		return shiborTerm;
	}

	public void setShiborTerm(String shiborTerm) {
		this.shiborTerm = shiborTerm;
	}

	public Integer getShiborDate() {
		return shiborDate;
	}

	public void setShiborDate(Integer shiborDate) {
		this.shiborDate = shiborDate;
	}

	
}