package com.dhcc.ftp.entity;



/**
 * LnMst entity. @author MyEclipse Persistence Tools
 */

public class LnMst  implements java.io.Serializable {


    // Fields    

     private LnMstId id;
     private String opnBrNo;
     private String prdtNo;
     private Double bal;
     private Double ysBal;
     private Double hstBal;
     private Double amtLmt;
     private Double ttlPrvdAmt;
     private Double rate;
     private Double rateFlt;
     private Double overRate;
     private Double overRateFlt;
     private Double fineRate;
     private Double fineRateFlt;
     private Double cmpdRate;
     private Double intstAcm;
     private Double inLoIntst;
     private Double inLoAcm;
     private Double outLoIntst;
     private Double outLoAcm;
     private Double cmpdLoIntst;
     private Double cmpdLoAcm;
     private String calCode;
     private Integer icDate;
     private Integer opnDate;
     private Integer mtrDate;
     private Integer lstDate;
     private String intstType;
     private String repayType;
     private String cmpdIntstInd;
     private Integer hstCnt;
     private Integer hstPg;
     private Integer repayAcId;
     private Integer saveAcId;
     private String expInd;
     private Double expRate;
     private Integer expMtrDate;
     private String acSts;
     private String fiveSts;
     private Integer cifNo;
     private String name;
     private String pactNo;
     private String trustNo;
     private Double advisAmtPln;
     private Double advisAmtFac;
     private Double gageAmt;
     private Double backBal;
     private String jzFlag;
     private Double jzAmt;
     private Short jzTerm;
     private Double actRate;
     private Double actBal;
     private String jzZbType;
     private Double jzZbPer;
     private Double jzZbAmt;
     private Integer jzDate;
     private String jzZbFlag;
     private String jzTestType;
     private String groupId;
     private Double turnJzIntst;
     private Double convertBal;
     private Double overflowBal;
     private Double jzByIntst;
     private Double jzByBal;
     private Double jzLostCrAmt;
     private Double jzLostDrAmt;
     private Double jzCrAmt;
     private Double jzIntstCrAmt;
     private Double jzBqHke;
     private Double jzBal;
     private String mac;
     private String fltFlag;
     private Double fltBl;
     private Short begcnt;
     private Short interval;
     private Double conamt;
     private Double bl;
     private String sailFlag;
     private Integer sailDate;
     private Double zfBal;
     private String fstPayInd;
     private Integer fstPayDay;
     private Integer graduDate;
     private Integer outId;
     private Integer graceDay;
     private String contractNo;
     private String curNo;
     private Double deductBegAmt;
     private Integer deductDay;
     private Double deductMinAmt;
     private Integer rateTerm;
     private String accNo;
     private String lnType;
     private String bailAcNo;
     private String rateNo;
     private Integer intstDate;
     private String repayBrNo;


    // Constructors

    /** default constructor */
    public LnMst() {
    }

	/** minimal constructor */
    public LnMst(LnMstId id) {
        this.id = id;
    }
    
