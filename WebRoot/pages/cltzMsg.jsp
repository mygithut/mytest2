<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.Ftp1PrdtClAdjust"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String message=String.valueOf(request.getAttribute("message"));
	Ftp1PrdtClAdjust po=(Ftp1PrdtClAdjust)request.getAttribute("clPo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	
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
			 art.dialog({
				  title:'提示',
				  icon: img,
				  content: content,
				  ok: function () {
					window.location.href="<%=basePath%>/CLTZ_getList.action?businessNo=<%=po.getBusinessNo()%>&productNo=<%=po.getProductNo()%>&brNo=<%=po.getBrNo()%>";
				 }
			});
				
		}
		 onLoad('<%=message%>');
		</script>
	</body>
</html>
