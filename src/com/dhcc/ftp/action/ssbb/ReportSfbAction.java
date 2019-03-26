package com.dhcc.ftp.action.ssbb;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.cache.CacheOperation;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.PageUtil;

/**
 * FTP盈利分析统计报表_试发布
 */
public class ReportSfbAction extends BoBuilder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer assessScope;
	private String date;
	private String brNo;
	private String assessScopeText;
	private String brName;
	private String manageLvl;
	private Integer isFirst;
	private String brCountLvl;
	private String brCountLvlText;
	private Integer isMx;//是否是查看子机构的盈利分析
	private String prdtType;
	DaoFactory df = new DaoFactory();
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil resultPriceUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	
	private String assessScopeText_Ajax;//专用于Ajax参数值获取：由于机构盈利分析采用ajax异步提交传参，所以本身直接就不会乱码，因此有别与其他报表页面获取参数
	private String brName_Ajax;//专用于Ajax参数值获取：由于机构盈利分析采用ajax异步提交传参，所以本身直接就不会乱码，因此有别与其他报表页面获取参数
	
	public String execute() throws Exception {
		return super.execute();
	}
	
	//机构总盈利分析
	public String jgzylfxReport() {
		System.out.println("################## 开始计算...");
		try{
			List<YlfxReport> ylfxReportList = reportSfbBO.brPayOffProfile(request, date, brNo, manageLvl, assessScope, isMx);
			request.getSession().setAttribute("ylfxReportList", ylfxReportList);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText_Ajax);
		if(brName != null)request.getSession().setAttribute("brName", brName_Ajax);
		request.getSession().setAttribute("manageLvl", manageLvl);
		//request.setAttribute("isFirst", isFirst);
		System.out.println("################## 计算完成，返回页面！");
		//return "jgzylfxList";
		return null;
	}

	//业务条线盈利分析
	public String ywtxylfxReport() throws Exception {
		List<YlfxReport> ylfxSfbReportList = reportSfbBO.busPayOffProfile(request, date, brNo, manageLvl, assessScope);
		request.getSession().setAttribute("ylfxSfbReportList", ylfxSfbReportList);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		return "ywtxzylfxList";
	}
	
	//存/贷款/投资业务产品盈利分析
	public String cpylfxReport() throws Exception {
		List<YlfxReport> cpylfxSfbReportList = reportSfbBO.prdtPayOffProfile(request, date, brNo, manageLvl, assessScope, prdtType);
		request.getSession().setAttribute("cpylfxSfbReportList", cpylfxSfbReportList);
		request.getSession().setAttribute("prdtType", prdtType);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		return "cpylfxList";
	}
	
	//资金中心利差分析
	public String zjzxlcfxReport() throws Exception {
		YlfxReport ylfxReport = reportSfbBO.financialCenterLCProfile(request, date, brNo, manageLvl, assessScope);
		request.getSession().setAttribute("ylfxReport", ylfxReport);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		System.out.println(ylfxReport);
		return "zjzxlcfxList";
	}
	
	//机构盈利排名
	public String jgylpmReport() throws Exception {
		List<YlfxReport> ylfxReportList = reportSfbBO.brPayOffRanking(request, date, brNo, manageLvl, brCountLvl, assessScope);
		request.getSession().setAttribute("ylfxReportList", ylfxReportList);
		if(date != null)request.getSession().setAttribute("date", date);
		if(brCountLvlText != null)request.getSession().setAttribute("brCountLvlText", brCountLvlText);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		return "jgylpmList";
	}
	//清除缓存
	public String clearCache() throws Exception {
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// 县联社
		System.out.println("县联社：" + xlsBrNo);
		CacheOperation co = CacheOperation.getInstance();//缓存
		//移除对应县联社date日期下的数据
	    co.removeCacheData(reportSfbBO, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date});
		return null;
	}
	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
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

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public PageUtil getResultPriceUtil() {
		return resultPriceUtil;
	}

	public void setResultPriceUtil(PageUtil resultPriceUtil) {
		this.resultPriceUtil = resultPriceUtil;
	}

	public Integer getAssessScope() {
		return assessScope;
	}

	public void setAssessScope(Integer assessScope) {
		this.assessScope = assessScope;
	}

	public String getManageLvl() {
		return manageLvl;
	}

	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}

	public Integer getIsMx() {
		return isMx;
	}

	public void setIsMx(Integer isMx) {
		this.isMx = isMx;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		//date = CommonFunctions.dateModifyM(date, -1);//处理到上个月的月底		
		this.date = date;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public String getPrdtType() {
		return prdtType;
	}

	public void setPrdtType(String prdtType) {
		this.prdtType = prdtType;
	}

	public String getBrCountLvl() {
		return brCountLvl;
	}

	public void setBrCountLvl(String brCountLvl) {
		this.brCountLvl = brCountLvl;
	}

	public String getAssessScopeText() {
		return assessScopeText;
	}

	public void setAssessScopeText(String assessScopeText) throws UnsupportedEncodingException {
		this.assessScopeText = new String(assessScopeText.getBytes("ISO-8859-1"),"UTF-8");
		this.assessScopeText_Ajax =assessScopeText;//由于机构盈利分析采用ajax异步提交传参，所以本身直接就不会乱码，因此有别与其他报表页面获取参数
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) throws UnsupportedEncodingException {
		this.brName = new String(brName.getBytes("ISO-8859-1"),"UTF-8");
		this.brName_Ajax = brName;//由于机构盈利分析采用ajax异步提交传参，所以本身直接就不会乱码，因此有别与其他报表页面获取参数
	}

	public String getBrCountLvlText() {
		return brCountLvlText;
	}

	public void setBrCountLvlText(String brCountLvlText) throws UnsupportedEncodingException {
		this.brCountLvlText = new String(brCountLvlText.getBytes("ISO-8859-1"),"UTF-8");
	}

}
