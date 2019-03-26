<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.*,com.dhcc.ftp.entity.RoleMst,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
		<meta http-equiv="pragram" content="no-cache" />
		<title></title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="../commonJs.jsp"></jsp:include>
		<%
String roleNo = (String)request.getSession().getAttribute("roleNo");
String roleName = (String)request.getSession().getAttribute("roleName");
%>


	</head>
	<body>
	<form action="" id="form1" method="get" style="display: none;">
		<div class="cr_header">
			当前位置：系统管理->角色管理
		</div>
		<%  
	PageUtil pageUtil=(PageUtil)request.getAttribute("pageUtil");
	List<RoleMst> list = (List<RoleMst>)pageUtil.getList();
	String str =(String)request.getAttribute("mag");
	if(null != str && !"".equals(str)){
		out.println("<font color='red' align='right'>"+str+"</font>");
	}
%>
        <div align="center">
		<table id="tableList">
			<thead>
				<tr>
					<th width="100" align="center">
						角色编号
					</th>
					<th width="150" align="center">
						角色名称
					</th>
					<th width="150" align="center">
						角色级别
					</th>
					<th width="100" align="center">
						操作
					</th>

				</tr>
			</thead>
			<tbody>
			<%for(RoleMst roleMst : list) { %>
					<tr
						onclick="javascript:setvalue('<%=roleMst.getRoleNo() %>','<%=roleMst.getRoleName() %>');">
						<td align="center">
							<%=roleMst.getRoleNo() %>
						</td>
						<td align="center">
							<%=roleMst.getRoleName() %>
						</td>
						<td align="center">
							<%=CastUtil.CastRoleLvl(roleMst.getRoleLvl(), "") %>
						</td>
						<td align="center">
							<input type="radio" name="choo"
								id="<%=roleMst.getRoleNo() %>">
						</td>
					</tr>
			<%} %>
			</tbody>
			<input type="hidden" id="roleno" name="roleno" value="">
				<input type="hidden" id="hidname" name="hidname" value="">
		</table>
		</div>
		<table border="0" width="50%" class="tb1"
			style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
			align="center">
			<tr>
				<td align="right"><%=pageUtil.getPageLine()%>
				</td>
			</tr>
		</table>
      </form>		
	</body>
	<script type="text/javascript">
	document.getElementById("form1").style.display="block";
j(function(){
    j('#tableList').flexigrid({
    		height: 240,width:600,
    		title: '角色管理',
    		buttons : [
   			   		{name: '新增', bclass: 'add', onpress : add},
   			   		{name: '查看详情', bclass: 'edit', onpress : detail},
   			   		{name: '菜单设置', bclass: 'list', onpress : menu},
   			   		{name: '删除', bclass: 'delete', onpress : delete_role}
   			   		]});
});
function delete_role(){
	var roleno = document.getElementById("roleno").value;
	if (typeof(roleno)=="undefined" || roleno.length==0)
   {
	alert("请选择一项");
	return false;
   }else{
	   art.dialog({
	        title:'删除',
	    	icon: 'question',
	        content: '确定删除?',
	        ok: function () {
		    var url = "<%=request.getContextPath()%>/rolemst_del.action";
			new Ajax.Request(url, {
				method : 'post',
				parameters : {
				roleNo : roleno
				},
				onSuccess : function() {
			    	   art.dialog({
		               	    title:'成功',
		           		    icon: 'succeed',
		           		    content: '删除成功！',
		           		    ok: function () {
		           		    	ok();
		           		        return true;
		           		    }
		            	 });
				}
			  });
	        },
	        cancelVal: '关闭',
	        cancel: true //为true等价于function(){}
	    });
   }
}

function setvalue(no,name){
	document.getElementById("roleno").value = no;
	document.getElementById("hidname").value = name;
	document.getElementById(no).checked=true;
}

function detail(){
	var roleno = document.getElementById("roleno").value;
	if(roleno.length != 0){
		art.dialog.open('<%=request.getContextPath()%>/rolemst_detail.action?roleNo='+roleno+'&rand='+ Math.random(), {
		    title: '详情',
		    width: 600,
		    height:200
		});
		}else{
		alert("请选择一项");
	}
}
function menu(){
	var roleno = document.getElementById("roleno").value;
	if (typeof(roleno)=="undefined" || roleno.length==0)
   {
		alert("请选择一项");
		return false;
   }else{
   		window.location="<%=request.getContextPath()%>/rolemst_menuSearch.action?roleNo="+roleno+"&random="+<%=Math.random()%>;
   }
}

function add() {

	art.dialog.open('<%=request.getContextPath()%>/rolemst_roleAdd.action?random='+<%=Math.random()%>, {
	    title: '新增角色',
	    width: 600,
	    height:200
	});
}

function ok () {
	window.location.href = "<%=request.getContextPath()%>/rolemst_list.action?roleNo=<%=roleNo%>&roleName=<%=roleName%>";
}
</script>
</html>