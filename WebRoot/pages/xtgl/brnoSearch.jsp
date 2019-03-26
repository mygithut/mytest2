<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.dhcc.ftp.entity.TelMst"%>
<%
	TelMst telmst = (TelMst) request.getSession().getAttribute(
			"userBean");
	String brno = telmst.getBrMst().getBrNo();
	String managelvl = telmst.getBrMst().getManageLvl();
%>
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
			当前位置：系统管理->机构管理
		</div>
		<form action="" method="post">
			<table width="70%" align="center" border="0">
				<tr>
					<td>
						<table width="800" align="left" class="table">
				<tr>
					<td class="middle_header" colspan="2"
						style="padding-top: 5px; padding-left: 10px; font-size: 12px; color: #333; font-weight: bold">
						查询
					</td>
				</tr>

				<tr>
					<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
					<input name="brName" id="brName" type="hidden" value="<%=telmst.getBrMst().getBrName() + "["+ telmst.getBrMst().getBrNo() + "]"%>" />
					<td width="36%" align="right">
						机构名称：
					</td>
					<td width="64%">
						<div id='comboxWithTree1'></div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
							<input type="button" name="Submit1" class="button" onclick="onclick_query()"
								value="查&nbsp;&nbsp;询">
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
							src="<%=request.getContextPath()%>/brmst_list.action?brNo=<%=telmst.getBrMst().getBrNo()%>"
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
		window.frames.downframe.location.href = 'brmst_list?brNo='+brNo;
	}
	function onclick_reset() {
		window.location.reload();
	}
</script>
</html>