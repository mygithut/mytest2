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
<div class="cr_header">��ǰλ�ã�ϵͳ����->��ɫ����</div>
<table align="center" class="toolbar" >
	<tr><td>
		<input type="button" class="button" value="��&nbsp;&nbsp;��" onclick="javascript:submit_onclick(this.form);">
		&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" value="��&nbsp;&nbsp;��" onclick="javascript:history.go(-1);">
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

	<p><a href="javascript:  d.closeAll();">չ������</a> | <a href="javascript: d.openAll();">�ر�����</a></p>

	<script type="text/javascript">
		<!--
		d = new dTree('d');
		var tree = document.getElementById("tree").value;
		var tree1 = tree.split("|");
		d.add(0,-1,'�˵�����');
		for (var i = 0; i < tree1.length; i++) {
			 var tree2 = tree1[i].split(",");
			 if (tree2[4] == 'true'){
				 d.add(tree2[0],tree2[1],'menuNo',tree2[2],tree2[3],tree2[4]);
			 }else{
				 d.add(tree2[0],tree2[1],'menuNo',tree2[2],tree2[3]);
			 }
		}
		// dTreeʵ�������Դ�Ϊ��  �ڵ�ID������ID��chechbox�����ƣ�chechbox��ֵ��chechbox����ʾ���ƣ�chechbox�Ƿ�ѡ��--Ĭ���ǲ�ѡ��chechbox�Ƿ���ã�Ĭ���ǿ��ã��ڵ����ӣ�Ĭ����������
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
	
	function onechecked(val){//ѡ���е�id 
	var elem = document.getElementById(val);//ȡ������е� ��ѡ��
	elem.checked =!elem.checked;            //ȡ��
	}
	
	
	function submit_onclick(form1){
		$('update').request({   
	        method:"post",
	        parameters:{t:new Date().getTime()},
	        onComplete: function(){ 
		    	   art.dialog({
	               	    title:'�ɹ�',
	           		    icon: 'succeed',
	           		    content: '�޸ĳɹ���',
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
