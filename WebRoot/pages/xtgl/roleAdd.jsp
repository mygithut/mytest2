<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<head> 
<title>���ӽ�ɫ¼�� </title>
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
 	if(!(isNull(rkm.roleName,"��ɫ����"))) {
  		return false;			
	}
	if(!(isNull(rkm.roleLvl,"��ɫ����"))) {
  		return false;			
	}
	$('save').request({   
        method:"post",
        parameters:{t:new Date().getTime()},
        onComplete: function(){ 
	    	   art.dialog({
              	    title:'�ɹ�',
          		    icon: 'succeed',
          		    content: '����ɹ���',
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
      <th align="right" width="27%"><font align="left">��ɫ���ƣ�</font></th>
      <td><input type="text" class="input1"  id="roleName" name="roleName" value="" maxlength="10"><font color="red">*</font></td>
    </tr>
	<tr >
		<th align="right" width="27%"><font align="left">��ɫ����</font></th>
		<td> 
			<select  style="width:150" id ="roleLvl" name="roleLvl">
				<option value="1">�ͼ�</option>
				<option value="2">�м�</option>
				<option value="3">�θ߼�</option>
				<option value="4">�߼�</option>
			</select>
		<font color="red">*</font>
		</td>
	</tr>
    <tr>
    	<th align="right"><font align="left">��ע��</font></th>
      	<td> <input type="text" class="input1"  name="filler" size="40" maxlength="100"></td>
    </tr>
  	<tr>
      <td colspan="2" align="center">
      <input type="button" value="��&nbsp;&nbsp;��" onclick="javascript:getrightstr(this.form);" class="button" >
      </td>
    </tr>
  </table>
 </form>

</body>
</html>




