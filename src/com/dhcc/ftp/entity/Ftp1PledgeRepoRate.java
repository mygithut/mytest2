package com.dhcc.ftp.entity;

/**
 * Ftp1PledgeRepoRate entity. @author MyEclipse Persistence Tools
 */

public class Ftp1PledgeRepoRate implements java.io.Serializable {

	// Fields

	private String repoId;
	private String termType;
	private Double repoRate;
	private String repoDate;

	// Constructors

	/** default constructor */
	public Ftp1PledgeRepoRate() {
	}

	/** minimal constructor */
	public Ftp1PledgeRepoRate(String repoId) {
		this.repoId = repoId;
	}

	/** full constructor */
	public Ftp1PledgeRepoRate(String repoId, String termType, Double repoRate,
			String repoDate) {
		this.repoId = repoId;
		this.termType = termType;
		this.repoRate = repoRate;
		this.repoDate = repoDate;
	}

	// Property accessors

	public String getRepoId() {
		return this.repoId;
	}

	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public Double getRepoRate() {
		return this.repoRate;
	}

	public void setRepoRate(Double repoRate) {
		this.repoRate = repoRate;
	}

	public String getRepoDate() {
		return this.repoDate;
	}

	public void setRepoDate(String repoDate) {
		this.repoDate = repoDate;
	}

}