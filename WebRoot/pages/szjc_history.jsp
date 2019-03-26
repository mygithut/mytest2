<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>双资金池-历史价格查看</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
</head>
<body>
<div class="cr_header">
			当前位置：双资金池->双资金池定价结果查看
		</div>
<form name="thisform" action="" method="post">
<table width="80%" border="0" align="center">
		   <tr>
		      <td>
  <table width="900" border="0" align="left" class="table">
    <tr>
				<td class="middle_header" colspan="4">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>
				</td>
			</tr>
	<tr>
		<td width="30%" align="right">机构名称：</td>
		<td colspan="3">
		<select name="brNo" id="brNo">
		            </select>
		</td>
	</tr>
	<tr>
				<td colspan="6" align="center">
					每页显示
					    <input type="text" name="pageSize" id="pageSize" value="10" size="5" />
						<input type="button" name="Submit1" class="button" onClick="doQuery()" value="查&nbsp;&nbsp;询">
				</td>
			</tr>
		</table>
		      </td>
		   </tr>
		   <tr height="350">
		      <td align="left">
		        <iframe src="" id="downFrame" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" align="middle"></iframe> 
		      </td>
		   </td>
		</table>
 </form>
</body>
<script type="text/javascript">	
fillSelect('brNo','fillSelect_getBrNoByLvl2');
function doQuery(){
	if(!(isNull(document.getElementById("brNo"),"机构"))) {
		return  ;			
	}
	   var brNo =document.getElementById("brNo").value;
	   window.frames.downFrame.location.href ='<%=request.getContextPath()%>/UL02_getResultPrice.action?brNo='+brNo+'&pageSize='+document.getElementById('pageSize').value;
	}
</script>
</html>
