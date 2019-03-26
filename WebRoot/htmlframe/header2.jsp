<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.*,java.util.*"%>
	<head>
		<title></title>
		<link type="text/css" href="css/styles.css" rel="stylesheet">
			<%TelMst user = (TelMst) request.getSession().getAttribute("userBean"); 
			String today = DateUtil.getCurrentDay();
			 %>
	</head>
	<body>
	    <div class="nav2" >
	    &nbsp;&nbsp;<%=today.substring(0,4)+"年"+today.substring(4,6)+"月"+today.substring(6,8)+"日" %>
	    &nbsp;&nbsp;操作员：<%=user.getTelName()+"["+user.getTelNo()+"]" %>
	    &nbsp;&nbsp;部门权限：<%=user.getBrMst().getBrName()+"["+user.getBrMst().getBrNo()+"]" %>
	    &nbsp;&nbsp;角色：<%=user.getRoleMst().getRoleName()+"["+user.getRoleMst().getRoleNo()+"]" %>
	    </div>

		<script>
</script>
	</body>
</html>
