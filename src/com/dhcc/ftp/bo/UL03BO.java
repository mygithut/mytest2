package com.dhcc.ftp.bo;

import java.util.List;

import com.dhcc.ftp.util.PageUtil;

public class UL03BO extends BaseBo{

	//sql分页查询
	public PageUtil sqlQueryPage(String brNo, int pageSize,
				int currentPage, int rowsCount) {
			
		// 按机构和prcmode分组后，取最新日期的定价结果
		String sql = "select * from "
				+ "(select b.br_name, a.curname, t.pool_no, t.Result_price, t.RES_DATE, t.br_no, "
				+ "row_number() over(partition by t.br_No,t.pool_no order by t.RES_DATE desc ) rn from ftp.Ftp_Result t "
				+ "left join ftp.br_mst b on t.br_no = b.br_no "
				+ "left join ftp.alm_cur a on t.cur_no = a.curno "
				+ "where prc_Mode ='3' and pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no) and t.cur_no = '01') "
				+ "where rn=1";

		String pageName = "UL03_getResultPrice.action?pageSize=" + pageSize;

		if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {
			sql += " and br_no = '" + brNo + "'";
			pageName += "&brNo=" + brNo;
		}
		sql += " order by br_no";
		if(rowsCount<0){
			rowsCount=daoFactory.query1(sql, null).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		int start=(currentPage-1)*pageSize+1;
		int end=currentPage*pageSize;
		if(currentPage==rowsCount/pageSize+1){
			end=((currentPage-1)*pageSize)+rowsCount%pageSize;
		}
		
		sql = "select * from (select m.*, rownumber() over(order by m.br_no) as rownumber from("+sql+") m ) where rownumber between "+start+" and "+end;
		List list=daoFactory.query1(sql, null);	
			
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
