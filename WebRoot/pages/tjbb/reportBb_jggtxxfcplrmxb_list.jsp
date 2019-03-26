<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>机构各条线下分产品FTP利润明细表</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("jggtxxfcplrmxbReportList");
String minDate = (String)session.getAttribute("jggtxxfcplrmxbMinDate");
String maxDate = (String)session.getAttribute("jggtxxfcplrmxbMaxDate");
String brName = (String)session.getAttribute("jgywtxlrhzbBrName");
String businessName = (String)session.getAttribute("jggtxxfcplrmxbBusinessName");

int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>数据统计错误，请联系管理员！！</p>
<%}else{ %>
<div align="center">机构：<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;单位：万元，%(年利率)</div>
<table id="tableList">
					<thead>
     <tr >
      <th align="center" width="300" >产品名称</th>
		 <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">余额</font></th>
      <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">日均余额</font></th>
      <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">利差(%)</font></th>
      <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">FTP利润</font></th>
       </tr>
					</thead>
					<tbody>
     <%  
         double rjye=0, ftplr=0,bal=0;
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
        	 rjye += entity.getRjye();
        	 ftplr += entity.getFtplr();
        	 bal+=entity.getBal();
     %>
        <tr>
     	<td align="center"><%=entity.getPrdtName()%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getBal()/10000)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getRjye()/10000)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getLc()*100,6)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFtplr()/10000)%></td>
		
		</tr>
		<%} %>
     <tr>
     	<td align="center"><font style="font-weight: bold">合计</font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(bal/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(rjye==0?0.00:ftplr/rjye*360/days*100,6)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr/10000)%></font></td>
		</tr>
					</tbody>
				</table>
<div align="center" width="1770">
<input type="button" name="Submit1" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="导&nbsp;&nbsp;出">
<input type="button" name="Submit2" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onclick="javascript: history.back();" value="返&nbsp;&nbsp;回">
</div>
<%} %>
</form>
</body>

<script type="text/javascript">
window.onload=function(){
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 230,width:900,
	    		title: '机构各条线下<%=businessName%>业务分产品FTP利润明细表'});
	});
}

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_jggtxxfcplrmxb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
