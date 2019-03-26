<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.util.*,java.text.*,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.CommonFunctions" errorPage="" %>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>期限匹配历史价格查看</title>
</head>
  <body>
  <div class="cr_header">
			当前位置：期限匹配->期限匹配历史价格查看
		</div>
    <iframe src="/ftp/pages/ul07Search.jsp" id="upframe" width="1020" height="110" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" align="middle"></iframe> 
    <iframe src="" id="downframe" width="1020" height="330" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes" align="middle" ></iframe> 
  </body>
</html>

