<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
   <head>
<!--		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">-->
<!--		<meta http-equiv="Expires " content="0 ">-->
<!--        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">-->
<!--        <meta http-equiv="Pragma " content="no-cache ">-->
<!--        <base target="_self"> -->
		<title>收益率曲线</title>
        <jsp:include page="commonJs.jsp" />
   </head>
   <body>
        <br/>
        <% String errorMessage = (String)request.getAttribute("errorMessage"); %>
	    <font color="red"><%= errorMessage %></font>
	</body>
	<script type="text/javascript">
	parent.parent.parent.parent.cancel();
	</script>
</html>
