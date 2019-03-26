<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
String prcMode = (String)session.getAttribute("prcMode");
String tjType = (String)session.getAttribute("tjType");
%>
<br/>
<%if(ylfxReportList == null) { %>
   <p>定价策略或资金池未配置！！</p>
<%}else{ %>
定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>&nbsp;&nbsp;&nbsp;&nbsp;统计类型：<%=tjType.equals("1")?"增量":"存量" %>&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
<div align="center">
				<table id="tableList">
					<thead>
     <tr >
      <th align="center" width="300" >机构名称</th>
      <th align="center" width="100" >机构编号</th>
      <th align="center" width="100" >净收入</th>
      <th align="center" width="100" >排名</th>
       </tr>
					</thead>
					<tbody>
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
					</tbody>
				</table>
			</div>
<%} %>
</form>
<div align="center">
<input type="button" name="Submit1"
				style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
				 onClick="doExport()" value="导&nbsp;&nbsp;出"></div>
</body>

<script type="text/javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: '机构盈利排名报表'});
});
j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/report_jgylpm_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
