<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.CommonFunctions"%>
<%
	List<YlfxReport> list = (List<YlfxReport>)request.getAttribute("list");
    String resValue = (String)request.getAttribute("resValue");
    System.out.println("resValue"+resValue);
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
		<td width="97%"><font style="font-size: 13px;">当前位置：资金转移定价>>单资金池</font></td>
	</tr>
</table>
<form id="form1" name="form1" method="get" action="">
<table width="1055" border="0" align="center"><tr>
<td align="center"><p class="style1">银行各机构盈利能力分析报表</p></td>
</tr>
<tr><td align="right">单位：百万元</td></tr></table>

<table class="table" align="center" id="" width="1055" >
     <tr >
      <th width="120" >机构</th>
      <th width="85" >贷款</th>
      <th width="85" >收益率</th>
      <th width="85" >利息收入</th>
      <th width="85" >存款</th>
      <th width="85" >付息率</th>
      <th width="85" >利息支出</th>
      <th width="85" >利差收入</th>
      <th width="85" >资金转移价格</th>
      <th width="85" >资金转移收入</th>
      <th width="85" >资金转移支出</th>
      <th width="85" >净收入</th>
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
		<td align="center"><%=resValue%>%</td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getCk()*Double.valueOf(resValue)/100,1)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getDk()*Double.valueOf(resValue)/100,1)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getLxsr()-entity.getLxzc()+entity.getCk()*Double.valueOf(resValue)/100-entity.getDk()*Double.valueOf(resValue)/100,1)%></td>
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
	window.location.href="<%=request.getContextPath()%>/distribute.action?url=UL01";
}
</script>
</html>
