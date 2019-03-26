<%@ page contentType="text/html;charset=GBK"%>
<%--<%@page import=""%>--%>
<style>
    .x-panel-body .x-panel-body-noheader .x-panel-body-noborder{
        width: 100%;
    }
</style>
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
    <%  
 	String ftpResultDescribe = (String)request.getAttribute("ftpResultDescribe");
    String checkAll = (String)request.getAttribute("checkAll");
%>
	<div id="tabs" style="margin: 1px 0px ">
      <div id="tab1" class="x-hide-display" style="height:300;width:100%;">
         <iframe src="<%=request.getContextPath()%>/pages/ul06Save.jsp?checkAll=<%=checkAll %>" id="tab1-iframe" frameborder="0" scrolling="auto" style="border:0px none;" width="100%" height="100%"></iframe>
      </div>
      <div id="tab2" class="x-hide-display" style="height:300;width:100%;">
         <iframe src="<%=request.getContextPath()%>/pages/ul06SaveError.jsp" id="tab2-iframe" frameborder="0" scrolling="auto" style="border:0px none;" width="100%" height="100%"></iframe>
      </div>
  </div>
</body>
<script type="text/javascript">
jQuery(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
	var ftpResultDescribe = '<%=ftpResultDescribe%>';
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