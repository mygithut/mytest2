<%@ page contentType="text/html;charset=GBK"%>
<%@ page language="java" import="java.util.*,com.dhcc.ftp.entity.*"
	session="true"%>
<%
	TelMst telmst = (TelMst) request.getSession().getAttribute(
			"userBean");
%>
<html>
	<head>
		<title>��������</title>
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
		<meta http-equiv="pragram" content="no-cache" />
		<jsp:include page="../commonJs.jsp"></jsp:include>
		<jsp:include page="../commonExt2.0.2.jsp" />
		<script type="text/javascript"
				src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg3.js'></script>
		<%
		String brNo =(String)request.getAttribute("brNo"); 
		%>
	</head>
	<base target="_self">
	<body>
		<form action="<%=request.getContextPath()%>/brmst_add.action" method="post" name="add" id="add">
			<table width="80%" align="center"
				style="border: #CCC 1px solid; rules: none; margin-top: 20px;"
				class="table">
				<tr>
					<th width="40%" align="right">
						������ţ�
					</th>
					<td width="60%">
						<input type="text" name="brNo" size="20" maxlength="20" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						�������ƣ�
					</th>
					<td width="60%">
						<input type="text" name="brName" size="40" maxlength="50">
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						���������ˣ�
					</th>
					<td width="60%">
						<input type="text" name="chargePersonName" size="20"
							maxlength="20">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						���ڻ������룺
					</th>
					<td width="60%">
						<input type="text" name="fbrCode" size="20" maxlength="20">
					</td>
				</tr>

				<tr>
					<th width="40%" align="right">
						��ϵ��ַ��
					</th>
					<td width="60%">
						<input type="text" name="conAddr" size="20" maxlength="50">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						�������룺
					</th>
					<td width="60%">
						<input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="postNo" size="20" maxlength="50">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right" class="std">
						��ϵ�ˣ�
					</th>
					<td width="60%">
						<input type="text" name="conPersonName" size="20" maxlength="50">
					</td>
				</tr>
			<%--	<tr>
					<th width="40%" align="right">
						�ϼ�������
					</th>
					<td width="60%">
						<input type="text" name="superBrNo" size="20" maxlength="50"
							value="<%=brNo%>" readonly="readonly">
						<font color="red">*</font>
					</td>
				</tr>--%>
				<tr>
					<input name="superBrNo" id="superBrNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
					<input name="superBrName" id="superBrName" type="hidden" value="<%=telmst.getBrMst().getBrName() + "["+ telmst.getBrMst().getBrNo() + "]"%>" />
					<td width="40%" align="right" class="std">
						�ϼ�������
					</td>
					<td width="60%">
						<div id='comboxWithTree1'></div>
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						��ϵ�绰��
					</th>
					<td width="60%">
						<input type="text" onkeyup="value=value.replace(/[^\d-]/g,'')" name="conPhone" size="20" maxlength="50">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						���棺
					</th>
					<td width="60%">
						<input type="text" onkeyup="value=value.replace(/[^\d-]/g,'')" name="fax" size="20" maxlength="50">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						�Ƿ�ҵ����������
					</th>
					<td width="60%">
						<select id ="ywjg" name="ywjg" style="width: 150"> 
						<option value="1"  selected="selected">��</option>
						<option value="0">��</option>
						</select>
					</td>
				</tr>
				<tr>
					<td  colspan="2" align="center">
							<input type="button" name="Submit"
								onclick=submit_onclick(this.form); value="��&nbsp;��"
								class="button">
					</td>
				</tr>
			</table>
		</form>
	</body>

	<SCRIPT language=javascript>
	function submit_onclick(frm) {
	    console.info(frm.brNo);

		if (!(isNull(frm.brNo, "�������"))) {
			frm.brNo.focus();
			return false;
		}
		if (!(isNumber(frm.brNo, "�������"))) {

			frm.brName.focus();
			return false;
		}
		if (!(isNull(frm.brName, "��������"))) {

			frm.brName.focus();
			return false;
		}

		if (!(isNull(frm.ywjg, "�Ƿ�ҵ��������"))) {
			frm.ywjg.focus();
			return false;
		}
<%--		if (!(isNull(frm.chargePersonName, "����������"))) {--%>
<%----%>
<%--			frm.chargePersonName.focus();--%>
<%--			return false;--%>
<%--		}--%>
<%--		if (!(isNull(frm.conAddr, "��ϵ��ַ"))) {--%>
<%--			frm.conAddr.focus();--%>
<%--			return false;--%>
<%--		}--%>
<%--		if (!(isNull(frm.postNo, "��������"))) {--%>
<%--			return false;--%>
<%--		}--%>
<%--		if (!(isNumber(frm.postNo, "��������"))) {--%>
<%--			return false;--%>
<%--		}--%>
<%--		var num = document.all("postNo").value;--%>
<%--		if (num.length != 6) {--%>
<%--			alert("��������ӦΪ6λ����!");--%>
<%--			return false;--%>
<%--		}--%>
<%--		if (!(isNull(frm.conPhone, "��ϵ�绰"))) {--%>
<%--			frm.conPhone.focus();--%>
<%--			return false;--%>
<%--		}--%>
		if (!(isNull(frm.superBrNo, "��һ��������"))) {

			frm.superBrNo.focus();
			return false;
		}
		$('add').request({   
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
			       }else  {
			    	   art.dialog({
		               	    title:'ʧ��',
		           		    icon: 'error',
		           		    content: '�ñ��'+frm.brNo.value+'�Ѵ��ڣ�',
		           		    cancelVal: '�ر�',
		           		    cancel: true
		            	 });
			       }
					
			   }
		   });
	}
	function ok () {
	    window.parent.location.reload();
	}
</SCRIPT>

</html>
