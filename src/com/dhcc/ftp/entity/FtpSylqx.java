package com.dhcc.ftp.entity;

import java.util.Date;



public class FtpSylqx  implements java.io.Serializable {

    private Integer curveId;
	private String curveNo;
    private String curveName;
    private String curveType;
    private String curveLlk;
    private String Remark;
    private String curNo;
    private String gjllmc;
    private Double gjllz;
    private String curveMode;
    private Date curveDate;
    private Double curveCkzbjl;
    private Double curveCkzbjll;
    public FtpSylqx() {
    }
    public FtpSylqx(Integer curveId) {
        this.curveId = curveId;
    }

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public String getCurveNo() {
		return curveNo;
	}

	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}

	public String getCurveName() {
		return curveName;
	}

	public void setCurveName(String curveName) {
		this.curveName = curveName;
	}

	public String getCurveType() {
		return curveType;
	}

	public void setCurveType(String curveType) {
		this.curveType = curveType;
	}

	public String getCurveLlk() {
		return curveLlk;
	}

	public void setCurveLlk(String curveLlk) {
		this.curveLlk = curveLlk;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getCurNo() {
		return curNo;
	}
	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}
	public String getGjllmc() {
		return gjllmc;
	}
	public void setGjllmc(String gjllmc) {
		this.gjllmc = gjllmc;
	}
	public Double getGjllz() {
		return gjllz;
	}
	public void setGjllz(Double gjllz) {
		this.gjllz = gjllz;
	}
	public String getCurveMode() {
		return curveMode;
	}
	public void setCurveMode(String curveMode) {
		this.curveMode = curveMode;
	}
	public Date getCurveDate() {
		return curveDate;
	}
	public void setCurveDate(Date curveDate) {
		this.curveDate = curveDate;
	}
	public Double getCurveCkzbjl() {
		return curveCkzbjl;
	}
	public void setCurveCkzbjl(Double curveCkzbjl) {
		this.curveCkzbjl = curveCkzbjl;
	}
	public Double getCurveCkzbjll() {
		return curveCkzbjll;
	}
	public void setCurveCkzbjll(Double curveCkzbjll) {
		this.curveCkzbjll = curveCkzbjll;
	}
	
}