package com.dhcc.ftp.dao;
/**
 * @desc:����������ά��DAO
 * @author :�����
 * @date ��2012-04-16
 */
import java.util.List;

import com.dhcc.ftp.bo.ReportBO;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.PageUtil;

public class UL04DAO extends DaoFactory {
	
	private PageUtil UL04Util = null;
	private int pageSize;
	private String sql = null;
	public PageUtil dofind(int page, Integer rowsCount, String brNo, String curveMarketType, String curveAssetsType, String curveDate, TelMst telMst) {
		String pageName = "";
		pageSize = 6;
		sql = "select m.*,b.br_name from (select substr(t.curve_no,1,4) curveNo, substr(t.curve_name,1,13), " +
				"t.curve_market_type, t.curve_assets_type, t.curve_date,t.br_no, " +
				"row_number() over(partition by br_no,substr(curve_no,1,4) order by curve_date desc, curve_No ) rn" +
				" from ftp.ftp_yield_curve t where 1=1 ";
		if (curveDate != null && !curveDate.equals("") && !curveDate.equals("null")) {
			sql += " and curve_date <= "+curveDate;
		}
		sql +=") m left join ftp.br_mst b on b.br_no=m.br_no  where rn=1 ";
		//sql = "select distinct substr(curve_no,1,4) curveNo, substr(curve_name,1,13), curve_market_type, curve_assets_type, curve_date from ftp_yield_curve where 1 = 1";
		if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {
			sql += " and m.br_no = '"+brNo+"'";
		}else if(Integer.valueOf(telMst.getBrMst().getManageLvl())<=2){//�������ʡ���磬Ҫ��ȡ��Ӧ�����������
			sql += " and m.br_no = '"+FtpUtil.getXlsBrNo(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl())+"'";
		}
		if (curveMarketType != null && !curveMarketType.equals("") && !curveMarketType.equals("null")) {
			sql += " and curve_market_type = '"+curveMarketType+"'";
		}
		if (curveAssetsType != null && !curveAssetsType.equals("") && !curveAssetsType.equals("null")) {
			sql += " and curve_assets_type = '"+curveAssetsType+"'";
		}
		
		sql += " order by m.br_no, curve_date desc, curveNo";
		pageName = "SYLQXLSCK_history_list.action?brNo="+brNo+"&curveMarketType="+curveMarketType+"&curveAssetsType="+curveAssetsType+"&curveDate="+curveDate;
		UL04Util = this.queryPage(sql, null, pageSize, page, rowsCount,pageName);
		return UL04Util;
	}
	public PageUtil queryPage(String sql, Object[] values, int pageSize,
			int currentPage, int rowsCount, String pageName) {
		// TODO Auto-generated method stub
		DaoFactory df = new DaoFactory();
		if(rowsCount<0){
			rowsCount=df.query1(sql, values).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		List list=df.query1(sql, values, pageSize, currentPage);
		
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
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "page='+this.value+'&rowsCount='+"+ rowsCount +")\">\r\n");
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
