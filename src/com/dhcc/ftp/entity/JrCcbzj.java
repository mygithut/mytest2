package com.dhcc.ftp.entity;

import com.dhcc.ftp.util.Excel;

/**
 * JrCcbzj entity. @author MyEclipse Persistence Tools
 */

public class JrCcbzj implements java.io.Serializable {

	// Fields
	@Excel(exportName="序号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String acId;
	@Excel(exportName="机构号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brNo;
	@Excel(exportName="币种",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String curNo;
	@Excel(exportName="交易对手名称",exportFieldWidth=60,exportConvertSign=0, importConvertSign=0)
	private String cusName;
	@Excel(exportName="交易对手编号",exportFieldWidth=30,exportConvertSign=0, importConvertSign=0)
	private String custNo;
	@Excel(exportName="产品编码",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String prdtNo;
	@Excel(exportName="产品名称",exportFieldWidth=60,exportConvertSign=0, importConvertSign=0)
	private String prdtName;
	@Excel(exportName="金额(元)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double amt;
	@Excel(exportName="利率(年%)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double rate;
	@Excel(exportName="开始日",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String startDate;
	@Excel(exportName="到期日",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String endDate;
	@Excel(exportName="计息天数",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Integer days;
	@Excel(exportName="交易员编号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String operator;
	private String businessNo;
	@Excel(exportName="科目号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String kmh;
	@Excel(exportName="机构名称",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brName;
	@Excel(exportName="交易员姓名",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String empName;
	@Excel(exportName="余额(元)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double  bal;

	public Double getBal() {
		return bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/** default constructor */
	public JrCcbzj() {
	}

	/** minimal constructor */
	public JrCcbzj(String acId) {
		this.acId = acId;
	}


	// Property accessors

	public String getAcId() {
		return this.acId.trim();
	}

	public void setAcId(String acId) {
		this.acId = acId;
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

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getPrdtNo() {
		return this.prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getPrdtName() {
		return this.prdtName;
	}

	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}

	public Double getAmt() {
		return this.amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getKmh() {
		return this.kmh;
	}

	public void setKmh(String kmh) {
		this.kmh = kmh;
	}

}