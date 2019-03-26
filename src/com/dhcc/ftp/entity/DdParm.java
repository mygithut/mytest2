package com.dhcc.ftp.entity;

/**
 * DdParm entity. @author MyEclipse Persistence Tools
 */

public class DdParm implements java.io.Serializable {

	// Fields

	private String prdtNo;
	private String title;
	private Integer begDate;
	private Integer endDate;
	private String cifType;
	private String curNo;
	private String sttlType;
	private String acType;
	private String balType;
	private Double minBal;
	private Double maxBal;
	private Double minOpnAmt;
	private Double maxOpnAmt;
	private String opnChrgInd;
	private String opnChrgCode;
	private String thrOpnInd;
	private String thrClsInd;
	private String lmtCode;
	private String fwdInfrmCode;
	private String intstBalType;
	private String intstTermType;
	private Double intstMaxBal;
	private Double intstMinBal;
	private String rateNo;
	private String rateType;
	private String rateMode;
	private String rateFuncCode;
	private Double minFltRatio;
	private Double maxFltRatio;
	private String intstType;
	private String acmCalcType;
	private Integer intstMon;
	private Integer intstDay;
	private String taxNo;
	private String chrgTermType;
	private String chrgCode;
	private String drtNo;
	private Double minDrtAmt;
	private Double lmtDrtAmt;
	private String keepChrgCode;
	private Integer opnDtrTerm;
	private String opnDtrType;
	private String txDrtInd;
	private String dtxDrtInd;
	private String slpCond;
	private String slpChrgInd;
	private String clsCondNo;
	private Integer hstKeepTerm;
	private String hstKeepType;
	private String prtInd;
	private String slpPrtInd;
	private Integer prtTerm;
	private String prtTermType;
	private String dpstInd;
	private String drawInd;
	private String thrDpstInd;
	private String thrDrawInd;
	private String opnAplicInd;
	private String cashInd;
	private String singlInd;
	private String odAplicInd;
	private String lkgInd;
	private String ownerPrdtNo;
	private Integer opnLmt;
	private Integer clsLmt;
	private String dcCode;
	private String putInd;

	// Constructors

	/** default constructor */
	public DdParm() {
	}

