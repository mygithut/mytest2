<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<%@page import="com.dhcc.ftp.entity.FtpEmpInfo"%>

	<head>
		<title>员工数据维护</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
				PageUtil frpEmpInfoUtil = (PageUtil) request.getAttribute("frpEmpInfoUtil");
				List<FtpEmpInfo> frpEmpInfoList = frpEmpInfoUtil.getList();
				
				if (frpEmpInfoList == null) {
					out.print("无该类型对应的数据！");
				} else {
			%>
				<table width="750" border="0" cellspacing="0" cellpadding="0" align="center" class="table">
						<tr>
							<th width="80">
							    选定
							</th>
							<th width="80">
								员工编号
							</th>
							<th width="80">
								员工姓名
							</td>
							<th width="200">
								所属机构
							</th>
						    <th width="80">
								员工状态
						    </th>
						</tr>
					<%
					for (FtpEmpInfo po : frpEmpInfoList) {
				%>
						<tr>
							<td align="center">
								<input type="radio" name="checkbox1" onclick="doChoose(this.value)" id="checkbox1" value="<%=CastUtil.trimNull(po.getEmpNo()+"@@"+po.getEmpName())%>" />
							</td>
							<td align="center">
								<%=CastUtil.trimNull(po.getEmpNo())%>
							</td>
							<td align="center">
								<%=CastUtil.trimNull(po.getEmpName())%>
							</td>
							<td align="center">
								<%=po.getBrMst()==null?"":CastUtil.trimNull(po.getBrMst().getBrName())%>
							</td>
							<td align="center">
								<%if(!CastUtil.trimNull(po.getEmpStatus()).equals("")){
									if(po.getEmpStatus().equals("1")){
										out.write("正常");
									}else if(po.getEmpStatus().equals("2")){
										out.write("离职");
									}
								}%>
							</td>
						</tr>
					<%
					}
					}
				%>
				</table>
			<table border="0" width="750" class="tb1" align="center">
				<tr>
					<td align="right"><%=frpEmpInfoUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
		function doChoose(bValue){
			parent.art.dialog.data('bValue', bValue);// 存储数据
			parent.art.dialog.close();
		}

<%--		function doImport() {--%>
<%--			window.location.href = "<%=request.getContextPath()%>/pages/shiborwhImport.jsp";--%>
<%--		}--%>

		function close(){
			window.location.reload();
		}

</script>
	</body>
</html>
