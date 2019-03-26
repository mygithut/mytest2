package com.dhcc.ftp.entity;


public class FtpBusinessInfo implements java.io.Serializable {

	private String brNo;
	private String brName;
	private String tel;//����Ա
	private String curNo;
	private String curName;
	private String acId;
	private String acSeqn;//acId+acSeqn Ψһ��ʶ
    private String customName;//�ͻ���
	private String prdtNo;
	private String prdtName;
	private String businessNo;
	private String businessName;
	private String opnDate;
	private String amt;
	private String bal;
	private String rate;
	private String term;
	private String mtrDate;
	private String interval;//���
	private Double ftpPrice;//���۽��
	private String methodNo;//���۷���
	private String methodName;//���۷���
	private String curveNo;//������
	private String curveName;//������
	private String wrkDate;//��������
	private String isZq;//�Ƿ�չ�ڴ���
	private String kmh;//��Ŀ��
	private String khjl;//�ͻ�����
	private String sjsflx;//ʵ����/����Ϣ
	private Double totalProfit;//�ܾ�������
	private Double curYearProfit;//���꾭������
	private String hkTerm;//��������
	private String zjTerm;//�۾�����
	private String scrapValueRate;//��ֵ��
	private String custNo;//�ͻ����
	private String zhzt;//�˻�״̬
	private String fivSts;//�弶����״̬
	private String zqrq;//չ������
	private String zqAmt;//չ�ڽ��
	private String zqMtrDate;
	private String flowType;
	// Constructors

	/** default constructor */
	public FtpBusinessInfo() {
		
	}
	public FtpBusinessInfo(String acId) {
		this.acId = acId;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCurNo() {
		return curNo;
	}
	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}
	public String getAcId() {
		return acId;
	}
	public void setAcId(String acId) {
		this.acId = acId;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getPrdtNo() {
		return prdtNo;
	}
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getOpnDate() {
		return opnDate;
	}
	public void setOpnDate(String opnDate) {
		this.opnDate = opnDate;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getMtrDate() {
		return mtrDate;
	}
	public void setMtrDate(String mtrDate) {
		this.mtrDate = mtrDate;
	}
	public Double getFtpPrice() {
		return ftpPrice;
	}
	public void setFtpPrice(Double ftpPrice) {
		this.ftpPrice = ftpPrice;
	}
	public String getCurveName() {
		return curveName;
	}
	public void setCurveName(String curveName) {
		this.curveName = curveName;
	}
	public String getWrkDate() {
		return wrkDate;
	}
	public void setWrkDate(String wrkDate) {
		this.wrkDate = wrkDate;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getCurName() {
		return curName;
	}
	public void setCurName(String curName) {
		this.curName = curName;
	}
	public String getPrdtName() {
		return prdtName;
	}
	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(String methodNo) {
		this.methodNo = methodNo;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getCurveNo() {
		return curveNo;
	}
	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getAcSeqn() {
		return acSeqn;
	}
	public void setAcSeqn(String acSeqn) {
		this.acSeqn = acSeqn;
	}
	public String getIsZq() {
		return isZq;
	}
	public void setIsZq(String isZq) {
		this.isZq = isZq;
	}
	public String getKmh() {
		return kmh;
	}
	public void setKmh(String kmh) {
		this.kmh = kmh;
	}
	public String getKhjl() {
		return khjl;
	}
	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}
	public String getSjsflx() {
		return sjsflx;
	}
	public void setSjsflx(String sjsflx) {
		this.sjsflx = sjsflx;
	}
	public Double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}
	public Double getCurYearProfit() {
		return curYearProfit;
	}
	public void setCurYearProfit(Double curYearProfit) {
		this.curYearProfit = curYearProfit;
	}
	public String getHkTerm() {
		return hkTerm;
	}
	public void setHkTerm(String hkTerm) {
		this.hkTerm = hkTerm;
	}
	public String getZjTerm() {
		return zjTerm;
	}
	public void setZjTerm(String zjTerm) {
		this.zjTerm = zjTerm;
	}
	public String getScrapValueRate() {
		return scrapValueRate;
	}
	public void setScrapValueRate(String scrapValueRate) {
		this.scrapValueRate = scrapValueRate;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getZhzt() {
		return zhzt;
	}
	public void setZhzt(String zhzt) {
		this.zhzt = zhzt;
	}
	public String getFivSts() {
		return fivSts;
	}
	public void setFivSts(String fivSts) {
		this.fivSts = fivSts;
	}
	public String getZqrq() {
		return zqrq;
	}
	public void setZqrq(String zqrq) {
		this.zqrq = zqrq;
	}
	public String getZqAmt() {
		return zqAmt;
	}
	public void setZqAmt(String zqAmt) {
		this.zqAmt = zqAmt;
	}
	public String getZqMtrDate() {
		return zqMtrDate;
	}
	public void setZqMtrDate(String zqMtrDate) {
		this.zqMtrDate = zqMtrDate;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	
}