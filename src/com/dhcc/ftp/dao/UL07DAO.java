package com.dhcc.ftp.dao;
/**
 * @desc:����ƥ��DAO
 * @author :�����
 * @date ��2012-04-17
 */
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.entity.FtpQxppResult;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;

public class UL07DAO extends DaoFactory {
	
	private PageUtil UL07Util = null;
	private int pageSize;
	public PageUtil dofind(HttpServletRequest request, int page, int rowsCount, String brNo, String curNo, String businessNo, String prdtNo, String wrkTime1, String wrkTime2) {
		String pageName = "";
		pageSize = 100;
		String sql = "";
		List<FtpQxppResult> ftpQxppResultList = null;
		pageName = "UL07_List.action?isQuery=0&brNo="+brNo+"&curNo="+curNo+"&businessNo="+businessNo+"&prdtNo="+prdtNo+"&wrkTime1="+wrkTime1+"&wrkTime2="+wrkTime2+"";
		//isQuery ==1 ����ǵ������ѯ����ť�����ѯ���ݿ�
//		if (isQuery.equals("1")) {
			//����ac_id��ac_sqen�����ȡwrk_num������ڵ��С�
			/*sql ="select * from (select t.*, row_number() over(partition by t.ac_id,t.br_no,t.kmh order by t.wrk_num desc ) rn " +
					"from ftp_qxpp_result t ) where rn=1";*/
			sql ="select t.* from ftp.ftp_qxpp_result t where 1=1";//��Ϊ����ʱ�Ѿ����ˡ�����Ψһ�ԡ�����(wrk_numûʹ���ˣ���Ϊ1)�����Կ���ֱ�Ӳ�������
			if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {//������ƣ�brNo���벻Ϊ�գ�������ѡ�񣬷���ִ�в�ѯ
				sql +=" and t.br_no "+LrmUtil.getBrSql(brNo);
			}
			if (curNo != null && !curNo.equals("")&& !curNo.equals("null")) {
				sql +=" and t.cur_no = '"+curNo+"'";
			}
			if (businessNo != null && !businessNo.equals("")&& !businessNo.equals("null")) {
				sql +=" and t.business_no = '"+businessNo+"'";
			}
			if (prdtNo != null && !prdtNo.equals("")&& !prdtNo.equals("null")) {
				sql +=" and t.product_no = '"+prdtNo+"'";
			}
			//��������
			/*if (wrkTime1 != null && !wrkTime1.equals("")&& !wrkTime1.equals("null")) {
				sql +=" and wrk_time >= '"+wrkTime1+"000000"+"'";//���۽��������ǡ�����+ʱ�䡯14λ
			}
			if (wrkTime2 != null && !wrkTime2.equals("")&& !wrkTime2.equals("null")) {
				sql +=" and wrk_time <= '"+wrkTime2+"235959"+"'";//���۽��������ǡ�����+ʱ�䡯14λ
			}*/
			////�������ݿ�ϵͳ����
			if (wrkTime1 != null && !wrkTime1.equals("")&& !wrkTime1.equals("null")) {
				sql +=" and t.wrk_sys_date >= '"+wrkTime1+"'";
			}
			if (wrkTime2 != null && !wrkTime2.equals("")&& !wrkTime2.equals("null")) {
				sql +=" and t.wrk_sys_date <= '"+wrkTime2+"'";
			}
			
			sql += " order by t.br_no, t.business_no,t.product_no,t.ac_id,t.wrk_sys_date";
			UL07Util = this.sqlQueryPage(sql, pageSize, page, rowsCount, pageName);
			
//			DaoFactory df = new DaoFactory();
			List listdata = UL07Util.getList();
			ftpQxppResultList = new ArrayList<FtpQxppResult>();
			if (listdata != null && listdata.size() > 0) {
				for (int i = 0; i < listdata.size(); i++) {
					Object[] o = (Object[])listdata.get(i);
					FtpQxppResult ftpQxppResult = new FtpQxppResult();
					ftpQxppResult.setCurNo(o[1] == null? null : o[1].toString());
					ftpQxppResult.setBrNo(o[2] == null? null : o[2].toString());
					ftpQxppResult.setAcId(o[3] == null? null : o[3].toString());
					ftpQxppResult.setBusinessName(o[4] == null? null : o[4].toString());
					ftpQxppResult.setBusinessNo(o[5] == null? null : o[5].toString());
					ftpQxppResult.setProductName(o[6] == null? null : o[6].toString());
					ftpQxppResult.setProductNo(o[7] == null? null : o[7].toString());
					ftpQxppResult.setWrkTime(o[8] == null? null : o[8].toString());
					ftpQxppResult.setFtpPrice(o[9] == null? Double.NaN : Double.valueOf(o[9].toString()));
					ftpQxppResult.setMethodNo(o[10] == null? null : o[10].toString());
					ftpQxppResult.setCurveNo(o[11] == null? null : o[11].toString());
					ftpQxppResult.setWrkNum(o[12] == null? 0 : Integer.valueOf(o[12].toString()));
					ftpQxppResult.setFtpTelNo(o[15] == null? null : o[15].toString());
					ftpQxppResult.setOpnDate(o[20] == null? null : o[20].toString());
					ftpQxppResult.setAmt(o[21] == null? null : Double.valueOf(o[21].toString()));
					ftpQxppResult.setBal(o[22] == null? null : Double.valueOf(o[22].toString()));
					ftpQxppResult.setRate(o[23] == null? null : Double.valueOf(o[23].toString()));
					ftpQxppResult.setTerm(o[24] == null? null : Integer.valueOf(o[24].toString()));
					ftpQxppResult.setMtrDate(o[25] == null? null : o[25].toString());
					ftpQxppResult.setWrkSysDate(o[26] == null? null : o[26].toString());
					ftpQxppResultList.add(ftpQxppResult);
				}
			}
//				request.getSession().setAttribute("ftpQxppResultList",ftpQxppResultList);
//		}else {
//			//����ǵ���·��ķ�ҳ��ť�����session�л�ȡ
//			ftpQxppResultList = (List<FtpQxppResult>)request.getSession().getAttribute("ftpQxppResultList");
//		}
		
		UL07Util.setList(ftpQxppResultList);
		return UL07Util;
	}
		
