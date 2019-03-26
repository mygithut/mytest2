<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%
TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); 
String relatedEmpNos = request.getParameter("relatedEmpNos");
%>
<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="../commonJs.jsp" />
		<jsp:include page="../commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'>
		</script>
		<title>关联操作-员工信息列表</title>
	</head>
	<body>
		<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
			     <table width="610"  class="table" align="center">
					<tr>
						<td class="middle_header" colspan="4"><font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>	</td>
					</tr>
					<tr>
					<td width="15%" align="right">员工编号： </td>
					<td>
						<input type="text" id="empNo" name="empNo" 	 />
					</td>
					<td width="15%" align="right">员工姓名： </td>
					<td>
						<input type="text" id="empName" name="empName" 	/>
					</td>
					</tr>
					<tr>
					<td width="15%" align="right">机构名称：</td>
					<td colspan="3">
					<div id='comboxWithTree1'></div>
					<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
					<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
				</td>
			</tr>
			<tr>
		        <td align="center" colspan="4">
		        <input name="query" class="button" type="button" id="query" height="20" onClick="doQuery()" value="查&nbsp;&nbsp;询" /> 
		        <input name="back" class="button" type="button" id="back" height="20" onClick="javaScript:window.location.reload()" value="重&nbsp;&nbsp;置" />
		        </td>
		      </tr>
			</table>
			</td>
		</tr>
		</table>
		<table align="center" width="610">
			<tr>
				<td align="center">
					<iframe src='<%=request.getContextPath()%>/YGZHGL_getEmps.action?brNo=<%=telmst.getBrMst().getBrNo()%>&relatedEmpNos=<%=relatedEmpNos %>' id="iframe" name="iframe" width="610"	height="300" frameborder="no" marginwidth="0" marginheight="0"		scrolling="no" allowtransparency="yes" align="center"></iframe>
				</td>
			</tr>
		</table>
		
	</body>
	<script type="text/javascript">
	function doQuery() {
		var brNo=document.getElementById('brNo').value;
		var empName=document.getElementById('empName').value;
		var empNo=document.getElementById('empNo').value;
		window.frames.iframe.location.href ='<%=request.getContextPath()%>/YGZHGL_getEmps.action?relatedEmpNos=<%=relatedEmpNos%>&brNo='+brNo+'&empName='+encodeURI(empName)+'&empNo='+empNo;
	}
	</script>
</html>

