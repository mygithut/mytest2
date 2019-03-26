package com.dhcc.ftp.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dhcc.ftp.util.PageUtil;

public class FtpSylqxDAO extends DaoFactory {
	private static final Log log = LogFactory.getLog(FtpPoolDAO.class);
	
	private PageUtil FtpSylqxUtil = null;
	private int pageSize;
	private String hql = null;
	public PageUtil dofind(int page,String curveName,String curveType,String curNo,Date curveDate) {
		String pageName = "";
		pageSize = 10;
		hql = "from FtpSylqx a where 1=1";
		if(curveName!=null&&!curveName.equals(""))
			hql+=" and a.curveName = '"+ curveName.trim() +"' ";
		if(curveType!=null&&!curveType.equals(""))
			hql+=" and a.curveType = '"+ curveType.trim() +"' ";
		if(curNo!=null&&!curNo.equals(""))
			hql+=" and a.curNo = '"+ curNo.trim() +"' ";
		if(curveDate!=null&&!curveDate.equals(""))
			hql+=" and a.curveDate = '"+ curveDate +"' ";
		hql+=" order by curveNo asc";
		pageName = "SYLQX.action?flag=list";
		FtpSylqxUtil = this.queryPage(hql, null, pageSize, page, -1,pageName);
		return FtpSylqxUtil;
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
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "&page='+this.value+'&rowsCount='+"+ rowsCount +")\">\r\n");
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