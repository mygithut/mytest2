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
	    &nbsp;&nbsp;<%=today.substring(0,4)+"��"+today.substring(4,6)+"��"+today.substring(6,8)+"��" %>
	    &nbsp;&nbsp;����Ա��<%=user.getTelName()+"["+user.getTelNo()+"]" %>
	    &nbsp;&nbsp;����Ȩ�ޣ�<%=user.getBrMst().getBrName()+"["+user.getBrMst().getBrNo()+"]" %>
	    &nbsp;&nbsp;��ɫ��<%=user.getRoleMst().getRoleName()+"["+user.getRoleMst().getRoleNo()+"]" %>
	    </div>

		<script>
</script>
	</body>
</html>
