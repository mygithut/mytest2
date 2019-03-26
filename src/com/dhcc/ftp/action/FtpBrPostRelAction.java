package com.dhcc.ftp.action;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpBrPostRel;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @author yanlei
 */
public class FtpBrPostRelAction extends BoBuilder implements ModelDriven<FtpBrPostRel> {
	private int pageSize = 10;
	private int rowsCount = -1;
	private FtpBrPostRel  po ;
	private int page = 1;
	private PageUtil ftpBrPostRelUtil = null;
	HttpServletRequest request = getRequest();
	private String savePath = null;
	DaoFactory df = new DaoFactory();

	/**
	 * 查询列表
	 * @return
	 * @throws Exception
	 */
	public String List() throws Exception {
		String hql = "from FtpBrPostRel h  where 1=1 ";
		if(po.getBrNo()!=null&&!"".equals(po.getBrNo())){
			hql+=" and  h.brMst.brNo  = '"+po.getBrNo()+"'";
		}
		hql+=" order by postNo ";
		String pageName = "FtpBrPostRel_List.action?brNo="+po.getBrNo();
		ftpBrPostRelUtil = queryPageBO.queryPage(hql, pageSize, page, rowsCount, pageName);
		return "List";
	}
	
	/**
	 * 查询detail
	 * @return
	 * @throws Exception
	 */
	public String Query() throws Exception {
		String hsql = "from FtpBrPostRel where id = "+po.getId();
		FtpBrPostRel ftpBrPostRel  = (FtpBrPostRel)df.getBean(hsql, null);
		request.setAttribute("ftpBrPostRel", ftpBrPostRel);
		return "detail";
	}
	
	 /**
	  * 保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
			//修改
		//BrMst brMst=brInfoBo.getInfo(this.po.getBrNo());
		BrMst brMst=new BrMst(this.po.getBrNo());
		this.po.setBrMst(brMst);
		TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
		this.po.setRelTelNo(telmst.getTelNo());
		this.po.setRelTime(DateUtil.getCurrentDay()+DateUtil.getCurrentTime());
	    	if (po.getId() != null && !po.getId().equals("")){
				df.update(this.po);
	    	}else {
	    		//添加
	    		this.po.setId(IDUtil.getInstanse().getUID());
				df.insert(this.po);
	    	}
	    	
	    	return null;
	    }
	
	 /**
	  * 删除方法
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception {
	    	String[] id = this.po.getId().split(",");
	    	String message="";
	    	
			for(int i = 0; i < id.length; i++){
				boolean flag=false;
				String hsql="delete from FtpBrPostRel where id="+id[i]+"";
				flag=df.delete(hsql, null);
				if(!flag){
					message="["+id+"]";
				}
			}
			if("".equals(message)){
				this.getResponse().getWriter().print("ok");
			}else{
				this.getResponse().getWriter().print(message);
			}
			this.getResponse().getWriter().flush();
			this.getResponse().getWriter().close();
			return NONE;
	}
	
	
	
	/**
	 * 检验员工编号是否重复
	 * @return
	 * @throws Exception
	 */
	public String checkEmp() throws Exception{
		String hsql = "from FtpBrPostRel where postNo = '"+po.getPostNo()+"'";
		FtpBrPostRel brPostRel = (FtpBrPostRel)df.getBean(hsql, null);
		if(brPostRel!=null){
			this.getResponse().getWriter().print("no");
		}else{
			this.getResponse().getWriter().print("ok");
		}
		return NONE;
	}
	
	
	public String getPostByBrNo() throws Exception {
		this.getResponse().setCharacterEncoding("utf-8");
		String hql = "from FtpBrPostRel h  where 1=1 ";
		if(po.getBrNo()!=null&&!"".equals(po.getBrNo())){
			hql+=" and  h.brMst.brNo  = '"+po.getBrNo()+"'";
		}
		hql+=" order by postNo ";
		java.util.List<FtpBrPostRel> brPostRels=df.query(hql, null);
		String json="[" ;
		for (int i = 0; i < brPostRels.size(); i++) {
			json+="{ 'name' :'"+brPostRels.get(i).getPostName()+"', 'value':'"+brPostRels.get(i).getPostNo()+"'}";
			if(i!=brPostRels.size()){
				json+=",";
			}
		}
		json+="]";
		this.getResponse().getWriter().print(json);
		this.getResponse().getWriter().flush();
		this.getResponse().getWriter().close();
		return NONE;
	}
	
	
	
	
	
	public String execute() throws Exception {
		return super.execute();
	}
	public FtpBrPostRel getModel() {
		if(this.po==null){
			this.po=new FtpBrPostRel();
		}
		return  this.po;
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public PageUtil getFtpBrPostRelUtil() {
		return ftpBrPostRelUtil;
	}

	public void setFtpBrPostRelUtil(PageUtil ftpBrPostRelUtil) {
		this.ftpBrPostRelUtil = ftpBrPostRelUtil;
	}

	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
}
