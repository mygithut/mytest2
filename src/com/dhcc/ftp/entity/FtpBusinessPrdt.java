package com.dhcc.ftp.entity;

/**
 * FtpBusinessPrdt entity. @author MyEclipse Persistence Tools
 */

public class FtpBusinessPrdt implements java.io.Serializable {

	// Fields

	private String prdtNo;
	private Integer accSeqc;
	private String prdtName;
	private String isPrice;
	private String accNo;
	private String accName;
	private String czh;
	private String isTermRequire;
	private String termNo;
	private String termName;
	private Integer minTerm;
	private Integer maxTerm;
	private String dataSource;
	private String tableName;
	private String minDateColname;
	private String maxDateColname;
	private String amtColname;
	private String rateColname;
	private String accColname;
	private String stsSql;

	// Constructors

	/** default constructor */
	public FtpBusinessPrdt() {
	}

	/** minimal constructor */
	public FtpBusinessPrdt(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	/** full constructor */
	public FtpBusinessPrdt(String prdtNo, Integer accSeqc, String prdtName,
			String isPrice, String accNo, String accName, String czh,
			String isTermRequire, String termNo, String termName,
			Integer minTerm, Integer maxTerm, String dataSource,
			String tableName, String minDateColname, String maxDateColname,
			String amtColname, String rateColname, String accColname,
			String stsSql) {
		this.prdtNo = prdtNo;
		this.accSeqc = accSeqc;
		this.prdtName = prdtName;
		this.isPrice = isPrice;
		this.accNo = accNo;
		this.accName = accName;
		this.czh = czh;
		this.isTermRequire = isTermRequire;
		this.termNo = termNo;
		this.termName = termName;
		this.minTerm = minTerm;
		this.maxTerm = maxTerm;
		this.dataSource = dataSource;
		this.tableName = tableName;
		this.minDateColname = minDateColname;
		this.maxDateColname = maxDateColname;
		this.amtColname = amtColname;
		this.rateColname = rateColname;
		this.accColname = accColname;
		this.stsSql = stsSql;
	}

	// Property accessors

	public String getPrdtNo() {
		return this.prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public Integer getAccSeqc() {
		return this.accSeqc;
	}

	public void setAccSeqc(Integer accSeqc) {
		this.accSeqc = accSeqc;
	}

	public String getPrdtName() {
		return this.prdtName;
	}

	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}

	public String getIsPrice() {
		return this.isPrice;
	}

	public void setIsPrice(String isPrice) {
		this.isPrice = isPrice;
	}

	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return this.accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getCzh() {
		return this.czh;
	}

	public void setCzh(String czh) {
		this.czh = czh;
	}

	public String getIsTermRequire() {
		return this.isTermRequire;
	}

	public void setIsTermRequire(String isTermRequire) {
		this.isTermRequire = isTermRequire;
	}

	public String getTermNo() {
		return this.termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getTermName() {
		return this.termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public Integer getMinTerm() {
		return this.minTerm;
	}

	public void setMinTerm(Integer minTerm) {
		this.minTerm = minTerm;
	}

	public Integer getMaxTerm() {
		return this.maxTerm;
	}

	public void setMaxTerm(Integer maxTerm) {
		this.maxTerm = maxTerm;
	}

	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getMinDateColname() {
		return this.minDateColname;
	}

	public void setMinDateColname(String minDateColname) {
		this.minDateColname = minDateColname;
	}

	public String getMaxDateColname() {
		return this.maxDateColname;
	}

	public void setMaxDateColname(String maxDateColname) {
		this.maxDateColname = maxDateColname;
	}

	public String getAmtColname() {
		return this.amtColname;
	}

	public void setAmtColname(String amtColname) {
		this.amtColname = amtColname;
	}

	public String getRateColname() {
		return this.rateColname;
	}

	public void setRateColname(String rateColname) {
		this.rateColname = rateColname;
	}

	public String getAccColname() {
		return this.accColname;
	}

	public void setAccColname(String accColname) {
		this.accColname = accColname;
	}

	public String getStsSql() {
		return this.stsSql;
	}

	public void setStsSql(String stsSql) {
		this.stsSql = stsSql;
	}

}