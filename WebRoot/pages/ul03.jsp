<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="java.text.*,java.util.List,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />

</head>
<body>
<div class="cr_header">
			��ǰλ�ã����ʽ��-->���ʽ�ض��ۼ���
</div>

<form action="<%=request.getContextPath()%>/UL03_report.action" method="get">
<table width="80%" border="0" align="center">
		   <tr>
		      <td align="left">
  <table width="900" border="0" align="left" class="table">
    <tr>
				<td class="middle_header" colspan="6">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��ѯ</font>
				</td>
			</tr>
	<tr>
		<td width="15%" align="right">
		���ڣ�
		</td>
		<td width="20%">
		<input disabled="disabled" type="text" name="date" maxlength="10" value="<%=CommonFunctions.GetDBSysDate() %>" size="10" /> 
<%--		<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('date')">--%>
		</td>
		<td width="15%" align="right">�������ƣ�</td>
		<td>
            <select name="brNo" id="brNo"></select>
		</td>
	   <td width="15%" align="right">ѡ����֣�</td>
			<td>
				<select style="width: 100" name="currencySelectId" id="currencySelectId">
				</select>
			</td>
		</tr>
	<tr align="center">
	   <td colspan="6">
	       <input type="button" name="Submit1" class="button" onClick="doQuery()" value="��&nbsp;&nbsp;ѯ">
	   </td>
	</tr>
	</table>
		      </td>
		   </tr>
		   <tr height="380">
		      <td align="left">
		        <iframe src="" id="downFrame" width="967" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" align="middle"></iframe> 
		      </td>
		   </td>
		</table>
<script type="text/javascript" language="javascript">
fillSelectContent('currencySelectId');
fillSelect('brNo','fillSelect_getBrNoByLvl2');
function doQuery(){//select��value����poolNo+poolType
	if ($("brNo").value == '') {
		alert("��ѡ��Ҫ��ѯ�Ļ���!!"); 
		return;
	}
	window.frames.downFrame.location.href = "<%=request.getContextPath()%>/UL03_getPoolList.action?brNo="+document.getElementById('brNo').value;
}

</script>
