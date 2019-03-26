package com.dhcc.ftp.entity;




public class YlfxReport  implements java.io.Serializable {

	private String brNo;
	private String brName;
	private String manageLvl;
	private String businessNo;//业务编号
	private String businessName;//业务名称
	private String prdtNo;
	private String prdtName;
	private Double zcpjqx;//资产平均期限
	private Double fzpjqx;//负债平均期限
    private Double zcye;//资产余额
    private Double lxsr;//利息收入
    private Double sxl;//生息率
    private Double zczyjg;//资产转移价格
    private Double zcjlc;//资产净利差
    private Double zczyzc;//资产转移支出
    private Double zcjsr;//资产净收入
    private Double fzye;//负债余额
    private Double lxzc;//利息支出
    private Double fxl;//付息率
    private Double fzzyjg;//负债转移价格
    private Double fzjlc;//负债净利差
    private Double fzzysr;//负债转移收入
    private Double fzjsr;//负债净收入
    private Double jsr;//净收入
	 
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public Double getZcye() {
		return zcye;
	}
	public void setZcye(Double zcye) {
		this.zcye = zcye;
	}
	public Double getLxsr() {
		return lxsr;
	}
	public void setLxsr(Double lxsr) {
		this.lxsr = lxsr;
	}
	public Double getSxl() {
		return sxl;
	}
	public void setSxl(Double sxl) {
		this.sxl = sxl;
	}
	public Double getZczyjg() {
		return zczyjg;
	}
	public void setZczyjg(Double zczyjg) {
		this.zczyjg = zczyjg;
	}
	public Double getZcjlc() {
		return zcjlc;
	}
	public void setZcjlc(Double zcjlc) {
		this.zcjlc = zcjlc;
	}
	public Double getZczyzc() {
		return zczyzc;
	}
	public void setZczyzc(Double zczyzc) {
		this.zczyzc = zczyzc;
	}
	public Double getZcjsr() {
		return zcjsr;
	}
	public void setZcjsr(Double zcjsr) {
		this.zcjsr = zcjsr;
	}
	public Double getFzye() {
		return fzye;
	}
	public void setFzye(Double fzye) {
		this.fzye = fzye;
	}
	public Double getLxzc() {
		return lxzc;
	}
	public void setLxzc(Double lxzc) {
		this.lxzc = lxzc;
	}
	public Double getFxl() {
		return fxl;
	}
	public void setFxl(Double fxl) {
		this.fxl = fxl;
	}
	public Double getFzzyjg() {
		return fzzyjg;
	}
	public void setFzzyjg(Double fzzyjg) {
		this.fzzyjg = fzzyjg;
	}
	public Double getFzjlc() {
		return fzjlc;
	}
	public void setFzjlc(Double fzjlc) {
		this.fzjlc = fzjlc;
	}
	public Double getFzzysr() {
		return fzzysr;
	}
	public void setFzzysr(Double fzzysr) {
		this.fzzysr = fzzysr;
	}
	public Double getFzjsr() {
		return fzjsr;
	}
	public void setFzjsr(Double fzjsr) {
		this.fzjsr = fzjsr;
	}
	public Double getJsr() {
		return jsr;
	}
	public void setJsr(Double jsr) {
		this.jsr = jsr;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getManageLvl() {
		return manageLvl;
	}
	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPrdtNo() {
		return prdtNo;
	}
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	public String getPrdtName() {
		return prdtName;
	}
	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}
	public Double getZcpjqx() {
		return zcpjqx;
	}
	public void setZcpjqx(Double zcpjqx) {
		this.zcpjqx = zcpjqx;
	}
	public Double getFzpjqx() {
		return fzpjqx;
	}
	public void setFzpjqx(Double fzpjqx) {
		this.fzpjqx = fzpjqx;
	}

}