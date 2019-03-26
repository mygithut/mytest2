<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpQxppResult,com.dhcc.ftp.util.*"
	errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="commonJs.jsp" />
		<title>单资金池-历史价格查看</title>
	</head>
	<%
		PageUtil resultPriceUtil = (PageUtil) request
				.getAttribute("resultPriceUtil");
		List list = resultPriceUtil.getList();
	%>
	<body>
		<form name="form1" method="post">

			<table id="tableList">
				<thead>
					<tr>
						<th width="250">
							机构
						</th>
						<th width="100">
							币种
						</th>
						<th width="100">
							定价方法
						</th>
						<th width="100">
							定价结果(%)
						</th>
						<th width="100">
							定价日期
						</th>
						<th width="100">
							操作
						</th>
					</tr>
				</thead>
				<%
					for (int i = 0; i < list.size(); i++) {
						Object[] o = (Object[]) list.get(i);
				%>
				<tbody>
					<tr>
						<td align="center"><%=CastUtil.trimNull(o[0])%></td>
						<td align="center"><%=CastUtil.trimNull(o[1])%></td>
						<td align="center"><%=CastUtil.trimNull(o[2])%></td>
						<td align="center"><%=CommonFunctions.doublecut(Double.valueOf(o[4].toString()) * 100, 3)%></td>
						<td align="center"><%=CastUtil.trimNull(o[5])%></td>
						<td align="center">
						<a href="javascript:showHistoryTransPrice('<%=o[6]%>','<%=o[5]%>','<%=o[2]%>','<%=o[3]%>')" >
							历史转移价格
						</td>
					</tr>
				</tbody>
				<%
					}
				%>
			</table>
			<table border="0" width="80%" class="tb1"
				style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
				align="center">
				<tr>
					<td align="right"><%=resultPriceUtil.getPageLine()%></td>
				</tr>
				</td>
				</tr>
			</table>
		</form>

	</body>

	<script type="text/javascript">
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 240,widht:900,
	    		title: '查询列表'});
	});
	function showHistoryTransPrice(brNo, date, methodName, method) {
		var w = 800; //弹出窗口的宽度;
		var h = 500; //弹出窗口的高度;
		sw = Math.floor((window.screen.width / 2 - w / 2));
		sh = Math.floor((window.screen.height / 2 - h / 2));
		pra = "height="
				+ h
				+ ", width="
				+ w
				+ ", top="
				+ sh
				+ ", left="
				+ sw
				+ "menubar=no, scrollbars=yes, resizable=no,location=no, status=no";
		window.open('<%=request.getContextPath()%>/UL01_history.action?prcMode=1&currencySelectId=01&brNo='
				+ brNo + '&date=' + date + '&methodName=' + methodName
				+ '&method=' + method, 'newwindow', pra);
	}
</script>
</html>
