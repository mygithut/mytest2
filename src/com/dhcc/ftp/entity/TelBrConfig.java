package com.dhcc.ftp.entity;

/**
 * TelBrConfig entity. @author MyEclipse Persistence Tools
 */

public class TelBrConfig implements java.io.Serializable {

	// Fields

	private String telNo;
	private String brNos;

	// Constructors

	/** default constructor */
	public TelBrConfig() {
	}

	/** minimal constructor */
	public TelBrConfig(String telNo) {
		this.telNo = telNo;
	}

	/** full constructor */
	public TelBrConfig(String telNo, String brNos) {
		this.telNo = telNo;
		this.brNos = brNos;
	}

	// Property accessors

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getBrNos() {
		return this.brNos;
	}

	public void setBrNos(String brNos) {
		this.brNos = brNos;
	}

}