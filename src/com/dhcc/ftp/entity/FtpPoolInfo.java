package com.dhcc.ftp.entity;

/**
 * FtpPoolInfo entity. @author MyEclipse Persistence Tools
 */

public class FtpPoolInfo implements java.io.Serializable {

	// Fields

	private String poolId;
	private String brNo;
	private String curNo;
	private String prcMode;
	private String poolNo;
	private String poolName;
	private String poolType;
	private String contentObject;
	private String generateDate;
	private String telNo;

	// Constructors

	/** default constructor */
	public FtpPoolInfo() {
	}

	/** minimal constructor */
	public FtpPoolInfo(String poolId) {
		this.poolId = poolId;
	}

	/** full constructor */
	public FtpPoolInfo(String poolId, String brNo, String curNo,
			String prcMode, String poolNo, String poolName, String poolType,
			String contentObject, String generateDate, String telNo) {
		this.poolId = poolId;
		this.brNo = brNo;
		this.curNo = curNo;
		this.prcMode = prcMode;
		this.poolNo = poolNo;
		this.poolName = poolName;
		this.poolType = poolType;
		this.contentObject = contentObject;
		this.generateDate = generateDate;
		this.telNo = telNo;
	}

	// Property accessors

	public String getPoolId() {
		return this.poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getCurNo() {
		return this.curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getPrcMode() {
		return this.prcMode;
	}

	public void setPrcMode(String prcMode) {
		this.prcMode = prcMode;
	}

	public String getPoolNo() {
		return this.poolNo;
	}

	public void setPoolNo(String poolNo) {
		this.poolNo = poolNo;
	}

	public String getPoolType() {
		return this.poolType;
	}

	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}

	public String getContentObject() {
		return this.contentObject;
	}

	public void setContentObject(String contentObject) {
		this.contentObject = contentObject;
	}

	public String getGenerateDate() {
		return this.generateDate;
	}

	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

}