<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>存贷款盈利分析</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxReport> cpylfxSfbReportList = (List<YlfxReport>)session.getAttribute("cpylfxSfbReportList");
String prdtType = (String)session.getAttribute("prdtType");
String prcMode = (String)session.getAttribute("prcMode");
%>
<br/>
<%if(cpylfxSfbReportList == null) { %>
   <p>定价策略或资金池未配置！！</p>
<%}else{ %>
定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
<table id="tableList">
					
     <%if(prdtType.equals("ck")) { %>
     <thead>
     <tr >
      <th align="center" width="100" >存款类别</th>
      <th align="center" width="220" >产品名称</th>
      <th align="center" width="75" >余额</th>
      <th align="center" width="75" >利息支出</th>
      <th align="center" width="70" >付息率(%)</th>
      <th align="center" width="80" >平均期限(天)</th>
      <th align="center" width="100" >负债转移价格(%)</th>
      <th align="center" width="90" >负债净利差(%)</th>
      <th align="center" width="80" >负债转移收入</th>
      <th align="center" width="80" >负债净收入</th>
       </tr>
       </thead>
       <tbody>
<%--       <tr>--%>
<%--        <td align="center" rowspan="7">个人存款</td>--%>
<%--       <% YlfxReport entity1 = cpylfxSfbReportList.get(0);%>--%>
<%--		<td align="center"><%=entity1.getPrdtName()%></td>--%>
<%--		<td align="center"><%=entity1.getFzye() == null ? 0.00 : FormatUtil.toMoney(entity1.getFzye()/10000)%></td>--%>
<%--		<td align="center"><%=entity1.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entity1.getLxzc()/10000)%></td>--%>
<%--		<td align="center"><%=entity1.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entity1.getFxl()*100,3)%></td>--%>
<%--		<td align="center"><%=entity1.getFzpjqx() == null ? 0.00 : entity1.getFzpjqx()%></td>--%>
<%--		<td align="center"><%=entity1.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entity1.getFzzyjg()*100,3)%></td>--%>
<%--		<td align="center"><%=entity1.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entity1.getFzjlc()*100,3)%></td>--%>
<%--		<td align="center"><%=entity1.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entity1.getFzzysr()/10000)%></td>--%>
<%--		<td align="center"><%=entity1.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entity1.getFzjsr()/10000)%></td>--%>
<%--       </tr>--%>
       <%for(int i = 0; i < 7; i++) {  %>
       <tr>
       <%YlfxReport entity = cpylfxSfbReportList.get(i); %>
        <td align="center">个人存款</td>
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
<%--       <tr>--%>
<%--        <td align="center" rowspan="10">单位存款</td>--%>
<%--       <% YlfxReport entity8 = cpylfxSfbReportList.get(7);%>--%>
<%--		<td align="center"><%=entity8.getPrdtName()%></td>--%>
<%--		<td align="center"><%=entity8.getFzye() == null ? 0.00 : FormatUtil.toMoney(entity8.getFzye()/10000)%></td>--%>
<%--		<td align="center"><%=entity8.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entity8.getLxzc()/10000)%></td>--%>
<%--		<td align="center"><%=entity8.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entity8.getFxl()*100,3)%></td>--%>
<%--		<td align="center"><%=entity8.getFzpjqx() == null ? 0.00 : entity8.getFzpjqx()%></td>--%>
<%--		<td align="center"><%=entity8.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entity8.getFzzyjg()*100,3)%></td>--%>
<%--		<td align="center"><%=entity8.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entity8.getFzjlc()*100,3)%></td>--%>
<%--		<td align="center"><%=entity8.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entity8.getFzzysr()/10000)%></td>--%>
<%--		<td align="center"><%=entity8.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entity8.getFzjsr()/10000)%></td>--%>
<%--       </tr>--%>
       <tr>
       <%for(int i = 7; i < 16; i++) {  %>
       <tr>
       <%YlfxReport entity = cpylfxSfbReportList.get(i); %>
        <td align="center">单位存款</td>
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
        <tr>
       <%YlfxReport entity17 = cpylfxSfbReportList.get(16); %>
		<td align="center">单位通知存款</td>
		<td align="center"><%=entity17.getPrdtName()%></td>
		<td align="center"><%=entity17.getFzye() == null ? 0.00 : FormatUtil.toMoney(entity17.getFzye()/10000)%></td>
		<td align="center"><%=entity17.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entity17.getLxzc()/10000)%></td>
		<td align="center"><%=entity17.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entity17.getFxl()*100,3)%></td>
		<td align="center"><%=entity17.getFzpjqx() == null ? 0.00 : entity17.getFzpjqx()%></td>
		<td align="center"><%=entity17.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entity17.getFzzyjg()*100,3)%></td>
		<td align="center"><%=entity17.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entity17.getFzjlc()*100,3)%></td>
		<td align="center"><%=entity17.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entity17.getFzzysr()/10000)%></td>
		<td align="center"><%=entity17.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entity17.getFzjsr()/10000)%></td>
       </tr>
       <tr>
       <% YlfxReport entity18 = cpylfxSfbReportList.get(17);%>
        <td align="center">同业存款</td>
		<td align="center"><%=entity18.getPrdtName()%></td>
		<td align="center"><%=entity18.getFzye() == null ? 0.00 : FormatUtil.toMoney(entity18.getFzye()/10000)%></td>
		<td align="center"><%=entity18.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entity18.getLxzc()/10000)%></td>
		<td align="center"><%=entity18.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entity18.getFxl()*100,3)%></td>
		<td align="center"><%=entity18.getFzpjqx() == null ? 0.00 : entity18.getFzpjqx()%></td>
		<td align="center"><%=entity18.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entity18.getFzzyjg()*100,3)%></td>
		<td align="center"><%=entity18.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entity18.getFzjlc()*100,3)%></td>
		<td align="center"><%=entity18.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entity18.getFzzysr()/10000)%></td>
		<td align="center"><%=entity18.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entity18.getFzjsr()/10000)%></td>
       </tr>
       <tr>
       <% YlfxReport entity19 = cpylfxSfbReportList.get(18);%>
        <td align="center">同业存款</td>
		<td align="center"><%=entity19.getPrdtName()%></td>
		<td align="center"><%=entity19.getFzye() == null ? 0.00 : FormatUtil.toMoney(entity19.getFzye()/10000)%></td>
		<td align="center"><%=entity19.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entity19.getLxzc()/10000)%></td>
		<td align="center"><%=entity19.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entity19.getFxl()*100,3)%></td>
		<td align="center"><%=entity19.getFzpjqx() == null ? 0.00 : entity19.getFzpjqx()%></td>
		<td align="center"><%=entity19.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entity19.getFzzyjg()*100,3)%></td>
		<td align="center"><%=entity19.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entity19.getFzjlc()*100,3)%></td>
		<td align="center"><%=entity19.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entity19.getFzzysr()/10000)%></td>
		<td align="center"><%=entity19.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entity19.getFzjsr()/10000)%></td>
       </tr>
       </tbody>
       <%}else if(prdtType.equals("dk")){ %>
       <thead>
       <tr >
      <th align="center" width="100" >贷款类别</th>
      <th align="center" width="220" >产品名称</th>
      <th align="center" width="75" >余额</th>
      <th align="center" width="75" >利息收入</th>
      <th align="center" width="70" >生息率(%)</th>
      <th align="center" width="80" >平均期限(天)</th>
      <th align="center" width="100" >资产转移价格(%)</th>
      <th align="center" width="90" >资产净利差(%)</th>
      <th align="center" width="80" >资产转移支出</th>
      <th align="center" width="80" >资产净收入</th>
       </tr>
       </thead>
       <tbody>
<%--       <tr>--%>
<%--        <td align="center" rowspan="5">农户贷款</td>--%>
<%--       <% YlfxReport entity1 = cpylfxSfbReportList.get(0); %>--%>
<%--		<td align="center"><%=entity1.getPrdtName()%></td>--%>
<%--		<td align="center"><%=entity1.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity1.getZcye()/10000)%></td>--%>
<%--		<td align="center"><%=entity1.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity1.getLxsr()/10000)%></td>--%>
<%--		<td align="center"><%=entity1.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity1.getSxl()*100,3)%></td>--%>
<%--		<td align="center"><%=entity1.getZcpjqx() == null ? 0.00 : entity1.getZcpjqx()%></td>--%>
<%--		<td align="center"><%=entity1.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity1.getZczyjg()*100,3)%></td>--%>
<%--		<td align="center"><%=entity1.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity1.getZcjlc()*100,3)%></td>--%>
<%--		<td align="center"><%=entity1.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity1.getZczyzc()/10000)%></td>--%>
<%--		<td align="center"><%=entity1.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity1.getZcjsr()/10000)%></td>--%>
<%--       </tr>--%>
       <%for(int i = 0; i < 5; i++) {%>
       <tr>
       <%YlfxReport entity = cpylfxSfbReportList.get(i); %>
        <td align="center">农户贷款</td>
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
<%--       <tr>--%>
<%--        <td align="center" rowspan="5">农村经济组织贷款</td>--%>
<%--       <% YlfxReport entity6 = cpylfxSfbReportList.get(5);%>--%>
<%--		<td align="center"><%=entity6.getPrdtName()%></td>--%>
<%--		<td align="center"><%=entity6.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity6.getZcye()/10000)%></td>--%>
<%--		<td align="center"><%=entity6.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity6.getLxsr()/10000)%></td>--%>
<%--		<td align="center"><%=entity6.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity6.getSxl()*100,3)%></td>--%>
<%--		<td align="center"><%=entity6.getZcpjqx() == null ? 0.00 : entity6.getZcpjqx()%></td>--%>
<%--		<td align="center"><%=entity6.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity6.getZczyjg()*100,3)%></td>--%>
<%--		<td align="center"><%=entity6.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity6.getZcjlc()*100,3)%></td>--%>
<%--		<td align="center"><%=entity6.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity6.getZczyzc()/10000)%></td>--%>
<%--		<td align="center"><%=entity6.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity6.getZcjsr()/10000)%></td>--%>
<%--        </tr>--%>
       <%for(int i = 5; i < 10; i++) {  %>
       <tr>
       <%YlfxReport entity = cpylfxSfbReportList.get(i); %>
        <td align="center">农村经济组织贷款</td>
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
<%--       <tr>--%>
<%--        <td align="center" rowspan="5">农村企业贷款</td>--%>
<%--      <%  YlfxReport entity11 = cpylfxSfbReportList.get(10);%>--%>
<%--		<td align="center"><%=entity11.getPrdtName()%></td>--%>
<%--		<td align="center"><%=entity11.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity11.getZcye()/10000)%></td>--%>
<%--		<td align="center"><%=entity11.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity11.getLxsr()/10000)%></td>--%>
<%--		<td align="center"><%=entity11.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity11.getSxl()*100,3)%></td>--%>
<%--		<td align="center"><%=entity11.getZcpjqx() == null ? 0.00 : entity11.getZcpjqx()%></td>--%>
<%--		<td align="center"><%=entity11.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity11.getZczyjg()*100,3)%></td>--%>
<%--		<td align="center"><%=entity11.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity11.getZcjlc()*100,3)%></td>--%>
<%--		<td align="center"><%=entity11.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity11.getZczyzc()/10000)%></td>--%>
<%--		<td align="center"><%=entity11.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity11.getZcjsr()/10000)%></td>--%>
<%--       </tr>--%>
       <%for(int i = 10; i < 15; i++) {  %>
       <tr>
       <%YlfxReport entity = cpylfxSfbReportList.get(i); %>
        <td align="center">农村企业贷款</td>
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
<%--       <tr>--%>
<%--        <td align="center" rowspan="5">非农贷款</td>--%>
<%--       <% YlfxReport entity16 = cpylfxSfbReportList.get(15);%>--%>
<%--		<td align="center"><%=entity16.getPrdtName()%></td>--%>
<%--		<td align="center"><%=entity16.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity16.getZcye()/10000)%></td>--%>
<%--		<td align="center"><%=entity16.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity16.getLxsr()/10000)%></td>--%>
<%--		<td align="center"><%=entity16.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity16.getSxl()*100,3)%></td>--%>
<%--		<td align="center"><%=entity16.getZcpjqx() == null ? 0.00 : entity16.getZcpjqx()%></td>--%>
<%--		<td align="center"><%=entity16.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity16.getZczyjg()*100,3)%></td>--%>
<%--		<td align="center"><%=entity16.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity16.getZcjlc()*100,3)%></td>--%>
<%--		<td align="center"><%=entity16.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity16.getZczyzc()/10000)%></td>--%>
<%--		<td align="center"><%=entity16.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity16.getZcjsr()/10000)%></td>--%>
<%--       </tr>--%>
       <%for(int i = 15; i < 20; i++) {  %>
       <tr>
       <%YlfxReport entity = cpylfxSfbReportList.get(i); %>
        <td align="center">非农贷款</td>
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
       <tr>
        <td align="center">信用卡透支</td>
       <% YlfxReport entity21 = cpylfxSfbReportList.get(20);%>
		<td align="center"><%=entity21.getPrdtName()%></td>
		<td align="center"><%=entity21.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity21.getZcye()/10000)%></td>
		<td align="center"><%=entity21.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity21.getLxsr()/10000)%></td>
		<td align="center"><%=entity21.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity21.getSxl()*100,3)%></td>
		<td align="center"><%=entity21.getZcpjqx() == null ? 0.00 : entity21.getZcpjqx()%></td>
		<td align="center"><%=entity21.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity21.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity21.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity21.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity21.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity21.getZczyzc()/10000)%></td>
		<td align="center"><%=entity21.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity21.getZcjsr()/10000)%></td>
       </tr>
       <tr>
        <td align="center">贴现资产</td>
       <% YlfxReport entity22 = cpylfxSfbReportList.get(21);%>
		<td align="center"><%=entity22.getPrdtName()%></td>
		<td align="center"><%=entity22.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity22.getZcye()/10000)%></td>
		<td align="center"><%=entity22.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity22.getLxsr()/10000)%></td>
		<td align="center"><%=entity22.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity22.getSxl()*100,3)%></td>
		<td align="center"><%=entity22.getZcpjqx() == null ? 0.00 : entity22.getZcpjqx()%></td>
		<td align="center"><%=entity22.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity22.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity22.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity22.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity22.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity22.getZczyzc()/10000)%></td>
		<td align="center"><%=entity22.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity22.getZcjsr()/10000)%></td>
       </tr>
        <tr>
        <td align="center">垫款</td>
       <% YlfxReport entity23 = cpylfxSfbReportList.get(22);%>
		<td align="center"><%=entity23.getPrdtName()%></td>
		<td align="center"><%=entity23.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity23.getZcye()/10000)%></td>
		<td align="center"><%=entity23.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity23.getLxsr()/10000)%></td>
		<td align="center"><%=entity23.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity23.getSxl()*100,3)%></td>
		<td align="center"><%=entity23.getZcpjqx() == null ? 0.00 : entity23.getZcpjqx()%></td>
		<td align="center"><%=entity23.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity23.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity23.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity23.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity23.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity23.getZczyzc()/10000)%></td>
		<td align="center"><%=entity23.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity23.getZcjsr()/10000)%></td>
       </tr>
       </tbody>
       <%}else if(prdtType.equals("tzyw")){ %>
       <thead>
       <tr >
       <th align="center" width="120" >投资业务类别</th>
       <th align="center" width="200" >产品名称</th>
       <th align="center" width="80" >余额</th>
       <th align="center" width="80" >利息收入</th>
       <th align="center" width="80" >生息率(%)</th>
       <th align="center" width="80" >平均期限(天)</th>
       <th align="center" width="100" >资产转移价格(%)</th>
       <th align="center" width="90" >资产净利差(%)</th>
       <th align="center" width="80" >资产转移支出</th>
       <th align="center" width="80" >资产净收入</th>
        </tr>
        </thead>
        <tbody>
        <tr>
         <td align="center">交易性金融资产</td>
        <% YlfxReport entity1 = cpylfxSfbReportList.get(0);%>
 		<td align="center"><%=entity1.getPrdtName()%></td>
 		<td align="center"><%=entity1.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity1.getZcye()/10000)%></td>
		<td align="center"><%=entity1.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity1.getLxsr()/10000)%></td>
		<td align="center"><%=entity1.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity1.getSxl()*100,3)%></td>
		<td align="center"><%=entity1.getZcpjqx() == null ? 0.00 : entity1.getZcpjqx()%></td>
		<td align="center"><%=entity1.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity1.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity1.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity1.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity1.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity1.getZczyzc()/10000)%></td>
		<td align="center"><%=entity1.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity1.getZcjsr()/10000)%></td>
       </tr>
        <tr>
         <td align="center">持有至到期投资</td>
        <% YlfxReport entity2 = cpylfxSfbReportList.get(1);%>
 		<td align="center"><%=entity2.getPrdtName()%></td>
 		<td align="center"><%=entity2.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity2.getZcye()/10000)%></td>
		<td align="center"><%=entity2.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity2.getLxsr()/10000)%></td>
		<td align="center"><%=entity2.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity2.getSxl()*100,3)%></td>
		<td align="center"><%=entity2.getZcpjqx() == null ? 0.00 : entity2.getZcpjqx()%></td>
		<td align="center"><%=entity2.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity2.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity2.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity2.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity2.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity2.getZczyzc()/10000)%></td>
		<td align="center"><%=entity2.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity2.getZcjsr()/10000)%></td>
       </tr>
        <tr>
         <td align="center">可供出售金融资产</td>
        <% YlfxReport entity3 = cpylfxSfbReportList.get(2);%>
 		<td align="center"><%=entity3.getPrdtName()%></td>
 		<td align="center"><%=entity3.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity3.getZcye()/10000)%></td>
		<td align="center"><%=entity3.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity3.getLxsr()/10000)%></td>
		<td align="center"><%=entity3.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity3.getSxl()*100,3)%></td>
		<td align="center"><%=entity3.getZcpjqx() == null ? 0.00 : entity3.getZcpjqx()%></td>
		<td align="center"><%=entity3.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity3.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity3.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity3.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity3.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity3.getZczyzc()/10000)%></td>
		<td align="center"><%=entity3.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity3.getZcjsr()/10000)%></td>
       </tr>
        <tr>
         <td align="center">应收款项类投资</td>
        <% YlfxReport entity4 = cpylfxSfbReportList.get(3);%>
 		<td align="center"><%=entity4.getPrdtName()%></td>
 		<td align="center"><%=entity4.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity4.getZcye()/10000)%></td>
		<td align="center"><%=entity4.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity4.getLxsr()/10000)%></td>
		<td align="center"><%=entity4.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity4.getSxl()*100,3)%></td>
		<td align="center"><%=entity4.getZcpjqx() == null ? 0.00 : entity4.getZcpjqx()%></td>
		<td align="center"><%=entity4.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity4.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity4.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity4.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity4.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity4.getZczyzc()/10000)%></td>
		<td align="center"><%=entity4.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity4.getZcjsr()/10000)%></td>
       </tr></tbody>
        <%} %>
</table>
<%} %>
<div align="center">
<input type="button" name="Submit1" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="导&nbsp;&nbsp;出"/>
<input type="button" name="Submit2" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					 onclick="javascript: history.back();" value="返&nbsp;&nbsp;回"/>
		</div>
</form>
</body>
<script type="text/javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: '<%if(prdtType.equals("ck")) {out.print("存款");}else if(prdtType.equals("dk")) {out.print("贷款");}else if(prdtType.equals("tzyw")) {out.print("投资");}
    			 %>业务盈利分析报表_试发布'});
});
j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/report_cpylfx_sfb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
