<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.PageUtil"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<meta http-equiv="expires" content="0" />
			<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
			<meta http-equiv="pragram" content="no-cache" />
			<title></title>
			<jsp:include page="../commonJs.jsp" />
		</head>
		<%
String telNo = (String)request.getAttribute("telNo");
String telName = (String)request.getAttribute("telName");
String brNo = (String)request.getAttribute("brNo");
%>
		<body>
   		  <form action="" id="form1" method="get" style="display: none;">		
			<table id="tableList">
				<thead>
					<tr>
						<th width="250">
							机构名称
						</th>
						<th width="100">
							操作员号
						</th>
						<th width="100">
							姓名
						</th>
						<th width="100">
							角色
						</th>
						<th width="100">
							员工
						</th>
						<th width="100">
							选择
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator var="item" status="status"
						value="#request.pageUtil.getList()">

						<tr
							onclick="javascript:setvalue('<s:property value="#item.telNo" />');">
							<td>
								<s:property value="#item.brMst.brName" />
							</td>
							<td>
								
								<s:property value="#item.telNo" />
							</td>
							<td>
								
								<s:property value="#item.telName" />
							</td>
							<td>
								<s:property value="#item.roleMst.roleName" />
							</td>
							<td>
								<s:property value="#item.ftpEmpInfo.empName" />
							</td>
							<td>
								<input type="radio" name="choo"
									id="radio_<s:property value="#item.telNo" />">
							</td>
						</tr>
					</s:iterator>
				</tbody>
				<input type="hidden" name="tlrno" id="tlrno" value="">
			</table>
			<table border="0" width="90%" class="tb1"
				style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
				align="center">
				<tr>
					<td colspan="3" align="right"><%=((PageUtil) request.getAttribute("pageUtil"))
							.getPageLine()%></td>
				</tr>
			</table>
		  </form>
		</body>
		<script type="text/javascript" language="javascript">
j(function(){
    document.getElementById("form1").style.display="block";
    j('#tableList').flexigrid({
    		height: 240,width:800,
    		title: '操作员管理',
    		buttons : [
   			   		{name: '新增', bclass: 'add', onpress : add},
   			   		{name: '查看详情', bclass: 'edit', onpress : datil_com},
   			   		{name: '密码重置', bclass: 'key', onpress : passwordReset},
   			   		{name: '删除', bclass: 'delete', onpress : onclick_del}
   			   		]});
});

	function setvalue(tlrno1) {
		document.getElementById("radio_"+tlrno1).checked=true;
		document.getElementById("tlrno").value = tlrno1;
	}

	function onclick_del() {
		var tlrno = document.getElementById("tlrno").value;
		if (tlrno.length <= 0) {
			alert("请选中一条记录！");
			return false;
		} else {
			art.dialog({
		        title:'删除',
		    	icon: 'question',
		        content: '确定删除?',
		        ok: function () {
				var url = "<%=request.getContextPath()%>/telmst_delete.action";
				new Ajax.Request(url, {
					method : 'post',
					parameters : {
					     tlrno : tlrno
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

	function ok () {
		var telName = '<%=telName%>';
		var telNo = '<%=telNo%>';
		var brNo = '<%=brNo%>';
		window.location.href = "<%=request.getContextPath()%>/telmst_list.action?telNo="+telNo+"&telName="+encodeURI(telName)+"&brNo="+brNo;
	}
	function datil_com() {
		var tlrno = document.getElementById("tlrno").value;
		if (tlrno.length <= 0) {
			alert("请选中一条记录！");
			return false;
		}
		art.dialog.open('<%=request.getContextPath()%>/telmst_search.action?tlrno=' + tlrno + '&remark=1'+'&random='+<%=Math.random()%>, {
		    title: '查看详情',
		    width: 700,
		    height:250
		});
	}
	function modifyrole() {
		var tlrno = document.getElementById("tlrno").value;
		if (tlrno.length <= 0) {
			alert("请选中一条记录！");
			return false;
		}
		art.dialog.open('<%=request.getContextPath()%>/telmst_modfiyRole.action?tlrno=' + tlrno + '&random='+<%=Math.random()%>, {
		    title: '修改角色',
		    width: 600,
		    height:200
		});
	}


	//密码重置
	function passwordReset() {
		var tlrno = document.getElementById("tlrno").value;
		if (tlrno.length <= 0) {
			alert("请选中一条记录！");
			return false;
		} else {
			var url = "<%=request.getContextPath()%>/telmst_pwdreset.action";
			new Ajax.Request(url, {
				method : 'post',
				parameters : {
				     tlrno : tlrno
				},
				onSuccess : function() {
			    	   art.dialog({
		               	    title:'成功',
		           		    icon: 'succeed',
		           		    content: '重置成功！',
		           		    ok: function () {
		           		    	ok();
		           		        return true;
		           		    }
		            	 });
				}
			});
		}
	}

	function add() {
		art.dialog.open('<%=request.getContextPath()%>/telmst_insertStart.action?random='+<%=Math.random()%>, {
		    title: '新增角色',
		    width: 700,
		    height:250
		});
	}
</script>