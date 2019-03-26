package com.dhcc.ftp.entity;

import com.dhcc.ftp.util.Excel;

/**
 * JrCj entity. @author MyEclipse Persistence Tools
 */

public class JrCj implements java.io.Serializable {

	// Fields

	@Excel(exportName="序号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String acId;
	@Excel(exportName="机构号",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String brNo;
	@Excel(exportName="币种",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String curNo;
	@Excel(exportName="交易对手名称",exportFieldWidth=60,exportConvertSign=0, importConvertSign=0)
	private String cusName;
	
	@Excel(exportName="交易对手编号",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String custNo;
	@Excel(exportName="产品编码",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String prdtNo;
	@Excel(exportName="产品名称",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String prdtName;
	@Excel(exportName="金额(元)",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private Double chaijieAmt;
	@Excel(exportName="到期支付金额（元）",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private Double dqzfAmt;
	@Excel(exportName="开始日",exportFieldWidth=8,exportConvertSign=0, importConvertSign=0)
	private String qxDate;
	@Excel(exportName="到期日",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String dqDate;
	@Excel(exportName="利率(年%)",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private Double rate;
	
	@Excel(exportName="业务类型",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String orgnlBusnTyp;
	private String zhzt;
	private Double interest;
	private Double leftAmt;
	@Excel(exportName="交易员姓名",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String empName;
	@Excel(exportName="机构名称",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String brName;
	@Excel(exportName="科目号",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String kmh;
	@Excel(exportName="计息天数",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private Integer jxts;
	@Excel(exportName="余额(元)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double  bal;

	public Double getBal() {
		return bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	public String getKmh() {
		return kmh;
	}

	public void setKmh(String kmh) {
		this.kmh = kmh;
	}


	public Integer getJxts() {
		return jxts;
	}

	public void setJxts(Integer jxts) {
		this.jxts = jxts;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	private String businessNo;
	@Excel(exportName="交易员编号",exportFieldWidth=20,exportConvertSign=0, importConvertSign=0)
	private String khjl;
	// Constructors

	/** default constructor */
	public JrCj() {
	}

	/** minimal constructor */
	public JrCj(String acId) {
		this.acId = acId;
	}

	/** full constructor */
	public JrCj(String acId, String brNo, String curNo, String cusName,
			String khjl, String custNo, String businessNo, String prdtNo,
			String prdtName, Double chaijieAmt, Double dqzfAmt, String qxDate,
			String dqDate, Double rate, 
			String orgnlBusnTyp, String zhzt, Double interest, Double leftAmt) {
		this.acId = acId;
		this.brNo = brNo;
		this.curNo = curNo;
		this.cusName = cusName;
		this.khjl = khjl;
		this.custNo = custNo;
		this.businessNo = businessNo;
		this.prdtNo = prdtNo;
		this.prdtName = prdtName;
		this.chaijieAmt = chaijieAmt;
		this.dqzfAmt = dqzfAmt;
		this.qxDate = qxDate;
		this.dqDate = dqDate;
		this.rate = rate;
		
		this.orgnlBusnTyp = orgnlBusnTyp;
		this.zhzt = zhzt;
		this.interest = interest;
		this.leftAmt = leftAmt;
		
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

	public String getKhjl() {
		return this.khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
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

	public Double getChaijieAmt() {
		return this.chaijieAmt;
	}

	public void setChaijieAmt(Double chaijieAmt) {
		this.chaijieAmt = chaijieAmt;
	}

	public Double getDqzfAmt() {
		return this.dqzfAmt;
	}

	public void setDqzfAmt(Double dqzfAmt) {
		this.dqzfAmt = dqzfAmt;
	}

	public String getQxDate() {
		return this.qxDate;
	}

	public void setQxDate(String qxDate) {
		this.qxDate = qxDate;
	}

	public String getDqDate() {
		return this.dqDate;
	}

	public void setDqDate(String dqDate) {
		this.dqDate = dqDate;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}



	public String getOrgnlBusnTyp() {
		return this.orgnlBusnTyp;
	}

	public void setOrgnlBusnTyp(String orgnlBusnTyp) {
		this.orgnlBusnTyp = orgnlBusnTyp;
	}

	public String getZhzt() {
		return this.zhzt;
	}

	public void setZhzt(String zhzt) {
		this.zhzt = zhzt;
	}

	public Double getInterest() {
		return this.interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getLeftAmt() {
		return this.leftAmt;
	}

	public void setLeftAmt(Double leftAmt) {
		this.leftAmt = leftAmt;
	}






}