package com.dhcc.ftp.entity;

/**
 * FtpYieldCurve entity. @author MyEclipse Persistence Tools
 */

public class FtpYieldCurve implements java.io.Serializable {

	// Fields

	private String curveId;
	private String curveNo;
	private Double a3;
	private Double b2;
	private Double c1;
	private Double d0;
	private Double XMin;
	private Double XMax;
	private String curveDate;
	private Integer sectionNum;
	private String curveAssetsType;
	private String curveMarketType;
	private String curveName;
	private String brNo;

	// Constructors

	/** default constructor */
	public FtpYieldCurve() {
	}

	/** minimal constructor */
	public FtpYieldCurve(String curveId) {
		this.curveId = curveId;
	}

	/** full constructor */
	public FtpYieldCurve(String curveId, String curveNo, Double a3, Double b2,
			Double c1, Double d0, Double XMin, Double XMax, String curveDate,
			Integer sectionNum, String curveAssetsType, String curveMarketType,
			String curveName, String brNo) {
		this.curveId = curveId;
		this.curveNo = curveNo;
		this.a3 = a3;
		this.b2 = b2;
		this.c1 = c1;
		this.d0 = d0;
		this.XMin = XMin;
		this.XMax = XMax;
		this.curveDate = curveDate;
		this.sectionNum = sectionNum;
		this.curveAssetsType = curveAssetsType;
		this.curveMarketType = curveMarketType;
		this.curveName = curveName;
		this.brNo = brNo;
	}

	// Property accessors

	public String getCurveId() {
		return this.curveId;
	}

	public void setCurveId(String curveId) {
		this.curveId = curveId;
	}

	public String getCurveNo() {
		return this.curveNo;
	}

	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}

	public Double getA3() {
		return this.a3;
	}

	public void setA3(Double a3) {
		this.a3 = a3;
	}

	public Double getB2() {
		return this.b2;
	}

	public void setB2(Double b2) {
		this.b2 = b2;
	}

	public Double getC1() {
		return this.c1;
	}

	public void setC1(Double c1) {
		this.c1 = c1;
	}

	public Double getD0() {
		return this.d0;
	}

	public void setD0(Double d0) {
		this.d0 = d0;
	}

	public Double getXMin() {
		return this.XMin;
	}

	public void setXMin(Double XMin) {
		this.XMin = XMin;
	}

	public Double getXMax() {
		return this.XMax;
	}

	public void setXMax(Double XMax) {
		this.XMax = XMax;
	}

	public String getCurveDate() {
		return this.curveDate;
	}

	public void setCurveDate(String curveDate) {
		this.curveDate = curveDate;
	}

	public Integer getSectionNum() {
		return this.sectionNum;
	}

	public void setSectionNum(Integer sectionNum) {
		this.sectionNum = sectionNum;
	}

	public String getCurveAssetsType() {
		return this.curveAssetsType;
	}

	public void setCurveAssetsType(String curveAssetsType) {
		this.curveAssetsType = curveAssetsType;
	}

	public String getCurveMarketType() {
		return this.curveMarketType;
	}

	public void setCurveMarketType(String curveMarketType) {
		this.curveMarketType = curveMarketType;
	}

	public String getCurveName() {
		return this.curveName;
	}

	public void setCurveName(String curveName) {
		this.curveName = curveName;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

}