<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="java.text.*,java.util.Date,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
</head>
<body>
<div class="cr_header">
			��ǰλ�ã�˫�ʽ��-->˫�ʽ������
</div>
<form action="" method="get">
<table width="90%" border="0" align="center">
    <tr>
		<td>
		    <table width="950" align="left" class="table">
			   <tr>
				  <td class="middle_header" colspan="6">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��ѯ</font>
				  </td>
			   </tr>
			   <tr>
		          <td width="36%" align="right">�������ƣ�</td>
		          <td width="64%">
		            <select name="brNo" id="brNo">
		            </select>
		          </td>
	           </tr>
	           <tr align="center">
	              <td colspan="2">
	                 <input type="button" name="Submit1" class="button" onClick="doQuery()" value="��&nbsp;&nbsp;ѯ">
	              </td>
	           </tr>
		    </table>
		      </td>
		   </tr>
		   <tr height="400">
		      <td >
		        <iframe src="" id="downFrame" width="967" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" align="middle"></iframe> 
		      </td>
		   </td>
		</table>
</form>
</body>
</html>
<script type="text/javascript" language="javascript">
fillSelect('brNo','fillSelect_getBrNoByLvl2');

function doQuery() {
	if ($("brNo").value == '') {
		alert("��ѡ��Ҫ��ѯ�Ļ���!!"); 
		return;
	}
	window.frames.downFrame.location.href = "<%=request.getContextPath()%>/SZJCPZ_list.action?brNo="+document.getElementById('brNo').value;
}
</script>
