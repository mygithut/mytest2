package com.dhcc.ftp.entity;



/**
 * TdMst entity. @author MyEclipse Persistence Tools
 */

public class TdMst  implements java.io.Serializable {


    // Fields    

     private TdMstId id;


    // Constructors

    /** default constructor */
    public TdMst() {
    }

    
    /** full constructor */
    public TdMst(TdMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public TdMstId getId() {
        return this.id;
    }
    
    public void setId(TdMstId id) {
        this.id = id;
    }
   








}