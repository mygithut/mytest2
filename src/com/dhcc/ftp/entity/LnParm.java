package com.dhcc.ftp.entity;

/**
 * LnParm entity. @author MyEclipse Persistence Tools
 */

public class LnParm implements java.io.Serializable {

	// Fields

	private String prdtNo;
	private String title;
	private Integer begDate;
	private Integer endDate;
	private String cifType;
	private String curNo;
	private String timeType;
	private Integer minTerm;
	private Integer maxTerm;
	private String termType;
	private Double minBal;
	private Double maxBal;
	private String intstDaysType;
	private String intstType;
	private String rateNo;
	private String getRateType;
	private String overRateType;
	private String overRateNo;
	private Integer minFltRatio;
	private Integer maxFltRatio;
	private Integer overRateMin;
	private Integer overRateMax;
	private Integer overRateDef;
	private String fineRateType;
	private String fineRateNo;
	private Integer fineRateMin;
	private Integer fineRateMax;
	private Integer fineRateDef;
	private String expInd;
	private String intstUseInd;
	private Double minIntst;
	private String cmpdIntstInd;
	private String cmpdIntstType;
	private String acSts;
	private String autoCls;
	private String prmChrgInd;
	private String prmChrgType;
	private String prmChrgNo;
	private Integer fdrpInfDays;
	private Double fdrqLmtAmt;
	private String fdrpChrgInd;
	private String fdrpChrgType;
	private String fdrpChrgNo;
	private Integer fdrpChrgTerm;
	private String holiInd;
	private String intType;
	private Integer intUnit;
	private String payPlnType;
	private String lnPayType;
	private String payPlnCrt;
	private String payTermType;
	private Integer payAmtTerm;
	private String fstPayInd;
	private Integer fstPayDay;
	private Integer monPayDay;
	private String fstPayIntsInd;
	private Integer fstPayIntsDay;
	private String intstRecalcInd;
	private String intstRecalcType;
	private String fwdrpRecalcInd;
	private String fwdrpRecalcType;
	private String intstChgMtr;
	private String repayChgMtr;
	private String contlnType;
	private String trustProtInd;
	private String lnType;
	private String turnIllInd;
	private Integer turnIllTerm;
	private String repayInd;
	private String autoPayInd;
	private String paySeqCode;
	private Integer opnLmt;
	private Integer clsLmt;
	private String dcCode;
	private String overDcCode;
	private String slaDcCode;
	private String badDcCode;
	private String over90DcCode;
	private String filler;

	// Constructors

	/** default constructor */
	public LnParm() {
	}