    /** full constructor */
    public LnMst(LnMstId id, String opnBrNo, String prdtNo, Double bal, Double ysBal, Double hstBal, Double amtLmt, Double ttlPrvdAmt, Double rate, Double rateFlt, Double overRate, Double overRateFlt, Double fineRate, Double fineRateFlt, Double cmpdRate, Double intstAcm, Double inLoIntst, Double inLoAcm, Double outLoIntst, Double outLoAcm, Double cmpdLoIntst, Double cmpdLoAcm, String calCode, Integer icDate, Integer opnDate, Integer mtrDate, Integer lstDate, String intstType, String repayType, String cmpdIntstInd, Integer hstCnt, Integer hstPg, Integer repayAcId, Integer saveAcId, String expInd, Double expRate, Integer expMtrDate, String acSts, String fiveSts, Integer cifNo, String name, String pactNo, String trustNo, Double advisAmtPln, Double advisAmtFac, Double gageAmt, Double backBal, String jzFlag, Double jzAmt, Short jzTerm, Double actRate, Double actBal, String jzZbType, Double jzZbPer, Double jzZbAmt, Integer jzDate, String jzZbFlag, String jzTestType, String groupId, Double turnJzIntst, Double convertBal, Double overflowBal, Double jzByIntst, Double jzByBal, Double jzLostCrAmt, Double jzLostDrAmt, Double jzCrAmt, Double jzIntstCrAmt, Double jzBqHke, Double jzBal, String mac, String fltFlag, Double fltBl, Short begcnt, Short interval, Double conamt, Double bl, String sailFlag, Integer sailDate, Double zfBal, String fstPayInd, Integer fstPayDay, Integer graduDate, Integer outId, Integer graceDay, String contractNo, String curNo, Double deductBegAmt, Integer deductDay, Double deductMinAmt, Integer rateTerm, String accNo, String lnType, String bailAcNo, String rateNo, Integer intstDate, String repayBrNo) {
        this.id = id;
        this.opnBrNo = opnBrNo;
        this.prdtNo = prdtNo;
        this.bal = bal;
        this.ysBal = ysBal;
        this.hstBal = hstBal;
        this.amtLmt = amtLmt;
        this.ttlPrvdAmt = ttlPrvdAmt;
        this.rate = rate;
        this.rateFlt = rateFlt;
        this.overRate = overRate;
        this.overRateFlt = overRateFlt;
        this.fineRate = fineRate;
        this.fineRateFlt = fineRateFlt;
        this.cmpdRate = cmpdRate;
        this.intstAcm = intstAcm;
        this.inLoIntst = inLoIntst;
        this.inLoAcm = inLoAcm;
        this.outLoIntst = outLoIntst;
        this.outLoAcm = outLoAcm;
        this.cmpdLoIntst = cmpdLoIntst;
        this.cmpdLoAcm = cmpdLoAcm;
        this.calCode = calCode;
        this.icDate = icDate;
        this.opnDate = opnDate;
        this.mtrDate = mtrDate;
        this.lstDate = lstDate;
        this.intstType = intstType;
        this.repayType = repayType;
        this.cmpdIntstInd = cmpdIntstInd;
        this.hstCnt = hstCnt;
        this.hstPg = hstPg;
        this.repayAcId = repayAcId;
        this.saveAcId = saveAcId;
        this.expInd = expInd;
        this.expRate = expRate;
        this.expMtrDate = expMtrDate;
        this.acSts = acSts;
        this.fiveSts = fiveSts;
        this.cifNo = cifNo;
        this.name = name;
        this.pactNo = pactNo;
        this.trustNo = trustNo;
        this.advisAmtPln = advisAmtPln;
        this.advisAmtFac = advisAmtFac;
        this.gageAmt = gageAmt;
        this.backBal = backBal;
        this.jzFlag = jzFlag;
        this.jzAmt = jzAmt;
        this.jzTerm = jzTerm;
        this.actRate = actRate;
        this.actBal = actBal;
        this.jzZbType = jzZbType;
        this.jzZbPer = jzZbPer;
        this.jzZbAmt = jzZbAmt;
        this.jzDate = jzDate;
        this.jzZbFlag = jzZbFlag;
        this.jzTestType = jzTestType;
        this.groupId = groupId;
        this.turnJzIntst = turnJzIntst;
        this.convertBal = convertBal;
        this.overflowBal = overflowBal;
        this.jzByIntst = jzByIntst;
        this.jzByBal = jzByBal;
        this.jzLostCrAmt = jzLostCrAmt;
        this.jzLostDrAmt = jzLostDrAmt;
        this.jzCrAmt = jzCrAmt;
        this.jzIntstCrAmt = jzIntstCrAmt;
        this.jzBqHke = jzBqHke;
        this.jzBal = jzBal;
        this.mac = mac;
        this.fltFlag = fltFlag;
        this.fltBl = fltBl;
        this.begcnt = begcnt;
        this.interval = interval;
        this.conamt = conamt;
        this.bl = bl;
        this.sailFlag = sailFlag;
        this.sailDate = sailDate;
        this.zfBal = zfBal;
        this.fstPayInd = fstPayInd;
        this.fstPayDay = fstPayDay;
        this.graduDate = graduDate;
        this.outId = outId;
        this.graceDay = graceDay;
        this.contractNo = contractNo;
        this.curNo = curNo;
        this.deductBegAmt = deductBegAmt;
        this.deductDay = deductDay;
        this.deductMinAmt = deductMinAmt;
        this.rateTerm = rateTerm;
        this.accNo = accNo;
        this.lnType = lnType;
        this.bailAcNo = bailAcNo;
        this.rateNo = rateNo;
        this.intstDate = intstDate;
        this.repayBrNo = repayBrNo;
    }

   
    // Property accessors

