<%@ page contentType="text/html;charset=GBK"%>
<%--<%@page import=""%>--%>
<html>
<head>
		<title></title>
	    <meta http-equiv="expires" content="0" /> 
        <meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
        <meta http-equiv="pragram" content="no-cache" />
		
        <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonExt2.0.2.jsp" />
		<jsp:include page="commonJs.jsp" />
<%--        <jsp:include page="commonExt2.0.2.jsp" />--%>
</head>
<body>
		<div class="cr_header">
			当前位置：期限匹配->期限匹配业务模拟定价结果
		</div>
    <%  
 	String ftpResultDescribe = (String)request.getAttribute("ftpResultDescribe");
%>
<br/>
<div align="center">
<input name="back" class="button" type="button" id="price" height="20" onClick="history.go(-1);" value="返&nbsp;&nbsp;回" />
</div>
	<div id="tabs" style="margin: 1px 20px ">
      <div id="tab1" class="x-hide-display" style="height:380;width:100%;">
         <iframe src="<%=request.getContextPath()%>/pages/qxppywmndj_result_success.jsp" id="tab1-iframe" frameborder="0" scrolling="auto" style="border:0px none;" width="100%" height="100%"></iframe>
      </div>
      <div id="tab2" class="x-hide-display" style="height:380;width:100%;">
         <iframe src="<%=request.getContextPath()%>/pages/qxppywmndj_result_error.jsp" id="tab2-iframe" frameborder="0" scrolling="auto" style="border:0px none;" width="100%" height="100%"></iframe>
      </div>
  </div>
</body>
<script type="text/javascript">
jQuery(document).ready(function(){
	parent.parent.parent.cancel();
});
var tabs;
Ext.onReady(function(){
    // basic tabs 1, built from existing content
    tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        activeTab: 0,
        frame:true,
        defaults:{autoHeight: true},
        items:[{
            id:"tab1",
            contentEl:'tab1',
            title: '成功结果'
        },{
            id:"tab2",
            contentEl:'tab2',
            title: '失败结果'
        }]
    });
});
</script>
</html>