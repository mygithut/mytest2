package com.dhcc.ftp.dao;
/**
 * @desc:数据管理：政策性金融债收益率维护DA0
 * @author :孙红玉
 * @date ：2012-03-28
 */
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dhcc.ftp.util.PageUtil;

public class FtpFinacialDAO extends DaoFactory {
	private DaoFactory factory=new DaoFactory();
	private static final Log log = LogFactory.getLog(FtpFinacialDAO.class);
	
	private PageUtil FtpStockUtil = null;
	private int pageSize;
	private String hql = null;
	public PageUtil dofind(int page) {
		String pageName = "";
		pageSize = 10;
		hql = "from FtpFinacialRate order by finacialDate desc,to_number(substr(financial_term,1,LENGTH(financial_term)-1))";//to_number(finacialTerm)
		pageName = "ZCXJRZWH_List.action?";
		FtpStockUtil = this.queryPage(hql, null, pageSize, page, -1,pageName);
		return FtpStockUtil;
	}
	public PageUtil queryPage(String hsql, Object[] values, int pageSize,
			int currentPage, int rowsCount, String pageName) {
		// TODO Auto-generated method stub
		DaoFactory df = new DaoFactory();
		if(rowsCount<0){
			rowsCount=df.query(hsql, values).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		List list=df.query(hsql, values, pageSize, currentPage);
		
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
		//System.out.println("currentPage"+currentPage);
		
		if(currentPage==1){
			buffer.append("首页&nbsp;");
			buffer.append("上一页&nbsp;");
		}
		else{
			buffer.append("<a href=\""+ pageName +"page=1&rowsCount="+ rowsCount +"\">首页</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"page="+ (currentPage-1) +"&rowsCount="+ rowsCount +"\">上一页</a>&nbsp;");
		}
		
		if(currentPage==pageCount){
			buffer.append("下一页&nbsp;");
			buffer.append("末页");
		}
		else{
			buffer.append("<a href=\""+ pageName +"page="+ (currentPage+1) +"&rowsCount="+ rowsCount +"\">下一页</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"page="+ pageCount +"&rowsCount="+ rowsCount +"\">末页</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;共检索出"+ rowsCount +"条数据，每页"+ pageSize +"条数据，页次<font color='red'>"+ currentPage+"</font>/"+ pageCount);
		
		//buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;跳转到：");
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "page='+this.value+'&rowsCount='+"+ rowsCount +")\">\r\n");
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
