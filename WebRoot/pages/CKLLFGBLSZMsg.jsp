<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String message=String.valueOf(request.getAttribute("message"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Ա������ά��</title>
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<script type="text/javascript">
		function onLoad(msg){
			var content,img;
			if(msg=="ok"){
				content="�޸ĳɹ�";
				img="succeed";
			}else{
				content="idΪ " +msg+ " �޸�ʧ��!!!";
				img="error";
			}
			//alert(content);
			//window.location.href="<%=basePath%>/FtpInterestMarginDivide_getList.action";
			 art.dialog({
				  title:'��ʾ',
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
