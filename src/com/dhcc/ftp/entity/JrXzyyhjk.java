package com.dhcc.ftp.entity;
import com.dhcc.ftp.util.Excel;
/**
 * JrXzyyhjk entity. @author MyEclipse Persistence Tools
 */

public class JrXzyyhjk implements java.io.Serializable {

	// Fields
	@Excel(exportName="���",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String acId;
	@Excel(exportName="������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brNo;
	@Excel(exportName="��������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String brName;
	@Excel(exportName="��Ʒ����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String prdtNo;
	@Excel(exportName="��Ʒ����",exportFieldWidth=60,exportConvertSign=0, importConvertSign=0)
	private String prdtName;
	@Excel(exportName="��Ŀ��",exportFieldWidth=60,exportConvertSign=0, importConvertSign=0)
	private String kmh;
	@Excel(exportName="���׶�������",exportFieldWidth=60,exportConvertSign=0, importConvertSign=0)
	private String cusName;
	@Excel(exportName="���(Ԫ)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double amt;
	@Excel(exportName="����(��%)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double rate;
	@Excel(exportName="��ʼ��",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String startDate;
	@Excel(exportName="������",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String endDate;
	@Excel(exportName="��Ϣ����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Integer days;
	@Excel(exportName="����Ա���",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String operator;
	@Excel(exportName="����Ա����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String operatorName;
	@Excel(exportName="���׶��ֱ��",exportFieldWidth=30,exportConvertSign=0, importConvertSign=0)
	private String custNo;
	private String businessNo;
//	@Excel(exportName="����",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private String curNo;
	@Excel(exportName="���(Ԫ)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
	private Double  bal;

	public Double getBal() {
		return bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	// Constructors

	/** default constructor */
	public JrXzyyhjk() {
	}

	/** minimal constructor */
	public JrXzyyhjk(String acId) {
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

	public String getKmh() {
		return this.kmh;
	}

	public void setKmh(String kmh) {
		this.kmh = kmh;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
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

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getCurNo() {
		return this.curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getBrName() {
		return this.brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

}