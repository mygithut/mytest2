<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<html>
<head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String filename = new String(("机构总盈利分析报表").getBytes("gb2312"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");  
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
String prcMode = (String)session.getAttribute("prcMode");
String date = (String)session.getAttribute("date");
String assessScopeText = (String)session.getAttribute("assessScopeText");
String brName = (String)session.getAttribute("brName");
String tjType = (String)session.getAttribute("tjType");
%>
<br/>
<%if(ylfxReportList == null) { %>
   <p>定价策略或资金池未配置！！</p>
<%}else{ %>
定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>
&nbsp;&nbsp;&nbsp;&nbsp;统计类型：<%=tjType.equals("1")?"增量":"存量" %>
&nbsp;&nbsp;&nbsp;&nbsp;机构：<%=brName %>
&nbsp;&nbsp;&nbsp;&nbsp;日期：<%=date %>
&nbsp;&nbsp;&nbsp;&nbsp;考核维度：<%=assessScopeText %>
&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
<table align="center" id="" border="1" >
     <tr>
      <th align="center" colspan="18"><font style="color:#333; font-size:18px;font-weight:bold">机构总盈利分析报表</font></th>
     </tr>
     <tr >
      <th align="center">机构</th>
      <th align="center"><font style="font-weight:bold">资产余额</font></th>
      <th align="center">利息收入</th>
      <th align="center">生息率(%)</th>
      <th align="center">平均期限(天)</th>
      <th align="center">资产转移价格(%)</th>
      <th align="center">资产净利差(%)</th>
      <th align="center">资产转移支出</th>
      <th align="center">资产净收入</th>
      <th align="center"><font style="font-weight:bold">负债余额</font></th>
      <th align="center">利息支出</th>
      <th align="center">付息率(%)</th>
      <th align="center">平均期限(天)</th>
      <th align="center">负债转移价格(%)</th>
      <th align="center">负债净利差(%)</th>
      <th align="center">负债转移收入</th>
      <th align="center">负债净收入</th>
      <th align="center"><font style="font-weight:bold">净收入</font></th>
       </tr>
     <% 
         for (YlfxReport entity:ylfxReportList){
     %>
        <tr>
     	<td align="center"><%=entity.getBrName()%></td>
		<td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(entity.getZcye()/10000)%></font></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getLxsr()/10000)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getSxl()*100,3)%></td>
		<td align="center"><%=entity.getZcpjqx()%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getZczyjg()*100,3)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getZcjlc()*100,3)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getZczyzc()/10000)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getZcjsr()/10000)%></td>
		<td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(entity.getFzye()/10000)%></font></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getLxzc()/10000)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFxl()*100,3)%></td>
		<td align="center"><%=entity.getFzpjqx()%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFzzyjg()*100,3)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFzjlc()*100,3)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFzzysr()/10000)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFzjsr()/10000)%></td>
		<td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(entity.getJsr()/10000)%></font></td>
		</tr>
     <% }
     %>
</table>
<%} %>
</form>
</body>
</html>
