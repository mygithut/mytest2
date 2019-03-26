package com.dhcc.ftp.action;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.Ftp1LdRatioAdjust;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class HDBZBTZAction extends BoBuilder implements ModelDriven<Ftp1LdRatioAdjust> {
	private Ftp1LdRatioAdjust po;
	private int page = 1;
	private int pageSize = -1;
	private int rowsCount = -1;
	private PageUtil pageUtil = null;
	private DaoFactory df = new DaoFactory();
	HttpServletRequest request=this.getRequest();
	
	public String getList() throws Exception {

		String pageName = "HDBZBTZ_getList.action?pageSize=" + pageSize;
		String hsql = "from Ftp1LdRatioAdjust where 1=1 ";

		// 机构名称
		if (po.getBrNo() != null && !po.getBrNo().equals("") && !po.getBrNo().equals("null")) {
			hsql += " and brNo " + LrmUtil.getBrSql(po.getBrNo());
		}
		hsql += " order by minRatio ";
		pageUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount,pageName);
		request.setAttribute("pageUtil", pageUtil);
		return "list";
	}


	public String del() throws Exception {
		String acIds[] = po.getId().split("@@");
		for (int i = 0; i < acIds.length; i++) {
			String hsql = "delete from Ftp1LdRatioAdjust where id='" + acIds[i]+"'";
			df.delete(hsql, null);
		}
		return null;
	}
	public String edit() throws Exception {
		
		String hsql = "from Ftp1LdRatioAdjust where id ='" + po.getId() + "'";
		Ftp1LdRatioAdjust ratioAdjust = (Ftp1LdRatioAdjust) df.getBean(hsql, null);
		request.setAttribute("ratioAdjust", ratioAdjust);
		
		return "Edit";
	}
	public String update() throws Exception {
		//BrMst brMst=new BrMst();
		//po.setBrMst(brMst);
		po.setAdjustValue(po.getAdjustValue()/10000);
		po.setMinRatio(po.getMinRatio()/100);
		po.setMaxRatio(po.getMaxRatio()/100);
		df.update(po);
		return null;
	}
	/**
	 * 保存手工录入的数据
	 * 
	 * @return
	 */
	public String save() throws Exception {
		//BrMst brMst=new BrMst();
		//po.setBrMst(brMst);
		po.setId(IDUtil.getInstanse().getUID());
		po.setAdjustValue(po.getAdjustValue()/10000);
		po.setMinRatio(po.getMinRatio()/100);
		po.setMaxRatio(po.getMaxRatio()/100);
		df.insert(po);
		return NONE;
	}


	public Ftp1LdRatioAdjust getModel() {
		if(po==null){
			po=new Ftp1LdRatioAdjust();
		}
		return po;
	}

}