	//sql��ҳ��ѯ
	public PageUtil sqlQueryPage(String hsql, int pageSize,
			int currentPage, int rowsCount, String pageName) {
		// TODO Auto-generated method stub
		if(rowsCount<0){
			String hsql1 = "select count(*) from ("+hsql+")";
			List list = this.query1(hsql1, null);
			rowsCount = Integer.valueOf(list.get(0).toString());
//			rowsCount=this.query1(hsql, null).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		List list=this.query1(hsql, null, pageSize, currentPage);
		
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
	}
//	public PageUtil queryPage(List dataList, int pageSize, int currentPage, String pageName) {
//		rowsCount=dataList.size();
//		pageSize=pageSize<1?12:pageSize;
//		currentPage=currentPage<1?1:currentPage;
//		int pageCount=rowsCount/pageSize;
//		pageCount=pageCount<1?1:pageCount;
//		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
//		currentPage=currentPage>pageCount?pageCount:currentPage;
//		int start=(currentPage-1)*pageSize;
//		int end=currentPage*pageSize-1;
//		if(currentPage==rowsCount/pageSize+1){
//			end=((currentPage-1)*pageSize)+rowsCount%pageSize-1;
//		}
//		List list=dataList.subList(start, end+1);
//		//List list=df.sqlQuery(sql, values, pageSize, currentPage);
//		
//		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
//		return new PageUtil(list,pageLine);
//		
//	}
	
    public String formartPageLine(int pageSize, int currentPage, int rowsCount,String pageName){
		pageSize=pageSize<1?102:pageSize;
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
		buffer.append("<input type=\"hidden\" name=\"currentPage\" id=\"currentPage\" value=\""+currentPage+"\"/>");
		buffer.append("<input type=\"hidden\" name=\"pageSize\" id=\"pageSize\" value=\""+pageSize+"\"/>");
		buffer.append("<input type=\"hidden\" id = \"rowsCount\" name = \"rowsCount\" value= \""+rowsCount+"\">");
		
		
		return buffer.toString();
	}
}
