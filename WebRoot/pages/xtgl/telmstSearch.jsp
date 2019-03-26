<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="../commonJs.jsp" />
		<jsp:include page="../commonExt2.0.2.jsp" />
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
	</head>
	<body>
		<div class="cr_header">
			当前位置：系统管理->操作员管理
		</div>
		<%
			TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		%>
		<form action="" method="post">
			<table width="70%" border="0" align="center">
				<tr>
					<td>
						<table width="800" align="left" class="table">
							<tr>
								<td class="middle_header" colspan="4">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">查询</font>
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">
									操作员号：
								</td>
								<td width="30%">
									<input type="text" id="telNo" name="telNo" maxlength="6" size="12" />
								</td>
								<td width="20%" align="right">
									操作员姓名：
								</td>
								<td width="30%">
									<input type="text" id="telName" name="telName" maxlength="20" size="20">
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">
									机构名称：
								</td>
		<input name="brNo" id="brNo" type="hidden" value="<%=telMst.getBrMst().getBrNo()%>" />
		<input name="brName" id="brName" type="hidden"  value="<%=telMst.getBrMst().getBrName() + "[" + telMst.getBrMst().getBrNo() + "]"%>" />
								<td colspan="3">
									<div id='comboxWithTree1'></div>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div align="center">
										<input type="button" name="Submit1" value="查&nbsp;&nbsp;询" onclick="onclick_query()" class="button">
										&nbsp;&nbsp;
										<input type="button" name="Reset" value="重&nbsp;&nbsp;置" onclick="onclick_reset()" class="button">
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left">
						<iframe
							src="<%=request.getContextPath()%>/telmst_list.action?brNo=<%=telMst.getBrMst().getBrNo()%>"
							id="downframe" name="downframe" width="100%" height="350" frameborder="no"
							border="0" marginwidth="0" marginheight="0" scrolling="no"
							allowtransparency="yes" align="middle"></iframe>
					</td>
				</tr>
			</table>

		</form>
	</body>
	<script type="text/javascript">
	jQuery(function(){// dom元素加载完毕
		jQuery(".table tr:even").addClass("tr-bg1");
		jQuery(".table tr:odd").addClass("tr-bg2");
	});
	function onclick_query() {
		var brNo = document.getElementById("brNo").value;
		var telNo = document.getElementById("telNo").value;
		var telName = document.getElementById("telName").value;
		window.frames.downframe.location.href = '<%=request.getContextPath()%>/telmst_list.action?brNo='
				+ brNo + '&telNo=' + telNo + '&telName=' + encodeURI(telName);
	}
	function onclick_reset() {
		window.location.reload();
	}
</script>
</html>


