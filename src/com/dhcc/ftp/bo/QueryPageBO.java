package com.dhcc.ftp.bo;

import java.util.List;

import com.dhcc.ftp.util.PageUtil;

public class QueryPageBO extends BaseBo{

	//hsql��ҳ��ѯ
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
	
	//sql��ҳ��ѯ
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
	 * ��ȡ������
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
			buffer.append("��ҳ&nbsp;");
			buffer.append("��һҳ&nbsp;");
		}
		else{
			buffer.append("<a href=\""+ pageName +"&page=1&rowsCount="+ rowsCount +"\">��ҳ</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"&page="+ (currentPage-1) +"&rowsCount="+ rowsCount +"\">��һҳ</a>&nbsp;");
		}
		
		if(currentPage==pageCount){
			buffer.append("��һҳ&nbsp;");
			buffer.append("ĩҳ");
		}
		else{
			buffer.append("<a href=\""+ pageName +"&page="+ (currentPage+1) +"&rowsCount="+ rowsCount +"\">��һҳ</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"&page="+ pageCount +"&rowsCount="+ rowsCount +"\">ĩҳ</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;��������"+ rowsCount +"�����ݣ�ÿҳ"+ pageSize +"�����ݣ�ҳ��<font color='red'>"+ currentPage+"</font>/"+ pageCount);
		
		//buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;��ת����");
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "&page='+this.value+'&rowsCount="+ rowsCount +"')\">\r\n");
		for(int i=0;i<pageCount;i++){
			String selected="";
			if(i==currentPage-1){
				selected="selected";
			}
			buffer.append("<option "+ selected +" value=\""+ (i+1) +"\">��"+ (i+1) +"ҳ</option>\r\n");
		}
		buffer.append("</select>");
		
		
		return buffer.toString();
	}
	
}
