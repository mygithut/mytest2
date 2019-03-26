<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<%@page import="com.dhcc.ftp.entity.Ftp1PrdtClAdjust"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	List list =(List)request.getAttribute("clList");
	Ftp1PrdtClAdjust adjust=(Ftp1PrdtClAdjust)request.getAttribute("clPo");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>策略调整</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonDatePicker.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<style type="text/css">
		.table td{ text-align: center;}
		</style>
	</head>
	<body>
	 <c:if test="${fn:length(clList)>0}">
		 <form id="form1" name="form1" method="post" action="<%=basePath%>/CLTZ_save.action">
			 <div style="overflow-y: scroll;height: 300px">
				 <table width="100%" class="table"  cellpadding="0" cellspacing="0" align="left" style="" >
					 <tr>
						 <th width="5%">序号</th>
						 <th width="30%">机构名称</th>
						 <th width="15%">业务类型</th>
						 <th width="30%">产品名称</th>
						 <th width="20%">调整值</th>
					 </tr>
					 <%
						 for(int i=0;i<list.size() ;i++){
							 Object[] obj=(Object[])list.get(i);
							 String adjustId =String.valueOf(obj[3]);
							 String value=String.valueOf(obj[4]);
							 value=value==null||"null".equals(value)?"0":value;
							 adjustId=adjustId==null||"null".equals(adjustId)?"":adjustId;
					 %>
					 <tr>
						 <td><%=i+1 %></td>
						 <td><%=obj[5]==null?"":LrmUtil.getBrName(obj[5].toString())%></td>
						 <td><%=obj[0] %></td>
						 <td><%=obj[1] %></td>
						 <td>
							 <input  name="pos[<%=i %>].brNo"   value="<%=String.valueOf(obj[5])%>" type="hidden"/>
							 <input  name="pos[<%=i %>].productNo"   value="<%=String.valueOf(obj[2])%>" type="hidden"/>
							 <input  name="pos[<%=i %>].adjustId"   value="<%=adjustId%>" type="hidden"/>
							 <input  name="pos[<%=i %>].adjustValue" size="10"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=CommonFunctions.doublecut(Double.valueOf(value)*10000,1)%>" type="text"/>
						 </td>
					 </tr>
					 <%} %>
				 </table>
				 <table border="0" width="100%" class="tb1"
						style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
						align="center">
					 <tr>
						 <td align="center">
							 <input  name="param1"   value="<%=adjust.getProductNo()%>" type="hidden"/>
							 <input  name="param2"   value="<%=adjust.getBusinessNo()%>" type="hidden"/>
							 <input  name="param3"   value="<%=adjust.getBrNo()%>" type="hidden"/>
							 <input name="query" class="button" type="button" onclick="javascript:dosubmit()" id="query" value="保&nbsp;&nbsp;存" />
						 </td>
					 </tr>
				 </table>
			 </div>
		 </form>
	 </c:if>
	</body>
<script language="javascript">
function dosubmit(){
	document.getElementById("form1").submit();
}
</script>
</html>
