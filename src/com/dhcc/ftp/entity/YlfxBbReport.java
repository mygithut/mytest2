package com.dhcc.ftp.entity;




public class YlfxBbReport  implements java.io.Serializable {
	private String brNo;
	private String brName;
	private String empNo;
	private String empName;
	private String manageLvl;
	private String staticNo;//ͳ�ƴ�����
	private String staticName;//ͳ�ƴ�������
	private String businessNo;//ҵ����
	private String businessName;//ҵ������
	private String productCtgNo;//��Ʒ������
	private String productCtgName;//��Ʒ��������
	private String prdtNo;
	private String prdtName;
	private String custNo;
	private String custName;
	private String acId;
	private String opnDate;
	private String mtrDate;
	private Double rate;
	private Double ftp;
	private Double rjye;
	private Double ftplr;
	private Double lc;
	private Double zcrjye;//�ʲ��վ����
	private Double fzrjye;//��ծ�վ����
    private Double zcftplr;//�ʲ�FTP����
    private Double fzftplr;//��ծFTP����
    private Double zclc;//�ʲ�����
    private Double fzlc;//��ծ����
    private Double ftplrhj;//����ϼ�
	private boolean isLeaf;//�����Ƿ�ΪҶ�ӽڵ�
	private String empBrNo;
	private String empBrName;
	private String empSuperBrNo;
	private String empSuperBrName;
	private Double bal;
	private Double zcbal;//�ʲ����
	private Double fzbal;//��ծ���
	private Double zcrate;//�ʲ�rate
	private Double fzrate;//��ծrate
	private Double zcftp;//�ʲ�ftp
	private Double fzftp;//��ծftp
	private Double grhqbal;//���˻��ڴ�����
	private Double grdqbal;//���˶��ڴ�����
	private Double yhkbal;//���п�������
	private Double dwhqbal;//��λ���ڴ�����
	private Double dwdqbal;//��λ���ڴ�����
	private Double czxbal;//�����Դ�������
	private Double yjhkbal;//Ӧ�������
	private Double bzjbal;//��֤�������

	private Double grhqrjye;//���˻��ڴ���վ����
	private Double grdqrjye;//���˶��ڴ���վ����
	private Double yhkrjye;//���п�����վ����
	private Double dwhqrjye;//��λ���ڴ���վ����
	private Double dwdqrjye;//��λ���ڴ���վ����
	private Double czxrjye;//�����Դ�����վ����
	private Double yjhkrjye;//Ӧ�����վ����
	private Double bzjrjye;//��֤�����վ����

	private Double grhqftplr;//���˻��ڴ��ftp����
	private Double grdqftplr;//���˶��ڴ��ftp����
	private Double yhkftplr;//���п����ftp����
	private Double dwhqftplr;//��λ���ڴ��ftp����
	private Double dwdqftplr;//��λ���ڴ��ftp����
	private Double czxftplr;//�����Դ����ftp����
	private Double yjhkftplr;//Ӧ����ftp����
	private Double bzjftplr;//��֤����ftp����

	private Double grdkftplr;//���˴���ftp����
	private Double gsdkftplr;//��˾����ftp����

	public Double getGrdkftplr() {
		return grdkftplr;
	}

	public void setGrdkftplr(Double grdkftplr) {
		this.grdkftplr = grdkftplr;
	}

	public Double getGsdkftplr() {
		return gsdkftplr;
	}

	public void setGsdkftplr(Double gsdkftplr) {
		this.gsdkftplr = gsdkftplr;
	}

	public Double getGrhqftplr() {
		return grhqftplr;
	}

	public void setGrhqftplr(Double grhqftplr) {
		this.grhqftplr = grhqftplr;
	}

	public Double getGrdqftplr() {
		return grdqftplr;
	}

	public void setGrdqftplr(Double grdqftplr) {
		this.grdqftplr = grdqftplr;
	}

	public Double getYhkftplr() {
		return yhkftplr;
	}

	public void setYhkftplr(Double yhkftplr) {
		this.yhkftplr = yhkftplr;
	}

	public Double getDwhqftplr() {
		return dwhqftplr;
	}

	public void setDwhqftplr(Double dwhqftplr) {
		this.dwhqftplr = dwhqftplr;
	}

