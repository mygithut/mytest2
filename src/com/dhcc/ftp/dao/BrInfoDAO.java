package com.dhcc.ftp.dao;

import java.util.List;

import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.PageUtil;

public class BrInfoDAO extends DaoFactory {

	private PageUtil BrnoUtil = null;
	private String hql = null;
	private String managelvl = null;
	private int perpgShwLine;
	private String brno = null;
	private String statement = null;
	private Object mark = null;

	//分页查询
	public PageUtil doquery(int page, TelMst telmst,String brNo,String operate) {
		
		String hql = "from BrMst b where b.brNo='" + brNo+"'";
		BrnoUtil = this.queryPage(hql, null, perpgShwLine, page, -1,
				"brnoSearch.action?brno="+brNo);
		return BrnoUtil;
	}
	//添加数据
	public String addData(BrMst brmst) {
		brno = brmst.getBrNo();
		mark = super.getUniqueResult("from BrMst b where b.brNo='" + brno+"'", null);
		if (null == mark) {
			super.insert(brmst);
			return "success";
		} else {
			return "input";
		}
	}
 
	public BrMst getInfo(String brno){
//		return (BrMst) super.getUniqueResult("from BrMst b where b.brNo='"+ brno+"'",null);
		return (BrMst) super.getBean("from BrMst b where b.brNo='"+ brno+"'",null);
	}
	
	public String getBrNo(String brName){
		BrMst brMst = (BrMst)super.getUniqueResult("from BrMst b where b.brName='"+ brName+"'",null);
		return brMst == null ? "" : brMst.getBrNo();
	}
	
	//修改数据
	public void modify(BrMst brmst){
		super.update("update BrMst b set b.brName='"+brmst.getBrName()+"',"+
					               		"b.chargePersonName='"+brmst.getChargePersonName()+"',"+
					               		"b.manageLvl='"+brmst.getManageLvl()+"',"+
					               		"b.fbrCode='"+brmst.getFbrCode()+"',"+
					               		"b.legalPersonFlag='"+brmst.getLegalPersonFlag()+"',"+
					               		"b.conPersonName='"+brmst.getConPersonName()+"',"+
					               		"b.conPhone='"+brmst.getConPhone()+"',"+
					               		"b.conAddr='"+brmst.getConAddr()+"',"+
					               		"b.postNo='"+brmst.getPostNo()+"',"+
					               		"b.brClFlag='"+brmst.getBrClFlag()+"',"+
					               		"b.superBrNo='"+brmst.getSuperBrNo()+"',"+
					               		"b.fax='"+brmst.getFax()+"',"+
					               		"b.brf='"+brmst.getBrf()+"',"+
					               		"b.isBusiness='"+brmst.getIsBusiness()+"'"+
					               		" where b.brNo='"+brmst.getBrNo()+"'"
					               		,null);

	}
	
	private String getHql(String managelvl) {

		if (managelvl.equals("4")) {
			statement = "from BrMst";
		} else if (managelvl.equals("3")) {
			statement = "from BrMst b where b.brNo='"+ brno+"' or b.superBrNo='"+ brno+"'" +
					" or b.brNo in (select br.brNo from BrMst br where br.brNo='"+ brno+"' or br.superBrNo='"+ brno+"'" +
					") or b.superBrNo in (select brm.brNo from BrMst brm where brm.brNo='"+ brno+"' or brm.superBrNo='"+ brno+"'" +
					" or brm.brNo in (select brms.brNo from BrMst brms where brms.brNo='"+ brno+"' or brms.superBrNo='"+ brno+"'))";
		} else if (managelvl.equals("2")) {
			statement = "from BrMst b where b.brNo='"+ brno+"' or b.superBrNo='"+ brno+"'" +
			" or b.brNo in (select br.brNo from BrMst br where br.brNo='"+ brno+"' or br.superBrNo='"+ brno+"')";
		} else if (managelvl.equals("1")) {
			statement = "from BrMst b where b.brNo='"+ brno+"' or b.superBrNo='"+ brno+"'";
		} else if (managelvl.equals("0")) {
			statement = "from BrMst b where b.brNo='"+ brno+"'";
		} else if (managelvl==null){
			statement = "from BrMst b where 1=2";
		}
		return statement;
	}
	//删除
	public void remove(String brno){
		super.delete("delete from BrMst where brNo='"+brno+"'", null);
	}
	@Override
	public PageUtil queryPage(String hsql, Object[] values, int pageSize,
			int currentPage, int rowsCount, String pageName) {
		// TODO Auto-generated method stub
		if(rowsCount<0){
			rowsCount=this.query(hsql, values).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		List list=this.query(hsql, values, pageSize, currentPage);
		
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
	}
	
    public String formartPageLine(int pageSize, int currentPage, int rowsCount,String pageName){
		
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		StringBuffer buffer=new StringBuffer();
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		
		if(currentPage==1){
			buffer.append("首页&nbsp;");
			buffer.append("上一页&nbsp;");
		}
		else{
			buffer.append("<a href=\""+ pageName +"&page=1&rowsCount="+ rowsCount +"\">首页</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"&page="+ (currentPage-1) +"&rowsCount="+ rowsCount +"\">上一页</a>&nbsp;");
		}
		
		if(currentPage==pageCount){
			buffer.append("下一页&nbsp;");
			buffer.append("末页");
		}
		else{
			buffer.append("<a href=\""+ pageName +"&page="+ (currentPage+1) +"&rowsCount="+ rowsCount +"\">下一页</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"&page="+ pageCount +"&rowsCount="+ rowsCount +"\">末页</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;共检索出"+ rowsCount +"条数据，每页"+ pageSize +"条数据，页次<font color='red'>"+ currentPage+"</font>/"+ pageCount);
		
		//buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;跳转到：");
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "?page='+this.value+'&rowsCount='+"+ rowsCount +")\">\r\n");
		for(int i=0;i<pageCount;i++){
			String selected="";
			if(i==currentPage-1){
				selected="selected";
			}
			buffer.append("<option "+ selected +" value=\""+ (i+1) +"\">第"+ (i+1) +"页</option>\r\n");
		}
		buffer.append("</select>");
		
		
		return buffer.toString();
	}
	
}
