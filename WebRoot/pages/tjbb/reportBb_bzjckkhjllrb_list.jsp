<%@ page contentType="text/html;charset=GBK"%>
<%@page
	import="java.util.List,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.YlfxBbReport"%>

<head>
	<title>保证金存款客户经理FTP利润明细表</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	<jsp:include page="../commonJs.jsp" />
</head>
<body>
	<form id="form1" name="form1" method="get" action="">
		<%
PageUtil bzjckkhjllrbReportUtil = (PageUtil)session.getAttribute("bzjckkhjllrbReportUtil");
List<YlfxBbReport> ylfxBbReportList = bzjckkhjllrbReportUtil.getList();
String minDate = (String)session.getAttribute("bzjckkhjllrbMinDate");
String maxDate = (String)session.getAttribute("bzjckkhjllrbMaxDate");
String empName = (String)session.getAttribute("bzjckkhjllrbEmpName");
String empNo = (String)session.getAttribute("bzjckkhjllrbEmpNo");
String brName = (String)session.getAttribute("bzjckkhjllrbBrName");
Integer currentPage = (Integer)request.getAttribute("page");
Integer pageSize = (Integer)request.getAttribute("pageSize");
currentPage=currentPage==null?1:currentPage;
pageSize=pageSize==null?100:pageSize;

int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
%>
		<br />
		<%if(ylfxBbReportList == null) { %>
		<p>
			数据统计错误，请联系管理员！！
		</p>
		<%}else{ 
	double zftplr = 0;//总的ftp利润
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplr();
	}
%>
		<div align="center">
			机构：<%= brName%>&nbsp;&nbsp;&nbsp;&nbsp;<%if(empNo!=null&&!empNo.equals("")){ %>客户经理：<%= empName%>[<%= empNo%>] <%} %>&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
			&nbsp;&nbsp;&nbsp;&nbsp;单位：元，%(年利率)
		</div>
		<table border="0" align="left" width="100%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<div style="width: 1000; height: 260; overflow: auto;" id="dv">
						<table id="tableList" class="table" align="left" width="1860" 
							cellpadding="0" cellspacing="0">
							<tr>
								<td class="middle_header" colspan="15">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">保证金存款客户经理FTP利润明细表
									</font>
								</td>
							</tr>
							<tr>
								<th align="center" width="50" class="td_relative">
									序号
								</th>
						<%--		<th align="center" width="200" class="td_relative">
									上级机构
								</th>--%>
								<th align="center" width="200" class="td_relative">
									机构名称
								</th>
								<th align="center" width="200" class="td_relative">
									客户经理名称
								</th>
								<th align="center" width="200" class="td_relative">
									账号
								</th>
								<th align="center" width="200" class="td_relative">
									产品名称
								</th>
								<th align="center" width="150" class="td_relative">
									客户名称
								</th>
								<th align="center" width="70" class="td_relative">
									开户日期
								</th>
								<th align="center" width="70" class="td_relative">
									到期日期
								</th>
								<th align="right" width="70" class="td_relative">
									期限
								</th>
								<th align="right" width="100" class="td_relative">
									余额
								</th>
								<th align="right" width="100" class="td_relative">
									日均余额
								</th>
								<th align="right" width="70" class="td_relative">
									利率(%)
								</th>
								<th align="right" width="80" class="td_relative">
									FTP价格(%)
								</th>
								<th align="right" width="70" class="td_relative">
									利差(%)
								</th>
								<th align="right" width="80" class="td_relative">
									FTP利润
								</th>
							</tr>

							<%  
         double rjye=0, ftplr=0, ratefz=0,ftpfz=0,bal=0;
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
        	 ratefz += entity.getRjye()*entity.getRate();
        	 ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
        	 rjye += entity.getRjye();
        	 ftplr += entity.getFtplr();
			 bal += entity.getBal();
     %>
							<tr>
								<td width="50" align="center"><%=i+1%></td>
<%--								<td width="200" align="center"><%=entity.getEmpSuperBrName()+"["+entity.getEmpSuperBrNo()+"]"%></td>--%>
								<td width="200" align="center"><%=entity.getBrName()+"["+entity.getBrNo()+"]"%></td>
								<td width="200" align="center"><%=entity.getEmpName()+"["+entity.getEmpNo()+"]"%></td>
								<td width="200" align="center"><%=entity.getAcId()%></td>
								<td width="200" align="center"><%=entity.getPrdtName()%></td>
								<td width="150" align="center"><%=entity.getCustName()%></td>
								<td width="70" align="center"><%=(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate()%></td>
								<td width="70" align="center"><%=(entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate()%></td>
								<td width="70" align="center"><%=((entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))||(entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null")))?"-":String.valueOf(CommonFunctions.daysSubtract(entity.getMtrDate(), entity.getOpnDate()))%></td>
								<td width="100" align="right"><%=FormatUtil.toMoney(entity.getBal())%></td>
									<td width="100" align="right"><%=FormatUtil.toMoney(entity.getRjye())%></td>
								<td width="70" align="right"><%=CommonFunctions.doublecut(entity.getRate()*100,6)%></td>
								<td width="80" align="right"><%=entity.getFtp()==-999?"-":CommonFunctions.doublecut(entity.getFtp()*100,6)%></td>
								<td width="70" align="right"><%=CommonFunctions.doublecut(entity.getLc()*100,6)%></td>
								<td width="80" align="right"><%=FormatUtil.toMoney(entity.getFtplr())%></td>
							</tr>
							<%} %>
							<tr>
								<td align="center">
									<font style="font-weight: bold">合计</font>
								</td>
					<%--			<td align="center">
									<font style="font-weight: bold">-</font>
								</td>--%>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=FormatUtil.toMoney(bal)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=FormatUtil.toMoney(rjye)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ratefz/rjye*100,6)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ftpfz/rjye*100,6)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ftplr/rjye*360/days*100,6)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr)%></font>
								</td>
							</tr>


						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="12">
					<table border="0" width="100%" class="tb1"
						style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
						align="center">
						<tr>
							<td align="right"><%=bzjckkhjllrbReportUtil.getPageLine()%>
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td colspan="11">
					<div align="center" width="1770">
						<input type="button" name="Submit1"
							style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
							onClick="doExport()" value="导&nbsp;&nbsp;出">
					</div>
				</td>
			</tr>
			<%} %>
		</table>
</body>

<script type="text/javascript">

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	var page="<%=currentPage%>";
	window.location.href='<%=request.getContextPath()%>/REPORTBB_bzjckkhjllrbExport.action?page='+page;
}
</script>
<style type="text/css">
.td_relative,.middle_header {
	background-color: #CCCCCC;
	top: expression(document.getElementById ( "dv") . scrollTop-1 );
	position: relative;
	z-index: 1
}
</style>
</html>