	public Double getDwdqftplr() {
		return dwdqftplr;
	}

	public void setDwdqftplr(Double dwdqftplr) {
		this.dwdqftplr = dwdqftplr;
	}

	public Double getCzxftplr() {
		return czxftplr;
	}

	public void setCzxftplr(Double czxftplr) {
		this.czxftplr = czxftplr;
	}

	public Double getYjhkftplr() {
		return yjhkftplr;
	}

	public void setYjhkftplr(Double yjhkftplr) {
		this.yjhkftplr = yjhkftplr;
	}

	public Double getBzjftplr() {
		return bzjftplr;
	}

	public void setBzjftplr(Double bzjftplr) {
		this.bzjftplr = bzjftplr;
	}

	private Double dsftplr;//��˽ftp����
	private Double dgftplr;//�Թ�ftp����

	public Double getDsftplr() {
		return dsftplr;
	}

	public void setDsftplr(Double dsftplr) {
		this.dsftplr = dsftplr;
	}

	public Double getDgftplr() {
		return dgftplr;
	}

	public void setDgftplr(Double dgftplr) {
		this.dgftplr = dgftplr;
	}

	public Double getGrhqbal() {
		return grhqbal;
	}

	public void setGrhqbal(Double grhqbal) {
		this.grhqbal = grhqbal;
	}

	public Double getGrdqbal() {
		return grdqbal;
	}

	public void setGrdqbal(Double grdqbal) {
		this.grdqbal = grdqbal;
	}

	public Double getGrdqrjye() {
		return grdqrjye;
	}

	public void setGrdqrjye(Double grdqrjye) {
		this.grdqrjye = grdqrjye;
	}

	public Double getYhkbal() {
		return yhkbal;
	}

	public void setYhkbal(Double yhkbal) {
		this.yhkbal = yhkbal;
	}

	public Double getDwhqbal() {
		return dwhqbal;
	}

	public void setDwhqbal(Double dwhqbal) {
		this.dwhqbal = dwhqbal;
	}

	public Double getDwdqbal() {
		return dwdqbal;
	}

	public void setDwdqbal(Double dwdqbal) {
		this.dwdqbal = dwdqbal;
	}

	public Double getCzxbal() {
		return czxbal;
	}

	public void setCzxbal(Double czxbal) {
		this.czxbal = czxbal;
	}

	public Double getYjhkbal() {
		return yjhkbal;
	}

	public void setYjhkbal(Double yjhkbal) {
		this.yjhkbal = yjhkbal;
	}

	public Double getBzjbal() {
		return bzjbal;
	}

	public void setBzjbal(Double bzjbal) {
		this.bzjbal = bzjbal;
	}

	public Double getGrhqrjye() {
		return grhqrjye;
	}

	public void setGrhqrjye(Double grhqrjye) {
		this.grhqrjye = grhqrjye;
	}

	public Double getYhkrjye() {
		return yhkrjye;
	}

	public void setYhkrjye(Double yhkrjye) {
		this.yhkrjye = yhkrjye;
	}

	public Double getDwhqrjye() {
		return dwhqrjye;
	}

	public void setDwhqrjye(Double dwhqrjye) {
		this.dwhqrjye = dwhqrjye;
	}

	public Double getDwdqrjye() {
		return dwdqrjye;
	}

	public void setDwdqrjye(Double dwdqrjye) {
		this.dwdqrjye = dwdqrjye;
	}

	public Double getCzxrjye() {
		return czxrjye;
	}

	public void setCzxrjye(Double czxrjye) {
		this.czxrjye = czxrjye;
	}

	public Double getYjhkrjye() {
		return yjhkrjye;
	}

	public void setYjhkrjye(Double yjhkrjye) {
		this.yjhkrjye = yjhkrjye;
	}

	public Double getBzjrjye() {
		return bzjrjye;
	}

