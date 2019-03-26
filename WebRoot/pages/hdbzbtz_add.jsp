<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%
	TelMst telmst = (TelMst) request.getSession().getAttribute(
			"userBean");
%>
<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<title>金融市场业务导入</title>
	</head>
	<body>
		
		<form action="<%=request.getContextPath()%>/HDBZBTZ_save.action" method="post" id="update" name="update" >
			<table width="80%" align="center" class="table" cellpadding="0"				cellspacing="0">
				<tr>
					<td align="right">
						最小存贷比(%)：
					</td>
					<td>
						<input type="text" name="minRatio" id="minRatio" maxlength="7"   onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						最大存贷比(%)：
					</td>
					<td>
						<input type="text" name="maxRatio" id="maxRatio"  maxlength="7"	   onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</td>
				</tr>
<%--				<tr>--%>
<%--					<td align="right">--%>
<%--						机构 ：--%>
<%--					</td>--%>
<%--					<td>--%>
<%--						<div id='comboxWithTree1' style="width: 300px;"></div>--%>
<%--						<input name="brNo" id="brNo" type="hidden"		value="<%=telmst.getBrMst().getBrNo()%>" />--%>
<%--						<input name="brName" id="brName" type="hidden"	value="<%=telmst.getBrMst().getBrName() + "["+ telmst.getBrMst().getBrNo() + "]"%>" />--%>
<%--					</td>--%>
<%--				</tr>--%>
				<tr>
					<td align="right">
						调整值(BP)：
					</td>
					<td>
						<input type="text" name="adjustValue" id="adjustValue"   onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
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
	/*起始日期-结束日期，日期面板生产js*/
    function doSave(FormName) {
    	 if(!(isNull(FormName.minRatio,"最小存贷比"))) {
     		return false;			
         }
         if(!(isNull(FormName.maxRatio,"最大存贷比"))) {
     		return false;			
         }
         if(!(isNull(FormName.adjustValue,"调整值"))) {
     		return false;			
         }
         var min =FormName.minRatio.value;
         var max =FormName.maxRatio.value;
        // alert(min); alert(max);
         if(parseFloat(min,10)>=parseFloat(max,10)){
        	 alert("最小值不能大于最大值");
        	 return ;
          }
       
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

