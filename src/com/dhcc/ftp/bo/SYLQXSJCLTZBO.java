package com.dhcc.ftp.bo;

import java.util.List;

import com.dhcc.ftp.util.PageUtil;

public class SYLQXSJCLTZBO extends BaseBo{

	//sql��ҳ��ѯ
	public PageUtil sqlQueryPage(String startDate, String curveMarketType, String curveAssetsType, int pageSize,
				int currentPage, int rowsCount) {
			
		String pageName="SYLQXSJCLTZ_list.action?pageSize="+pageSize;
		//��CURVE_MARKET_TYPE,CURVE_ASSETS_TYPE,TERM_TYPE,BR_NO�����ȡ��������
		String sql = "select m.curve_market_type,m.curve_assets_type,m.term_type,m.br_no,m.adjust_date,m.adjust_value,m.adjust_id,m.rm from (select  t.curve_market_type,t.curve_assets_type,t.term_type,t.br_no,t.adjust_date,t.adjust_value,t.adjust_id" +
				", row_number() over(partition by CURVE_MARKET_TYPE,CURVE_ASSETS_TYPE,TERM_TYPE,BR_NO order by ADJUST_DATE desc ) rm " +
				"from ftp.ftp_yield_curve_adjust t where 1=1 ";
		if (startDate != null && !startDate.equals("")){
			sql +="and t.adjust_Date<='"+startDate+"'";
			pageName +="&startDate="+startDate;
		}
		sql+=		")m where rm=1 and  br_no='1800000009' ";
		if (curveMarketType != null && !curveMarketType.equals("")){
			sql+="and curve_Market_Type = '"+curveMarketType+"'";
			pageName +="&curveMarketType="+curveMarketType;
		}
		if (curveAssetsType != null && !curveAssetsType.equals("")){
			sql+="and curve_Assets_Type = '"+curveAssetsType+"'";
			pageName +="&curveAssetsType="+curveAssetsType;
		}
		
		sql +=" order by curve_Market_Type, curve_Assets_Type, adjust_id";
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
		
		sql = "select * from (select m.*, rownumber() over(order by m.curve_Market_Type, m.curve_Assets_Type, m.adjust_id) as rownumber from("+sql+") m ) where rownumber between "+start+" and "+end;
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
