package com.dhcc.ftp.entity;

import com.dhcc.ftp.util.Excel;

/**
 * JrXqmm entity. @author MyEclipse Persistence Tools
 */

public class JrXqmm implements java.io.Serializable {

	// Fields
	@Excel(exportName="���",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String acId;
	@Excel(exportName="������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brNo;
	@Excel(exportName="����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String curNo;
	@Excel(exportName="���׶�������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String cusName;
	@Excel(exportName="��Ʒ����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String prdtNo;
	@Excel(exportName="��Ʒ����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String prdtName;
	@Excel(exportName="��������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String buyDate;
	@Excel(exportName="����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double netPrice;
	@Excel(exportName="Ʊ����(Ԫ)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double bondAmt;
	@Excel(exportName="Ʊ������(%)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double rate;
	@Excel(exportName="�ɽ����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double fullpriceAmt;
	@Excel(exportName="������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String opeDate;
	private String businessNo;
	@Excel(exportName="��������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String buyOrSale;
	@Excel(exportName="���׶��ֱ��",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String custNo;
	@Excel(exportName="����Ա���",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String khjl;
	@Excel(exportName="��Ʒ���",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String orgnlbusntyp;
	@Excel(exportName="����Ա����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String empName;
	@Excel(exportName="��������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brName;
	@Excel(exportName="��Ŀ��",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String kmh;
	@Excel(exportName="���(Ԫ)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double  bal;

	public Double getBal() {
		return bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}
	// Constructors

	public String getBrName() {
		return brName;
	}

	public String getKmh() {
		return kmh;
	}

	public void setKmh(String kmh) {
		this.kmh = kmh;
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
	public JrXqmm() {
	}

	/** minimal constructor */
	public JrXqmm(String acId) {
		this.acId = acId;
	}

	/** full constructor */
	public JrXqmm(String acId, String brNo, String curNo, String cusName,
			String prdtNo, String prdtName, String buyDate, Double netPrice,
			Double bondAmt, Double fullpriceAmt, String opeDate,
			String businessNo, String buyOrSale, String custNo, String khjl,
			String orgnlbusntyp,Double rate) {
		this.acId = acId;
		this.brNo = brNo;
		this.curNo = curNo;
		this.cusName = cusName;
		this.prdtNo = prdtNo;
		this.prdtName = prdtName;
		this.buyDate = buyDate;
		this.netPrice = netPrice;
		this.bondAmt = bondAmt;
		this.fullpriceAmt = fullpriceAmt;
		this.opeDate = opeDate;
		this.businessNo = businessNo;
		this.buyOrSale = buyOrSale;
		this.custNo = custNo;
		this.khjl = khjl;
		this.orgnlbusntyp = orgnlbusntyp;
		this.rate = rate;
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

	public String getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public Double getNetPrice() {
		return this.netPrice;
	}

	public void setNetPrice(Double netPrice) {
		this.netPrice = netPrice;
	}

	public Double getBondAmt() {
		return this.bondAmt;
	}

	public void setBondAmt(Double bondAmt) {
		this.bondAmt = bondAmt;
	}

	public Double getFullpriceAmt() {
		return this.fullpriceAmt;
	}

	public void setFullpriceAmt(Double fullpriceAmt) {
		this.fullpriceAmt = fullpriceAmt;
	}

	public String getOpeDate() {
		return this.opeDate;
	}

	public void setOpeDate(String opeDate) {
		this.opeDate = opeDate;
	}

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getBuyOrSale() {
		return this.buyOrSale;
	}

	public void setBuyOrSale(String buyOrSale) {
		this.buyOrSale = buyOrSale;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getKhjl() {
		return this.khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}

	public String getOrgnlbusntyp() {
		return this.orgnlbusntyp;
	}

	public void setOrgnlbusntyp(String orgnlbusntyp) {
		this.orgnlbusntyp = orgnlbusntyp;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}