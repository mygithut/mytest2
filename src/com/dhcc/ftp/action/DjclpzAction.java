package com.dhcc.ftp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.PageUtil;

/**
 * <p>
 * Title: DjclpzAction 定价策略配置
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
 * @date Oct 10, 2012 3:17:54 PM
 * 
 * @version 1.0
 */
public class DjclpzAction extends BoBuilder {
	
	private PageUtil ftpSystemInitialUtil = null;
	private int page = 1;
	private String brNo;
	private String setResult;
	private int pageSize = 10;
	private int rowsCount = -1;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	DaoFactory df = new DaoFactory();
	
	public String execute() throws Exception {
		return super.execute();
	}
	//查询所有县联社的配置结果(机构等级=2)
	public String list() throws Exception {
		TelMst telMst = (TelMst)request.getSession().getAttribute("userBean");
		
		String hsql = "select b.br_no, b.br_name, nvl(f.set_result,'0') set_result, f.set_date, t.tel_name, f.Set_num from ftp.br_mst b " +
				"left join ftp.ftp_system_initial f on b.br_no = f.br_no and f.Set_valid_mark = '1' " +//有效
				"left join ftp.tel_mst t on f.tel_no = t.tel_no "+ 
				"where b.manage_lvl = '2' ";
				
		String pageName = "DJCLPZ_list.action?pageSize="+pageSize;
		
        if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {
			hsql += " and b.br_no = '"+brNo+"'";
			pageName +="&brNo="+brNo;
		}else if(Integer.valueOf(telMst.getBrMst().getManageLvl())<=2){//如果不是省联社，要获取对应的县联社机构
			hsql += " and b.br_no = '"+FtpUtil.getXlsBrNo(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl())+"'";
		}
        
		if (setResult != null && !setResult.equals("") && !setResult.equals("null")) {
			if (setResult.equals("0")) {
				hsql += " and set_Result is null";
			}else {
				hsql += " and set_Result = '"+setResult+"'";
			}
			pageName +="&setResult="+setResult;
		}
		
		hsql += " order by set_result asc, b.br_no desc";
		System.out.println("hsql="+hsql);
		
		ftpSystemInitialUtil = queryPageBO.sqlQueryPage(hsql, pageSize, page, rowsCount, pageName);
		request.setAttribute("ftpSystemInitialUtil", ftpSystemInitialUtil);
		return "list";
	}
	
	public String save() throws Exception {
		TelMst bean = (TelMst)getSession().getAttribute("userBean");
		ftpSystemInitialBO.save(brNo, setResult, bean);
		return null;
	}
	
	public PageUtil getFtpSystemInitialUtil() {
		return ftpSystemInitialUtil;
	}
	public void setFtpSystemInitialUtil(PageUtil ftpSystemInitialUtil) {
		this.ftpSystemInitialUtil = ftpSystemInitialUtil;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getSetResult() {
		return setResult;
	}
	public void setSetResult(String setResult) {
		this.setResult = setResult;
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

}
