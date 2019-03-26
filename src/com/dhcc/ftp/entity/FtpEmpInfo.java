package com.dhcc.ftp.entity;

/**
 * FtpEmpInfo entity. @author MyEclipse Persistence Tools
 */

public class FtpEmpInfo implements java.io.Serializable {

	// Fields

	private String empNo;
	private String empName;
	private String brNo;
	private String empLvl;
	private String postNo;
	private String birthdaydate;
	private String sex;
	private String empStatus;
	private String lastModityTime;
	private String lastModifyTelNo;
	private BrMst brMst;

	// Constructors

	/** default constructor */
	public FtpEmpInfo() {
	}

	/** minimal constructor */
	public FtpEmpInfo(String empNo) {
		this.empNo = empNo;
	}

	/** full constructor */
	public FtpEmpInfo(String empNo, String empName, String brNo,
			String empLvl, String postNo, String birthdaydate, String sex,
			String empStatus, String lastModityTime, String lastModifyTelNo) {
		this.empNo = empNo;
		this.empName = empName;
		this.brNo = brNo;
		this.empLvl = empLvl;
		this.postNo = postNo;
		this.birthdaydate = birthdaydate;
		this.sex = sex;
		this.empStatus = empStatus;
		this.lastModityTime = lastModityTime;
		this.lastModifyTelNo = lastModifyTelNo;
	}

	// Property accessors
	
	public BrMst getBrMst() {
		return brMst;
	}

	public void setBrMst(BrMst brMst) {
		this.brMst = brMst;
	}

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getEmpLvl() {
		return this.empLvl;
	}

	public void setEmpLvl(String empLvl) {
		this.empLvl = empLvl;
	}

	public String getPostNo() {
		return this.postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getBirthdaydate() {
		return this.birthdaydate;
	}

	public void setBirthdaydate(String birthdaydate) {
		this.birthdaydate = birthdaydate;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmpStatus() {
		return this.empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getLastModityTime() {
		return this.lastModityTime;
	}

	public void setLastModityTime(String lastModityTime) {
		this.lastModityTime = lastModityTime;
	}

	public String getLastModifyTelNo() {
		return this.lastModifyTelNo;
	}

	public void setLastModifyTelNo(String lastModifyTelNo) {
		this.lastModifyTelNo = lastModifyTelNo;
	}

}