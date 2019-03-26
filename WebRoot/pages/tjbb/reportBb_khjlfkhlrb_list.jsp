<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.*,com.dhcc.ftp.entity.YlfxBbReport"%>

<html>
<head>
<title>客户经理分客户FTP利润表</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
PageUtil khjlfkhlrbReportUtil = (PageUtil)session.getAttribute("khjlfkhlrbReportUtil");
List<YlfxBbReport> ylfxBbReportList = khjlfkhlrbReportUtil.getList();
String minDate = (String)session.getAttribute("khjlfkhlrbMinDate");
String maxDate = (String)session.getAttribute("khjlfkhlrbMaxDate");
String empNo = (String)session.getAttribute("khjlfkhlrbEmpNo");
String empName = (String)session.getAttribute("khjlfkhlrbEmpName");
Integer currentPage = (Integer)request.getAttribute("page");
Integer pageSize = (Integer)request.getAttribute("pageSize");
currentPage=currentPage==null?1:currentPage;
pageSize=pageSize==null?100:pageSize;

int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>数据统计错误，请联系管理员！！</p>
<%}else{ 
	double zftplr = 0;//总的ftp利润
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplr();
	}
%>
<div align="center">客户经理：<%= empName%>[<%= empNo%>]
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;单位：元，%(年利率)</div>
<table border="0" align="left" width="100%" cellpadding="0" cellspacing="0">
         <tr>
             <td>
				<table align="left" class="table" width="900" cellpadding="0" cellspacing="0" >
						<tr>
								<td class="middle_header" colspan="7">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">客户经理分客户FTP利润表
									</font>
								</td>
							</tr>
						<tr>
						      <th align="center" width="30%" >客户名称</th>
							<th align="right" width="15%" >余额</th>
						      <th align="right" width="15%" >日均余额</th>
						      <th align="right" width="10%" >利差(%)</th>
						      <th align="right" width="10%" >FTP利润</th>
						      <th align="right" width="10%" > FTP利润占比(%)</th>
						      <th align="center" width="10%" >排名</th>
                        </tr>
				</table>
			   </td>
			</tr>
			<tr>
			  	<td>
				<div align="center" style="height: 220px; overflow-y: auto">
					<table align="left" class="table" width="900" cellpadding="0" cellspacing="0" style="margin-top:-2px">
						   
					     <%  
					         double rjye=0, ftplr=0, lc=0,bal=0;
					         for (int i = 0; i < ylfxBbReportList.size(); i++){
					        	 YlfxBbReport entity = ylfxBbReportList.get(i);
					        	 rjye += entity.getRjye();
					        	 ftplr += entity.getFtplr();
								 bal += entity.getBal();
					     %>
                           <tr>
					     	<%-- <td align="center" width="30%" ><%=entity.getCustName()+"["+entity.getCustNo()+"]"%></td> --%>
					     	<td align="center" width="30%" ><%=entity.getCustName()%></td>
							   <td align="right" width="15%"><%=FormatUtil.toMoney(entity.getBal())%></td>
							<td align="right" width="15%"><%=FormatUtil.toMoney(entity.getRjye())%></td>
							<td align="right" width="10%"><%=CommonFunctions.doublecut(entity.getLc()*100,6)%></td>
							<td align="right" width="10%"><%=FormatUtil.toMoney(entity.getFtplr())%></td>
							<td align="right" width="10%"><%=CommonFunctions.doublecut(entity.getFtplr()/zftplr*100,2)%></td>
							<td align="center" width="10%"><%=currentPage==1?(i+1):(i+1+pageSize*(currentPage-1))%></td>
		                   </tr>
		                  <%} %>
     <tr>
     	<td align="center" width="30%"><font style="font-weight: bold">合计</font></td>
		 <td align="right" width="15%"><font style="font-weight: bold"><%=FormatUtil.toMoney(bal)%></font></td>
		<td align="right" width="15%"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye)%></font></td>
		<td align="right" width="10%"><font style="font-weight: bold"><%=CommonFunctions.doublecut(rjye==0?0.00:ftplr/rjye*360/days*100,6)%></font></td>
		<td align="right" width="10%"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr)%></font></td>
		<td align="right" width="10%"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		<td align="center" width="10%"><font style="font-weight: bold">-</font></td>
		</tr>
						</table>
			        </div>
				</td>
			  </tr>
			<tr>
				<td colspan="6">
					<table border="0" width="100%" class="tb1"
						style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
						align="center">
						<tr>
							<td align="right"><%=khjlfkhlrbReportUtil.getPageLine()%>
							</td>
						</tr>
					</table>

				</td>
			</tr>
	          <tr>
					<td>
                      <div align="center" width="1770">
                         <input type="button" name="Submit1" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="导&nbsp;&nbsp;出">
                      </div>
			        </td>
              </tr>
       </table>
        <%} %>
</form>
</body>

<script type="text/javascript">

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.location.href='<%=request.getContextPath()%>/REPORTBB_khjlfkhlrbExport.action';
}
<%-- function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_khjlfkhlrb_export.jsp';
	window.parent.document.thisform.submit();
} --%>

</script>
</html>
