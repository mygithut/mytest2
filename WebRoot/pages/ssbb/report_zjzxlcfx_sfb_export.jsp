<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<html>
<head>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String filename = new String(("�ʽ����������������_�Է���").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");  
YlfxReport ylfxReport = (YlfxReport)session.getAttribute("ylfxReport");
String prcMode = (String)session.getAttribute("prcMode");
String date = (String)session.getAttribute("date");
String assessScopeText = (String)session.getAttribute("assessScopeText");
String brName = (String)session.getAttribute("brName");
%>
<br/>
<%if(ylfxReport == null) { %>
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
<table border="1" align="center" id="" width="600">
     <tr>
      <th align="center" colspan="2"><font style="color:#333; font-size:18px;font-weight:bold">�ʽ����������������_�Է���</font></th>
     </tr>
     <tr >
      <td align="center"><font style="font-weight:bold">�ʲ����׶�</font></td>
      <td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(ylfxReport.getZcye()/10000)%></font></td>
     </tr>
     <tr >
      <td align="center">�ʲ�ת�Ƽ۸�(%)</td>
      <td align="center"><%=CommonFunctions.doublecut(ylfxReport.getZczyjg()*100,3)%></td>
     </tr>
     <tr >
      <td align="center">�ʲ�ת������</td>
      <td align="center"><%=FormatUtil.toMoney(ylfxReport.getZczyzc()/10000)%></td>
     </tr>
     <tr >
      <td align="center"><font style="font-weight:bold">��ծ���׶�</font></td>
      <td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(ylfxReport.getFzye()/10000)%></font></td>
     </tr>
     <tr >
      <td align="center">��ծת�Ƽ۸�(%)</td>
      <td align="center"><%=CommonFunctions.doublecut(ylfxReport.getFzzyjg()*100,3)%></td>
     </tr>
     <tr >
      <td align="center">��ծת��֧��</td>
      <td align="center"><%=FormatUtil.toMoney(ylfxReport.getFzzysr()/10000)%></td>
     </tr>
     <tr >
      <td align="center"><font style="font-weight:bold">������</font> </td>
      <td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney((ylfxReport.getZczyzc()-ylfxReport.getFzzysr())/10000)%></font></td>
     </tr>
</table>
<%} %>
</form>
</body>
</html>
