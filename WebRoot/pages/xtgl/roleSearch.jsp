<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
	    <meta http-equiv="expires" content="0" /> 
        <meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
        <meta http-equiv="pragram" content="no-cache" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/style_body.css" type="text/css">
<script  type="text/javascript" language="JavaScript1.2" >
function submit_onclick(FormName){	 
	   FormName.submit()
}	

</script>
</head>
<body bgcolor=#FFFFFF alink=#333333 vlink=#333333 link=#333333 topmargin=0 leftmargin=0 onload="">
<div class="cr_header">当前位置：系统管理->角色管理</div>
<form action="<%=request.getContextPath()%>/rolemst_list.action" method="post">
<table width="50%" align="center" style="border:#CCC 1px solid;rules:none;margin-top:50px;">
    <tr height="26">
	   <td class="middle_header" colspan="2" style="padding-top:5px; padding-left:10px; color:#333; font-weight:bold">
	     角色管理
	   </td>
	</tr>
	<tr >
		<td width="36%" align="right">角色编号：
		</td>
		<td width="64%">
			<input type="text" name="roleNo" maxlength="6" size="12" />
		</td>
	</tr>
	<tr >
		<td width="36%" align="right">角色名称：
		</td>
		<td width="64%">
			<input type="text" name="roleName" maxlength="6" size="12" />
		</td>
	</tr>
	<tr><td colspan="2"></td></tr>
	<tr>
      <td colspan="2" align="center">
      	<input  type="button" name="Submit1" onclick="submit_onclick(this.form)" class="okey_input"/>
     	&nbsp;&nbsp;&nbsp;
     	<input type="reset" name="Submit2" value="" class="reset_input">
     </td>
	</tr> 
</table>
</form>
</body>
</html>


