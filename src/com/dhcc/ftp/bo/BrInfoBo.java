package com.dhcc.ftp.bo;

import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.PageUtil;

public class BrInfoBo extends BaseBo{

	//分页查询
	public PageUtil doquery(int page, TelMst telmst,String brNo,String operate) {
		return brInfoDAO.doquery(page, telmst, brNo, operate);
	}
	//添加数据
	public String addData(BrMst brmst) {
		return brInfoDAO.addData(brmst);
	}
 
	public BrMst getInfo(String brno){
		return brInfoDAO.getInfo(brno);
	}
	
	public String getBrNo(String brName){
		return brInfoDAO.getBrNo(brName);
	}
	
	//修改数据
	public void modify(BrMst brmst){
		 brInfoDAO.modify(brmst);
	}
	
	//删除
	public void remove(String brno){
		brInfoDAO.remove(brno);
	}
	
	public PageUtil queryPage(String hsql, Object[] values, int pageSize,int currentPage, int rowsCount, String pageName) {
		return brInfoDAO.queryPage(hsql, values, pageSize, currentPage, rowsCount, pageName);
	}
	
    public String formartPageLine(int pageSize, int currentPage, int rowsCount,String pageName){
		return brInfoDAO.formartPageLine(pageSize, currentPage, rowsCount, pageName) ;
	}
    public String getBrHql(String brno, String managelvl) {
    	String hsql= "";
		if (managelvl.equals("3")) {
			hsql = "from BrMst order by brNo desc ";
		} else if (managelvl.equals("1")) {
			hsql = "from BrMst b where b.brNo='"+ brno+"' or b.superBrNo='"+ brno+"' order by b.brNo desc";
		}else if (managelvl.equals("2")) {
			hsql = "from BrMst b where b.brNo='"+ brno+"' or b.superBrNo='"+ brno+"' order by b.brNo desc";
		}
		else if (managelvl.equals("0")) {
			hsql = "from BrMst b where b.brNo='"+ brno+"' order by b.brNo desc";
		} else if (managelvl==null){
			hsql = "from BrMst b where 1=2";
		}
		return hsql;
	}
}
