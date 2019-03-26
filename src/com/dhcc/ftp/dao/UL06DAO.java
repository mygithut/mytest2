package com.dhcc.ftp.dao;
/**
 * @desc:期限匹配DAO
 * @author :孙红玉
 * @date ：2012-04-17
 */
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.entity.FtpBusinessInfo;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;

public class UL06DAO extends DaoFactory {
	
	private PageUtil UL06Util = null;
	private PageUtil UL06FtpUtil = null;
	private int pageSize;
	private int rowsCount;
	public PageUtil dofind(HttpServletRequest request, int page, String checkAll, String isQuery, String brNo, String prdtNo, String curNo, String businessNo, String opnDate1, String opnDate2, String mtrDate1, String mtrDate2) {
		String pageName = "";
		pageSize = 100;
		String sql = "";
		List<FtpBusinessInfo> ftpBusinessInfoList = null;
		pageName = "UL06_List.action?checkAll="+checkAll+"&isQuery=0&brNo="+brNo+"&curNo="+curNo+"&businessNo="+businessNo+"&opnDate1="+opnDate1+"&opnDate2="+opnDate2+"&mtrDate1="+mtrDate1+"&mtrDate2="+mtrDate2+"";
		//isQuery=1如果是点击“查询”按钮，则查询数据库
		if (isQuery.equals("1")) {
			sql ="select * from ftp.ftp_qxpp_business"; 
			sql += " where 1 = 1";//
			
			if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {//界面控制，brNo必须不为空，即必须选择，否则不执行查询，否则不执行查询
				sql +=" and br_no "+LrmUtil.getBrSql(brNo);
			}
			if (curNo != null && !curNo.equals("")&& !curNo.equals("null")) {
				sql +=" and cur_no = "+curNo;//字段类型变为整型
			}
			if (businessNo != null && !businessNo.equals("")&& !businessNo.equals("null")) {
				sql +=" and business_no = '"+businessNo+"'";
			}
			if (prdtNo != null && !prdtNo.equals("")&& !prdtNo.equals("null")) {
				sql +=" and prdt_no = '"+prdtNo+"'";
			}
			
			if (opnDate1 != null && !opnDate1.equals("")&& !opnDate1.equals("null")) {//界面控制，opnDate1必须不为空，即必须选择，不选也设置默认值
				sql +=" and ( (opn_date > '"+opnDate1+"'";
			}
			if (opnDate2 != null && !opnDate2.equals("")&& !opnDate2.equals("null")) {//界面控制，opnDate2必须不为空，即必须选择，不选也设置默认值
				sql +=" and opn_date <= '"+opnDate2+"') ";//or (business_no in('YW201','YW203','YW107'))---(prdt_no in ('P2010','P2038','P2056','P2001','P2055','P2031'))
			}			
			//二次实施新加：对于长期(1年以上-不含1年)贷款类的产品，需要在每年年初重定价---<视图创建时过滤掉账户状态不正常以及余额为0的>
			long sysDate=CommonFunctions.GetDBSysDate();
			if(sysDate%10000==110){//每年系统日期为1月10号时 对1月1号--10号的新发生业务定价，此时就把长期贷款类的产品按1月1号新生业务进行重定价
				sql +="or (substr(prdt_no,1,2)='P1' and (prdt_name like '%3年%' or prdt_name like '%5年%') )";
			}
			sql +=" )";
			//sql +=" and zhzt!='1'";//去掉销户账户--不能去掉，这样会漏掉本期内开户又本期内销户的账户
				
			sql += " order by br_no,business_no,prdt_no,ac_id";
			DaoFactory df = new DaoFactory();
			List listdata = df.query1(sql, null);
			ftpBusinessInfoList = new ArrayList<FtpBusinessInfo>();
			if (listdata != null && listdata.size() > 0) {
				for (int i = 0; i < listdata.size(); i++) {
					Object[] o = (Object[])listdata.get(i);
					FtpBusinessInfo ftpBusinessInfo = new FtpBusinessInfo();
					ftpBusinessInfo.setBrNo(o[0] == null? null : o[0].toString().trim());
					ftpBusinessInfo.setTel(o[1] == null? null : o[1].toString().trim());
					ftpBusinessInfo.setCurNo(o[2] == null? null : o[2].toString().trim());
					ftpBusinessInfo.setAcId(o[3] == null? null : o[3].toString().trim());
					ftpBusinessInfo.setCustomName(o[4] == null? null : o[4].toString().trim());
					ftpBusinessInfo.setBusinessNo(o[5] == null? null : o[5].toString().trim());
					ftpBusinessInfo.setBusinessName(FtpUtil.getBusinessName(ftpBusinessInfo.getBusinessNo()));
					ftpBusinessInfo.setPrdtNo(o[7] == null? null : o[7].toString().trim());
					ftpBusinessInfo.setPrdtName(o[8] == null? null : o[8].toString().trim());
					ftpBusinessInfo.setOpnDate(o[9] == null? null : o[9].toString().trim());
					ftpBusinessInfo.setAmt(o[10] == null? null : o[10].toString().trim());
					ftpBusinessInfo.setBal(o[11] == null? null : o[11].toString().trim());
					ftpBusinessInfo.setRate(o[12] == null? null : String.valueOf(Double.valueOf(o[12].toString().trim())/100));
					ftpBusinessInfo.setTerm(o[13] == null? null : o[13].toString());
					ftpBusinessInfo.setMtrDate(o[14] == null? null : o[14].toString().trim());
					//ftpBusinessInfo.setInterval(o[13] == null? null : o[13].toString().trim());
					ftpBusinessInfo.setIsZq(o[15] == null? null : o[15].toString().trim());//新加，‘是否是展期贷款’；定价结果要求保存此字段
					
					ftpBusinessInfo.setKmh(o[6] == null? null : o[6].toString().trim());//修改第二个o[6],原错误写的o[7]
					
					ftpBusinessInfo.setKhjl(o[16] == null? null : o[16].toString().trim());//客户经理,第三次实施新加
					ftpBusinessInfo.setSjsflx(o[17] == null? null : o[17].toString().trim());//'实际收/付利息',第三次实施新加
					
					ftpBusinessInfo.setCustNo(o[18] == null? null : o[18].toString().trim());//客户编号
					ftpBusinessInfo.setZhzt(o[19] == null? null : o[19].toString().trim());//账户状态
					ftpBusinessInfo.setFivSts(o[20] == null? null : o[20].toString().trim());//'五级分类状态',蚌埠实施新加
					ftpBusinessInfo.setZqrq(o[21] == null? null : o[21].toString().trim());//展期日期
					ftpBusinessInfo.setZqAmt(o[22] == null? null : o[22].toString().trim());//展期金额
					ftpBusinessInfo.setZqMtrDate(o[23] == null? null : o[23].toString().trim());//展期到期日期
					ftpBusinessInfoList.add(ftpBusinessInfo);
				}
			}
			request.getSession().setAttribute("ftpBusinessInfoList",ftpBusinessInfoList);
		}else {
			//如果是点击下方的分页按钮，则从session中获取
			ftpBusinessInfoList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftpBusinessInfoList");
		}
		
		UL06Util = this.queryPage(ftpBusinessInfoList, pageSize, page, pageName);
		return UL06Util;
	}
	public PageUtil doFY(HttpServletRequest request, int page, String date, List<FtpBusinessInfo> ftped_dataList, String url) {
		String pageName = "";
		pageSize = 100;
		pageName = "/ftp/"+url+".action?date="+date;
		UL06FtpUtil = this.queryPage(ftped_dataList, pageSize, page, pageName);
		return UL06FtpUtil;
	}
		
		
	public PageUtil queryPage(List dataList, int pageSize, int currentPage, String pageName) {
		rowsCount=dataList.size();
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		int start=(currentPage-1)*pageSize;
		int end=currentPage*pageSize-1;
		if(currentPage==rowsCount/pageSize+1){
			end=((currentPage-1)*pageSize)+rowsCount%pageSize-1;
		}
		List list=dataList.subList(start, end+1);
		//List list=df.sqlQuery(sql, values, pageSize, currentPage);
		
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
		
	}
	
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
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "&page='+this.value+'&rowsCount='+"+ rowsCount +")\">\r\n");
		for(int i=0;i<pageCount;i++){
			String selected="";
			if(i==currentPage-1){
				selected="selected";
			}
			buffer.append("<option "+ selected +" value=\""+ (i+1) +"\">第"+ (i+1) +"页</option>\r\n");
		}
		buffer.append("</select>");
		buffer.append("<input type=\"hidden\" name=\"currentPage\" id=\"currentPage\" value=\""+currentPage+"\"/>");
		buffer.append("<input type=\"hidden\" name=\"pageSize\" id=\"pageSize\" value=\""+pageSize+"\"/>");
		
		
		return buffer.toString();
	}
}
