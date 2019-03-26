package com.dhcc.ftp.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1DkAdjust;
import com.dhcc.ftp.entity.Ftp1DkSfblAdjust;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 贷款上浮比例调整
 * @author Sunhongyu
 *
 */
public class FtpDksfblAdjustAction extends BoBuilder implements ModelDriven<Ftp1DkSfblAdjust> {
	private Ftp1DkSfblAdjust po;
	
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil ftpDkSfblUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	DaoFactory df = new DaoFactory();
	
	
	
	public String getList() throws Exception{
		String pageName = "FtpDkSfblAdjust_getList.action?pageSize=" + pageSize;
		String hsql = "from Ftp1DkSfblAdjust where 1=1 ";
		hsql += " order by minPercent";
		ftpDkSfblUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount,	pageName);
		request.setAttribute("ftpDkSfblUtil", ftpDkSfblUtil);
		return  "list";
	}
	
	public String getDetail() throws Exception{
		String hsql = "from Ftp1DkSfblAdjust where id ='" + po.getId() + "'";
		Ftp1DkSfblAdjust ftp1DkSfblAdjust = (Ftp1DkSfblAdjust) df.getBean(hsql, null);
		request.setAttribute("dk", ftp1DkSfblAdjust);
		return "detail";
	}
	
	public String save()  throws Exception{
		TelMst telmst = (TelMst) this.getRequest().getSession().getAttribute("userBean"); 
		po.setLastModifyTime(DateUtil.getCurrentDay());
		po.setLastModifyTelno(telmst.getTelNo());
		po.setAdjustValue(po.getAdjustValue()/100);
		df.update(po);
		return NONE;
	}

	public Ftp1DkSfblAdjust getModel() {
		if(po==null){
			po=new Ftp1DkSfblAdjust();
		}
		return po;
	}

	public Ftp1DkSfblAdjust getPo() {
		return po;
	}

	public void setPo(Ftp1DkSfblAdjust po) {
		this.po = po;
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

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public DaoFactory getDf() {
		return df;
	}

	public void setDf(DaoFactory df) {
		this.df = df;
	}

	public PageUtil getFtpDkSfblUtil() {
		return ftpDkSfblUtil;
	}

	public void setFtpDkSfblUtil(PageUtil ftpDkSfblUtil) {
		this.ftpDkSfblUtil = ftpDkSfblUtil;
	}


}
