<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
		<title>收益率曲线导出</title>
</head>
	<body>
<form name="form1" method="post">
<%response.setHeader("Content-disposition","attachment");
  double[] key = (double[])session.getAttribute("key");
  Double[] keyRate = (Double[])session.getAttribute("keyRate");
  Double[] COF1 = (Double[])session.getAttribute("COF1");
  Double[] VOF1 = (Double[])session.getAttribute("VOF1");
  Double[] COF2 = (Double[])session.getAttribute("COF2");
  Double[] VOF2 = (Double[])session.getAttribute("VOF2");
  Double[] COF3 = (Double[])session.getAttribute("COF3");
  Double[] VOF3 = (Double[])session.getAttribute("VOF3");
  Double[] COF4 = (Double[])session.getAttribute("COF4");
  Double[] VOF4 = (Double[])session.getAttribute("VOF4");
  String curveType = request.getParameter("curveType");
  String date = request.getParameter("date");
     %>
   <table width="800" align="center" cellspacing="0" cellpadding="0" border="1" id="">
   <tr align="center"><td colspan="8"><font style="font-size: 15px; font-weight: bold;"><%=date %> FTP定价关键点分析表—<%=curveType.equals("1")?"存款":"负债" %></font></td></tr>
	 <tr>
	   <td>序号</td>
	   <td>基准点</td>
	   <td>利率（%）</td>
	   <td>信用风险修正（%）</td>
	   <td>存款准备金修正（%）</td>
	   <td>期权修正（%）</td>
	   <td>政策性调整（%）</td>
	   <td>FTP（%）</td>
	 </tr>
	 <%for (int i = 0; i < key.length; i++) { %>
	 <tr>
	     <td><%=i+1 %></td>
	     <td><%=key[i] < 1 ? (int)(key[i]*30)+"天" : (key[i] < 12 ?  (int)key[i]+"个月" : (int)(key[i]/12)+"年") %></td>
	     <td><%=CommonFunctions.doublecut(keyRate[i]*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((VOF1[i]-keyRate[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((VOF2[i]-VOF1[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((VOF3[i]-VOF2[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((VOF4[i]-VOF3[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut(VOF4[i]*100, 4) %></td>
	 </tr>
	 <%} %>
	</table>
	<br/>
	<table width="800" align="center" cellspacing="0" cellpadding="0" border="1">
	<tr align="center"><td colspan="8"><font style="font-size: 15px; font-weight: bold;"><%=date %> FTP定价关键点分析表—<%=curveType.equals("1")?"贷款":"资产" %></font></td></tr>
	 <tr>
	   <td>序号</td>
	   <td>基准点</td>
	   <td>利率（%）</td>
	   <td>信用风险修正（%）</td>
	   <td>存款准备金修正（%）</td>
	   <td>期权修正（%）</td>
	   <td>政策性调整（%）</td>
	   <td>FTP（%）</td>
	 </tr>
	 <%for (int i = 0; i < key.length; i++) { %>
	 <tr>
	     <td><%=i+1 %></td>
	     <td><%=key[i] < 1 ? (int)(key[i]*30)+"天" : (key[i] < 12 ?  (int)key[i]+"个月" : (int)(key[i]/12)+"年") %></td>
	     <td><%=CommonFunctions.doublecut(keyRate[i]*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((COF1[i]-keyRate[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((COF2[i]-COF1[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((COF3[i]-COF2[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((COF4[i]-COF3[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut(COF4[i]*100, 4) %></td>
	 </tr>
	 <%} %>
	</table>
	</body>
</html>
