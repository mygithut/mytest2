<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@ page
	import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>业务条线盈利分析</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
String prcMode = (String)session.getAttribute("prcMode");
String tjType = (String)session.getAttribute("tjType");
String date = (String)session.getAttribute("date");
%>
			<br />
			<%if(ylfxReportList == null) { %>
			<p>
				定价策略或资金池未配置！！
			</p>
			<%}else{ %>
			定价策略：<%if(prcMode.equals("1")){out.print("单资金池");
}else if(prcMode.equals("2")){out.print("双资金池");
}else if(prcMode.equals("3")){out.print("多资金池");
}else if(prcMode.equals("4")){out.print("期限匹配");} %>&nbsp;&nbsp;&nbsp;&nbsp;统计类型：<%=tjType.equals("1")?"增量":"存量" %>&nbsp;&nbsp;&nbsp;&nbsp;单位：万元
				<table id="tableList">
					<thead>
						<tr>
							<th align="center" width="110">
								<font style="font-weight: bold">业务名称</font>
							</th>
							<th align="center" width="80">
								资产余额
							</th>
							<th align="center" width="80">
								利息收入
							</th>
							<th align="center" width="80">
								生息率(%)
							</th>
							<th align="center" width="80">
								平均期限(天)
							</th>
							<th align="center" width="95">
								资产转移价格(%)
							</th>
							<th align="center" width="90">
								资产净利差(%)
							</th>
							<th align="center" width="80">
								资产转移支出
							</th>
							<th align="center" width="80">
								资产净收入
							</th>
							<th align="center" width="110">
								<font style="font-weight: bold">业务名称</font>
							</th>
							<th align="center" width="80">
								负债余额
							</th>
							<th align="center" width="80">
								利息支出
							</th>
							<th align="center" width="80">
								付息率(%)
							</th>
							<th align="center" width="80">
								平均期限(天)
							</th>
							<th align="center" width="95">
								负债转移价格(%)
							</th>
							<th align="center" width="90">
								负债净利差(%)
							</th>
							<th align="center" width="80">
								负债转移收入
							</th>
							<th align="center" width="80">
								负债净收入
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<% //资产比负债多两行
         for (int i = 0; i < 6; i++){
        	 YlfxReport entityZc = ylfxReportList.get(i);
        	 YlfxReport entityFz = ylfxReportList.get(i+8);
     %>
							<td align="center">
								<%if(entityZc.getBusinessName().equals("贷款")) {%>
								<a href="javascript:doQuery('dk')"><font
									style="font-weight: bold">贷款</font>
								</a>
								<%}else if(entityZc.getBusinessName().equals("投资业务")) {%>
								<a href="javascript:doQuery('tzyw')"><font
									style="font-weight: bold">投资业务</font>
								</a>
								<%}else { %>
								<font style="font-weight: bold"><%=entityZc.getBusinessName()%></font>
								<%} %>
							</td>
							<td align="center"><%=entityZc.getZcye() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZcye()/10000)%></td>
							<td align="center"><%=entityZc.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getLxsr()/10000)%></td>
							<td align="center"><%=entityZc.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getSxl()*100,2)%></td>
							<td align="center"><%=entityZc.getZcpjqx() == null ? 0.00 : entityZc.getZcpjqx()%></td>
							<td align="center"><%=entityZc.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getZczyjg()*100,2)%></td>
							<td align="center"><%=entityZc.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getZcjlc()*100,2)%></td>
							<td align="center"><%=entityZc.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZczyzc()/10000)%></td>
							<td align="center"><%=entityZc.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZcjsr()/10000)%></td>
							<td align="center">
								<%if(entityFz.getBusinessName().equals("存款")) {%>
								<a href="javascript:doQuery('ck')"><font
									style="font-weight: bold">存款</font>
								</a>
								<%}else { %>
								<font style="font-weight: bold"><%=entityFz.getBusinessName()%></font>
								<%} %>
							</td>
							<td align="center"><%=entityFz.getFzye() == null ? 0.00 : FormatUtil.toMoney(entityFz.getFzye()/10000)%></td>
							<td align="center"><%=entityFz.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entityFz.getLxzc()/10000)%></td>
							<td align="center"><%=entityFz.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entityFz.getFxl()*100,2)%></td>
							<td align="center"><%=entityFz.getFzpjqx() == null ? 0.00 : entityFz.getFzpjqx()%></td>
							<td align="center"><%=entityFz.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entityFz.getFzzyjg()*100,2)%></td>
							<td align="center"><%=entityFz.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entityFz.getFzjlc()*100,2)%></td>
							<td align="center"><%=entityFz.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entityFz.getFzzysr()/10000)%></td>
							<td align="center"><%=entityFz.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entityFz.getFzjsr()/10000)%></td>
						</tr>
						<% }
     %>
						<% 
        	 YlfxReport entityZc2 = ylfxReportList.get(6);
     %>
						<td align="center">
							<font style="font-weight: bold"><%=entityZc2.getBusinessName()%></font>
						</td>
						<td align="center"><%=entityZc2.getZcye() == null ? 0.00 : FormatUtil.toMoney(entityZc2.getZcye()/10000)%></td>
						<td align="center"><%=entityZc2.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entityZc2.getLxsr()/10000)%></td>
						<td align="center"><%=entityZc2.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entityZc2.getSxl()*100,2)%></td>
						<td align="center"><%=entityZc2.getZcpjqx() == null ? 0.00 : entityZc2.getZcpjqx()%></td>
						<td align="center"><%=entityZc2.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entityZc2.getZczyjg()*100,2)%></td>
						<td align="center"><%=entityZc2.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entityZc2.getZcjlc()*100,2)%></td>
						<td align="center"><%=entityZc2.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entityZc2.getZczyzc()/10000)%></td>
						<td align="center"><%=entityZc2.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entityZc2.getZcjsr()/10000)%></td>
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
						<td align="center">
							<font style="font-weight: bold"><%=entityZc.getBusinessName()%></font>
						</td>
						<td align="center"><%=entityZc.getZcye() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZcye()/10000)%></td>
						<td align="center"><%=entityZc.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getLxsr()/10000)%></td>
						<td align="center"><%=entityZc.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getSxl()*100,2)%></td>
						<td align="center"><%=entityZc.getZcpjqx() == null ? 0.00 : entityZc.getZcpjqx()%></td>
						<td align="center"><%=entityZc.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getZczyjg()*100,2)%></td>
						<td align="center"><%=entityZc.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getZcjlc()*100,2)%></td>
						<td align="center"><%=entityZc.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZczyzc()/10000)%></td>
						<td align="center"><%=entityZc.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZcjsr()/10000)%></td>
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
					</tbody>
				</table>
			<%} %>
			<div align="center">
				<input type="button" name="Submit1"
					style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					onClick="doExport()" value="导&nbsp;&nbsp;出">
			</div>
		</form>
	</body>

	<script type="text/javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: '业务条线盈利分析报表'});
});
j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doQuery(prdtType){
	var brNo = window.parent.document.getElementById("brNo").value;
	var manageLvl = window.parent.document.getElementById("manageLvl").value;
	var date = <%=date%>;
	var tjType = <%=tjType%>;
	var assessScope = window.parent.document.getElementById("assessScope").value;
	window.location.href ='<%=request.getContextPath()%>/REPORT_cpylfxReport.action?brNo='+brNo+'&manageLvl='+manageLvl+'&tjType='+tjType+'&date='+date+'&assessScope='+assessScope+'&prdtType='+prdtType;
	parent.parent.parent.parent.openNewDiv();
}
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/report_ywtxylfx_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
