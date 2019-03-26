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
 * FTPӯ������ͳ�Ʊ���_�Է���
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
	private Integer isMx;//�Ƿ��ǲ鿴�ӻ�����ӯ������
	private String prdtType;
	DaoFactory df = new DaoFactory();
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil resultPriceUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	
	private String assessScopeText_Ajax;//ר����Ajax����ֵ��ȡ�����ڻ���ӯ����������ajax�첽�ύ���Σ����Ա���ֱ�ӾͲ������룬����б�����������ҳ���ȡ����
	private String brName_Ajax;//ר����Ajax����ֵ��ȡ�����ڻ���ӯ����������ajax�첽�ύ���Σ����Ա���ֱ�ӾͲ������룬����б�����������ҳ���ȡ����
	
	public String execute() throws Exception {
		return super.execute();
	}
	
	//������ӯ������
	public String jgzylfxReport() {
		System.out.println("################## ��ʼ����...");
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
		System.out.println("################## ������ɣ�����ҳ�棡");
		//return "jgzylfxList";
		return null;
	}

	//ҵ������ӯ������
	public String ywtxylfxReport() throws Exception {
		List<YlfxReport> ylfxSfbReportList = reportSfbBO.busPayOffProfile(request, date, brNo, manageLvl, assessScope);
		request.getSession().setAttribute("ylfxSfbReportList", ylfxSfbReportList);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		return "ywtxzylfxList";
	}
	
	//��/����/Ͷ��ҵ���Ʒӯ������
	public String cpylfxReport() throws Exception {
		List<YlfxReport> cpylfxSfbReportList = reportSfbBO.prdtPayOffProfile(request, date, brNo, manageLvl, assessScope, prdtType);
		request.getSession().setAttribute("cpylfxSfbReportList", cpylfxSfbReportList);
		request.getSession().setAttribute("prdtType", prdtType);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		return "cpylfxList";
	}
	
	//�ʽ������������
	public String zjzxlcfxReport() throws Exception {
		YlfxReport ylfxReport = reportSfbBO.financialCenterLCProfile(request, date, brNo, manageLvl, assessScope);
		request.getSession().setAttribute("ylfxReport", ylfxReport);
		if(date != null)request.getSession().setAttribute("date", date);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		System.out.println(ylfxReport);
		return "zjzxlcfxList";
	}
	
	//����ӯ������
	public String jgylpmReport() throws Exception {
		List<YlfxReport> ylfxReportList = reportSfbBO.brPayOffRanking(request, date, brNo, manageLvl, brCountLvl, assessScope);
		request.getSession().setAttribute("ylfxReportList", ylfxReportList);
		if(date != null)request.getSession().setAttribute("date", date);
		if(brCountLvlText != null)request.getSession().setAttribute("brCountLvlText", brCountLvlText);
		if(assessScopeText != null)request.getSession().setAttribute("assessScopeText", assessScopeText);
		if(brName != null)request.getSession().setAttribute("brName", brName);
		return "jgylpmList";
	}
	//�������
	public String clearCache() throws Exception {
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// ������
		System.out.println("�����磺" + xlsBrNo);
		CacheOperation co = CacheOperation.getInstance();//����
		//�Ƴ���Ӧ������date�����µ�����
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
		this.assessScopeText = new String(assessScopeText.getBytes("ISO-8859-1"),"UTF-8");
		this.assessScopeText_Ajax =assessScopeText;//���ڻ���ӯ����������ajax�첽�ύ���Σ����Ա���ֱ�ӾͲ������룬����б�����������ҳ���ȡ����
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) throws UnsupportedEncodingException {
		this.brName = new String(brName.getBytes("ISO-8859-1"),"UTF-8");
		this.brName_Ajax = brName;//���ڻ���ӯ����������ajax�첽�ύ���Σ����Ա���ֱ�ӾͲ������룬����б�����������ҳ���ȡ����
	}

	public String getBrCountLvlText() {
		return brCountLvlText;
	}

	public void setBrCountLvlText(String brCountLvlText) throws UnsupportedEncodingException {
		this.brCountLvlText = new String(brCountLvlText.getBytes("ISO-8859-1"),"UTF-8");
	}

}
