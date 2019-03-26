<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<head>
<title>客户经理分产品FTP利润表</title>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
/* String filename = new String(("客户经理分产品FTP利润表").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");  */
String exportName=(String)session.getAttribute("exportName");
String filename = new String((exportName).getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("khjlfcplrbReportList");
String minDate = (String)session.getAttribute("khjlfcplrbMinDate");
String maxDate = (String)session.getAttribute("khjlfcplrbMaxDate");
String empName = (String)session.getAttribute("khjlfcplrbEmpName");
String empNo = (String)session.getAttribute("khjlfcplrbEmpNo");

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
<div align="center"><font style="font-size:20px;font-weight:bold;">客户经理分产品FTP利润表</font></div>
<div align="center">客户经理：<%= empName%>[<%= empNo%>]
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;单位：元，%(年利率)</div>
<table border="1">
     <tr >
      <th align="center" width="300" >产品名称</th>
      <th align="center" width="120" >余额</th>
      <th align="center" width="120" >日均余额</th>
      <th align="center" width="100" >利差(%)</th>
      <th align="center" width="100" >FTP利润</th>
      <th align="center" width="100" >FTP利润占比(%)</th>
      <th align="center" width="100" >排名</th>
       </tr>
     <%  
         double rjye=0, ftplr=0, lc=0,bal=0;
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
        	 rjye += entity.getRjye();
        	 ftplr += entity.getFtplr();
        	 bal += entity.getBal();
     %>
        <tr>
     	<td align="center"><%=entity.getPrdtName()%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getBal())%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getRjye())%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getLc()*100,3)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFtplr())%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFtplr()/zftplr*100,2)%></td>
		<td align="center"><%=i+1%></td>
		</tr>
		<%} %>
     <tr>
     	<td align="center"><font style="font-weight: bold">合计</font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(bal)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=CommonFunctions.doublecut(rjye==0?0.00:ftplr/rjye*360/days*100,3)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		<td align="center"><font style="font-weight: bold">-</font></td>
		</tr>
				</table>
<%} %>
</form>
</body>
</html>
