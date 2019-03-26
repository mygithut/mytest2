<%@ page contentType="text/html;charset=GBK"%>
<%@page
	import="java.util.List,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.YlfxBbReport"%>
<html>
<head>
	<title>客户经理FTP利润明细表</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	<jsp:include page="../commonJs.jsp" />
</head>
<body>
	<form id="form1" name="form1" method="get" action="">
		<%
PageUtil khjllrmxbReportUtil = (PageUtil)session.getAttribute("khjllrmxbReportUtil");
List<YlfxBbReport> ylfxBbReportList = khjllrmxbReportUtil.getList();
String minDate = (String)session.getAttribute("khjllrmxbMinDate");
String maxDate = (String)session.getAttribute("khjllrmxbMaxDate");
String empName = (String)session.getAttribute("khjllrmxbEmpName");
String empNo = (String)session.getAttribute("khjllrmxbEmpNo");
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
			客户经理：<%= empName%>[<%= empNo%>] &nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
			&nbsp;&nbsp;&nbsp;&nbsp;单位：元，%(年利率)
		</div>
		<table border="0" align="left" width="100%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<div style="width: 1000px; height: 260px; overflow: auto;" id="dv">
						<table id="tableList" class="table" align="left" width="1560"
							cellpadding="0" cellspacing="0">
							<tr>
								<td class="middle_header" colspan="13">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">客户经理FTP利润明细表
									</font>
								</td>
							</tr>
							<tr>
								<th align="center" width="50" class="td_relative">
									序号
								</th>
								<th align="center" width="250" class="td_relative">
									账号
								</th>
								<th align="center" width="200" class="td_relative">
									产品名称
								</th>
								<th align="center" width="300" class="td_relative">
									客户名称
								</th>
								<th align="center" width="70" class="td_relative">
									开户日期
								</th>
								<th align="center" width="70" class="td_relative">
									到期日期
								</th>
								<th align="right" width="100" class="td_relative">
									余额
								</th>
								<th align="right" width="100" class="td_relative">
									日均余额
								</th>
								<th align="right" width="120" class="td_relative">
									日均余额积数
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
								<td align="center"><%=i+1%></td>
								<td align="center"><%=entity.getAcId()%></td>
								<td align="center"><%=entity.getPrdtName()%></td>
								<td align="center"><%=entity.getCustName()%></td>
								<td align="center"><%=(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate()%></td>
								<td align="center"><%=(entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate()%></td>
								<td align="right"><%=FormatUtil.toMoney(entity.getBal())%></td>
								<td align="right"><%=FormatUtil.toMoney(entity.getRjye())%></td>
								<td align="right"><%=FormatUtil.toMoney(entity.getRjye()*days)%></td>
								<td align="right"><%=CommonFunctions.doublecut(entity.getRate()*100,6)%></td>
								<td align="right"><%=entity.getFtp()==-999?"-":CommonFunctions.doublecut(entity.getFtp()*100,6)%></td>
								<td align="right"><%=CommonFunctions.doublecut(entity.getLc()*100,6)%></td>
								<td align="right"><%=FormatUtil.toMoney(entity.getFtplr())%></td>
							</tr>
							<%} %>
							<tr>
								<td align="center">
									<font style="font-weight: bold">合计</font>
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
									<font style="font-weight: bold"><%=FormatUtil.toMoney(rjye*days)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ratefz/rjye*100,3)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ftpfz/rjye*100,3)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ftplr/rjye*360/days*100,3)%></font>
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
				<td>
					<table border="0" width="100%" class="tb1"
						style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
						align="center">
						<tr>
							<td align="right"><%=khjllrmxbReportUtil.getPageLine()%>
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td>
					<div align="center" width="1770">
						<input type="button" name="Submit1"
							style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
							onClick="doExport()" value="导&nbsp;&nbsp;出">
					</div>
				</td>
			</tr>
			<%} %>
		</table>
		</form>
</body>

<script type="text/javascript">

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.location.href = '<%=request.getContextPath()%>/REPORTBB_khjllrmxbExport.action';
}
<%-- function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_khjllrmxb_export.jsp';
	window.parent.document.thisform.submit();
} --%>

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
