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
List<YlfxReport> cpylfxReportList = (List<YlfxReport>)session.getAttribute("cpylfxReportList");
String prdtType = (String)session.getAttribute("prdtType");
String prcMode = (String)session.getAttribute("prcMode");
String tjType = (String)session.getAttribute("tjType");
%>
<br/>
<%if(cpylfxReportList == null) { %>
   <p>定价策略或资金池未配置！！</p>
<%}else{ %>
定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>&nbsp;&nbsp;&nbsp;&nbsp;统计类型：<%=tjType.equals("1")?"增量":"存量" %>&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
<table id="tableList">
					
     <%if(prdtType.equals("ck")) { %>
     <thead>
     <tr >
      <th align="center" width="220" >产品大类名称</th>
      <th align="center" width="75" >余额</th>
      <th align="center" width="75" >利息支出</th>
      <th align="center" width="65" >付息率(%)</th>
      <th align="center" width="80" >平均期限(天)</th>
      <th align="center" width="100" >负债转移价格(%)</th>
      <th align="center" width="90" >负债净利差(%)</th>
      <th align="center" width="80" >负债转移收入</th>
      <th align="center" width="80" >负债净收入</th>
       </tr>
       </thead>
       <tbody>
 <%for(int i = 0; i < cpylfxReportList.size(); i++) {  %>
       <tr>
       <%YlfxReport entity = cpylfxReportList.get(i); %>
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
       </tbody>
       <%}else if(prdtType.equals("dk") || prdtType.equals("tzyw")){ %>
       <thead>
       <tr >
      <th align="center" width="220" >产品大类名称</th>
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
<%for(int i = 0; i < cpylfxReportList.size(); i++) {%>
       <tr>
       <%YlfxReport entity = cpylfxReportList.get(i); %>
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
       </tbody>
       
       <%} %>
</table>
<%} %>
<div align="center">
<input type="button" name="Submit1" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="导&nbsp;&nbsp;出">
<input type="button" name="Submit2" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onclick="javascript: history.back();" value="返&nbsp;&nbsp;回">	</div>
</form>
</body>
<script type="text/javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: '<%if(prdtType.equals("ck")) {out.print("存款");}else if(prdtType.equals("dk")) {out.print("贷款");}else if(prdtType.equals("tzyw")) {out.print("投资");}
    			 %>业务盈利分析报表'});
});
j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/report_cpylfx_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
