package com.dhcc.ftp.entity;



/**
 * DdMst entity. @author MyEclipse Persistence Tools
 */

public class DdMst  implements java.io.Serializable {


    // Fields    

     private DdMstId id;


    // Constructors

    /** default constructor */
    public DdMst() {
    }

    
    /** full constructor */
    public DdMst(DdMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public DdMstId getId() {
        return this.id;
    }
    
    public void setId(DdMstId id) {
        this.id = id;
    }
   








}