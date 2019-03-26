<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String ac_id = request.getParameter("ac_id");
String empNo = request.getParameter("empNo");
String rate = request.getParameter("rate");
String relType = request.getParameter("relType");
String prdtNo = request.getParameter("prdtNo");
if(null==relType||relType.equals("")||relType.isEmpty()){
	relType="0";
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>员工账户关联</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	<jsp:include page="../commonJs.jsp" />
	</script>
  </head>
  
  <body>
  <form action="">
  <table class="table" align="center" width="95%">
  <tr>
	  <td class="middle_header" colspan="2">
	  <font
		style="padding-left: 10px; color: #333; font-size: 14px; font-weight: bold">关联操作</font>
	  </td>
  </tr>
  <tr>
      <td align="center">关联方式</td>
      <td align="center">
         <select  id="relType" name="relType" onchange="javascript:changeSelected(this.value)">
            <option value="">请选择</option>
         <%if(null!=relType&&!relType.equalsIgnoreCase("null")&&relType.equals("1")){%>
            <option value="1" selected="selected">固定比例</option>
            <option value="2">固定金额</option>
         <%}else if(null!=relType&&!relType.equalsIgnoreCase("null")&&relType.equals("2")){%>
            <option value="1">固定比例</option>
            <option value="2" selected="selected">固定金额</option>
         <%}else{%>
            <option value="1">固定比例</option>
            <option value="2">固定金额</option>
         <%} %>
         </select>
      </td>
  </tr>
  <tr>
	   <td align="center" colspan="2">
		  <iframe src='' id="iframe" name="iframe" width="100%"	height="300" frameborder="no" marginwidth="0" marginheight="0"		scrolling="no" allowtransparency="yes" align="center"></iframe>
	   </td>
  </tr>
  </table>
  </form>
  </body>
  <script type="text/javascript">
  j(function(){
	   if(<%=relType%>!=""&&null!=<%=relType%>&&<%=relType%>=="1"){
			window.frames.iframe.location.href ='<%=basePath%>/YGZHGL_getRelatedEmp.action?ac_id=<%=ac_id%>&empNo=<%=empNo%>&rate=<%=rate%>&prdtNo=<%=prdtNo%>&relType=1';
	   }else if(<%=relType%>!=''&&null!=<%=relType%>&&<%=relType%>=="2"){
		    window.frames.iframe.location.href ='<%=basePath%>/YGZHGL_getRelatedEmp.action?ac_id=<%=ac_id%>&empNo=<%=empNo%>&rate=<%=rate%>&prdtNo=<%=prdtNo%>&relType=2';
	   }
	});
  function changeSelected(value){
     window.frames.iframe.location.href ='<%=basePath%>/YGZHGL_getRelatedEmp.action?ac_id=<%=ac_id%>&empNo=<%=empNo%>&rate=<%=rate%>&relType=<%=relType%>&prdtNo=<%=prdtNo%>&value='+value;
  }

  </script>
</html>
