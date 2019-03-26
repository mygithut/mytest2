<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%
TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); 
%>
<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'>
		</script>
		<title>员工信息维护</title>
	</head>
	<body>
		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
			     <table width="750"  class="table"  align="left">
					<tr>
						<td class="middle_header" colspan="4"><font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>	</td>
					</tr>
					<tr>
					<td  align="right">员工编号： </td>
					<td>
						<input type="text" id="empNo" name="empNo" 	 />
					</td>
					<td  align="right">员工姓名： </td>
					<td>
						<input type="text" id="empName" name="empName" 	/>
					</td>
					</tr>
					<tr>
					<td  align="right">机构名称：</td>
					<td colspan="1">
					<div id='comboxWithTree1'></div>
					<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
					<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
				</td>
					<td  align="right">员工状态：</td>
					<td colspan="1">
						<select name="empStatus" id="empStatus">
							<option value="">请选择</option>
							<option value="1"  >正常</option>
							<option value="2"  >离职</option>
						</select>
				</td>
			</tr>
			<tr>
		        <td align="center" colspan="4">
		        <input name="query" class="button" type="button" id="query" height="20" onClick="doQuery()" value="查&nbsp;&nbsp;询" /> 
		        <input name="back" class="button" type="button" id="back" height="20" onClick="doClear()" value="重&nbsp;&nbsp;置" />
		          </td>
		      </tr>
			</table>
			</td>
		</tr>
			<tr>
				<td>
					<iframe src="<%=request.getContextPath()%>/YGXXWH_List.action?brNo=<%=telmst.getBrMst().getBrNo()%>&ve=1&empStatus=1" id="iframe" name="iframe" width="100%"	height="295" frameborder="no" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" align="middle"></iframe>
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
	function doQuery() {
		var brNo=document.getElementById('brNo').value;
		var empName=document.getElementById('empName').value;
		var empNo=document.getElementById('empNo').value;
		var empStatus=document.getElementById('empStatus').value;
		//alert("FtpEmpInfo_List.action?brNo="+brNo+"&empName="+empName+"&empNo"+empNo)
		if (brNo == '') {
			alert("请选择要查询的机构!!"); 
			return;
		}
		
		window.frames.iframe.location.href = "<%=request.getContextPath()%>/YGXXWH_List.action?ve=1&brNo="+brNo+"&empNo="+empNo+"&empStatus="+empStatus+"&empName="+encodeURI(empName);
			}
	function doClear(){
		//document.getElementById('brNo').value="";
		document.getElementById('empName').value="";
		document.getElementById('empNo').value="";
	}
	
	
	
	</script>
</html>

