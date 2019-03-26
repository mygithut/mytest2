<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<head> 
<title>增加角色录入 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" />
<jsp:include page="../commonJs.jsp"></jsp:include>
</head>
<%
String roleNo = (String)request.getSession().getAttribute("roleNo");
String roleName = (String)request.getSession().getAttribute("roleName");
%>
<script type="text/javascript" language="javascript">

function getrightstr(rkm){
	if(!(strIsAllAngleAllPage(rkm))) {
		return false;			
	}
 	if(!(isNull(rkm.roleName,"角色名称"))) {
  		return false;			
	}
	if(!(isNull(rkm.roleLvl,"角色级别"))) {
  		return false;			
	}
	$('save').request({   
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
<SCRIPT language=javascript src="<%=request.getContextPath() %>/pages/js/validate.js"></SCRIPT>
<body>

<form action="<%=request.getContextPath()%>/rolemst_add.action" method="post" name="save" id="save">
  <table width="80%" align="center" style="border:#CCC 1px solid;rules:none;margin-top:20px;" class="table">
    <tr>
      <th align="right" width="27%"><font align="left">角色名称：</font></th>
      <td><input type="text" class="input1"  id="roleName" name="roleName" value="" maxlength="10"><font color="red">*</font></td>
    </tr>
	<tr >
		<th align="right" width="27%"><font align="left">角色级别：</font></th>
		<td> 
			<select  style="width:150" id ="roleLvl" name="roleLvl">
				<option value="1">低级</option>
				<option value="2">中级</option>
				<option value="3">次高级</option>
				<option value="4">高级</option>
			</select>
		<font color="red">*</font>
		</td>
	</tr>
    <tr>
    	<th align="right"><font align="left">备注：</font></th>
      	<td> <input type="text" class="input1"  name="filler" size="40" maxlength="100"></td>
    </tr>
  	<tr>
      <td colspan="2" align="center">
      <input type="button" value="保&nbsp;&nbsp;存" onclick="javascript:getrightstr(this.form);" class="button" >
      </td>
    </tr>
  </table>
 </form>

</body>
</html>