    public LnMstId getId() {
        return this.id;
    }
    
    public void setId(LnMstId id) {
        this.id = id;
    }

    public String getOpnBrNo() {
        return this.opnBrNo;
    }
    
    public void setOpnBrNo(String opnBrNo) {
        this.opnBrNo = opnBrNo;
    }

    public String getPrdtNo() {
        return this.prdtNo;
    }
    
    public void setPrdtNo(String prdtNo) {
        this.prdtNo = prdtNo;
    }

    public Double getBal() {
        return this.bal;
    }
    
    public void setBal(Double bal) {
        this.bal = bal;
    }

    public Double getYsBal() {
        return this.ysBal;
    }
    
    public void setYsBal(Double ysBal) {
        this.ysBal = ysBal;
    }

    public Double getHstBal() {
        return this.hstBal;
    }
    
    public void setHstBal(Double hstBal) {
        this.hstBal = hstBal;
    }

    public Double getAmtLmt() {
        return this.amtLmt;
    }
    
    public void setAmtLmt(Double amtLmt) {
        this.amtLmt = amtLmt;
    }

    public Double getTtlPrvdAmt() {
        return this.ttlPrvdAmt;
    }
    
    public void setTtlPrvdAmt(Double ttlPrvdAmt) {
        this.ttlPrvdAmt = ttlPrvdAmt;
    }

    public Double getRate() {
        return this.rate;
    }
    
    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRateFlt() {
        return this.rateFlt;
    }
    
    public void setRateFlt(Double rateFlt) {
        this.rateFlt = rateFlt;
    }

    public Double getOverRate() {
        return this.overRate;
    }
    
    public void setOverRate(Double overRate) {
        this.overRate = overRate;
    }

    public Double getOverRateFlt() {
        return this.overRateFlt;
    }
    
    public void setOverRateFlt(Double overRateFlt) {
        this.overRateFlt = overRateFlt;
    }

    public Double getFineRate() {
        return this.fineRate;
    }
    
    public void setFineRate(Double fineRate) {
        this.fineRate = fineRate;
    }

    public Double getFineRateFlt() {
        return this.fineRateFlt;
    }
    
    public void setFineRateFlt(Double fineRateFlt) {
        this.fineRateFlt = fineRateFlt;
    }

    public Double getCmpdRate() {
        return this.cmpdRate;
    }
    
    public void setCmpdRate(Double cmpdRate) {
        this.cmpdRate = cmpdRate;
    }

    public Double getIntstAcm() {
        return this.intstAcm;
    }
    
    public void setIntstAcm(Double intstAcm) {
        this.intstAcm = intstAcm;
    }

    public Double getInLoIntst() {
        return this.inLoIntst;
    }
    
    public void setInLoIntst(Double inLoIntst) {
        this.inLoIntst = inLoIntst;
    }

    public Double getInLoAcm() {
        return this.inLoAcm;
    }
    
    public void setInLoAcm(Double inLoAcm) {
        this.inLoAcm = inLoAcm;
    }

    public Double getOutLoIntst() {
        return this.outLoIntst;
    }
    
    public void setOutLoIntst(Double outLoIntst) {
        this.outLoIntst = outLoIntst;
    }

    public Double getOutLoAcm() {
        return this.outLoAcm;
    }
    
    public void setOutLoAcm(Double outLoAcm) {
        this.outLoAcm = outLoAcm;
    }

