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
		<title>����Ա��������¼��</title>
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
						�������ƣ�
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
						���룺
					</th>
					<td>
						<input type="password" name="passwd" class="input1" maxlength="6"
							size="12" value="000000" readonly="readonly" />
						<font color="red">*</font> ��ʼ����Ϊ&quot;000000&quot;
					</td>
				</tr>
				<tr>
					<th align="right">
						����Ա����:
					</th>
					<td>
						<input class="input1" onkeyup="value=value.replace(/[^\d\.]/g,'')"
							name="tlrno" maxlength="6" size="7" />
						<font color="red">*</font> ���������д6λ��
					</td>
				</tr>
				<tr>
					<th align="right">
						��ɫ��
					</th>
					<td>
						<select class="input1" name="roleNo" id="roleNo">
						</select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th align="right">
						������
					</th>
					<td>
						<input class="input1" name="name" maxlength="15" size="15"
							value="" />
						<font color="red">*</font>
					</td>
				</tr>

				<tr>
					<th align="right">
						Ա����
					</th>
					<td>
						<input type="hidden" name="empNo" id="empNo" />
						<input type="text" name="empName" id="empName" maxlength="15" size="15" readonly="readonly" />
						<input type="button" value="ѡ��" class="red button"
							onclick="javascript:doSelEmp()" />
					</td>
				</tr>
				<tr height="30">
					<td colspan="2" align="center">
						<input type="button" name="Submit1" value="��&nbsp;&nbsp;��"
							onclick="submit_onclick(this.form)" class="button">
						<%--&nbsp;&nbsp;&nbsp;&nbsp;--%>
						<%--        <input type="button" name="Reset" value="��&nbsp;&nbsp;��" onclick="javascript:back()" class="enter-input">--%>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script language="javascript">
	fillSelect('roleNo','<%=request.getContextPath()%>/fillSelect_getRoleLvl.action');


function submit_onclick(frm){
      
	  if(!(isNull(frm.passwd,"����"))) {
			return false;			
	  }
	  if(!(isNull(frm.name,"����"))) {
			return false;			
	  }
	   if(!(isNull(frm.tlrno,"����Ա���"))) {
			return false;			
	  }
	  if((frm.tlrno.value).length != 6) {
	  alert("����Ա��ű�������λ��");
			return false;			
	  }	  
	  if(!(isNull(frm.roleNo,"��ɫ"))) {
			return false;			
	  }
	  $('insert').request({
	       method:"post",
	       parameters:{t:new Date().getTime()},
	       onSuccess : function(res) {
				var resp = res.responseText;
			       if (resp == '1') {
			    	   art.dialog({
		               	    title:'�ɹ�',
		           		    icon: 'succeed',
		           		    content: '�����ɹ���',
		           		    ok: function () {
		           		    	ok();
		           		        return true;
		           		    }
		            	 });
			       }else if(resp == '0')  {
			    	   art.dialog({
		               	    title:'ʧ��',
		           		    icon: 'error',
		           		    content: '����Ա���'+frm.tlrno.value+'�Ѿ����ڣ����޸ģ�',
		           		    cancelVal: '�ر�',
		           		    cancel: true
		            	 });
			       }else{
			    	   art.dialog({
		               	    title:'ʧ��',
		           		    icon: 'error',
		           		    content: '����ʧ�ܣ�����ϵ����Ա',
		           		    cancelVal: '�ر�',
		           		    cancel: true
		            	 });
			       }
			       
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
	    window.parent.location.reload();
	}

</script>
</html>

