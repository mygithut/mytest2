<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonDatePicker.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->

		<title>提前还款/支取调整</title>
	</head>
	<body>
		<div class="cr_header">
			当前位置：数据管理->提前还款/支取调整
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
				<td>
					<table width="1000" align="center">
						<tr>
							<td>
								<iframe src="<%=basePath%>/TQHKZQTZ_List.action" id="iframe" width="100%"	height="450" frameborder="no" marginwidth="0" marginheight="0"		scrolling="no" allowtransparency="yes" align="middle"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

