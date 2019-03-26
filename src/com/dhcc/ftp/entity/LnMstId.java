package com.dhcc.ftp.entity;



/**
 * LnMstId entity. @author MyEclipse Persistence Tools
 */

public class LnMstId  implements java.io.Serializable {


    // Fields    

     private Integer acId;
     private Integer acSeqn;


    // Constructors

    /** default constructor */
    public LnMstId() {
    }

    
    /** full constructor */
    public LnMstId(Integer acId, Integer acSeqn) {
        this.acId = acId;
        this.acSeqn = acSeqn;
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
   








}