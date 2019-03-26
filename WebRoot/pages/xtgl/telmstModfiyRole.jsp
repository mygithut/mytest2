<%@ page contentType="text/html;charset=GBK"%>
<%@ page language="java" import="java.util.*" session="true"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>

<%
	TelMst telMst = (TelMst) request.getAttribute("telMst");
	String xct = request.getContextPath();
	String[] lst = (String[]) request.getAttribute("roleNoList");
	String[] lst2 = (String[]) request.getAttribute("roleNameList");
	String trmno1 = "";
	trmno1 = telMst.getRoleMst().getRoleNo();
	if (null == trmno1 || "".equals(trmno1)) {
		trmno1 = "1";
	}
%>
<html>
	<head>
		<title>操作员更改角色</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
	<br/>
		<table width="80%" align="center" class="table">
			<tr>
				<th width="36%" align="right">
					操作员号码：
				</th>
				<td width="64%">
					<input type="text" name="tlrno" value="<%=telMst.getTelNo()%>"
						maxlength="30" size="18" readonly />
				</td>
			</tr>
			<tr>
				<th width="36%" align="right">
					操作员姓名：
				</th>
				<td width="64%">
					<input type="text" value="<%=telMst.getTelName()%>" maxlength="30"
						size="18" readonly />
				</td>
			</tr>
			<tr>
				<th width="36%" align="right">
					新角色选择：
				</th>
				<td width="64%">
					<select name="roleno" id="roleno">
					</select>
				</td>
			</tr>
			<tr height="30">
				<td align="center" colspan="2">
					<input type="button" name="Submit1"
						onClick="return(my_check(this.form))" value="保&nbsp;存"
						class="button">
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</body>
</html>
<script type="text/javascript" language="JavaScript1.2">
fillSelectLast('roleno','<%=request.getContextPath()%>/fillSelect_getRoleLvl.action','<%=telMst.getRoleMst().getRoleNo()%>');
function my_check(FormName)
{
var tlrno = $("tlrno").value;
var roleno = $("roleno").value;
art.dialog({
    title:'删除',
	icon: 'question',
    content: '确定修改该操作员的角色?',
    ok: function () {
	var url = "<%=request.getContextPath()%>/telmst_updateRole.action";
	new Ajax.Request(url, {
		method : 'post',
		parameters : {
		     tlrno : tlrno,roleNo:roleno
		},
		onSuccess : function() {
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
    },
    cancelVal: '关闭',
    cancel: true //为true等价于function(){}
});
}
function ok () {
    window.parent.location.reload();
}
</script>