	/** minimal constructor */
	public DdParm(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	/** full constructor */
	public DdParm(String prdtNo, String title, Integer begDate,
			Integer endDate, String cifType, String curNo, String sttlType,
			String acType, String balType, Double minBal, Double maxBal,
			Double minOpnAmt, Double maxOpnAmt, String opnChrgInd,
			String opnChrgCode, String thrOpnInd, String thrClsInd,
			String lmtCode, String fwdInfrmCode, String intstBalType,
			String intstTermType, Double intstMaxBal, Double intstMinBal,
			String rateNo, String rateType, String rateMode,
			String rateFuncCode, Double minFltRatio, Double maxFltRatio,
			String intstType, String acmCalcType, Integer intstMon,
			Integer intstDay, String taxNo, String chrgTermType,
			String chrgCode, String drtNo, Double minDrtAmt, Double lmtDrtAmt,
			String keepChrgCode, Integer opnDtrTerm, String opnDtrType,
			String txDrtInd, String dtxDrtInd, String slpCond,
			String slpChrgInd, String clsCondNo, Integer hstKeepTerm,
			String hstKeepType, String prtInd, String slpPrtInd,
			Integer prtTerm, String prtTermType, String dpstInd,
			String drawInd, String thrDpstInd, String thrDrawInd,
			String opnAplicInd, String cashInd, String singlInd,
			String odAplicInd, String lkgInd, String ownerPrdtNo,
			Integer opnLmt, Integer clsLmt, String dcCode, String putInd) {
		this.prdtNo = prdtNo;
		this.title = title;
		this.begDate = begDate;
		this.endDate = endDate;
		this.cifType = cifType;
		this.curNo = curNo;
		this.sttlType = sttlType;
		this.acType = acType;
		this.balType = balType;
		this.minBal = minBal;
		this.maxBal = maxBal;
		this.minOpnAmt = minOpnAmt;
		this.maxOpnAmt = maxOpnAmt;
		this.opnChrgInd = opnChrgInd;
		this.opnChrgCode = opnChrgCode;
		this.thrOpnInd = thrOpnInd;
		this.thrClsInd = thrClsInd;
		this.lmtCode = lmtCode;
		this.fwdInfrmCode = fwdInfrmCode;
		this.intstBalType = intstBalType;
		this.intstTermType = intstTermType;
		this.intstMaxBal = intstMaxBal;
		this.intstMinBal = intstMinBal;
		this.rateNo = rateNo;
		this.rateType = rateType;
		this.rateMode = rateMode;
		this.rateFuncCode = rateFuncCode;
		this.minFltRatio = minFltRatio;
		this.maxFltRatio = maxFltRatio;
		this.intstType = intstType;
		this.acmCalcType = acmCalcType;
		this.intstMon = intstMon;
		this.intstDay = intstDay;
		this.taxNo = taxNo;
		this.chrgTermType = chrgTermType;
		this.chrgCode = chrgCode;
		this.drtNo = drtNo;
		this.minDrtAmt = minDrtAmt;
		this.lmtDrtAmt = lmtDrtAmt;
		this.keepChrgCode = keepChrgCode;
		this.opnDtrTerm = opnDtrTerm;
		this.opnDtrType = opnDtrType;
		this.txDrtInd = txDrtInd;
		this.dtxDrtInd = dtxDrtInd;
		this.slpCond = slpCond;
		this.slpChrgInd = slpChrgInd;
		this.clsCondNo = clsCondNo;
		this.hstKeepTerm = hstKeepTerm;
		this.hstKeepType = hstKeepType;
		this.prtInd = prtInd;
		this.slpPrtInd = slpPrtInd;
		this.prtTerm = prtTerm;
		this.prtTermType = prtTermType;
		this.dpstInd = dpstInd;
		this.drawInd = drawInd;
		this.thrDpstInd = thrDpstInd;
		this.thrDrawInd = thrDrawInd;
		this.opnAplicInd = opnAplicInd;
		this.cashInd = cashInd;
		this.singlInd = singlInd;
		this.odAplicInd = odAplicInd;
		this.lkgInd = lkgInd;
		this.ownerPrdtNo = ownerPrdtNo;
		this.opnLmt = opnLmt;
		this.clsLmt = clsLmt;
		this.dcCode = dcCode;
		this.putInd = putInd;
	}

	// Property accessors

	public String getPrdtNo() {
		return this.prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getBegDate() {
		return this.begDate;
	}

	public void setBegDate(Integer begDate) {
		this.begDate = begDate;
	}

	public Integer getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	public String getCifType() {
		return this.cifType;
	}

	public void setCifType(String cifType) {
		this.cifType = cifType;
	}

	public String getCurNo() {
		return this.curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getSttlType() {
		return this.sttlType;
	}

	public void setSttlType(String sttlType) {
		this.sttlType = sttlType;
	}

	public String getAcType() {
		return this.acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getBalType() {
		return this.balType;
	}

	public void setBalType(String balType) {
		this.balType = balType;
	}

	public Double getMinBal() {
		return this.minBal;
	}

	public void setMinBal(Double minBal) {
		this.minBal = minBal;
	}

	public Double getMaxBal() {
		return this.maxBal;
	}

	public void setMaxBal(Double maxBal) {
		this.maxBal = maxBal;
	}

	public Double getMinOpnAmt() {
		return this.minOpnAmt;
	}

	public void setMinOpnAmt(Double minOpnAmt) {
		this.minOpnAmt = minOpnAmt;
	}

	public Double getMaxOpnAmt() {
		return this.maxOpnAmt;
	}

	public void setMaxOpnAmt(Double maxOpnAmt) {
		this.maxOpnAmt = maxOpnAmt;
	}

	public String getOpnChrgInd() {
		return this.opnChrgInd;
	}

	public void setOpnChrgInd(String opnChrgInd) {
		this.opnChrgInd = opnChrgInd;
	}

	public String getOpnChrgCode() {
		return this.opnChrgCode;
	}

	public void setOpnChrgCode(String opnChrgCode) {
		this.opnChrgCode = opnChrgCode;
	}

	public String getThrOpnInd() {
		return this.thrOpnInd;
	}

	public void setThrOpnInd(String thrOpnInd) {
		this.thrOpnInd = thrOpnInd;
	}

	public String getThrClsInd() {
		return this.thrClsInd;
	}

	public void setThrClsInd(String thrClsInd) {
		this.thrClsInd = thrClsInd;
	}

	public String getLmtCode() {
		return this.lmtCode;
	}

	public void setLmtCode(String lmtCode) {
		this.lmtCode = lmtCode;
	}

	public String getFwdInfrmCode() {
		return this.fwdInfrmCode;
	}

	public void setFwdInfrmCode(String fwdInfrmCode) {
		this.fwdInfrmCode = fwdInfrmCode;
	}

	public String getIntstBalType() {
		return this.intstBalType;
	}

	public void setIntstBalType(String intstBalType) {
		this.intstBalType = intstBalType;
	}

	public String getIntstTermType() {
		return this.intstTermType;
	}

	public void setIntstTermType(String intstTermType) {
		this.intstTermType = intstTermType;
	}

	public Double getIntstMaxBal() {
		return this.intstMaxBal;
	}

	public void setIntstMaxBal(Double intstMaxBal) {
		this.intstMaxBal = intstMaxBal;
	}

	public Double getIntstMinBal() {
		return this.intstMinBal;
	}

	public void setIntstMinBal(Double intstMinBal) {
		this.intstMinBal = intstMinBal;
	}

	public String getRateNo() {
		return this.rateNo;
	}

	public void setRateNo(String rateNo) {
		this.rateNo = rateNo;
	}

	public String getRateType() {
		return this.rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRateMode() {
		return this.rateMode;
	}

	public void setRateMode(String rateMode) {
		this.rateMode = rateMode;
	}

	public String getRateFuncCode() {
		return this.rateFuncCode;
	}

	public void setRateFuncCode(String rateFuncCode) {
		this.rateFuncCode = rateFuncCode;
	}

	public Double getMinFltRatio() {
		return this.minFltRatio;
	}

	public void setMinFltRatio(Double minFltRatio) {
		this.minFltRatio = minFltRatio;
	}

	public Double getMaxFltRatio() {
		return this.maxFltRatio;
	}

	public void setMaxFltRatio(Double maxFltRatio) {
		this.maxFltRatio = maxFltRatio;
	}

	public String getIntstType() {
		return this.intstType;
	}

	public void setIntstType(String intstType) {
		this.intstType = intstType;
	}

	public String getAcmCalcType() {
		return this.acmCalcType;
	}

	public void setAcmCalcType(String acmCalcType) {
		this.acmCalcType = acmCalcType;
	}

	public Integer getIntstMon() {
		return this.intstMon;
	}

	public void setIntstMon(Integer intstMon) {
		this.intstMon = intstMon;
	}

	public Integer getIntstDay() {
		return this.intstDay;
	}

	public void setIntstDay(Integer intstDay) {
		this.intstDay = intstDay;
	}

	public String getTaxNo() {
		return this.taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getChrgTermType() {
		return this.chrgTermType;
	}

	public void setChrgTermType(String chrgTermType) {
		this.chrgTermType = chrgTermType;
	}

	public String getChrgCode() {
		return this.chrgCode;
	}

	public void setChrgCode(String chrgCode) {
		this.chrgCode = chrgCode;
	}

	public String getDrtNo() {
		return this.drtNo;
	}

	public void setDrtNo(String drtNo) {
		this.drtNo = drtNo;
	}

	public Double getMinDrtAmt() {
		return this.minDrtAmt;
	}

	public void setMinDrtAmt(Double minDrtAmt) {
		this.minDrtAmt = minDrtAmt;
	}

	public Double getLmtDrtAmt() {
		return this.lmtDrtAmt;
	}

	public void setLmtDrtAmt(Double lmtDrtAmt) {
		this.lmtDrtAmt = lmtDrtAmt;
	}

	public String getKeepChrgCode() {
		return this.keepChrgCode;
	}

	public void setKeepChrgCode(String keepChrgCode) {
		this.keepChrgCode = keepChrgCode;
	}

	public Integer getOpnDtrTerm() {
		return this.opnDtrTerm;
	}

	public void setOpnDtrTerm(Integer opnDtrTerm) {
		this.opnDtrTerm = opnDtrTerm;
	}

	public String getOpnDtrType() {
		return this.opnDtrType;
	}

	public void setOpnDtrType(String opnDtrType) {
		this.opnDtrType = opnDtrType;
	}

	public String getTxDrtInd() {
		return this.txDrtInd;
	}

	public void setTxDrtInd(String txDrtInd) {
		this.txDrtInd = txDrtInd;
	}

	public String getDtxDrtInd() {
		return this.dtxDrtInd;
	}

	public void setDtxDrtInd(String dtxDrtInd) {
		this.dtxDrtInd = dtxDrtInd;
	}

	public String getSlpCond() {
		return this.slpCond;
	}

	public void setSlpCond(String slpCond) {
		this.slpCond = slpCond;
	}

	public String getSlpChrgInd() {
		return this.slpChrgInd;
	}

	public void setSlpChrgInd(String slpChrgInd) {
		this.slpChrgInd = slpChrgInd;
	}

	public String getClsCondNo() {
		return this.clsCondNo;
	}

	public void setClsCondNo(String clsCondNo) {
		this.clsCondNo = clsCondNo;
	}

	public Integer getHstKeepTerm() {
		return this.hstKeepTerm;
	}

	public void setHstKeepTerm(Integer hstKeepTerm) {
		this.hstKeepTerm = hstKeepTerm;
	}

	public String getHstKeepType() {
		return this.hstKeepType;
	}

	public void setHstKeepType(String hstKeepType) {
		this.hstKeepType = hstKeepType;
	}

	public String getPrtInd() {
		return this.prtInd;
	}

	public void setPrtInd(String prtInd) {
		this.prtInd = prtInd;
	}

	public String getSlpPrtInd() {
		return this.slpPrtInd;
	}

	public void setSlpPrtInd(String slpPrtInd) {
		this.slpPrtInd = slpPrtInd;
	}

	public Integer getPrtTerm() {
		return this.prtTerm;
	}

	public void setPrtTerm(Integer prtTerm) {
		this.prtTerm = prtTerm;
	}

	public String getPrtTermType() {
		return this.prtTermType;
	}

	public void setPrtTermType(String prtTermType) {
		this.prtTermType = prtTermType;
	}

	public String getDpstInd() {
		return this.dpstInd;
	}

	public void setDpstInd(String dpstInd) {
		this.dpstInd = dpstInd;
	}

	public String getDrawInd() {
		return this.drawInd;
	}

	public void setDrawInd(String drawInd) {
		this.drawInd = drawInd;
	}

	public String getThrDpstInd() {
		return this.thrDpstInd;
	}

	public void setThrDpstInd(String thrDpstInd) {
		this.thrDpstInd = thrDpstInd;
	}

	public String getThrDrawInd() {
		return this.thrDrawInd;
	}

	public void setThrDrawInd(String thrDrawInd) {
		this.thrDrawInd = thrDrawInd;
	}

	public String getOpnAplicInd() {
		return this.opnAplicInd;
	}

	public void setOpnAplicInd(String opnAplicInd) {
		this.opnAplicInd = opnAplicInd;
	}

	public String getCashInd() {
		return this.cashInd;
	}

	public void setCashInd(String cashInd) {
		this.cashInd = cashInd;
	}

	public String getSinglInd() {
		return this.singlInd;
	}

	public void setSinglInd(String singlInd) {
		this.singlInd = singlInd;
	}

	public String getOdAplicInd() {
		return this.odAplicInd;
	}

	public void setOdAplicInd(String odAplicInd) {
		this.odAplicInd = odAplicInd;
	}

	public String getLkgInd() {
		return this.lkgInd;
	}

	public void setLkgInd(String lkgInd) {
		this.lkgInd = lkgInd;
	}

	public String getOwnerPrdtNo() {
		return this.ownerPrdtNo;
	}

	public void setOwnerPrdtNo(String ownerPrdtNo) {
		this.ownerPrdtNo = ownerPrdtNo;
	}

	public Integer getOpnLmt() {
		return this.opnLmt;
	}

	public void setOpnLmt(Integer opnLmt) {
		this.opnLmt = opnLmt;
	}

	public Integer getClsLmt() {
		return this.clsLmt;
	}

	public void setClsLmt(Integer clsLmt) {
		this.clsLmt = clsLmt;
	}

	public String getDcCode() {
		return this.dcCode;
	}

	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}

	public String getPutInd() {
		return this.putInd;
	}

	public void setPutInd(String putInd) {
		this.putInd = putInd;
	}

}