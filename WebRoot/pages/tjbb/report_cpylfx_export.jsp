<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<html>
<head>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String prdtType = (String)session.getAttribute("prdtType");
String tjType = (String)session.getAttribute("tjType");
String prdtName = "";
if(prdtType.equals("ck")) {prdtName="存款";
}else if(prdtType.equals("dk")) {prdtName="贷款";
}else if(prdtType.equals("tzyw")) {prdtName="投资";
}
String filename = new String((prdtName+"业务盈利分析报表").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");  
List<YlfxReport> cpylfxReportList = (List<YlfxReport>)session.getAttribute("cpylfxReportList");
String prcMode = (String)session.getAttribute("prcMode");
String date = (String)session.getAttribute("date");
String assessScopeText = (String)session.getAttribute("assessScopeText");
String brName = (String)session.getAttribute("brName");
%>
<br/>
<%if(cpylfxReportList == null) { %>
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
      <th align="center" colspan="10"><font style="color:#333; font-size:18px;font-weight:bold"><%if(prdtType.equals("ck")) {out.print("存款");}else if(prdtType.equals("dk")) {out.print("贷款");}else if(prdtType.equals("tzyw")) {out.print("投资");}
	 %>业务盈利分析报表</font></th>
     </tr>
     <%if(prdtType.equals("ck")) { %>
     <tr >
      <th align="center">产品大类名称</th>
      <th align="center">余额</th>
      <th align="center">利息支出</th>
      <th align="center">付息率(%)</th>
      <th align="center">平均期限(天)</th>
      <th align="center">负债转移价格(%)</th>
      <th align="center">负债净利差(%)</th>
      <th align="center">负债转移收入</th>
      <th align="center">负债净收入</th>
       </tr>
       <%for(int i = 0; i < cpylfxReportList.size(); i++) {  %>
       <tr>
       <%YlfxReport entity = cpylfxReportList.get(i); %>
		<td align="center"><%=entity.getPrdtName()%></td>
		<td align="center"><%=entity.getFzye() == null ? 0.00 : FormatUtil.toMoney(entity.getFzye()/10000)%></td>
		<td align="center"><%=entity.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entity.getLxzc()/10000)%></td>
		<td align="center"><%=entity.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entity.getFxl()*100,3)%></td>
		<td align="center"><%=entity.getFzpjqx() == null ? 0.00 : entity.getFzpjqx()%></td>
		<td align="center"><%=entity.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entity.getFzzyjg()*100,3)%></td>
		<td align="center"><%=entity.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entity.getFzjlc()*100,3)%></td>
		<td align="center"><%=entity.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entity.getFzzysr()/10000)%></td>
		<td align="center"><%=entity.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entity.getFzjsr()/10000)%></td>
       </tr>
       <%} %>
       <%}else if(prdtType.equals("dk")){ %>
       <tr >
      <th align="center" width="220" >产品大类名称</th>
      <th align="center" width="75" >余额</th>
      <th align="center" width="75" >利息收入</th>
      <th align="center" width="70" >生息率(%)</th>
      <th align="center" width="80" >平均期限(天)</th>
      <th align="center" width="100" >资产转移价格(%)</th>
      <th align="center" width="90" >资产净利差(%)</th>
      <th align="center" width="80" >资产转移支出</th>
      <th align="center" width="80" >资产净收入</th>
       </tr>
       <%for(int i = 0; i < cpylfxReportList.size(); i++) {%>
       <tr>
       <%YlfxReport entity = cpylfxReportList.get(i); %>
		<td align="center"><%=entity.getPrdtName()%></td>
		<td align="center"><%=entity.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity.getZcye()/10000)%></td>
		<td align="center"><%=entity.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity.getLxsr()/10000)%></td>
		<td align="center"><%=entity.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity.getSxl()*100,3)%></td>
		<td align="center"><%=entity.getZcpjqx() == null ? 0.00 : entity.getZcpjqx()%></td>
		<td align="center"><%=entity.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity.getZczyzc()/10000)%></td>
		<td align="center"><%=entity.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity.getZcjsr()/10000)%></td>
       </tr>
       <%} %>
        <%} %>
        
</table>
<%} %>
</form>
</body>
</html>
