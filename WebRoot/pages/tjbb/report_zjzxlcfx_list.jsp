<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资金中心利差分析</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
YlfxReport ylfxReport = (YlfxReport)session.getAttribute("ylfxReport");
String prcMode = (String)session.getAttribute("prcMode");
String tjType = (String)session.getAttribute("tjType");
%>
<br/>
<%if(ylfxReport == null) { %>
   <p>定价策略或资金池未配置！！</p>
<%}else{ %>
定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>&nbsp;&nbsp;&nbsp;&nbsp;统计类型：<%=tjType.equals("1")?"增量":"存量" %>&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
				<table id="tableList">
					<thead>
						<tr>
							<th width="250">
								数据项
							</th>
							<th width="250">
								金额
							</th>
						</tr>
					</thead>
					<tbody>
     <tr >
      <td align="center" width="250" ><font style="font-weight:bold">资产交易额</font></td>
      <td align="center" width="250"><font style="font-weight:bold"><%=FormatUtil.toMoney(ylfxReport.getZcye()/10000)%></font></td>
     </tr>
     <tr >
      <td align="center" width="250" >资产转移价格(%)</td>
      <td align="center" width="250"><%=CommonFunctions.doublecut(ylfxReport.getZczyjg()*100,3)%></td>
     </tr>
     <tr >
      <td align="center" width="250" >资产转移收入</td>
      <td align="center" width="250"><%=FormatUtil.toMoney(ylfxReport.getZczyzc()/10000)%></td>
     </tr>
     <tr >
      <td align="center" width="250" ><font style="font-weight:bold">负债交易额</font></td>
      <td align="center" width="250"><font style="font-weight:bold"><%=FormatUtil.toMoney(ylfxReport.getFzye()/10000)%></font></td>
     </tr>
     <tr >
      <td align="center" width="250" >负债转移价格(%)</td>
      <td align="center" width="250"><%=CommonFunctions.doublecut(ylfxReport.getFzzyjg()*100,3)%></td>
     </tr>
     <tr >
      <td align="center" width="250" >负债转移支出</td>
      <td align="center" width="250"><%=FormatUtil.toMoney(ylfxReport.getFzzysr()/10000)%></td>
     </tr>
     <tr >
      <td align="center" width="250" ><font style="font-weight:bold">净收入</td>
      <td align="center" width="250"><font style="font-weight:bold"><%=FormatUtil.toMoney((ylfxReport.getZczyzc()-ylfxReport.getFzzysr())/10000)%></font></td>
     </tr>
					</tbody>
				</table>
<%} %>
</form>
<div align="center" >
<input type="button" name="Submit1"
				style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
				 onClick="doExport()" value="导&nbsp;&nbsp;出"></div>
</body>
<script type="text/javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:700,
    		title: '资金中心利差分析'});
});
j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/report_zjzxlcfx_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
