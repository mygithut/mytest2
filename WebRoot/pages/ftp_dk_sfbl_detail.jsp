<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>

<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
	
		
		<title>贷款上浮比例调整</title>
	</head>
	<body>
	<% Ftp1DkSfblAdjust dk=(Ftp1DkSfblAdjust)request.getAttribute("dk"); %>
		<form action="<%=request.getContextPath()%>/FtpDksfblAdjust_save.action"  method="post" id="update" name="update" >	    
			<table width="80%" align="center" class="table" cellpadding="0"	 cellspacing="0">
			<input type="hidden" name="id" id="id" value="${dk.id}" 	/>
				<tr>
					<td align="right">
						最小上浮比例(%) ：
					</td>
					<td>
					<c:if test="${dk.minPercent!=-100.0 }">
						<input type="text" name="minPercent" id="minPercent"   value="<%=dk.getMinPercent()==null?0:CommonFunctions.doubleFormat(dk.getMinPercent(),2)%>" 	  onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</c:if>
					<c:if test="${dk.minPercent==-100.0 }">-100.0
						<input type="hidden" name="minPercent" id="minPercent"   value="<%=dk.getMinPercent()==null?0:CommonFunctions.doubleFormat(dk.getMinPercent(),2)%>" 	  onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</c:if>
					</td>
				</tr>
				<tr>
					<td align="right">
						最大上浮比例(%) ：
					</td>
					<td>
					<c:if test="${dk.maxPercent!=9999999999.99 }">
						<input type="text" name="maxPercent" id="maxPercent"   value="<%=dk.getMaxPercent()==null?0:CommonFunctions.doubleFormat(dk.getMaxPercent(),2)%>" 	  onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</c:if>
					<c:if test="${dk.maxPercent==9999999999.99 }">
						+∞
						<input type="hidden" name="maxPercent" id="maxPercent"   value="<%=dk.getMaxPercent()==null?0:CommonFunctions.doubleFormat(dk.getMaxPercent(),2)%>" 	  onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</c:if>
					</td>
				</tr>
				<tr>
					<td align="right">
						调整值(%)：
					</td>
					<td>
						<input type="text"   name="adjustValue" id="adjustValue"  value="<%=dk.getAdjustValue() == null ? "" : CommonFunctions.doublecut(dk.getAdjustValue() *100,1) %>"  onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<input type="button" value="保&nbsp;&nbsp;存" class="red button"	onclick="javascript:doSave(this.form)" />
					</td>
				</tr>
			</table>
	</form>
	</body>
	<script type="text/javascript">
<%--	j(document).ready(function(){--%>
<%--		j("input[type='text']").click(function(){--%>
<%--			this.select();--%>
<%--		});--%>
<%--	});--%>
    function doSave(FormName){
 		$('update').request({   
 		       method:"post",
 		       onSuccess : function(response) {
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
    function ok(){
		  window.parent.location.reload();
	}
	</script>
</html>

