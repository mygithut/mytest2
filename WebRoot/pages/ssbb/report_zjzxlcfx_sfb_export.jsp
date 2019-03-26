<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<html>
<head>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String filename = new String(("资金中心利差分析报表_试发布").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");  
YlfxReport ylfxReport = (YlfxReport)session.getAttribute("ylfxReport");
String prcMode = (String)session.getAttribute("prcMode");
String date = (String)session.getAttribute("date");
String assessScopeText = (String)session.getAttribute("assessScopeText");
String brName = (String)session.getAttribute("brName");
%>
<br/>
<%if(ylfxReport == null) { %>
   <p>定价策略或资金池未配置！！</p>
<%}else{ %>
定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>
&nbsp;&nbsp;&nbsp;&nbsp;机构：<%=brName %>
&nbsp;&nbsp;&nbsp;&nbsp;日期：<%=date %>
&nbsp;&nbsp;&nbsp;&nbsp;考核维度：<%=assessScopeText %>
&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
<table border="1" align="center" id="" width="600">
     <tr>
      <th align="center" colspan="2"><font style="color:#333; font-size:18px;font-weight:bold">资金中心利差分析报表_试发布</font></th>
     </tr>
     <tr >
      <td align="center"><font style="font-weight:bold">资产交易额</font></td>
      <td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(ylfxReport.getZcye()/10000)%></font></td>
     </tr>
     <tr >
      <td align="center">资产转移价格(%)</td>
      <td align="center"><%=CommonFunctions.doublecut(ylfxReport.getZczyjg()*100,3)%></td>
     </tr>
     <tr >
      <td align="center">资产转移收入</td>
      <td align="center"><%=FormatUtil.toMoney(ylfxReport.getZczyzc()/10000)%></td>
     </tr>
     <tr >
      <td align="center"><font style="font-weight:bold">负债交易额</font></td>
      <td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(ylfxReport.getFzye()/10000)%></font></td>
     </tr>
     <tr >
      <td align="center">负债转移价格(%)</td>
      <td align="center"><%=CommonFunctions.doublecut(ylfxReport.getFzzyjg()*100,3)%></td>
     </tr>
     <tr >
      <td align="center">负债转移支出</td>
      <td align="center"><%=FormatUtil.toMoney(ylfxReport.getFzzysr()/10000)%></td>
     </tr>
     <tr >
      <td align="center"><font style="font-weight:bold">净收入</font> </td>
      <td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney((ylfxReport.getZczyzc()-ylfxReport.getFzzysr())/10000)%></font></td>
     </tr>
</table>
<%} %>
</form>
</body>
</html>
