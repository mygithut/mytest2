package com.dhcc.ftp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpInterestMarginDivide;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @author yl
 */
public class CKLLFGBLSZAction extends BoBuilder implements ModelDriven<FtpInterestMarginDivide> {
	private int pageSize = 10;
	private int rowsCount = -1;
	private FtpInterestMarginDivide po ;
	private int page = 1;
	private PageUtil frpInterestMarginUtil = null;
	private List<FtpInterestMarginDivide> pos=new ArrayList<FtpInterestMarginDivide>();
	HttpServletRequest request = getRequest();
	private String savePath = null;
	DaoFactory df = new DaoFactory();
	
	
	public String execute() throws Exception {
		return super.execute();
	}
	/**
	 * 查询列表
	 * @return
	 * @throws Exception
	 */
	public String getList() throws Exception {
		String hsql = "from FtpInterestMarginDivide h  where 1=1 ";//substr(shiborTerm,2,1),substr(shiborTerm,1,1)";
		hsql+=" order by h.termType,h.assetsType ";
		//String pageName = "FtpEmpInfo_List.action?";
		java.util.List<FtpInterestMarginDivide> lstFtp=df.query(hsql, null);
		//frpInterestMarginUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount, pageName);
		Map<String, FtpInterestMarginDivide> map=new HashMap<String, FtpInterestMarginDivide>();
		for (FtpInterestMarginDivide divide : lstFtp) {
			map.put(divide.getTermType()+"-"+divide.getAssetsType(), divide);
		}
		request.setAttribute("divideMap", map);
		return "List";
	}
	
	/**
	 * 查询detail
	 * @return
	 * @throws Exception
	 */
	private FtpInterestMarginDivide getQuery(String id) throws Exception {
		String hsql = "from FtpInterestMarginDivide where id = "+id;
		FtpInterestMarginDivide ftpInterestMarginDivide = (FtpInterestMarginDivide)df.getBean(hsql, null);
		/*request.setAttribute("ftpInterestMarginDivide", ftpInterestMarginDivide);
		return "detail";*/
		return ftpInterestMarginDivide;
	}
	
	
	
	 /**
	  * 保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
		boolean flag=true;
		String message="";
		for (int i = 0; i < pos.size(); i++) {
			FtpInterestMarginDivide divide=this.getQuery(pos.get(i).getId());
			divide.setLastModifyTelNo(telmst.getTelNo());
			divide.setLastModifyTime(DateUtil.getCurrentDay()+DateUtil.getCurrentTime());
			divide.setRate(pos.get(i).getRate());
			flag=df.update(divide);
			if(!flag){
				message="["+divide.getId()+"]";
			}
		}
		if("".equals(message)){
			this.getRequest().setAttribute("message", "ok");
		}else{
			this.getRequest().setAttribute("message", message);
		}
	    	return "update";
	}
	
	 
	
	
	
	public void setPos(java.util.List<FtpInterestMarginDivide> pos) {
		this.pos = pos;
	}
	
	public java.util.List<FtpInterestMarginDivide> getPos() {
		return pos;
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



	public FtpInterestMarginDivide getPo() {
		return po;
	}



	public void setPo(FtpInterestMarginDivide po) {
		this.po = po;
	}



	public int getPage() {
		return page;
	}



	public void setPage(int page) {
		this.page = page;
	}



	public PageUtil getFrpInterestMarginUtil() {
		return frpInterestMarginUtil;
	}



	public void setFrpInterestMarginUtil(PageUtil frpInterestMarginUtil) {
		this.frpInterestMarginUtil = frpInterestMarginUtil;
	}



	public String getSavePath() {
		return savePath;
	}



	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}



	public FtpInterestMarginDivide getModel() {
		if(po==null){
			this.po=new FtpInterestMarginDivide();
		}
		return po;
	}

}
