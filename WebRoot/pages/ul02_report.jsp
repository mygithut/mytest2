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
		<td width="97%"><font style="font-size: 13px;">��ǰλ�ã��ʽ�ת�ƶ���>>˫�ʽ��</font></td>
	</tr>
</table>
<form id="form1" name="form1" method="get" action="">
<table width="1000" border="0" align="center"><tr>
<td align="center"><p class="style1">���и�����ӯ��������������</p></td>
</tr>
<tr><td align="right">��λ������Ԫ</td></tr></table>

<table class="table" align="center" id="" width="1000" >
     <tr >
      <th width="120" >����</th>
      <th width="70" >����</th>
      <th width="70" >������</th>
      <th width="70" >��Ϣ����</th>
      <th width="70" >���</th>
      <th width="70" >��Ϣ��</th>
      <th width="70" >��Ϣ֧��</th>
      <th width="70" >��������</th>
      <th width="80" >��ծת�Ƽ۸�</th>
      <th width="80" >��ծת������</th>
      <th width="80" >�ʲ�ת�Ƽ۸�</th>
      <th width="80" >�ʲ�ת��֧��</th>
      <th width="70" >������</th>
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
<div align="center"><input type="button" name="Submit1" value="��&nbsp;&nbsp;��" onclick="back()" class="button"/>
		</div>
</form>
</body>

<script type="text/javascript">
function back() {
	window.location.href="<%=request.getContextPath()%>/distribute.action?url=UL02";
}
</script>
</html>