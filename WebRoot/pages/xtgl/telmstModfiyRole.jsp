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
		<title>����Ա���Ľ�ɫ</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
	<br/>
		<table width="80%" align="center" class="table">
			<tr>
				<th width="36%" align="right">
					����Ա���룺
				</th>
				<td width="64%">
					<input type="text" name="tlrno" value="<%=telMst.getTelNo()%>"
						maxlength="30" size="18" readonly />
				</td>
			</tr>
			<tr>
				<th width="36%" align="right">
					����Ա������
				</th>
				<td width="64%">
					<input type="text" value="<%=telMst.getTelName()%>" maxlength="30"
						size="18" readonly />
				</td>
			</tr>
			<tr>
				<th width="36%" align="right">
					�½�ɫѡ��
				</th>
				<td width="64%">
					<select name="roleno" id="roleno">
					</select>
				</td>
			</tr>
			<tr height="30">
				<td align="center" colspan="2">
					<input type="button" name="Submit1"
						onClick="return(my_check(this.form))" value="��&nbsp;��"
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
    title:'ɾ��',
	icon: 'question',
    content: 'ȷ���޸ĸò���Ա�Ľ�ɫ?',
    ok: function () {
	var url = "<%=request.getContextPath()%>/telmst_updateRole.action";
	new Ajax.Request(url, {
		method : 'post',
		parameters : {
		     tlrno : tlrno,roleNo:roleno
		},
		onSuccess : function() {
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
    },
    cancelVal: '�ر�',
    cancel: true //Ϊtrue�ȼ���function(){}
});
}
function ok () {
    window.parent.location.reload();
}
</script>
