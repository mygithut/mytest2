<%@ page contentType="text/html;charset=GBK"%>
<%@ page language="java" import="java.util.*" session="true"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>

<%
	TelMst telMst = (TelMst) request.getAttribute("telMst");
%>
<html>
<head>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" />
<title>�޸�����</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="../commonJs.jsp" />
<body>
<input type="hidden" id="telno" value="<%=telMst.getTelNo() %>"/>
<table width="90%" align="center" class="table">
	<tr height="26">
	   <td class="middle_header" colspan="2">
	     <font style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">�޸�����</font>
	   </td>
	</tr>
	<tr>
						<td width="36%" align="right">����Ա���룺
						</td>
						<td width="64%"><input type="text" class="input1" id="tlrno" name="tlrno" value="<%=telMst.getTelNo()%>" maxlength="30" size="18" readonly /></td>
					</tr>
					<tr>
						<td width="36%" align="right">����Ա������
						</td>
						<td width="64%"><input type="text" class="input1" value="<%=telMst.getTelName()%>" maxlength="30" size="18" readonly /></td>
					</tr>
					<tr>
						<td width="36%" align="right">��������룺
						</td>
						<td width="64%"><input type="password" name="passwd" id="passwd" class="input1" maxlength="6" size="12" /> <font color="red">*</font>��ʼ����Ϊ&quot;000000&quot;</td>
					</tr>
					<tr>
						<td width="36%" align="right">���������룺
						</td>
						<td width="64%"><input type="password" name="passwd1" id="passwd1" class="input1" maxlength="6" size="12" /></td>
					</tr>
					<tr>
						<td width="36%" align="right">�ٴ����������룺
						</td>
						<td width="64%"><input type="password" name="passwd2" id="passwd2" class="input1" maxlength="6" size="12" /></td>
					</tr>
					<tr height="18">
						<td align="center" colspan="2">
							<input type="button" class="button" name="okey_input" onClick="return(my_check(this.form))" value="��&nbsp;&nbsp;��"> 
<%--					    &nbsp;&nbsp;<input type="reset" name="Reset" value="" class="reset_input">--%>
						</td>
					</tr>
</table>
</body>
</html>
<script type="text/javascript" language="JavaScript1.2">
function my_check(FormName)
{
	var tlrno=document.getElementById("telno").value;
    var oldpwd = document.getElementById("passwd").value ;
    var pwd1 = document.getElementById("passwd1").value;
    var pwd2 = document.getElementById("passwd2").value;
    if(oldpwd!="<%=telMst.getPasswd()%>"){alert("��������ȷ��ԭʼ���룡");}
    else if(pwd1.length<6){alert("�����볤�Ȳ���С��6λ��");}
    else if(pwd1!=pwd2){alert("�������������벻ͬ��");}
    else{
    	var url = "<%=request.getContextPath()%>/telmst_pwdUpdate.action";
		new Ajax.Request(url, {
			method : 'post',
			parameters : {
			     tlrno : tlrno,passwd:pwd1
			},
			onSuccess : function() {
		    	   art.dialog({
	               	    title:'�ɹ�',
	           		    icon: 'succeed',
	           		    content: '�޸ĳɹ�,�����µ�¼��',
	           		    ok: function () {
	           		    	ok();
	           		        return true;
	           		    }
	            	 });
			}
		});
	} 
}
function ok() {
	/* var sreturn = "ok";
	window.returnValue=sreturn; */
	window.close();
	window.top.open("<%=request.getContextPath()%>/logoutSession.jsp", "_self");
}
</script>
