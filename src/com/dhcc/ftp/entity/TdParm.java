package com.dhcc.ftp.entity;

/**
 * TdParm entity. @author MyEclipse Persistence Tools
 */

public class TdParm implements java.io.Serializable {

	// Fields

	private String prdtNo;
	private String title;
	private Integer begDate;
	private Integer endDate;
	private Integer term;
	private String termType;
	private String cifType;
	private String curNo;
	private String balType;
	private Double minBal;
	private Double maxBal;
	private Double minOpnAmt;
	private Double maxOpnAmt;
	private String tdType;
	private String rateNo;
	private String rateType;
	private String rateMode;
	private String rateFuncCode;
	private Double minFltRatio;
	private Double maxFltRatio;
	private String intstTermType;
	private String keepInd;
	private String ovrdIntstInd;
	private String ovrdRateNo;
	private String felbckRateNo;
	private String intstType;
	private String acmCalcMode;
	private Integer intstMon;
	private Integer intstDay;
	private String tfrLmtInd;
	private String tfrPrtInd;
	private String attermPrtInd;
	private String attermDateInd;
	private String intstTaxNo;
	private String cashInd;
	private String dpstInd;
	private Double dpstMinAmt;
	private Double drawMinAmt;
	private String wdrInd;
	private String fwdDrawInd;
	private Integer wdrLmtCnt;
	private String thrDpstInd;
	private String thrDrawInd;
	private String thrOpnInd;
	private String thrClsInd;
	private Integer dedrIntvl;
	private String intvlType;
	private String eduProInd;
	private String holiInd;
	private Integer opnLmt;
	private Integer clsLmt;
	private String dcCode;
	private String putInd;
	private String brNo;
	private Integer prtNo;

	// Constructors

	/** default constructor */
	public TdParm() {
	}

