<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<html>
<head>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String filename = new String(("����ӯ����������").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls"); 
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
String prcMode = (String)session.getAttribute("prcMode");
String date = (String)session.getAttribute("date");
String assessScopeText = (String)session.getAttribute("assessScopeText");
String brCountLvlText = (String)session.getAttribute("brCountLvlText");
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
&nbsp;&nbsp;&nbsp;&nbsp;����ͳ�Ƽ���<%=brCountLvlText %>
&nbsp;&nbsp;&nbsp;&nbsp;������<%=brName %>
&nbsp;&nbsp;&nbsp;&nbsp;���ڣ�<%=date %>
&nbsp;&nbsp;&nbsp;&nbsp;����ά�ȣ�<%=assessScopeText %>
&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ
<table border="1" align="center" id="" width="700" >
     <tr>
      <th align="center" colspan="4"><font style="color:#333; font-size:18px;font-weight:bold">����ӯ����������_�Է���</font></th>
     </tr>
     <tr >
      <th align="center" width="400" >��������</th>
      <th align="center" width="100" >�������</th>
      <th align="center" width="100" >������</th>
      <th align="center" width="100" >����</th>
       </tr>
     <% 
         for (int i = 0; i < ylfxReportList.size(); i++){
        	 YlfxReport entity = ylfxReportList.get(i);
     %>
        <tr>
     	<td align="center"><%=entity.getBrName()%></td>
		<td align="center"><%=entity.getBrNo()%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getJsr()/10000)%></td>
		<td align="center"><%=i+1%></td>
		</tr>
     <% }
     %>
</table>
<%} %>
</form>
</body>

<script type="text/javascript">
jQuery(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
</script>
</html>