    public Double getCmpdLoIntst() {
        return this.cmpdLoIntst;
    }
    
    public void setCmpdLoIntst(Double cmpdLoIntst) {
        this.cmpdLoIntst = cmpdLoIntst;
    }

    public Double getCmpdLoAcm() {
        return this.cmpdLoAcm;
    }
    
    public void setCmpdLoAcm(Double cmpdLoAcm) {
        this.cmpdLoAcm = cmpdLoAcm;
    }

    public String getCalCode() {
        return this.calCode;
    }
    
    public void setCalCode(String calCode) {
        this.calCode = calCode;
    }

    public Integer getIcDate() {
        return this.icDate;
    }
    
    public void setIcDate(Integer icDate) {
        this.icDate = icDate;
    }

    public Integer getOpnDate() {
        return this.opnDate;
    }
    
    public void setOpnDate(Integer opnDate) {
        this.opnDate = opnDate;
    }

    public Integer getMtrDate() {
        return this.mtrDate;
    }
    
    public void setMtrDate(Integer mtrDate) {
        this.mtrDate = mtrDate;
    }

    public Integer getLstDate() {
        return this.lstDate;
    }
    
    public void setLstDate(Integer lstDate) {
        this.lstDate = lstDate;
    }

    public String getIntstType() {
        return this.intstType;
    }
    
    public void setIntstType(String intstType) {
        this.intstType = intstType;
    }

    public String getRepayType() {
        return this.repayType;
    }
    
    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getCmpdIntstInd() {
        return this.cmpdIntstInd;
    }
    
    public void setCmpdIntstInd(String cmpdIntstInd) {
        this.cmpdIntstInd = cmpdIntstInd;
    }

    public Integer getHstCnt() {
        return this.hstCnt;
    }
    
    public void setHstCnt(Integer hstCnt) {
        this.hstCnt = hstCnt;
    }

    public Integer getHstPg() {
        return this.hstPg;
    }
    
    public void setHstPg(Integer hstPg) {
        this.hstPg = hstPg;
    }

    public Integer getRepayAcId() {
        return this.repayAcId;
    }
    
    public void setRepayAcId(Integer repayAcId) {
        this.repayAcId = repayAcId;
    }

    public Integer getSaveAcId() {
        return this.saveAcId;
    }
    
    public void setSaveAcId(Integer saveAcId) {
        this.saveAcId = saveAcId;
    }

    public String getExpInd() {
        return this.expInd;
    }
    
    public void setExpInd(String expInd) {
        this.expInd = expInd;
    }

    public Double getExpRate() {
        return this.expRate;
    }
    
    public void setExpRate(Double expRate) {
        this.expRate = expRate;
    }

    public Integer getExpMtrDate() {
        return this.expMtrDate;
    }
    
    public void setExpMtrDate(Integer expMtrDate) {
        this.expMtrDate = expMtrDate;
    }

    public String getAcSts() {
        return this.acSts;
    }
    
    public void setAcSts(String acSts) {
        this.acSts = acSts;
    }

    public String getFiveSts() {
        return this.fiveSts;
    }
    
    public void setFiveSts(String fiveSts) {
        this.fiveSts = fiveSts;
    }

    public Integer getCifNo() {
        return this.cifNo;
    }
    