	/** minimal constructor */
	public LnParm(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	/** full constructor */
	public LnParm(String prdtNo, String title, Integer begDate,
			Integer endDate, String cifType, String curNo, String timeType,
			Integer minTerm, Integer maxTerm, String termType, Double minBal,
			Double maxBal, String intstDaysType, String intstType,
			String rateNo, String getRateType, String overRateType,
			String overRateNo, Integer minFltRatio, Integer maxFltRatio,
			Integer overRateMin, Integer overRateMax, Integer overRateDef,
			String fineRateType, String fineRateNo, Integer fineRateMin,
			Integer fineRateMax, Integer fineRateDef, String expInd,
			String intstUseInd, Double minIntst, String cmpdIntstInd,
			String cmpdIntstType, String acSts, String autoCls,
			String prmChrgInd, String prmChrgType, String prmChrgNo,
			Integer fdrpInfDays, Double fdrqLmtAmt, String fdrpChrgInd,
			String fdrpChrgType, String fdrpChrgNo, Integer fdrpChrgTerm,
			String holiInd, String intType, Integer intUnit, String payPlnType,
			String lnPayType, String payPlnCrt, String payTermType,
			Integer payAmtTerm, String fstPayInd, Integer fstPayDay,
			Integer monPayDay, String fstPayIntsInd, Integer fstPayIntsDay,
			String intstRecalcInd, String intstRecalcType,
			String fwdrpRecalcInd, String fwdrpRecalcType, String intstChgMtr,
			String repayChgMtr, String contlnType, String trustProtInd,
			String lnType, String turnIllInd, Integer turnIllTerm,
			String repayInd, String autoPayInd, String paySeqCode,
			Integer opnLmt, Integer clsLmt, String dcCode, String overDcCode,
			String slaDcCode, String badDcCode, String over90DcCode,
			String filler) {
		this.prdtNo = prdtNo;
		this.title = title;
		this.begDate = begDate;
		this.endDate = endDate;
		this.cifType = cifType;
		this.curNo = curNo;
		this.timeType = timeType;
		this.minTerm = minTerm;
		this.maxTerm = maxTerm;
		this.termType = termType;
		this.minBal = minBal;
		this.maxBal = maxBal;
		this.intstDaysType = intstDaysType;
		this.intstType = intstType;
		this.rateNo = rateNo;
		this.getRateType = getRateType;
		this.overRateType = overRateType;
		this.overRateNo = overRateNo;
		this.minFltRatio = minFltRatio;
		this.maxFltRatio = maxFltRatio;
		this.overRateMin = overRateMin;
		this.overRateMax = overRateMax;
		this.overRateDef = overRateDef;
		this.fineRateType = fineRateType;
		this.fineRateNo = fineRateNo;
		this.fineRateMin = fineRateMin;
		this.fineRateMax = fineRateMax;
		this.fineRateDef = fineRateDef;
		this.expInd = expInd;
		this.intstUseInd = intstUseInd;
		this.minIntst = minIntst;
		this.cmpdIntstInd = cmpdIntstInd;
		this.cmpdIntstType = cmpdIntstType;
		this.acSts = acSts;
		this.autoCls = autoCls;
		this.prmChrgInd = prmChrgInd;
		this.prmChrgType = prmChrgType;
		this.prmChrgNo = prmChrgNo;
		this.fdrpInfDays = fdrpInfDays;
		this.fdrqLmtAmt = fdrqLmtAmt;
		this.fdrpChrgInd = fdrpChrgInd;
		this.fdrpChrgType = fdrpChrgType;
		this.fdrpChrgNo = fdrpChrgNo;
		this.fdrpChrgTerm = fdrpChrgTerm;
		this.holiInd = holiInd;
		this.intType = intType;
		this.intUnit = intUnit;
		this.payPlnType = payPlnType;
		this.lnPayType = lnPayType;
		this.payPlnCrt = payPlnCrt;
		this.payTermType = payTermType;
		this.payAmtTerm = payAmtTerm;
		this.fstPayInd = fstPayInd;
		this.fstPayDay = fstPayDay;
		this.monPayDay = monPayDay;
		this.fstPayIntsInd = fstPayIntsInd;
		this.fstPayIntsDay = fstPayIntsDay;
		this.intstRecalcInd = intstRecalcInd;
		this.intstRecalcType = intstRecalcType;
		this.fwdrpRecalcInd = fwdrpRecalcInd;
		this.fwdrpRecalcType = fwdrpRecalcType;
		this.intstChgMtr = intstChgMtr;
		this.repayChgMtr = repayChgMtr;
		this.contlnType = contlnType;
		this.trustProtInd = trustProtInd;
		this.lnType = lnType;
		this.turnIllInd = turnIllInd;
		this.turnIllTerm = turnIllTerm;
		this.repayInd = repayInd;
		this.autoPayInd = autoPayInd;
		this.paySeqCode = paySeqCode;
		this.opnLmt = opnLmt;
		this.clsLmt = clsLmt;
		this.dcCode = dcCode;
		this.overDcCode = overDcCode;
		this.slaDcCode = slaDcCode;
		this.badDcCode = badDcCode;
		this.over90DcCode = over90DcCode;
		this.filler = filler;
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

	public String getTimeType() {
		return this.timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
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

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
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

	public String getIntstDaysType() {
		return this.intstDaysType;
	}

	public void setIntstDaysType(String intstDaysType) {
		this.intstDaysType = intstDaysType;
	}

	public String getIntstType() {
		return this.intstType;
	}

	public void setIntstType(String intstType) {
		this.intstType = intstType;
	}

	public String getRateNo() {
		return this.rateNo;
	}

	public void setRateNo(String rateNo) {
		this.rateNo = rateNo;
	}

	public String getGetRateType() {
		return this.getRateType;
	}

	public void setGetRateType(String getRateType) {
		this.getRateType = getRateType;
	}

	public String getOverRateType() {
		return this.overRateType;
	}

	public void setOverRateType(String overRateType) {
		this.overRateType = overRateType;
	}

	public String getOverRateNo() {
		return this.overRateNo;
	}

	public void setOverRateNo(String overRateNo) {
		this.overRateNo = overRateNo;
	}

	public Integer getMinFltRatio() {
		return this.minFltRatio;
	}

	public void setMinFltRatio(Integer minFltRatio) {
		this.minFltRatio = minFltRatio;
	}

	public Integer getMaxFltRatio() {
		return this.maxFltRatio;
	}

	public void setMaxFltRatio(Integer maxFltRatio) {
		this.maxFltRatio = maxFltRatio;
	}

	public Integer getOverRateMin() {
		return this.overRateMin;
	}

	public void setOverRateMin(Integer overRateMin) {
		this.overRateMin = overRateMin;
	}

	public Integer getOverRateMax() {
		return this.overRateMax;
	}

	public void setOverRateMax(Integer overRateMax) {
		this.overRateMax = overRateMax;
	}

	public Integer getOverRateDef() {
		return this.overRateDef;
	}

	public void setOverRateDef(Integer overRateDef) {
		this.overRateDef = overRateDef;
	}

	public String getFineRateType() {
		return this.fineRateType;
	}

	public void setFineRateType(String fineRateType) {
		this.fineRateType = fineRateType;
	}

	public String getFineRateNo() {
		return this.fineRateNo;
	}

	public void setFineRateNo(String fineRateNo) {
		this.fineRateNo = fineRateNo;
	}

	public Integer getFineRateMin() {
		return this.fineRateMin;
	}

	public void setFineRateMin(Integer fineRateMin) {
		this.fineRateMin = fineRateMin;
	}

	public Integer getFineRateMax() {
		return this.fineRateMax;
	}

	public void setFineRateMax(Integer fineRateMax) {
		this.fineRateMax = fineRateMax;
	}

	public Integer getFineRateDef() {
		return this.fineRateDef;
	}

	public void setFineRateDef(Integer fineRateDef) {
		this.fineRateDef = fineRateDef;
	}

	public String getExpInd() {
		return this.expInd;
	}

	public void setExpInd(String expInd) {
		this.expInd = expInd;
	}

	public String getIntstUseInd() {
		return this.intstUseInd;
	}

	public void setIntstUseInd(String intstUseInd) {
		this.intstUseInd = intstUseInd;
	}

	public Double getMinIntst() {
		return this.minIntst;
	}

	public void setMinIntst(Double minIntst) {
		this.minIntst = minIntst;
	}

	public String getCmpdIntstInd() {
		return this.cmpdIntstInd;
	}

	public void setCmpdIntstInd(String cmpdIntstInd) {
		this.cmpdIntstInd = cmpdIntstInd;
	}

	public String getCmpdIntstType() {
		return this.cmpdIntstType;
	}

	public void setCmpdIntstType(String cmpdIntstType) {
		this.cmpdIntstType = cmpdIntstType;
	}

	public String getAcSts() {
		return this.acSts;
	}

	public void setAcSts(String acSts) {
		this.acSts = acSts;
	}

	public String getAutoCls() {
		return this.autoCls;
	}

	public void setAutoCls(String autoCls) {
		this.autoCls = autoCls;
	}

	public String getPrmChrgInd() {
		return this.prmChrgInd;
	}

	public void setPrmChrgInd(String prmChrgInd) {
		this.prmChrgInd = prmChrgInd;
	}

	public String getPrmChrgType() {
		return this.prmChrgType;
	}

	public void setPrmChrgType(String prmChrgType) {
		this.prmChrgType = prmChrgType;
	}

	public String getPrmChrgNo() {
		return this.prmChrgNo;
	}

	public void setPrmChrgNo(String prmChrgNo) {
		this.prmChrgNo = prmChrgNo;
	}

	public Integer getFdrpInfDays() {
		return this.fdrpInfDays;
	}

	public void setFdrpInfDays(Integer fdrpInfDays) {
		this.fdrpInfDays = fdrpInfDays;
	}

	public Double getFdrqLmtAmt() {
		return this.fdrqLmtAmt;
	}

	public void setFdrqLmtAmt(Double fdrqLmtAmt) {
		this.fdrqLmtAmt = fdrqLmtAmt;
	}

	public String getFdrpChrgInd() {
		return this.fdrpChrgInd;
	}

	public void setFdrpChrgInd(String fdrpChrgInd) {
		this.fdrpChrgInd = fdrpChrgInd;
	}

	public String getFdrpChrgType() {
		return this.fdrpChrgType;
	}

	public void setFdrpChrgType(String fdrpChrgType) {
		this.fdrpChrgType = fdrpChrgType;
	}

	public String getFdrpChrgNo() {
		return this.fdrpChrgNo;
	}

	public void setFdrpChrgNo(String fdrpChrgNo) {
		this.fdrpChrgNo = fdrpChrgNo;
	}

	public Integer getFdrpChrgTerm() {
		return this.fdrpChrgTerm;
	}

	public void setFdrpChrgTerm(Integer fdrpChrgTerm) {
		this.fdrpChrgTerm = fdrpChrgTerm;
	}

	public String getHoliInd() {
		return this.holiInd;
	}

	public void setHoliInd(String holiInd) {
		this.holiInd = holiInd;
	}

	public String getIntType() {
		return this.intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}

	public Integer getIntUnit() {
		return this.intUnit;
	}

	public void setIntUnit(Integer intUnit) {
		this.intUnit = intUnit;
	}

	public String getPayPlnType() {
		return this.payPlnType;
	}

	public void setPayPlnType(String payPlnType) {
		this.payPlnType = payPlnType;
	}

	public String getLnPayType() {
		return this.lnPayType;
	}

	public void setLnPayType(String lnPayType) {
		this.lnPayType = lnPayType;
	}

	public String getPayPlnCrt() {
		return this.payPlnCrt;
	}

	public void setPayPlnCrt(String payPlnCrt) {
		this.payPlnCrt = payPlnCrt;
	}

	public String getPayTermType() {
		return this.payTermType;
	}

	public void setPayTermType(String payTermType) {
		this.payTermType = payTermType;
	}

	public Integer getPayAmtTerm() {
		return this.payAmtTerm;
	}

	public void setPayAmtTerm(Integer payAmtTerm) {
		this.payAmtTerm = payAmtTerm;
	}

	public String getFstPayInd() {
		return this.fstPayInd;
	}

	public void setFstPayInd(String fstPayInd) {
		this.fstPayInd = fstPayInd;
	}

	public Integer getFstPayDay() {
		return this.fstPayDay;
	}

	public void setFstPayDay(Integer fstPayDay) {
		this.fstPayDay = fstPayDay;
	}

	public Integer getMonPayDay() {
		return this.monPayDay;
	}

	public void setMonPayDay(Integer monPayDay) {
		this.monPayDay = monPayDay;
	}

	public String getFstPayIntsInd() {
		return this.fstPayIntsInd;
	}

	public void setFstPayIntsInd(String fstPayIntsInd) {
		this.fstPayIntsInd = fstPayIntsInd;
	}

	public Integer getFstPayIntsDay() {
		return this.fstPayIntsDay;
	}

	public void setFstPayIntsDay(Integer fstPayIntsDay) {
		this.fstPayIntsDay = fstPayIntsDay;
	}

	public String getIntstRecalcInd() {
		return this.intstRecalcInd;
	}

	public void setIntstRecalcInd(String intstRecalcInd) {
		this.intstRecalcInd = intstRecalcInd;
	}

	public String getIntstRecalcType() {
		return this.intstRecalcType;
	}

	public void setIntstRecalcType(String intstRecalcType) {
		this.intstRecalcType = intstRecalcType;
	}

	public String getFwdrpRecalcInd() {
		return this.fwdrpRecalcInd;
	}

	public void setFwdrpRecalcInd(String fwdrpRecalcInd) {
		this.fwdrpRecalcInd = fwdrpRecalcInd;
	}

	public String getFwdrpRecalcType() {
		return this.fwdrpRecalcType;
	}

	public void setFwdrpRecalcType(String fwdrpRecalcType) {
		this.fwdrpRecalcType = fwdrpRecalcType;
	}

	public String getIntstChgMtr() {
		return this.intstChgMtr;
	}

	public void setIntstChgMtr(String intstChgMtr) {
		this.intstChgMtr = intstChgMtr;
	}

	public String getRepayChgMtr() {
		return this.repayChgMtr;
	}

	public void setRepayChgMtr(String repayChgMtr) {
		this.repayChgMtr = repayChgMtr;
	}

	public String getContlnType() {
		return this.contlnType;
	}

	public void setContlnType(String contlnType) {
		this.contlnType = contlnType;
	}

	public String getTrustProtInd() {
		return this.trustProtInd;
	}

	public void setTrustProtInd(String trustProtInd) {
		this.trustProtInd = trustProtInd;
	}

	public String getLnType() {
		return this.lnType;
	}

	public void setLnType(String lnType) {
		this.lnType = lnType;
	}

	public String getTurnIllInd() {
		return this.turnIllInd;
	}

	public void setTurnIllInd(String turnIllInd) {
		this.turnIllInd = turnIllInd;
	}

	public Integer getTurnIllTerm() {
		return this.turnIllTerm;
	}

	public void setTurnIllTerm(Integer turnIllTerm) {
		this.turnIllTerm = turnIllTerm;
	}

	public String getRepayInd() {
		return this.repayInd;
	}

	public void setRepayInd(String repayInd) {
		this.repayInd = repayInd;
	}

	public String getAutoPayInd() {
		return this.autoPayInd;
	}

	public void setAutoPayInd(String autoPayInd) {
		this.autoPayInd = autoPayInd;
	}

	public String getPaySeqCode() {
		return this.paySeqCode;
	}

	public void setPaySeqCode(String paySeqCode) {
		this.paySeqCode = paySeqCode;
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

	public String getOverDcCode() {
		return this.overDcCode;
	}

	public void setOverDcCode(String overDcCode) {
		this.overDcCode = overDcCode;
	}

	public String getSlaDcCode() {
		return this.slaDcCode;
	}

	public void setSlaDcCode(String slaDcCode) {
		this.slaDcCode = slaDcCode;
	}

	public String getBadDcCode() {
		return this.badDcCode;
	}

	public void setBadDcCode(String badDcCode) {
		this.badDcCode = badDcCode;
	}

	public String getOver90DcCode() {
		return this.over90DcCode;
	}

	public void setOver90DcCode(String over90DcCode) {
		this.over90DcCode = over90DcCode;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

}