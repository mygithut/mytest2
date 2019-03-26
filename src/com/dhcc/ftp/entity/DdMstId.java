package com.dhcc.ftp.entity;



/**
 * DdMstId entity. @author MyEclipse Persistence Tools
 */

public class DdMstId  implements java.io.Serializable {


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
     private String cashInd;
     private String sttlType;
     private String acType;
     private String intstType;
     private Integer hstCnt;
     private Integer hstPg;
     private Integer opnDate;
     private Integer icDate;
     private Integer lstDate;
     private Integer valDate;
     private String odttInd;
     private String acSts;
     private String holdSts;
     private Double holdAmt;
     private Double ctlAmt;
     private String odInd;
     private String drawPwd;
     private Integer cifNo;
     private String name;
     private String calCode;
     private String mac;
     private Double zfBal;
     private Integer jxDate;
     private Double mimAcm;
     private Double perCtlAmt;


    // Constructors

    /** default constructor */
    public DdMstId() {
    }

    
    /** full constructor */
    public DdMstId(Integer acId, Integer acSeqn, String opnBrNo, String prdtNo, Double bal, Double acboBal, Double ysBal, Double hstBal, Double rate, Double fltRatio, Double intstAcm, String cashInd, String sttlType, String acType, String intstType, Integer hstCnt, Integer hstPg, Integer opnDate, Integer icDate, Integer lstDate, Integer valDate, String odttInd, String acSts, String holdSts, Double holdAmt, Double ctlAmt, String odInd, String drawPwd, Integer cifNo, String name, String calCode, String mac, Double zfBal, Integer jxDate, Double mimAcm, Double perCtlAmt) {
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
        this.cashInd = cashInd;
        this.sttlType = sttlType;
        this.acType = acType;
        this.intstType = intstType;
        this.hstCnt = hstCnt;
        this.hstPg = hstPg;
        this.opnDate = opnDate;
        this.icDate = icDate;
        this.lstDate = lstDate;
        this.valDate = valDate;
        this.odttInd = odttInd;
        this.acSts = acSts;
        this.holdSts = holdSts;
        this.holdAmt = holdAmt;
        this.ctlAmt = ctlAmt;
        this.odInd = odInd;
        this.drawPwd = drawPwd;
        this.cifNo = cifNo;
        this.name = name;
        this.calCode = calCode;
        this.mac = mac;
        this.zfBal = zfBal;
        this.jxDate = jxDate;
        this.mimAcm = mimAcm;
        this.perCtlAmt = perCtlAmt;
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

    public String getCashInd() {
        return this.cashInd;
    }
    
    public void setCashInd(String cashInd) {
        this.cashInd = cashInd;
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

    public String getIntstType() {
        return this.intstType;
    }
    
    public void setIntstType(String intstType) {
        this.intstType = intstType;
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

    public Integer getLstDate() {
        return this.lstDate;
    }
    
    public void setLstDate(Integer lstDate) {
        this.lstDate = lstDate;
    }

    public Integer getValDate() {
        return this.valDate;
    }
    
    public void setValDate(Integer valDate) {
        this.valDate = valDate;
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

    public String getOdInd() {
        return this.odInd;
    }
    
    public void setOdInd(String odInd) {
        this.odInd = odInd;
    }

    public String getDrawPwd() {
        return this.drawPwd;
    }
    
    public void setDrawPwd(String drawPwd) {
        this.drawPwd = drawPwd;
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

    public String getMac() {
        return this.mac;
    }
    
    public void setMac(String mac) {
        this.mac = mac;
    }

    public Double getZfBal() {
        return this.zfBal;
    }
    
    public void setZfBal(Double zfBal) {
        this.zfBal = zfBal;
    }

    public Integer getJxDate() {
        return this.jxDate;
    }
    
    public void setJxDate(Integer jxDate) {
        this.jxDate = jxDate;
    }

    public Double getMimAcm() {
        return this.mimAcm;
    }
    
    public void setMimAcm(Double mimAcm) {
        this.mimAcm = mimAcm;
    }

    public Double getPerCtlAmt() {
        return this.perCtlAmt;
    }
    
    public void setPerCtlAmt(Double perCtlAmt) {
        this.perCtlAmt = perCtlAmt;
    }
   








}