<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户经理分产品FTP利润表</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("khjlfcplrbReportList");
String minDate = (String)session.getAttribute("khjlfcplrbMinDate");
String maxDate = (String)session.getAttribute("khjlfcplrbMaxDate");
String empName = (String)session.getAttribute("khjlfcplrbEmpName");
String empNo = (String)session.getAttribute("khjlfcplrbEmpNo");

int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>数据统计错误，请联系管理员！！</p>
<%}else{ 
	double zftplr = 0;//总的ftp利润
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplr();
	}
%>
<div align="center">客户经理：<%= empName%>[<%= empNo%>]
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;单位：元，%(年利率)</div>
<table id="tableList">
					<thead>
     <tr >
      <th align="center" width="300" >产品名称</th>
		 <th align="center" width="100" >余额</th>
      <th align="center" width="100" >日均余额</th>
      <th align="center" width="100" >利差(%)</th>
      <th align="center" width="100" >FTP利润</th>
      <th align="center" width="100" >FTP利润占比(%)</th>
      <th align="center" width="100" >排名</th>
       </tr>
					</thead>
					<tbody>
     <%  
         double rjye=0, ftplr=0, lc=0,bal=0;
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
        	 rjye += entity.getRjye();
        	 ftplr += entity.getFtplr();
			 bal += entity.getBal();
     %>
        <tr>
     	<td align="center"><%=entity.getPrdtName()%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getBal())%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getRjye())%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getLc()*100,6)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFtplr())%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFtplr()/zftplr*100,2)%></td>
		<td align="center"><%=i+1%></td>
		</tr>
		<%} %>
     <tr>
     	<td align="center"><font style="font-weight: bold">合计</font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(bal)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=CommonFunctions.doublecut(rjye==0?0.00:ftplr/rjye*360/days*100,6)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		<td align="center"><font style="font-weight: bold">-</font></td>
		</tr>
					</tbody>
				</table>
<div align="center" width="1770">
<input type="button" name="Submit1" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="导&nbsp;&nbsp;出">
</div>
<%} %>
</form>
</body>

<script type="text/javascript">
window.onload=function(){
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 230,width:900,
	    		title: '客户经理分产品FTP利润表'});
	});
}

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_khjlfcplrb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
