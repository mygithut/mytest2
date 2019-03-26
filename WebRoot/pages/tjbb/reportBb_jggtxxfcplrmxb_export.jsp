<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<head>
<title>机构各条线下分产品FTP利润明细表</title>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
/* String filename = new String(("机构各条线下分产品FTP利润明细表").getBytes("GBK"),"ISO-8859-1");  */  
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("jggtxxfcplrmxbReportList");
String minDate = (String)session.getAttribute("jggtxxfcplrmxbMinDate");
String maxDate = (String)session.getAttribute("jggtxxfcplrmxbMaxDate");
String brName = (String)session.getAttribute("jgywtxlrhzbBrName");
String businessName = (String)session.getAttribute("jggtxxfcplrmxbBusinessName");
String exportName=(String)session.getAttribute("exportName");
String filename = new String((exportName).getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");

int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>数据统计错误，请联系管理员！！</p>
<%}else{ %>
<div align="center"><font style="font-size:20px;font-weight:bold;">机构各条线下<%=businessName%>业务分产品FTP利润明细表</font></div>
<div align="center">机构：<%= brName%>
&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;单位：万元，%(年利率)</div>
<table border="1">
     <tr >
      <th align="center" width="400" >产品名称</th>
      <th align="center" width="120" >余额</th>
      <th align="center" width="120" >日均余额</th>
      <th align="center" width="110" >利差(%)</th>
      <th align="center" width="120" >FTP利润</th>
      
       </tr>
     <%  double rjye=0, ftplr=0,bal=0;
         boolean isxj = false;//是否显示负债类小计
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
        	 rjye += entity.getRjye();
        	 ftplr += entity.getFtplr();
        	 bal+=entity.getBal();
     %>
        <tr>
     	<td align="center"><%=entity.getPrdtName()%></td>
     	<td align="center"><%=FormatUtil.toMoney(entity.getBal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getRjye()/10000)%></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getLc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFtplr()/10000)%></td>
		
		</tr>
	   <%} %>
     <tr>
     	<td align="center"><font style="font-weight: bold">合计</font></td>
     	<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(bal/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(rjye==0?0.00:ftplr/rjye*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr/10000)%></font></td>
		</tr>
				</table>
<%} %>
</form>
</body>

</html>
