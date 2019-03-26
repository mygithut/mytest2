package com.dhcc.ftp.entity;



/**
 * Ftp1DkSfblAdjust entity. @author MyEclipse Persistence Tools
 */

public class Ftp1DkSfblAdjust  implements java.io.Serializable {


    // Fields    

     private String id;
     private Double minPercent;
     private Double maxPercent;
     private Double adjustValue;
     private String brNo;
     private String lastModifyTime;
     private String lastModifyTelno;


    // Constructors

    /** default constructor */
    public Ftp1DkSfblAdjust() {
    }

	/** minimal constructor */
    public Ftp1DkSfblAdjust(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public Ftp1DkSfblAdjust(String id, Double minPercent, Double maxPercent, Double adjustValue, String brNo, String lastModifyTime, String lastModifyTelno) {
        this.id = id;
        this.minPercent = minPercent;
        this.maxPercent = maxPercent;
        this.adjustValue = adjustValue;
        this.brNo = brNo;
        this.lastModifyTime = lastModifyTime;
        this.lastModifyTelno = lastModifyTelno;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public Double getMinPercent() {
        return this.minPercent;
    }
    
    public void setMinPercent(Double minPercent) {
        this.minPercent = minPercent;
    }

    public Double getMaxPercent() {
        return this.maxPercent;
    }
    
    public void setMaxPercent(Double maxPercent) {
        this.maxPercent = maxPercent;
    }

    public Double getAdjustValue() {
        return this.adjustValue;
    }
    
    public void setAdjustValue(Double adjustValue) {
        this.adjustValue = adjustValue;
    }

    public String getBrNo() {
        return this.brNo;
    }
    
    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }

    public String getLastModifyTime() {
        return this.lastModifyTime;
    }
    
    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getLastModifyTelno() {
        return this.lastModifyTelno;
    }
    
    public void setLastModifyTelno(String lastModifyTelno) {
        this.lastModifyTelno = lastModifyTelno;
    }
   








}