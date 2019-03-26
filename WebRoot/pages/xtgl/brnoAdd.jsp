<%@ page contentType="text/html;charset=GBK"%>
<%@ page language="java" import="java.util.*,com.dhcc.ftp.entity.*"
	session="true"%>
<%
	TelMst telmst = (TelMst) request.getSession().getAttribute(
			"userBean");
%>
<html>
	<head>
		<title>机构管理</title>
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
						机构编号：
					</th>
					<td width="60%">
						<input type="text" name="brNo" size="20" maxlength="20" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						机构名称：
					</th>
					<td width="60%">
						<input type="text" name="brName" size="40" maxlength="50">
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						机构负责人：
					</th>
					<td width="60%">
						<input type="text" name="chargePersonName" size="20"
							maxlength="20">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						金融机构代码：
					</th>
					<td width="60%">
						<input type="text" name="fbrCode" size="20" maxlength="20">
					</td>
				</tr>

				<tr>
					<th width="40%" align="right">
						联系地址：
					</th>
					<td width="60%">
						<input type="text" name="conAddr" size="20" maxlength="50">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						邮政编码：
					</th>
					<td width="60%">
						<input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="postNo" size="20" maxlength="50">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right" class="std">
						联系人：
					</th>
					<td width="60%">
						<input type="text" name="conPersonName" size="20" maxlength="50">
					</td>
				</tr>
			<%--	<tr>
					<th width="40%" align="right">
						上级机构：
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
						上级机构：
					</td>
					<td width="60%">
						<div id='comboxWithTree1'></div>
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						联系电话：
					</th>
					<td width="60%">
						<input type="text" onkeyup="value=value.replace(/[^\d-]/g,'')" name="conPhone" size="20" maxlength="50">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						传真：
					</th>
					<td width="60%">
						<input type="text" onkeyup="value=value.replace(/[^\d-]/g,'')" name="fax" size="20" maxlength="50">
					</td>
				</tr>
				<tr>
					<th width="40%" align="right">
						是否业务办理机构：
					</th>
					<td width="60%">
						<select id ="ywjg" name="ywjg" style="width: 150"> 
						<option value="1"  selected="selected">是</option>
						<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td  colspan="2" align="center">
							<input type="button" name="Submit"
								onclick=submit_onclick(this.form); value="保&nbsp;存"
								class="button">
					</td>
				</tr>
			</table>
		</form>
	</body>

	<SCRIPT language=javascript>
	function submit_onclick(frm) {
	    console.info(frm.brNo);

		if (!(isNull(frm.brNo, "机构编号"))) {
			frm.brNo.focus();
			return false;
		}
		if (!(isNumber(frm.brNo, "机构编号"))) {

			frm.brName.focus();
			return false;
		}
		if (!(isNull(frm.brName, "机构名称"))) {

			frm.brName.focus();
			return false;
		}

		if (!(isNull(frm.ywjg, "是否业务办理机构"))) {
			frm.ywjg.focus();
			return false;
		}
<%--		if (!(isNull(frm.chargePersonName, "机构负责人"))) {--%>
<%----%>
<%--			frm.chargePersonName.focus();--%>
<%--			return false;--%>
<%--		}--%>
<%--		if (!(isNull(frm.conAddr, "联系地址"))) {--%>
<%--			frm.conAddr.focus();--%>
<%--			return false;--%>
<%--		}--%>
<%--		if (!(isNull(frm.postNo, "邮政编码"))) {--%>
<%--			return false;--%>
<%--		}--%>
<%--		if (!(isNumber(frm.postNo, "邮政编码"))) {--%>
<%--			return false;--%>
<%--		}--%>
<%--		var num = document.all("postNo").value;--%>
<%--		if (num.length != 6) {--%>
<%--			alert("邮政编码应为6位数字!");--%>
<%--			return false;--%>
<%--		}--%>
<%--		if (!(isNull(frm.conPhone, "联系电话"))) {--%>
<%--			frm.conPhone.focus();--%>
<%--			return false;--%>
<%--		}--%>
		if (!(isNull(frm.superBrNo, "上一级机构号"))) {

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
		               	    title:'成功',
		           		    icon: 'succeed',
		           		    content: '新增成功！',
		           		    ok: function () {
		           		    	ok();
		           		        return true;
		           		    }
		            	 });
			       }else  {
			    	   art.dialog({
		               	    title:'失败',
		           		    icon: 'error',
		           		    content: '该编号'+frm.brNo.value+'已存在！',
		           		    cancelVal: '关闭',
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
