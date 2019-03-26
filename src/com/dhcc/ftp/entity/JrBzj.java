package com.dhcc.ftp.entity;

import com.dhcc.ftp.util.Excel;



/**
 * JrBzj entity. @author MyEclipse Persistence Tools
 */

public class JrBzj  implements java.io.Serializable {


    // Fields    
	@Excel(exportName="账号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String acId;
	@Excel(exportName="机构号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String brNo;
    @Excel(exportName="机构名称",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
    private String brName;
	@Excel(exportName="业务编码",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String businessNo;
     @Excel(exportName="产品编码",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String prdtNo;
     @Excel(exportName="产品名称",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String prdtName;
     @Excel(exportName="金额(元)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private Double amt;
     @Excel(exportName="余额(元)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private Double bal;
     @Excel(exportName="利率(年%)",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private Double rate;
     @Excel(exportName="开始日",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String opnDate;
     @Excel(exportName="到期日",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String mtrDate;
     @Excel(exportName="客户编号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String custNo;
     @Excel(exportName="客户名称",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String cusName;
     @Excel(exportName="科目号",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String kmh;
     @Excel(exportName="币种",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String curNo;
     @Excel(exportName="计息天数",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private Double days;
     @Excel(exportName="操作员",exportFieldWidth=32,exportConvertSign=0, importConvertSign=0)
     private String EmpNo ;


    // Constructors

    

	public String getBrName() {
		return brName;
	}


	public String getEmpNo() {
		return EmpNo;
	}


	public void setEmpNo(String empNo) {
		EmpNo = empNo;
	}


	public Double getDays() {
		return days;
	}


	public void setDays(Double days) {
		this.days = days;
	}


	public void setBrName(String brName) {
		this.brName = brName;
	}

	/** default constructor */
    public JrBzj() {
    }

	/** minimal constructor */
    public JrBzj(String acId) {
        this.acId = acId;
    }
    
    /** full constructor */
    public JrBzj(String acId, String brNo, String businessNo, String prdtNo, String prdtName, Double amt, Double bal, Double rate, String opnDate, String mtrDate, String custNo, String cusName, String kmh, String curNo,String brName,Double days,String EmpNo) {
        this.acId = acId;
        this.brNo = brNo;
        this.businessNo = businessNo;
        this.prdtNo = prdtNo;
        this.prdtName = prdtName;
        this.amt = amt;
        this.bal = bal;
        this.rate = rate;
        this.opnDate = opnDate;
        this.mtrDate = mtrDate;
        this.custNo = custNo;
        this.cusName = cusName;
        this.kmh = kmh;
        this.curNo = curNo;
        this.brName = brName;
        this.days = days;
        this.EmpNo = EmpNo;
    }

   
    // Property accessors

    public String getAcId() {
        return this.acId;
    }
    
    public void setAcId(String acId) {
        this.acId = acId;
    }

    public String getBrNo() {
        return this.brNo;
    }
    
    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }

    public String getBusinessNo() {
        return this.businessNo;
    }
    
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getPrdtNo() {
        return this.prdtNo;
    }
    
    public void setPrdtNo(String prdtNo) {
        this.prdtNo = prdtNo;
    }

    public String getPrdtName() {
        return this.prdtName;
    }
    
    public void setPrdtName(String prdtName) {
        this.prdtName = prdtName;
    }

    public Double getAmt() {
        return this.amt;
    }
    
    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getBal() {
        return this.bal;
    }
    
    public void setBal(Double bal) {
        this.bal = bal;
    }

    public Double getRate() {
        return this.rate;
    }
    
    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getOpnDate() {
        return this.opnDate;
    }
    
    public void setOpnDate(String opnDate) {
        this.opnDate = opnDate;
    }

    public String getMtrDate() {
        return this.mtrDate;
    }
    
    public void setMtrDate(String mtrDate) {
        this.mtrDate = mtrDate;
    }

    public String getCustNo() {
        return this.custNo;
    }
    
    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getCusName() {
        return this.cusName;
    }
    
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getKmh() {
        return this.kmh;
    }
    
    public void setKmh(String kmh) {
        this.kmh = kmh;
    }

    public String getCurNo() {
        return this.curNo;
    }
    
    public void setCurNo(String curNo) {
        this.curNo = curNo;
    }
   








}