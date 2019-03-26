package com.dhcc.ftp.action;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1DkAdjust;
import com.dhcc.ftp.entity.JrCcbzj;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class FtpDkAdjustAction extends BoBuilder implements ModelDriven<Ftp1DkAdjust> {
	private Ftp1DkAdjust po;
	
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil ftpDkUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	DaoFactory df = new DaoFactory();
	
	
	
	public String getList() throws Exception{
		String pageName = "FtpDkAdjust_getList.action?pageSize=" + pageSize;
		String hsql = "from Ftp1DkAdjust where 1=1 ";
		hsql += " order by id";
		ftpDkUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount,	pageName);
		request.setAttribute("ftpDkUtil", ftpDkUtil);
		return  "list";
	}
	
	public String getDetail() throws Exception{
		String hsql = "from Ftp1DkAdjust where id ='" + po.getId() + "'";
		Ftp1DkAdjust dkAdjust = (Ftp1DkAdjust) df.getBean(hsql, null);
		request.setAttribute("dk", dkAdjust);
		return "detail";
	}
	
	public String save()  throws Exception{
		TelMst telmst = (TelMst) this.getRequest().getSession().getAttribute("userBean"); 
		po.setLastModifyTime(DateUtil.getCurrentDay());
		po.setLastModifyTelno(telmst.getTelNo());
		po.setAdjustValue(po.getAdjustValue()/100);
		po.setMinPercent(po.getMinPercent()/100);
		po.setMaxPercent(po.getMaxPercent()/100);
		df.update(po);
		return NONE;
	}

	public Ftp1DkAdjust getModel() {
		if(po==null){
			po=new Ftp1DkAdjust();
		}
		return po;
	}

	public Ftp1DkAdjust getPo() {
		return po;
	}

	public void setPo(Ftp1DkAdjust po) {
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

	public PageUtil getFtpDkUtil() {
		return ftpDkUtil;
	}

	public void setFtpDkUtil(PageUtil ftpDkUtil) {
		this.ftpDkUtil = ftpDkUtil;
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

}
