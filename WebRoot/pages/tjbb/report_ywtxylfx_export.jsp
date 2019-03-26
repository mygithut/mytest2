<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<html>
<head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String filename = new String(("业务条线盈利分析报表").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");  
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
String prcMode = (String)session.getAttribute("prcMode");
String date = (String)session.getAttribute("date");
String assessScopeText = (String)session.getAttribute("assessScopeText");
String brName = (String)session.getAttribute("brName");
String tjType = (String)session.getAttribute("tjType");
%>
<br/>
<%if(ylfxReportList == null) { %>
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
<table border="1" align="center" id="">
     <tr>
      <th align="center" colspan="18"><font style="color:#333; font-size:18px;font-weight:bold">业务条线盈利分析报表</font></th>
     </tr>
     <tr >
      <th align="center" ><font style="font-weight:bold">业务名称</font></th>
      <th align="center">资产余额</th>
      <th align="center">利息收入</th>
      <th align="center">生息率(%)</th>
      <th align="center">平均期限(天)</th>
      <th align="center">资产转移价格(%)</th>
      <th align="center">资产净利差(%)</th>
      <th align="center">资产转移支出</th>
      <th align="center">资产净收入</th>
      <th align="center"><font style="font-weight:bold">业务名称</font></th>
      <th align="center">负债余额</th>
      <th align="center">利息支出</th>
      <th align="center">付息率(%)</th>
      <th align="center">平均期限(天)</th>
      <th align="center">负债转移价格(%)</th>
      <th align="center">负债净利差(%)</th>
      <th align="center">负债转移收入</th>
      <th align="center">负债净收入</th>
       </tr>
       <tr>
     <% //资产比负债多两行
         for (int i = 0; i < 6; i++){
        	 YlfxReport entityZc = ylfxReportList.get(i);
        	 YlfxReport entityFz = ylfxReportList.get(i+8);
     %>
		<td align="center">
		<font style="font-weight:bold"><%=entityZc.getBusinessName()%></font>
		</td>
		<td align="center"><%=entityZc.getZcye() == null ? 0.0 : FormatUtil.toMoney(entityZc.getZcye()/10000)%></td>
		<td align="center"><%=entityZc.getLxsr() == null ? 0.0 : FormatUtil.toMoney(entityZc.getLxsr()/10000)%></td>
		<td align="center"><%=entityZc.getSxl() == null ? 0.0 : CommonFunctions.doublecut(entityZc.getSxl()*100,2)%></td>
		<td align="center"><%=entityZc.getZcpjqx() == null ? 0.0 : entityZc.getZcpjqx()%></td>
		<td align="center"><%=entityZc.getZczyjg() == null ? 0.0 : CommonFunctions.doublecut(entityZc.getZczyjg()*100,2)%></td>
		<td align="center"><%=entityZc.getZcjlc() == null ? 0.0 : CommonFunctions.doublecut(entityZc.getZcjlc()*100,2)%></td>
		<td align="center"><%=entityZc.getZczyzc() == null ? 0.0 : FormatUtil.toMoney(entityZc.getZczyzc()/10000)%></td>
		<td align="center"><%=entityZc.getZcjsr() == null ? 0.0 : FormatUtil.toMoney(entityZc.getZcjsr()/10000)%></td>
		<td align="center">
		<font style="font-weight:bold"><%=entityFz.getBusinessName()%></font>
		</td>
		<td align="center"><%=entityFz.getFzye() == null ? 0.0 : FormatUtil.toMoney(entityFz.getFzye()/10000)%></td>
		<td align="center"><%=entityFz.getLxzc() == null ? 0.0 : FormatUtil.toMoney(entityFz.getLxzc()/10000)%></td>
		<td align="center"><%=entityFz.getFxl() == null ? 0.0 : CommonFunctions.doublecut(entityFz.getFxl()*100,2)%></td>
		<td align="center"><%=entityFz.getFzpjqx() == null ? 0.0 : entityFz.getFzpjqx()%></td>
		<td align="center"><%=entityFz.getFzzyjg() == null ? 0.0 : CommonFunctions.doublecut(entityFz.getFzzyjg()*100,2)%></td>
		<td align="center"><%=entityFz.getFzjlc() == null ? 0.0 : CommonFunctions.doublecut(entityFz.getFzjlc()*100,2)%></td>
		<td align="center"><%=entityFz.getFzzysr() == null ? 0.0 : FormatUtil.toMoney(entityFz.getFzzysr()/10000)%></td>
		<td align="center"><%=entityFz.getFzjsr() == null ? 0.0 : FormatUtil.toMoney(entityFz.getFzjsr()/10000)%></td>
		</tr>
     <% }
     %>
     <% 
        	 YlfxReport entityZc2 = ylfxReportList.get(6);
     %>
		<td align="center"><font style="font-weight:bold"><%=entityZc2.getBusinessName()%></font></td>
		<td align="center"><%=entityZc2.getZcye() == null ? 0.0 : FormatUtil.toMoney(entityZc2.getZcye()/10000)%></td>
		<td align="center"><%=entityZc2.getLxsr() == null ? 0.0 : FormatUtil.toMoney(entityZc2.getLxsr()/10000)%></td>
		<td align="center"><%=entityZc2.getSxl() == null ? 0.0 : CommonFunctions.doublecut(entityZc2.getSxl()*100,2)%></td>
		<td align="center"><%=entityZc2.getZcpjqx() == null ? 0.0 : entityZc2.getZcpjqx()%></td>
		<td align="center"><%=entityZc2.getZczyjg() == null ? 0.0 : CommonFunctions.doublecut(entityZc2.getZczyjg()*100,2)%></td>
		<td align="center"><%=entityZc2.getZcjlc() == null ? 0.0 : CommonFunctions.doublecut(entityZc2.getZcjlc()*100,2)%></td>
		<td align="center"><%=entityZc2.getZczyzc() == null ? 0.0 : FormatUtil.toMoney(entityZc2.getZczyzc()/10000)%></td>
		<td align="center"><%=entityZc2.getZcjsr() == null ? 0.0 : FormatUtil.toMoney(entityZc2.getZcjsr()/10000)%></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		</tr>
     <% 
     YlfxReport entityZc = ylfxReportList.get(7);
     %>
		<td align="center"><font style="font-weight:bold"><%=entityZc.getBusinessName()%></font></td>
		<td align="center"><%=entityZc.getZcye() == null ? 0.0 : FormatUtil.toMoney(entityZc.getZcye()/10000)%></td>
		<td align="center"><%=entityZc.getLxsr() == null ? 0.0 : FormatUtil.toMoney(entityZc.getLxsr()/10000)%></td>
		<td align="center"><%=entityZc.getSxl() == null ? 0.0 : CommonFunctions.doublecut(entityZc.getSxl()*100,2)%></td>
		<td align="center"><%=entityZc.getZcpjqx() == null ? 0.0 : entityZc.getZcpjqx()%></td>
		<td align="center"><%=entityZc.getZczyjg() == null ? 0.0 : CommonFunctions.doublecut(entityZc.getZczyjg()*100,2)%></td>
		<td align="center"><%=entityZc.getZcjlc() == null ? 0.0 : CommonFunctions.doublecut(entityZc.getZcjlc()*100,2)%></td>
		<td align="center"><%=entityZc.getZczyzc() == null ? 0.0 : FormatUtil.toMoney(entityZc.getZczyzc()/10000)%></td>
		<td align="center"><%=entityZc.getZcjsr() == null ? 0.0 : FormatUtil.toMoney(entityZc.getZcjsr()/10000)%></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		</tr>
</table>
<%} %>
</form>
</body>
</html>
