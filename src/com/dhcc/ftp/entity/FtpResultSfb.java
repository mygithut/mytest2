package com.dhcc.ftp.entity;




public class FtpResultSfb  implements java.io.Serializable {

    private String resultId;
     private String prcMode;
     private String prcMethod;
     private Double resultPrice;
     private Double afresValue;
     private AlmCur almCur;
     private BrMst brMst;
     private String poolNo;
     private String memo;
     private String resDate;

    public FtpResultSfb() {
    }
    public FtpResultSfb(String resultId) {
        this.resultId = resultId;
    }
    public FtpResultSfb(String resultId,String prcMode,String prcMethod,Double resultPrice,Double afresValue,AlmCur almCur,BrMst brMst,String poolNo,String memo, String resDate) {
    	this.resultId=resultId;
        this.prcMode = prcMode;
        this.prcMethod = prcMethod;
        this.resultPrice = resultPrice;
        this.afresValue = afresValue;
        this.almCur=almCur;
        this.brMst=brMst;
        this.poolNo=poolNo;
        this.memo=memo;
        this.resDate=resDate;
    }
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getPrcMode() {
		return prcMode;
	}
	public void setPrcMode(String prcMode) {
		this.prcMode = prcMode;
	}
	public String getPrcMethod() {
		return prcMethod;
	}
	public void setPrcMethod(String prcMethod) {
		this.prcMethod = prcMethod;
	}
	public Double getAfresValue() {
		return afresValue;
	}
	public void setAfresValue(Double afresValue) {
		this.afresValue = afresValue;
	}
	public AlmCur getAlmCur() {
		return almCur;
	}
	public void setAlmCur(AlmCur almCur) {
		this.almCur = almCur;
	}
	public BrMst getBrMst() {
		return brMst;
	}
	public void setBrMst(BrMst brMst) {
		this.brMst = brMst;
	}
	public String getPoolNo() {
		return poolNo;
	}
	public void setPoolNo(String poolNo) {
		this.poolNo = poolNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getResDate() {
		return resDate;
	}
	public void setResDate(String resDate) {
		this.resDate = resDate;
	}
	public Double getResultPrice() {
		return resultPrice;
	}
	public void setResultPrice(Double resultPrice) {
		this.resultPrice = resultPrice;
	}

   
}