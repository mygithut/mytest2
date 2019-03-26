package com.dhcc.ftp.entity;

/**
 * FtpProductMethodRel entity. @author MyEclipse Persistence Tools
 */

public class FtpProductMethodRel implements java.io.Serializable {

	// Fields

	private String productNo;
	private String curNo;
	private String customerType;
	private String termType;
	private String rateType;
	private String amtType;
	private String businessName;
	private String businessNo;
	private String productName;
	private String methodName;
	private String methodNo;
	private Integer assignTerm;
	private Double adjustRate;
	private String curveNo;
	private String methodTypeNo;
	private String availableMethod;
	private String availableMethodNo;
	private Double appointRate;
	private Double appointDeltaRate;
	private String brNo;
	private Double lrAdjustRate;
	private Double epsAdjustRate;
	private String isTz;
	// Constructors

	public Double getLrAdjustRate() {
		return lrAdjustRate;
	}

	public void setLrAdjustRate(Double lrAdjustRate) {
		this.lrAdjustRate = lrAdjustRate;
	}

	public Double getEpsAdjustRate() {
		return epsAdjustRate;
	}

	public void setEpsAdjustRate(Double epsAdjustRate) {
		this.epsAdjustRate = epsAdjustRate;
	}

	/** default constructor */
	public FtpProductMethodRel() {
	}

	/** minimal constructor */
	public FtpProductMethodRel(String productNo) {
		this.productNo = productNo;
	}

	/** full constructor */
	public FtpProductMethodRel(String productNo, String curNo,
			String customerType, String termType, String rateType,
			String amtType, String businessName, String businessNo,
			String productName, String methodName, String methodNo,
			Integer assignTerm, Double adjustRate, String curveNo,
			String methodTypeNo, String availableMethod,
			String availableMethodNo, Double appointRate,
			Double appointDeltaRate, String brNo) {
		this.productNo = productNo;
		this.curNo = curNo;
		this.customerType = customerType;
		this.termType = termType;
		this.rateType = rateType;
		this.amtType = amtType;
		this.businessName = businessName;
		this.businessNo = businessNo;
		this.productName = productName;
		this.methodName = methodName;
		this.methodNo = methodNo;
		this.assignTerm = assignTerm;
		this.adjustRate = adjustRate;
		this.curveNo = curveNo;
		this.methodTypeNo = methodTypeNo;
		this.availableMethod = availableMethod;
		this.availableMethodNo = availableMethodNo;
		this.appointRate = appointRate;
		this.appointDeltaRate = appointDeltaRate;
		this.brNo = brNo;
	}

	// Property accessors

	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getCurNo() {
		return this.curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getRateType() {
		return this.rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getAmtType() {
		return this.amtType;
	}

	public void setAmtType(String amtType) {
		this.amtType = amtType;
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

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodNo() {
		return this.methodNo;
	}

	public void setMethodNo(String methodNo) {
		this.methodNo = methodNo;
	}

	public Integer getAssignTerm() {
		return this.assignTerm;
	}

	public void setAssignTerm(Integer assignTerm) {
		this.assignTerm = assignTerm;
	}

	public Double getAdjustRate() {
		return this.adjustRate;
	}

	public void setAdjustRate(Double adjustRate) {
		this.adjustRate = adjustRate;
	}

	public String getCurveNo() {
		return this.curveNo;
	}

	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}

	public String getMethodTypeNo() {
		return this.methodTypeNo;
	}

	public void setMethodTypeNo(String methodTypeNo) {
		this.methodTypeNo = methodTypeNo;
	}

	public String getAvailableMethod() {
		return this.availableMethod;
	}

	public void setAvailableMethod(String availableMethod) {
		this.availableMethod = availableMethod;
	}

	public String getAvailableMethodNo() {
		return this.availableMethodNo;
	}

	public void setAvailableMethodNo(String availableMethodNo) {
		this.availableMethodNo = availableMethodNo;
	}

	public Double getAppointRate() {
		return this.appointRate;
	}

	public void setAppointRate(Double appointRate) {
		this.appointRate = appointRate;
	}

	public Double getAppointDeltaRate() {
		return this.appointDeltaRate;
	}

	public void setAppointDeltaRate(Double appointDeltaRate) {
		this.appointDeltaRate = appointDeltaRate;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getIsTz() {
		return isTz;
	}

	public void setIsTz(String isTz) {
		this.isTz = isTz;
	}

	
    
}