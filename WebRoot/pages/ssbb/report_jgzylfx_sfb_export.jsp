<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<html>
<head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String filename = new String(("������ӯ����������_�Է���").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");  
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
String prcMode = (String)session.getAttribute("prcMode");
String date = (String)session.getAttribute("date");
String assessScopeText = (String)session.getAttribute("assessScopeText");
String brName = (String)session.getAttribute("brName");
%>
<br/>
<%if(ylfxReportList == null) { %>
   <p>���۲��Ի��ʽ��δ���ã���</p>
<%}else{ %>
���۲��ԣ�<%if(prcMode.equals("1")){out.print("���ʽ��");
}else if(prcMode.equals("2")){out.print("˫�ʽ��");
}else if(prcMode.equals("3")){out.print("���ʽ��");
}else if(prcMode.equals("4")){out.print("����ƥ��");} %>
&nbsp;&nbsp;&nbsp;&nbsp;������<%=brName %>
&nbsp;&nbsp;&nbsp;&nbsp;���ڣ�<%=date %>
&nbsp;&nbsp;&nbsp;&nbsp;����ά�ȣ�<%=assessScopeText %>
&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ
<table align="center" id="" border="1" >
     <tr>
      <th align="center" colspan="18"><font style="color:#333; font-size:18px;font-weight:bold">������ӯ����������_�Է���</font></th>
     </tr>
     <tr >
      <th align="center">����</th>
      <th align="center"><font style="font-weight:bold">�ʲ����</font></th>
      <th align="center">��Ϣ����</th>
      <th align="center">��Ϣ��(%)</th>
      <th align="center">ƽ������(��)</th>
      <th align="center">�ʲ�ת�Ƽ۸�(%)</th>
      <th align="center">�ʲ�������(%)</th>
      <th align="center">�ʲ�ת��֧��</th>
      <th align="center">�ʲ�������</th>
      <th align="center"><font style="font-weight:bold">��ծ���</font></th>
      <th align="center">��Ϣ֧��</th>
      <th align="center">��Ϣ��(%)</th>
      <th align="center">ƽ������(��)</th>
      <th align="center">��ծת�Ƽ۸�(%)</th>
      <th align="center">��ծ������(%)</th>
      <th align="center">��ծת������</th>
      <th align="center">��ծ������</th>
      <th align="center"><font style="font-weight:bold">������</font></th>
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
