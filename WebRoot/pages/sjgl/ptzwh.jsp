<%@ page contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>普通债 (AA-)收益率维护</title>
	</head>
	<body>
		<div class="cr_header">
			当前位置：数据管理->普通债(AA-)收益率维护
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td>
					<table width="1030">
						<tr>
							<td>
								<iframe src="<%=request.getContextPath()%>/PTZWH_List.action" id="iframe" width="100%"
									height="450" frameborder="no" marginwidth="0" marginheight="0"
									scrolling="no" allowtransparency="yes" align="middle"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

