<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.dhcc.ftp.entity.RoleMst,java.util.List"%>
<%@page import="java.util.List,java.util.ArrayList,com.dhcc.ftp.util.MenuManager,com.dhcc.ftp.util.MenuTreeReader" %>
<% 
RoleMst role = (RoleMst)request.getAttribute("roleMst");
String roleNo = (String)request.getSession().getAttribute("roleNo");
String roleName = (String)request.getSession().getAttribute("roleName");
%>

<html>
<head> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/pages/js/dtree/dtree.css" type="text/css">
<script src="<%=request.getContextPath() %>/pages/js/dtree/dtree.js"></script>
<script type="text/javascript" language=javascript src="<%=request.getContextPath()%>/pages/js/prototype.js"></script>
<script rel="stylesheet" src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/artDialog.source.js?skin=blue"></script>
<script src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/plugins/iframeTools.source.js"></script>
</head>
<body topmargin=0 leftmargin=0 >
<form action="<%=request.getContextPath()%>/rolemst_menuUpdate.action" method="post" name="update" id="update">
<div class="cr_header">当前位置：系统管理->角色管理</div>
<table align="center" class="toolbar" >
	<tr><td>
		<input type="button" class="button" value="保&nbsp;&nbsp;存" onclick="javascript:submit_onclick(this.form);">
		&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="javascript:history.go(-1);">
	</td></tr>
</table>
<input type="hidden" id="roleNo" name="roleNo" value="<%=role.getRoleNo() %>">
<input type="hidden" id="rolename" name="rolename" value="<%=role.getRoleName() %>">
<table width="600" style="margin-left:150px;">
<tr>
<td width="150"></td>
    <td width="400" height="420"  valign="top">
    <div style="width:100%;height:100%; overflow:scroll; display:block">
<div class="dtree">
<input id="tree" value="<%=MenuManager.getInstance().getMenuTreeString(role.getRoleNo()) %>" type="hidden"/>

	<p><a href="javascript:  d.closeAll();">展开所有</a> | <a href="javascript: d.openAll();">关闭所有</a></p>

	<script type="text/javascript">
		<!--
		d = new dTree('d');
		var tree = document.getElementById("tree").value;
		var tree1 = tree.split("|");
		d.add(0,-1,'菜单设置');
		for (var i = 0; i < tree1.length; i++) {
			 var tree2 = tree1[i].split(",");
			 if (tree2[4] == 'true'){
				 d.add(tree2[0],tree2[1],'menuNo',tree2[2],tree2[3],tree2[4]);
			 }else{
				 d.add(tree2[0],tree2[1],'menuNo',tree2[2],tree2[3]);
			 }
		}
		// dTree实例属性以此为：  节点ID，父类ID，chechbox的名称，chechbox的值，chechbox的显示名称，chechbox是否被选中--默认是不选，chechbox是否可用：默认是可用，节点链接：默认是虚链接
		document.write(d);
		d.openAll();
		//-->
	</script>

</div></div>
</td>
</tr>
</table>
</form>
</body>
<script language="javascript">
	function chooseall(){
	var element = document.getElementsByName('choo');
	    for(var j=0; j<element.length;j++){
	     element[j].checked = true;
	   }
	}
	function unchooseall(){
	var element2 = document.getElementsByName('choo');
	 for(var j=0; j<element2.length;j++){
	     element2[j].checked = false;
	   }
	}
	function chooallornone(){
	var elem = document.getElementById("chooelement");
	if(elem.checked == true){
	chooseall();
	}else{
	unchooseall();
	}
	}
	
	function onechecked(val){//选中行的id 
	var elem = document.getElementById(val);//取得这个行的 多选框
	elem.checked =!elem.checked;            //取反
	}
	
	
	function submit_onclick(form1){
		$('update').request({   
	        method:"post",
	        parameters:{t:new Date().getTime()},
	        onComplete: function(){ 
		    	   art.dialog({
	               	    title:'成功',
	           		    icon: 'succeed',
	           		    content: '修改成功！',
	           		    ok: function () {
	           		    	ok();
	           		        return true;
	           		    }
	            	 });
      } 
    });
}
	function ok () {
		<%if(roleNo.equals(role.getRoleNo())){%>
		    window.top.open("<%=request.getContextPath()%>/logoutSession.jsp", "_self");
		<%}%>
	}
</script>
</html>
