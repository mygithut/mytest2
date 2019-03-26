package com.dhcc.ftp.entity;


/**
 * FtpPublicRate entity. @author MyEclipse Persistence Tools
 */

public class FtpPublicRate implements java.io.Serializable {

	private String rateId;
	private String rateNo;
	private Double rate;
	private String adDate;
	private Double floatPercent;
	private String czh;
	private Integer cq;
	private Double floatPercentDg;

	// Constructors

	/** default constructor */
	public FtpPublicRate() {
	}
	public FtpPublicRate(String rateId) {
		this.rateId = rateId;
	}
	public void FtpGzsc(String rateId,String rateNo,Double rate,String adDate,
			Double floatPercent, String czh, Integer cq,  Double floatPercentDg) {
		this.rateId = rateId;
		this.rateNo = rateNo;
		this.rate = rate;
		this.adDate = adDate;
		this.floatPercent = floatPercent;
		this.czh = czh;
		this.cq = cq;
		this.floatPercentDg = floatPercentDg;
	}
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	public String getRateNo() {
		return rateNo;
	}
	public void setRateNo(String rateNo) {
		this.rateNo = rateNo;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getAdDate() {
		return adDate;
	}
	public void setAdDate(String adDate) {
		this.adDate = adDate;
	}
	public Double getFloatPercent() {
		return floatPercent;
	}
	public void setFloatPercent(Double floatPercent) {
		this.floatPercent = floatPercent;
	}
	public String getCzh() {
		return czh;
	}
	public void setCzh(String czh) {
		this.czh = czh;
	}
	public Integer getCq() {
		return cq;
	}
	public void setCq(Integer cq) {
		this.cq = cq;
	}
	public Double getFloatPercentDg() {
		return floatPercentDg;
	}
	public void setFloatPercentDg(Double floatPercentDg) {
		this.floatPercentDg = floatPercentDg;
	}

}