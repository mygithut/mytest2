package com.dhcc.ftp.action.tjbb;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.cache.CacheOperation;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.PageUtil;

/**
 * FTPӯ������ͳ�Ʊ���
 */
public class ReportAction extends BoBuilder {
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
	private String tjType;
	private Integer isFirst;
	private String brCountLvl;
	private String brCountLvlText;
	private Integer isMx;//�Ƿ��ǲ鿴�ӻ�����ӯ������
	private String prdtType;
	DaoFactory df = new DaoFactory();
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil resultPriceUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	
	public String execute() throws Exception {
		return super.execute();
	}
	
	//������ӯ������
	public String jgzylfxReport() throws Exception {
		String STime=CommonFunctions.GetCurrentTime();
		List<YlfxReport> ylfxReportList = reportBO.brPayOffProfile(request, date, brNo, manageLvl, assessScope, isMx, tjType);
		
		request.getSession().setAttribute("ylfxReportList", ylfxReportList);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		if(tjType != null)request.getSession().setAttribute("tjType", tjType);
		request.setAttribute("isFirst", isFirst);
		
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("��ʱ"+CostFen+"��"+CostMiao+"��");		
		return "jgzylfxList";
	}

	//ҵ������ӯ������
	public String ywtxylfxReport() throws Exception {
		String STime=CommonFunctions.GetCurrentTime();
		List<YlfxReport> ylfxReportList = reportBO.busPayOffProfile(request, date, brNo, manageLvl, assessScope, tjType);
		request.getSession().setAttribute("ylfxReportList", ylfxReportList);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		if(tjType != null)request.getSession().setAttribute("tjType", tjType);
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("��ʱ"+CostFen+"��"+CostMiao+"��");
		return "ywtxzylfxList";
	}
	
	//��/����/Ͷ��ҵ���Ʒӯ������
	public String cpylfxReport() throws Exception {
		String STime=CommonFunctions.GetCurrentTime();
		List<YlfxReport> cpylfxReportList = reportBO.prdtPayOffProfile(request, date, brNo, manageLvl, assessScope, prdtType, tjType);
		request.getSession().setAttribute("cpylfxReportList", cpylfxReportList);
		request.getSession().setAttribute("prdtType", prdtType);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		if(tjType != null)request.getSession().setAttribute("tjType", tjType);
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("��ʱ"+CostFen+"��"+CostMiao+"��");
		return "cpylfxList";
	}
	
	//�ʽ������������
	public String zjzxlcfxReport() throws Exception {
		String STime=CommonFunctions.GetCurrentTime();
		YlfxReport ylfxReport = reportBO.financialCenterLCProfile(request, date, brNo, manageLvl, assessScope, tjType);
		request.getSession().setAttribute("ylfxReport", ylfxReport);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		if(tjType != null)request.getSession().setAttribute("tjType", tjType);
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("��ʱ"+CostFen+"��"+CostMiao+"��");
		return "zjzxlcfxList";
	}
	
	//����ӯ������
	public String jgylpmReport() throws Exception {
		String STime=CommonFunctions.GetCurrentTime();
		List<YlfxReport> ylfxReportList = reportBO.brPayOffRanking(request, date, brNo, manageLvl, brCountLvl, assessScope, tjType);
		request.getSession().setAttribute("ylfxReportList", ylfxReportList);
		if(date != null)request.getSession().setAttribute("date", date);
		if(brCountLvlText != null)request.getSession().setAttribute("brCountLvlText", brCountLvlText);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		if(tjType != null)request.getSession().setAttribute("tjType", tjType);
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("��ʱ"+CostFen+"��"+CostMiao+"��");
		return "jgylpmList";
	}
	//�������
	public String clearCache() throws Exception {
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// ������
		
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
	    	date2 = date2.substring(0,4)+"1231";
		
		System.out.println("�����磺" + xlsBrNo);
		CacheOperation co = CacheOperation.getInstance();//����
		//�Ƴ���Ӧ������date�����µ�����
	    co.removeCacheData(reportBO, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType});
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
		//date = CommonFunctions.dateModifyM(date, -1);//�����ϸ��µ��µ�		
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
		this.assessScopeText = CommonFunctions.Chinese_CodeChange(assessScopeText);
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) throws UnsupportedEncodingException {
		this.brName = CommonFunctions.Chinese_CodeChange(brName);
	}

	public String getBrCountLvlText() {
		return brCountLvlText;
	}

	public void setBrCountLvlText(String brCountLvlText) throws UnsupportedEncodingException {
		this.brCountLvlText = CommonFunctions.Chinese_CodeChange(brCountLvlText);
	}

	public String getTjType() {
		return tjType;
	}

	public void setTjType(String tjType) {
		this.tjType = tjType;
	}


}