	public void setBzjrjye(Double bzjrjye) {
		this.bzjrjye = bzjrjye;
	}

	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getManageLvl() {
		return manageLvl;
	}
	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPrdtNo() {
		return prdtNo;
	}
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	public String getPrdtName() {
		return prdtName;
	}
	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}
	public Double getZcrjye() {
		return zcrjye;
	}
	public void setZcrjye(Double zcrjye) {
		this.zcrjye = zcrjye;
	}
	public Double getFzrjye() {
		return fzrjye;
	}
	public void setFzrjye(Double fzrjye) {
		this.fzrjye = fzrjye;
	}
	public Double getZcftplr() {
		return zcftplr;
	}
	public void setZcftplr(Double zcftplr) {
		this.zcftplr = zcftplr;
	}
	public Double getFzftplr() {
		return fzftplr;
	}
	public void setFzftplr(Double fzftplr) {
		this.fzftplr = fzftplr;
	}
	public Double getZclc() {
		return zclc;
	}
	public void setZclc(Double zclc) {
		this.zclc = zclc;
	}
	public Double getFzlc() {
		return fzlc;
	}
	public void setFzlc(Double fzlc) {
		this.fzlc = fzlc;
	}
	public Double getFtplrhj() {
		return ftplrhj;
	}
	public void setFtplrhj(Double ftplrhj) {
		this.ftplrhj = ftplrhj;
	}
	public String getStaticNo() {
		return staticNo;
	}
	public void setStaticNo(String staticNo) {
		this.staticNo = staticNo;
	}
	public String getStaticName() {
		return staticName;
	}
	public void setStaticName(String staticName) {
		this.staticName = staticName;
	}
	public Double getRjye() {
		return rjye;
	}
	public void setRjye(Double rjye) {
		this.rjye = rjye;
	}
	public Double getFtplr() {
		return ftplr;
	}
	public void setFtplr(Double ftplr) {
		this.ftplr = ftplr;
	}
	public Double getLc() {
		return lc;
	}
	public void setLc(Double lc) {
		this.lc = lc;
	}
	public String getProductCtgNo() {
		return productCtgNo;
	}
	public void setProductCtgNo(String productCtgNo) {
		this.productCtgNo = productCtgNo;
	}
	public String getProductCtgName() {
		return productCtgName;
	}
	public void setProductCtgName(String productCtgName) {
		this.productCtgName = productCtgName;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAcId() {
		return acId;
	}
	public void setAcId(String acId) {
		this.acId = acId;
	}
	public String getOpnDate() {
		return opnDate;
	}
	public void setOpnDate(String opnDate) {
		this.opnDate = opnDate;
	}
	public String getMtrDate() {
		return mtrDate;
	}
	public void setMtrDate(String mtrDate) {
		this.mtrDate = mtrDate;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getFtp() {
		return ftp;
	}
	public void setFtp(Double ftp) {
		this.ftp = ftp;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getEmpBrNo() {
		return empBrNo;
	}
	public void setEmpBrNo(String empBrNo) {
		this.empBrNo = empBrNo;
	}
	public String getEmpBrName() {
		return empBrName;
	}
	public void setEmpBrName(String empBrName) {
		this.empBrName = empBrName;
	}
	public String getEmpSuperBrNo() {
		return empSuperBrNo;
	}
	public void setEmpSuperBrNo(String empSuperBrNo) {
		this.empSuperBrNo = empSuperBrNo;
	}
	public String getEmpSuperBrName() {
		return empSuperBrName;
	}
	public void setEmpSuperBrName(String empSuperBrName) {
		this.empSuperBrName = empSuperBrName;
	}
	public Double getBal() {
		return bal;
	}
	public Double getZcbal() {
		return zcbal;
	}
	public Double getFzbal() {
		return fzbal;
	}
	public Double getZcrate() {
		return zcrate;
	}
	public Double getFzrate() {
		return fzrate;
	}
	public Double getZcftp() {
		return zcftp;
	}
	public Double getFzftp() {
		return fzftp;
	}
	public void setBal(Double bal) {
		this.bal = bal;
	}
	public void setZcbal(Double zcbal) {
		this.zcbal = zcbal;
	}
	public void setFzbal(Double fzbal) {
		this.fzbal = fzbal;
	}
	public void setZcrate(Double zcrate) {
		this.zcrate = zcrate;
	}
	public void setFzrate(Double fzrate) {
		this.fzrate = fzrate;
	}
	public void setZcftp(Double zcftp) {
		this.zcftp = zcftp;
	}
	public void setFzftp(Double fzftp) {
		this.fzftp = fzftp;
	}
	
}