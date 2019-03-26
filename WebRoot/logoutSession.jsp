<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.dhcc.ftp.filter.SessionUserListener" %>
<%
String path=request.getContextPath()+"/login.jsp";
//session.invalidate();
System.out.println("\"redirect\"="+request.getParameter("redirect"));
String telNo = (String)session.getAttribute("userid");
SessionUserListener.removeUserSession(telNo); //移除当前登录用户的session
if ("false".equals(request.getParameter("redirect"))) {
	
} else {
	try {
		response.sendRedirect(path);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
%>
