<%@ page contentType="text/html;charset=GBK"%>
<%@ page language="java"
	import="java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.TelMst"
	session="true"%>
<head>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
	<meta http-equiv="pragram" content="no-cache" />
	<title>��ѯ��¼�ɹ�</title>
	<%
		TelMst telMst = (TelMst) request.getAttribute("telMst");
        TelMst userBean = (TelMst) request.getSession().getAttribute("userBean");
	%>

	<jsp:include page="../commonJs.jsp" />
	<jsp:include page="../commonExt2.0.2.jsp" />
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
</head>
<body>
	<form action="<%=request.getContextPath()%>/telmst_update.action" method="post" name="update"
		id="update">
		<table width="80%" align="center"
			style="border: #CCC 1px solid; rules: none; margin-top: 20px;"
			class="table">
			<input type="hidden" name="rightflag" value="" />
			<tr>
			    <input name="brNo" id="brNo" type="hidden" value="<%=telMst.getBrMst().getBrNo()%>" />
				<input name="brName" id="brName" type="hidden"  value="<%=telMst.getBrMst().getBrName() + "[" + telMst.getBrMst().getBrNo() + "]"%>" />
				<th width="36%" align="right">
					<font>�������ƣ�</font>
				</th>
				<td><div id='comboxWithTree1'></div>
				</td>
			</tr>
			<tr>
				<th align="right">
					<font>����Ա�ţ�</font>
				</th>
				<td><%=telMst.getTelNo()%><input type="hidden" name="tlrno"
						value="<%=telMst.getTelNo()%>" />
				</td>
			</tr>
			<tr>
				<th align="right">
					<font>��ɫ��</font>
				</th>
				<td>
					<select name="roleNo" id="roleNo">
					</select>
				</td>
			</tr>
			<tr>
				<th align="right">
					<font>������</font>
				</th>
				<td>
					<input class="input1" name="name" maxlength="15" size="15"
						value="<%=telMst.getTelName()%>" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
					<th align="right">
						Ա����
					</th>
					<td>
						<input type="hidden" name="empNo" id="empNo" value="<%=telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getEmpNo()%>" />
						<input type="text" name="empName" maxlength="15" size="15" id="empName" value="<%=telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getEmpName()%>" readonly="readonly" />
						<input type="button" value="ѡ��" class="red button"
							onclick="javascript:doSelEmp()" />
					</td>
				</tr>
			<tr height="30">
				<td colspan="2" align="center">
					<input type="button" value="��&nbsp;&nbsp;��"
						onclick="submit_onclick(this.form)" class="button">
				</td>
			</tr>
		</table>
	</form>
</body>


<script type="text/javascript" language="javascript">
fillSelectLast('roleNo','<%=request.getContextPath()%>/fillSelect_getRoleLvl.action','<%=telMst.getRoleMst().getRoleNo()%>');
function submit_onclick(frm){
	if(!(isNull(frm.name,"����"))) {
		return false;			
    }
		
   $('update').request({   
       method:"post",
       parameters:{t:new Date().getTime()},
       onSuccess : function() {
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
function doSelEmp(){
	art.dialog.open('<%=request.getContextPath()%>/pages/selEmpS.jsp?Rnd='+Math.random(), {
	    title: 'ѡ��',
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
	<%if(telMst.getTelNo().equals(userBean.getTelNo())){%>
    window.top.open("<%=request.getContextPath()%>/logoutSession.jsp", "_self");
    <%}else{%>
    window.parent.location.reload();
    <%}%>
}
</script>
