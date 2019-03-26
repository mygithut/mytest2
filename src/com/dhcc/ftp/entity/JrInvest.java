package com.dhcc.ftp.entity;

import com.dhcc.ftp.util.Excel;

/**
 * JrInvest entity. @author MyEclipse Persistence Tools
 */

public class JrInvest implements java.io.Serializable {

	// Fields
	@Excel(exportName="���",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String acId;
	@Excel(exportName="������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brNo;
	private String curNo;
	@Excel(exportName="����Ա",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String khjl;
	@Excel(exportName="���׶�������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String cusName;
	@Excel(exportName="���׶��ֱ��",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String custNo;
	@Excel(exportName="��Ʒ���",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String prdtNo;
	@Excel(exportName="��Ʒ����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String prdtName;
	@Excel(exportName="ҵ������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String orgnlbusntyp;
	@Excel(exportName="Ʊ����(Ԫ)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double holdPrincipal;
	@Excel(exportName="Ʊ������(%)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double billYield;
	@Excel(exportName="��������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String startDate;
	@Excel(exportName="������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String endDate;
	private String businessNo;
	@Excel(exportName="�ɽ����(Ԫ)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double dealAmt;
	@Excel(exportName="����Ա����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String empName;
	@Excel(exportName="��������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brName;
	@Excel(exportName="��Ŀ��",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String kmh;
	@Excel(exportName="��Ϣ����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Integer jxts;
	@Excel(exportName="���(Ԫ)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double  bal;

	public Double getBal() {
		return bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}
	
	
	// Constructors

	public Integer getJxts() {
		return jxts;
	}

	public void setJxts(Integer jxts) {
		this.jxts = jxts;
	}

	public String getKmh() {
		return kmh;
	}

	public void setKmh(String kmh) {
		this.kmh = kmh;
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

	/** default constructor */
	public JrInvest() {
	}

	/** minimal constructor */
	public JrInvest(String acId) {
		this.acId = acId;
	}

	/** full constructor */
	public JrInvest(String acId, String brNo, String curNo, String khjl,
			String cusName, String custNo, String prdtNo, String prdtName,
			String orgnlbusntyp, Double holdPrincipal, Double billYield,
			String startDate, String endDate, String businessNo,Double rate,Double dealAmt) {
		this.acId = acId;
		this.brNo = brNo;
		this.curNo = curNo;
		this.khjl = khjl;
		this.cusName = cusName;
		this.custNo = custNo;
		this.prdtNo = prdtNo;
		this.prdtName = prdtName;
		this.orgnlbusntyp = orgnlbusntyp;
		this.holdPrincipal = holdPrincipal;
		this.billYield = billYield;
		this.startDate = startDate;
		this.endDate = endDate;
		this.businessNo = businessNo;
		this.dealAmt = dealAmt;
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

	public String getKhjl() {
		return this.khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
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

	public String getOrgnlbusntyp() {
		return this.orgnlbusntyp;
	}

	public void setOrgnlbusntyp(String orgnlbusntyp) {
		this.orgnlbusntyp = orgnlbusntyp;
	}

	public Double getHoldPrincipal() {
		return this.holdPrincipal;
	}

	public void setHoldPrincipal(Double holdPrincipal) {
		this.holdPrincipal = holdPrincipal;
	}

	public Double getBillYield() {
		return this.billYield;
	}

	public void setBillYield(Double billYield) {
		this.billYield = billYield;
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

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}


	public Double getDealAmt() {
		return dealAmt;
	}

	public void setDealAmt(Double dealAmt) {
		this.dealAmt = dealAmt;
	}

}