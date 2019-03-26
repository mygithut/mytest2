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
System.out.println("###### 开始加载机构总盈利分析列表页面...");
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
//Integer isFirst = (Integer)request.getAttribute("isFirst");//是否显示“返回”按钮
Integer isFirst = Integer.valueOf(request.getParameter("isFirst"));//是否显示“返回”按钮
String prcMode = (String)session.getAttribute("prcMode");
%>
<br/>
<%if(ylfxReportList == null) { %>
   <p>定价策略或资金池未配置！！</p>
<%}else{ %>
定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
<table id="tableList">
					<thead>
     <tr >
      <th align="center" width="270" >机构</th>
      <th align="center" width="80" ><font style="font-weight:bold">资产余额</font></th>
      <th align="center" width="80" >利息收入</th>
      <th align="center" width="80" >生息率(%)</th>
      <th align="center" width="80" >平均期限(天)</th>
      <th align="center" width="100" >资产转移价格(%)</th>
      <th align="center" width="90" >资产净利差(%)</th>
      <th align="center" width="80" >资产转移支出</th>
      <th align="center" width="80" >资产净收入</th>
      <th align="center" width="80" ><font style="font-weight:bold">负债余额</font></th>
      <th align="center" width="80" >利息支出</th>
      <th align="center" width="80" >付息率(%)</th>
      <th align="center" width="80" >平均期限(天)</th>
      <th align="center" width="100" >负债转移价格(%)</th>
      <th align="center" width="90" >负债净利差(%)</th>
      <th align="center" width="80" >负债转移收入</th>
      <th align="center" width="80" >负债净收入</th>
      <th align="center" width="80" ><font style="font-weight:bold">净收入</font></th>
      <th align="center" width="80" >明细</th>
       </tr>
       </thead><tbody>
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
		<td align="center">
		<%if(!entity.getManageLvl().equals("0")) { %>
		<a href="javascript:doQuery('<%=entity.getBrNo() %>','<%=entity.getManageLvl() %>')">明细</a>
		<%}else{ %>
		无
		<%} %>
		</td>
		</tr>
     <% }
     %></tbody>
</table>
<%} %>

<div align="center" width="1770">
<input type="button" name="Submit1" style="color: #fff; background-image: url(../themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="导&nbsp;&nbsp;出">
<%if(isFirst == 0){ %>
<input type="button" name="Submit2" style="color: #fff; background-image: url(../themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					 onclick="javascript: history.back();" value="返&nbsp;&nbsp;回">	
<%} %>
</div>
</form>
</body>

<script type="text/javascript">
parent.parent.parent.parent.cancel();

j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: '机构总盈利分析报表_试发布'});
});

function doQuery(brNo, manageLvl){
	   var date = window.parent.document.getElementById("date").value;
	   var assessScope = window.parent.document.getElementById("assessScope").value;
	   //window.location.href ='/ftp/REPORTSFB_jgzylfxReport.action?isMx=1&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope+'&isFirst=0';
	   parent.parent.parent.parent.openNewDiv();
	   var url = "<%=request.getContextPath()%>/REPORTSFB_jgzylfxReport.action";
	    new Ajax.Request(url, {
			method : 'post',
			parameters : {
			 	brNo:brNo,manageLvl:manageLvl,date:date,assessScope:assessScope,isMx:1,t:new Date().getTime()
			},
			onSuccess : function() {
<%--				alert('后台计算成功');--%>
				window.location.href="report_jgzylfx_sfb_list.jsp?isFirst=0"; 
		    }
	    });
}
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/ssbb/report_jgzylfx_sfb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