	/** minimal constructor */
	public TdParm(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	/** full constructor */
	public TdParm(String prdtNo, String title, Integer begDate,
			Integer endDate, Integer term, String termType, String cifType,
			String curNo, String balType, Double minBal, Double maxBal,
			Double minOpnAmt, Double maxOpnAmt, String tdType, String rateNo,
			String rateType, String rateMode, String rateFuncCode,
			Double minFltRatio, Double maxFltRatio, String intstTermType,
			String keepInd, String ovrdIntstInd, String ovrdRateNo,
			String felbckRateNo, String intstType, String acmCalcMode,
			Integer intstMon, Integer intstDay, String tfrLmtInd,
			String tfrPrtInd, String attermPrtInd, String attermDateInd,
			String intstTaxNo, String cashInd, String dpstInd,
			Double dpstMinAmt, Double drawMinAmt, String wdrInd,
			String fwdDrawInd, Integer wdrLmtCnt, String thrDpstInd,
			String thrDrawInd, String thrOpnInd, String thrClsInd,
			Integer dedrIntvl, String intvlType, String eduProInd,
			String holiInd, Integer opnLmt, Integer clsLmt, String dcCode,
			String putInd, String brNo, Integer prtNo) {
		this.prdtNo = prdtNo;
		this.title = title;
		this.begDate = begDate;
		this.endDate = endDate;
		this.term = term;
		this.termType = termType;
		this.cifType = cifType;
		this.curNo = curNo;
		this.balType = balType;
		this.minBal = minBal;
		this.maxBal = maxBal;
		this.minOpnAmt = minOpnAmt;
		this.maxOpnAmt = maxOpnAmt;
		this.tdType = tdType;
		this.rateNo = rateNo;
		this.rateType = rateType;
		this.rateMode = rateMode;
		this.rateFuncCode = rateFuncCode;
		this.minFltRatio = minFltRatio;
		this.maxFltRatio = maxFltRatio;
		this.intstTermType = intstTermType;
		this.keepInd = keepInd;
		this.ovrdIntstInd = ovrdIntstInd;
		this.ovrdRateNo = ovrdRateNo;
		this.felbckRateNo = felbckRateNo;
		this.intstType = intstType;
		this.acmCalcMode = acmCalcMode;
		this.intstMon = intstMon;
		this.intstDay = intstDay;
		this.tfrLmtInd = tfrLmtInd;
		this.tfrPrtInd = tfrPrtInd;
		this.attermPrtInd = attermPrtInd;
		this.attermDateInd = attermDateInd;
		this.intstTaxNo = intstTaxNo;
		this.cashInd = cashInd;
		this.dpstInd = dpstInd;
		this.dpstMinAmt = dpstMinAmt;
		this.drawMinAmt = drawMinAmt;
		this.wdrInd = wdrInd;
		this.fwdDrawInd = fwdDrawInd;
		this.wdrLmtCnt = wdrLmtCnt;
		this.thrDpstInd = thrDpstInd;
		this.thrDrawInd = thrDrawInd;
		this.thrOpnInd = thrOpnInd;
		this.thrClsInd = thrClsInd;
		this.dedrIntvl = dedrIntvl;
		this.intvlType = intvlType;
		this.eduProInd = eduProInd;
		this.holiInd = holiInd;
		this.opnLmt = opnLmt;
		this.clsLmt = clsLmt;
		this.dcCode = dcCode;
		this.putInd = putInd;
		this.brNo = brNo;
		this.prtNo = prtNo;
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

	public Integer getTerm() {
		return this.term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
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

	public String getTdType() {
		return this.tdType;
	}

	public void setTdType(String tdType) {
		this.tdType = tdType;
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

	public String getIntstTermType() {
		return this.intstTermType;
	}

	public void setIntstTermType(String intstTermType) {
		this.intstTermType = intstTermType;
	}

	public String getKeepInd() {
		return this.keepInd;
	}

	public void setKeepInd(String keepInd) {
		this.keepInd = keepInd;
	}

	public String getOvrdIntstInd() {
		return this.ovrdIntstInd;
	}

	public void setOvrdIntstInd(String ovrdIntstInd) {
		this.ovrdIntstInd = ovrdIntstInd;
	}

	public String getOvrdRateNo() {
		return this.ovrdRateNo;
	}

	public void setOvrdRateNo(String ovrdRateNo) {
		this.ovrdRateNo = ovrdRateNo;
	}

	public String getFelbckRateNo() {
		return this.felbckRateNo;
	}

	public void setFelbckRateNo(String felbckRateNo) {
		this.felbckRateNo = felbckRateNo;
	}

	public String getIntstType() {
		return this.intstType;
	}

	public void setIntstType(String intstType) {
		this.intstType = intstType;
	}

	public String getAcmCalcMode() {
		return this.acmCalcMode;
	}

	public void setAcmCalcMode(String acmCalcMode) {
		this.acmCalcMode = acmCalcMode;
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

	public String getTfrLmtInd() {
		return this.tfrLmtInd;
	}

	public void setTfrLmtInd(String tfrLmtInd) {
		this.tfrLmtInd = tfrLmtInd;
	}

	public String getTfrPrtInd() {
		return this.tfrPrtInd;
	}

	public void setTfrPrtInd(String tfrPrtInd) {
		this.tfrPrtInd = tfrPrtInd;
	}

	public String getAttermPrtInd() {
		return this.attermPrtInd;
	}

	public void setAttermPrtInd(String attermPrtInd) {
		this.attermPrtInd = attermPrtInd;
	}

	public String getAttermDateInd() {
		return this.attermDateInd;
	}

	public void setAttermDateInd(String attermDateInd) {
		this.attermDateInd = attermDateInd;
	}

	public String getIntstTaxNo() {
		return this.intstTaxNo;
	}

	public void setIntstTaxNo(String intstTaxNo) {
		this.intstTaxNo = intstTaxNo;
	}

	public String getCashInd() {
		return this.cashInd;
	}

	public void setCashInd(String cashInd) {
		this.cashInd = cashInd;
	}

	public String getDpstInd() {
		return this.dpstInd;
	}

	public void setDpstInd(String dpstInd) {
		this.dpstInd = dpstInd;
	}

	public Double getDpstMinAmt() {
		return this.dpstMinAmt;
	}

	public void setDpstMinAmt(Double dpstMinAmt) {
		this.dpstMinAmt = dpstMinAmt;
	}

	public Double getDrawMinAmt() {
		return this.drawMinAmt;
	}

	public void setDrawMinAmt(Double drawMinAmt) {
		this.drawMinAmt = drawMinAmt;
	}

	public String getWdrInd() {
		return this.wdrInd;
	}

	public void setWdrInd(String wdrInd) {
		this.wdrInd = wdrInd;
	}

	public String getFwdDrawInd() {
		return this.fwdDrawInd;
	}

	public void setFwdDrawInd(String fwdDrawInd) {
		this.fwdDrawInd = fwdDrawInd;
	}

	public Integer getWdrLmtCnt() {
		return this.wdrLmtCnt;
	}

	public void setWdrLmtCnt(Integer wdrLmtCnt) {
		this.wdrLmtCnt = wdrLmtCnt;
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

	public Integer getDedrIntvl() {
		return this.dedrIntvl;
	}

	public void setDedrIntvl(Integer dedrIntvl) {
		this.dedrIntvl = dedrIntvl;
	}

	public String getIntvlType() {
		return this.intvlType;
	}

	public void setIntvlType(String intvlType) {
		this.intvlType = intvlType;
	}

	public String getEduProInd() {
		return this.eduProInd;
	}

	public void setEduProInd(String eduProInd) {
		this.eduProInd = eduProInd;
	}

	public String getHoliInd() {
		return this.holiInd;
	}

	public void setHoliInd(String holiInd) {
		this.holiInd = holiInd;
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

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public Integer getPrtNo() {
		return this.prtNo;
	}

	public void setPrtNo(Integer prtNo) {
		this.prtNo = prtNo;
	}

}