package com.dhcc.ftp.entity;



/**
 * TdMstId entity. @author MyEclipse Persistence Tools
 */

public class TdMstId  implements java.io.Serializable {


    // Fields    

     private Integer acId;
     private Integer acSeqn;
     private String opnBrNo;
     private String prdtNo;
     private Double bal;
     private Double acboBal;
     private Double ysBal;
     private Double hstBal;
     private Double rate;
     private Double fltRatio;
     private Double intstAcm;
     private String intstType;
     private Integer opnDate;
     private Integer icDate;
     private Integer mtrDate;
     private Integer lstDate;
     private Double tdAmt;
     private Integer ttlCnt;
     private Integer txCnt;
     private Integer hstCnt;
     private String odttInd;
     private String acSts;
     private String holdSts;
     private Double holdAmt;
     private Double ctlAmt;
     private String tfrInd;
     private Integer cifNo;
     private String name;
     private String calCode;
     private Integer bookLine;
     private String mac;
     private String lxsFlag;
     private Double zfBal;


    // Constructors

    /** default constructor */
    public TdMstId() {
    }

    
    /** full constructor */
    public TdMstId(Integer acId, Integer acSeqn, String opnBrNo, String prdtNo, Double bal, Double acboBal, Double ysBal, Double hstBal, Double rate, Double fltRatio, Double intstAcm, String intstType, Integer opnDate, Integer icDate, Integer mtrDate, Integer lstDate, Double tdAmt, Integer ttlCnt, Integer txCnt, Integer hstCnt, String odttInd, String acSts, String holdSts, Double holdAmt, Double ctlAmt, String tfrInd, Integer cifNo, String name, String calCode, Integer bookLine, String mac, String lxsFlag, Double zfBal) {
        this.acId = acId;
        this.acSeqn = acSeqn;
        this.opnBrNo = opnBrNo;
        this.prdtNo = prdtNo;
        this.bal = bal;
        this.acboBal = acboBal;
        this.ysBal = ysBal;
        this.hstBal = hstBal;
        this.rate = rate;
        this.fltRatio = fltRatio;
        this.intstAcm = intstAcm;
        this.intstType = intstType;
        this.opnDate = opnDate;
        this.icDate = icDate;
        this.mtrDate = mtrDate;
        this.lstDate = lstDate;
        this.tdAmt = tdAmt;
        this.ttlCnt = ttlCnt;
        this.txCnt = txCnt;
        this.hstCnt = hstCnt;
        this.odttInd = odttInd;
        this.acSts = acSts;
        this.holdSts = holdSts;
        this.holdAmt = holdAmt;
        this.ctlAmt = ctlAmt;
        this.tfrInd = tfrInd;
        this.cifNo = cifNo;
        this.name = name;
        this.calCode = calCode;
        this.bookLine = bookLine;
        this.mac = mac;
        this.lxsFlag = lxsFlag;
        this.zfBal = zfBal;
    }

   
    // Property accessors

    public Integer getAcId() {
        return this.acId;
    }
    
    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public Integer getAcSeqn() {
        return this.acSeqn;
    }
    
    public void setAcSeqn(Integer acSeqn) {
        this.acSeqn = acSeqn;
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

    public Double getAcboBal() {
        return this.acboBal;
    }
    
    public void setAcboBal(Double acboBal) {
        this.acboBal = acboBal;
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

    public Double getRate() {
        return this.rate;
    }
    
    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getFltRatio() {
        return this.fltRatio;
    }
    
    public void setFltRatio(Double fltRatio) {
        this.fltRatio = fltRatio;
    }

    public Double getIntstAcm() {
        return this.intstAcm;
    }
    
    public void setIntstAcm(Double intstAcm) {
        this.intstAcm = intstAcm;
    }

    public String getIntstType() {
        return this.intstType;
    }
    
    public void setIntstType(String intstType) {
        this.intstType = intstType;
    }

    public Integer getOpnDate() {
        return this.opnDate;
    }
    
    public void setOpnDate(Integer opnDate) {
        this.opnDate = opnDate;
    }

    public Integer getIcDate() {
        return this.icDate;
    }
    
    public void setIcDate(Integer icDate) {
        this.icDate = icDate;
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

    public Double getTdAmt() {
        return this.tdAmt;
    }
    
    public void setTdAmt(Double tdAmt) {
        this.tdAmt = tdAmt;
    }

    public Integer getTtlCnt() {
        return this.ttlCnt;
    }
    
    public void setTtlCnt(Integer ttlCnt) {
        this.ttlCnt = ttlCnt;
    }

    public Integer getTxCnt() {
        return this.txCnt;
    }
    
    public void setTxCnt(Integer txCnt) {
        this.txCnt = txCnt;
    }

    public Integer getHstCnt() {
        return this.hstCnt;
    }
    
    public void setHstCnt(Integer hstCnt) {
        this.hstCnt = hstCnt;
    }

    public String getOdttInd() {
        return this.odttInd;
    }
    
    public void setOdttInd(String odttInd) {
        this.odttInd = odttInd;
    }

    public String getAcSts() {
        return this.acSts;
    }
    
    public void setAcSts(String acSts) {
        this.acSts = acSts;
    }

    public String getHoldSts() {
        return this.holdSts;
    }
    
    public void setHoldSts(String holdSts) {
        this.holdSts = holdSts;
    }

    public Double getHoldAmt() {
        return this.holdAmt;
    }
    
    public void setHoldAmt(Double holdAmt) {
        this.holdAmt = holdAmt;
    }

    public Double getCtlAmt() {
        return this.ctlAmt;
    }
    
    public void setCtlAmt(Double ctlAmt) {
        this.ctlAmt = ctlAmt;
    }

    public String getTfrInd() {
        return this.tfrInd;
    }
    
    public void setTfrInd(String tfrInd) {
        this.tfrInd = tfrInd;
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

    public String getCalCode() {
        return this.calCode;
    }
    
    public void setCalCode(String calCode) {
        this.calCode = calCode;
    }

    public Integer getBookLine() {
        return this.bookLine;
    }
    
    public void setBookLine(Integer bookLine) {
        this.bookLine = bookLine;
    }

    public String getMac() {
        return this.mac;
    }
    
    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLxsFlag() {
        return this.lxsFlag;
    }
    
    public void setLxsFlag(String lxsFlag) {
        this.lxsFlag = lxsFlag;
    }

    public Double getZfBal() {
        return this.zfBal;
    }
    
    public void setZfBal(Double zfBal) {
        this.zfBal = zfBal;
    }
   








}