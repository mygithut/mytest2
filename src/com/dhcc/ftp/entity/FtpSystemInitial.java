package com.dhcc.ftp.entity;

/**
 * FtpSystemInitial entity. @author MyEclipse Persistence Tools
 */

public class FtpSystemInitial implements java.io.Serializable {

	// Fields

	private String setId;
	private String setResult;
	private String setDate;
	private String telNo;
	private String setValidMark;
	private Integer setNum;
	private String brNo;

	// Constructors

	/** default constructor */
	public FtpSystemInitial() {
	}

	/** minimal constructor */
	public FtpSystemInitial(String setId) {
		this.setId = setId;
	}

	/** full constructor */
	public FtpSystemInitial(String setId, String setResult, String setDate,
			String telNo, String setValidMark, Integer setNum, String brNo) {
		this.setId = setId;
		this.setResult = setResult;
		this.setDate = setDate;
		this.telNo = telNo;
		this.setValidMark = setValidMark;
		this.setNum = setNum;
		this.brNo = brNo;
	}

	// Property accessors

	public String getSetId() {
		return this.setId;
	}

	public void setSetId(String setId) {
		this.setId = setId;
	}

	public String getSetResult() {
		return this.setResult;
	}

	public void setSetResult(String setResult) {
		this.setResult = setResult;
	}

	public String getSetDate() {
		return this.setDate;
	}

	public void setSetDate(String setDate) {
		this.setDate = setDate;
	}

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getSetValidMark() {
		return this.setValidMark;
	}

	public void setSetValidMark(String setValidMark) {
		this.setValidMark = setValidMark;
	}

	public Integer getSetNum() {
		return this.setNum;
	}

	public void setSetNum(Integer setNum) {
		this.setNum = setNum;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

}