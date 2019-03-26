<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<%@page import="com.dhcc.ftp.entity.RoleMst,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.dao.DaoFactory"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
RoleMst roleMst=(RoleMst)request.getAttribute("roleMstBean");
String roleNo = (String)request.getSession().getAttribute("roleNo");
String roleName = (String)request.getSession().getAttribute("roleName");
%>
<html>
<head> 
<title>增加角色录入 </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../commonJs.jsp"></jsp:include>
</head>
<script type="text/javascript" language="javascript">

function getrightstr(rkm){
	
	if(!(strIsAllAngleAllPage(rkm))) {
		return false;			
	}
 	if(!(isNull(rkm.roleName,"角色名称"))) {
  		return false;			
	}
    $('update').request({   
        method:"post",
        parameters:{t:new Date().getTime()},
        onComplete: function(){ 
	    	   art.dialog({
              	    title:'成功',
          		    icon: 'succeed',
          		    content: '保存成功！',
          		    ok: function () {
          		    	ok();
          		        return true;
          		    }
           	 });
      } 
    });
}
function ok () {
    window.parent.location.reload();
}

</script>


<s:actionmessage cssStyle="color:red;font-size:12px;" />
<body  topmargin=0 leftmargin=0 onload="">
<form action="<%=request.getContextPath()%>/rolemst_update.action" method="post" name="update" id="update">
  <table width="80%" align="center" style="border:#CCC 1px solid;rules:none;margin-top:20px;" class="table">
    <tr>
      <th align="right" width="27%">角色编号：</th>
      <td><input type="text" class="input1"  id="roleNo" name="roleNo" value="<%=roleMst.getRoleNo() %>" readonly="readonly"></td>
    </tr>
    <tr>
      <th align="right" width="27%">角色名称：</th>
      <td><input type="text" class="input1"  id="roleName" name="roleName" value="<%=roleMst.getRoleName() %>" maxlength="10"><font color="red">*</td>
    </tr>
	<tr >
		<th align="right" width="27%">角色级别：</th>
		<td> 
			<select  style="width:150" id ="roleLvl" name="roleLvl">
				<option value="1">低级</option>
				<option value="2">中级</option>
				<option value="3">次高级</option>
				<option value="4">高级</option>
			</select>
			
			<font color="red">* 
		</td>
	</tr>
<%--    <tr >--%>
<%--      <td align="right">有效日期：</td>--%>
<%--      <td><input type="text" class="input1"  name="effdate" maxlength="8" size="8">--%>
<%--      <img style="CURSOR:hand" src="<%=request.getContextPath() %>/pages/images/time.gif" align="Middle" onClick="fPopUpCalendarDlg(effdate);return false"><font color="red">*--%>
<%--      --%>
<%--      </td>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--    	<td align="right">备注1：</td>--%>
<%--      	<td> <input type="text" class="input1"  name="filler" size="40" maxlength="100" value="<%=roleMst.getBrf() %>"></td>--%>
<%--    </tr>--%>
  	<tr>
      <td colspan="2" align="center">
      <input type="button" value="保&nbsp;&nbsp;存" onclick="javascript:getrightstr(this.form);" class="button" >
      </td>
    </tr>
  </table>
 </form>

</body>
<script type="text/javascript" language="javascript">
 <%if(roleMst.getRoleLvl()!=null){%>
	for(var i=0;i<$("roleLvl").length;i++){
		if($("roleLvl").options[i].value=="<%=roleMst.getRoleLvl()%>"){
			$("roleLvl").selectedIndex=i;break;}
	}
    <%}%>
</script>


