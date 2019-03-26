<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.CommonFunctions"%>
<%
	List<YlfxReport> list = (List<YlfxReport>)request.getAttribute("list");
Double resValue21 = (Double)request.getAttribute("resValue21");
    Double resValue22 = (Double)request.getAttribute("resValue22");
%>
<html>
<head>
<style type="text/css">
<!--
.style1 {
	font-size: 18px;
	font-weight: bold;
}
-->
</style>
<title></title>
<jsp:include page="commonJs.jsp" />
</head>
<body>
<table width="100%" cellpadding="0" cellspacing=0 align="center">
	<tr bgcolor="#D9F3B2">
		<td>&nbsp;&nbsp;<img src="/ftp/pages/images/house.gif"/></td>
		<td width="97%"><font style="font-size: 13px;">当前位置：资金转移定价>>多资金池</font></td>
	</tr>
</table>
<form id="form1" name="form1" method="get" action="">
<table width="1000" border="0" align="center"><tr>
<td align="center"><p class="style1">银行各机构盈利能力分析报表</p></td>
</tr>
<tr><td align="right">单位：百万元</td></tr></table>

<table class="table" align="center" id="" width="1000" >
     <tr >
      <th width="120" >机构</th>
      <th width="70" >贷款</th>
      <th width="70" >收益率</th>
      <th width="70" >利息收入</th>
      <th width="70" >存款</th>
      <th width="70" >付息率</th>
      <th width="70" >利息支出</th>
      <th width="70" >利差收入</th>
      <th width="80" >负债转移价格</th>
      <th width="80" >负债转移收入</th>
      <th width="80" >资产转移价格</th>
      <th width="80" >资产转移支出</th>
      <th width="70" >净收入</th>
       </tr>
     <% System.out.println(list);
         for (YlfxReport entity:list){
     %>
        <tr>
     	<td align="center"><%=entity.getBrName()%></td>
		<td align="center"><%=entity.getDk()%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getLxsr()/entity.getDk()*100,2)%>%</td>
		<td align="center"><%=entity.getLxsr()%></td>
		<td align="center"><%=entity.getCk()%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getLxzc()/entity.getCk()*100,2)%>%</td>
		<td align="center"><%=entity.getLxzc()%></td>
		<td align="center"><%=entity.getLxsr()-entity.getLxzc()%></td>
		<td align="center"><%=resValue22%>%</td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getCk()*resValue22/100,1)%></td>
		<td align="center"><%=resValue21%>%</td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getDk()*resValue21/100,1)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getLxsr()-entity.getLxzc()+entity.getCk()*resValue22/100-entity.getDk()*resValue21/100,1)%></td>
		</tr>
     <% }
     %>
</table>

<div align="center"><input type="button" name="Submit1" value="返&nbsp;&nbsp;回" onclick="back()" class="button"/>
		</div>
</form>
</body>

<script type="text/javascript">
function back() {
	window.location.href="<%=request.getContextPath()%>/distribute.action?url=UL03";
}
</script>
</html>
