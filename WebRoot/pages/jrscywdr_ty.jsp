<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>

<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css"	type="text/css">
        <jsp:include page="commonJs.jsp" />
        <jsp:include page="commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->
        <script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
        <script type="text/javascript" language="text/javascript" src="<%=request.getContextPath()%>/pages/js/validate.js"></script>
		<title>�����г�ҵ����</title>
	</head>
	<body>
		<div class="cr_header">
			��ǰλ�ã�̨�˲�¼->�����г�ҵ��¼->ͬҵ̨��ά��
		</div>
		  <table width="950" border="0" align="center" >
		   <tr>
		      <td>
		        <table width="950" align="left" class="table" >
			<tr>
				<td class="middle_header" colspan="4">
					<font style="padding-left:10px; color:#333; font-size:14px;font-weight:bold">��ѯ</font>
				</td>
			</tr>
			<tr>
    <tr>

				<td width="15%" align="right">�������ƣ�</td>
				<td >
				<%TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); %>
					<div id='comboxWithTree1' style="width: 300px;"></div>
					<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
					<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
				</td>
				<td width="15%" align="right">
					���׶������ƣ�
				</td>
				<td width="35%">
					<input type="text" name="cusName" id="cusName" value="" size="20" />
				</td>
	</tr>

	<tr>
				<td colspan="4" align="center">
					ÿҳ��ʾ
					    <input type="text" name="pageSize" onblur="isInt(this,'ҳ��:')" id="pageSize" value="10" size="5" />
						<input name="query" class="button" type="button" id="query" height="20" onClick="javascript:doQuery()" value="��&nbsp;&nbsp;ѯ" /> 
				</td>
	</tr>
</table>
</td>
</tr>
<tr height="350">
 <td>
  <iframe src="<%=request.getContextPath()%>/JRSCYWDRTY_List.action" id="downFrame" name="downFrame" width="100%" height="350px" frameborder="0"  marginwidth="0" marginheight="0" scrolling="no"  align="left" style="overflow-x: yes"></iframe>
  </td>
</tr>
</table>
</body>
<script type="text/javascript">
   function doQuery(){
      var para ="<%=request.getContextPath()%>/JRSCYWDRTY_List.action?pageSize="+j('#pageSize').val()+"&brNo="+encodeURI(j('#brNo').val())+"&cusName="+encodeURI(j('#cusName').val());
      window.frames.downFrame.location.href = para;
   }
</script>
</html>

