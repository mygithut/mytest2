package com.dhcc.ftp.entity;

/**
 * FtpBrPostRel entity. @author MyEclipse Persistence Tools
 */

public class FtpBrPostRel implements java.io.Serializable {

	// Fields

	private String id;
	private String brNo;
	private String postNo;
	private String postName;
	private String relTime;
	private String relTelNo;
	private BrMst brMst;

	// Constructors
	
	public BrMst getBrMst() {
		return brMst;
	}

	public void setBrMst(BrMst brMst) {
		this.brMst = brMst;
	}

	/** default constructor */
	public FtpBrPostRel() {
	}

	/** minimal constructor */
	public FtpBrPostRel(String id) {
		this.id = id;
	}

	/** full constructor */
	public FtpBrPostRel(String id, String brNo, String postNo, String postName,
			String relTime, String relTelNo) {
		this.id = id;
		this.brNo = brNo;
		this.postNo = postNo;
		this.postName = postName;
		this.relTime = relTime;
		this.relTelNo = relTelNo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getPostNo() {
		return this.postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getRelTime() {
		return this.relTime;
	}

	public void setRelTime(String relTime) {
		this.relTime = relTime;
	}

	public String getRelTelNo() {
		return this.relTelNo;
	}

	public void setRelTelNo(String relTelNo) {
		this.relTelNo = relTelNo;
	}

}