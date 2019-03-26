package com.dhcc.ftp.entity;

/**
 * FtpBusinessStaticDivide entity. @author MyEclipse Persistence Tools
 */

public class FtpBusinessStaticDivide implements java.io.Serializable {

	// Fields

	private String id;
	private String staticName;
	private String staticNo;
	private String businessName;
	private String businessNo;
	private String productCtgName;
	private String productCtgNo;
	private String productName;
	private String productNo;
	private String brNo;

	// Constructors

	/** default constructor */
	public FtpBusinessStaticDivide() {
	}

	/** minimal constructor */
	public FtpBusinessStaticDivide(String id) {
		this.id = id;
	}

	/** full constructor */
	public FtpBusinessStaticDivide(String id, String staticName, String staticNo,
			String businessName, String businessNo, String productCtgName, String productCtgNo,
			String productName, String productNo, String brNo) {
		this.id = id;
		this.staticName = staticName;
		this.staticNo = staticNo;
		this.businessName = businessName;
		this.businessNo = businessNo;
		this.productCtgName = productCtgName;
		this.productCtgNo = productCtgNo;
		this.productName = productName;
		this.productNo = productNo;
		this.brNo = brNo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getProductCtgName() {
		return this.productCtgName;
	}

	public void setProductCtgName(String productCtgName) {
		this.productCtgName = productCtgName;
	}

	public String getProductCtgNo() {
		return this.productCtgNo;
	}

	public void setProductCtgNo(String productCtgNo) {
		this.productCtgNo = productCtgNo;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getStaticName() {
		return staticName;
	}

	public void setStaticName(String staticName) {
		this.staticName = staticName;
	}

	public String getStaticNo() {
		return staticNo;
	}

	public void setStaticNo(String staticNo) {
		this.staticNo = staticNo;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

}