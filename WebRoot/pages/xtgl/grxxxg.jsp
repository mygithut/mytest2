<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String xct = request.getContextPath();
	TelMst user = (TelMst) request.getSession().getAttribute("userBean");
%>

<html>
<head>
<title>个人信息修改</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/color.js"></script> --%>
<script rel="stylesheet" src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/artDialog.source.js?skin=blue"></script>
<script src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/plugins/iframeTools.source.js"></script>
<jsp:include page="../commonJs.jsp" />
</head>
<body topmargin=0 leftmargin=0 onLoad="" onkeydo if (event.keyCode== 13) event.keyCode=9">
<div class="cr_header">当前位置：系统管理->个人信息修改</div>
<table width="50%" align="center" style="border:#CCC 1px solid;rules:none;margin-top:50px;" class="table">
	<tr height="26">
	   <td class="middle_header" colspan="2" style="padding-top:5px; padding-left:10px; color:#333; font-weight:bold">
	     个人信息修改
	   </td>
	</tr>
					<tr>
						<td width="40%" align="right">机构号：</td>
						<td width="60%"><%=user.getBrMst().getBrNo()%></td>
					</tr>
					<tr>
						<td align="right">机构名称：</td>
						<td><%=user.getBrMst().getBrName()%></td>
					</tr>

<%--					<tr>--%>
<%--						<td align="right">密码：</td>--%>
<%--						<td><input type="password" name="passwd" class="input1" maxlength="6" size="12" value="<%=user.getPasswd() %>" readonly="readonly" /></td>--%>
<%--					</tr>--%>
					<tr>
						<td align="right">操作员号码：</td>
						<td><%=user.getTelNo()%></td>
					</tr>
					<tr>
						<td align="right">姓名：</td>
						<td><%=user.getTelName()%></td>
					</tr>
					<tr>
						<td align="right">身份证号：</td>
						<td><%=CastUtil.trimNull(user.getIdNo())%></td>
					</tr>

					<tr>
						<td align="right">角色：</td>
						<%String roleName="";if(user.getRoleMst()!=null)roleName=user.getRoleMst().getRoleName(); %>
						<td><%=roleName%></td>
					<tr>
						<td align="right">终结标志：</td>
						<td>启用</td>
					</tr>

<%--					<tr>--%>
<%--						<td align="right">每页显示的行数：</td>--%>
<%--						<td id="psl"><%=user.getPerpgShwLine()%></td>--%>
<%--					</tr>--%>
	<tr align="center">
		<td colspan="2">
		<input type="button" class="button" value="修改密码" onclick="modifypwd()" /> 
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">	
jQuery(function(){// dom元素加载完毕
	jQuery(".table tr:even").addClass("tr-bg1");
	jQuery(".table tr:odd").addClass("tr-bg2");
});
function modifypwd() {
	var tlrno = '<%=user.getTelNo()%>';
<%-- 	var sreturn = self.showModalDialog('<%=request.getContextPath()%>/telmst_pwd.action?tlrno='+tlrno,window, "dialogWidth=35;dialogHeight=16;");
	if (typeof (sreturn) != "undefined" && sreturn.length > 0) {
		window.top.open("<%=request.getContextPath()%>/logoutSession.jsp", "_self");
	} --%>
	art.dialog.open('<%=request.getContextPath()%>/telmst_pwd.action?tlrno='+tlrno, {
	    title: '修改密码',
	    width: 500,
	    height:300 
	})
	}
</script>
</html>

