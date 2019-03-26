<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<html>
<head>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String filename = new String(("机构盈利排名报表").getBytes("GBK"),"ISO-8859-1");   
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
   <p>定价策略或资金池未配置！！</p>
<%}else{ %>
定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>
&nbsp;&nbsp;&nbsp;&nbsp;机构统计级别：<%=brCountLvlText %>
&nbsp;&nbsp;&nbsp;&nbsp;机构：<%=brName %>
&nbsp;&nbsp;&nbsp;&nbsp;日期：<%=date %>
&nbsp;&nbsp;&nbsp;&nbsp;考核维度：<%=assessScopeText %>
&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
<table border="1" align="center" id="" width="700" >
     <tr>
      <th align="center" colspan="4"><font style="color:#333; font-size:18px;font-weight:bold">机构盈利排名报表_试发布</font></th>
     </tr>
     <tr >
      <th align="center" width="400" >机构名称</th>
      <th align="center" width="100" >机构编号</th>
      <th align="center" width="100" >净收入</th>
      <th align="center" width="100" >排名</th>
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
