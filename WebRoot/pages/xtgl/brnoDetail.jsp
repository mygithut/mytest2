<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" session="true"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
	    <%
			BrMst brmst = (BrMst)request.getAttribute("brmst");
			String brNo =(String)request.getAttribute("brNo");
		%>
<html>
	<head>
		<meta http-equiv="expires" content="0" /> 
        <meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
        <meta http-equiv="pragram" content="no-cache" />
		<title>机构管理</title>
            <jsp:include page="../commonJs.jsp"></jsp:include>

	</head>
	<base target="_self">

	<body>
		<form action="<%=request.getContextPath()%>/brmst_update.action" method="post" id="update" name="update">
					<table width="80%" align="center" style="border:#CCC 1px solid;rules:none;margin-top:20px;" class="table">
							<tr>
								<th width="40%" align="right">
									机构编号：
								</th>
								<td width="60%" height="11">
									<input type="text" name="brNo" size="20" maxlength="50" value="<%=brmst.getBrNo()%>" readonly/>
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<th width="40%" align="right">
									机构名称：
								</th>
								<td width="60%">
									<input type="text" name="brName" size="40" maxlength="50" value="<%=brmst.getBrName()%>"/>
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<th width="40%" align="right">
									机构负责人：
								</th>
								<td width="60%">
									<input type="text" name="chargePersonName" size="20" value="<%=CastUtil.trimNull(brmst.getChargePersonName())%>"
										maxlength="50"/>
								</td>
							</tr>
							<tr >
								<th width="40%" align="right">
									金融机构代码：
								</th>
								<td width="60%">
									<input type="text" name="fbrCode" size="20" maxlength="50" value=<%=CastUtil.trimNull(brmst.getFbrCode())%>>
								</td>
							</tr>

							<tr>
								<th width="40%" align="right">
									联系地址：
								</th>
								<td width="60%">
									<input type="text" name="conAddr" size="20" maxlength="50" value="<%=CastUtil.trimNull(brmst.getConAddr())%>">
								</td>
							</tr>
							<tr>
								<th width="40%">
										邮政编码：
								</th>
								<td width="60%">
									<input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="postNo" size="20" maxlength="50" value="<%=CastUtil.trimNull(brmst.getPostNo()) %>">
								</td>
							</tr>
							<tr>
								<th width="40%" align="right">
									联系人：
								</th>
								<td width="60%">
									<input type="text" name="conPersonName" size="20" maxlength="50" value="<%=CastUtil.trimNull(brmst.getConPersonName())%>">
								</td>
							</tr>
								<tr>
								<th width="40%" align="right">
									上级机构：
								</th>
								<td width="60%">
									<input type="text" name="superBrNo" size="20" maxlength="50" value="<%=brmst.getSuperBrNo()%>" readonly>
								    <font color="red">*</font>
								</td>
							</tr>
							<tr>
								<th width="40%" align="right">
									联系电话：
								</th>
								<td width="60%">
									<input type="text" onkeyup="value=value.replace(/[^\d-]/g,'')"  name="conPhone" size="20" maxlength="50" value="<%=CastUtil.trimNull(brmst.getConPhone())%>">
								</td>
							</tr>
							<tr>
								<th width="40%" align="right">
									传真：
								</th>
								<td width="60%">
									<input type="text" onkeyup="value=value.replace(/[^\d-]/g,'')" name="fax" size="20" maxlength="50" value="<%=CastUtil.trimNull(brmst.getFax())%>">
								</td>
							</tr>
							<tr>
								<th width="40%" align="right">
									是否业务办理机构：
								</th>
								<td width="60%">
								  <select id ="ywjg" name ="ywjg" style="width:150">
								    <option value="1" <%if(CastUtil.trimNull(brmst.getIsBusiness()).equals("1")){ %>selected<%} %>>是</option>
								    <option value="0" <%if(CastUtil.trimNull(brmst.getIsBusiness()).equals("0")){ %>selected<%} %>>否</option>
								  </select>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
										<input type="button" name="Submit" class="button"
											onclick=submit_onclick(this.form); value="保&nbsp;&nbsp;存">
					
				</td>
			</tr>
		</table>
		<input type="hidden" value="<%=brmst.getManageLvl() %>" id="manageLvl" name="manageLvl">
		</form>
	</body>

	<SCRIPT language=javascript>
	function submit_onclick(frm) {
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

			frm.brName.focus();
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
		if(!(isNull(frm.superBrNo,"上一级机构号"))) {

			frm.superBrNo.focus();
			return false;
		}
		$('update').request({   
		       method:"post",
		       parameters:{t:new Date().getTime()},
		       onSuccess : function() {
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
</SCRIPT>

</html>
