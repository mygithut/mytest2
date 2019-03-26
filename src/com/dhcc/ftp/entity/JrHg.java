package com.dhcc.ftp.entity;

import com.dhcc.ftp.util.Excel;

/**
 * JrHg entity. @author MyEclipse Persistence Tools
 */

public class JrHg implements java.io.Serializable {

	// Fields
	@Excel(exportName="序号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String acId;
	@Excel(exportName="机构号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brNo;
	//@Excel(exportName="币种",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String curNo;
	@Excel(exportName="交易对手名称",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String cusName;
	@Excel(exportName="交易对手编号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String custNo;
	@Excel(exportName="产品编号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String prdtNo;
	@Excel(exportName="产品名称",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String prdtName;
//	@Excel(exportName="业务类型",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String orgnlbusntyp;
	private String jyfx;
	@Excel(exportName="首期交割日",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String sqjgr;
	@Excel(exportName="计息天数",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Integer jyts;
	@Excel(exportName="到期交割日",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String dqjgr;
	
	@Excel(exportName="票面金额(元)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double qmje;
	@Excel(exportName="成交金额(元)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double cjje;
	private Double dqzfje;
	private String businessNo;
	/*@Excel(exportName="交易员",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String khjl;*/
	@Excel(exportName="回购利率(年%)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double hglv;
	@Excel(exportName="交易员编号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String khjl;
	@Excel(exportName="科目号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String kmh;
	@Excel(exportName="交易员姓名",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String empName;
	@Excel(exportName="机构名称",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brName;
	@Excel(exportName="票号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String ph;
	@Excel(exportName="余额(元)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double  bal;

	public Double getBal() {
		return bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}
	// Constructors

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getKhjl() {
		return khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
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
	public JrHg() {
	}

	/** minimal constructor */
	public JrHg(String acId) {
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

	public String getOrgnlbusntyp() {
		return this.orgnlbusntyp;
	}

	public void setOrgnlbusntyp(String orgnlbusntyp) {
		this.orgnlbusntyp = orgnlbusntyp;
	}

	public String getJyfx() {
		return this.jyfx;
	}

	public void setJyfx(String jyfx) {
		this.jyfx = jyfx;
	}

	public String getSqjgr() {
		return this.sqjgr;
	}

	public void setSqjgr(String sqjgr) {
		this.sqjgr = sqjgr;
	}


	public Integer getJyts() {
		return jyts;
	}

	public void setJyts(Integer jyts) {
		this.jyts = jyts;
	}

	public String getDqjgr() {
		return this.dqjgr;
	}

	public void setDqjgr(String dqjgr) {
		this.dqjgr = dqjgr;
	}

	public Double getHglv() {
		return this.hglv;
	}

	public void setHglv(Double hglv) {
		this.hglv = hglv;
	}

	public Double getQmje() {
		return this.qmje;
	}

	public void setQmje(Double qmje) {
		this.qmje = qmje;
	}

	public Double getCjje() {
		return this.cjje;
	}

	public void setCjje(Double cjje) {
		this.cjje = cjje;
	}

	public Double getDqzfje() {
		return this.dqzfje;
	}

	public void setDqzfje(Double dqzfje) {
		this.dqzfje = dqzfje;
	}

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}



}