<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>政策性金融债收益率维护</title>
	</head>
	<body>
		<div class="cr_header">
			当前位置：数据管理->政策性金融债收益率维护
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td>
					<table width="1030">
						<tr>
							<td>
								<iframe src="<%=request.getContextPath()%>/ZCXJRZWH_List.action" id="iframe" width="100%"
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

