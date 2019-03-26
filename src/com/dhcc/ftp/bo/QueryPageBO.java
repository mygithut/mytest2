package com.dhcc.ftp.bo;

import java.util.List;

import com.dhcc.ftp.util.PageUtil;

public class QueryPageBO extends BaseBo{

	//hsql分页查询
	public PageUtil queryPage(String hsql, int pageSize,
			int currentPage, int rowsCount, String pageName) {
		// TODO Auto-generated method stub
		System.out.println("hsql"+hsql);
		if(rowsCount<0){
			rowsCount=daoFactory.query(hsql, null).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		List list=daoFactory.query(hsql, null, pageSize, currentPage);
		
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
	}
	
	//sql分页查询
	public PageUtil sqlQueryPage(String hsql, int pageSize,
			int currentPage, int rowsCount, String pageName) {
		// TODO Auto-generated method stub
		if(rowsCount<0){
			rowsCount = this.getCount(hsql);
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		List list=daoFactory.query1(hsql, null, pageSize, currentPage);
		
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
	}
	
	/**
	 * 获取总条数
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(String sql) {  
        Integer amount = 0;  
        String countSql = "select count(*) from ("+sql+")";  
        List list = daoFactory.query1(countSql, null);
        amount = (Integer)list.get(0);
        return amount;  
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
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "&page='+this.value+'&rowsCount="+ rowsCount +"')\">\r\n");
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
