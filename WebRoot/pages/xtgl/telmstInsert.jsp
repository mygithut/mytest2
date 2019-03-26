<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%@ taglib uri="/struts-tags" prefix="s"%>



<%
	TelMst user = (TelMst) session.getAttribute("userBean");
	String[] lst = (String[]) request.getAttribute("roleNoList");
	String[] lst2 = (String[]) request.getAttribute("roleNameList");
%>
<html>
	<head>
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
		<meta http-equiv="pragram" content="no-cache" />
		<title>操作员基本资料录入</title>
		<jsp:include page="../commonJs.jsp" />
		<jsp:include page="../commonExt2.0.2.jsp" />
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
	</head>
	<body>
		<form action="<%=request.getContextPath()%>/telmst_insert.action"
			name="insert" id="insert" method="post">
			<table width="80%" align="center" class="table">
				<tr>
					<th width="25%" align="right">
						机构名称：
					</th>
					<input name="brNo" id="brNo" type="hidden"
						value="<%=user.getBrMst().getBrNo()%>" />
					<input name="brName" id="brName" type="hidden"
						value="<%=user.getBrMst().getBrName() + "[" + user.getBrMst().getBrNo() + "]"%>" />
					<td width="75%">
						<div id='comboxWithTree1'></div>
					</td>
				</tr>

				<tr>
					<th align="right">
						密码：
					</th>
					<td>
						<input type="password" name="passwd" class="input1" maxlength="6"
							size="12" value="000000" readonly="readonly" />
						<font color="red">*</font> 初始密码为&quot;000000&quot;
					</td>
				</tr>
				<tr>
					<th align="right">
						操作员号码:
					</th>
					<td>
						<input class="input1" onkeyup="value=value.replace(/[^\d\.]/g,'')"
							name="tlrno" maxlength="6" size="7" />
						<font color="red">*</font> 此项必须填写6位数
					</td>
				</tr>
				<tr>
					<th align="right">
						角色：
					</th>
					<td>
						<select class="input1" name="roleNo" id="roleNo">
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th align="right">
						姓名：
					</th>
					<td>
						<input class="input1" name="name" maxlength="15" size="15"
							value="" />
						<font color="red">*</font>
					</td>
				</tr>

				<tr>
					<th align="right">
						员工：
					</th>
					<td>
						<input type="hidden" name="empNo" id="empNo" />
						<input type="text" name="empName" id="empName" maxlength="15" size="15" readonly="readonly" />
						<input type="button" value="选择" class="red button"
							onclick="javascript:doSelEmp()" />
					</td>
				</tr>
				<tr height="30">
					<td colspan="2" align="center">
						<input type="button" name="Submit1" value="保&nbsp;&nbsp;存"
							onclick="submit_onclick(this.form)" class="button">
						<%--&nbsp;&nbsp;&nbsp;&nbsp;--%>
						<%--        <input type="button" name="Reset" value="返&nbsp;&nbsp;回" onclick="javascript:back()" class="enter-input">--%>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script language="javascript">
	fillSelect('roleNo','<%=request.getContextPath()%>/fillSelect_getRoleLvl.action');


function submit_onclick(frm){
      
	  if(!(isNull(frm.passwd,"密码"))) {
			return false;			
	  }
	  if(!(isNull(frm.name,"姓名"))) {
			return false;			
	  }
	   if(!(isNull(frm.tlrno,"操作员编号"))) {
			return false;			
	  }
	  if((frm.tlrno.value).length != 6) {
	  alert("操作员编号必须是六位！");
			return false;			
	  }	  
	  if(!(isNull(frm.roleNo,"角色"))) {
			return false;			
	  }
	  $('insert').request({
	       method:"post",
	       parameters:{t:new Date().getTime()},
	       onSuccess : function(res) {
				var resp = res.responseText;
			       if (resp == '1') {
			    	   art.dialog({
		               	    title:'成功',
		           		    icon: 'succeed',
		           		    content: '新增成功！',
		           		    ok: function () {
		           		    	ok();
		           		        return true;
		           		    }
		            	 });
			       }else if(resp == '0')  {
			    	   art.dialog({
		               	    title:'失败',
		           		    icon: 'error',
		           		    content: '操作员编号'+frm.tlrno.value+'已经存在，请修改！',
		           		    cancelVal: '关闭',
		           		    cancel: true
		            	 });
			       }else{
			    	   art.dialog({
		               	    title:'失败',
		           		    icon: 'error',
		           		    content: '新增失败！请联系管理员',
		           		    cancelVal: '关闭',
		           		    cancel: true
		            	 });
			       }
			       
		   }
	   });
	}
function doSelEmp(){
	art.dialog.open('<%=request.getContextPath()%>/pages/selEmpS.jsp?Rnd='+Math.random(), {
	    title: '选择',
	    width: 800,
	    height:400,
	    id:'sel',
	    close: function (){
    		var paths = art.dialog.data('bValue');
    		if(paths!=null&&paths!=""){
	    		var path=paths.split("@@");
	    		document.getElementById("empNo").value=path[0];
	    		document.getElementById("empName").value=path[1];
	    	}
	     }
	});
}
	function ok () {
	    window.parent.location.reload();
	}

</script>
</html>

