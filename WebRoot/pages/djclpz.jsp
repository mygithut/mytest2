<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<html>
	<head>
		<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<div class="cr_header">
			��ǰλ�ã�ϵͳ����->���۲�������
		</div>
		<table width="90%" border="0" align="center">
		   <tr>
		      <td>
		        <table width="900" align="left" class="table">
			<tr>
				<td class="middle_header" colspan="6">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��ѯ</font>
				</td>
			</tr>
			<tr>
				<td width="12%" align="right">
					��������
				</td>
				<td width="21%">
					<select name="brNo" id="brNo">
		            </select>
				</td>
				<td width="12%" align="right">
					���ý��
				</td>
				<td width="21%">
				    <select style="width: 150" name="setResult" id="setResult">
				        <option value="">��ѡ��</option>
				        <option value="0">δ����</option>
				        <option value="1">���ʽ��</option>
				        <option value="2">˫�ʽ��</option>
				        <option value="3">���ʽ��</option>
				        <option value="4">����ƥ��</option>
            		</select>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					ÿҳ��ʾ
					    <input type="text" name="pageSize" id="pageSize" onkeyup="value=value.replace(/[^\d]/g,'')"  value="10" size="5" />
						<input type="button" name="Submit1" class="button" onClick="doQuery()" value="��&nbsp;&nbsp;ѯ">
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