<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String message=String.valueOf(request.getAttribute("message"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>员工数据维护</title>
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<script type="text/javascript">
		function onLoad(msg){
			var content,img;
			if(msg=="ok"){
				content="修改成功";
				img="succeed";
			}else{
				content="id为 " +msg+ " 修改失败!!!";
				img="error";
			}
			//alert(content);
			//window.location.href="<%=basePath%>/FtpInterestMarginDivide_getList.action";
			 art.dialog({
				  title:'提示',
				  icon: img,
				  content: content,
				  ok: function () {
					window.location.href="<%=basePath%>/CKLLFGBLSZ_getList.action";
				 }
			});
				
		}
		 onLoad('<%=message%>');
		</script>
	</body>
</html>
