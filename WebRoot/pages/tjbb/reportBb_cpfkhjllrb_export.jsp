<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<head>
<title>产品分客户经理FTP利润表</title>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String minDate = (String)session.getAttribute("cpfkhjllrbMinDate");
String maxDate = (String)session.getAttribute("cpfkhjllrbMaxDate");
String brName = (String)session.getAttribute("cpfkhjllrbBrName");
String businessName = (String)session.getAttribute("cpfkhjllrbBusinessName");
String prdtCtgName = (String)session.getAttribute("cpfkhjllrbPrdtCtgName");
String prdtCtgNo = (String)session.getAttribute("cpfkhjllrbPrdtCtgNo");
String prdtName = (String)session.getAttribute("cpfkhjllrbPrdtName");
String prdtNo = (String)session.getAttribute("cpfkhjllrbPrdtNo");
/* String filename = new String((((prdtNo==null||prdtNo.equals(""))?((prdtCtgNo==null||prdtCtgNo.equals(""))?businessName:prdtCtgName):prdtName)+"-产品分客户经理FTP利润表").getBytes("GBK"),"ISO-8859-1"); */
String exportName=(String)session.getAttribute("exportName");
String filename = new String((exportName).getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");   
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("cpfkhjllrbReportList");
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
<div align="center"><font style="font-size:20px;font-weight:bold;"><%=(prdtNo==null||prdtNo.equals(""))?((prdtCtgNo==null||prdtCtgNo.equals(""))?businessName:prdtCtgName):prdtName%>-产品分客户经理FTP利润表</font></div>
<div align="center">机构：<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;单位：万元，%(年利率)</div>
<table border="1">
     <tr >
      <th align="center" width="240" >客户经理名称</th>
      <th align="center" width="300" >机构名称</th>
      <th align="center" width="100" >余额</th>
      <th align="center" width="100" >日均余额</th>
      <th align="center" width="100" >利差(%)</th>
      <th align="center" width="100" >FTP利润</th>
      <th align="center" width="100" >FTP利润占比(%)</th>
      <th align="center" width="50" >排名</th>
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
     	<td align="center"><%=entity.getEmpName()%>[<%=entity.getEmpNo()%>]</td>
     	<td align="center"><%=entity.getBrName()%>[<%=entity.getBrNo()%>]</td>
		<td align="right"><%=FormatUtil.toMoney(entity.getBal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getRjye()/10000)%></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getLc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFtplr()/10000)%></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getFtplr()/zftplr*100,2)%></td>
		<td align="center"><%=i+1%></td>
		</tr>
		<%} %>
     <tr>
     	<td align="center"><font style="font-weight: bold">合计</font></td>
     	<td align="center"><font style="font-weight: bold">-</font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(bal/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(rjye==0?0.00:ftplr/rjye*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		<td align="center"><font style="font-weight: bold">-</font></td>
		</tr>
				</table>
<%} %>
</form>
</body>

</html>
