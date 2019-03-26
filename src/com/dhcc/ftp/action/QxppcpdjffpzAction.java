package com.dhcc.ftp.action;

/**
 * <p>
 * Title: DjclpzAction 期限匹配-期限匹配产品定价方法配置
 * </p>
 * 
 * <p>
 * Description: TODO
 * </p>
 * 
 * <p>
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @author 孙红玉
 * 
 * @date Oct 22, 2012 4:47:54 PM
 * 
 * @version 1.0
 */
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;

public class QxppcpdjffpzAction  extends BoBuilder {

	private String curNo;
	private String businessNo;
	private String productNo;
	private String methodNo;
	private String methodName;
	private String availableMethod;
	private String availableMethodNo;
	private String curveNo;
	private String adjustRate;
	private String assignTerm;
	private String appointRate;
	private String appointDeltaRate;
	private String lrAdjustRate;
	private String epsAdjustRate;
	private int page = 1;
	private int pageSize = 20;
	private int rowsCount = -1;
	private PageUtil ftpProductMethodRelUtil = null;
	private String brNo;
	private String isTz;
	
	HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}
	
	
	public String list() throws Exception {
		String hsql = "select" +
				" p.BUSINESS_NAME, " +
				"p.PRODUCT_NAME," +
				" p.METHOD_NAME, " +
				"p.ADJUST_RATE, " +
				"y.curve_name, " +
				"p.ASSIGN_TERM , " +
				"p.APPOINT_RATE," +
				" p.APPOINT_DELTA_RATE," +
				"p.PRODUCT_NO," +
				"p.METHOD_NO," +
				"p.br_no ," +
				"p.LR_adjust_rate," +
				"p.eps_adjust_rate, " +
				"y.curve_no "+
				" from ftp.Ftp_Product_Method_Rel p" +
				" left join (select min(curve_name)curve_name,curve_no from ftp.Ftp_YIELD_CURVE group by curve_no) y on y.curve_no = p.curve_no" +
				" where 1=1 ";
		
		String pageName = "QXPPCPDJFFPZ_list.action?pageSize="+pageSize+"&brNo="+brNo+"&curNo="+curNo+"&businessNo="+businessNo+"&productNo="+productNo;
		
		if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {//界面控制，brNo必须不为空，即必须选择，否则不执行查询
			hsql +=" and p.br_no ='"+brNo+"'";
		}
		
        if (curNo != null && !curNo.equals("") && !curNo.equals("null")) {
			hsql += " and p.cur_no = '"+curNo+"'";
		}
        if (businessNo != null && !businessNo.equals("") && !businessNo.equals("null")) {
			hsql += " and p.business_no = '"+businessNo+"'";
		}
        if (productNo != null && !productNo.equals("") && !productNo.equals("null")) {
			hsql += " and p.product_no = '"+productNo+"'";
		}
         
        hsql += " order by business_No,product_No";
		
        ftpProductMethodRelUtil = queryPageBO.sqlQueryPage(hsql, pageSize, page, rowsCount, pageName);
		request.setAttribute("ftpProductMethodRelUtil", ftpProductMethodRelUtil);
		return "list";
	}

	public String getFtpProductMethodRel() throws Exception {
		FtpProductMethodRel ftpProductMethodRel = ftpProductMethodRelBO.getFtpProductMethodRel(productNo, brNo);
		request.setAttribute("ftpProductMethodRel", ftpProductMethodRel);
		return "modify";
	}

	public String save() throws Exception {
		if(availableMethod.equals("")) availableMethod = "无";
		if(adjustRate == null || adjustRate.equals(""))
			adjustRate = "0";
		if(assignTerm == null || assignTerm.equals(""))
			assignTerm = "0";
		if(appointRate == null || appointRate.equals(""))
			appointRate = "0";
		if(appointDeltaRate == null || appointDeltaRate.equals(""))
			appointDeltaRate = "0";
		if(lrAdjustRate == null || lrAdjustRate.equals(""))
			lrAdjustRate = "0";
		if(epsAdjustRate == null || epsAdjustRate.equals(""))
			epsAdjustRate = "0";
		String hsql = "update FtpProductMethodRel set methodNo = '"+methodNo+"', methodName = '"+methodName+"'" +
				", availableMethod = '"+availableMethod+"', availableMethodNo = '"+availableMethodNo+"'" +
				", curveNo = '"+curveNo+"', adjustRate = "+Double.valueOf(adjustRate)/100+"" +
				", assignTerm = "+Integer.valueOf(assignTerm)+", appointRate = "+Double.valueOf(appointRate)/100+"" +
				", appointDeltaRate = "+Double.valueOf(appointDeltaRate)/100+" " +
				" , lrAdjustRate=" +Double.valueOf(lrAdjustRate)/100+" " +
				" , epsAdjustRate =" +Double.valueOf(epsAdjustRate)/100+" " +
				" , isTz ='"+isTz+"'"+
				"  where productNo = '"+productNo+"' and brNo = '"+brNo+"'";
		System.out.println("hsql:"+hsql);
		df.update(hsql, null);
		return null;
	}
	public String getCurNo() {
		return curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getLrAdjustRate() {
		return lrAdjustRate;
	}

	public void setLrAdjustRate(String lrAdjustRate) {
		this.lrAdjustRate = lrAdjustRate;
	}

	

	public String getEpsAdjustRate() {
		return epsAdjustRate;
	}


	public void setEpsAdjustRate(String epsAdjustRate) {
		this.epsAdjustRate = epsAdjustRate;
	}


	public int getRowsCount() {
		return rowsCount;
	}


	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}


	public PageUtil getFtpProductMethodRelUtil() {
		return ftpProductMethodRelUtil;
	}


	public void setFtpProductMethodRelUtil(PageUtil ftpProductMethodRelUtil) {
		this.ftpProductMethodRelUtil = ftpProductMethodRelUtil;
	}



	public String getProductNo() {
		return productNo;
	}


	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}


	public String getMethodNo() {
		return methodNo;
	}


	public void setMethodNo(String methodNo) {
		this.methodNo = methodNo;
	}


	public String getCurveNo() {
		return curveNo;
	}


	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}


	public String getAdjustRate() {
		return adjustRate;
	}


	public void setAdjustRate(String adjustRate) {
		this.adjustRate = adjustRate;
	}


	public String getAssignTerm() {
		return assignTerm;
	}


	public void setAssignTerm(String assignTerm) {
		this.assignTerm = assignTerm;
	}


	public String getAppointRate() {
		return appointRate;
	}


	public void setAppointRate(String appointRate) {
		this.appointRate = appointRate;
	}


	public String getAppointDeltaRate() {
		return appointDeltaRate;
	}


	public void setAppointDeltaRate(String appointDeltaRate) {
		this.appointDeltaRate = appointDeltaRate;
	}


	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public String getAvailableMethod() {
		return availableMethod;
	}


	public void setAvailableMethod(String availableMethod) {
		this.availableMethod = availableMethod;
	}


	public String getAvailableMethodNo() {
		return availableMethodNo;
	}


	public void setAvailableMethodNo(String availableMethodNo) {
		this.availableMethodNo = availableMethodNo;
	}


	public String getBrNo() {
		return brNo;
	}


	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}


	public String getIsTz() {
		return isTz;
	}


	public void setIsTz(String isTz) {
		this.isTz = isTz;
	}
	
	
	
}
