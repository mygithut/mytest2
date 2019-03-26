 package com.dhcc.ftp.entity;


public class FtpTjbbResult implements java.io.Serializable {

	private String brNo;
	private String prdtNo;
	private String businessNo;
	private Double averateBalM;
	private Double averateBalQ;
	private Double averateBalY;
	private Double bal;
	private Double rate;
	private Double ftpPrice;//定价结果
	private String isZq;//是否展期贷款
	private String kmh;//科目号
	private String custNo;//客户编号
	private FtpTjbbResultComposite ftpTjbbResultComposite = new FtpTjbbResultComposite();
	
	public FtpTjbbResultComposite getFtpTjbbResultComposite() {
		return ftpTjbbResultComposite;
	}
	public void setFtpTjbbResultComposite(
			FtpTjbbResultComposite ftpTjbbResultComposite) {
		this.ftpTjbbResultComposite = ftpTjbbResultComposite;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getPrdtNo() {
		return prdtNo;
	}
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public Double getAverateBalM() {
		return averateBalM;
	}
	public void setAverateBalM(Double averateBalM) {
		this.averateBalM = averateBalM;
	}
	public Double getAverateBalQ() {
		return averateBalQ;
	}
	public void setAverateBalQ(Double averateBalQ) {
		this.averateBalQ = averateBalQ;
	}
	public Double getAverateBalY() {
		return averateBalY;
	}
	public void setAverateBalY(Double averateBalY) {
		this.averateBalY = averateBalY;
	}
	public Double getBal() {
		return bal;
	}
	public void setBal(Double bal) {
		this.bal = bal;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getFtpPrice() {
		return ftpPrice;
	}
	public void setFtpPrice(Double ftpPrice) {
		this.ftpPrice = ftpPrice;
	}
	public String getIsZq() {
		return isZq;
	}
	public void setIsZq(String isZq) {
		this.isZq = isZq;
	}
	public String getKmh() {
		return kmh;
	}
	public void setKmh(String kmh) {
		this.kmh = kmh;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	
}