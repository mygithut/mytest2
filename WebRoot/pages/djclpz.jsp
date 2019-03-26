<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<html>
	<head>
		<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<div class="cr_header">
			当前位置：系统管理->定价策略配置
		</div>
		<table width="90%" border="0" align="center">
		   <tr>
		      <td>
		        <table width="900" align="left" class="table">
			<tr>
				<td class="middle_header" colspan="6">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>
				</td>
			</tr>
			<tr>
				<td width="12%" align="right">
					机构名称
				</td>
				<td width="21%">
					<select name="brNo" id="brNo">
		            </select>
				</td>
				<td width="12%" align="right">
					配置结果
				</td>
				<td width="21%">
				    <select style="width: 150" name="setResult" id="setResult">
				        <option value="">请选择</option>
				        <option value="0">未配置</option>
				        <option value="1">单资金池</option>
				        <option value="2">双资金池</option>
				        <option value="3">多资金池</option>
				        <option value="4">期限匹配</option>
            		</select>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					每页显示
					    <input type="text" name="pageSize" id="pageSize" onkeyup="value=value.replace(/[^\d]/g,'')"  value="10" size="5" />
						<input type="button" name="Submit1" class="button" onClick="doQuery()" value="查&nbsp;&nbsp;询">
				</td>
			</tr>
		</table>
		      </td>
		   </tr>
		   <tr height="330">
		      <td colspan="6" align="left">
		        <iframe src="<%=request.getContextPath()%>/DJCLPZ_list.action" id="downFrame" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" align="middle"></iframe> 
		      </td>
		   </tr>
		</table>
	</body>
	<script type="text/javascript">
	fillSelect('brNo','fillSelect_getBrNoByLvl2');
	
function doQuery() {
	   window.frames.downFrame.location.href = "<%=request.getContextPath()%>/DJCLPZ_list.action?pageSize="+document.getElementById('pageSize').value+"&brNo="+document.getElementById('brNo').value+"&setResult="+document.getElementById('setResult').value;
		 
	}
</script>
</html>