    public void setCifNo(Integer cifNo) {
        this.cifNo = cifNo;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getPactNo() {
        return this.pactNo;
    }
    
    public void setPactNo(String pactNo) {
        this.pactNo = pactNo;
    }

    public String getTrustNo() {
        return this.trustNo;
    }
    
    public void setTrustNo(String trustNo) {
        this.trustNo = trustNo;
    }

    public Double getAdvisAmtPln() {
        return this.advisAmtPln;
    }
    
    public void setAdvisAmtPln(Double advisAmtPln) {
        this.advisAmtPln = advisAmtPln;
    }

    public Double getAdvisAmtFac() {
        return this.advisAmtFac;
    }
    
    public void setAdvisAmtFac(Double advisAmtFac) {
        this.advisAmtFac = advisAmtFac;
    }

    public Double getGageAmt() {
        return this.gageAmt;
    }
    
    public void setGageAmt(Double gageAmt) {
        this.gageAmt = gageAmt;
    }

    public Double getBackBal() {
        return this.backBal;
    }
    
    public void setBackBal(Double backBal) {
        this.backBal = backBal;
    }

    public String getJzFlag() {
        return this.jzFlag;
    }
    
    public void setJzFlag(String jzFlag) {
        this.jzFlag = jzFlag;
    }

    public Double getJzAmt() {
        return this.jzAmt;
    }
    
    public void setJzAmt(Double jzAmt) {
        this.jzAmt = jzAmt;
    }

    public Short getJzTerm() {
        return this.jzTerm;
    }
    
    public void setJzTerm(Short jzTerm) {
        this.jzTerm = jzTerm;
    }

    public Double getActRate() {
        return this.actRate;
    }
    
    public void setActRate(Double actRate) {
        this.actRate = actRate;
    }

    public Double getActBal() {
        return this.actBal;
    }
    
    public void setActBal(Double actBal) {
        this.actBal = actBal;
    }

    public String getJzZbType() {
        return this.jzZbType;
    }
    
    public void setJzZbType(String jzZbType) {
        this.jzZbType = jzZbType;
    }

    public Double getJzZbPer() {
        return this.jzZbPer;
    }
    
    public void setJzZbPer(Double jzZbPer) {
        this.jzZbPer = jzZbPer;
    }

    public Double getJzZbAmt() {
        return this.jzZbAmt;
    }
    
    public void setJzZbAmt(Double jzZbAmt) {
        this.jzZbAmt = jzZbAmt;
    }

    public Integer getJzDate() {
        return this.jzDate;
    }
    
    public void setJzDate(Integer jzDate) {
        this.jzDate = jzDate;
    }

    public String getJzZbFlag() {
        return this.jzZbFlag;
    }
    
    public void setJzZbFlag(String jzZbFlag) {
        this.jzZbFlag = jzZbFlag;
    }

    public String getJzTestType() {
        return this.jzTestType;
    }
    
    public void setJzTestType(String jzTestType) {
        this.jzTestType = jzTestType;
    }

    public String getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Double getTurnJzIntst() {
        return this.turnJzIntst;
    }
    
    public void setTurnJzIntst(Double turnJzIntst) {
        this.turnJzIntst = turnJzIntst;
    }

    public Double getConvertBal() {
        return this.convertBal;
    }
    
    public void setConvertBal(Double convertBal) {
        this.convertBal = convertBal;
    }

    public Double getOverflowBal() {
        return this.overflowBal;
    }
    
    public void setOverflowBal(Double overflowBal) {
        this.overflowBal = overflowBal;
    }

    public Double getJzByIntst() {
        return this.jzByIntst;
    }
    
    public void setJzByIntst(Double jzByIntst) {
        this.jzByIntst = jzByIntst;
    }

    public Double getJzByBal() {
        return this.jzByBal;
    }
    
    public void setJzByBal(Double jzByBal) {
        this.jzByBal = jzByBal;
    }

    public Double getJzLostCrAmt() {
        return this.jzLostCrAmt;
    }
    
    public void setJzLostCrAmt(Double jzLostCrAmt) {
        this.jzLostCrAmt = jzLostCrAmt;
    }

    public Double getJzLostDrAmt() {
        return this.jzLostDrAmt;
    }
    
    public void setJzLostDrAmt(Double jzLostDrAmt) {
        this.jzLostDrAmt = jzLostDrAmt;
    }

    public Double getJzCrAmt() {
        return this.jzCrAmt;
    }
    
    public void setJzCrAmt(Double jzCrAmt) {
        this.jzCrAmt = jzCrAmt;
    }

    public Double getJzIntstCrAmt() {
        return this.jzIntstCrAmt;
    }
    
    public void setJzIntstCrAmt(Double jzIntstCrAmt) {
        this.jzIntstCrAmt = jzIntstCrAmt;
    }

    public Double getJzBqHke() {
        return this.jzBqHke;
    }
    
    public void setJzBqHke(Double jzBqHke) {
        this.jzBqHke = jzBqHke;
    }

    public Double getJzBal() {
        return this.jzBal;
    }
    
    public void setJzBal(Double jzBal) {
        this.jzBal = jzBal;
    }

    public String getMac() {
        return this.mac;
    }
    
    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getFltFlag() {
        return this.fltFlag;
    }
    
    public void setFltFlag(String fltFlag) {
        this.fltFlag = fltFlag;
    }

    public Double getFltBl() {
        return this.fltBl;
    }
    
    public void setFltBl(Double fltBl) {
        this.fltBl = fltBl;
    }

    public Short getBegcnt() {
        return this.begcnt;
    }
    
    public void setBegcnt(Short begcnt) {
        this.begcnt = begcnt;
    }

    public Short getInterval() {
        return this.interval;
    }
    
    public void setInterval(Short interval) {
        this.interval = interval;
    }

    public Double getConamt() {
        return this.conamt;
    }
    
    public void setConamt(Double conamt) {
        this.conamt = conamt;
    }

    public Double getBl() {
        return this.bl;
    }
    
    public void setBl(Double bl) {
        this.bl = bl;
    }

    public String getSailFlag() {
        return this.sailFlag;
    }
    
    public void setSailFlag(String sailFlag) {
        this.sailFlag = sailFlag;
    }

    public Integer getSailDate() {
        return this.sailDate;
    }
    
    public void setSailDate(Integer sailDate) {
        this.sailDate = sailDate;
    }

    public Double getZfBal() {
        return this.zfBal;
    }
    
    public void setZfBal(Double zfBal) {
        this.zfBal = zfBal;
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

    public Integer getGraduDate() {
        return this.graduDate;
    }
    
    public void setGraduDate(Integer graduDate) {
        this.graduDate = graduDate;
    }

    public Integer getOutId() {
        return this.outId;
    }
    
    public void setOutId(Integer outId) {
        this.outId = outId;
    }

    public Integer getGraceDay() {
        return this.graceDay;
    }
    
    public void setGraceDay(Integer graceDay) {
        this.graceDay = graceDay;
    }

    public String getContractNo() {
        return this.contractNo;
    }
    
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCurNo() {
        return this.curNo;
    }
    
    public void setCurNo(String curNo) {
        this.curNo = curNo;
    }

    public Double getDeductBegAmt() {
        return this.deductBegAmt;
    }
    
    public void setDeductBegAmt(Double deductBegAmt) {
        this.deductBegAmt = deductBegAmt;
    }

    public Integer getDeductDay() {
        return this.deductDay;
    }
    
    public void setDeductDay(Integer deductDay) {
        this.deductDay = deductDay;
    }

    public Double getDeductMinAmt() {
        return this.deductMinAmt;
    }
    
    public void setDeductMinAmt(Double deductMinAmt) {
        this.deductMinAmt = deductMinAmt;
    }

    public Integer getRateTerm() {
        return this.rateTerm;
    }
    
    public void setRateTerm(Integer rateTerm) {
        this.rateTerm = rateTerm;
    }

    public String getAccNo() {
        return this.accNo;
    }
    
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getLnType() {
        return this.lnType;
    }
    
    public void setLnType(String lnType) {
        this.lnType = lnType;
    }

    public String getBailAcNo() {
        return this.bailAcNo;
    }
    
    public void setBailAcNo(String bailAcNo) {
        this.bailAcNo = bailAcNo;
    }

    public String getRateNo() {
        return this.rateNo;
    }
    
    public void setRateNo(String rateNo) {
        this.rateNo = rateNo;
    }

    public Integer getIntstDate() {
        return this.intstDate;
    }
    
    public void setIntstDate(Integer intstDate) {
        this.intstDate = intstDate;
    }

    public String getRepayBrNo() {
        return this.repayBrNo;
    }
    
    public void setRepayBrNo(String repayBrNo) {
        this.repayBrNo = repayBrNo;
    }
   








}